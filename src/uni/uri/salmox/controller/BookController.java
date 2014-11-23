/*-
 * Classname:             BookController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:49:46
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.view.BookPanel;

/**
 * Controle de livros
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookController implements ObserverInterface {

    //visão do livro
    private BookPanel bookPanel;
    //interface do DAO das categorias
    private CategoryBoxDAOInterface boxDAO;
    //enum categoria
    private Category category;
    //usuário
    private User user;
    //caixa
    private Box box;
    //listav de livros
    private List<Book> books;
    //observador
    private ObserverInterface observer;
    //código do livro selecionado
    private int selectedCodeBook = 0;

    /**
     * Construtor para usuário padrâo
     *
     * @param box caixa
     * @param category categoria da caixa
     * @param defaultUser usuário padrão
     */
    public BookController(Box box, Category category, DefaultUser defaultUser) {
        this.category = category;
        this.user = defaultUser;
        this.box = box;
        //busca dados
        searchData();

        //inicializa e popula view
        bookPanel = new BookPanel(category, books, box.getCodeClass() + box.getTitleBox(), user);

        //adiciona a eventos aos botões
        //abrir
        bookPanel.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                openBook();
            }
        });
        //voltar
        bookPanel.setActionListenerBackButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                backToBox();
            }
        });
        //solicitar
        bookPanel.setActionListenerRequestButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                requestBook();
            }
        });
        //abrir livro com mouse
        bookPanel.setMouseListenerTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    storeSelectedBook();
                    openBook();
                }
            }
        });
        //define evento de teclado para janela
        bookPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    storeSelectedBook();
                    backToBox();
                }
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    storeSelectedBook();
                    openBook();
                }
            }
        });

        //define evento para visualização de livro
        bookPanel.setVisualizeBookListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component object = e.getComponent();
                if (object instanceof JTable) {
                    JTable table = (JTable) object;

                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Book book = bookPanel.getBook(row);
                        DAOFactory daoFactory = new DAOFactory(Category.getCategory(book));
                        Book bookSpecific = daoFactory.getDAO().searchBook(book.getCodeBook());
                        table.setToolTipText(bookSpecific.toString());
                    } else {
                        table.setToolTipText("");
                    }
                }
            }
        });

    }//fim do método construtor para usuários padrão

    /**
     * Contrutor para administrador
     *
     * @param box caixa
     * @param category categoria da caixa
     * @param administrator administrador
     */
    public BookController(Box box, Category category, Administrator administrator) {
        this.category = category;
        this.user = administrator;
        this.box = box;
        //busca dados
        searchData();
        //inicializa e popula view
        bookPanel = new BookPanel(category, books, box.getCodeClass() + box.getTitleBox(), user);
        //adiciona eventos aos botões
        //abrir
        bookPanel.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                openBook();
            }
        });
        //voltar
        bookPanel.setActionListenerBackButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                backToBox();
            }
        });
        //adicionar
        bookPanel.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                addBook();
            }
        });
        //editar
        bookPanel.setActionListenerEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                editBook();
            }
        });
        //deletar
        bookPanel.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                eraseBook();
            }
        });
        //retirar
        bookPanel.setActionListenerWithdrawalButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                withdrawalBook();
            }
        });
        //subir posição do livro
        bookPanel.setActionListenerUPButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                up();
                restoreSelectedBook();
            }
        });
        //descer posição do livro
        bookPanel.setActionListenerDownButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                down();
                restoreSelectedBook();
            }
        });
        //ordenar livro
        bookPanel.setActionListenerSortButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBook();
                sort();
                restoreSelectedBook();
            }
        });
        //abrir livro com mouse
        bookPanel.setMouseListenerTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    storeSelectedBook();
                    openBook();
                }
            }
        });
        //defin eventos de teclado para janelas
        bookPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    storeSelectedBook();
                    backToBox();
                }
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    storeSelectedBook();
                    openBook();
                }
                if (KeyEvent.VK_DELETE == e.getKeyCode()) {
                    storeSelectedBook();
                    eraseBook();
                }
            }
        });
        //define evento para visualização de livro
        bookPanel.setVisualizeBookListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component object = e.getComponent();
                if (object instanceof JTable) {
                    JTable table = (JTable) object;

                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Book book = bookPanel.getBook(row);
                        DAOFactory daoFactory = new DAOFactory(Category.getCategory(book));
                        Book bookSpecific = daoFactory.getDAO().searchBook(book.getCodeBook());
                        table.setToolTipText(bookSpecific.toString());
                    } else {
                        table.setToolTipText("");
                    }
                }
            }
        });

    }//fim do construtor para administradores

    /**
     * Busca dados
     */
    private void searchData() {
        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();
        //busca caixas 
        Iterator iterator = boxDAO.searchBooks(box.getCodeBox());
        books = new ArrayList();
        while (iterator.hasNext()) {
            books.add((Book) iterator.next());
        }
    }//fim do método searchData

    /**
     * Abre o livro
     */
    private void openBook() {
        //pega livro da view
        Book book = bookPanel.getSelectedBook();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //abre controller pde documetos
            DocumentController documentController;
            if (user instanceof Administrator) {
                documentController = new DocumentController(box, book, category, (Administrator) user);
            } else {
                documentController = new DocumentController(box, book, category, (DefaultUser) user);
            }
            documentController.registerAllObservers(observer);
            observer.update(false, documentController.getPanel());
        }
    }//fim do método openBook

    /**
     * Volta para a página anterior
     */
    private void backToBox() {
        //volta para caixa
        BoxController boxController;
        if (user instanceof Administrator) {
            boxController = new BoxController(category, (Administrator) user);

        } else {
            boxController = new BoxController(category, (DefaultUser) user);
        }
        boxController.registerAllObservers(observer);
        boxController.setSelectedCodeBox(box.getCodeBox());
        observer.update(false, boxController.getPanel());
    }//fim do método backToBox

    /**
     * Solicita livro
     */
    private void requestBook() {

        //solicita documentos do livro
        //pega livro da caixa
        Book book = bookPanel.getSelectedBook();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

        } else {
            //verifica se há algum documento presente
            boolean present = false;
            Iterator documents = boxDAO.searchDocuments(book.getCodeBook());
            while (documents.hasNext() && !present) {
                if (((Document) documents.next()).getPresent()) {
                    present = true;
                }
            }
            if (present) {
                RequestController requestController = new RequestController(category, book);
                requestController.registerObserver(observer);
                requestController.notifyObservers(true, null);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum documento do livro está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método requestBook

    /**
     * Adiciona livro
     */
    private void addBook() {
        AddBookController addBookController;
        if (books != null && !books.isEmpty()) {
            Book book = books.get(books.size() - 1);
            Category category = Category.getCategory(box);
            addBookController = new AddBookController(category, book.getCodeBookSpecific() + 1, box);


        } else {
            addBookController = new AddBookController(category, 1, box);
        }
        addBookController.registerObserver(observer);
        addBookController.registerObserver(this);
        addBookController.notifyObservers(true, null);
    }//fim do método addBook

    /**
     * Edita livro
     */
    private void editBook() {

        Book book = bookPanel.getSelectedBook();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddBookController addBookController = new AddBookController(category, book, box);
            addBookController.registerObserver(observer);
            addBookController.registerObserver(this);
            addBookController.notifyObservers(true, null);
        }
    }//fim do método editBook

    /**
     * Deleta livro
     */
    private void eraseBook() {
        Book book = bookPanel.getSelectedBook();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int response = JOptionPane.showConfirmDialog(null, "Deseja excluir livro? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == 0) {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                genericBoxDAO.deleteBook(book);
                update(false, null);
            }
        }
    }//fim do método eraseBook

    /**
     * Retira livro
     */
    private void withdrawalBook() {
        if (!new GenericBoxDAO().checkActiveBox(this.box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Book book = bookPanel.getSelectedBook();
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean present = false;
            Iterator documents = boxDAO.searchDocuments(book.getCodeBook());
            while (documents.hasNext() && !present) {
                if (((Document) documents.next()).getPresent()) {
                    present = true;
                }
            }
            if (present) {
                WithdrawalController withdrawalController = new WithdrawalController(category, book);
                withdrawalController.registerAllObservers(observer);
                withdrawalController.notifyObservers(true, null);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum documento do livro está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método withdrawalBook

    /**
     * Sobe o livro (diminui o código em 1)
     */
    private void up() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não]   </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Book bookUp = bookPanel.getSelectedBook();
                if (bookUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int code;
                code = bookUp.getCodeBookSpecific();
                if (code > 1) {
                    for (int i = 0; i < books.size(); i++) {
                        if (books.get(i).getCodeBookSpecific() == (code - 1)) {
                            boxDAO.changeCodeBook(books.get(i).getCodeBook(), code);
                        }
                    }
                    boxDAO.changeCodeBook(bookUp.getCodeBook(), code - 1);
                }



                searchData();
                bookPanel.refresh(books);
            }
        }
    }//fim do método up

    /**
     * Abaixa o livro (diminui o código em 1)
     */
    private void down() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não] </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Book bookUp = bookPanel.getSelectedBook();
                if (bookUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um livro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int code;
                code = (bookUp).getCodeBookSpecific();
                if (code > 0) {
                    for (int i = 0; i < books.size(); i++) {
                        if (books.get(i).getCodeBookSpecific() == (code + 1)) {
                            boxDAO.changeCodeBook(books.get(i).getCodeBook(), code);
                        }
                    }
                    boxDAO.changeCodeBook(bookUp.getCodeBook(), code + 1);
                }


                searchData();
                bookPanel.refresh(books);
            }
        }
    }//fim do método down

    /**
     * Ordena códigos por uma ordem especifica dererminada pelo tipo de caixa
     */
    private void sort() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não]  </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                List<Book> tempBooks = new ArrayList();
                for (int i = 0; i < books.size(); i++) {
                    tempBooks.add(books.get(i));
                }
                Collections.sort(tempBooks, Book.forTitle);
                for (int i = 0; i < tempBooks.size(); i++) {
                    tempBooks.get(i).setCodeBookSpecific(i + 1);
                }
                for (int i = 0; i < tempBooks.size(); i++) {
                    for (int j = 0; j < books.size(); j++) {
                        if (tempBooks.get(i).getCodeBook() == books.get(j).getCodeBook()) {
                            boxDAO.changeCodeBook(books.get(j).getCodeBook(), tempBooks.get(i).getCodeBookSpecific());
                        }
                    }
                }



            }
        }
        searchData();
        bookPanel.refresh(books);
    }//fim do método sort

    /**
     * Recebe observador para todos os observados filhos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Retorna o panel da visão
     *
     * @return <code>JPanel</code> visão
     */
    public JPanel getPanel() {
        return bookPanel.getPanel();
    }//fim do método getPanel

    /**
     * Armazenar livro selecionado
     */
    private void storeSelectedBook() {
        if (bookPanel.getSelectedBook() != null) {
            selectedCodeBook = bookPanel.getSelectedBook().getCodeBook();
        } else {
            selectedCodeBook = 0;
        }
    }//fim do método storeSelectedBox

    /**
     * Define código da caixa selecionada
     *
     * @param selectedCodeBook código da caixa selecionada
     */
    public void setSelectedCodeBook(int selectedCodeBook) {
        this.selectedCodeBook = selectedCodeBook;
        restoreSelectedBook();
    }//fim do método setSelectedCodeBook

    /**
     * Restaura livro selecionado
     */
    private void restoreSelectedBook() {
        bookPanel.setSelectedBook(selectedCodeBook);
    }//fim do método restoreSelectedBook

    /**
     * Atualiza quando solicitado pelo observer
     *
     * @param active
     * @param object
     */
    public void update(boolean active, Object object) {
        if (!active) {
            searchData();
            bookPanel.refresh(books);
            bookPanel.getPanel().requestFocus();
            if (object instanceof Book) {
                Book book = (Book) object;
                int codeBook = 0;
                for (int i = 0; i < books.size(); i++) {
                    if (book.getCodeBookSpecific() == books.get(i).getCodeBookSpecific()) {
                        codeBook = books.get(i).getCodeBook();
                    }
                }

                setSelectedCodeBook(codeBook);
            }
            restoreSelectedBook();
        }
    }//fim do método update
}//fim da classe BookController

