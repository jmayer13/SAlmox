/*-
 * Classname:             RequestsDefaultUserTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  29/04/2013 - 13:18:36
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Request;

/**
 * Modelo da tabela de solicitações de documentos para usuários padrão
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestsDefaultUserTableModel extends AbstractTableModel {

    //lista com solicitações
    private List<Request> requests;
    //número de colunas
    private int COLUMN_NUMBER = 3;
    //colunas da tabela
    //colunas da data
    private int COLUMN_DATE = 0;
    //coluna de observação
    private int COLUMN_OBSERVATION = 1;
    //coluna de motivo
    private int COLUMN_MOTIVE = 2;

    /**
     * Construtor sem parâmetro
     */
    public RequestsDefaultUserTableModel() {
        requests = new ArrayList();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de solicitações
     *
     * @param requests solicitações
     */
    public RequestsDefaultUserTableModel(List<Request> requests) {
        this.requests = requests;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de solicitações

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return requests.size();
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
        if (columnIndex == COLUMN_DATE) {
            return "Data";
        } else if (columnIndex == COLUMN_OBSERVATION) {
            return "Observação";
        } else if (columnIndex == COLUMN_MOTIVE) {
            return "Motivo";
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
        if (columnIndex == COLUMN_DATE) {
            return String.class;
        } else if (columnIndex == COLUMN_OBSERVATION) {
            return String.class;
        } else if (columnIndex == COLUMN_MOTIVE) {
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
        Request request = requests.get(rowIndex);
        if (columnIndex == COLUMN_DATE) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(request.getDateRequest());
        } else if (columnIndex == COLUMN_OBSERVATION) {
            return request.getObservationRequest();
        } else if (columnIndex == COLUMN_MOTIVE) {
            return request.getMotiveRequest();
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
     * Retorna solicitação da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>Request</code> solicitação da linha
     */
    public Request getRow(int rowIndex) {
        return requests.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma solicitação a tabela
     *
     * @param request solicitação
     */
    public void addRow(Request request) {
        requests.add(request);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove uma solicitação da tabela
     *
     * @param rowIndex índice da caixa
     */
    public void eraseRow(int rowIndex) {
        requests.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove uma solicitação da tabela
     *
     * @param request solicitação
     */
    public void eraseRow(Request request) {
        requests.remove(request);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        Collections.sort(requests);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param requests lista de solicitações
     */
    public void refresh(List<Request> requests) {
        this.requests = requests;
        order();
        fireTableDataChanged();
    }//fim do método refresh
}//fim da classe RequestsDefaultUserTableModel 

