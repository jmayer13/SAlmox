
/*-
 * Classname:             WithdrawalDocumentTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  26/03/2013 - 13:56:52
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;

/**
 * Modelo de tabela de retiradas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalDocumentTableModel extends AbstractTableModel {

    //lista com documentos
    private List<Document> documents;
    //lista com caixas
    private List<Box> boxes;
    //lista com livros
    private List<Book> books;
    //número de colunas
    private int COLUMN_NUMBER = 5;
    //coluna de caixa
    private int COLUMN_BOX = 0;
    //coluna código livro
    private int COLUMN_CODE_BOOK = 1;
    //coluna de livro
    private int COLUMN_BOOK = 2;
    //coluna de código do documento
    private int COLUMN_CODE_DOCUMENT = 3;
    //coluna título do documento
    private int COLUMN_TITLE_DOCUMENT = 4;
    private GenericBoxDAO genericBoxDAO;

    /**
     * Construtor sem parametros
     */
    public WithdrawalDocumentTableModel() {
        documents = new ArrayList();
        boxes = new ArrayList();
        books = new ArrayList();
        fireTableDataChanged();
        genericBoxDAO = new GenericBoxDAO();
    }//fim do construtor sem parametros

    /**
     * Construtor com listas
     *
     * @param boxes lista de caixas
     * @param books lista de livros
     * @param documents lista de documentos
     */
    public WithdrawalDocumentTableModel(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.documents = documents;
        this.boxes = boxes;
        this.books = books;
        order();
        fireTableDataChanged();
        genericBoxDAO = new GenericBoxDAO();
    }//fim do construtor com argumentos

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return documents.size();
    }//fim do método getRowCount

    /**
     * Retorna número de colunas
     *
     * @return <code>Integer</code> numero de colunas
     */
    public int getColumnCount() {
        return COLUMN_NUMBER;
    }//fim do método getColumnCount

    /**
     * Retorna o nome da coluna
     *
     * @param columnIndex índice da coluna
     * @return <code>String</code> com nome da coluna
     */
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == COLUMN_BOX) {
            return "Caixa";
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return "Código Livro";
        } else if (columnIndex == COLUMN_BOOK) {
            return "Livro";
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return "Código Documento";
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return "Documento";
        } else {
            return "";
        }
    }//fim do método getColumnName

    /**
     * Retorna classe da coluna
     *
     * @param columnIndex índice da coluna
     * @return <code>Class</code> da coluna
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex == COLUMN_BOX) {
            return String.class;
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return String.class;
        } else if (columnIndex == COLUMN_BOOK) {
            return String.class;
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return String.class;
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return String.class;
        } else {
            return null;
        }
    }//fim do método getColumnClass

    /**
     * Retorna valor de célula da tabela
     *
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @return <code>Object</code> valor da céluna
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Document document = documents.get(rowIndex);
        if (columnIndex == COLUMN_BOX) {
            return boxes.get(rowIndex).getTitleBox();
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return books.get(rowIndex).getCodeBookSpecific();
        } else if (columnIndex == COLUMN_BOOK) {
            return books.get(rowIndex).getTitleBook();
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return document.getCodeDocumentSpecific();
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return document.getTitleDocument();
        } else {
            return "";
        }
    }//fim do método getValueAt

    /**
     * Retorna valor indicando se a célula é editavel
     *
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @return <code>Boolean</code> editável
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }//fim do método isCellEditable

    /**
     * Retorna documento da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Document</code> documento da linha
     */
    public Document getRow(int rowIndex) {
        return documents.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma documento a tabela
     *
     * @param titleBox título da caixa
     * @param document documento
     */
    public void addRow(Box box, Book book, Document document) {
        for (int i = 0; i < documents.size(); i++) {
            if (document.getCodeDocument() == documents.get(i).getCodeDocument()) {
                return;
            }
        }
        documents.add(document);
        books.add(book);
        boxes.add(box);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove um documento da tabela
     *
     * @param rowIndex índice da linha
     */
    public void eraseRow(int rowIndex) {
        documents.remove(rowIndex);
        books.remove(rowIndex);
        boxes.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove um documento da tabela
     *
     * @param document documento
     */
    public void eraseRow(Document document) {
        boxes.remove(documents.indexOf(document));
        books.remove(documents.indexOf(document));
        documents.remove(document);

        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        //Collections.sort(documents);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param titleBoxes lista com títulos das caixas
     * @param document lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.documents = documents;
        this.boxes = boxes;
        this.books = books;
        order();
        fireTableDataChanged();
    }//fim do método refresh
}//fim da classe WithdrawalDocumentTableModel

