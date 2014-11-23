
/*-
 * Classname:             UserManagerTableModel.java
 *
 * Version information:   1.0
 *
 * Date:                  01/04/2013 - 14:44:02
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.User;

/**
 * Modelo da table da view de gerenciamento de usuários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class UserManagerTableModel extends AbstractTableModel {

    //lista com usuários
    private List<User> users;
    //número de colunas
    private int COLUMN_NUMBER = 4;
    //coluna de nome do usuário
    private int COLUMN_NAME = 0;
    //coluna de e-mail
    private int COLUMN_MAIL = 1;
    //coluna de login
    private int COLUMN_LOGIN = 2;
    //coluna de tipo de usuário
    private int COLUMN_TYPE = 3;

    /**
     * Construtor sem parametros
     */
    public UserManagerTableModel() {
        users = new ArrayList();
        fireTableDataChanged();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de usuários
     *
     * @param users usuários
     */
    public UserManagerTableModel(List<User> users) {
        this.users = users;
        order();
        fireTableDataChanged();
    }//fim do construtor com lista de documentos

    /**
     * Retorna número de linhas
     *
     * @return <code>Integer</code> número de linhas
     */
    public int getRowCount() {
        return users.size();
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
        if (columnIndex == COLUMN_NAME) {
            return "Nome";
        } else if (columnIndex == COLUMN_MAIL) {
            return "Email";
        } else if (columnIndex == COLUMN_LOGIN) {
            return "Login";
        } else if (columnIndex == COLUMN_TYPE) {
            return "Tipo";
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
        if (columnIndex == COLUMN_NAME) {
            return String.class;
        } else if (columnIndex == COLUMN_MAIL) {
            return String.class;
        } else if (columnIndex == COLUMN_LOGIN) {
            return String.class;
        } else if (columnIndex == COLUMN_TYPE) {
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
        User user = users.get(rowIndex);
        if (columnIndex == COLUMN_NAME) {
            return user.getName();
        } else if (columnIndex == COLUMN_MAIL) {
            return user.getEmail();
        } else if (columnIndex == COLUMN_LOGIN) {
            return user.getUserLogin();
        } else if (columnIndex == COLUMN_TYPE) {
            if (user instanceof Administrator) {
                return "Administrador";
            } else if (user instanceof DefaultUser) {
                return "Usuário Padrão";
            } else {
                return "Desconhecido";
            }
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
     * Retorna usuário da linha especificada
     *
     * @param rowIndex índice da linha
     * @return <code>User</code> usuário da linha
     */
    public User getRow(int rowIndex) {
        return users.get(rowIndex);
    }//fim do método getRow

    /**
     * Adiciona uma usuário a tabela
     *
     * @param user usuário
     */
    public void addRow(User user) {
        users.add(user);
        order();
        fireTableDataChanged();
    }//fim do método addRow

    /**
     * Remove um usuário da tabela
     *
     * @param rowIndex índice da linha
     */
    public void eraseRow(int rowIndex) {
        users.remove(rowIndex);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com índice

    /**
     * Remove um documento da tabela
     *
     * @param user user
     */
    public void eraseRow(User user) {
        users.remove(user);
        order();
        fireTableDataChanged();
    }//fim do método eraseRow com caixa

    /**
     * Ordena tabela
     */
    public void order() {
        Collections.sort(users);
    }//fim do método order

    /**
     * Atualiza tabela
     *
     * @param users lista com usuários
     */
    public void refresh(List<User> users) {
        this.users = users;
        order();
        fireTableDataChanged();
    }//fim do método refresh

    /**
     * Obt~em posição do usuário na tabela
     *
     * @param selectedCodeUser código do usuário
     * @return <-code>Integer</code> posição do usuário, retorna -1 não
     * encontrada
     */
    public int getBoxPosition(int selectedCodeUser) {
        order();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCodeUser() == selectedCodeUser) {
                return i;
            }
        }
        return -1;
    }//fim do método getBoxPosition
}//fim da classe UserManagerTableModel

