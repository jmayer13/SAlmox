/*-
 * Classname:             RequestDocumentTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  24/04/2013 - 13:25:03
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentA;
import uni.uri.salmox.model.DocumentAE;
import uni.uri.salmox.model.DocumentCC;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.model.DocumentEX;
import uni.uri.salmox.model.DocumentF;
import uni.uri.salmox.model.DocumentFP;
import uni.uri.salmox.model.DocumentMC;
import uni.uri.salmox.model.DocumentSAE;
import uni.uri.salmox.util.Category;

/**
 * Modelo de tabela de solitar documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestDocumentTableModel extends AbstractTableModel {

    //lista com livros na tabela
    private List<Document> documents;
    //lista com  caixas
    private List<Box> boxes;
    private List<Book> books;
    //número de colunas
    private int COLUMN_NUMBER = 6;
    //coluna de categoria
    private int COLUMN_CATEGORY = 0;
    //coluna de caixa
    private int COLUMN_BOX = 1;
    //codigo livro
    private int COLUMN_CODE_BOOK = 2;
    //coluna de livro
    private int COLUMN_BOOK = 3;
    //coluna de código de documento
    private int COLUMN_CODE_DOCUMENT = 4;
    //coluna de título do documento
    private int COLUMN_TITLE_DOCUMENT = 5;

    /**
     * Construtor sem parametros
     */
    public RequestDocumentTableModel() {
        documents = new ArrayList();
        books = new ArrayList();
        boxes = new ArrayList();
        fireTableDataChanged();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de documentos
     *
     * @param documents documentos
     */
    public RequestDocumentTableModel(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.documents = documents;
        this.books = books;
        this.boxes = boxes;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de documentos

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
        if (columnIndex == COLUMN_CATEGORY) {
            return "Categoria";
        } else if (columnIndex == COLUMN_BOX) {
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
        if (columnIndex == COLUMN_CATEGORY) {
            return String.class;
        } else if (columnIndex == COLUMN_BOX) {
            return String.class;
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return String.class;
        } else if (columnIndex == COLUMN_BOOK) {
            return String.class;
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return Integer.class;
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
        Book book = books.get(rowIndex);
        Box box = boxes.get(rowIndex);
        if (columnIndex == COLUMN_CATEGORY) {
            return Category.getCategory(document).getName();
        } else if (columnIndex == COLUMN_BOX) {
            return box.getTitleBox();
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return book.getCodeBookSpecific();
        } else if (columnIndex == COLUMN_BOOK) {
            return book.getTitleBook();
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            if (document instanceof DocumentA) {
                DocumentA documentA = (DocumentA) document;
                return documentA.getCodeDocumentA();
            } else if (document instanceof DocumentAE) {
                DocumentAE documentAE = (DocumentAE) document;
                return documentAE.getCodeDocumentAE();
            } else if (document instanceof DocumentEX) {
                DocumentEX documentEX = (DocumentEX) document;
                return documentEX.getCodeDocumentEX();
            } else if (document instanceof DocumentF) {
                DocumentF documentF = (DocumentF) document;
                return documentF.getCodeDocumentF();
            } else if (document instanceof DocumentFP) {
                DocumentFP documentFP = (DocumentFP) document;
                return documentFP.getCodeDocumentFP();
            } else if (document instanceof DocumentCC) {
                DocumentCC documentCC = (DocumentCC) document;
                return documentCC.getCodeDocumentCC();
            } else if (document instanceof DocumentMC) {
                DocumentMC documentMC = (DocumentMC) document;
                return documentMC.getCodeDocumentMC();
            } else if (document instanceof DocumentSAE) {
                DocumentSAE documentSAE = (DocumentSAE) document;
                return documentSAE.getCodeDocumentSAE();
            } else if (document instanceof DocumentDP) {
                DocumentDP documentDP = (DocumentDP) document;
                return documentDP.getCodeDocumentDP();
            } else {
                return 0;
            }
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
            if (documents.get(i).getCodeDocument() == document.getCodeDocument()) {
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
        boxes.remove(rowIndex);
        books.remove(rowIndex);
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
     * @param codeBoxes lista com códigos das caixas
     * @param document lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.documents = documents;
        this.boxes = boxes;
        this.books = books;
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm lista de documentos
     *
     * @return <code>List </code> lista de documentos
     */
    public List getDocuments() {
        return documents;
    }//fim do método getDocuments
}//fim da classe RequestDocumentTableModel 

