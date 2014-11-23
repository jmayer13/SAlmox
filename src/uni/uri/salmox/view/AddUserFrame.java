
/*-
 * Classname:             AddUserFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  02/04/2013 - 13:19:24
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para cadastro e edição de usuários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddUserFrame {

    //frame principal
    private JFrame frame;
    //label do frame
    private JLabel frameLabel;
    //label nome
    private JLabel nameLabel;
    //label email
    private JLabel mailLabel;
    //label tipo
    private JLabel typeLabel;
    //label login
    private JLabel loginLabel;
    //label senha
    private JLabel passwordLabel;
    //campo de texto de nome
    private JTextField nameField;
    //campo de texto de email
    private JTextField mailField;
    //campo de texto de login
    private JTextField loginField;
    //campo de texto de senha
    private JPasswordField passwordField;
    //campo de texto de tipo
    private JComboBox typeField;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;
    //usuário para edição
    private User user;
    //título da janela
    private String title;

    /**
     * Construtor sem parãmetros
     */
    public AddUserFrame() {
        title = "Adicionar usuário";
        createView();

    }//fim do construtor de cadastro

    /**
     * Construtor com usuário para edição
     *
     * @param user usuário para edição
     */
    public AddUserFrame(User user) {
        title = "Editar usuário";
        this.user = user;
        createView();
        fillsView();
    }//fim do construtor de edição

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame(title);
        frameLabel = new JLabel(title);
        nameLabel = new JLabel("Nome:*");
        mailLabel = new JLabel("Email:*");
        typeLabel = new JLabel("Tipo:*");
        loginLabel = new JLabel("Login:*");
        passwordLabel = new JLabel("Senha:*");
        nameField = new JTextField();
        mailField = new JTextField();
        loginField = new JTextField();
        passwordField = new JPasswordField();
        typeField = new JComboBox(new Object[]{"Administrador", "Usuário Padrão"});
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define layout
        frame.setLayout(null);
        frameLabel.setHorizontalAlignment(JLabel.CENTER);

        //obtêm o tamanho da tela
        takeScreenSize();

        //define tamanho e posição
        frame.setBounds((x - 480) / 2, (y - 450) / 2, 480, 450);
        frameLabel.setBounds((500 - 200) / 2, 10, 200, 30);
        nameLabel.setBounds(50, 50, 60, 30);
        mailLabel.setBounds(50, 100, 60, 30);
        typeLabel.setBounds(50, 150, 60, 30);
        loginLabel.setBounds(50, 200, 100, 30);
        passwordLabel.setBounds(50, 250, 100, 30);
        nameField.setBounds(130, 50, 250, 30);
        mailField.setBounds(130, 100, 200, 30);
        loginField.setBounds(130, 200, 150, 30);
        passwordField.setBounds(130, 250, 150, 30);
        typeField.setBounds(130, 150, 150, 30);
        okButton.setBounds(100, 355, 150, 30);
        cancelButton.setBounds(270, 355, 150, 30);

        //configurações gráficas
        //cor
        frame.getContentPane().setBackground(getColor());
        frameLabel.setBackground(Color.BLACK);
        nameLabel.setBackground(getColor());
        nameLabel.setForeground(Color.BLACK);
        mailLabel.setBackground(getColor());
        mailLabel.setForeground(Color.BLACK);
        typeLabel.setBackground(getColor());
        typeLabel.setForeground(Color.BLACK);
        loginLabel.setBackground(getColor());
        loginLabel.setForeground(Color.BLACK);
        passwordLabel.setBackground(getColor());
        passwordLabel.setForeground(Color.BLACK);
        nameField.setForeground(Color.BLACK);
        mailField.setForeground(Color.BLACK);
        loginField.setForeground(Color.BLACK);
        passwordField.setForeground(Color.BLACK);
        typeField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //fonte
        frameLabel.setFont(FontFactory.getFontLarge());
        nameLabel.setFont(FontFactory.getFontDefault());
        mailLabel.setFont(FontFactory.getFontDefault());
        typeLabel.setFont(FontFactory.getFontDefault());
        loginLabel.setFont(FontFactory.getFontDefault());
        passwordLabel.setFont(FontFactory.getFontDefault());
        nameField.setFont(FontFactory.getFontDefault());
        mailField.setFont(FontFactory.getFontDefault());
        loginField.setFont(FontFactory.getFontDefault());
        passwordField.setFont(FontFactory.getFontDefault());
        typeField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        nameField.setToolTipText("Insira o nome do usuário");
        mailField.setToolTipText("Insira o e-mail do usuário");
        loginField.setToolTipText("Insira o login do usuário");
        passwordField.setToolTipText("Insira a senha do usuário");
        typeField.setToolTipText("<HTML>Defina a categoria do usuário <br />Administrador: Almoxarifado <br /> Usuário padrão: Fora do almoxarifado</HTML>");
        okButton.setToolTipText("Finaliza cadastro/edição do usuário");
        cancelButton.setToolTipText("Cancela edição do usuário");

        //junção de componentes
        frame.add(frameLabel);
        frame.add(nameLabel);
        frame.add(mailLabel);
        frame.add(typeLabel);
        frame.add(loginLabel);
        frame.add(passwordLabel);
        frame.add(nameField);
        frame.add(mailField);
        frame.add(loginField);
        frame.add(passwordField);
        frame.add(typeField);
        frame.add(okButton);
        frame.add(cancelButton);

        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(nameField);
        contextPopupMenu.addInComponet(mailField);
        contextPopupMenu.addInComponet(loginField);

        //confugurações finais
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }//fim do método createView

    /**
     * Preenche componetes com dados do usuário
     */
    private void fillsView() {
        nameField.setText(user.getName());
        mailField.setText(user.getEmail());
        loginField.setText(user.getUserLogin());
        if (user instanceof Administrator) {
            typeField.setSelectedIndex(0);
        } else {
            typeField.setSelectedIndex(1);
        }
        passwordField.setText("*****");
    }//fim do método fillsView

    /**
     * Obtêm o usuário
     *
     * @return <code>User</code> usuário
     */
    public User getUser() {
        User user;
        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (mailField.getText() == null || mailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o email!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (loginField.getText() == null || loginField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o login!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            if (passwordField.getPassword().length < 3) {
                JOptionPane.showMessageDialog(null, "A senha deve contêr pelo menos três digitos!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Informe a senha!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (this.user == null) {
            if (typeField.getSelectedIndex() == 0) {
                user = new Administrator();
            } else {
                user = new DefaultUser();
            }
        } else {
            if (typeField.getSelectedIndex() == 0 && this.user instanceof DefaultUser) {
                user = new Administrator();
                user.setCodeUser(this.user.getCodeUser());
            } else if (typeField.getSelectedIndex() == 1 && this.user instanceof Administrator) {
                user = new DefaultUser();
                user.setCodeUser(this.user.getCodeUser());
            } else {
                user = this.user;
            }
        }

        user.setName(nameField.getText());
        user.setEmail(mailField.getText());
        user.setUserLogin(loginField.getText());
        //testa se a senha foi alterada
        try {
            String testPassword = "*****";
            String password = String.valueOf(passwordField.getPassword());
            if (!password.equals(testPassword)) {
                user.setPassword(password);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return user;
    }//fim do método getUser

    /**
     * Busca o tamanho da tela
     */
    private void takeScreenSize() {
        Preferences preferences = PreferencesManager.getPreferencesSystem();
        x = preferences.getInt(PreferencesManager.KEY_SCREEN_WIDTH, 0);
        y = preferences.getInt(PreferencesManager.KEY_SCREEN_HEIGTH, 0);
        if (x == 0 || y == 0) {
            ScreenResolution resolution = new ScreenResolution();
            x = resolution.getX();
            y = resolution.getY();
        }
    }//fim do método takeScreenSize

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        nameField.addKeyListener(keyListener);
        mailField.addKeyListener(keyListener);
        loginField.addKeyListener(keyListener);
        passwordField.addKeyListener(keyListener);
        typeField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);

    }//fim do método setKeyListenerFrame

    /**
     * Fecha a janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Define visibilidade do frame
     *
     * @param visible true-visivel false-invisivel
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }//fim do método setVisible

    /**
     * Define se a janela está ativa
     *
     * @param enable true-ativada false-desativada
     */
    public void enableFrame(boolean enable) {
        frame.setEnabled(enable);
    }//fim do método enableFrame

    /**
     * Define ouvinte para o botão ok
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerOKButton(ActionListener actionLiatener) {
        okButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerOKButton

    /**
     * Define ouvinte para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenetCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenetCancelButton

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_USER);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddUserFrame addUserFrame = new AddUserFrame(new User("nome", "login", "titulo", "tipo"));
    }
}//fim da classe AddUserFrame

