
/*-
 * Classname:             BoxTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  15/03/2013 - 13:16:18
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Box;

/**
 * Modelo para a tabela de BoxPanel
 *
 * @see javax.swing.table.AbstractTableModel
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxTableModel extends AbstractTableModel {

    //lista com boxes na tabela
    private List<Box> boxes;
    //número de colunas
    private int COLUMN_NUMBER = 1;
    //coluna da tabela
    private int COLUMN_BOX = 0;

    /**
     * Construtor com lista de caixas
     *
     * @param boxes caixas
     */
    public BoxTableModel(List<Box> boxes) {
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
            return box.getCodeClass() + "." + box.getTitleBox();
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
        boxes.add(box);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove uma caixa da tabela
     *
     * @param rowIndex índice da caixa
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
        Collections.sort(boxes, Box.forCategoryCode);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param boxes lista com caixas
     */
    public void refresh(List<Box> boxes) {
        this.boxes = boxes;
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Retorna a linha do código
     *
     * @param selectedCodeBox código da caixa
     * @return <code>Integer</code> com linha
     */
    public int getBoxPosition(int selectedCodeBox) {
        order();
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getCodeBox() == selectedCodeBox) {
                return i;
            }
        }
        return -1;
    }//fim do método getBoxPosition
}//fim da classe BoxTableModel

