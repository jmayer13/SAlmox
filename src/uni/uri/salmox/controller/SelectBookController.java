/*- 
 * Classname:             SelectBookController.java 
 * 
 * Version information:   1.0 
 * 
 * Date:                  11/12/2013 - 13:52:11 
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
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.ResultSearchBookTableModel;
import uni.uri.salmox.view.SelectBox;

/**
 * Controle de seleção de livro
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectBookController implements SubjectInterface {

    //observadores
    private List<ObserverInterface> observers;
    //interface do DAO das categorias
    private CategoryBoxDAOInterface boxDAO;
    //visão
    private SelectBox view;
    //categoria
    private Category category;
    //caixa
    private Box box;

    /**
     * Contrutor com categoria e caixa
     *
     * @param category
     * @param box
     */
    public SelectBookController(Category category, Box box) {
        this.category = category;
        this.box = box;
        observers = new ArrayList();
        //cria a instancia de DAO dependento da categoria
        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();

        //busca livros 
        Iterator iterator = boxDAO.searchBooks(box.getCodeBox());
        List<Book> books = new ArrayList();
        while (iterator.hasNext()) {
            books.add((Book) iterator.next());
        }
        List<Box> boxes = new ArrayList();
        boxes.add(box);
        ResultSearchBookTableModel tableModel = new ResultSearchBookTableModel(boxes, books);

        //inicializa view
        view = new SelectBox(tableModel);
        //define eventos
        //adicionar
        view.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        //cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //abrir
        view.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBook();
            }
        });
        //fecha janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

    }//fim do construtor

    /**
     * Adiciona livro a lista
     */
    private void addBook() {
        List<Book> books = view.getBooksSelected();
        if (books.isEmpty()) {
            if (view.getBookSelected() != null) {
                books.add(view.getBookSelected());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (!books.isEmpty()) {
            close();
            notifyObservers(false, books);
        }

    }//fim do método addBook

    /**
     * Abre livro
     */
    private void openBook() {
        Book book = view.getBookSelected();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            SelectDocumentController selectDocumentController = new SelectDocumentController(category, box, book);
            for (int i = 0; i < observers.size(); i++) {
                selectDocumentController.registerObserver(observers.get(i));
            }
            close();
        }
    }//fim do método openBook

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
}//fim da classe SelectBookController 

