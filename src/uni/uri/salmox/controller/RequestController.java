/*-
 * Classname:             RequestController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:57:15
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.RequestDAO;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.RequestDocumentFrame;

/**
 * Controle de solicitações
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestController implements ObserverInterface, SubjectInterface {

    //visão
    private RequestDocumentFrame view;
    //lista de observadores
    private List<ObserverInterface> observers;

    /**
     * Método construtor com caixa
     *
     * @param category categoria
     * @param box caixa
     */
    public RequestController(Category category, Box box) {
        start();
        addBox(category, box);
    }//fim do método construtor com caixa

    /**
     * Método construtor com livro
     *
     * @param category categoria
     * @param book livro
     */
    public RequestController(Category category, Book book) {
        start();
        addBook(category, book);
    }//fim do método construtor com livro

    /**
     * Método construtor com documento
     *
     * @param document documento
     */
    public RequestController(Document document) {
        start();
        addDocument(document);
    }//fim do método construtor com documento

    /**
     * Método construtor sem parâmetro
     *
     */
    public RequestController() {
        start();
    }//fim do método construtor sem parâmetro

    /**
     * Inicializa visâo
     */
    private void start() {
        //inicializa lista de observadores
        observers = new ArrayList();
        view = new RequestDocumentFrame();
        //define eventos
        //remover
        view.setActionListenerRemoveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = view.getSelectedRow();
                if (rowIndex > 0) {
                    view.removeDocument(rowIndex);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        //solicitar
        view.setActionListenerRequestButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                request();
            }
        });
        //buscar
        view.setActionListenerSearchButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        //cancelar
        view.setActionListenetCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //fechar
        view.setCloseWindowListener(new WindowAdapter() {
            public void windowClosiing(WindowEvent e) {
                close();
            }
        });
        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    request();
                }
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    close();
                }
            }
        });

    }//fim do método star

    /**
     * Adiciona caixa a lista de solicitação
     *
     * @param categoria
     * @param box caixa
     */
    public void addBox(Category category, Box box) {
        List<Book> books = new ArrayList();
        List<Document> documents = new ArrayList();
        DAOFactory daoFactory = new DAOFactory(category);
        CategoryBoxDAOInterface CategoryDao = daoFactory.getDAO();
        Iterator iteratorBooks;
        Iterator iteratorDocuments;
        if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        iteratorBooks = CategoryDao.searchBooks(box.getCodeBox());
        while (iteratorBooks.hasNext()) {
            Book book = (Book) iteratorBooks.next();
            iteratorDocuments = CategoryDao.searchDocuments(book.getCodeBook());
            while (iteratorDocuments.hasNext()) {
                Document document = (Document) iteratorDocuments.next();
                if (document.getPresent()) {
                    books.add(book);
                    documents.add(document);
                }
            }
        }

        //percorre caixa e adiciona documentos
        for (int i = 0; i < documents.size(); i++) {
            view.addDocument(box, books.get(i), documents.get(i));
        }
    }//fim do método addBox

    /**
     * Faz solicitação dos documentos
     */
    private void request() {
        RequestDAO requestDAO = new RequestDAO();
        Request request = view.getRequest();
        if (request != null) {
            requestDAO.storeRequest(request);
            view.close();
            notifyObservers(false, request);
        }
    }//fim do método request

    /**
     * Busca mais documentos
     */
    private void search() {
        SearchController searchController = new SearchController();
        searchController.registerObserver(this);
        searchController.update(true, null);
        searchController.setOpenedWithdrawal(true);
    }//fim do método search

    /**
     * Fecha a janela
     */
    private void close() {
        view.close();
        notifyObservers(false, null);
    }//fim do método close

    /**
     * Adiciona livro a lista de solicitação
     *
     * @param category category
     * @param book livro
     */
    public void addBook(Category category, Book book) {
        List<Document> documents = new ArrayList();
        Iterator iterator;
        DAOFactory daoFactory = new DAOFactory(category);
        CategoryBoxDAOInterface CategoryDao = daoFactory.getDAO();
        iterator = CategoryDao.searchDocuments(book.getCodeBook());
        while (iterator.hasNext()) {
            Document document = (Document) iterator.next();
            if (document.getPresent()) {
                documents.add(document);
            }
        }


        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        Box box = genericBoxDAO.searchBox(book.getCodeBox());
        if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < documents.size(); i++) {
            view.addDocument(box, book, documents.get(i));
        }
    }//fim do método addBook

    /**
     * Adiciona docuento a lista
     *
     * @param document documento
     */
    public void addDocument(Document document) {
        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        Book book = genericBoxDAO.searchBook(document.getCodeBook());
        Box box = genericBoxDAO.searchBox(book.getCodeBox());
        if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        view.addDocument(box, book, document);
    }//fim do método addDocument

    /**
     * Atualiza lista
     *
     * @param active ativo?
     * @param object objeto a ser adicionado a lista
     */
    public void update(boolean active, Object object) {
        if (!active) {
            if (object instanceof Box) {
                Box box = (Box) object;
                box = new GenericBoxDAO().discoveryCategory(box);
                addBox(Category.getCategory(box), box);
            } else if (object instanceof Book) {
                Book book = (Book) object;
                Box box = new GenericBoxDAO().searchBox(book.getCodeBox());
                box = new GenericBoxDAO().discoveryCategory(box);
                addBook(Category.getCategory(box), book);
            } else if (object instanceof Document) {
                Document document = (Document) object;
                addDocument(document);
            } else if (object instanceof Withdrawal) {
                close();
            }
            view.requestFrameFocus();
        }
    }//fim do método update

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
}//fim da classe RequestController

