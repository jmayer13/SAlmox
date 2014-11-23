/*-
 * Classname:             AddBookController.java
 *
 * Version information:   1.0
 *
 * Date:                  04/06/2013 - 15:36:39
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
import uni.uri.salmox.DAO.AEDAO;
import uni.uri.salmox.DAO.CCDAO;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DACDAO;
import uni.uri.salmox.DAO.DADDAO;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.DGDAO;
import uni.uri.salmox.DAO.DSGDAO;
import uni.uri.salmox.DAO.EXDAO;
import uni.uri.salmox.DAO.FCDAO;
import uni.uri.salmox.DAO.FDAO;
import uni.uri.salmox.DAO.FPDAO;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.SAEDAO;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.model.BookAE;
import uni.uri.salmox.model.BookCC;
import uni.uri.salmox.model.BookDAC;
import uni.uri.salmox.model.BookDAD;
import uni.uri.salmox.model.BookDG;
import uni.uri.salmox.model.BookDP;
import uni.uri.salmox.model.BookDSG;
import uni.uri.salmox.model.BookEX;
import uni.uri.salmox.model.BookF;
import uni.uri.salmox.model.BookFC;
import uni.uri.salmox.model.BookFP;
import uni.uri.salmox.model.BookMC;
import uni.uri.salmox.model.BookSAE;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.PreviouslyRegisteredException;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.AddBookAEFrame;
import uni.uri.salmox.view.AddBookAFrame;
import uni.uri.salmox.view.AddBookCCFrame;
import uni.uri.salmox.view.AddBookDACFrame;
import uni.uri.salmox.view.AddBookDADFrame;
import uni.uri.salmox.view.AddBookDGFrame;
import uni.uri.salmox.view.AddBookDPFrame;
import uni.uri.salmox.view.AddBookDSGFrame;
import uni.uri.salmox.view.AddBookEXFrame;
import uni.uri.salmox.view.AddBookFCFrame;
import uni.uri.salmox.view.AddBookFFrame;
import uni.uri.salmox.view.AddBookFPFrame;
import uni.uri.salmox.view.AddBookInterface;
import uni.uri.salmox.view.AddBookMCFrame;
import uni.uri.salmox.view.AddBookSAEFrame;

/**
 * Controle de adição/edição de livro
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBookController implements SubjectInterface {

    //visão
    private AddBookInterface view;
    //DAO
    private CategoryBoxDAOInterface bookDAO;
    //observadores
    private List<ObserverInterface> observers;
    //categoria
    private Category category;
    //livro 
    private Book book;
    //código sugerido
    private int codeSugered;
    //caixa
    private Box box;

    /**
     * Construtor para adição
     *
     * @param category categoria
     * @param codeSugered código sugerido
     * @param box caixa
     */
    public AddBookController(Category category, int codeSugered, Box box) {

        //inicializa lista de observadores
        observers = new ArrayList();
        //define variaveis
        this.category = category;
        this.codeSugered = codeSugered;
        this.box = box;
        //inicia visão
        createView();
        //define eventos para a visão
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addBook();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });
    }//fim do construtor para adição

    /**
     * Construtor para edição
     *
     * @param category categoria
     * @param book livro
     * @param box caixa
     */
    public AddBookController(Category category, Book book, Box box) {
        //inicializa lista de observadores
        observers = new ArrayList();
        //define veriaveis
        this.category = category;
        this.book = book;
        this.box = box;
        //inicia visão
        createView();
        //define eventos
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editBook();
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
                    editBook();
                }
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    cancel();
                }
            }
        });
    }//fim do construtor

    /**
     * Adicionar livro
     */
    private void addBook() {
        //pega livro da visão
        Book book = view.getBook();
        //resta livro
        if (book != null) {
            try {
                book.setCodeBox(box.getCodeBox());
                //registra livro e fecha vosão
                bookDAO.registerBook(book);
                view.close();
                //notifica observadores
                notifyObservers(false, book);
            } catch (PreviouslyRegisteredException exception) {
                JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro! Código já existente!", "Erro!", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//fim do método addBook

    /**
     * Edita livro
     */
    private void editBook() {
        //pega livro da visão
        Book book = view.getBook();
        if (book != null) {
            if (!new GenericBoxDAO().checkActiveBox(this.box.getCodeBox())) {
                JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

            } else {
                book.setCodeBox(this.book.getCodeBox());
                book.setCodeBook(this.book.getCodeBook());
                if (bookDAO.checkBookCode(this.book, book)) {
                    bookDAO.editBook(book);
                    view.close();
                    notifyObservers(false, book);

                } else {
                    JOptionPane.showMessageDialog(null, "O código já está cadastrado!", "Atenção!", JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }//fim do método edit\book

    /**
     * Cria visão adequada
     */
    private void createView() {
        DAOFactory dAOFactory = new DAOFactory(category);
        bookDAO = dAOFactory.getDAO();
        if (book == null) {
            if (category == Category.A) {
                view = new AddBookAFrame(codeSugered, box.getTitleBox());

            } else if (category == Category.AE) {
                view = new AddBookAEFrame(codeSugered, box.getTitleBox());
                ((AddBookAEFrame) view).setCourseList(new AEDAO().listCourses());

            } else if (category == Category.CC) {
                view = new AddBookCCFrame(codeSugered, box.getTitleBox());
                ((AddBookCCFrame) view).setCourseList(new CCDAO().listCourses());

            } else if (category == Category.DP) {
                view = new AddBookDPFrame(codeSugered, box.getTitleBox());

            } else if (category == Category.EX) {
                view = new AddBookEXFrame(codeSugered, box.getTitleBox());
                ((AddBookEXFrame) view).setCourseList(new EXDAO().listCourses());

            } else if (category == Category.F) {
                view = new AddBookFFrame(codeSugered, box.getTitleBox());
                ((AddBookFFrame) view).setCourseList(new FDAO().listCourses());
            } else if (category == Category.FC) {
                view = new AddBookFCFrame(codeSugered, box.getTitleBox());
                ((AddBookFCFrame) view).setCourseList(new FCDAO().listBookTitle());

            } else if (category == Category.FP) {
                view = new AddBookFPFrame(codeSugered, box.getTitleBox());
                ((AddBookFPFrame) view).setCourseList(new FPDAO().listCourses());
                ((AddBookFPFrame) view).setTypeCourseList(new FPDAO().listTypeCourses());

            } else if (category == Category.MC) {
                view = new AddBookMCFrame(codeSugered, box.getTitleBox());

            } else if (category == Category.DAC) {
                view = new AddBookDACFrame(codeSugered, box.getTitleBox());
                ((AddBookDACFrame) view).setCourseList(new DACDAO().listBookTitle());

            } else if (category == Category.DAD) {
                view = new AddBookDADFrame(codeSugered, box.getTitleBox());
                ((AddBookDADFrame) view).setCourseList(new DADDAO().listBookTitle());

            } else if (category == Category.DG) {
                view = new AddBookDGFrame(codeSugered, box.getTitleBox());
                ((AddBookDGFrame) view).setCourseList(new DGDAO().listBookTitle());

            } else if (category == Category.DSG) {
                view = new AddBookDSGFrame(codeSugered, box.getTitleBox());
                ((AddBookDSGFrame) view).setCourseList(new DSGDAO().listBookTitle());


            } else if (category == Category.SAE) {
                view = new AddBookSAEFrame(codeSugered, box.getTitleBox());
                ((AddBookSAEFrame) view).setCourseList(new SAEDAO().listTitle());

            }
        } else {
            if (category == Category.A) {
                view = new AddBookAFrame(box.getTitleBox(), (BookA) book);

            } else if (category == Category.AE) {
                view = new AddBookAEFrame(box.getTitleBox(), (BookAE) book);
                ((AddBookAEFrame) view).setCourseList(new AEDAO().listCourses());

            } else if (category == Category.CC) {
                view = new AddBookCCFrame(box.getTitleBox(), (BookCC) book);
                ((AddBookCCFrame) view).setCourseList(new CCDAO().listCourses());

            } else if (category == Category.DP) {
                view = new AddBookDPFrame(box.getTitleBox(), (BookDP) book);

            } else if (category == Category.EX) {
                view = new AddBookEXFrame(box.getTitleBox(), (BookEX) book);
                ((AddBookEXFrame) view).setCourseList(new EXDAO().listCourses());

            } else if (category == Category.F) {
                view = new AddBookFFrame(box.getTitleBox(), (BookF) book);
                ((AddBookFFrame) view).setCourseList(new FDAO().listCourses());
            } else if (category == Category.FC) {
                view = new AddBookFCFrame(box.getTitleBox(), (BookFC) book);
                ((AddBookFCFrame) view).setCourseList(new FCDAO().listBookTitle());

            } else if (category == Category.FP) {
                view = new AddBookFPFrame(box.getTitleBox(), (BookFP) book);
                ((AddBookFPFrame) view).setCourseList(new FPDAO().listCourses());
                ((AddBookFPFrame) view).setTypeCourseList(new FPDAO().listTypeCourses());

            } else if (category == Category.MC) {
                view = new AddBookMCFrame(box.getTitleBox(), (BookMC) book);

            } else if (category == Category.DAD) {
                view = new AddBookDADFrame(box.getTitleBox(), (BookDAD) book);
                ((AddBookDADFrame) view).setCourseList(new DADDAO().listBookTitle());

            } else if (category == Category.DAC) {
                view = new AddBookDACFrame(box.getTitleBox(), (BookDAC) book);
                ((AddBookDACFrame) view).setCourseList(new DACDAO().listBookTitle());
            } else if (category == Category.DG) {
                view = new AddBookDGFrame(box.getTitleBox(), (BookDG) book);
                ((AddBookDGFrame) view).setCourseList(new DGDAO().listBookTitle());

            } else if (category == Category.DSG) {
                view = new AddBookDSGFrame(box.getTitleBox(), (BookDSG) book);
                ((AddBookDSGFrame) view).setCourseList(new DSGDAO().listBookTitle());

            } else if (category == Category.SAE) {
                view = new AddBookSAEFrame(box.getTitleBox(), (BookSAE) book);
                ((AddBookSAEFrame) view).setCourseList(new SAEDAO().listTitle());

            }

        }
    }//fim do método createview

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
}//fim da classe AddBookController

