/*-
 * Classname:             HistoryController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:06:07
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
import javax.swing.JPanel;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.WithdrawalDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.HistoryWithdrawalPanel;
import uni.uri.salmox.view.SeeWithdrawalFrame;

/**
 * Controle da janela de histórico de retiradas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class HistoryController {

    //visão
    private HistoryWithdrawalPanel view;
    //observer
    private ObserverInterface observer;
    //dao de reitadas
    private WithdrawalDAO withdrawalDAO;
    //visão para visualizar teriadas
    private SeeWithdrawalFrame seeWithdrawalFrame;

    /**
     * Construtor para Usuário Padrão
     *
     * @param defaultser usuário padrão
     */
    public HistoryController(DefaultUser defaultser) {
        withdrawalDAO = new WithdrawalDAO();

        Iterator iterator = withdrawalDAO.searchUserCloseWithdrawal(TemporaryData.getUser().getName());
        List<Withdrawal> withdrawals = new ArrayList();
        while (iterator.hasNext()) {
            withdrawals.add((Withdrawal) iterator.next());
        }
        view = new HistoryWithdrawalPanel(withdrawals);

        //define evento para botão mais
        view.setActionListenerMoreButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Withdrawal wathdrawal = view.getSelectedBook();
                if (wathdrawal != null) {
                    List<Document> documents = wathdrawal.getListDocument();
                    List<Book> books = new ArrayList();
                    List<Box> boxes = new ArrayList();
                    GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                    for (int i = 0; i < documents.size(); i++) {
                        Book book = genericBoxDAO.searchBook(documents.get(i).getCodeBook());
                        books.add(book);
                        boxes.add(genericBoxDAO.searchBox(book.getCodeBox()));
                    }
                    seeWithdrawalFrame = new SeeWithdrawalFrame(wathdrawal, boxes, books, documents);
                    seeWithdrawalFrame.setCloseWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            close();
                        }
                    });
                    seeWithdrawalFrame.setCloseButtonActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            close();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma retirada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
    }//fim do construtor

    /**
     * Construtor para Administrador
     *
     * @param administrator adiministrador
     */
    public HistoryController(Administrator administrator) {
        withdrawalDAO = new WithdrawalDAO();

        Iterator iterator = withdrawalDAO.searchCloseWithdrawal();
        List<Withdrawal> withdrawals = new ArrayList();
        while (iterator.hasNext()) {
            withdrawals.add((Withdrawal) iterator.next());
        }

        view = new HistoryWithdrawalPanel(withdrawals);
        //define evento para o botão mais
        view.setActionListenerMoreButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Withdrawal wathdrawal = view.getSelectedBook();
                if (wathdrawal != null) {
                    List<Document> documents = wathdrawal.getListDocument();
                    List<Book> books = new ArrayList();
                    List<Box> boxes = new ArrayList();
                    GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                    for (int i = 0; i < documents.size(); i++) {
                        Book book = genericBoxDAO.searchBook(documents.get(i).getCodeBook());
                        books.add(book);
                        boxes.add(genericBoxDAO.searchBox(book.getCodeBox()));
                    }
                    seeWithdrawalFrame = new SeeWithdrawalFrame(wathdrawal, boxes, books, documents);
                    seeWithdrawalFrame.setCloseWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            close();
                        }
                    });
                    seeWithdrawalFrame.setCloseButtonActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            close();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma retirada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
    }//fim do construtor para admionistrador

    /**
     * Obtêm a visão
     *
     * @return <code>JPanel</code> visão
     */
    public JPanel getPanel() {
        return view.getPanel();
    }//fim do método getPanel

    /**
     * Fecha view
     */
    private void close() {
        seeWithdrawalFrame.close();
    }//fim do método close

    /**
     * Define observer para ser registrado em todos os subject
     *
     * @param observer observer
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers
}//fim da classe HistoryController

