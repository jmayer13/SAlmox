/*- 
 * Classname:             SelectDocumentController.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  12/12/2013 - 16:15:20 
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
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.ResultSearchDocumentTableModel;
import uni.uri.salmox.view.SelectBox;

/**
 * Controle para a seleção de documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectDocumentController implements SubjectInterface {

    //observadores
    private List<ObserverInterface> observers;
    //interface do DAO das categorias
    private CategoryBoxDAOInterface boxDAO;
    //visão
    private SelectBox view;

    /**
     * Contrutor com categoria, livro e caixa
     *
     * @param category categoria
     * @param box caixa
     * @param book livro
     */
    public SelectDocumentController(Category category, Box box, Book book) {
        observers = new ArrayList();
        //cria a instancia de DAO dependento da categoria
        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();


        //busca dados
        Iterator iterator = boxDAO.searchDocuments();
        List<Document> documents = new ArrayList();
        while (iterator.hasNext()) {
            documents.add((Document) iterator.next());
        }
        List<Box> boxes = new ArrayList();
        List<Book> books = new ArrayList();
        boxes.add(box);
        books.add(book);

        //inicializa view
        ResultSearchDocumentTableModel tableModel = new ResultSearchDocumentTableModel(boxes, books, documents);
        view = new SelectBox(tableModel);
        view.setShowOpenButton(false);
        view = new SelectBox(tableModel);
        //define eventos
        //adicionar
        view.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
        //cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //fechar janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }//fim do método construtor

    /**
     * Adiciona documento
     */
    private void addDocument() {
        List<Document> documents = view.getDocumentsSelected();
        if (documents.isEmpty()) {
            if (view.getDocumentSelected() != null) {
                documents.add(view.getDocumentSelected());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um document!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (!documents.isEmpty()) {
            close();
            notifyObservers(false, documents);
        }
    }//fim do método addDcoument

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
}//fim da classe SelectDocumentController 

