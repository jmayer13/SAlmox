/*- 
 * Classname:             WithdrawalGenerateController.java 
 * 
 * Version information:   1.0 
 * 
 * Date:                  08/01/2014 - 17:14:52 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.controller;

import uni.uri.salmox.report.ReportWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import uni.uri.salmox.DAO.RequestDAO;
import uni.uri.salmox.DAO.WithdrawalDAO;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.WithdrawalGenerateFrame;

/**
 * Controle para finalizaão de requisição e geração de reçlatórios
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalGenerateController implements SubjectInterface {

    //lista de observafores
    private List<ObserverInterface> observers;
    //visão
    private WithdrawalGenerateFrame view;
    //lista de documentos
    private List<Document> documents;
    //solicitação
    private Request request = null;

    /**
     * Construtor com lista de documentos
     *
     * @param documents lista de documentos
     */
    public WithdrawalGenerateController(List<Document> documents) {
        observers = new ArrayList();
        this.documents = documents;
        view = new WithdrawalGenerateFrame();

        //define eventos 
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                request();
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
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //teclado
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    request();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close();
                }
            }
        });

    }//fim do construtor

    /**
     * Request the document
     */
    private void request() {
        Withdrawal withdrawal = view.getWithdrawal();
        if (withdrawal == null) {
            return;
        }
        withdrawal.setListDocument(documents);
        WithdrawalDAO withdrawalDAO = new WithdrawalDAO();
        int code = withdrawalDAO.withdrawsDocument(withdrawal);
        if (code == 0) {
            System.err.println("Erro ao obtêr código da retirada!");
        }
        if (request != null) {
            new RequestDAO().disableRequest(request);
        }
        //FECHAR
        close();
        notifyObservers(false, withdrawal);
        //GERAR RELATÓRIO
        ReportWithdrawal reportWithdrawal = new ReportWithdrawal(code);

    }//fim do método request

    /**
     * Fecha a janela
     */
    private void close() {
        view.close();
        notifyObservers(false, null);
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

    /**
     * Define nome do usuário que solicitou
     *
     * @param request solicitação
     */
    public void setRequest(Request request) {
        this.request = request;
        view.setUserName(request.getNameDefaultUser());
        if (request.getMotiveRequest() != null) {
            view.setMotive(request.getMotiveRequest());
        }
        if (request.getObservationRequest() != null) {
            view.setObservation(request.getObservationRequest());
        }
    }//fim do método setUser
}//fim da classe WithdrawalGenerateController 

