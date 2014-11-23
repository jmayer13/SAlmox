/*- 
 * Classname:             SelectBoxController.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  10/12/2013 - 16:24:20 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.ResultSearchBoxTableModel;
import uni.uri.salmox.view.SelectBox;

/**
 * Controle para seleção de caixa multifuncional
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectBoxController implements SubjectInterface {

    //observadores
    private List<ObserverInterface> observers;
    //visão
    private SelectBox view;
    //DAO
    private CategoryBoxDAOInterface boxDAO;
    //categoria
    private Category category;

    /**
     * Contrutor sem parâmetros
     */
    public SelectBoxController(Category category) {
        this.category = category;
        observers = new ArrayList();
        //cria a instancia de DAO dependento da categoria
        DAOFactory daoFactory = new DAOFactory(category);
        boxDAO = daoFactory.getDAO();
        //busca caixas
        Iterator iterator = boxDAO.searchBoxes();
        List boxes = new ArrayList();
        while (iterator.hasNext()) {
            boxes.add((Box) iterator.next());
        }
        ResultSearchBoxTableModel tableModel = new ResultSearchBoxTableModel(boxes);
        view = new SelectBox(tableModel);
        //define evento para o botão adicionar
        view.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBox();
            }
        });
        //define evneto para o botão cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //define evento para botão sair
        view.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBox();
            }
        });
        //define eventp para fechamento de janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }//fim do construtorsem parâmetros

    /**
     * Define se é seleção de caixas somente
     *
     * @param active flag de controle
     */
    public void onlyBoxes(boolean active) {
        view.setShowOpenButton(!active);
    }//fim do método onlyBoxes

    /**
     * Adiciona lista de caixas aos observers
     */
    private void addBox() {
        List<Box> boxes = view.getBoxesSelected();
        if (boxes.isEmpty()) {
            if (view.getBoxSelected() != null) {
                boxes.add(view.getBoxSelected());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (!boxes.isEmpty()) {
            close();
            notifyObservers(false, boxes);
        }
    }//fim do método addBox

    /**
     * Abre caixa
     */
    private void openBox() {
        Box box = view.getBoxSelected();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else {
            SelectBookController selectbookController = new SelectBookController(category, box);
            for (int i = 0; i < observers.size(); i++) {
                selectbookController.registerObserver(observers.get(i));
            }
            close();
        }
    }//fim do método openBox

    /**
     * Fecha janela
     */
    private void close() {
        view.close();
        notifyObservers(false, null);
    }//fim do método close

    /**
     * Registra observador
     *
     * @param observer observador
     */
    public void registerObserver(ObserverInterface observer) {
        observers.add(observer);
    }//fim do método registerObserver

    /**
     * Remove observador da lista
     *
     * @param observer observador
     */
    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }//fim do método removeObserver

    /**
     * Notifica os observadores das mudanças
     *
     * @param active janela ativa
     * @param object objeto de retorno
     */
    public void notifyObservers(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método notifyObservers
}//fim da classe SelectBoxController 

