/*-
 * Classname:             RequestDefaultUserController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:17:07
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
import javax.swing.JPanel;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.RequestDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.RequestsDefaultUserPanel;
import uni.uri.salmox.view.SeeRequestFrame;

/**
 * Controle responsável por mostrar as solicitações de usuários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestDefaultUserController implements ObserverInterface {

    //observador pai
    private ObserverInterface observer;
    //visão
    private RequestsDefaultUserPanel view;
    //DAO
    private RequestDAO requestDAO;
    //mais informações
    private SeeRequestFrame seeRequestFrame;

    /**
     * Método construtor
     */
    public RequestDefaultUserController() {
        requestDAO = new RequestDAO();
        if (TemporaryData.getUser() instanceof Administrator) {
            Iterator iterator = requestDAO.searchOpenRequest();
            List<Request> requests = new ArrayList();
            while (iterator.hasNext()) {
                requests.add((Request) iterator.next());
            }
            view = new RequestsDefaultUserPanel(requests);

            //adiciona eventos
            view.setActionListenerWithdrawButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    withdrawal();
                }
            });
        } else {
            //para usuários padrão
            DefaultUser defaultUser = (DefaultUser) TemporaryData.getUser();
            Iterator iterator = requestDAO.searchRequesByUser(defaultUser.getCodeDefaultUserr());
            List<Request> requests = new ArrayList();
            while (iterator.hasNext()) {
                requests.add((Request) iterator.next());
            }
            view = new RequestsDefaultUserPanel(requests);
            view.changeForRequestMode();

            //adiciona eventos
            //retirada
            view.setActionListenerWithdrawButton(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    request();
                }
            });
        }
        //apagar
        view.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abortRequest();

            }
        });
        //teclado
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_F5 == e.getKeyCode()) {
                    update(false, null);
                }
            }
        });

        view.setActionListenerMoreButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Request request = view.getSelectedRequest();
                if (request != null) {
                    List<Document> documents = new RequestDAO().listDocuments(request.getCodeRequest());
                    List<Book> books = new ArrayList();
                    List<Box> boxes = new ArrayList();
                    GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                    for (int i = 0; i < documents.size(); i++) {
                        Book book = genericBoxDAO.searchBook(documents.get(i).getCodeBook());
                        books.add(book);
                        boxes.add(genericBoxDAO.searchBox(book.getCodeBox()));
                    }
                    seeRequestFrame = new SeeRequestFrame(request, boxes, books, documents);
                    seeRequestFrame.setCloseWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            close();
                        }
                    });
                    seeRequestFrame.setCloseButtonActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            close();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma solicitação!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

    }//fim do construtor

    /**
     * Fecha view
     */
    private void close() {
        seeRequestFrame.close();
    }//fim do método close

    /**
     * Cancela soliicitação
     */
    private void abortRequest() {
        if (view.getSelectedRequest() != null) {
            RequestDAO requestDAO = new RequestDAO();
            requestDAO.cancelRequest(view.getSelectedRequest());
            Iterator iterator;
            if (TemporaryData.getUser() instanceof Administrator) {
                iterator = requestDAO.searchOpenRequest();
            } else {
                DefaultUser defaultUser = (DefaultUser) TemporaryData.getUser();
                iterator = requestDAO.searchRequesByUser(defaultUser.getCodeDefaultUserr());
            }
            List<Request> requests = new ArrayList();
            while (iterator.hasNext()) {
                requests.add((Request) iterator.next());
            }
            view.refresh(requests);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma solicitação!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método cancelRequest

    /**
     * Faz retirada dessa solocitação
     */
    private void withdrawal() {
        if (view.getSelectedRequest() != null) {
            Request request = view.getSelectedRequest();
            WithdrawalController withdrawalController = new WithdrawalController(request);
            withdrawalController.registerAllObservers(observer);
            withdrawalController.registerObserver(observer);
            withdrawalController.registerObserver(this);
            withdrawalController.notifyObservers(true, null);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma solicitação!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }

    }//fim do método withdrawal

    /**
     * Abre janela para solicitações
     */
    private void request() {
        RequestController requestController = new RequestController();
        requestController.registerObserver(observer);
        requestController.registerObserver(this);
        requestController.notifyObservers(true, null);
    }//fim do método request

    /**
     * Obtêm panel da visão
     *
     * @return <code>JPanel</code> panel
     */
    public JPanel getPanel() {
        return view.getPanel();
    }//fim do método getPanel

    /**
     * Registra o observer em todos os observers filhos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Atualiza lista
     *
     * @param active ativo?
     * @param object objeto a ser adicionado a lista
     */
    public void update(boolean active, Object object) {
        if (!active) {
            if (TemporaryData.getUser() instanceof Administrator) {
                Iterator iterator = requestDAO.searchOpenRequest();
                List<Request> requests = new ArrayList();
                while (iterator.hasNext()) {
                    requests.add((Request) iterator.next());
                }
                view.refresh(requests);
            } else { //para usuários padrão
                DefaultUser defaultUser = (DefaultUser) TemporaryData.getUser();
                Iterator iterator = requestDAO.searchRequesByUser(defaultUser.getCodeDefaultUserr());
                List<Request> requests = new ArrayList();
                while (iterator.hasNext()) {
                    requests.add((Request) iterator.next());
                }
                view.refresh(requests);
            }
        }
    }//fim do método update
}//fim da classe RequestDefaultUserController

