/*-
 * Classname:             SearchResultFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 14:53:17
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentA;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;
import uni.uri.salmox.util.TemporaryData;

/**
 * Frame com resultados da busca
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SearchResultFrame {

    //frame principal
    private JFrame frame;
    //tabela com resultados
    private JTable table;
    //modelo da tabela com resultados
    private TableModel tableModel;
    //crollPane para a tabela com resultado
    private JScrollPane scrollPane;
    //botão solicitar
    private JButton requestButton;
    //botão retirar
    private JButton withdrawButton;
    //botão editar
    private JButton editButton;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor com resultados da busca
     *
     * @param tableModel modelo da tabela
     */
    public SearchResultFrame(TableModel tableModel) {
        this.tableModel = tableModel;
        if (TemporaryData.getUser() instanceof Administrator) {
            createViewAdministrator();
        } else {
            createViewDefaultUser();
        }
    }//fim do construtor com resultados da busca

    /**
     * Cria visão
     */
    private void createViewAdministrator() {
        //inicializa componentes
        frame = new JFrame("Resultado da Busca");
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        withdrawButton = new JButton("Retirar");
        editButton = new JButton("Editar");

        //pega tamanho da tela e define layout 
        frame.setLayout(null);
        takeScreenSize();

        //define tamanho e posição dos componentes
        frame.setBounds(0, 0, x, y);
        scrollPane.setBounds(50, 50, x - 450, y - 150);
        table.setBounds(0, 0, x - 400, y);
        withdrawButton.setBounds(x - 300, 100, 200, 30);
        editButton.setBounds(x - 300, 150, 200, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = 20;
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
        }

        //define cor e fonte
        frame.getContentPane().setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        withdrawButton.setFont(FontFactory.getFontLarge());
        editButton.setFont(FontFactory.getFontLarge());

        //dicas
        withdrawButton.setToolTipText("Abre janela para retirada dos documentos selecionados");
        editButton.setToolTipText("Edita documento/livro/caixa selecionado");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        frame.add(scrollPane);
        frame.add(withdrawButton);
        frame.add(editButton);

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
     * Cria visão
     */
    private void createViewDefaultUser() {
        //inicializa componentes
        frame = new JFrame("Resultado da Busca");
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        requestButton = new JButton("Solicitar");

        //pega tamanho da tela e define layout 
        frame.setLayout(null);
        takeScreenSize();

        //define tamanho e posição dos componentes
        frame.setBounds(0, 0, x, y);
        scrollPane.setBounds(50, 50, x - 450, y - 150);
        table.setBounds(0, 0, x - 400, y);
        requestButton.setBounds(x - 300, 100, 200, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = 20;
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
        }

        //define cor e fonte
        frame.getContentPane().setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        requestButton.setFont(FontFactory.getFontLarge());

        //dicas
        requestButton.setToolTipText("Abre janela para solicitação dos documentos selecionados");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        frame.add(scrollPane);
        frame.add(requestButton);

        //define panel como visivel
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }//fim do método createViewDeffaultUser

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
        if (enable) {
            frame.requestFocus();
        }
    }//fim do método enableFrame

    /**
     * Define evento para a retirada
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerWithdrawButton(ActionListener actionListener) {
        withdrawButton.addActionListener(actionListener);
    }//fim do método setActionListenerWithdrawButton

    /**
     * Define Evento para o botão editar
     *
     * @param actionListener ouvunte
     */
    public void setActionListenerEditButton(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }//fim do método setActionListenerEditButton

    /**
     * Define evento para a solicitação
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerRequestButton(ActionListener actionListener) {
        requestButton.addActionListener(actionListener);
    }//fim do método setActionListenerRequestButton

    /**
     * Obtêm caixa selecionada se for o caso
     *
     * @return <code>Box</code> caixa
     */
    public Box getBoxSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchBoxTableModel resultSearchBoxTableModel = (ResultSearchBoxTableModel) tableModel;
            if (table.getSelectedRow() < 0) {
                return null;
            }
            return resultSearchBoxTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
        } else {
            return null;
        }
    }//fim do método getBoxSelected

    /**
     * Obtêm livro selecionado se for o caso
     *
     * @return <code>Book</code> livro
     */
    public Book getBookSelected() {
        if (tableModel instanceof ResultSearchBookTableModel) {
            ResultSearchBookTableModel resultSearchBookTableModel = (ResultSearchBookTableModel) tableModel;
            if (table.getSelectedRow() < 0) {
                return null;
            }
            return resultSearchBookTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
        } else {
            return null;
        }
    }//fim do método getBookSelected

    /**
     * Obtêm documento selecionade se for o caso
     *
     * @return <code>Document</code>
     */
    public Document getDocumentSelected() {
        if (tableModel instanceof ResultSearchDocumentTableModel) {
            ResultSearchDocumentTableModel resultSearchDocumentTableModel = (ResultSearchDocumentTableModel) tableModel;
            if (table.getSelectedRow() < 0) {
                return null;
            }
            return resultSearchDocumentTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
        } else {
            return null;
        }
    }//fim do método getDocumentSelected

    /**
     * Obtêm lista de caixas com a caixa de combinação marcada
     *
     * @return <code>List</code> caixas marcadas
     */
    public List<Box> getBoxesSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchBoxTableModel resultSearchBoxTableModel = (ResultSearchBoxTableModel) tableModel;
            return resultSearchBoxTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getBoxesSelected

    /**
     * Obtêm lista de livros com a caixa de combinação marcada
     *
     * @return <code>List</code> livros marcados
     */
    public List<Book> getBooksSelected() {
        if (tableModel instanceof ResultSearchBookTableModel) {
            ResultSearchBookTableModel resultSearchBookTableModel = (ResultSearchBookTableModel) tableModel;
            return resultSearchBookTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getBooksSelected

    /**
     * Obtêm lista de documentos com a caixa de combinação marcada
     *
     * @return <code>List</code> documentos marcados
     */
    public List<Document> getDocumentsSelected() {
        if (tableModel instanceof ResultSearchDocumentTableModel) {
            ResultSearchDocumentTableModel resultSearchDocumentTableModel = (ResultSearchDocumentTableModel) tableModel;
            return resultSearchDocumentTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getDocumentsSelected

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener
    //metodo para teste

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_SEARCH);
    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        List<Box> boxes = new ArrayList();
        List<Book> books = new ArrayList();;
        List<Document> documents = new ArrayList();
        boxes.add(new BoxA(0, 0, "A-0", "none", ""));
        books.add(new BookA(0, "B A", 0, "", 2000, "typeA"));
        documents.add(new DocumentA(0, "D A", 0, "", true));
        boxes.add(new BoxA(1, 1, "A-1", "none", ""));
        books.add(new BookA(1, "B A", 1, "", 2000, "typeA"));
        documents.add(new DocumentA(1, "D A", 1, "", true));
        ResultSearchDocumentTableModel resultSearchDocumentTableModel = new ResultSearchDocumentTableModel(boxes, books, documents);
        new SearchResultFrame(resultSearchDocumentTableModel);
    }

    /**
     * Altera opções para seleção de caixas para exclusão
     *
     */
    public void changeToDelete() {
        this.editButton.setVisible(false);
        ActionListener[] ac = withdrawButton.getActionListeners();
        for (int i = 0; i < ac.length; i++) {
            this.withdrawButton.removeActionListener(ac[i]);
        }
        withdrawButton.setText("Selecionar");
    }//fim do método changeToDelete

    /**
     * Substitui caixa
     *
     * @param box caixa
     */
    public void replaceBox(Box box) {
        ResultSearchBoxTableModel resultSearchBoxTableModel = (ResultSearchBoxTableModel) tableModel;
        resultSearchBoxTableModel.replaceBox(box);
    }//fim do método replaceBox

    /**
     * Substitui livro
     *
     * @param book livro
     */
    public void replaceBook(Book book) {
        ResultSearchBookTableModel resultSearchBookTableModel = (ResultSearchBookTableModel) tableModel;
        resultSearchBookTableModel.replaceBook(book);
    }//fim do método replaceBook

    /**
     * Substitui documento
     *
     * @param document documento
     */
    public void replaceDocument(Document document) {
        ResultSearchDocumentTableModel resultSearchDocumentTableModel = (ResultSearchDocumentTableModel) tableModel;
        resultSearchDocumentTableModel.replaceDocument(document);
    }//fim do método replaceDocument
}//fim da classe SearchResultFrame

