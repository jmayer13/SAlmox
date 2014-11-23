/*-
 * Classname:             ManagerUserController.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 14:42:37
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uni.uri.salmox.DAO.UserDAO;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.view.UserManagerPanel;

/**
 * Controle de usuário
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ManagerUserController implements ObserverInterface {

    //DAO
    private UserDAO userDAO;
    //visão
    private UserManagerPanel view;
    //lista de usuários
    private List<User> users;
    //observer
    private ObserverInterface observer;
    //código do usuário selecionado
    private int selectedCodeUser = 0;

    /**
     * Construtorsem parâmetros
     */
    public ManagerUserController() {
        //inicializa DAO
        userDAO = new UserDAO();
        //inicializa visão
        view = new UserManagerPanel();
        //busca usuários
        Iterator iterator = userDAO.searchUsers();
        users = new ArrayList();
        while (iterator.hasNext()) {
            users.add((User) iterator.next());
        }
        view.refresh(users);

        //define eventos
        //adicionar
        view.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        //editar
        view.setActionListenerEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });
        //apagar
        view.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eraseUser();
            }
        });
    }//fim do construtor

    /**
     * Adicionar usuário
     */
    private void addUser() {
        AddUserController addUserController = new AddUserController();
        addUserController.registerObserver(observer);
        addUserController.registerObserver(this);
        addUserController.notifyObservers(true, false);
    }//fim do método addUser

    /**
     * Editar usuário
     */
    private void editUser() {
        User user = view.getSelectedUser();
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Selecione um usuário!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddUserController addUserController = new AddUserController(user);
            addUserController.registerObserver(observer);
            addUserController.registerObserver(this);
            addUserController.notifyObservers(true, false);
        }
    }//fim do método editUser

    /**
     * Apaga usuário
     */
    private void eraseUser() {
        User user = view.getSelectedUser();
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Selecione um usuário!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int response = JOptionPane.showConfirmDialog(null, "Deseja excluir usuário? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == 0) {
                userDAO.deleteUser(user);
                update(true, new User());
            }
        }
    }//fim do método eraseUser

    /**
     * Define observador para todos os subjetos filhos
     *
     * @param observer
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Obtêm panel da visão
     *
     * @return <code>JPanel</code> visão
     */
    public JPanel getPanel() {
        return view.getPanel();
    }//fim do método getPanel

    /**
     * Define código do usuário selecionado
     *
     * @param selectedCodeUser código do usuário selecionada
     */
    public void setSelectedCodeUser(int selectedCodeUser) {
        this.selectedCodeUser = selectedCodeUser;
        restoreSelectedUser();
    }//fim do método setSelectedCodeUser

    /**
     * Restaura usuário selecionada
     */
    private void restoreSelectedUser() {
        view.setSelectedUser(selectedCodeUser);
    }//fim do método restoreSelectedUser

    /**
     * Atualiza controllert
     *
     * @param active
     * @param object
     */
    public void update(boolean active, Object object) {
        if (object instanceof User) {
            User user = (User) object;
            //busca usuários
            Iterator iterator = userDAO.searchUsers();
            users = new ArrayList();
            while (iterator.hasNext()) {
                users.add((User) iterator.next());
            }
            view.refresh(users);
            setSelectedCodeUser(user.getCodeUser());
        }
    }//fim do método update
}//fim da classe ManagerUserController

