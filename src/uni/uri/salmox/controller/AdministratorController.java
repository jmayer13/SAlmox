/*-
 * Classname:             AdministratorController.java
 *
 * Version information:   1.0
 *
 * Date:                  22/05/2013 - 15:16:44
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import uni.uri.salmox.DAO.ConectionFactory;
import uni.uri.salmox.DAO.ConnectionDatabase;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.OpenManualOfUser;
import uni.uri.salmox.view.AboutFrame;
import uni.uri.salmox.view.AdministratorFrame;
import uni.uri.salmox.view.WallpaperPanel;

/**
 * Controle do Administrador
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AdministratorController implements ObserverInterface {

    //usuário administrador
    private Administrator user;
    //visão principal do administrador
    private AdministratorFrame administratorFrame;
    //controle das caixas
    private BoxController boxController;

    /**
     * Contrutor com administrador
     *
     * @param administrator administrador
     */
    public AdministratorController(Administrator administrator) {
        this.user = administrator;
        //inicializa visão
        administratorFrame = new AdministratorFrame();
        administratorFrame.changePanel(new WallpaperPanel());

        //define eventos para os menus
        //define ouvintes para as categorias de caixas
        //A
        administratorFrame.setActionListenerAMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.A, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //AE
        administratorFrame.setActionListenerAEMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.AE, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //CC
        administratorFrame.setActionListenerCCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.CC, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DP
        administratorFrame.setActionListenerDPMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DP, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //EX
        administratorFrame.setActionListenerEXMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.EX, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //F
        administratorFrame.setActionListenerFMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.F, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //FC
        administratorFrame.setActionListenerFCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.FC, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //FP
        administratorFrame.setActionListenerFPMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.FP, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //MC
        administratorFrame.setActionListenerMCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.MC, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //SAE
        administratorFrame.setActionListenerSAEMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.SAE, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DAD
        administratorFrame.setActionListenerDADMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DAD, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DAC
        administratorFrame.setActionListenerDACMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DAC, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DG
        administratorFrame.setActionListenerDGMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DG, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DSG
        administratorFrame.setActionListenerDSGMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DSG, user);
                administratorFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //sobre
        administratorFrame.setActionListenerAboutMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });
        //entrega
        administratorFrame.setActionListenerDeliverMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deliver();
            }
        });
        //histórico
        administratorFrame.setActionListenerHistoryMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                history();
            }
        });
        //tela inicial
        administratorFrame.setActionListenerHomeScreenMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWallpaper();
            }
        });
        //logout
        administratorFrame.setActionListenerLogoutMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        //manual
        administratorFrame.setActionListenerManualItemMenu(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openManual();
            }
        });
        //opções
        administratorFrame.setActionListenerOptionsMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                options();
            }
        });
        //solicitação
        administratorFrame.setActionListenerRequestMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                request();
            }
        });
        //retiradas
        administratorFrame.setActionListenerWithdrawalMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdrawal();
            }
        });
        //buscar
        administratorFrame.setActionListenerSearchMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        //lista de exclusão
        administratorFrame.setActionListenerEraseListMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eraseList();
            }
        });
        //registrar usuário
        administratorFrame.setActionListenerRegisterMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        //gerênciar usuários
        administratorFrame.setActionListenerManagerMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                managerUser();
            }
        });
        //fechar janela
        administratorFrame.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //fecha conexão com o banco e sai do programa
                ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
                connectionDatabase.closeConnection();
                administratorFrame.close();
                System.exit(0);
            }
        });
    }//fim do construtor com administrador

    /**
     * Registra observador para janelas filhas e netas
     */
    private void registeriInBoxObservers() {
        boxController.registerAllObservers(this);
    }//fim do método registeriInBoxObservers

    /**
     * Inicializa o controller do histórico de solicitações
     */
    private void history() {
        HistoryController historyController = new HistoryController(user);
        administratorFrame.changePanel(historyController.getPanel());
        historyController.registerAllObservers(this);
    }//fim do método history 

    /**
     * Abre a janela de notas do programa
     */
    private void about() {
        AboutFrame aboutFrame = new AboutFrame();
    }//fim do método about

    /**
     * Entrega documento
     */
    private void deliver() {
        DevolutionController devolutionController = new DevolutionController();
        administratorFrame.changePanel(devolutionController.getPanel());
        devolutionController.registerAllObservers(this);
    }//fim do método deliver

    /**
     * Retirada de documentos
     */
    private void withdrawal() {
        WithdrawalController withdrawalController = new WithdrawalController();
        withdrawalController.registerAllObservers(this);
        withdrawalController.registerObserver(this);
        withdrawalController.notifyObservers(true, null);
    }//fim do método withdrawal

    /**
     * Troca panel por plano de fundo
     */
    private void showWallpaper() {
        administratorFrame.changePanel(new WallpaperPanel());
    }//fim do método showWallpaper

    /**
     * Inicializa o controller do login
     */
    private void logout() {
        administratorFrame.close();
        LoginController loginController = new LoginController();
    }//fim do método logout

    /**
     * Abre o manual do usuário
     */
    private void openManual() {
        OpenManualOfUser openManual = new OpenManualOfUser();
        openManual.openManualUser();
    }//fim do método openManual

    /**
     * Inicializa o controller de opções
     */
    private void options() {
        OptionsController oprionsController = new OptionsController();
        oprionsController.registerObserver(this);
    }//fim do método options

    /**
     * Inicializa controller de solicitações
     */
    private void request() {
        RequestDefaultUserController requestDefaultUserController = new RequestDefaultUserController();
        administratorFrame.changePanel(requestDefaultUserController.getPanel());
        requestDefaultUserController.registerAllObservers(this);
    }//fim do método request

    /**
     * Inicializa controller de busca
     */
    private void search() {
        SearchController searchController = new SearchController();
        searchController.registerObserver(this);
        searchController.notifyObservers(true, false);
    }//fim do método search

    /**
     * Inicializa Controle de lista de exclusão
     */
    private void eraseList() {
        DeleteListBoxesController deleteListBoxesController = new DeleteListBoxesController();
        administratorFrame.changePanel(deleteListBoxesController.getPanel());
        deleteListBoxesController.registerAllObservers(this);
    }//fim do método eraseList

    /**
     * Inicializa controle de registro de usuário
     */
    private void addUser() {
        AddUserController addUserController = new AddUserController();
        addUserController.registerObserver(this);
        addUserController.notifyObservers(true, null);
    }//fim do método addUser

    /**
     * Inicializa controller de gerência de usuário
     */
    private void managerUser() {
        ManagerUserController managerUserController = new ManagerUserController();
        administratorFrame.changePanel(managerUserController.getPanel());
        managerUserController.registerAllObservers(this);
    }//fim do método managerUser

    /**
     * Atualiza visão segundo o observado (subject)
     *
     * @param active o frame do subject está ativo
     * @param object objetro atualizado(não utilizado aqui)
     */
    public void update(boolean active, Object object) {
        //se o frame subject foi desativado ativa frame principal e pede o foco
        administratorFrame.enableFrame(!active);
        if (active == false) {
            administratorFrame.requestFrameFocus();
            //se for um panel ele atualiza o panel interno
            if (object instanceof JPanel) {
                JPanel panel = (JPanel) object;
                administratorFrame.changePanel(panel);
                panel.requestFocus();
            }
        }
        administratorFrame.requestFrameFocus();
    }//fim do método update

    //método para testes
    public static void main(String args[]) {
        new AdministratorController(new Administrator());
    }
}//fim da classe AdministratorController

