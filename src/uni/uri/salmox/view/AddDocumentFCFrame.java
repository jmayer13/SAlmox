/*- 
 * Classname:             AddDocumentFC.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  02/08/2014 - 19:07:23 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.DocumentFC;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.Item;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Adiciona documento FC
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddDocumentFCFrame implements AddDocumentInterface {
    //frame principal

    private JFrame frame;
    //label com caixa e livro
    private JLabel localLabel;
    //label código
    private JLabel codeLabel;
    //label título
    private JLabel titleLabel;
    //label itens
    private JLabel itemLabel;
    //label observação
    private JLabel observationLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para código
    private JAutoSuggestComboBox titleField;
    //campo de texto para observação
    private JTextArea observationField;
    //items
    private JCheckBox schoolHistoryCheckBox;
    private JCheckBox attestCheckBox;
    private JCheckBox rgCheckBox;
    private JCheckBox cpfCheckBox;
    private JCheckBox birthCertificateCheckBox;
    private JCheckBox marriageCertificateCheckBox;
    private JCheckBox voterCheckBox;
    private JCheckBox certificateReservistCheckBox;
    private JCheckBox photoCheckBox;
    //scrollPane para TextArea observationField
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //documento de almoxarifado
    private DocumentFC documentFC;
    //título do frame
    private String title;
    //título da caixa e do livro
    private String local;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor para cadastro com caixa e livro do almoxarifado
     *
     * @param codeSugered código sugerido
     * @param titleBook título do livro
     */
    public AddDocumentFCFrame(int codeSugered, String titleBook) {
        local = titleBook;
        title = "Adicionar Documento - " + Category.A.getName();
        createView();
        codeField.setText("" + codeSugered);
    }//fim do construtor de cadastro

    /**
     * Construtor para edição com caixa, livro e documento de almoxarifado
     *
     * @param documentFC documento de almoxarifado
     * @param titleBook título do livro
     */
    public AddDocumentFCFrame(DocumentFC documentFC, String titleBook) {
        local = titleBook;
        title = "Editar Documento - " + Category.A.getName();
        this.documentFC = documentFC;
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
        titleLabel = new JLabel("Título: *");
        itemLabel = new JLabel("Itens:");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        titleField = new JAutoSuggestComboBox();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");
        schoolHistoryCheckBox = new JCheckBox("histórico escolar");
        attestCheckBox = new JCheckBox("atestado de matrícula (3ª ano)");
        rgCheckBox = new JCheckBox("RG");
        cpfCheckBox = new JCheckBox("CPF");
        birthCertificateCheckBox = new JCheckBox("certidão de nascimento");
        marriageCertificateCheckBox = new JCheckBox("certidão de casamento");
        voterCheckBox = new JCheckBox("título de eleitor");
        certificateReservistCheckBox = new JCheckBox("certificado de reservista");
        photoCheckBox = new JCheckBox("foto 3X4");

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
        frame.setBounds((x - 500) / 2, (y - 600) / 2, 500, 600);
        localLabel.setBounds((600 - 150) / 2, 10, 150, 30);
        codeLabel.setBounds(30, 60, 90, 30);
        titleLabel.setBounds(30, 110, 120, 30);
        itemLabel.setBounds(30, 150, 120, 30);

        schoolHistoryCheckBox.setBounds(140, 150, 200, 20);
        attestCheckBox.setBounds(140, 175, 200, 20);
        rgCheckBox.setBounds(140, 200, 200, 20);
        cpfCheckBox.setBounds(140, 225, 200, 20);
        birthCertificateCheckBox.setBounds(140, 250, 200, 20);
        marriageCertificateCheckBox.setBounds(140, 275, 200, 20);
        voterCheckBox.setBounds(140, 300, 200, 20);
        certificateReservistCheckBox.setBounds(140, 325, 200, 20);
        photoCheckBox.setBounds(140, 350, 200, 20);

        observationLabel.setBounds(30, 380, 100, 30);
        codeField.setBounds(140, 60, 100, 30);
        titleField.setBounds(140, 110, 250, 30);
        scrollPane.setBounds(140, 380, 300, 100);
        okButton.setBounds(120, 520, 140, 30);
        cancelButton.setBounds(300, 520, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        schoolHistoryCheckBox.setBackground(getColor());
        attestCheckBox.setBackground(getColor());
        rgCheckBox.setBackground(getColor());
        cpfCheckBox.setBackground(getColor());
        birthCertificateCheckBox.setBackground(getColor());
        marriageCertificateCheckBox.setBackground(getColor());
        voterCheckBox.setBackground(getColor());
        certificateReservistCheckBox.setBackground(getColor());
        photoCheckBox.setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        itemLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        titleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);
        schoolHistoryCheckBox.setForeground(Color.BLACK);
        attestCheckBox.setForeground(Color.BLACK);
        rgCheckBox.setForeground(Color.BLACK);
        cpfCheckBox.setForeground(Color.BLACK);
        birthCertificateCheckBox.setForeground(Color.BLACK);
        marriageCertificateCheckBox.setForeground(Color.BLACK);
        voterCheckBox.setForeground(Color.BLACK);
        certificateReservistCheckBox.setForeground(Color.BLACK);
        photoCheckBox.setForeground(Color.BLACK);

        //aligamento
        localLabel.setHorizontalAlignment(JLabel.CENTER);

        //fonte
        localLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontDefault());
        itemLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        titleField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());
        schoolHistoryCheckBox.setFont(FontFactory.getFontSmall());
        attestCheckBox.setFont(FontFactory.getFontSmall());
        rgCheckBox.setFont(FontFactory.getFontSmall());
        cpfCheckBox.setFont(FontFactory.getFontSmall());
        birthCertificateCheckBox.setFont(FontFactory.getFontSmall());
        marriageCertificateCheckBox.setFont(FontFactory.getFontSmall());
        voterCheckBox.setFont(FontFactory.getFontSmall());
        certificateReservistCheckBox.setFont(FontFactory.getFontSmall());
        photoCheckBox.setFont(FontFactory.getFontSmall());

        //dicas
        codeField.setToolTipText("Insira o código do documento ");
        titleField.setToolTipText("Insira o título do documento");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (documentFC == null) {
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
        frame.add(itemLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(titleField);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);
        frame.add(schoolHistoryCheckBox);
        frame.add(attestCheckBox);
        frame.add(rgCheckBox);
        frame.add(cpfCheckBox);
        frame.add(birthCertificateCheckBox);
        frame.add(marriageCertificateCheckBox);
        frame.add(voterCheckBox);
        frame.add(certificateReservistCheckBox);
        frame.add(photoCheckBox);

        //define evento
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
        codeField.setText("" + documentFC.getCodeDocumentSpecific());
        titleField.setText(documentFC.getTitleDocument());
        observationField.setText(documentFC.getObservationDocument());
        List<Item> items = documentFC.getItems();
        while (!items.isEmpty()) {
            Item item = items.remove(0);
            if (item == Item.schoolHistory) {
                schoolHistoryCheckBox.setSelected(true);
            }
            if (item == Item.attest) {
                attestCheckBox.setSelected(true);
            }
            if (item == Item.rg) {
                rgCheckBox.setSelected(true);
            }
            if (item == Item.cpf) {
                cpfCheckBox.setSelected(true);
            }
            if (item == Item.birthCertificate) {
                birthCertificateCheckBox.setSelected(true);
            }
            if (item == Item.marriageCertificate) {
                marriageCertificateCheckBox.setSelected(true);
            }
            if (item == Item.voter) {
                voterCheckBox.setSelected(true);
            }
            if (item == Item.certificateReservist) {
                certificateReservistCheckBox.setSelected(true);
            }
            if (item == Item.photo) {
                photoCheckBox.setSelected(true);
            }
        }

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
     * @return <code>DocumentFC</code> documento
     */
    public DocumentFC getDocument() {
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (titleField.getText() == null || titleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o título do documento!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        DocumentFC documentFC = new DocumentFC();
        if (this.documentFC != null) {
            documentFC.setCodeDocument(this.documentFC.getCodeDocument());
        }
        documentFC.setCodeDocumentSpecific(Integer.valueOf(codeField.getText()));
        documentFC.setTitleDocument(titleField.getText());
        documentFC.setObservationDocument(observationField.getText());


        if (schoolHistoryCheckBox.isSelected()) {
            documentFC.addItem(Item.schoolHistory);
        }
        if (attestCheckBox.isSelected()) {
            documentFC.addItem(Item.attest);
        }
        if (rgCheckBox.isSelected()) {
            documentFC.addItem(Item.rg);
        }
        if (cpfCheckBox.isSelected()) {
            documentFC.addItem(Item.cpf);
        }
        if (birthCertificateCheckBox.isSelected()) {
            documentFC.addItem(Item.birthCertificate);
        }
        if (marriageCertificateCheckBox.isSelected()) {
            documentFC.addItem(Item.marriageCertificate);
        }
        if (voterCheckBox.isSelected()) {
            documentFC.addItem(Item.voter);
        }
        if (certificateReservistCheckBox.isSelected()) {
            documentFC.addItem(Item.certificateReservist);
        }
        if (photoCheckBox.isSelected()) {
            documentFC.addItem(Item.photo);
        }
        return documentFC;
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
        observationField.addKeyListener(keyListener);
        scrollPane.addKeyListener(keyListener);
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
     * Define lista para auto sugestão
     *
     * @param list com sugestôes de título
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
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_FC);
    }//fim do método getColor

    //método para testes
    public static void main(String[] args) {
        AddDocumentFCFrame addDocumentFCFrame = new AddDocumentFCFrame(0, "teste");

    }
}//fim da classe AddDocumentFCFrame

