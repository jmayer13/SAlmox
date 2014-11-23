/*-
 * Classname:             ResultSearchBookTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  06/05/2013 - 13:18:42
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

/**
 * Modelo de tabela para busca de livro
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ResultSearchBookTableModel extends AbstractTableModel {

    private List<Boolean> checkedList;
    //lista com books na tabela
    private List<Book> books;
    //lista de caixas 
    private List<Box> boxes;
    //número de colunas
    private int COLUMN_NUMBER = 5;
    //caixa de veriificação
    private final int COLUMN_CHECKED = 0;
    //coluna da caixa
    private int COLUMN_BOX = 1;
    //coluna de código de livro da categoria
    private int COLUMN_CODE_BOOK = 2;
    //coluna de tiulo da caixa
    private int COLUMN_TITLE_BOOK = 3;
    //coluna observação do livro
    private int COLUMN_OBSERVATION_BOOK = 4;

    /**
     * Construtor com lista de livros
     *
     * @param boxes caixas
     */
    public ResultSearchBookTableModel(List<Box> boxes, List<Book> books) {
        this.books = books;
        this.boxes = boxes;
        checkedList = new ArrayList();
        for (int i = 0; i < books.size(); i++) {
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
        return books.size();
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
            return "Código do Livro";
        } else if (columnIndex == COLUMN_TITLE_BOOK) {
            return "Título do Livro";
        } else if (columnIndex == COLUMN_OBSERVATION_BOOK) {
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
        } else if (columnIndex == COLUMN_TITLE_BOOK) {
            return String.class;
        } else if (columnIndex == COLUMN_OBSERVATION_BOOK) {
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
        Book book = books.get(rowIndex);
        Box box = boxes.get(rowIndex);
        if (columnIndex == COLUMN_CHECKED) {
            return checkedList.get(rowIndex);
        } else if (columnIndex == COLUMN_BOX) {
            return box.getTitleBox();
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
        } else if (columnIndex == COLUMN_TITLE_BOOK) {
            return book.getTitleBook();
        } else if (columnIndex == COLUMN_OBSERVATION_BOOK) {
            return book.getObservationBook();
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
     * @return <code>Book</code> livro da linha
     */
    public Book getRow(int rowIndex) {
        return books.get(rowIndex);
    }//fim do método getRow

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
    public void addRow(Box box, Book book) {
        books.add(book);
        boxes.add(box);
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
        checkedList.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove uma linha da tabela
     *
     * @param book caixa
     */
    public void eraseRow(Book book) {
        boolean watcher = false;

        for (int i = 0; i < boxes.size(); i++) {
            if (!watcher) {
                if (boxes.get(i).getCodeBox() == book.getCodeBox()) {
                    boxes.remove(i);
                    watcher = true;
                }
            }
        }
        books.remove(book);
        checkedList.remove(0);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        /* boolean watcher = false;

         for (int i = 0; i < books.size(); i++) {
         checkedList.set(i, false);
         }
         Collections.sort(books);
         List<Box> newOrderBoxes = new ArrayList();
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
     */
    public void refresh(List<Box> boxes, List<Book> books) {
        this.boxes = boxes;
        this.books = books;
        checkedList.clear();
        for (int i = 0; i < books.size(); i++) {
            checkedList.add(false);
        }
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm livros marcados
     *
     * @return <code>List</code> lista de livros
     */
    public List<Book> getCheckedRows() {
        List<Book> checkedBooks = new ArrayList();
        for (int i = 0; i < books.size(); i++) {
            if (checkedList.get(i)) {
                checkedBooks.add(books.get(i));
            }
        }
        return checkedBooks;
    }//fim do método getCheckedRows

    @Override
    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            checkedList.set(rowIndex, (Boolean) object);
        }
    }

    /**
     * Substitui livro
     *
     * @param book livro
     */
    public void replaceBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCodeBook() == book.getCodeBook()) {
                books.remove(i);
                books.add(book);
            }
        }
        fireTableDataChanged();
    }//fim do método replaceBook
}//fim da classe ResultSearchBookTableModel 

