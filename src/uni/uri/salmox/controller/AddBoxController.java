/*-
 * Classname:             AddBoxController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 13:55:23
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
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.model.BoxSAE;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.PreviouslyRegisteredException;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.AddBoxAFrame;
import uni.uri.salmox.view.AddBoxFrame;
import uni.uri.salmox.view.AddBoxInterface;
import uni.uri.salmox.view.AddBoxSAEFrame;

/**
 * Controle de adição e edição de caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBoxController implements SubjectInterface {

    //visão
    private AddBoxInterface view;
    //DAO
    private CategoryBoxDAOInterface boxDAO;
    //observadores
    private List<ObserverInterface> observers;
    //categoria
    private Category category;
    //caixa
    private Box box;

    /**
     * Construtor para cadastro de caixa
     *
     * @param category categoria da caixa
     * @param codeSugered código sugerido para a caixa
     */
    public AddBoxController(Category category, int codeSugered) {

        //inicializa lista de observadores
        observers = new ArrayList();
        //checa categoria e cria view referente
        this.category = category;
        //inicializa DAO
        createDAO();

        //verifica se pertence as categorias com dados especificos
        if (category == Category.A) {
            ADAO aDAO = new ADAO();
            view = new AddBoxAFrame(codeSugered, aDAO.listTypeBox());
        } else if (category == Category.SAE) {
            view = new AddBoxSAEFrame(codeSugered);
        } else {
            view = new AddBoxFrame(category, codeSugered);
        }

        //define eventos
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBox();
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
                    addBox();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });
    }//fim do construtor para cadastro de caixa

    /**
     * Construtor para edição de caixas
     *
     * @param category categoria
     * @param box caixa a ser editada
     */
    public AddBoxController(Category category, Box box) {

        //inicializa lista de observadores
        observers = new ArrayList();
        //cria view referente a categoria
        this.category = category;
        //inicializa DAO
        createDAO();
        this.box = box;
        //verifica se pertence as categorias com dados especificos
        if (category == Category.A) {
            ADAO aDAO = new ADAO();
            view = new AddBoxAFrame((BoxA) box, aDAO.listTypeBox());

        } else if (category == Category.SAE) {
            view = new AddBoxSAEFrame((BoxSAE) box);
        } else {
            view = new AddBoxFrame(category, box);
        }

        //define eventos
        //ok
        view.setActionListenerOKButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editBox();
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
                    editBox();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });

    }//fim do construtor para edição

    /**
     * Cadastra caixa
     */
    private void addBox() {

        //obtêm caixa da view
        Box box = view.getBox();
        //testa se caixa está vazia
        if (box != null) {
            //tenta registrar a caixa, uma exceção é disparada caso o código já exista
            try {
                boxDAO.registerBox(box);
                view.close();
                notifyObservers(false, box);
            } catch (PreviouslyRegisteredException exception) {
                JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa! Código já existente!", "Erro!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//fim do método addBox

    /**
     * Edita caixa
     */
    private void editBox() {

        //obtêm caixa da view
        Box box = view.getBox();
        //testa se a caixa é nula
        if (box != null) {
            if (!new GenericBoxDAO().checkActiveBox(this.box.getCodeBox())) {
                JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

            } else {
                box.setCodeBox(this.box.getCodeBox());
                //testa salvar as alterações na caixa se o código já existir dispara uma exceção

                if (boxDAO.checkBoxCode(this.box, box)) {
                    boxDAO.editBox(box);
                    view.close();
                    notifyObservers(false, box);
                } else {
                    JOptionPane.showMessageDialog(null, "Esse código já está sendo usado!", "Erro!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//fim do método editBox

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
     * Cria o DAO de acordo com a caixa
     */
    private void createDAO() {
        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();
    }//fim do método createDAO

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
}//fim da classe AddBoxController

