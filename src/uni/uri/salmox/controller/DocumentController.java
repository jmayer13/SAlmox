/*-
 * Classname:             DocumentController.java
 *
 * Version information:   1.0
 *
 * Date:                  04/06/2013 - 14:59:45
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
import uni.uri.salmox.view.DocumentPanel;

/**
 * Controle de documento
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentController implements ObserverInterface {

    //visão do documento
    private DocumentPanel documentPanel;
    //interface do DAO das categorias
    private CategoryBoxDAOInterface boxDAO;
    //enum categoria
    private Category category;
    //usuário
    private User user;
    //caixa
    private Box box;
    //livro
    private Book book;
    //observador
    private ObserverInterface observer;
    //lista de documentos
    private List<Document> documents;
    //código do documento selecionado
    private int selectedCodeDocument = 0;

    /**
     * Contrutor para usuário padrão
     *
     * @param box caixa
     * @param book livro
     * @param category categoria
     * @param defaultUser usuário
     */
    public DocumentController(Box box, Book book, Category category, DefaultUser defaultUser) {
        this.category = category;
        this.user = defaultUser;
        this.box = box;
        this.book = book;
        //busca dados
        searchData();
        //inicializa e popula view
        documentPanel = new DocumentPanel(category, documents, box.getCodeClass() + box.getTitleBox() + " > " + book.getTitleBook(), user);

        //define evemmtos para os botões
        //voltar
        documentPanel.setActionListenerBackButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                backToBook();
            }
        });
        //solicitar
        documentPanel.setActionListenerRequestButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                requestDocument();
            }
        });
        //evento de teclas
        documentPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    backToBook();
                }
            }
        });
        //define evento para visualização de documento
        documentPanel.setVisualizeDocumentListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component object = e.getComponent();
                if (object instanceof JTable) {
                    JTable table = (JTable) object;

                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Document document = documentPanel.getDocument(row);
                        DAOFactory daoFactory = new DAOFactory(Category.getCategory(document));
                        Document documentSpecific = daoFactory.getDAO().searchDocument(document.getCodeDocument());
                        table.setToolTipText(documentSpecific.toString());
                    } else {
                        table.setToolTipText("");
                    }
                }
            }
        });

    }//fim do construtor para administrador

    /**
     * Contrutor para Administrador
     *
     * @param box caixa
     * @param book livro
     * @param category categoria
     * @param administrator administrador
     */
    public DocumentController(Box box, Book book, Category category, Administrator administrator) {
        this.category = category;
        this.user = administrator;
        this.book = book;
        this.box = box;
        //busca dados
        searchData();

        //inicializa e popula view
        documentPanel = new DocumentPanel(category, documents, box.getCodeClass() + box.getTitleBox() + " > " + book.getTitleBook(), user);

        //voltar
        documentPanel.setActionListenerBackButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backToBook();
            }
        });
        //adicionar
        documentPanel.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                addDocument();
            }
        });
        //editar
        documentPanel.setActionListenerEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                editDocument();
            }
        });
        //apagar
        documentPanel.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                eraseDocument();
            }
        });
        //retirada
        documentPanel.setActionListenerWithdrawalButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                withdrawalDocument();
            }
        });
        //subir posição do documento
        documentPanel.setActionListenerUPButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                up();
                restoreSelectedDocument();
            }
        });
        //descer posição do documento
        documentPanel.setActionListenerDownButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                down();
                restoreSelectedDocument();
            }
        });
        //ordenar documento
        documentPanel.setActionListenerSortButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedDocument();
                sort();
                restoreSelectedDocument();
            }
        });
        //evento de teclas
        documentPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    backToBook();
                }
                if (KeyEvent.VK_DELETE == e.getKeyCode()) {
                    storeSelectedDocument();
                    eraseDocument();;
                }
            }
        });
    }//fim do construtor para administrador

    /**
     * Busca dados
     */
    private void searchData() {
        //cria a instancia de DAO dependento da categoria

        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();

        //busca caixas
        Iterator iterator = boxDAO.searchDocuments(book.getCodeBook());
        documents = new ArrayList();
        while (iterator.hasNext()) {
            documents.add((Document) iterator.next());
        }
    }//fim do método searchData

    /**
     * Voltar para o livro
     */
    private void backToBook() {
        BookController bookController;
        GenericBoxDAO genericBoxdao = new GenericBoxDAO();
        int codeBox = book.getCodeBox();
        if (user instanceof Administrator) {

            bookController = new BookController(boxDAO.searchBox(codeBox), category, (Administrator) user);
        } else {
            bookController = new BookController(boxDAO.searchBox(codeBox), category, (DefaultUser) user);
        }
        bookController.registerAllObservers(observer);
        bookController.setSelectedCodeBook(book.getCodeBook());
        observer.update(false, bookController.getPanel());
    }//fim do método backToBook

    /**
     * Solicita um documento
     */
    private void requestDocument() {
        Document document = documentPanel.getSelectedDocument();
        if (document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

        } else {
            if (document.getPresent()) {
                RequestController requestController = new RequestController(document);
                requestController.registerObserver(observer);
                requestController.notifyObservers(true, false);
            } else {
                JOptionPane.showMessageDialog(null, "Documento não está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método requestDocument

    /**
     * Adiciona um documento
     */
    private void addDocument() {
        AddDocumentController addDocumentController;
        if (documents != null && !documents.isEmpty()) {
            int nextCode = new GenericBoxDAO().countDocuments(book.getCodeBox()) + 1;
            Document document = documents.get(documents.size() - 1);
            Category category = Category.getCategory(document);
            addDocumentController = new AddDocumentController(category, nextCode, book);


        } else {
            int nextCode = new GenericBoxDAO().countDocuments(book.getCodeBox()) + 1;
            addDocumentController = new AddDocumentController(category, nextCode, book);
        }

        addDocumentController.registerObserver(observer);
        addDocumentController.registerObserver(this);
        addDocumentController.notifyObservers(true, false);
    }//fim do método addDocument

    /**
     * Edita documento
     */
    private void editDocument() {
        Document document = documentPanel.getSelectedDocument();
        if (document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddDocumentController addDocumentController = new AddDocumentController(category, document, book);
            addDocumentController.registerObserver(observer);
            addDocumentController.registerObserver(this);
            addDocumentController.notifyObservers(true, false);
        }
    }//fim do método editDocument

    /**
     * Apaga documento
     */
    private void eraseDocument() {
        Document document = documentPanel.getSelectedDocument();
        if (document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int response = JOptionPane.showConfirmDialog(null, "Deseja excluir livro? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == 0) {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                genericBoxDAO.deleteDocument(document);
                update(false, null);
            }
        }
    }//fim do método eraseDocument

    /**
     * Retirada de documento
     */
    private void withdrawalDocument() {
        Document document = documentPanel.getSelectedDocument();
        if (document == null) {
            JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

        } else {
            if (document.getPresent()) {
                WithdrawalController withdrawalController = new WithdrawalController(document);
                withdrawalController.registerAllObservers(observer);
                withdrawalController.notifyObservers(true, false);
            } else {
                JOptionPane.showMessageDialog(null, "Documento não está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método withdrawalDocument

    /**
     * Recebe observador para todos os observados filhos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Sobe o documento (diminui o código em 1)
     */
    private void up() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não] </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Document documentUp = documentPanel.getSelectedDocument();
                if (documentUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int code;
                code = documentUp.getCodeDocumentSpecific();
                if (code > 1) {
                    for (int i = 0; i < documents.size(); i++) {
                        if (documents.get(i).getCodeDocumentSpecific() == (code - 1)) {
                            boxDAO.changeCodeDocument(documents.get(i).getCodeDocument(), code);
                        }
                    }
                    boxDAO.changeCodeDocument(documentUp.getCodeDocument(), code - 1);
                }
                searchData();
                documentPanel.refresh(documents);
            }
        }
    }//fim do método up

    /**
     * Abaixa o documento (diminui o código em 1)
     */
    private void down() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não] </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Document documentUp = documentPanel.getSelectedDocument();
                if (documentUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um documento!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int code = documentUp.getCodeDocumentSpecific();
                if (code > 0) {
                    for (int i = 0; i < documents.size(); i++) {
                        if (documents.get(i).getCodeDocumentSpecific() == (code + 1)) {
                            boxDAO.changeCodeDocument(documents.get(i).getCodeDocument(), code);
                        }
                    }
                    boxDAO.changeCodeDocument(documentUp.getCodeDocument(), code + 1);
                }
                searchData();
                documentPanel.refresh(documents);
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
                //pega primeiro código de documentos
                int firstCode = 1000;
                for (int i = 0; i < documents.size(); i++) {
                    int codeDocumentI = documents.get(i).getCodeDocumentSpecific();
                    if (firstCode > codeDocumentI) {
                        firstCode = codeDocumentI;
                    }

                }
                List<Document> tempDocuments = new ArrayList();
                for (int i = 0; i < documents.size(); i++) {
                    tempDocuments.add(documents.get(i));
                }
                Collections.sort(tempDocuments, Document.forTitle);
                for (int i = 0; i < tempDocuments.size(); i++) {
                    tempDocuments.get(i).setCodeDocumentSpecific(firstCode + i);

                }
                for (int i = 0; i < tempDocuments.size(); i++) {
                    for (int j = 0; j < documents.size(); j++) {
                        if (tempDocuments.get(i).getCodeDocument() == documents.get(j).getCodeDocument()) {
                            boxDAO.changeCodeDocument(documents.get(j).getCodeDocument(), tempDocuments.get(i).getCodeDocumentSpecific());
                        }
                    }
                }
            }
        }
        searchData();
        documentPanel.refresh(documents);
    }//fim do método sort

    /**
     * Armazenar documento selecionado
     */
    private void storeSelectedDocument() {
        if (documentPanel.getSelectedDocument() != null) {
            selectedCodeDocument = documentPanel.getSelectedDocument().getCodeDocument();
        } else {
            selectedCodeDocument = 0;
        }
    }//fim do método storeSelectedDocument

    /**
     * Restaura documento selecionado
     */
    private void restoreSelectedDocument() {
        documentPanel.setSelectedDocument(selectedCodeDocument);
    }//fim do método restoreSelectedDocument

    /**
     * Obtêm panel da visão
     *
     * @return <code>JPanel</code> panel
     */
    public JPanel getPanel() {
        return documentPanel.getPanel();
    }//fim do método getPanel

    /**
     * Atualiza quando solicitado pelo observer
     *
     * @param active
     * @param object
     */
    public void update(boolean active, Object object) {
        if (!active) {
            searchData();
            documentPanel.refresh(documents);
            documentPanel.getPanel().requestFocus();
            if (object instanceof Document) {
                Document document = (Document) object;
                int codeDocument = 0;
                for (int i = 0; i < documents.size(); i++) {
                    if (document.getCodeDocumentSpecific() == documents.get(i).getCodeDocumentSpecific()) {
                        codeDocument = documents.get(i).getCodeDocument();
                    }
                }
                this.selectedCodeDocument = codeDocument;
            }

            restoreSelectedDocument();
        }
    }//fim do método update
}//fim da classe DocumentController

