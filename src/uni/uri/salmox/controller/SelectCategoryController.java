/*- 
 * Classname:             SelectCategoryController.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  12/12/2013 - 13:20:46 
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
import java.util.List;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.SelectCategoryFrame;

/**
 * Controle de seleção de categoria
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectCategoryController implements SubjectInterface {

    //observadores
    private List<ObserverInterface> observers;
    //visão
    private SelectCategoryFrame view;

    /**
     * Método contrutor sem parâmtros
     */
    public SelectCategoryController() {

        observers = new ArrayList();

        //cria lista de categorias
        List<Category> categories = new ArrayList();
        categories.add(Category.A);
        categories.add(Category.AE);
        categories.add(Category.CC);
        categories.add(Category.DAC);
        categories.add(Category.DAD);
        categories.add(Category.DG);
        categories.add(Category.DP);
        categories.add(Category.DSG);
        categories.add(Category.EX);
        categories.add(Category.F);
        categories.add(Category.FC);
        categories.add(Category.FP);
        categories.add(Category.MC);
        categories.add(Category.SAE);

        //inicializa visão com listasde categorias
        view = new SelectCategoryFrame(categories);

        //define eventos
        //ok
        view.setOkButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //pega categoria e inicializa controle de seleção de caixa
                Category category = view.getSelectedCategory();
                if (category != null) {
                    SelectBoxController selectBoxController = new SelectBoxController(category);
                    selectBoxController.onlyBoxes(true);
                    for (int i = 0; i < observers.size(); i++) {
                        selectBoxController.registerObserver(observers.get(i));
                    }
                    view.close();

                }
            }
        });
        //cancelar
        view.setCancelButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.close();
                notifyObservers(false, null);
            }
        });
        //fechar janela
        view.setFrameWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                view.close();
                notifyObservers(false, null);
            }
        });
    }//fim do método construtor

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
}//fim da classe SelectCategoryController 

