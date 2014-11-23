
/*-
 * Classname:             UserManagerPanel.java
 *
 * Version information:   1.0
 *
 * Date:                  01/04/2013 - 14:43:47
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
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Panel de gerência de usuário
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class UserManagerPanel {

    //modelo da tabela
    private UserManagerTableModel usermanagerTableModel;
    //paenl principal
    private JPanel panel;
    //scrollPane para tabela
    private JScrollPane scrollPane;
    //tabela
    private JTable table;
    //label com título do panel
    private JLabel titleLabel;
    //botão adicionar
    private JButton addButton;
    //botão editar
    private JButton editButton;
    //botão excluir
    private JButton eraseButton;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor sem parametros
     */
    public UserManagerPanel() {
        usermanagerTableModel = new UserManagerTableModel();
        createView();
    }//fim do construtor sem parametros

    /**
     * Construtor com lista de usuários
     *
     * @param users lista com usuários
     */
    public UserManagerPanel(List<User> users) {
        usermanagerTableModel = new UserManagerTableModel(users);
        createView();
    }//fim do construtor com lista de usuários

    /**
     * Cria a visão
     */
    private void createView() {
        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(usermanagerTableModel);
        titleLabel = new JLabel("Gerenciamento de Usuários");
        addButton = new JButton("Adicionar");
        editButton = new JButton("Editar");
        eraseButton = new JButton("Excluir");

        //pega tamanho da tela e define layout 
        panel.setLayout(null);
        takeScreenSize();

        //define tamanho e posição dos componentes
        panel.setBounds(0, 0, x - 150, y - 200);
        scrollPane.setBounds(50, 60, x - 450, y - 200);
        titleLabel.setBounds((x - 300) / 2, 10, 300, 30);
        table.setBounds(0, 0, x - 400, y);
        addButton.setBounds(x - 300, 100, 200, 30);
        editButton.setBounds(x - 300, 150, 200, 30);
        eraseButton.setBounds(x - 300, 200, 200, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 1) {
                int sizeColumnCode = (x - 500) / 5;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
            if (i == 2) {
                int sizeColumnCode = (x - 500) / 5;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
            if (i == 3) {
                int sizeColumnCode = (x - 500) / 5;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
        }

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        addButton.setFont(FontFactory.getFontLarge());
        editButton.setFont(FontFactory.getFontLarge());
        eraseButton.setFont(FontFactory.getFontLarge());
        titleLabel.setFont(FontFactory.getFontLarge());

        //dicas
        addButton.setToolTipText("Abre janela com formulário para cadastro de usuários");
        editButton.setToolTipText("Abre janela com formulário para edição de usuários");
        eraseButton.setToolTipText("Finaliza a retirada e gera o relatório");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(addButton);
        panel.add(eraseButton);
        panel.add(editButton);
        panel.add(titleLabel);

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
     * @param users lista com usuários
     */
    public void refresh(List<User> users) {
        usermanagerTableModel.refresh(users);
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
     * Define evento para o botão adicionar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerAddButton(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }//fim do método setActionListenerAddButton

    /**
     * Define evento para o botão editar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerEditButton(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }//fim do método setActionListenerEditButton

    /**
     * Define evento para o botão excluir
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerEraseButton(ActionListener actionListener) {
        eraseButton.addActionListener(actionListener);
    }//fim do método setActionListenerEraseButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_USER);
    }//fim do método getColor

    //metodo para teste
    public static void main(String args[]) {
        List<User> users = new ArrayList();
        users.add(new User("nome", "login", "titulo", "tipo"));
        UserManagerPanel userManagerPanel = new UserManagerPanel(users);
        AdministratorFrame administratorFrame = new AdministratorFrame();
        administratorFrame.changePanel(userManagerPanel.getPanel());
    }

    /**
     * Obtêm usuário selecionado
     *
     * @return <code>User</code> usuário
     */
    public User getSelectedUser() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return usermanagerTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
    }//fim do mérodo getSelectedUser

    /**
     * Define usuário selecionado
     *
     * @param selectedCodeUser
     */
    public void setSelectedUser(int selectedCodeUser) {
        if (selectedCodeUser > 0) {
            int row = usermanagerTableModel.getBoxPosition(selectedCodeUser);
            if (row != -1) {
                table.setRowSelectionInterval(row, row);
            } else {
                table.clearSelection();
            }

        }
    }//fim do método setSelectedUser
}//fim da classe UserManagerPanel

