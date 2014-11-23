
/*-
 * Classname:             AddBoxSAEFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  25/03/2013
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
import uni.uri.salmox.model.BoxSAE;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para cadastro e edição de caixas de Filantropia
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBoxSAEFrame implements AddBoxInterface {

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
    //label ano
    private JLabel yearLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para ano
    private JTextField yearField;
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
    private BoxSAE boxSAE;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor para cadastro de caixa de filantropia
     *
     * @param codeSugered código sugerido (ultimo código cadastrado +1)
     */
    public AddBoxSAEFrame(int codeSugered) {
        this.boxSAE = boxSAE;
        title = "Adicionar Caixa " + Category.SAE.getName();
        createView();
        codeField.setText(codeSugered + "");
        boxSAE = new BoxSAE();
    }//fim do construtor para cadastro

    /**
     * Construtor para edição de caixa de almoxarifado
     *
     * @param boxSAE caixa de filantropia
     */
    public AddBoxSAEFrame(BoxSAE boxSAE) {
        this.boxSAE = boxSAE;
        this.category = "SAE";
        title = "Editar Caixa " + Category.SAE.getName();
        createView();
        fillsView();
    }//fim do construtor para edição

    /**
     * Cria a janela
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame(title);
        titleLabel = new JLabel(category + "-");
        codeLabel = new JLabel("Código: *");
        yearLabel = new JLabel("Ano: *");
        responsibleLabel = new JLabel("Responsáveis: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        yearField = new JTextField();
        responsibleField = new JTextField();
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
        titleLabel.setBounds(130, 30, 50, 30);
        codeLabel.setBounds(30, 30, 90, 30);
        yearLabel.setBounds(30, 80, 120, 30);
        responsibleLabel.setBounds(30, 130, 120, 30);
        observationLabel.setBounds(30, 180, 100, 30);
        codeField.setBounds(182, 30, 100, 30);
        yearField.setBounds(150, 80, 250, 30);
        responsibleField.setBounds(150, 130, 250, 30);
        scrollPane.setBounds(150, 180, 250, 150);
        okButton.setBounds(120, 390, 140, 30);
        cancelButton.setBounds(300, 390, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        yearLabel.setForeground(Color.BLACK);
        responsibleLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        yearField.setForeground(Color.BLACK);
        responsibleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        titleLabel.setHorizontalAlignment(JLabel.RIGHT);

        //fonte
        titleLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        yearLabel.setFont(FontFactory.getFontDefault());
        responsibleLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        yearField.setFont(FontFactory.getFontDefault());
        responsibleField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código da caixa (OBRIGATÓRIO)");
        yearField.setToolTipText("Insira o ano da caixa");
        responsibleField.setToolTipText("Insira o nome dos responsáveis pela caixa (OBRIGATÓRIO)");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (boxSAE == null) {
            okButton.setToolTipText("Cadastra caixa");
            cancelButton.setToolTipText("Cancela o cadastro da caixa");
        } else {
            okButton.setToolTipText("Salva alterações da caixa");
            cancelButton.setToolTipText("Cancela a edição da caixa");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);
        ((AbstractDocument) yearField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(titleLabel);
        frame.add(codeLabel);
        frame.add(yearLabel);
        frame.add(responsibleLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(yearField);
        frame.add(responsibleField);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);


        //define evento
        //limita campo de ano para ter no máximo 4 caracteres
        yearField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                JTextField tf = (JTextField) keyEvent.getComponent();
                if (tf.getText().length() >= 4) {
                    keyEvent.setKeyChar((char) KeyEvent.VK_CLEAR);
                }
            }
        });

        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(yearField);
        contextPopupMenu.addInComponet(responsibleField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        //define focus primario
        yearField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        codeField.setText("" + boxSAE.getCodeBoxSAE());
        yearField.setText("" + boxSAE.getYear());
        responsibleField.setText(boxSAE.getResponsibleBox());
        observationField.setText(boxSAE.getObservationBox());
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
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        codeField.addKeyListener(keyListener);
        yearField.addKeyListener(keyListener);
        responsibleField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

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
    public BoxSAE getBox() {
        BoxSAE boxSAE = new BoxSAE();
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (yearField.getText() == null || yearField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o ano da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (responsibleField.getText() == null || responsibleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o responsável da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            boxSAE.setCodeBoxSAE(Integer.valueOf(codeField.getText()));
            boxSAE.setTitleBox(category + "-" + codeField.getText());
            boxSAE.setYear(Integer.valueOf(yearField.getText()));
            boxSAE.setResponsibleBox(responsibleField.getText());
            boxSAE.setObservationBox(observationField.getText());
            return boxSAE;
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
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_SAE);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddBoxSAEFrame addBoxAFrame = new AddBoxSAEFrame(0);
    }
}//fim da classe AddBoxSAEFrame

