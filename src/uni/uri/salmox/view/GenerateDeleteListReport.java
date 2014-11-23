/*-
 * Classname:             GenerateDeleteListReport.java
 *
 * Version information:   1.0
 *
 * Date:                  30/04/2013 - 14:54:36
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;
import uni.uri.salmox.util.TemporaryData;

/**
 * Janela para gerar relatório de exclusão
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class GenerateDeleteListReport {

//frame principal
    private JFrame frame;
    //label título do frame
    private JLabel titleFrameLabel;
    //label responsável
    private JLabel responsibleLabel;
    //label observação
    private JLabel observationLabel;
    //campo para responável
    private JAutoSuggestComboBox responsibleField;
    //campo paea observação
    private JTextArea observationField;
    //scrollpane para text area
    private JScrollPane observationPane;
    //botão gerar relatório
    private JButton generateButton;
    //botão cancelar
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor sem parâmetros
     */
    public GenerateDeleteListReport() {
        createView();
    }//fim do construtor sem parâmetros

    /**
     * Cria visão
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame("Gerar relatório");
        titleFrameLabel = new JLabel("Gerar relatório");
        responsibleLabel = new JLabel("Responsável: *");
        observationLabel = new JLabel("Observação:");
        responsibleField = new JAutoSuggestComboBox();
        observationField = new JTextArea();
        observationPane = new JScrollPane();
        generateButton = new JButton("Gerar");
        cancelButton = new JButton("Cancelar");

        //define layout
        frame.setLayout(null);

        //configura textarea
        //quebra de linha
        observationField.setLineWrap(true);
        //impede separação de palavras
        observationField.setWrapStyleWord(true);

        //define alinhamento
        titleFrameLabel.setHorizontalAlignment(JLabel.CENTER);

        //pega o tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds((x - 500) / 2, (y - 400) / 2, 500, 400);
        titleFrameLabel.setBounds((500 - 150) / 2, 10, 150, 30);
        responsibleLabel.setBounds(50, 60, 120, 30);
        observationLabel.setBounds(50, 110, 100, 30);
        responsibleField.setBounds(175, 60, 200, 30);
        observationPane.setBounds(175, 110, 250, 100);
        generateButton.setBounds(110, 320, 150, 30);
        cancelButton.setBounds(280, 320, 150, 30);

        //define cor
        frame.getContentPane().setBackground(getColor());
        titleFrameLabel.setForeground(Color.BLACK);
        responsibleLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        responsibleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        generateButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //define fonte
        titleFrameLabel.setFont(FontFactory.getFontLarge());
        responsibleLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        generateButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        responsibleField.setToolTipText("Insira o nome do responsável pela exclusão");
        observationField.setToolTipText("Se desejado insira uma observação");
        generateButton.setToolTipText("Gera o relatório de retirada");
        cancelButton.setToolTipText("Cancela");

        //adiciona os componentes ao frame
        observationPane.setViewportView(observationField);
        frame.add(titleFrameLabel);
        frame.add(responsibleLabel);
        frame.add(observationLabel);
        frame.add(responsibleField);
        frame.add(observationPane);
        frame.add(generateButton);
        frame.add(cancelButton);

        //popup menu
        //cria popupMenu
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(responsibleField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
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
     * Obtêm responsável
     *
     * @return <code>String</code>
     */
    public String getResponsible() {
        return responsibleField.getText();
    }//fim do método getResponsible

    /**
     * Obtêm observação
     *
     * @return <code>String</code>
     */
    public String getObservation() {
        return observationField.getText();
    }//fim do método getObservation

    /**
     * Define ouvinte para o botão gerar
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerGenerateButton(ActionListener actionLiatener) {
        generateButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerGenerateButton

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
        Preferences preferences = PreferencesManager.getPrefetencesUser(TemporaryData.getUser());
        byte[] b = preferences.getByteArray(PreferencesManager.KEY_COLOR_DELETE_LIST, null);
        if (b != null) {
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            ObjectInputStream is;
            Color c = null;
            try {
                is = new ObjectInputStream(in);
                c = (Color) is.readObject();
            } catch (IOException exception) {
                System.err.println("Erro: ao converter dados de cores de preferencias!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi obter cores personalizadas!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(exception);
            } catch (ClassNotFoundException exception) {
                System.err.println("Erro: ao converter dados de cores de preferencias!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi obter cores personalizadas!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(exception);
            }

            if (c != null) {
                return c;
            } else {
                return Color.WHITE;
            }
        } else {
            return Color.WHITE;
        }
    }//fim do método getColor

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame
    //método para testes

    public static void main(String[] args) {
        GenerateDeleteListReport generateDeleteListReport = new GenerateDeleteListReport();
    }
}//fim da classe GenerateDeleteListReport 

