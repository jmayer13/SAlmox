
/*-
 * Classname:             AddBookAFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  03/04/2013 - 13:22:13
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
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela para cadastroe edição de livros de almoxarifado
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBookAFrame implements AddBookInterface {

    //frame principal
    private JFrame frame;
    //label com categoria
    private JLabel titleLabel;
    //label código
    private JLabel codeLabel;
    //label observação
    private JLabel observationLabel;
    //label tiuulo do livro
    private JLabel bookLabel;
    //label ano
    private JLabel yearLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para ano
    private JTextField yearField;
    //campo de texto para título do livro
    private JTextField bookField;
    //área de texto para observação
    private JTextArea observationField;
    //scrollPane para o área de texto
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //tótulo da caixa
    private String titleBox;
    //título da caixa
    private String title;
    //livro almoxarifado
    private BookA bookA;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor para cadastro de livro de almoxarifado
     *
     * @param codeSugered código sugerido (ultimo código cadastrado +1)
     * @param titleBox título da caixa
     */
    public AddBookAFrame(int codeSugered, String titleBox) {
        this.titleBox = titleBox;
        title = "Adicionar livro - " + Category.A.getName();
        createView();
        codeField.setText("" + codeSugered);
        bookA = new BookA();
    }//fim do construtor para cadastro

    /**
     * Construtor para edição de livro do almoxarifado
     *
     * @param titleBox título da caixa
     * @param bookA livro de almoxarifado
     */
    public AddBookAFrame(String titleBox, BookA bookA) {
        this.bookA = bookA;
        this.titleBox = titleBox;
        title = "Editar livro - " + Category.A.getName();
        createView();
        fillsView();
    }//fim do construtor para edição

    /**
     * Cria a janela
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame(title);
        titleLabel = new JLabel(titleBox);
        codeLabel = new JLabel("Código: *");
        yearLabel = new JLabel("Ano: *");
        bookLabel = new JLabel("Livro: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        yearField = new JTextField();
        bookField = new JTextField();
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
        titleLabel.setBounds((500 - 150) / 2, 10, 150, 30);
        codeLabel.setBounds(30, 60, 90, 30);
        bookLabel.setBounds(30, 110, 120, 30);
        yearLabel.setBounds(30, 160, 120, 30);
        observationLabel.setBounds(30, 220, 100, 30);
        codeField.setBounds(140, 60, 100, 30);
        bookField.setBounds(140, 110, 250, 30);
        yearField.setBounds(140, 160, 100, 30);
        scrollPane.setBounds(140, 220, 250, 150);
        okButton.setBounds(120, 430, 140, 30);
        cancelButton.setBounds(300, 430, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        yearLabel.setForeground(Color.BLACK);
        bookLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        yearField.setForeground(Color.BLACK);
        bookField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        //fonte
        titleLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        yearLabel.setFont(FontFactory.getFontDefault());
        bookLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        yearField.setFont(FontFactory.getFontDefault());
        bookField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código do livro ");
        yearField.setToolTipText("Insira o ano do livro");
        bookField.setToolTipText("Insira o título do livro");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (bookA == null) {
            okButton.setToolTipText("Cadastra livro");
            cancelButton.setToolTipText("Cancela o cadastro do livro");
        } else {
            okButton.setToolTipText("Salva alterações do livro");
            cancelButton.setToolTipText("Cancela a edição do livro");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);
        ((AbstractDocument) yearField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(titleLabel);
        frame.add(codeLabel);
        frame.add(yearLabel);
        frame.add(bookLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(yearField);
        frame.add(bookField);
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

        //define menu de contexto
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(yearField);
        contextPopupMenu.addInComponet(bookField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        //define focus primario
        bookField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        codeField.setText("" + bookA.getCodeBookA());
        yearField.setText("" + bookA.getYear());
        bookField.setText(bookA.getTitleBook());
        observationField.setText(bookA.getObservationBook());
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
     * Retorna dados dos campos em forma de livro
     *
     * @return <code>BookA</code> livro do almoxarifado
     */
    public BookA getBook() {
        BookA bookA = new BookA();
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (bookField.getText() == null || bookField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o título do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (yearField.getText() == null || yearField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o ano do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            bookA.setCodeBookSpecific(Integer.valueOf(codeField.getText()));
            bookA.setTitleBook(bookField.getText());
            bookA.setYear(Integer.valueOf(yearField.getText()));
            bookA.setObservationBook(observationField.getText());
            return bookA;
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Formato incorreto!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }//fim do método getBook

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
        yearField.addKeyListener(keyListener);
        bookField.addKeyListener(keyListener);
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
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_A);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddBookAFrame addBookAFrame = new AddBookAFrame(0, "test");
    }
}//fim da classe AddBookAFrame

