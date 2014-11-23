
/*-
 * Classname:             DocumentTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  19/03/2013 - 13:31:32
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Document;

/**
 * Modelo para tabela de documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentTableModel extends AbstractTableModel {

    //lista com documentos na tabela
    private List<Document> documents;
    //número de colunas
    private int COLUMN_NUMBER = 2;
    //coluna de código dos livros
    private int COLUMN_CODE = 0;
    //coluna de título de livro
    private int COLUMN_TITLE = 1;

    /**
     * Construtor com lista de documentos
     *
     * @param documents documentos
     */
    public DocumentTableModel(List<Document> documents) {
        this.documents = documents;
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
        Document document = documents.get(rowIndex);
        if (columnIndex == COLUMN_CODE) {

            return document.getCodeDocumentSpecific();

        } else if (columnIndex == COLUMN_TITLE) {
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
     * Adiciona um documento a tabela
     *
     * @param document documento
     */
    public void addRow(Document document) {
        documents.add(document);
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
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove um documento da tabela
     *
     * @param document documento
     */
    public void eraseRow(Document document) {
        documents.remove(document);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        Collections.sort(documents, Document.forCategoryCode);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param document lista com documentos
     */
    public void refresh(List<Document> documents) {
        this.documents = documents;
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obtêm a posição do documemto com código informado
     *
     * @param selectedCodeDocument código do documento
     * @return <code>Integer</code> com posição do documento
     */
    public int getDocumentPosition(int selectedCodeDocument) {
        order();
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getCodeDocument() == selectedCodeDocument) {
                return i;
            }
        }
        return -1;
    }//fim do método getDocumentPosition
}//fim da classe DocumentTableModel

