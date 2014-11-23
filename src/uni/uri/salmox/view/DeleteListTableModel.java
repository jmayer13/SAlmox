/*-
 * Classname:             DeleteListTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  23/04/2013 - 14:04:56
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.Category;

/**
 * Modelo de tabela de exclusão de caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DeleteListTableModel extends AbstractTableModel {
    //lista com caixas na tabela

    private List<Box> boxes;
    //número de colunas
    private int COLUMN_NUMBER = 3;
    //coluna de caixa
    private int COLUMN_BOX = 0;
    //coluna de categoria
    private int COLUMN_CATEGORY = 1;
    //coluna de observação da caixa
    private int COLUMN_OBSERVATION_BOX = 2;

    /**
     * Construtor sem parametros
     *
     */
    public DeleteListTableModel() {
        boxes = new ArrayList();
        fireTableDataChanged();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de caixas
     *
     * @param boxes boxes
     */
    public DeleteListTableModel(List<Box> boxes) {
        this.boxes = boxes;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de caixas

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return boxes.size();
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
        } else if (columnIndex == COLUMN_CATEGORY) {
            return "Categoria";
        } else if (columnIndex == COLUMN_OBSERVATION_BOX) {
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
        if (columnIndex == COLUMN_BOX) {
            return String.class;
        } else if (columnIndex == COLUMN_CATEGORY) {
            return String.class;
        } else if (columnIndex == COLUMN_OBSERVATION_BOX) {
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
        Box box = boxes.get(rowIndex);
        if (columnIndex == COLUMN_BOX) {
            return box.getTitleBox();
        } else if (columnIndex == COLUMN_CATEGORY) {
            return Category.getCategory(box).getName();

        } else if (columnIndex == COLUMN_OBSERVATION_BOX) {
            return box.getObservationBox();

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
     * Retorna caixa da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Box</code> caixa da linha
     */
    public Box getRow(int rowIndex) {
        return boxes.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma caixa a tabela
     *
     * @param box caixa
     */
    public void addRow(Box box) {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getCodeBox() == box.getCodeBox()) {
                return;
            }
        }
        boxes.add(box);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove uma caixa da tabela
     *
     * @param rowIndex índice da linha
     */
    public void eraseRow(int rowIndex) {
        boxes.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove uma caixa da tabela
     *
     * @param box caixa
     */
    public void eraseRow(Box box) {
        boxes.remove(box);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        Collections.sort(boxes, Box.forTitle);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param box lista com caixas
     */
    public void refresh(List<Box> boxes) {
        this.boxes = boxes;
        order();
        fireTableDataChanged();
    }//fim do método refresh
}//fim da classe DeleteListTableModel 

