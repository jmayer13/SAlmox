
/*-
 * Classname:             AddBookFFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  04/04/2013 - 14:13:34
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
import java.util.ArrayList;
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
import uni.uri.salmox.model.BookF;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela para cadastro e edição de livros de Formandos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBookFFrame implements AddBookInterface {

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
    //label sementre
    private JLabel semesterLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para ano
    private JTextField yearField;
    //campo de texto para curso
    private JAutoSuggestComboBox courseField;
    //combo com semestre
    private JComboBox semesterField;
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
    //livro formandos
    private BookF bookF;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor para cadastro de livro de formandos
     *
     * @param codeSugered código sugerido (ultimo código cadastrado +1)
     * @param titleBox título da caixa
     */
    public AddBookFFrame(int codeSugered, String titleBox) {
        this.titleBox = titleBox;
        title = "Adicionar livro - " + Category.F.getName();
        createView();
        codeField.setText("" + codeSugered);
        bookF = new BookF();
    }//fim do construtor para cadastro

    /**
     * Construtor para edição de livro de formandos
     *
     * @param titleBox título da caixa
     * @param bookF livro de formandos
     */
    public AddBookFFrame(String titleBox, BookF bookF) {
        this.bookF = bookF;
        this.titleBox = titleBox;
        title = "Editar livro - " + Category.F.getName();
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
        bookLabel = new JLabel("Curso: *");
        semesterLabel = new JLabel("Semestre: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        yearField = new JTextField();
        semesterField = new JComboBox(new Object[]{"1º Semestre", "2º Semestre", "Indefinido"});
        courseField = new JAutoSuggestComboBox();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define layout
        frame.setLayout(null);
        courseField.setEditable(true);

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
        semesterLabel.setBounds(30, 210, 120, 30);
        observationLabel.setBounds(30, 260, 100, 30);
        codeField.setBounds(140, 60, 100, 30);
        courseField.setBounds(140, 110, 250, 30);
        yearField.setBounds(140, 160, 100, 30);
        semesterField.setBounds(140, 210, 150, 30);
        scrollPane.setBounds(140, 260, 250, 150);
        okButton.setBounds(120, 430, 140, 30);
        cancelButton.setBounds(300, 430, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        yearLabel.setForeground(Color.BLACK);
        semesterLabel.setForeground(Color.BLACK);
        bookLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        yearField.setForeground(Color.BLACK);
        semesterField.setForeground(Color.BLACK);
        courseField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        //fonte
        titleLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        yearLabel.setFont(FontFactory.getFontDefault());
        semesterLabel.setFont(FontFactory.getFontDefault());
        bookLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        yearField.setFont(FontFactory.getFontDefault());
        semesterField.setFont(FontFactory.getFontDefault());
        courseField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código do livro ");
        yearField.setToolTipText("Insira o ano do livro");
        semesterField.setToolTipText("Selecione o semestre do ano");
        courseField.setToolTipText("Insira o curso do livro");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (bookF == null) {
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
        frame.add(semesterLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(yearField);
        frame.add(semesterField);
        frame.add(courseField);
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
        contextPopupMenu.addInComponet(courseField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);


        //define focus primario
        courseField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        codeField.setText("" + bookF.getCodeBookF());
        yearField.setText("" + bookF.getYear());
        if (bookF.getSemester() == 0) {
            semesterField.setSelectedIndex(2);
        } else if (bookF.getSemester() == 1) {
            semesterField.setSelectedIndex(0);
        } else {
            semesterField.setSelectedIndex(1);
        }
        courseField.setText(bookF.getTitleBook());
        observationField.setText(bookF.getObservationBook());
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
     * @return <code>BookF</code> livro de atas eexames
     */
    public BookF getBook() {
        BookF bookF = new BookF();
        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (courseField.getText() == null || courseField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o curso do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (yearField.getText() == null || yearField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o ano do livro!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            bookF.setCodeBookF(Integer.valueOf(codeField.getText()));
            bookF.setTitleBook(courseField.getText());
            bookF.setYear(Integer.valueOf(yearField.getText()));
            if (semesterField.getSelectedIndex() == 0) {
                bookF.setSemester(1);
            } else if (semesterField.getSelectedIndex() == 1) {
                bookF.setSemester(2);
            } else {
                bookF.setSemester(0);
            }
            bookF.setObservationBook(observationField.getText());
            return bookF;
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
     * Informa lista de cursos para o auto-completar
     *
     * @param list lista com tipos de caixa de almoxarifado
     */
    public void setCourseList(List<String> list) {
        courseField.setSuggestList(list);
    }//fim do método setCourseList

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        codeField.addKeyListener(keyListener);
        yearField.addKeyListener(keyListener);
        courseField.addKeyListener(keyListener);
        semesterField.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_F);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddBookFFrame addBookFFrame = new AddBookFFrame(0, "test");
        List<String> list = new ArrayList();
        list.add("teste");
        addBookFFrame.setCourseList(list);
    }
}//fim da classe AddBookFFrame

