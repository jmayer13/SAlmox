/*-
 * Classname:             WithdrawalController.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 13:36:12
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
import uni.uri.salmox.view.WithdrawalDocumentFrame;

/**
 * Controle de retirada de documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalController implements ObserverInterface, SubjectInterface {

    //visão
    public WithdrawalDocumentFrame view;
    //lista de observadores
    private List<ObserverInterface> observers;
    //observador
    private ObserverInterface observer;
    //solicitação
    private Request request;

    /**
     * Construtor com caixa
     *
     * @param category categoria
     * @param box caixa
     */
    public WithdrawalController(Category category, Box box) {
        start();
        addBox(category, box);
    }//fim do contrutor de caixa

    /**
     * Contrutor com livro
     *
     * @param category categoria
     * @param book livro
     */
    public WithdrawalController(Category category, Book book) {
        start();
        addBook(category, book);
    }//fim do construtor de livro

    /**
     * Construtor com documento
     *
     * @param document documento
     */
    public WithdrawalController(Document document) {
        start();
        addDocument(document);
    }//fim do construtor de documento

    /**
     * Construtor sem parâmetro
     */
    public WithdrawalController() {
        start();
    }//fim do método WithdrawalController

    /**
     * Construtor com requisição
     *
     * @param request requisiç~~ao
     */
    public WithdrawalController(Request request) {
        this.request = request;
        start();
        RequestDAO requestDAO = new RequestDAO();
        Iterator iterator = requestDAO.getDocuments(request.getCodeRequest());
        while (iterator.hasNext()) {
            Document document = (Document) iterator.next();
            if (document.getPresent()) {
                addDocument(document);
            } else {
                JOptionPane.showMessageDialog(null, "Documento " + document.getTitleDocument() + "não está presente!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//fim do métodoconstrutor

    /**
     * Inicializa visão e define eventos
     */
    private void start() {
        observers = new ArrayList();
        //inicializa visão
        view = new WithdrawalDocumentFrame();
        //define eventos
        //apagar
        view.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Document document = view.getSelectedDocument();
                if (document != null) {
                    view.eraseDocument(document);

                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um item!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //buscar
        view.setActionListenerSearchButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        //cancelar-fechar
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        //teclado
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    close();
                }
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    ok();
                }
            }
        });
    }//fim do método start

    /**
     * Salva retirada
     */
    private void ok() {
        List<Document> documents = view.getDocuments();
        if (!documents.isEmpty()) {
            WithdrawalGenerateController w = new WithdrawalGenerateController(documents);
            if (request != null) {
                w.setRequest(request);
            }
            w.registerObserver(this);
            w.notifyObservers(true, null);
        }
    }//fim do método ok

    /**
     * Fecha a janela
     */
    private void close() {
        view.close();
        notifyObservers(false, null);
    }//fim do método close

    /**
     * Busca
     */
    private void search() {
        SearchController searchController = new SearchController();
        searchController.registerObserver(this);
        searchController.update(true, null);
        searchController.setOpenedWithdrawal(true);
    }//fim do método search

    /**
     * Adiciona caixa
     *
     * @param category categoria
     * @param box caixa
     */
    public void addBox(Category category, Box box) {
        List<Book> books = new ArrayList();
        List<Document> documents = new ArrayList();
        Iterator iteratorBooks;
        Iterator iteratorDocuments;
        DAOFactory daoFactory = new DAOFactory(category);
        CategoryBoxDAOInterface categoryDAO = daoFactory.getDAO();
        iteratorBooks = categoryDAO.searchBooks(box.getCodeBox());
        while (iteratorBooks.hasNext()) {
            Book book = (Book) iteratorBooks.next();
            books.add(book);
            iteratorDocuments = categoryDAO.searchDocuments(book.getCodeBook());
            while (iteratorDocuments.hasNext()) {
                Document document = (Document) iteratorDocuments.next();
                documents.add(document);
            }
        }

        if (documents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não foram encontrados documentos na caixa " + box.getTitleBox() + "!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
        if (new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {

            for (int i = 0; i < documents.size(); i++) {
                Book book = null;
                for (int j = 0; j < books.size(); j++) {
                    if (books.get(j).getCodeBook() == documents.get(i).getCodeBook()) {
                        book = books.get(j);
                    }
                }
                view.addDocument(box, book, documents.get(i));
            }
        } else {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " foi descartada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do addBox

    /**
     * Adiciona livro
     *
     * @param category categoria
     * @param book livro
     */
    public void addBook(Category category, Book book) {
        List<Document> documents = new ArrayList();
        Iterator iterator;
        DAOFactory daoFactory = new DAOFactory(category);
        CategoryBoxDAOInterface categoryDAO = daoFactory.getDAO();
        iterator = categoryDAO.searchDocuments(book.getCodeBook());
        while (iterator.hasNext()) {
            Document document = (Document) iterator.next();
            documents.add(document);
        }

        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        Box box = genericBoxDAO.searchBox(book.getCodeBox());
        if (new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            for (int i = 0; i < documents.size(); i++) {
                view.addDocument(box, book, documents.get(i));
            }
        } else {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " foi descartada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método addBook

    /**
     * Adiciona documento
     *
     * @param document documento
     */
    public void addDocument(Document document) {
        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        Book book = genericBoxDAO.searchBook(document.getCodeBook());
        Box box = genericBoxDAO.searchBox(book.getCodeBox());
        if (new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            view.addDocument(box, book,
                    document);
        } else {
            JOptionPane.showMessageDialog(null, "A caixa " + box.getTitleBox() + " foi descartada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método addDocument

    /**
     * Atualiza o observador
     *
     * @param active ativo?
     * @param object objeto
     */
    public void update(boolean active, Object object) {
        view.setEnable(!active);
        if (!active) {
            notifyObservers(false, null);
            notifyObservers(true, null);
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
     * Recebe observador para todos os observados filhos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Registra observador
     *
     * @param observerInterface observador
     */
    public void registerObserver(ObserverInterface observerInterface) {
        observers.add(observerInterface);
    }//fim do método registerObserver

    /**
     * Remove observador da lista
     *
     * @param observerInterface observador
     */
    public void removeObserver(ObserverInterface observerInterface) {
        observers.remove(observerInterface);
    }//fim do método removeObserver

    /**
     * Atualiza observadores
     *
     * @param active estado do janela
     * @param object objeto
     */
    public void notifyObservers(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método notifyObservers
}//fim da classe WithdrawalController

