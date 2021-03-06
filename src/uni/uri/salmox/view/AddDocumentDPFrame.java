/*-
 * Classname:             AddDocumentDPFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  15/04/2013 - 13:34:46
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela para cadastro e edição de documento de AddDocumentDPFrame
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddDocumentDPFrame implements AddDocumentInterface {
    //frame principal

    private JFrame frame;
    //label com caixa e livro
    private JLabel localLabel;
    //label código
    private JLabel codeLabel;
    //label título
    private JLabel titleLabel;
    //label observação
    private JLabel observationLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para código
    private JTextField titleField;
    //campo de texto para observação
    private JTextArea observationField;
    //scrollPane para TextArea observationField
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //documento de almoxarifado
    private DocumentDP documentDP;
    //título do frame
    private String title;
    //título da caixa e do livro
    private String local;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor para cadastro com caixa e livro do AddDocumentDPFrame
     *
     * @param codeSugered código sugerido
     * @param titleBook título do livro
     */
    public AddDocumentDPFrame(int codeSugered, String titleBook) {
        local = titleBook;
        title = "Adicionar Documento - " + Category.DP.getName();
        createView();
        codeField.setText("" + codeSugered);
    }//fim do construtor de cadastro

    /**
     * Construtor para edição com caixa, livro e documento de departamento
     * pessoal
     *
     * @param documentDP documento de departamento pessoal
     * @param titleBook título do livro
     */
    public AddDocumentDPFrame(DocumentDP documentDP, String titleBook) {
        local = titleBook;
        title = "Editar Documento - " + Category.DP.getName();
        this.documentDP = documentDP;
        createView();
        fillsView();
    }//fim do construtor de edição

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame(title);
        localLabel = new JLabel(local);
        codeLabel = new JLabel("Código: *");
        titleLabel = new JLabel("Nome: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        titleField = new JTextField();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define layout
        frame.setLayout(null);

        //configura textarea
        //quebra de linha
        observationField.setLineWrap(true);
        //impede separação de palavras
        observationField.setWrapStyleWord(true);

        //pega tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds((x - 500) / 2, (y - 480) / 2, 500, 480);
        localLabel.setBounds((500 - 150) / 2, 10, 150, 30);
        codeLabel.setBounds(30, 60, 90, 30);
        titleLabel.setBounds(30, 110, 120, 30);
        observationLabel.setBounds(30, 160, 100, 30);
        codeField.setBounds(140, 60, 100, 30);
        titleField.setBounds(140, 110, 250, 30);
        scrollPane.setBounds(140, 160, 250, 150);
        okButton.setBounds(120, 390, 140, 30);
        cancelButton.setBounds(300, 390, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        titleLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        titleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        localLabel.setHorizontalAlignment(JLabel.CENTER);

        //fonte
        localLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        titleField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código do documento ");
        titleField.setToolTipText("Insira o nome do funcionário");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (documentDP == null) {
            okButton.setToolTipText("Cadastra documento");
            cancelButton.setToolTipText("Cancela o cadastro do documento");
        } else {
            okButton.setToolTipText("Salva alterações do documento");
            cancelButton.setToolTipText("Cancela a edição do documento");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(localLabel);
        frame.add(codeLabel);
        frame.add(titleLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(titleField);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);

        //cria popupMenu
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(titleField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);


        //define focus primario
        titleField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche formulário para edição
     */
    private void fillsView() {
        codeField.setText("" + documentDP.getCodeDocumentDP());
        titleField.setText(documentDP.getTitleDocument());
        observationField.setText(documentDP.getObservationDocument());
    }//fim do método fillsView

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
     * Retorna dados dos campos em forma de documento
     *
     * @return <code>DocumentDP</code> documento
     */
    public DocumentDP getDocument() {
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (titleField.getText() == null || titleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o título do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        DocumentDP documentDP = new DocumentDP();
        if (this.documentDP != null) {
            documentDP.setCodeDocument(this.documentDP.getCodeDocument());
        }
        documentDP.setCodeDocumentDP(Integer.valueOf(codeField.getText()));
        documentDP.setTitleDocument(titleField.getText());
        documentDP.setObservationDocument(observationField.getText());
        return documentDP;
    }//fim do método getDocument

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
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DP);
    }//fim do método getColor

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        codeField.addKeyListener(keyListener);
        titleField.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame
    //método para testes

    public static void main(String[] args) {
        AddDocumentDPFrame addDocumentDPFrame = new AddDocumentDPFrame(0, "teste");

    }
}//fim da classe AddDocumentDPFrame

