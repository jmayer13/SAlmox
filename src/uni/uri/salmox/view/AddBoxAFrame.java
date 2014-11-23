
/*-
 * Classname:             AddBoxAFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  22/03/2013 - 13:15:44
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
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
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para cadastro e edição de caixas do Almoxarifado
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBoxAFrame implements AddBoxInterface {

    //frame principal
    private JFrame frame;
    //label com categoria
    private JLabel titleLabel;
    //label código
    private JLabel codeLabel;
    //label observação
    private JLabel observationLabel;
    //label responsáveis
    private JLabel responsibleLabel;
    //label tipo
    private JLabel typeBoxALabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para tipo
    private JAutoSuggestComboBox typeBoxAField;
    //campo de texto para responsáveis
    private JTextField responsibleField;
    //área de texto para observação
    private JTextArea observationField;
    //scrollPane para o área de texto
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //categoria da caixa
    private String category;
    //título da caixa
    private String title;
    //caixa
    private BoxA boxA;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor para cadastro de caixa do almoxarifado
     *
     * @param codeSugered código sugerido (ultimo código cadastrado +1)
     */
    public AddBoxAFrame(int codeSugered, List<String> type) {
        this.category = "A";
        title = "Adicionar Caixa " + Category.A.getName();
        createView();
        codeField.setText(codeSugered + "");
        boxA = new BoxA();
        setTypeBoxList(type);
    }//fim do construtor para cadastro

    /**
     * Construtor para edição de caixa do almoxarifado
     *
     * @param box caixa
     */
    public AddBoxAFrame(BoxA boxA, List<String> type) {
        this.boxA = boxA;
        this.category = "A";
        title = "Editar Caixa " + Category.A.getName();
        createView();
        fillsView();
        setTypeBoxList(type);
    }//fim do construtor para edição

    /**
     * Cria a janela
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame(title);
        titleLabel = new JLabel(category + "-");
        codeLabel = new JLabel("Código: *");
        typeBoxALabel = new JLabel("Tipo: *");
        responsibleLabel = new JLabel("Responsáveis: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        typeBoxAField = new JAutoSuggestComboBox();
        responsibleField = new JTextField();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define layout
        frame.setLayout(null);
        typeBoxAField.setEditable(true);

        //configura textarea
        //quebra de linha
        observationField.setLineWrap(true);
        //impede separação de palavras
        observationField.setWrapStyleWord(true);

        //pega tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds((x - 500) / 2, (y - 480) / 2, 500, 480);
        titleLabel.setBounds(130, 30, 50, 30);
        codeLabel.setBounds(30, 30, 90, 30);
        typeBoxALabel.setBounds(30, 80, 120, 30);
        responsibleLabel.setBounds(30, 130, 120, 30);
        observationLabel.setBounds(30, 180, 100, 30);
        codeField.setBounds(182, 30, 100, 30);
        typeBoxAField.setBounds(150, 80, 250, 30);
        responsibleField.setBounds(150, 130, 250, 30);
        scrollPane.setBounds(150, 180, 250, 150);
        okButton.setBounds(120, 390, 140, 30);
        cancelButton.setBounds(300, 390, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        typeBoxALabel.setForeground(Color.BLACK);
        responsibleLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        typeBoxAField.setForeground(Color.BLACK);
        //  typeBoxAField.setBackground(Color.LIGHT_GRAY);
        responsibleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        titleLabel.setHorizontalAlignment(JLabel.RIGHT);

        //fonte
        titleLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        typeBoxALabel.setFont(FontFactory.getFontDefault());
        responsibleLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        typeBoxAField.setFont(FontFactory.getFontDefault());
        responsibleField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código da caixa (OBRIGATÓRIO)");
        typeBoxAField.setToolTipText("Tipo da caixa EX: Relatórios Contábeis");
        responsibleField.setToolTipText("Insira o nome dos responsáveis pela caixa (OBRIGATÓRIO)");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (boxA == null) {
            okButton.setToolTipText("Cadastra caixa");
            cancelButton.setToolTipText("Cancela o cadastro da caixa");
        } else {
            okButton.setToolTipText("Salva alterações da caixa");
            cancelButton.setToolTipText("Cancela a edição da caixa");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(titleLabel);
        frame.add(codeLabel);
        frame.add(typeBoxALabel);
        frame.add(responsibleLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(typeBoxAField);
        frame.add(responsibleField);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);

        //define menu de contexto
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(typeBoxAField);
        contextPopupMenu.addInComponet(responsibleField);
        contextPopupMenu.addInComponet(observationField);


        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        //define focus primario
        typeBoxAField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        codeField.setText("" + boxA.getCodeBoxA());
        typeBoxAField.setText(boxA.getTypeBox());
        responsibleField.setText(boxA.getResponsibleBox());
        observationField.setText(boxA.getObservationBox());
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
     * Retorna dados dos campos em forma de caixa
     *
     * @return code>Box</code> caixa cadastrada/editada
     */
    public BoxA getBox() {
        BoxA boxA = new BoxA();
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (typeBoxAField.getText() == null || typeBoxAField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o tipo da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (responsibleField.getText() == null || responsibleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o responsável da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            boxA.setCodeBoxA(Integer.valueOf(codeField.getText()));
            boxA.setTitleBox(category + "-" + codeField.getText());
            boxA.setTypeBox(typeBoxAField.getText());
            boxA.setResponsibleBox(responsibleField.getText());
            boxA.setObservationBox(observationField.getText());
            return boxA;
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Formato incorreto!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }//fim do método getBox

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
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        titleLabel.addKeyListener(keyListener);
        codeLabel.addKeyListener(keyListener);
        observationLabel.addKeyListener(keyListener);
        responsibleLabel.addKeyListener(keyListener);
        typeBoxALabel.addKeyListener(keyListener);
        codeField.addKeyListener(keyListener);
        typeBoxAField.addKeyListener(keyListener);
        responsibleField.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Informa lista de tipos de caixa para o auto-completar
     *
     * @param list lista com tipos de caixa de almoxarifado
     */
    public void setTypeBoxList(List<String> list) {
        typeBoxAField.setSuggestList(list);
    }//fim do método setTypeBoxList

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_A);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddBoxAFrame addBoxAFrame = new AddBoxAFrame(0, new ArrayList());
        List<String> list = new ArrayList();
        list.add("teste");
        list.add("ok");
        list.add("testado");
        addBoxAFrame.setTypeBoxList(list);
    }
}//fim da classe AddBoxAFrame

