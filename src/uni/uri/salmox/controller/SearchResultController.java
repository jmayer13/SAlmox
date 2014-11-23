/*- 
 * Classname:             SearchResultController.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  31/07/2013 - 14:31:51 
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
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.ResultSearchBookTableModel;
import uni.uri.salmox.view.ResultSearchBoxTableModel;
import uni.uri.salmox.view.ResultSearchDocumentTableModel;
import uni.uri.salmox.view.SearchResultFrame;

/**
 * Controle de resultados de buscas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SearchResultController implements SubjectInterface, ObserverInterface {

    //visão
    private SearchResultFrame view;
    //lista de observadores
    private List<ObserverInterface> observers;
    //atualizar somente
    private boolean updateOnly = false;

    /**
     * Costrutor com modelo de caixa
     *
     * @param model modelo da tabela de caixa
     */
    public SearchResultController(ResultSearchBoxTableModel model) {
        //inicializa visão
        view = new SearchResultFrame(model);
        observers = new ArrayList();
        //define evento baseado no tipo de usuário
        if (TemporaryData.getUser() instanceof Administrator) {
            view.setActionListenerEditButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editBox();
                }
            });
            view.setActionListenerWithdrawButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (updateOnly) {
                        List<Box> boxes = view.getBoxesSelected();
                        if (boxes != null && !boxes.isEmpty()) {
                            for (int i = 0; i < boxes.size(); i++) {
                                notifyObservers(false, boxes.get(i));
                            }
                            view.close();
                        } else {
                            Box box = view.getBoxSelected();
                            if (box != null) {
                                notifyObservers(false, box);
                                view.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        withdrawalBox();
                    }
                }
            });
        } else {
            view.setActionListenerRequestButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (updateOnly) {
                        List<Box> boxes = view.getBoxesSelected();
                        if (boxes != null && !boxes.isEmpty()) {
                            for (int i = 0; i < boxes.size(); i++) {
                                notifyObservers(false, boxes.get(i));
                            }
                            view.close();
                        } else {
                            Box box = view.getBoxSelected();
                            if (box != null) {
                                notifyObservers(false, box);
                                view.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        requestBox();
                    }
                }
            });
        }
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }//fim do construtor de caixa

    /**
     * Construtor com modelo de livro
     *
     * @param model modelo de tabela de livro
     */
    public SearchResultController(ResultSearchBookTableModel model) {

        //inicializa visão
        view = new SearchResultFrame(model);
        observers = new ArrayList();
        //define evento baseado no tipo de usuário
        if (TemporaryData.getUser() instanceof Administrator) {
            view.setActionListenerEditButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editBook();
                }
            });
            view.setActionListenerWithdrawButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (updateOnly) {
                        List<Book> books = view.getBooksSelected();
                        if (books != null && !books.isEmpty()) {
                            for (int i = 0; i < books.size(); i++) {
                                notifyObservers(false, books.get(i));
                            }
                            view.close();
                        } else {
                            Book book = view.getBookSelected();
                            if (book != null) {
                                notifyObservers(false, book);
                                view.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        withdrawalBook();
                    }
                }
            });
        } else {
            view.setActionListenerRequestButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (updateOnly) {
                        List<Book> books = view.getBooksSelected();
                        if (books != null && !books.isEmpty()) {
                            for (int i = 0; i < books.size(); i++) {
                                notifyObservers(false, books.get(i));
                            }
                            view.close();
                        } else {
                            Book book = view.getBookSelected();
                            if (book != null) {
                                notifyObservers(false, book);
                                view.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        requestBook();
                    }
                }
            });
        }
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }//fim do construtor de livro

    /**
     * Construtor com modelo de documento
     *
     * @param model modelo de tabela de documento
     */
    public SearchResultController(ResultSearchDocumentTableModel model) {
        view = new SearchResultFrame(model);
        observers = new ArrayList();
        if (TemporaryData.getUser() instanceof Administrator) {
            view.setActionListenerEditButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editDocument();
                }
            });
            view.setActionListenerWithdrawButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (updateOnly) {
                        List<Document> documents = view.getDocumentsSelected();
                        if (documents != null && !documents.isEmpty()) {
                            for (int i = 0; i < documents.size(); i++) {
                                notifyObservers(false, documents.get(i));
                            }
                            view.close();
                        } else {
                            Document document = view.getDocumentSelected();
                            if (document != null) {
                                notifyObservers(false, document);
                                view.close();
                            } else {
                                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        withdrawalDocument();
                    }
                }
            });
        } else {
            view.setActionListenerRequestButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    requestDocument();
                }
            });
        }
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }//fim do construtor de documento

    /**
     * Fecha a view
     */
    private void close() {
        view.close();
        notifyObservers(false, null);
    }//fim do método close

    /**
     * Retira documento
     */
    private void withdrawalDocument() {
        List<Document> documents = view.getDocumentsSelected();
        Document document = view.getDocumentSelected();
        if ((documents == null || documents.isEmpty()) && document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        WithdrawalController withdrawalController = new WithdrawalController();
        for (int i = 0; i < observers.size(); i++) {
            withdrawalController.registerObserver(observers.get(i));
        }
        if (documents != null && !documents.isEmpty()) {
            for (int i = 0; i < documents.size(); i++) {
                withdrawalController.addDocument(documents.get(i));
            }
            view.close();
        } else {
            if (document != null) {
                withdrawalController.addDocument(document);
                view.close();
            }
        }
    }//fim do método withdrawalDocument

    /**
     * Solicita documento
     */
    private void requestDocument() {
        List<Document> documents = view.getDocumentsSelected();
        Document document = view.getDocumentSelected();
        if ((documents == null || documents.isEmpty()) && document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        RequestController requestController = new RequestController();
        for (int i = 0; i < observers.size(); i++) {
            requestController.registerObserver(observers.get(i));
        }
        if (documents != null && !documents.isEmpty()) {
            for (int i = 0; i < documents.size(); i++) {
                requestController.addDocument(documents.get(i));
            }
            view.close();
        } else {
            if (document != null) {
                requestController.addDocument(document);
                view.close();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método requestDocument

    /**
     * Retira livro
     */
    private void withdrawalBook() {
        List<Book> books = view.getBooksSelected();
        Book book = view.getBookSelected();
        if ((books == null || books.isEmpty()) && book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        WithdrawalController withdrawalController = new WithdrawalController();
        for (int i = 0; i < observers.size(); i++) {
            withdrawalController.registerObserver(observers.get(i));
        }
        if (books != null && !books.isEmpty()) {
            for (int i = 0; i < books.size(); i++) {
                withdrawalController.addBook(Category.getCategory(books.get(i)), books.get(i));
            }
            view.close();
        } else {
            if (book != null) {
                withdrawalController.addBook(Category.getCategory(book), book);
                view.close();
            }
        }
    }//fim do método withdrawalBook

    /**
     * Solicita livro
     */
    private void requestBook() {
        List<Book> books = view.getBooksSelected();
        Book book = view.getBookSelected();
        if ((books == null || books.isEmpty()) && book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        RequestController requestController = new RequestController();
        for (int i = 0; i < observers.size(); i++) {
            requestController.registerObserver(observers.get(i));
        }

        if (books != null && !books.isEmpty()) {
            for (int i = 0; i < books.size(); i++) {
                requestController.addBook(Category.getCategory(books.get(i)), books.get(i));
            }
            view.close();
        } else {
            if (book != null) {
                requestController.addBook(Category.getCategory(book), book);
                view.close();
            }
        }
    }//fim do método requestBook

    /**
     * Retira caixa
     */
    private void withdrawalBox() {
        List<Box> boxes = view.getBoxesSelected();
        Box box = view.getBoxSelected();
        if ((boxes == null || boxes.isEmpty()) && box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        WithdrawalController withdrawalController = new WithdrawalController();
        for (int i = 0; i < observers.size(); i++) {
            withdrawalController.registerObserver(observers.get(i));
        }
        if (boxes != null && !boxes.isEmpty()) {
            for (int i = 0; i < boxes.size(); i++) {
                withdrawalController.addBox(Category.getCategory(boxes.get(i)), boxes.get(i));
            }
            view.close();
        } else {
            if (box != null) {
                withdrawalController.addBox(Category.getCategory(box), box);
                view.close();
            }
        }
    }//fim do método withdrawalBox

    /**
     * Solicita caixa
     */
    private void requestBox() {
        List<Box> boxes = view.getBoxesSelected();
        Box box = view.getBoxSelected();
        if ((boxes == null || boxes.isEmpty()) && box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        RequestController requestController = new RequestController();
        for (int i = 0; i < observers.size(); i++) {
            requestController.registerObserver(observers.get(i));
        }
        if (boxes != null && !boxes.isEmpty()) {
            for (int i = 0; i < boxes.size(); i++) {
                requestController.addBox(Category.getCategory(new GenericBoxDAO().discoveryCategory(boxes.get(i))), boxes.get(i));
            }
            view.close();
        } else {
            if (box != null) {
                requestController.addBox(Category.getCategory(new GenericBoxDAO().discoveryCategory(box)), box);
                view.close();
            }
        }
    }//fim do método requestBox

    /**
     * Edita caixa
     */
    private void editBox() {
        Box box = view.getBoxSelected();
        if (box != null) {
            box = new GenericBoxDAO().discoveryCategory(box);
            AddBoxController addBoxController = new AddBoxController(Category.getCategory(box), box);
            addBoxController.registerObserver(this);
            addBoxController.notifyObservers(true, false);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método editBox

    /**
     * Edita livro
     */
    private void editBook() {
        Book book = view.getBookSelected();
        if (book != null) {
            GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
            Box box = genericBoxDAO.searchBox(book.getCodeBox());
            AddBookController addBookController = new AddBookController(Category.getCategory(book), book, box);
            addBookController.registerObserver(this);
            addBookController.notifyObservers(true, null);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método editBook

    /**
     * Edita documento
     */
    private void editDocument() {
        Document document = view.getDocumentSelected();
        if (document != null) {
            GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
            Book book = genericBoxDAO.searchBook(document.getCodeBook());
            AddDocumentController addDocumentController = new AddDocumentController(Category.getCategory(document), document, book);
            addDocumentController.registerObserver(this);
            addDocumentController.notifyObservers(true, null);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método editDocument

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

    /**
     * Define modo de selecao de caixas para exclusao
     */
    public void setDeleteMode() {
        view.changeToDelete();
        view.setActionListenerWithdrawButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Box> boxes = view.getBoxesSelected();
                if (boxes.isEmpty()) {
                    if (view.getBoxSelected() != null) {
                        boxes.add(view.getBoxSelected());
                    }
                }
                if (boxes.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    for (int i = 0; i < boxes.size(); i++) {
                        if (Category.getCategory(boxes.get(i)) == null) {
                            GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                            boxes.set(i, genericBoxDAO.discoveryCategory(boxes.get(i)));
                        }
                    }
                    close();
                    notifyObservers(false, boxes);
                }

            }
        });
    }//fim do método setDeleteMode

    /**
     * Atualiza observadores
     *
     * @param active se está ativo true=ativo false=desativado
     * @param object objeto a ser atualizado
     */
    public void update(boolean active, Object object) {
        view.enableFrame(!active);
        if (!active) {
            if (object instanceof Box) {
                view.replaceBox((Box) object);
            } else if (object instanceof Book) {
                view.replaceBook((Book) object);
            } else if (object instanceof Document) {
                view.replaceDocument((Document) object);
            }
        }
    }//fim do método update

    /**
     * Ordena que o controller apenas atualize sem abrir novas abas de retirada
     */
    public void justUpdate() {
        updateOnly = true;
    }//fim do método justUpdate
}//fim da classe SearchResultController 

