/*-
 * Classname:             ResultSearchBoxTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  03/05/2013 - 15:07:41
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

/**
 * Modelo da tabela do resuktado da busca por caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ResultSearchBoxTableModel extends AbstractTableModel {

    private List<Boolean> checkedList;
    //lista com boxes na tabela
    private List<Box> boxes;
    //número de colunas
    private int COLUMN_NUMBER = 4;
    private final int COLUMN_CHECKED = 0;
    //coluna da tabela
    private int COLUMN_BOX = 1;
    //coluna responsáveis
    private int COLUMN_RESPONSIBLES = 2;
    //coluna obsrvação
    private int COLUMN_OBSERVATION = 3;

    /**
     * Construtor com lista de caixas
     *
     * @param boxes caixas
     */
    public ResultSearchBoxTableModel(List<Box> boxes) {
        this.boxes = boxes;
        checkedList = new ArrayList();
        for (int i = 0; i < boxes.size(); i++) {
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
        if (columnIndex == COLUMN_CHECKED) {
            return " ";
        } else if (columnIndex == COLUMN_BOX) {
            return "Caixa";
        } else if (columnIndex == COLUMN_RESPONSIBLES) {
            return "Responsaveis";
        } else if (columnIndex == COLUMN_OBSERVATION) {
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
        } else if (columnIndex == COLUMN_RESPONSIBLES) {
            return String.class;
        } else if (columnIndex == COLUMN_OBSERVATION) {
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
        if (columnIndex == COLUMN_CHECKED) {
            return checkedList.get(rowIndex);
        } else if (columnIndex == COLUMN_BOX) {
            return box.getTitleBox();
        } else if (columnIndex == COLUMN_RESPONSIBLES) {
            return box.getResponsibleBox();
        } else if (columnIndex == COLUMN_OBSERVATION) {
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
        if (columnIndex == COLUMN_CHECKED) {
            return true;
        } else {
            return false;
        }
    }//fim do método isCellEditable

    /**
     * Retorna caixa da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Box</code> caixa da linha
     */
    public Box getRow(int rowIndex) {
        if (!boxes.isEmpty()) {
            return boxes.get(rowIndex);
        } else {
            return null;
        }
    }//fim do método getRow

    /**
     * Adiciona uma caixa a tabela
     *
     * @param box caixa
     */
    public void addRow(Box box) {
        boxes.add(box);
        checkedList.add(false);
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
        checkedList.remove(rowIndex);
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
        checkedList.remove(0);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        for (int i = 0; i < boxes.size(); i++) {
            checkedList.set(i, false);
        }
        Collections.sort(boxes);

    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param boxes lista com caixas
     */
    public void refresh(List<Box> boxes) {
        this.boxes = boxes;
        checkedList.clear();
        for (int i = 0; i < boxes.size(); i++) {
            checkedList.add(false);
        }

        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm caixas marcadas
     *
     * @return <code>List</code> lista de caixas marcadas
     */
    public List<Box> getCheckedRows() {
        List<Box> checkedBoxes = new ArrayList();
        for (int i = 0; i < boxes.size(); i++) {
            if (checkedList.get(i)) {
                checkedBoxes.add(boxes.get(i));
            }
        }
        return checkedBoxes;
    }//fim do método getCheckedRows

    /**
     * Define valor para célula
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
     * Substitui caixa
     *
     * @param box caixa
     */
    public void replaceBox(Box box) {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getCodeBox() == box.getCodeBox()) {
                boxes.remove(i);
                boxes.add(box);
            }
        }
        fireTableDataChanged();
    }//fim do método replaceBox
}//fim da classe ResultSearchBoxTableModel 

