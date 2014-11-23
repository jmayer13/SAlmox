/*-
 * Classname:             AddUserController.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 14:41:38
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.UserDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.AddUserFrame;

/**
 * Controle de adição/edição de usuários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddUserController implements SubjectInterface {

    //lista de observadores
    private List<ObserverInterface> observers;
    //DAO do usuário
    private UserDAO userDAO;
    //visão
    private AddUserFrame view;
    //user para edição
    private User user;

    /**
     * Construtor para adição
     */
    public AddUserController() {
        //inicializa lista de observadores
        observers = new ArrayList();
        //inicializa DAO
        userDAO = new UserDAO();
        //inicializa visão
        view = new AddUserFrame();
        //define evento da view
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        //cancelar
        view.setActionListenetCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        //fechar janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addUser();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });
    }//fim do construtor para adição

    /**
     * Construtor para edição
     *
     * @param user usuário
     */
    public AddUserController(User user) {
        //inicializa lista de observadores
        observers = new ArrayList();
        //inicializa DAO
        userDAO = new UserDAO();
        //copia user
        this.user = user;
        //inicializa visão
        view = new AddUserFrame(user);
        //define evento da view
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });
        //cancelar
        view.setActionListenetCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        //fechar janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    editUser();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });
    }//fim do construtor para edição

    /**
     * Adiciona usuário
     */
    private void addUser() {
        User user = view.getUser();
        if (user != null) {
            if (userDAO.checkLogin(user.getUserLogin())) {
                JOptionPane.showMessageDialog(null, "Erro: esse login já está sendo usado!", "Erro!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            userDAO.registerUser(user);
            view.close();
            if (user instanceof Administrator) {
                user = userDAO.searchAdministrator(user.getUserLogin());
            } else if (user instanceof DefaultUser) {
                user = userDAO.searchDefaultUser(user.getUserLogin());
            } else {
                System.err.println("Usuario sem categoria");
            }
            notifyObservers(false, user);
        }
    }//fim do método addUser

    /**
     * Edita usuário
     */
    private void editUser() {
        User user = view.getUser();
        if (user != null) {
            if ((user instanceof Administrator && this.user instanceof Administrator)
                    || (user instanceof DefaultUser && this.user instanceof DefaultUser)) {
                userDAO.editUser(user);
            } else {
                userDAO.deleteUser(user);
                userDAO.registerUser(user);
                if (user instanceof Administrator) {
                    user = userDAO.searchAdministrator(user.getUserLogin());
                } else if (user instanceof DefaultUser) {
                    user = userDAO.searchDefaultUser(user.getUserLogin());
                } else {
                    System.err.println("Usuario sem categoria");
                }
            }
            view.close();
            notifyObservers(false, user);
        }
    }//fim do método editUser

    /**
     * Cencela cadastro/edição
     */
    private void cancel() {
        view.close();
        notifyObservers(false, null);
    }//fim do método cancel

    /**
     * Fecha a janela
     */
    private void close() {
        int response = JOptionPane.showConfirmDialog(null, "Os novos dados serão perdidos! Deseja continuar? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == 0) {
            cancel();
        }
    }//fim do método close

    /**
     * Registra observador
     *
     * @param observer observador
     */
    public void registerObserver(ObserverInterface observer) {
        observers.add(observer);
    }//fim do método registerObserver

    /**
     * Remove observador da lista
     *
     * @param observer observador
     */
    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }//fim do método removeObserver

    /**
     * Notifica os observadores das mudanças
     *
     * @param active janela ativa
     * @param object objeto de retorno
     */
    public void notifyObservers(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método notifyObservers
}//fim da classe AddUserController

