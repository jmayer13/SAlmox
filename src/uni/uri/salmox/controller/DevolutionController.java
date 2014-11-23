/*- 
 * Classname:             DevolutionController.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  26/09/2013 - 14:07:55 
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
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.view.DevolutionDocumentPanel;
import uni.uri.salmox.view.SeeWithdrawalFrame;

/**
 * Controle de devolução de documentos requisitados
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DevolutionController {

    //visão
    private DevolutionDocumentPanel view;
    //observador
    private ObserverInterface observer;
    //DAO
    private WithdrawalDAO withdrawalDAO;
    private SeeWithdrawalFrame seeWithdrawalFrame;

    /**
     * Construtor sem parâmetros
     */
    public DevolutionController() {
        withdrawalDAO = new WithdrawalDAO();
        Iterator iterator = withdrawalDAO.searchOpenWithdrawal();
        List<Withdrawal> withdrawals = new ArrayList();
        while (iterator.hasNext()) {
            withdrawals.add((Withdrawal) iterator.next());
        }
        view = new DevolutionDocumentPanel(withdrawals);
        //define evento do botao entregar
        view.setActionListenerReturnButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Withdrawal wathdrawal = view.getSelectedRow();
                if (wathdrawal != null) {
                    withdrawalDAO.returnsDocument(wathdrawal);
                    Iterator iterator = withdrawalDAO.searchOpenWithdrawal();
                    List<Withdrawal> withdrawals = new ArrayList();
                    while (iterator.hasNext()) {
                        withdrawals.add((Withdrawal) iterator.next());
                    }
                    view.refresh(withdrawals);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma retirada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });
        //define evento para o borao mais
        view.setActionListenerMoreButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Withdrawal wathdrawal = view.getSelectedRow();
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
     * Fecha view
     */
    private void close() {
        seeWithdrawalFrame.close();
    }//fim do método close

    /**
     * Obtêm o panel da visão
     *
     * @return <code>JPanel </code>´panel
     */
    public JPanel getPanel() {
        return view.getPanel();
    }//fim do método getPanel

    /**
     * Define observador para ser registrado em todos os subjetos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//registerAllObservers
}//fim da classe DevolutionController 

