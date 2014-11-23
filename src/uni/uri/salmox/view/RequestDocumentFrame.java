/*-
 * Classname:             RequestDocumentFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  25/04/2013 - 13:14:49
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para o usuário padrão solicitar documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestDocumentFrame {

    //frame principal
    private JFrame frame;
    //scrollpane para tabela
    private JScrollPane scrollPane;
    //tabela 
    private JTable table;
    //modelo da tabela
    private RequestDocumentTableModel tableModel;
    //label do frame
    private JLabel frameLabel;
    //label motivo
    private JLabel motiveLabel;
    //label observação
    private JLabel observationLabel;
    //campo de texto motivo
    private JTextArea motiveField;
    //scrollpane para textarea motiveField
    private JScrollPane motivePane;
    //campo de texto observação
    private JTextArea observationField;
    //scrollpane para textarea observationField
    private JScrollPane observationPane;
    //botão buscar
    private JButton searchButton;
    //botão remover
    private JButton removeButton;
    //botão solicitar
    private JButton requestButton;
    //botão cancelar 
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor sem parãmetros
     */
    public RequestDocumentFrame() {
        tableModel = new RequestDocumentTableModel();
        createView();
    }//fim do construtor sem oparametros

    /**
     * Construtor com listas de documentos
     *
     * @param boxes lista de caixa
     * @param books lista de livros
     * @param documents lista de documentos
     */
    public RequestDocumentFrame(List<Box> boxes, List<Book> books, List<Document> documents) {//atualizar classe view e checar DAO 
        tableModel = new RequestDocumentTableModel(boxes, books, documents);
        createView();

    }//fim do construtor com listas

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Solicitar documentos");
        frameLabel = new JLabel("Solicitar Documentos");
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        motiveLabel = new JLabel("Motivo:");
        observationLabel = new JLabel("Observação:");
        motiveField = new JTextArea();
        motivePane = new JScrollPane();
        observationField = new JTextArea();
        observationPane = new JScrollPane();
        searchButton = new JButton("Buscar");
        removeButton = new JButton("Remover");
        requestButton = new JButton("Solicitar");
        cancelButton = new JButton("Cancelar");

        //define layout
        frame.setLayout(null);
        frameLabel.setHorizontalAlignment(JLabel.CENTER);

        //quebra de linha JTextArea
        motiveField.setLineWrap(true);
        observationField.setLineWrap(true);
        motiveField.setWrapStyleWord(true);
        observationField.setWrapStyleWord(true);

        //obtêm o tamanho da tela
        takeScreenSize();

        //define tamanho e posição
        frame.setBounds((x - 900) / 2, 0, 900, 700);
        frameLabel.setBounds((900 - 200) / 2, 10, 200, 30);
        scrollPane.setBounds(40, 50, 600, 400);
        //table .setBounds(0 ,0 ,500 ,450 );
        motiveLabel.setBounds(150, 470, 100, 30);
        observationLabel.setBounds(150, 540, 100, 30);
        motivePane.setBounds(250, 470, 230, 60);
        observationPane.setBounds(250, 540, 230, 60);
        searchButton.setBounds(680, 60, 150, 30);
        removeButton.setBounds(680, 110, 150, 30);
        requestButton.setBounds(540, 620, 140, 30);
        cancelButton.setBounds(700, 620, 140, 30);

        //configurações gráficas
        //cor
        frame.getContentPane().setBackground(getColor());
        frameLabel.setBackground(Color.BLACK);
        scrollPane.setBackground(getColor());
        table.setForeground(Color.BLACK);
        motiveLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        motiveField.setForeground(Color.BLACK);
        motiveField.setBackground(Color.WHITE);
        observationField.setBackground(Color.WHITE);
        observationField.setForeground(Color.BLACK);
        searchButton.setForeground(Color.BLACK);
        removeButton.setForeground(Color.BLACK);
        requestButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //fonte
        frameLabel.setFont(FontFactory.getFontLarge());
        table.setFont(FontFactory.getFontDefault());
        motiveLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        searchButton.setFont(FontFactory.getFontLarge());
        removeButton.setFont(FontFactory.getFontLarge());
        requestButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        motiveField.setToolTipText("Insira o motivo da solicitação");
        observationField.setToolTipText("Insira uma obervação sobre a solicitação");
        searchButton.setToolTipText("Buscar mais documentos");
        removeButton.setToolTipText("Remover documento da lista");
        requestButton.setToolTipText("Faz solicitação dos documentos listados");
        cancelButton.setToolTipText("Cancela a solicitação de documentos");

        //junção de componentes
        scrollPane.setViewportView(table);
        motivePane.setViewportView(motiveField);
        observationPane.setViewportView(observationField);
        frame.add(frameLabel);
        frame.add(scrollPane);
        frame.add(motiveLabel);
        frame.add(observationLabel);
        frame.add(motivePane);
        frame.add(searchButton);
        frame.add(removeButton);
        frame.add(requestButton);
        frame.add(observationPane);
        frame.add(cancelButton);

        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(motiveField);
        contextPopupMenu.addInComponet(observationField);

        table.setAutoCreateRowSorter(true);

        //confugurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
     * Atualiza tabela
     *
     * @param codeBoxes lista com códigos das caixas
     * @param documents lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        tableModel.refresh(boxes, books, documents);
        //table.repaint();
    }//fim do método refresh

    public void addDocument(Box box, Book book, Document document) {
        tableModel.addRow(box, book, document);
        //table.repaint();

    }//fim do método refresh

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        table.addKeyListener(keyListener);
        motiveField.addKeyListener(keyListener);
        motivePane.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        observationPane.addKeyListener(keyListener);
        searchButton.addKeyListener(keyListener);
        removeButton.addKeyListener(keyListener);
        requestButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Remove documento pelo código
     *
     * @param indexRow indicê dodocumento
     */
    public void removeDocument(int indexRow) {
        tableModel.eraseRow(indexRow);
    }//fim do método removeDocument

    /**
     * Obtêm a linha selecionada
     *
     * @return <code>Integer</code> linha selecionada
     */
    public int getSelectedRow() {
        if (table.getSelectedRow() == -1) {
            return 0;
        }
        return table.convertRowIndexToModel(table.getSelectedRow());
    }//fimdo método getSelectedRow

    /**
     * Obtêm solicitação
     *
     * @return <code>Solicitação</code>
     */
    public Request getRequest() {
        Request request = new Request();
        request.setDateRequest(new Date(System.currentTimeMillis()));
        List<Document> documents = tableModel.getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        request.setListDocumentRequest(documents);
        request.setMotiveRequest(motiveField.getText());
        request.setObservationRequest(observationField.getText());

        return request;
    }//fim do método getRequest

    /**
     * Define se a janela está ativa
     *
     * @param enable true-ativada false-desativada
     */
    public void enableFrame(boolean enable) {
        frame.setEnabled(enable);
    }//fim do método enableFrame

    /**
     * Fecha a janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Define ouvinte para o botão remover
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerRemoveButton(ActionListener actionLiatener) {
        removeButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerRemoveButton

    /**
     * Define ouvinte para o botão remover
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerRequestButton(ActionListener actionLiatener) {
        requestButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerRemoveButton

    /**
     * Define ouvinte para o botão buscar
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerSearchButton(ActionListener actionLiatener) {
        searchButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerSearchButton

    /**
     * Define ouvinte para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenetCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenetCancelButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_REQUEST);
    }//fim do método getColor

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Requisita o foco
     */
    public void requestFrameFocus() {
        frame.requestFocusInWindow();
    }//fim da classe requestFrameFocus

    //método para testes
    public static void main(String args[]) {
        RequestDocumentFrame requestDocumentFPanel = new RequestDocumentFrame();
    }
}//fim da classe RequestDocumentFrame 

