
/*-
 * Classname:             BookTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  18/03/2013 - 14:25:07
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.util.Category;

/**
 * Modelo para tabela de livros
 *
 * @see javax.swing.table.AbstractTableModel
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookTableModel extends AbstractTableModel {
    //lista com livros na tabela

    private List<Book> books;
    //número de colunas
    private int COLUMN_NUMBER = 2;
    //coluna de código dos livros
    private int COLUMN_CODE = 0;
    //coluna de título de livro
    private int COLUMN_TITLE = 1;

    /**
     * Construtor com lista de livros
     *
     * @param books livros
     */
    public BookTableModel(List<Book> books) {
        this.books = books;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de livros

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
        if (columnIndex == COLUMN_CODE) {
            return "Código";
        } else if (columnIndex == COLUMN_TITLE) {
            return "Título";
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
        if (columnIndex == COLUMN_CODE) {
            return Integer.class;
        } else if (columnIndex == COLUMN_TITLE) {
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

        if (columnIndex == COLUMN_CODE) {
            Category category = Category.getCategory(book);
            return book.getCodeBookSpecific();

        } else if (columnIndex == COLUMN_TITLE) {
            return book.getTitleBook();
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
     * Retorna livro da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Box</code> caixa da linha
     */
    public Book getRow(int rowIndex) {
        return books.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma caixa a tabela
     *
     * @param book livro
     */
    public void addRow(Book book) {
        books.add(book);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove um livro da tabela
     *
     * @param rowIndex índice da linha
     */
    public void eraseRow(int rowIndex) {
        books.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove um livro da tabela
     *
     * @param book livro
     */
    public void eraseRow(Book book) {
        books.remove(book);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        Collections.sort(books, Book.forCategoryCode);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param books lista com livros
     */
    public void refresh(List<Book> books) {
        this.books = books;
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm a posição do livro com código informado
     *
     * @param selectedCodeBook código do livro
     * @return <code>Integer</code> com posição do livro
     */
    public int getBookPosition(int selectedCodeBook) {
        order();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCodeBook() == selectedCodeBook) {
                return i;
            }
        }
        return -1;
    }//fim do método getBoxPosition
}//fim da classe BookTableModel

