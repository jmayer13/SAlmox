/*-
 * Classname:             ResultSearchDocumentTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  06/05/2013 - 14:23:58
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.model.BookAE;
import uni.uri.salmox.model.BookCC;
import uni.uri.salmox.model.BookDP;
import uni.uri.salmox.model.BookEX;
import uni.uri.salmox.model.BookF;
import uni.uri.salmox.model.BookFP;
import uni.uri.salmox.model.BookMC;
import uni.uri.salmox.model.BookSAE;
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

/**
 * Descrição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ResultSearchDocumentTableModel extends AbstractTableModel {

    private List<Boolean> checkedList;
    //lista com books na tabela
    private List<Book> books;
    //lista de caixas 
    private List<Box> boxes;
    //lista de documentos
    private List<Document> documents;
    //número de colunas
    private final int COLUMN_NUMBER = 6;
    private final int COLUMN_CHECKED = 0;
    //coluna da caixa
    private final int COLUMN_BOX = 1;
    //coluna de código de livro da categoria
    private final int COLUMN_CODE_BOOK = 2;
    //coluna com código do documento por categoria
    private final int COLUMN_CODE_DOCUMENT = 3;
    //coluna com título do documento
    private final int COLUMN_TITLE_DOCUMENT = 4;
    //coluna com observação
    private final int COLUMN_OBSERVATION_DOCUMENT = 5;

    /**
     * Construtor com listas
     *
     * @param boxes caixas
     * @param books livros
     * @param documents documentos
     */
    public ResultSearchDocumentTableModel(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.boxes = new ArrayList();
        this.books = new ArrayList();
        this.documents = new ArrayList();
        for (int i = 0; i < boxes.size(); i++) {
            this.boxes.add(boxes.get(i));
        }
        for (int i = 0; i < books.size(); i++) {
            this.books.add(books.get(i));
        }
        for (int i = 0; i < documents.size(); i++) {
            this.documents.add(documents.get(i));
        }
        checkedList = new ArrayList();
        for (int i = 0; i < documents.size(); i++) {
            checkedList.add(false);
        }
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de caixas

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
        if (columnIndex == COLUMN_CHECKED) {
            return " ";
        } else if (columnIndex == COLUMN_BOX) {
            return "Caixa";
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return "Livro";
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return "Código do Documento";
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return "Título do Documento";
        } else if (columnIndex == COLUMN_OBSERVATION_DOCUMENT) {
            return "Observação";
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
        if (columnIndex == COLUMN_CHECKED) {
            return Boolean.class;
        } else if (columnIndex == COLUMN_BOX) {
            return String.class;
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            return Integer.class;
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            return String.class;
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return String.class;
        } else if (columnIndex == COLUMN_OBSERVATION_DOCUMENT) {
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
        Book book = null;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCodeBook() == document.getCodeBook()) {
                book = books.get(i);
            }
        }

        Box box = null;
        for (int i = 0; i < boxes.size(); i++) {
            if (book.getCodeBox() == boxes.get(i).getCodeBox()) {
                box = boxes.get(i);
            }
        }

        if (columnIndex == COLUMN_CHECKED) {
            return checkedList.get(rowIndex);
        } else if (columnIndex == COLUMN_BOX) {
            if (box != null) {
                return box.getTitleBox();
            } else {
                return null;
            }
        } else if (columnIndex == COLUMN_CODE_BOOK) {
            if (book instanceof BookA) {
                BookA bookA = (BookA) book;
                return bookA.getCodeBookA();
            } else if (book instanceof BookAE) {
                BookAE bookAE = (BookAE) book;
                return bookAE.getCodeBookAE();
            } else if (book instanceof BookCC) {
                BookCC bookCC = (BookCC) book;
                return bookCC.getCodeBookCC();
            } else if (book instanceof BookDP) {
                BookDP bookDP = (BookDP) book;
                return bookDP.getCodeBookDP();
            } else if (book instanceof BookEX) {
                BookEX bookEX = (BookEX) book;
                return bookEX.getCodeBookEX();
            } else if (book instanceof BookF) {
                BookF bookF = (BookF) book;
                return bookF.getCodeBookF();
            } else if (book instanceof BookFP) {
                BookFP bookFP = (BookFP) book;
                return bookFP.getCodeBookFP();
            } else if (book instanceof BookMC) {
                BookMC bookMC = (BookMC) book;
                return bookMC.getCodeBookMC();
            } else if (book instanceof BookSAE) {
                BookSAE bookSAE = (BookSAE) book;
                return bookSAE.getCodeBookSAE();
            } else {
                return "";
            }
        } else if (columnIndex == COLUMN_CODE_DOCUMENT) {
            if (document instanceof DocumentA) {
                DocumentA documentA = (DocumentA) document;
                return documentA.getCodeDocumentA();
            } else if (document instanceof DocumentAE) {
                DocumentAE documentAE = (DocumentAE) document;
                return documentAE.getCodeDocumentAE();
            } else if (document instanceof DocumentCC) {
                DocumentCC documentCC = (DocumentCC) document;
                return documentCC.getCodeDocumentCC();
            } else if (document instanceof DocumentDP) {
                DocumentDP documentDP = (DocumentDP) document;
                return documentDP.getCodeDocumentDP();
            } else if (document instanceof DocumentEX) {
                DocumentEX documentEX = (DocumentEX) document;
                return documentEX.getCodeDocumentEX();
            } else if (document instanceof DocumentF) {
                DocumentF documentF = (DocumentF) document;
                return documentF.getCodeDocumentF();
            } else if (document instanceof DocumentFP) {
                DocumentFP documentFP = (DocumentFP) document;
                return documentFP.getCodeDocumentFP();
            } else if (document instanceof DocumentMC) {
                DocumentMC documentMC = (DocumentMC) document;
                return documentMC.getCodeDocumentMC();
            } else if (document instanceof DocumentSAE) {
                DocumentSAE documentSAE = (DocumentSAE) document;
                return documentSAE.getCodeDocumentSAE();
            } else {
                return "";
            }
        } else if (columnIndex == COLUMN_TITLE_DOCUMENT) {
            return document.getTitleDocument();
        } else if (columnIndex == COLUMN_OBSERVATION_DOCUMENT) {
            return document.getObservationDocument();
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
        if (columnIndex == COLUMN_CHECKED) {
            return true;
        } else {
            return false;
        }
    }//fim do método isCellEditable

    /**
     * Retorna livro da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Document</code> documento da linha
     */
    public Document getRow(int rowIndex) {
        return documents.get(rowIndex);
    }//fim do método getRow

    /**
     * Retorna livro da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Book</code> livro da linha
     */
    public Book getRowBook(int rowIndex) {
        return books.get(rowIndex);
    }//fim do método getRowBook

    /**
     * Retorna caixa da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Box</code> caixa da linha
     */
    public Box getRowBox(int rowIndex) {
        return boxes.get(rowIndex);
    }//fim do método getRowBox

    /**
     * Adiciona um linha a tabela
     *
     * @param box caixa
     * @param book livro
     */
    public void addRow(Box box, Book book, Document document) {
        books.add(book);
        boxes.add(box);
        documents.add(document);
        checkedList.add(false);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove uma linha da tabela
     *
     * @param rowIndex índice da caixa
     */
    public void eraseRow(int rowIndex) {
        boxes.remove(rowIndex);
        books.remove(rowIndex);
        documents.remove(rowIndex);
        checkedList.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove uma linha da tabela
     *
     * @param document documento
     */
    public void eraseRow(Document document) {
        boolean watcher = false;
        Book book = null;
        for (int i = 0; i < books.size(); i++) {
            if (!watcher) {
                if (books.get(i).getCodeBook() == document.getCodeBook()) {
                    book = books.get(i);
                    books.remove(i);
                    watcher = true;
                }
            }
        }
        for (int i = 0; i < boxes.size(); i++) {
            if (!watcher) {
                if (boxes.get(i).getCodeBox() == book.getCodeBox()) {
                    boxes.remove(i);
                    watcher = true;
                }
            }
        }

        documents.remove(document);
        checkedList.remove(0);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        /*    boolean watcher = false;
         Book book = null;

         for (int i = 0; i < documents.size(); i++) {
         checkedList.set(i, false);
         }
         Collections.sort(documents);

         List<Box> newOrderBoxes = new ArrayList();
         List<Book> newOrderBooks = new ArrayList();

         for (int i = 0; i < documents.size(); i++) {
         for (int j = 0; j < books.size(); j++) {
         if (!watcher) {
         if (books.get(j).getCodeBook() == documents.get(i).getCodeBook()) {
         newOrderBooks.add(books.get(j));
         watcher = true;
         }
         }
         }
         watcher = false;
         }
         books = newOrderBooks;

         for (int i = 0; i < books.size(); i++) {
         for (int j = 0; j < boxes.size(); j++) {
         if (!watcher) {
         if (boxes.get(j).getCodeBox() == books.get(i).getCodeBox()) {
         newOrderBoxes.add(boxes.get(j));
         watcher = true;
         }
         }
         }
         watcher = false;
         }

         boxes = newOrderBoxes;*/
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param boxes lista com caixas
     * @param books lista com livros
     * @param lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        this.boxes = boxes;
        this.books = books;
        this.documents = documents;
        checkedList.clear();
        for (int i = 0; i < documents.size(); i++) {
            checkedList.add(false);
        }

        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm lista de documentos marcados
     *
     * @return <code></code>
     */
    public List<Document> getCheckedRows() {
        List<Document> checkedDocuments = new ArrayList();
        for (int i = 0; i < documents.size(); i++) {
            if (checkedList.get(i)) {
                checkedDocuments.add(documents.get(i));
            }
        }
        return checkedDocuments;
    }//fim do método getCheckedRows

    /**
     * Define valor de célula especificada
     *
     * @param object valor da célula
     * @param rowIndex indice da linha
     * @param columnIndex indice da coluna
     */
    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            checkedList.set(rowIndex, (Boolean) object);
        }
    }//fim do método setValueAt

    /**
     * Substitui documento
     *
     * @param document documento
     */
    public void replaceDocument(Document document) {
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getCodeDocument() == document.getCodeDocument()) {
                documents.remove(i);
                documents.add(document);
            }
        }
        fireTableDataChanged();
    }//fim do método replaceDocument
}//fim da classe ResultSearchDocumentTableModel 

