
/*-
 * Classname:             AddDocumentAEFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  10/04/2013 - 14:34:39
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.DocumentAE;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.DateDocumentFilter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela para cadastro e edição de documento de atas e exames
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddDocumentAEFrame implements AddDocumentInterface {
//frame principal

    private JFrame frame;
    //label com caixa e livro
    private JLabel localLabel;
    //label código
    private JLabel codeLabel;
    //label disciplina
    private JLabel titleLabel;
    //label data
    private JLabel dateLabel;
    //label anexo
    private JLabel annexLabel;
    //label observação
    private JLabel observationLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para código
    private JAutoSuggestComboBox titleField;
    //campo de texto para data
    private JTextField dateField;
    //combo para anexo
    private JComboBox annexComboBox;
    //campo de texto para observação
    private JTextArea observationField;
    //scrollPane para TextArea observationField
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //documento de atas e exames
    private DocumentAE documentAE;
    //título do frame
    private String title;
    //título da caixa e do livro
    private String local;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor para cadastro com caixa e livro do atas e exames
     *
     * @param codeSugered código sugerido
     * @param titleBook título do livro
     */
    public AddDocumentAEFrame(int codeSugered, String titleBook) {
        local = titleBook;
        title = "Adicionar Documento - " + Category.AE.getName();
        createView();
        codeField.setText("" + codeSugered);

    }//fim do construtor de cadastro

    /**
     * Construtor para edição com caixa, livro e documento de aras e exames
     *
     * @param documentAE documento de atas e exames
     * @param titleBook título do livro
     */
    public AddDocumentAEFrame(DocumentAE documentAE, String titleBook) {
        local = titleBook;
        title = "Editar Documento -  " + Category.AE.getName();
        this.documentAE = documentAE;
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
        titleLabel = new JLabel("Disciplina: *");
        dateLabel = new JLabel("Data: *");
        annexLabel = new JLabel("Anexo: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        titleField = new JAutoSuggestComboBox();
        dateField = new JTextField();
        annexComboBox = new JComboBox(new Object[]{"A-E", "A", "E"});
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
        frame.setBounds((x - 500) / 2, (y - 520) / 2, 500, 520);
        localLabel.setBounds((500 - 150) / 2, 10, 150, 30);
        codeLabel.setBounds(30, 60, 90, 30);
        titleLabel.setBounds(30, 110, 120, 30);
        dateLabel.setBounds(30, 160, 120, 30);
        annexLabel.setBounds(30, 210, 120, 30);
        observationLabel.setBounds(30, 260, 100, 30);
        codeField.setBounds(140, 60, 100, 30);
        titleField.setBounds(140, 110, 250, 30);
        dateField.setBounds(140, 160, 250, 30);
        annexComboBox.setBounds(140, 210, 250, 30);
        scrollPane.setBounds(140, 260, 250, 150);
        okButton.setBounds(120, 430, 140, 30);
        cancelButton.setBounds(300, 430, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        titleLabel.setForeground(Color.BLACK);
        dateLabel.setForeground(Color.BLACK);
        annexLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        titleField.setForeground(Color.BLACK);
        dateField.setForeground(Color.BLACK);
        annexComboBox.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        localLabel.setHorizontalAlignment(JLabel.CENTER);

        //fonte
        localLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontDefault());
        dateLabel.setFont(FontFactory.getFontDefault());
        annexLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        titleField.setFont(FontFactory.getFontDefault());
        dateField.setFont(FontFactory.getFontDefault());
        annexComboBox.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código do documento ");
        titleField.setToolTipText("Insira a disciplina do documento");
        dateField.setToolTipText("Insira a data do documento");
        annexComboBox.setToolTipText("Defina os anexos do documento");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (documentAE == null) {
            okButton.setToolTipText("Cadastra documento");
            cancelButton.setToolTipText("Cancela o cadastro do documento");
        } else {
            okButton.setToolTipText("Salva alterações do documento");
            cancelButton.setToolTipText("Cancela a edição do documento");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);
        DocumentFilter dateDocumentFilter = new DateDocumentFilter();
        ((AbstractDocument) dateField.getDocument()).setDocumentFilter(dateDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(localLabel);
        frame.add(codeLabel);
        frame.add(titleLabel);
        frame.add(dateLabel);
        frame.add(annexLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(titleField);
        frame.add(dateField);
        frame.add(annexComboBox);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);

        //define evento
        //limitar tamanho do field
        dateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                JTextField tf = (JTextField) keyEvent.getComponent();
                if (tf.getText().length() >= 10 && tf.getSelectedText() == null) {
                    keyEvent.setKeyChar((char) KeyEvent.VK_CLEAR);
                }
            }
        });

        //cria popupMenu
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(titleField);
        contextPopupMenu.addInComponet(dateField);
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
        codeField.setText("" + documentAE.getCodeDocumentAE());
        titleField.setText(documentAE.getTitleDocument());

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            dateField.setText(df.format(documentAE.getDateExam()));
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(illegalArgumentException.getMessage());
            illegalArgumentException.printStackTrace();
        }
        observationField.setText(documentAE.getObservationDocument());
        String annex = documentAE.getAnnex();
        annexComboBox.setSelectedItem(annex);


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
     * @return <code>DocumentAE</code> documento
     */
    public DocumentAE getDocument() {
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (titleField.getText() == null || titleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe a disciplina do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (dateField.getText() == null || dateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe a data!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            DocumentAE documentAE = new DocumentAE();
            if (this.documentAE != null) {
                documentAE.setCodeDocument(this.documentAE.getCodeDocument());
            }
            documentAE.setCodeDocumentAE(Integer.valueOf(codeField.getText()));
            documentAE.setTitleDocument(titleField.getText());

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date date;
            date = new java.sql.Date(df.parse(dateField.getText()).getTime());
            documentAE.setDateExam(date);
            documentAE.setAnnex((String) annexComboBox.getSelectedItem());
            documentAE.setObservationDocument(observationField.getText());
            return documentAE;
        } catch (ParseException parseException) {
            return null;
        }
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
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        codeField.addKeyListener(keyListener);
        titleField.addKeyListener(keyListener);
        dateField.addKeyListener(keyListener);
        annexComboBox.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

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
     * Define lista de disciplinas para auto sugestão
     *
     * @param list com sugestôes de disciplinas
     */
    public void setTitleDocument(List<String> list) {
        titleField.setSuggestList(list);
    }//fim do método setTitleDocument

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_AE);
    }//fim do método getColor

    //método para testes
    public static void main(String[] args) {
        AddDocumentAEFrame addDocumentAEFrame = new AddDocumentAEFrame(0, "teste");
    }
}//fim da classe AddDocumentAEFrame

