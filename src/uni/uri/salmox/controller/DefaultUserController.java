/*-
 * Classname:             DefaultUserController.java
 *
 * Version information:   1.0
 *
 * Date:                  22/05/2013 - 15:16:27
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
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.OpenManualOfUser;
import uni.uri.salmox.view.AboutFrame;
import uni.uri.salmox.view.DefaultUserFrame;
import uni.uri.salmox.view.WallpaperPanel;

/**
 * Controle da janela principal do usuário padrão
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DefaultUserController implements ObserverInterface {

    //janela principal do usuário padrão
    private DefaultUserFrame defaultUserFrame;
    //controller de caixa
    private BoxController boxController;
    //usuário padrão
    private DefaultUser user;

    /**
     * Construtor com usuário padrão
     *
     * @param defaultUser usuário padrão
     */
    public DefaultUserController(DefaultUser defaultUser) {

        this.user = defaultUser;
        //inicializa view
        defaultUserFrame = new DefaultUserFrame();
        //cria o panel interno
        defaultUserFrame.changePanel(new WallpaperPanel());

        //registra listeners de menus
        //categorias de caixa
        //A
        defaultUserFrame.setActionListenerAMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.A, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //AE
        defaultUserFrame.setActionListenerAEMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.AE, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //CC
        defaultUserFrame.setActionListenerCCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.CC, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DP
        defaultUserFrame.setActionListenerDPMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DP, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //EX
        defaultUserFrame.setActionListenerEXMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.EX, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //F
        defaultUserFrame.setActionListenerFMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.F, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //FC
        defaultUserFrame.setActionListenerFCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.FC, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //FP
        defaultUserFrame.setActionListenerFPMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.FP, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //MC
        defaultUserFrame.setActionListenerMCMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.MC, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //SAE
        defaultUserFrame.setActionListenerSAEMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.SAE, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DAD
        defaultUserFrame.setActionListenerDADMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DAD, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DAC
        defaultUserFrame.setActionListenerDACMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DAC, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DG
        defaultUserFrame.setActionListenerDGMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DG, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });
        //DSG
        defaultUserFrame.setActionListenerDSGMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boxController = new BoxController(Category.DSG, user);
                defaultUserFrame.changePanel(boxController.getPanel());
                registeriInBoxObservers();
            }
        });

        //sobre
        defaultUserFrame.setActionListenerAboutMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });
        //histórico
        defaultUserFrame.setActionListenerHistoryMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                history();
            }
        });
        //tela inicial
        defaultUserFrame.setActionListenerHomeScreenmenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWallpaper();
            }
        });
        //logout
        defaultUserFrame.setActionListenerLogoutMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        //manual do usuário
        defaultUserFrame.setActionListenerManualItemMenu(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openManual();
            }
        });
        //opções
        defaultUserFrame.setActionListenerOptionsMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                options();
            }
        });
        //solicitação
        defaultUserFrame.setActionListenerRequestMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                request();
            }
        });
        //busca
        defaultUserFrame.setActionListenerSearchMenuItem(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        //evento para fechamento de janela
        defaultUserFrame.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //fecha conexão com o banco e sai do programa      
                ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
                connectionDatabase.closeConnection();
                defaultUserFrame.close();
                System.exit(0);
            }
        });
    }//fim do construtor

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
        defaultUserFrame.changePanel(historyController.getPanel());
        historyController.registerAllObservers(this);
    }//fim do método history 

    /**
     * Abre a janela de notas do programa
     */
    private void about() {
        AboutFrame aboutFrame = new AboutFrame();
    }//fim do método about

    /**
     * Troca panel por plano de fundo
     */
    private void showWallpaper() {
        defaultUserFrame.changePanel(new WallpaperPanel());
    }//fim do método showWallpaper

    /**
     * Inicializa o controller do login
     */
    private void logout() {
        defaultUserFrame.close();
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
        defaultUserFrame.changePanel(requestDefaultUserController.getPanel());
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
     * Atualiza visão segundo o observado (subject)
     *
     * @param active o frame do subject está ativo
     * @param object objetro atualizado(não utilizado aqui)
     */
    public void update(boolean active, Object object) {
        //se o frame subject foi desativado ativa frame principal e pede o foco
        defaultUserFrame.enableFrame(!active);
        if (active == false) {
            defaultUserFrame.requestFrameFocus();
            //se for um panel ele atualiza o panel interno
            if (object instanceof JPanel) {
                JPanel panel = (JPanel) object;
                defaultUserFrame.changePanel(panel);
                panel.requestFocus();
            }
        }
        defaultUserFrame.requestFrameFocus();
    }//fim do método update

    //método para testes
    public static void main(String args[]) {
        new DefaultUserController(new DefaultUser());
    }
}//fim da classe DefaultUserController

