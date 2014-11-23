/*-
 * Classname:             LoginController.java
 *
 * Version information:   1.0
 *
 * Date:                  22/05/2013 - 13:22:45
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.ConectionFactory;
import uni.uri.salmox.DAO.ConnectionDatabase;
import uni.uri.salmox.DAO.UserDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.LoginFrame;

/**
 * Controle do login
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class LoginController implements ObserverInterface {

    //visão do login
    private LoginFrame loginFrame;
    //DAO para usuário
    private UserDAO userDAO;

    /**
     * Construtor sem parâmetros
     */
    public LoginController() {
        userDAO = new UserDAO();
        loginFrame = new LoginFrame();
        //define evento para o botão login
        loginFrame.setLoginButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        //define evento para o botão configurações
        loginFrame.setConfigurationButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configure();
            }
        });

        //define evento para fechar janela
        loginFrame.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        //define evento para a tecla enter
        loginFrame.setFrameKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

    }//fim do construtor

    /**
     * Checa login e abre janela correspondente ao nível do usuário
     */
    private void login() {
        //pega a senha da view
        String login = loginFrame.getLogin();
        //senha
        String password = null;
        //tenta pegar e converter a senha da visão
        try {
            password = String.valueOf(loginFrame.getPassword());
        } catch (NullPointerException nullPointerException) {
            //retorna mensagem se senha vazia
            JOptionPane.showMessageDialog(null, "Digite sua senha!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //verifica se campos não estão vazios
        if (login == null || password == null) {
            if (login == null) {
                JOptionPane.showMessageDialog(null, "Digite seu login!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            }
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Digite sua senha!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            //cria User
            User user = new User();
            user.setUserLogin(login);
            user.setPassword(password);
            //checa usuário
            int type = userDAO.login(user);
            //login não encontrado
            if (type == 0) {
                JOptionPane.showMessageDialog(null, "Login incorreto ou não cadastrado!", "Atenção", JOptionPane.WARNING_MESSAGE);
            } //senha incorreta
            else if (type == 1) {
                JOptionPane.showMessageDialog(null, "Senha Incorreta!", "Atenção", JOptionPane.WARNING_MESSAGE);
            } //usuário padrão
            else if (type == 2) {
                DefaultUser defaultUser = userDAO.searchDefaultUser(login);
                TemporaryData.setUser(defaultUser);
                loginFrame.close();
                DefaultUserController defaultUserController = new DefaultUserController(defaultUser);
            } //administrador
            else if (type == 3) {
                Administrator administrator = userDAO.searchAdministrator(login);
                TemporaryData.setUser(administrator);
                loginFrame.close();
                AdministratorController administradorController = new AdministratorController(administrator);
            }
        }
    }//fim do método login

    /**
     * Abre configurações
     */
    private void configure() {
        OptionsController optionsController = new OptionsController();
        optionsController.registerObserver(this);

    }//fim do método configure

    /**
     * Fecha a visão e a conexão se criada
     */
    private void close() {

        //se a conexão foi crianda a fecha
        if (userDAO != null) {
            ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
            connectionDatabase.closeConnection();
        }
        loginFrame.close();
        System.exit(0);
    }//fim do método close

    /**
     * Atualiza observador
     *
     * @param active subjeto ativo?
     * @param object resposta
     */
    public void update(boolean active, Object object) {
        if (!active) {
            if (object instanceof Boolean) {
                boolean notice = (Boolean) object;
                if (notice) {
                    ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
                    connectionDatabase.closeConnection();
                    loginFrame.close();
                    new LoginController();
                }
            }
        }
    }//fim do método update
}//fim da classe LoginController

