/*-
 * Classname:             HistoryWithdrawalTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  02/05/2013 - 13:16:02
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Withdrawal;

/**
 * Modelo de table para histórico de retiradas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class HistoryWithdrawalTableModel extends AbstractTableModel {

    //lista de retiradas não entregues
    private List<Withdrawal> withdrawals;
    //número de colunas
    private int COLUMN_NUMBER = 4;
    //coluna código
    private int COLUMN_CODE = 0;
    //coluna de usuário
    private int COLUMN_USER = 1;
    //coluna de data
    private int COLUMN_DATE = 2;
    //coluna de observação
    private int COLUMN_OBSERVATION = 3;

    /**
     * Construtor sem parametros
     */
    public HistoryWithdrawalTableModel() {
        withdrawals = new ArrayList();
        fireTableDataChanged();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de documentos
     *
     * @param withdrawals retiradas
     */
    public HistoryWithdrawalTableModel(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de documentos

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return withdrawals.size();
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
        } else if (columnIndex == COLUMN_USER) {
            return "Usuário";
        } else if (columnIndex == COLUMN_DATE) {
            return "Data";
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
        if (columnIndex == COLUMN_CODE) {
            return String.class;
        } else if (columnIndex == COLUMN_USER) {
            return String.class;
        } else if (columnIndex == COLUMN_DATE) {
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
        Withdrawal withdrawal = withdrawals.get(rowIndex);
        if (columnIndex == COLUMN_CODE) {
            return withdrawal.getCodeWithDrawal();
        } else if (columnIndex == COLUMN_USER) {
            return withdrawal.getSolicitor();
        } else if (columnIndex == COLUMN_DATE) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(withdrawal.getDateWithdrawal());
        } else if (columnIndex == COLUMN_OBSERVATION) {
            return withdrawal.getObservationWithdrawal();
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
     * @return <code>Withdrawal</code> retirada
     */
    public Withdrawal getRow(int rowIndex) {
        return withdrawals.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma retirada a tabela
     *
     * @param withdrawal retirada
     */
    public void addRow(Withdrawal withdrawal) {
        withdrawals.add(withdrawal);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove uma retirada da tabela
     *
     * @param rowIndex índice da linha
     */
    public void eraseRow(int rowIndex) {
        withdrawals.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove uma retirada da tabela
     *
     * @param withdrawal retirada
     */
    public void eraseRow(Withdrawal withdrawal) {
        withdrawals.remove(withdrawal);

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
     * @param withdrawals lista com retiradas
     */
    public void refresh(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
        order();
        fireTableDataChanged();
    }//fim do método refresh
}//fim da classe HistoryWithdrawalTableModel 

