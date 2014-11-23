/*-
 * Classname:             RequestsDefaultUserPanel.java
 *
 * Version information:   1.0
 *
 * Date:                  29/04/2013 - 13:19:10
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Visão com solicitação
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestsDefaultUserPanel {

    //panel principal
    private JPanel panel;
    //panel rolavel para tabela
    private JScrollPane scrollPane;
    //tabela
    private JTable table;
    //título
    private JLabel titlePanelLabel;
    //botão retirar
    private JButton withdrawButton;
    //botão retirar
    private JButton eraseButton;
    //mais informaç~oes
    private JButton moreButton;
    //modelo da tabela
    private RequestsDefaultUserTableModel tableModel;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Contrutor sem parâmetros
     */
    public RequestsDefaultUserPanel() {
        tableModel = new RequestsDefaultUserTableModel();
        createView();
    }//fim do construtor

    /**
     * Contrutor com lista de solicitações
     *
     * @param requests solicitações
     */
    public RequestsDefaultUserPanel(List<Request> requests) {
        tableModel = new RequestsDefaultUserTableModel(requests);
        createView();
    }//fim do construtorcom lista de solicitações

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        titlePanelLabel = new JLabel("Solicitações");
        withdrawButton = new JButton("Retirar");
        eraseButton = new JButton("Apagar");
        moreButton = new JButton("Mais informações");

        //pega tamanho da tela e define layout 
        panel.setLayout(null);
        takeScreenSize();
        titlePanelLabel.setHorizontalAlignment(JLabel.CENTER);

        //define tamanho e posição dos componentes
        panel.setBounds(0, 0, x - 150, y - 200);
        scrollPane.setBounds(50, 60, x - 450, y - 200);
        titlePanelLabel.setBounds((x - 300) / 2, 10, 300, 30);
        table.setBounds(0, 0, x - 400, y);
        withdrawButton.setBounds(x - 300, 50, 200, 30);
        eraseButton.setBounds(x - 300, 100, 200, 30);
        moreButton.setBounds(x - 300, 150, 200, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = (x - 500) / 5;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
            if (i == 1) {
                int sizeColumnCode = (x - 500) / 3;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
        }

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        withdrawButton.setFont(FontFactory.getFontLarge());
        eraseButton.setFont(FontFactory.getFontLarge());
        titlePanelLabel.setFont(FontFactory.getFontLarge());
        moreButton.setFont(FontFactory.getFontLarge());

        //dicas
        withdrawButton.setToolTipText("Abre janela para retirada de documentos");
        eraseButton.setToolTipText("Cancela a solicitação e a apaga da lista");
        moreButton.setToolTipText("Abre janela com detalhes da solicitação");
        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(withdrawButton);
        panel.add(eraseButton);
        panel.add(titlePanelLabel);
        panel.add(moreButton);

        //define panel como visivel
        panel.setVisible(true);

    }//fim do método createViewAdministrator

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
     * Atualiza tabela
     *
     * @param requests lista de solicitações
     */
    public void refresh(List<Request> requests) {
        tableModel.refresh(requests);
        table.repaint();
    }//fim do método refresh

    /**
     * Obtêm panel
     *
     * @return <code>JPanel</code> de gerenciamento de usuários
     */
    public JPanel getPanel() {
        return panel;
    }//fim do método getPanel

    /**
     * Define evento para o botão retirar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerWithdrawButton(ActionListener actionListener) {
        withdrawButton.addActionListener(actionListener);
    }//fim do método setActionListenerWithdrawButton

    /**
     * Define evento para o botão apagar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerEraseButton(ActionListener actionListener) {
        eraseButton.addActionListener(actionListener);
    }//fim do método setActionListenerSearchButton

    /**
     * Define evento para o botão mais informações
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerMoreButton(ActionListener actionListener) {
        moreButton.addActionListener(actionListener);
    }//fim do método setActionListenerEditButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_REQUEST);
    }//fim do método getColor

    /**
     * Pega a solicitação selecionada
     *
     * @return <code>Request</code> solicitação
     */
    public Request getSelectedRequest() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return tableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));

    }//fim do método getSelectedBox

    /**
     * Muda Label do Botão retirar
     */
    public void changeForRequestMode() {
        withdrawButton.setText("Solicitar");
    }//fim do método changeForRequestMode

    //metodo para teste
    public static void main(String args[]) {

        RequestsDefaultUserPanel requestsDefaultUserPanel = new RequestsDefaultUserPanel();
        AdministratorFrame administratorFrame = new AdministratorFrame();
        administratorFrame.changePanel(requestsDefaultUserPanel.getPanel());
    }

    public void setKeyListenerFrame(KeyAdapter keyAdapter) {
        panel.setFocusable(true);
        panel.addKeyListener(keyAdapter);
        table.addKeyListener(keyAdapter);
        withdrawButton.addKeyListener(keyAdapter);
        eraseButton.addKeyListener(keyAdapter);
    }
}//fim da classe RequestsDefaultUserPanel 

