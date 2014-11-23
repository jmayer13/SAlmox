/*-
 * Classname:             AddDocumentController.java
 *
 * Version information:   1.0
 *
 * Date:                  05/06/2013 - 15:39:02
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
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.ADAO;
import uni.uri.salmox.DAO.AEDAO;
import uni.uri.salmox.DAO.CCDAO;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DACDAO;
import uni.uri.salmox.DAO.DADDAO;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.DGDAO;
import uni.uri.salmox.DAO.DSGDAO;
import uni.uri.salmox.DAO.FCDAO;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.SAEDAO;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentA;
import uni.uri.salmox.model.DocumentAE;
import uni.uri.salmox.model.DocumentCC;
import uni.uri.salmox.model.DocumentDAC;
import uni.uri.salmox.model.DocumentDAD;
import uni.uri.salmox.model.DocumentDG;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.model.DocumentDSG;
import uni.uri.salmox.model.DocumentEX;
import uni.uri.salmox.model.DocumentF;
import uni.uri.salmox.model.DocumentFC;
import uni.uri.salmox.model.DocumentFP;
import uni.uri.salmox.model.DocumentMC;
import uni.uri.salmox.model.DocumentSAE;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.PreviouslyRegisteredException;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.AddDocumentAEFrame;
import uni.uri.salmox.view.AddDocumentAFrame;
import uni.uri.salmox.view.AddDocumentCCFrame;
import uni.uri.salmox.view.AddDocumentDACFrame;
import uni.uri.salmox.view.AddDocumentDADFrame;
import uni.uri.salmox.view.AddDocumentDGFrame;
import uni.uri.salmox.view.AddDocumentDPFrame;
import uni.uri.salmox.view.AddDocumentDSGFrame;
import uni.uri.salmox.view.AddDocumentEXFrame;
import uni.uri.salmox.view.AddDocumentFCFrame;
import uni.uri.salmox.view.AddDocumentFFrame;
import uni.uri.salmox.view.AddDocumentFPFrame;
import uni.uri.salmox.view.AddDocumentInterface;
import uni.uri.salmox.view.AddDocumentMCFrame;
import uni.uri.salmox.view.AddDocumentSAEFrame;

/**
 * Controle de adição/edição de documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddDocumentController implements SubjectInterface {

    //visão
    private AddDocumentInterface view;
    //DAO
    private CategoryBoxDAOInterface documentDAO;
    //observadores
    private List<ObserverInterface> observers;
    //categoria
    private Category category;
    //livro 
    private Book book;
    //código sugerido
    private int codeSugered;
    //documento
    private Document document;

    /**
     * Construtor para adição
     *
     * @param category categoria
     * @param codeSugered código sugerido
     * @param book livro
     */
    public AddDocumentController(Category category, int codeSugered, Book book) {
        //inicializa lista de observadores
        observers = new ArrayList();
        //pega variaveis
        this.category = category;
        this.codeSugered = codeSugered;
        this.book = book;
        //cria visão
        createView();
        //define eventos
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
        //cancelar
        view.setActionListenetCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        //fechar janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    addDocument();
                }
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    cancel();
                }
            }
        });
    }//fim do construtor para adição

    /**
     * Construtor para edição
     *
     * @param category categoria
     * @param document documento
     * @param book livro
     */
    public AddDocumentController(Category category, Document document, Book book) {
        //inicializa lista de observadores
        observers = new ArrayList();
        //categoria
        this.category = category;
        //livro
        this.book = book;
        //documento
        this.document = document;
        //cria visão
        createView();
        //definição de eventos
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDocument();
            }
        });
        //cancelar
        view.setActionListenetCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        //fechar a janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    editDocument();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });
    }//fim do construtor para edição

    /**
     * Cria visão
     */
    private void createView() {
        DAOFactory dAOFactory = new DAOFactory(category);
        documentDAO = dAOFactory.getDAO();

        if (document == null) {
            if (category == Category.A) {
                view = new AddDocumentAFrame(codeSugered, book.getTitleBook());
                ((AddDocumentAFrame) view).setTitleDocument(new ADAO().listTitles());

            } else if (category == Category.AE) {
                view = new AddDocumentAEFrame(codeSugered, book.getTitleBook());
                ((AddDocumentAEFrame) view).setTitleDocument(new AEDAO().listDiciplines());
            } else if (category == Category.CC) {
                view = new AddDocumentCCFrame(codeSugered, book.getTitleBook());
                ((AddDocumentCCFrame) view).setTitleDocument(new CCDAO().listTitles());
            } else if (category == Category.DP) {
                view = new AddDocumentDPFrame(codeSugered, book.getTitleBook());
            } else if (category == Category.EX) {
                view = new AddDocumentEXFrame(codeSugered, book.getTitleBook());
            } else if (category == Category.DAD) {
                view = new AddDocumentDADFrame(codeSugered, book.getTitleBook());
                ((AddDocumentDADFrame) view).setTitleDocument(new DADDAO().listDocumentsTitles());

            } else if (category == Category.DAC) {
                view = new AddDocumentDACFrame(codeSugered, book.getTitleBook());
                ((AddDocumentDACFrame) view).setTitleDocument(new DACDAO().listDocumentsTitles());

            } else if (category == Category.DG) {
                view = new AddDocumentDGFrame(codeSugered, book.getTitleBook());
                ((AddDocumentDGFrame) view).setTitleDocument(new DGDAO().listDocumentsTitles());

            } else if (category == Category.DSG) {
                view = new AddDocumentDSGFrame(codeSugered, book.getTitleBook());
                ((AddDocumentDSGFrame) view).setTitleDocument(new DSGDAO().listDocumentsTitles());

            } else if (category == Category.F) {
                view = new AddDocumentFFrame(codeSugered, book.getTitleBook());
            } else if (category == Category.FC) {
                view = new AddDocumentFCFrame(codeSugered, book.getTitleBook());
                ((AddDocumentFCFrame) view).setTitleDocument(new FCDAO().listDocumentsTitles());

            } else if (category == Category.FP) {
                view = new AddDocumentFPFrame(codeSugered, book.getTitleBook());
            } else if (category == Category.MC) {
                view = new AddDocumentMCFrame(codeSugered, book.getTitleBook());
            } else if (category == Category.SAE) {
                view = new AddDocumentSAEFrame(codeSugered, book.getTitleBook());
                ((AddDocumentSAEFrame) view).setTitleDocument(new SAEDAO().listDocTitles());

            }
        } else {
            if (category == Category.A) {
                view = new AddDocumentAFrame((DocumentA) document, book.getTitleBook());
                ((AddDocumentAFrame) view).setTitleDocument(new ADAO().listTitles());
            } else if (category == Category.AE) {
                view = new AddDocumentAEFrame((DocumentAE) document, book.getTitleBook());
                ((AddDocumentAEFrame) view).setTitleDocument(new AEDAO().listDiciplines());
            } else if (category == Category.CC) {
                view = new AddDocumentCCFrame((DocumentCC) document, book.getTitleBook());
                ((AddDocumentCCFrame) view).setTitleDocument(new CCDAO().listTitles());
            } else if (category == Category.DP) {
                view = new AddDocumentDPFrame((DocumentDP) document, book.getTitleBook());
            } else if (category == Category.DAC) {
                view = new AddDocumentDACFrame((DocumentDAC) document, book.getTitleBook());
                ((AddDocumentDACFrame) view).setTitleDocument(new DACDAO().listDocumentsTitles());

            } else if (category == Category.DAD) {
                view = new AddDocumentDADFrame((DocumentDAD) document, book.getTitleBook());
                ((AddDocumentDADFrame) view).setTitleDocument(new DADDAO().listDocumentsTitles());

            } else if (category == Category.DG) {
                view = new AddDocumentDGFrame((DocumentDG) document, book.getTitleBook());
                ((AddDocumentDGFrame) view).setTitleDocument(new DGDAO().listDocumentsTitles());

            } else if (category == Category.DSG) {
                view = new AddDocumentDSGFrame((DocumentDSG) document, book.getTitleBook());
                ((AddDocumentDSGFrame) view).setTitleDocument(new DSGDAO().listDocumentsTitles());


            } else if (category == Category.EX) {
                view = new AddDocumentEXFrame((DocumentEX) document, book.getTitleBook());
            } else if (category == Category.F) {
                view = new AddDocumentFFrame((DocumentF) document, book.getTitleBook());
            } else if (category == Category.FC) {
                view = new AddDocumentFCFrame((DocumentFC) document, book.getTitleBook());
                ((AddDocumentFCFrame) view).setTitleDocument(new FCDAO().listDocumentsTitles());

            } else if (category == Category.FP) {
                view = new AddDocumentFPFrame((DocumentFP) document, book.getTitleBook());
            } else if (category == Category.MC) {
                view = new AddDocumentMCFrame((DocumentMC) document, book.getTitleBook());
            } else if (category == Category.SAE) {
                view = new AddDocumentSAEFrame((DocumentSAE) document, book.getTitleBook());
                ((AddDocumentSAEFrame) view).setTitleDocument(new SAEDAO().listDocTitles());
            }

        }
    }//fim do método createView

    /**
     * Adiciona documento
     */
    private void addDocument() {
        //busca documento da visão
        Document document = view.getDocument();
        //testa documento
        if (document != null) {
            try {
                document.setCodeBook(book.getCodeBook());
                //registra documento e fecha visão
                documentDAO.registerDocument(document);
                view.close();
                //notifica observadores
                notifyObservers(false, document);
            } catch (PreviouslyRegisteredException exception) {
                JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento! Código já existente!", "Erro!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//fim do método addDocument

    /**
     * Edita documemto
     */
    private void editDocument() {
        //paga documento da visão
        Document document = view.getDocument();
        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        Box box = genericBoxDAO.searchBox(book.getCodeBox());
        if (!genericBoxDAO.checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }    //testa documento
        if (document != null) {
            document.setCodeBook(this.document.getCodeBook());
            document.setPresent(this.document.getPresent());
            document.setCodeDocument(document.getCodeDocument());
            if (documentDAO.checkDocumentCode(this.document, document)) {
                //edita documento e fecha visão
                documentDAO.editDocument(document);
                view.close();
                //notifica observadores
                notifyObservers(false, document);
            } else {
                JOptionPane.showMessageDialog(null, "O código já está cadastrado!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//fim do método editDocument

    /**
     * Cancela o cadastro/edição da caixa
     */
    private void cancel() {
        view.close();
        notifyObservers(false, null);
    }//fim do método cancel

    /**
     * Fecha janela
     */
    private void close() {
        int response = JOptionPane.showConfirmDialog(null, "Os novos dados serão perdidos! Deseja continuar? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == 0) {
            cancel();
        }
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
}//fim da classe AddDocumentController

