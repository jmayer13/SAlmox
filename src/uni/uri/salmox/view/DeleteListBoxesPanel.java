/*-
 * Classname:             DeleteListBoxesPanel.java
 *
 * Version information:   1.0
 *
 * Date:                  23/04/2013 - 14:03:52
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela para exclusão de caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DeleteListBoxesPanel {

    //panel principal
    private JPanel panel;
    //panel rolavel para a tabela
    private JScrollPane scrollPane;
    //tabela
    private JTable table;
    //título
    private JLabel titlePanelLabel;
    //botão adicionar
    private JButton addButton;
    //botão pesquisar
    private JButton searchButton;
    //botão cancelar
    private JButton cancelButton;
    //botão gerar relatório
    private JButton generateReportButton;
    //modelo da tabela
    private DeleteListTableModel tableModel;
    //largura
    private int x;
    //comprimento
    private int y;

    /**
     * Contrutor sem parãmetros
     */
    public DeleteListBoxesPanel() {
        tableModel = new DeleteListTableModel();
        createView();
    }//fim do construtor

    /**
     * Construtor com lista de caixas
     *
     * @param boxes lista de caixas a excluir
     */
    public DeleteListBoxesPanel(List<Box> boxes) {
        tableModel = new DeleteListTableModel(boxes);
        createView();
    }//fim do construtor com lista de caixas

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        titlePanelLabel = new JLabel("Ata de Exclusão");
        addButton = new JButton("Adicionar");
        searchButton = new JButton("Buscar");
        cancelButton = new JButton("Remover");
        generateReportButton = new JButton("Gerar Relatório");

        //pega tamanho da tela e define layout 
        panel.setLayout(null);
        takeScreenSize();
        titlePanelLabel.setHorizontalAlignment(JLabel.CENTER);

        //define tamanho e posição dos componentes
        panel.setBounds(0, 0, x - 150, y - 200);
        scrollPane.setBounds(50, 60, x - 450, y - 200);
        titlePanelLabel.setBounds((x - 300) / 2, 10, 300, 30);
        table.setBounds(0, 0, x - 400, y);
        addButton.setBounds(x - 300, 100, 200, 30);
        searchButton.setBounds(x - 300, 150, 200, 30);
        cancelButton.setBounds(x - 300, 200, 200, 30);
        generateReportButton.setBounds(x - 325, y - 275, 250, 30);

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
                int sizeColumnCode = (x - 500) / 5;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }

        }

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        addButton.setFont(FontFactory.getFontLarge());
        searchButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());
        generateReportButton.setFont(FontFactory.getFontLarge());
        titlePanelLabel.setFont(FontFactory.getFontLarge());

        //dicas
        addButton.setToolTipText("Abre janela para seleção de caixas");
        searchButton.setToolTipText("Abre janela para busca de caixa");
        cancelButton.setToolTipText("Cancela exclusão da caixa selecionada");
        generateReportButton.setToolTipText("Gera o relatório e exclui as caixas");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(addButton);
        panel.add(searchButton);
        panel.add(cancelButton);
        panel.add(generateReportButton);
        panel.add(titlePanelLabel);

        //define panel como visivel
        panel.setVisible(true);

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
     * Atualiza tabela
     *
     * @param users lista com usuários
     */
    public void refresh(List<Box> boxes) {
        tableModel.refresh(boxes);
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
     * Define evento para o botão adiciomar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerAddButton(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }//fim do método setActionListenerAddButton

    /**
     * Define evento para o botão buscar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerSearchButton(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }//fim do método setActionListenerSearchButton

    /**
     * Define evento para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenerCancelButton

    /**
     * Define evento para o botão gerar relatório
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerGenerateReportButton(ActionListener actionListener) {
        generateReportButton.addActionListener(actionListener);
    }//fim do método setActionListenerGenerateReportButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_DELETE_LIST);
    }//fim do método getColor

    /**
     * Obt~em caixa selecionada
     *
     * @return <code>Box</code> caixa
     */
    public Box getSelectedBox() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return tableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
    }//fim do método getSelectedBox

    /**
     * Remove a caixada lista
     *
     * @param box caixa
     */
    public void removeBox(Box box) {
        tableModel.eraseRow(box);
    }//fim do método da removeBox

    /**
     * Adiciona caixa a lista
     *
     * @param box caixa
     */
    public void addBox(Box box) {
        tableModel.addRow(box);
    }//fim do método addBox

    /**
     * Adiciona lista de caixas a lista
     *
     * @param boxes lista de caixas
     */
    public void addBoxes(List<Box> boxes) {
        for (int i = 0; i < boxes.size(); i++) {
            tableModel.addRow(boxes.get(i));
        }
    }//fim do método addBoxes

    //metodo para teste
    public static void main(String args[]) {

        DeleteListBoxesPanel deleteListBoxesPanel = new DeleteListBoxesPanel();
        AdministratorFrame administratorFrame = new AdministratorFrame();
        administratorFrame.changePanel(deleteListBoxesPanel.getPanel());
    }

    /**
     * Obtêm caixas
     *
     * @return <code>List</code> lista com caixa
     */
    public List<Box> getBoxes() {
        List<Box> boxes = new ArrayList();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boxes.add(tableModel.getRow(i));
        }
        return boxes;
    }//fim do método getBoxes
}//fim da classe DeleteListBoxesPanel 

