/*-
 * Classname:             ColorTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  15/05/2013 - 13:15:17
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.util.Category;

/**
 * Modelo de tabema de cores para configurações
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ColorTableModel extends AbstractTableModel {

    //lista com cores das caixas
    private List<Color> colorsBoxes;
    //lista com cores dos livros
    private List<Color> colorsBooks;
    //lista com cores dos documentos
    private List<Color> colorsDocuments;
    //número de colunas
    private int COLUMN_NUMBER = 4;
    //coluna categoria
    private int COLUMN_CATEGORY = 0;
    //coluna caixa
    private int COLUMN_BOX = 1;
    //coluna livro
    private int COLUMN_BOOK = 2;
    //coluna documento
    private int COLUMN_DOCUMENT = 3;

    /**
     * Construtor sem parâmetros
     */
    public ColorTableModel() {
        colorsBoxes = new ArrayList();
        colorsBooks = new ArrayList();
        colorsDocuments = new ArrayList();

    }//fim do construtor

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return colorsBoxes.size();
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
        } else if (columnIndex == COLUMN_BOOK) {
            return "Livro";
        } else if (columnIndex == COLUMN_DOCUMENT) {
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
        if (columnIndex == 0) {
            return String.class;
        } else {
            return Color.class;
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
        if (columnIndex == COLUMN_CATEGORY) {
            if (rowIndex == 0) {
                return Category.A.getName();
            } else if (rowIndex == 1) {
                return Category.AE.getName();
            } else if (rowIndex == 2) {
                return Category.CC.getName();
            } else if (rowIndex == 3) {
                return Category.DAC.getName();
            } else if (rowIndex == 4) {
                return Category.DAD.getName();
            } else if (rowIndex == 5) {
                return Category.DG.getName();
            } else if (rowIndex == 6) {
                return Category.DP.getName();
            } else if (rowIndex == 7) {
                return Category.DSG.getName();
            } else if (rowIndex == 8) {
                return Category.EX.getName();
            } else if (rowIndex == 9) {
                return Category.F.getName();
            } else if (rowIndex == 10) {
                return Category.FC.getName();
            } else if (rowIndex == 11) {
                return Category.FP.getName();
            } else if (rowIndex == 12) {
                return Category.MC.getName();
            } else if (rowIndex == 13) {
                return Category.SAE.getName();
            } else {
                return "";
            }
        }
        return "";

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
        if (columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }//fim do método isCellEditable

    /**
     * Obtêm cor da celula
     *
     * @param rowIndex linha
     * @param columnIndex coluna
     * @return <code>Integer</code> cor
     */
    public Color getColor(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return Color.CYAN;
        } else if (columnIndex == 1) {
            return colorsBoxes.get(rowIndex);
        } else if (columnIndex == 2) {
            return colorsBooks.get(rowIndex);
        } else if (columnIndex == 3) {
            return colorsDocuments.get(rowIndex);
        } else {
            return Color.BLACK;
        }

    }//fim do método getColor

    /**
     * Define cor da célula
     *
     * @param rowIndex linha
     * @param columnIndex coluna
     * @param colorRGB cor
     */
    public void setColor(int rowIndex, int columnIndex, Color color) {
        if (columnIndex == 1) {
            colorsBoxes.set(rowIndex, color);
        } else if (columnIndex == 2) {
            colorsBooks.set(rowIndex, color);
        } else if (columnIndex == 3) {
            colorsDocuments.set(rowIndex, color);
        }
        fireTableDataChanged();
    }//fim do método setColor

    /**
     * Define lista de cores
     *
     * @param colorsBoxes lçista de cores para caixas
     * @param colorsBooks lista de cores para livros
     * @param colorsDocuments lista de cores para documentos
     */
    public void setColorList(List<Color> colorsBoxes, List<Color> colorsBooks, List<Color> colorsDocuments) {
        this.colorsBoxes = colorsBoxes;
        this.colorsBooks = colorsBooks;
        this.colorsDocuments = colorsDocuments;
        fireTableDataChanged();
    }//fim do método setColorList

    /**
     * Obtêm lista de cores de caixas
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsBoxes() {
        return colorsBoxes;
    }//fim do método getColorsBoxes

    /**
     * Obtêm lista de cores de livros
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsBooks() {
        return colorsBooks;
    }//fim do método getColorsBooks

    /**
     * Obtêm lista de cores de documentos
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsDocuments() {
        return colorsDocuments;
    }//fim do método getColorsDocuments
}//fim da classe ColorTableModel 

