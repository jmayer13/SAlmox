
/*-
 * Classname:             AdministratorFrame.java
 *
 * Version information:   1.1
 *
 * Date:                  30/01/2013 - 14:31:42
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Visão da janela principal do administrador
 *
 * @see uni.uri.salmox.view.FrameInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AdministratorFrame {

    //frame principal
    private JFrame frame;
    //panel de fundo
    private JPanel backgroundPanel;
    //panel interior que é mudado
    private JPanel insidePanel;
    //scrollPane para as mudanças de panel
    private JScrollPane scrollPane;
    //barra de menus do administrador
    private JMenuBar menuBar;
    //Menu abrir caixa
    private JMenu openBoxMenu;
    //Menu usuário
    private JMenu userMenu;
    //menu excluir caixa
    private JMenu deleteBoxMenu;
    //Menu buscar
    private JMenu searchMenu;
    //Menu retiradas
    private JMenu withdrawawMenu;
    //Menu sistema
    private JMenu systemMenu;
    //Menu ajuda
    private JMenu helpMenu;
    //itens do menu abrir caixa
    //setor 100 - SECRETARIA
    private JMenu secretaryMenu;
    //Item do Menu abrir caixa, Formandos
    private JMenuItem fMenuItem;
    //Item do Menu abrir caixa, financeiro e cobamça
    private JMenuItem fcMenuItem;
    //Item do menu abrir caixa, ex-alunos
    private JMenuItem exMenuItem;
    //Item do menu abrir caixa, atas e exames
    private JMenuItem aeMenuItem;
    //Item do menu abrir caixa, cadernos de chamada
    private JMenuItem ccMenuItem;
    //Item do menu abrir caixa, formandos pós-graduação
    private JMenuItem fpMenuItem;
    //setor 200 - PESQUISA
    private JMenu researchMenu;
    //setor 300 - EXTENSÃO
    private JMenu extentionMenu;
    //setor 500 - SAE 
    private JMenu saeMenu;
    //Item do menu abrir caixa, sae filantropia
    private JMenuItem saeMenuItem;
    //setor 600 - DIREÇÃO
    private JMenu directionMenu;
    //setor 700 - DEPARTAMENTO PESSOAL
    private JMenu personnelDepartmentMenu;
    //Item do menu abrir caixa, departamento pessoal
    private JMenuItem dpMenuItem;
    //setor 800 - CONTABILIDADE
    private JMenu bookkeepingMenu;
    //Item do menu abrir caixa, movimentos de caixa
    private JMenuItem mcMenuItem;
    //setor 900 - FINANCEIRO/JURÍDICO
    private JMenu financialMenu;
    //setor 1000 - ALMOXARIFADO/COMPRAS
    private JMenu warehouseMenu;
    //Item do menu abrir caixa, almoxarifado
    private JMenuItem aMenuItem;
    //Items do menu usuários
    //Item do menu usuários, cadastro
    private JMenuItem registerMenuItem;
    //Item do menu usuários, gerênciar
    private JMenuItem managerMenuItem;
    //Itens do menu excluir caixa
    //Item do menu excluir caixa, listar
    private JMenuItem listMenuItem;
    //Itens do menu buscar
    //Item do menu buscar, buscar
    private JMenuItem searchMenuItem;
    //Itens do menu retiradas
    //Item do menu retirada, solicitações
    private JMenuItem requestMenuItem;
    //Item do menu retiradas, retirar
    private JMenuItem withdrawalMenuItem;
    //Item do menu retiradas, entregar
    private JMenuItem deliverMenuItem;
    //Item do menu retiradas, histórico
    private JMenuItem historyMenuItem;
    //Itens do menu sistema
    //Item do menu sistema,logout
    private JMenuItem logoutMenuItem;
    //Item do menu sistema, opções
    private JMenuItem optionsMenuItem;
    //Item do menu sistema, tela inicial
    private JMenuItem homeScreenmenuItem;
    //Itens do menu ajuda
    //Item do menu ajuda, manual
    private JMenuItem manualItemMenu;
    //Item do menu ajuda, sobre
    private JMenuItem aboutMenuItem;
    //Item do menu direção, diretor geral
    private JMenuItem dgMenuItem;
    //item do menu direção, diretor academico
    private JMenuItem dacMenuItem;
    //item do menu direção, diretor administrativo
    private JMenuItem dadMenuItem;
    //item do menu direção, secretaria do gabinete
    private JMenuItem dsgMenuItem;
    //tamanho da tela
    private int x = 0;
    private int y = 0;

    /**
     * Construtor de AdministratorFrame sem parâmetros
     */
    public AdministratorFrame() {
        createView();
    }//fim do construtor

    /**
     * Cria a visão da janela do administrador
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Sistema de Gerência de Documentos do Arquivo Central da URI - Administrador");
        backgroundPanel = new WallpaperPanel();
        scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        menuBar = new JMenuBar();
        openBoxMenu = new JMenu("Abrir caixa");
        userMenu = new JMenu("Usuários");
        deleteBoxMenu = new JMenu("Excluir Caixas");
        searchMenu = new JMenu("Buscar");
        withdrawawMenu = new JMenu("Retiradas");
        systemMenu = new JMenu("Sistema");
        helpMenu = new JMenu("Ajuda");
        fMenuItem = new JMenuItem("Formandos");
        fcMenuItem = new JMenuItem("Financeiro e Cobrança");
        exMenuItem = new JMenuItem("Ex-Alunos");
        aeMenuItem = new JMenuItem("Atas e Exames");
        ccMenuItem = new JMenuItem("Cadernos de Chamada");
        fpMenuItem = new JMenuItem("Formandos Pós-Graduação");
        mcMenuItem = new JMenuItem("Movimentos de Caixa");
        dpMenuItem = new JMenuItem("Departamento Pessoal");
        saeMenuItem = new JMenuItem("SAE");
        aMenuItem = new JMenuItem("Almoxarifado");
        registerMenuItem = new JMenuItem("Cadastrar");
        managerMenuItem = new JMenuItem("Gerenciar");
        listMenuItem = new JMenuItem("Listar");
        searchMenuItem = new JMenuItem("Buscar");
        requestMenuItem = new JMenuItem("Solicitações");
        withdrawalMenuItem = new JMenuItem("Retirar");
        deliverMenuItem = new JMenuItem("Entregar");
        historyMenuItem = new JMenuItem("Histórico");
        logoutMenuItem = new JMenuItem("Logout");
        optionsMenuItem = new JMenuItem("Opções");
        homeScreenmenuItem = new JMenuItem("Tela Inicial");
        manualItemMenu = new JMenuItem("Manual");
        aboutMenuItem = new JMenuItem("Sobre");

        secretaryMenu = new JMenu("100 - SECRETARIA");
        researchMenu = new JMenu("200 - PESQUISA");
        extentionMenu = new JMenu("300 - EXTENSÃO");
        saeMenu = new JMenu("500 - SAE ");
        directionMenu = new JMenu("600 - DIREÇÃO");
        personnelDepartmentMenu = new JMenu("700 - DEPARTAMENTO PESSOAL");
        bookkeepingMenu = new JMenu("800 - CONTABILIDADE");
        financialMenu = new JMenu("900 - FINANCEIRO/JURÍDICO");
        warehouseMenu = new JMenu("1000 - ALMOXARIFADO/COMPRAS");

        dgMenuItem = new JMenuItem("Diretor(a) Geral");
        dacMenuItem = new JMenuItem("Diretor(a) Acadêmico(a)");
        dadMenuItem = new JMenuItem("Diretor(a) Administrativo");
        dsgMenuItem = new JMenuItem("Secretaria do Gabinete");

        //define layout como nulo
        frame.setLayout(null);
        backgroundPanel.setLayout(null);

        //busca tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds(0, 0, x, y);
        scrollPane.setBounds(0, 35, x, y - 100);
        //barra de menus
        menuBar.setBounds(0, 0, x, 35);
        //menus
        openBoxMenu.setBounds(0, 0, 80, 35);
        userMenu.setBounds(80, 0, 70, 35);
        deleteBoxMenu.setBounds(150, 0, 100, 35);
        searchMenu.setBounds(250, 0, 60, 35);
        withdrawawMenu.setBounds(310, 0, 75, 35);
        systemMenu.setBounds(385, 0, 65, 35);
        helpMenu.setBounds(450, 0, 50, 35);

        //itens do menu usuários
        registerMenuItem.setBounds(80, 35, 100, 30);
        managerMenuItem.setBounds(80, 65, 100, 30);
        //itens do menu excluir caixas
        listMenuItem.setBounds(150, 35, 100, 30);
        //itens do menu buscar
        searchMenuItem.setBounds(250, 35, 100, 30);
        //itens do menu retiradas
        requestMenuItem.setBounds(310, 65, 100, 30);
        withdrawalMenuItem.setBounds(310, 95, 100, 30);
        deliverMenuItem.setBounds(310, 125, 100, 30);
        historyMenuItem.setBounds(310, 155, 100, 30);
        //itens do menu sistema
        logoutMenuItem.setBounds(358, 35, 100, 30);
        optionsMenuItem.setBounds(385, 65, 100, 30);
        homeScreenmenuItem.setBounds(385, 95, 100, 30);
        //itens do menu ajuda
        manualItemMenu.setBounds(450, 35, 100, 30);
        aboutMenuItem.setBounds(450, 65, 100, 30);

        //configurações visuais
        openBoxMenu.setFont(FontFactory.getFontDefault());
        userMenu.setFont(FontFactory.getFontDefault());
        deleteBoxMenu.setFont(FontFactory.getFontDefault());
        searchMenu.setFont(FontFactory.getFontDefault());
        withdrawawMenu.setFont(FontFactory.getFontDefault());
        systemMenu.setFont(FontFactory.getFontDefault());
        helpMenu.setFont(FontFactory.getFontDefault());
        fMenuItem.setFont(FontFactory.getFontDefault());
        fcMenuItem.setFont(FontFactory.getFontDefault());
        exMenuItem.setFont(FontFactory.getFontDefault());
        aeMenuItem.setFont(FontFactory.getFontDefault());
        ccMenuItem.setFont(FontFactory.getFontDefault());
        fpMenuItem.setFont(FontFactory.getFontDefault());
        mcMenuItem.setFont(FontFactory.getFontDefault());
        dpMenuItem.setFont(FontFactory.getFontDefault());
        saeMenuItem.setFont(FontFactory.getFontDefault());
        aMenuItem.setFont(FontFactory.getFontDefault());
        registerMenuItem.setFont(FontFactory.getFontDefault());
        managerMenuItem.setFont(FontFactory.getFontDefault());
        listMenuItem.setFont(FontFactory.getFontDefault());
        searchMenuItem.setFont(FontFactory.getFontDefault());
        requestMenuItem.setFont(FontFactory.getFontDefault());
        withdrawalMenuItem.setFont(FontFactory.getFontDefault());
        deliverMenuItem.setFont(FontFactory.getFontDefault());
        historyMenuItem.setFont(FontFactory.getFontDefault());
        logoutMenuItem.setFont(FontFactory.getFontDefault());
        optionsMenuItem.setFont(FontFactory.getFontDefault());
        homeScreenmenuItem.setFont(FontFactory.getFontDefault());
        manualItemMenu.setFont(FontFactory.getFontDefault());
        aboutMenuItem.setFont(FontFactory.getFontDefault());
        secretaryMenu.setFont(FontFactory.getFontDefault());
        researchMenu.setFont(FontFactory.getFontDefault());
        extentionMenu.setFont(FontFactory.getFontDefault());
        saeMenu.setFont(FontFactory.getFontDefault());
        directionMenu.setFont(FontFactory.getFontDefault());
        personnelDepartmentMenu.setFont(FontFactory.getFontDefault());
        bookkeepingMenu.setFont(FontFactory.getFontDefault());
        financialMenu.setFont(FontFactory.getFontDefault());
        warehouseMenu.setFont(FontFactory.getFontDefault());
        dgMenuItem.setFont(FontFactory.getFontDefault());
        dacMenuItem.setFont(FontFactory.getFontDefault());
        dadMenuItem.setFont(FontFactory.getFontDefault());
        dsgMenuItem.setFont(FontFactory.getFontDefault());

        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        //junção de componentes
        frame.setContentPane(backgroundPanel);
        scrollPane.setBackground(Color.WHITE);
        frame.add(scrollPane);

        //adiciona itens de menu ao menu 
        openBoxMenu.add(secretaryMenu);
        secretaryMenu.add(fMenuItem);
        secretaryMenu.add(exMenuItem);
        secretaryMenu.add(aeMenuItem);
        secretaryMenu.add(ccMenuItem);
        secretaryMenu.add(fpMenuItem);
        openBoxMenu.add(researchMenu);
        openBoxMenu.add(extentionMenu);
        openBoxMenu.add(saeMenu);
        saeMenu.add(saeMenuItem);
        openBoxMenu.add(directionMenu);
        openBoxMenu.add(personnelDepartmentMenu);
        personnelDepartmentMenu.add(dpMenuItem);
        openBoxMenu.add(bookkeepingMenu);
        bookkeepingMenu.add(mcMenuItem);
        openBoxMenu.add(financialMenu);
        financialMenu.add(fcMenuItem);
        openBoxMenu.add(warehouseMenu);
        warehouseMenu.add(aMenuItem);
        directionMenu.add(
                dgMenuItem);
        directionMenu.add(dacMenuItem);
        directionMenu.add(dadMenuItem);
        directionMenu.add(dsgMenuItem);
        //adiciona itens de menu ao menu usuário
        userMenu.add(registerMenuItem);
        userMenu.add(managerMenuItem);
        //adicona itens demenus ao menu deletar caixa
        deleteBoxMenu.add(listMenuItem);
        //adiciona itens de menus ao menu busca
        searchMenu.add(searchMenuItem);
        //adiciona itens de menus ao menu retiradas
        withdrawawMenu.add(requestMenuItem);
        withdrawawMenu.add(withdrawalMenuItem);
        withdrawawMenu.add(deliverMenuItem);
        withdrawawMenu.add(historyMenuItem);
        //adiciona itens de menus ao menu sistema
        systemMenu.add(logoutMenuItem);
        systemMenu.add(optionsMenuItem);
        systemMenu.add(homeScreenmenuItem);
        //adiciona itens de menus ao menu ajuda
        helpMenu.add(manualItemMenu);
        helpMenu.add(aboutMenuItem);
        //adiciona menus a barra de menus
        menuBar.add(openBoxMenu);
        menuBar.add(userMenu);
        menuBar.add(deleteBoxMenu);
        menuBar.add(searchMenu);
        menuBar.add(withdrawawMenu);
        menuBar.add(systemMenu);
        menuBar.add(helpMenu);
        //adiciona barra de menus
        frame.add(menuBar);

        //define visibilidade
        frame.setVisible(true);
        scrollPane.setVisible(true);

        //define método visual para o programa
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenu menu = ((JMenu) e.getSource());
                if (!menu.isPopupMenuVisible()) {
                    menu.setBorder(BorderFactory.createRaisedSoftBevelBorder());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JMenu) e.getSource()).setBorder(null);
            }
        };
        openBoxMenu.addMouseListener(mouseListener);
        userMenu.addMouseListener(mouseListener);
        deleteBoxMenu.addMouseListener(mouseListener);
        searchMenu.addMouseListener(mouseListener);
        withdrawawMenu.addMouseListener(mouseListener);
        systemMenu.addMouseListener(mouseListener);
        helpMenu.addMouseListener(mouseListener);

        //configurações finais do frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }//fim do método createView

    /**
     * Busca o tamanho da tela
     */
    private void takeScreenSize() {
        Preferences preferences = PreferencesManager.getPreferencesSystem();
        x = preferences.getInt(PreferencesManager.KEY_SCREEN_WIDTH, 0);
        y = preferences.getInt(PreferencesManager.KEY_SCREEN_HEIGTH, 0);
        if (x == 0 || y == 0) {
            ScreenResolution resolution = new ScreenResolution();
            x = resolution.getX();
            y = resolution.getY();
        }
    }//fim do método takeScreenSize

    /**
     * Muda panel interior
     *
     * @param panel novo panel
     */
    public void changePanel(JPanel panel) {
        if (panel != null) {

            //atualiza panel
            insidePanel = panel;
            //configurações
            insidePanel.setLocation(0, 0);
            insidePanel.setSize(x, y);

            //adiciona panel
            scrollPane.setViewportView(insidePanel);
            scrollPane.setVisible(true);
        }
    }//fim do método changePanel

    /**
     * Define visibilidade do frame principal
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }//fim do métodosetVisible

    /**
     * Fecha o frame principal
     */
    public void close() {
        frame.dispose();
    }//fim do método exit

    /**
     * Define se frame está ativo
     *
     * @param enable verdadeiro-ativar falso-desativar
     */
    public void enableFrame(boolean enable) {
        frame.setEnabled(enable);
    }//fim do método enableFrame

    /**
     * Define ouvinte para menu de formandos
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerFMenuItem(ActionListener actionListener) {
        fMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerFMenuItem

    /**
     * Define ouvinte para menu de financero e cobrança
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerFCMenuItem(ActionListener actionListener) {
        fcMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerFCMenuItem

    /**
     * Define ouvinte para menu de ex-alunos
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerEXMenuItem(ActionListener actionListener) {
        exMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerEXMenuItem

    /**
     * Define ouvinte para menu de atas e exames
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerAEMenuItem(ActionListener actionListener) {
        aeMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerAEMenuItem

    /**
     * Define ouvinte para menu de cadernos de chamada
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerCCMenuItem(ActionListener actionListener) {
        ccMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerCCMenuItem

    /**
     * Define ouvinte para menu de formandos pós-graduação
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerFPMenuItem(ActionListener actionListener) {
        fpMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerFPMenuItem

    /**
     * Define ouvinte para menu de movimentação de caixa
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerMCMenuItem(ActionListener actionListener) {
        mcMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerMCMenuItem

    /**
     * Define ouvinte para menu de departamento pessoal
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDPMenuItem(ActionListener actionListener) {
        dpMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerDPMenuItem

    /**
     * Define ouvinte para menu de filantropia
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerSAEMenuItem(ActionListener actionListener) {
        saeMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerSAEMenuItem

    /**
     * Define ouvinte para menu de almoxarifado
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerAMenuItem(ActionListener actionListener) {
        aMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerAMenuItem 

    /**
     * Define ouvinte para menu de diretor geral
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDGMenuItem(ActionListener actionListener) {
        dgMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerdgMenuItem

    /**
     * Define ouvinte para menu de diretor academico
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDACMenuItem(ActionListener actionListener) {
        dacMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerdacMenuItem

    /**
     * Define ouvinte para menu de direção administrativa
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDADMenuItem(ActionListener actionListener) {
        dadMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerDADMenuItem

    /**
     * Define ouvinte para menu de secretaria do gabinete
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDSGMenuItem(ActionListener actionListener) {
        dsgMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerdsgMenuItem

    /**
     * Define ouvinte para menu de registro de usuário
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerRegisterMenuItem(ActionListener actionListener) {
        registerMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerRegisterMenuItem

    /**
     * Define ouvinte para menu de gerênciamento de usuário
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerManagerMenuItem(ActionListener actionListener) {
        managerMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerManagerMenuItem

    /**
     * Define ouvinte para menu de listar caixas para exclusão
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerEraseListMenuItem(ActionListener actionListener) {
        listMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerListMenuItem

    /**
     * Define ouvinte para menu de busca
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerSearchMenuItem(ActionListener actionListener) {
        searchMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerSearchMenuItem

    /**
     * Define ouvinte para menu de solicitações de documentos
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerRequestMenuItem(ActionListener actionListener) {
        requestMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerRequestMenuItem

    /**
     * Define ouvinte para menu de retiradas de documentos
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerWithdrawalMenuItem(ActionListener actionListener) {
        withdrawalMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerWithdrawalMenuItem

    /**
     * Define ouvinte para menu de entrega de documentos
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerDeliverMenuItem(ActionListener actionListener) {
        deliverMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerDeliverMenuItem

    /**
     * Define ouvinte para menu de histórico de reiradas
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerHistoryMenuItem(ActionListener actionListener) {
        historyMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerHistoryMenuItem

    /**
     * Define ouvinte para menu de logout
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerLogoutMenuItem(ActionListener actionListener) {
        logoutMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerLogoutMenuItem

    /**
     * Define ouvinte para menu de opções
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerOptionsMenuItem(ActionListener actionListener) {
        optionsMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerOptionsMenuItem

    /**
     * Define ouvinte para menu de mostrar tela inicial
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerHomeScreenMenuItem(ActionListener actionListener) {
        homeScreenmenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerHomeScreenMenuItem

    /**
     * Define ouvinte para menu de mostrar manual do usuário
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerManualItemMenu(ActionListener actionListener) {
        manualItemMenu.addActionListener(actionListener);
    }//fim do método setActionListenerManualItemMenu

    /**
     * Define ouvinte para menu sobre
     *
     * @param actionListener ouvinte de evento
     */
    public void setActionListenerAboutMenuItem(ActionListener actionListener) {
        aboutMenuItem.addActionListener(actionListener);
    }//fim do método setActionListenerAboutMenuItem

    /**
     * Define evento ao fechar janela
     *
     * @param windowAdapter
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Requer foco para o frame
     */
    public void requestFrameFocus() {
        frame.requestFocus();
        if (insidePanel != null) {
            insidePanel.requestFocus();
        }
    }//fim do método requestFrameFocus

    // Método para teste da interface
    public static void main(String args[]) {
        AdministratorFrame administratorFrame = new AdministratorFrame();
        administratorFrame.changePanel(new WallpaperPanel(1));
    }//fim do método main
}//fim da classe AdministratorFrame

