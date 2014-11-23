
/*-
 * Classname:             LoginFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  25/01/2013 - 15:48:14
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Visão da Janela de login
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class LoginFrame {

    //frame principal
    private JFrame frame;
    //panel com background
    private JPanel backgroudPanel;
    //panel para acesso
    private JPanel acessPanel;
    //label login
    private JLabel loginLabel;
    //label senha
    private JLabel passwordLabel;
    //campo de login
    private JTextField loginTextField;
    //campo de senha com omissão de caracteres
    private JPasswordField passwordField;
    //botão login que checa dados
    private JButton loginButton;
    //botão de configurações
    private JButton configurationButton;
    //tamanho da tela
    private int x = 0;
    private int y = 0;

    /**
     * COnstrutor sem argumentos
     */
    public LoginFrame() {
        createView();
    }//fim do construtor

    /**
     * Cria a visão
     */
    private void createView() {
        //iicializa frame e define título
        frame = new JFrame("Sistema de Gerência de Documentos do Arquivo Central da URI");
        //inicializa panel de plano de fundo
        backgroudPanel = new WallpaperPanel();
        //inicializa panel para acesso
        acessPanel = new JPanel() {
            //deixa panel translucido
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    final int R = 0;
                    final int G = 100;
                    final int B = 176;

                    Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
                            0.0f, getHeight(), new Color(R, G, B, 200), true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        //inicializa labels e fields
        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Senha:");
        loginTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        String separator = System.getProperty("file.separator");
        configurationButton = new JButton(new ImageIcon("image" + separator + "config.png"));

        //define layout como nulo
        backgroudPanel.setLayout(null);
        frame.setLayout(null);
        acessPanel.setLayout(null);

        //busca em preferencias tamanho da tela
        takeScreenSize();

        //define localização e tamanhos
        frame.setBounds(0, 0, x, y);
        backgroudPanel.setBounds(0, 0, x, y);
        acessPanel.setBounds((x - 380) / 2, (y - 240) / 2, 380, 240);
        loginLabel.setBounds(50, 45, 90, 30);
        passwordLabel.setBounds(50, 85, 90, 30);
        loginTextField.setBounds(140, 45, 150, 30);
        passwordField.setBounds(140, 85, 150, 30);
        loginButton.setBounds(200, 150, 110, 30);
        configurationButton.setBounds(acessPanel.getWidth() - 30, 10, 24, 24);

        //configurações visuais
        loginLabel.setFont(FontFactory.getFontLarge());
        passwordLabel.setFont(FontFactory.getFontLarge());
        loginTextField.setFont(FontFactory.getFontLarge());
        passwordField.setFont(FontFactory.getFontLarge());
        loginButton.setFont(FontFactory.getFontLarge());
        acessPanel.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.BLUE));
        loginTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 200), 1));
        configurationButton.setBorder(BorderFactory.createEmptyBorder());
        loginLabel.setForeground(Color.black);
        passwordLabel.setForeground(Color.black);
        loginTextField.setForeground(Color.black);
        passwordField.setForeground(Color.black);
        loginButton.setForeground(Color.black);

        //dicas
        loginTextField.setToolTipText("Insita o nome do usuário");
        passwordField.setToolTipText("Insira a senha do usuário");
        loginButton.setToolTipText("Iniciar sessão");

        //adiciona ao panel
        acessPanel.add(loginLabel);
        acessPanel.add(passwordLabel);
        acessPanel.add(loginTextField);
        acessPanel.add(passwordField);
        acessPanel.add(loginButton);
        acessPanel.add(configurationButton);

        //adiciona panel de fundo a janela
        frame.setContentPane(backgroudPanel);
        frame.add(acessPanel);

        //configurações finais
        acessPanel.setVisible(true);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //define posição inicial
        loginTextField.requestFocus();
        loginTextField.requestFocusInWindow();
    }//fim do método createView

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
     * Define visibilidade do frame principal
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }//fim do métodosetVisible

    /**
     * Fecha o frame principal
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Define se o frame está ativo
     *
     * @param enable verdadeiro-ativar e falso-desativar
     */
    public void enableFrame(boolean enable) {
        frame.setEnabled(enable);
    }//fim do método enableFrame

    /**
     * Define evento para botão login
     *
     * @param actoonListener ouvinte
     */
    public void setLoginButtonActionListener(ActionListener actoonListener) {
        loginButton.addActionListener(actoonListener);
    }//fim do método setLoginButtonActionListener

    /**
     * Define evento para o botão configurações
     *
     * @param actoonListener evento
     */
    public void setConfigurationButtonActionListener(ActionListener actoonListener) {
        configurationButton.addActionListener(actoonListener);
    }//fim do método setConfigurationButtonActionListener

    /**
     * Define evento para tecla ENTER
     *
     * @param keyListener evento de tecla
     */
    public void setFrameKeyListener(KeyListener keyListener) {
        loginTextField.requestFocus();
        loginTextField.addKeyListener(keyListener);
        passwordField.addKeyListener(keyListener);
        loginButton.addKeyListener(keyListener);
    }//fim do método setFrameKeyListener

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Obtêm login
     *
     * @return <code>String</code> login
     */
    public String getLogin() {
        return loginTextField.getText();
    }//fim do método getLogin

    /**
     * Obtêm senha
     *
     * @return <code>char[]</code> senha
     */
    public char[] getPassword() {
        return passwordField.getPassword();
    }//fim do método getPassword

    // Método para teste da interface
    public static void main(String args[]) {
        LoginFrame loginFrame = new LoginFrame();
    }//fim do método main
}//fim da classe LoginFrame

