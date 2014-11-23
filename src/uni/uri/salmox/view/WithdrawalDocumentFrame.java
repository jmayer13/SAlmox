
/*-
 * Classname:             WithdrawalDocumentFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  26/03/2013 - 13:56:12
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame com documentos para retirar
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalDocumentFrame {

    //modelo para a tabela
    private WithdrawalDocumentTableModel withdrawalDocumentTableModel;
    //janela principal
    private JFrame frame;
    //scrollpanel para a tabela
    private JScrollPane scrollPane;
    //tabela
    private JTable table;
    //botão busca
    private JButton searchButton;
    //botão remover
    private JButton eraseButton;
    //botão gerar relatório
    private JButton generateReportButton;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor sem parâmetros
     */
    public WithdrawalDocumentFrame() {
        withdrawalDocumentTableModel = new WithdrawalDocumentTableModel();
        createView();
    }//fim do construtor sem argumentos

    /**
     * Cria a visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Retirar documentos");
        scrollPane = new JScrollPane();
        table = new JTable(withdrawalDocumentTableModel);
        searchButton = new JButton("Buscar");
        eraseButton = new JButton("Remover");
        generateReportButton = new JButton("Gerar Relatório");
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancelar");

        //pega tamanho da tela e define layout 
        frame.setLayout(null);
        takeScreenSize();

        //define tamanho e posição dos componentes
        frame.setBounds((x - 1000) / 2, (y - 600) / 2, 1000, 600);
        scrollPane.setBounds(50, 50, frame.getWidth() - 350, frame.getHeight() - 150);
        table.setBounds(0, 0, frame.getWidth(), frame.getHeight() - 300);
        searchButton.setBounds(frame.getWidth() - 250, 100, 200, 30);
        eraseButton.setBounds(frame.getWidth() - 250, 150, 200, 30);
        generateReportButton.setBounds(frame.getWidth() - 250, 200, 200, 30);
        okButton.setBounds(frame.getWidth() - 400, frame.getHeight() - 70, 150, 30);
        cancelButton.setBounds(frame.getWidth() - 190, frame.getHeight() - 70, 150, 30);

        //define tamanho das colunas da tabela
     /*   TableColumn column;
         for (int i = 0; i < table.getColumnCount(); i++) {
         column = table.getColumnModel().getColumn(i);
         if (i == 0) {
         int sizeColumnCode = (x - 500) / 5;
         column.setPreferredWidth(sizeColumnCode);
         column.setMaxWidth(sizeColumnCode);
         }
         if (i == 1) {
         int sizeColumnCode = (x - 500) / 5;
         column.setPreferredWidth(sizeColumnCode);
         column.setMaxWidth(sizeColumnCode);
         }
         if (i == 2) {
         int sizeColumnCode = (x - 500) / 5;
         column.setPreferredWidth(sizeColumnCode);
         column.setMaxWidth(sizeColumnCode);
         }
         }*/

        //define cor e fonte
        frame.getContentPane().setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        searchButton.setFont(FontFactory.getFontLarge());
        eraseButton.setFont(FontFactory.getFontLarge());
        generateReportButton.setFont(FontFactory.getFontLarge());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        searchButton.setToolTipText("Abre janela de busca para buscar mais documentos");
        eraseButton.setToolTipText("Remove documento da lista");
        generateReportButton.setToolTipText("Finaliza a retirada e gera o relatório");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        frame.add(scrollPane);
        frame.add(searchButton);
        frame.add(eraseButton);
        //frame.add(generateReportButton);
        frame.add(okButton);
        frame.add(cancelButton);

        //define panel como visivel
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }//fim do método createViewAdministrator

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
     * Atualiza tabela
     *
     * @param titleBoxes lista com títulos das caixas
     * @param documents lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        withdrawalDocumentTableModel.refresh(boxes, books, documents);
        table.repaint();
    }//fim do método refresh

    /**
     * Define evento para o botão buscar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerSearchButton(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }//fim do método setactionListenerSearchButton

    /**
     * Define evento para o botão remover
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerEraseButton(ActionListener actionListener) {
        eraseButton.addActionListener(actionListener);
    }//fim do método setactionListenerEraseButton

    /**
     * Define evento para o botão gerar relatório
     *
     * @param actionListener
     */
    public void setActionListenerGenerateReportButton(ActionListener actionListener) {
        generateReportButton.addActionListener(actionListener);
    }//fim do método setactionListenerGenerateReportButton

    /**
     * Adiciona documento
     *
     * @param box caixa
     * @param book livro
     * @param document documento
     */
    public void addDocument(Box box, Book book, Document document) {
        if (document.getPresent()) {
            withdrawalDocumentTableModel.addRow(box, book, document);
        } else {
            JOptionPane.showMessageDialog(null, "O documento " + document.getTitleDocument() + " não está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método addDocument

    /**
     * Obtêm lista de documentos marcados
     *
     * @return <code>Document</code> documento
     */
    public Document getSelectedDocument() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return withdrawalDocumentTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
    }//fim do método getSelectedDocument

    /**
     * Apaga docuento
     *
     * @param document documento
     */
    public void eraseDocument(Document document) {
        withdrawalDocumentTableModel.eraseRow(document);
    }//fim do método eraseDocument

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
        frame.requestFocus();
    }//fim da classe requestFrameFocus

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_WITHDRAWAL);
    }//fim do método getColor

    //metodo para testes
    public static void main(String args[]) {
        WithdrawalDocumentFrame wd = new WithdrawalDocumentFrame();
    }

    /*
     * Define evento para o botão cancelar
     */
    public void setActionListenerCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenerCancelButton

    public void setEnable(boolean b) {
        frame.setEnabled(b);
    }

    /**
     * Define método
     *
     * @param actionListener
     */
    public void setActionListenerOKButton(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }//fim do método setActionListenerOKButton

    public List<Document> getDocuments() {
        List<Document> documents = new ArrayList();
        for (int i = 0; i < withdrawalDocumentTableModel.getRowCount(); i++) {
            documents.add(withdrawalDocumentTableModel.getRow(i));
        }
        return documents;
    }//fim do método getDocuments

    /**
     * Define eventos de teclado
     *
     * @param keyAdapter puvinte do evento
     */
    public void setKeyListenerFrame(KeyAdapter keyAdapter) {
        frame.setFocusable(true);
        frame.addKeyListener(keyAdapter);
        table.addKeyListener(keyAdapter);
        searchButton.addKeyListener(keyAdapter);
        eraseButton.addKeyListener(keyAdapter);
        generateReportButton.addKeyListener(keyAdapter);
        okButton.addKeyListener(keyAdapter);
        cancelButton.addKeyListener(keyAdapter);
    }//fim do método setKeyListenerFrame
}//fim da classe WithdrawalDocumentFrame

