
/*-
 * Classname:             BoxPanel.java
 *
 * Version information:   1.01
 *
 * Date:                  15/03/2013 - 13:15:54
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Panel de visualização de caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxPanel {

    //panel principal
    private JPanel panel;
    //scrollPane que contêm a janela
    private JScrollPane scrollPane;
    //título da caixa
    private JLabel titleLabel;
    //label do icone
    private JLabel iconLabel;
    //botão abrir
    private JButton openButton;
    //botão adicionar
    private JButton addButton;
    //botão editar
    private JButton editButton;
    //botão excluir
    private JButton eraseButton;
    //botão gerar lombada
    private JButton generateSpine;
    //botão gerar etiqueta
    private JButton generateTag;
    //botão retirar 
    private JButton withdrawButton;
    //botão solicitação
    private JButton requestButton;
    //botão ordenar
    private JButton sortButton;
    //botão subir
    private JButton upButton;
    //botão descer 
    private JButton downButton;
    //botão generate sheet(gerar ficha)
    private JButton generateSheetButton;
    //tabela com caixas
    private JTable table;
    //modelo da tebela
    private BoxTableModel boxTableModel;
    //largura da tela
    private int x;
    //comprimento da tela
    private int y;
    //título da caixa
    private String title;
    //lista de caixas
    private List<Box> boxes;
    //icone de caixa
    private ImageIcon boxIcon;
    //categoria 
    private Category category;

    /**
     * Construtor do panel para visualização de caixas
     *
     * @param category categoria
     * @param boxes caixas
     * @param title título do panel (categoria da caixa)
     * @param user usuário
     */
    public BoxPanel(Category category, List<Box> boxes, String title, User user) {
        this.category = category;
        boxTableModel = new BoxTableModel(boxes);
        this.boxes = boxes;
        this.title = title;
        if (user instanceof Administrator) {
            createViewAdministrator();
        } else {
            createViewDefaultUser();
        }
    }//fim do construtor

    /**
     * Cria a visão do panel para Administradores
     */
    private void createViewAdministrator() {

        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(boxTableModel);
        String separator = System.getProperty("file.separator");
        boxIcon = new ImageIcon("image" + separator + "box.png");
        sortButton = new JButton(new ImageIcon("image" + separator + "sort.png"));
        upButton = new JButton(new ImageIcon("image" + separator + "up.png"));
        downButton = new JButton(new ImageIcon("image" + separator + "down.png"));
        iconLabel = new JLabel();
        iconLabel.setIcon(boxIcon);
        titleLabel = new JLabel(title);
        openButton = new JButton("Abrir");
        addButton = new JButton("Adicionar");
        editButton = new JButton("Editar");
        eraseButton = new JButton("Excluir");
        generateSpine = new JButton("Gerar Lombada");
        generateTag = new JButton("Gerar Etiquetas");
        withdrawButton = new JButton("Retirar");
        generateSheetButton = new JButton("Gerar Ficha");

        //pega tamanho da tela e define layout 
        panel.setLayout(null);
        takeScreenSize();


        //define tamanho e posição dos componentes
        panel.setBounds(0, 0, x - 150, y - 200);
        scrollPane.setBounds(50, 60, x - 450, y - 200);
        table.setBounds(0, 0, x - 400, y);
        iconLabel.setBounds(((x - 100) / 2) - 110, 2, 50, 50);
        titleLabel.setBounds((x - 200) / 2, 10, 200, 30);
        openButton.setBounds(x - 300, 50, 200, 30);
        addButton.setBounds(x - 300, 100, 200, 30);
        editButton.setBounds(x - 300, 150, 200, 30);
        eraseButton.setBounds(x - 300, 200, 200, 30);
        generateSpine.setBounds(x - 300, 250, 200, 30);
        generateTag.setBounds(x - 300, 300, 200, 30);
        withdrawButton.setBounds(x - 300, 350, 200, 30);
        generateSheetButton.setBounds(x - 300, 400, 200, 30);
        sortButton.setBounds(x - 225, 450, 50, 50);
        upButton.setBounds(x - 225, 520, 50, 50);
        downButton.setBounds(x - 225, 590, 50, 50);

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontLarge());
        openButton.setFont(FontFactory.getFontLarge());
        addButton.setFont(FontFactory.getFontLarge());
        editButton.setFont(FontFactory.getFontLarge());
        eraseButton.setFont(FontFactory.getFontLarge());
        generateSpine.setFont(FontFactory.getFontLarge());
        generateTag.setFont(FontFactory.getFontLarge());
        withdrawButton.setFont(FontFactory.getFontLarge());
        generateSheetButton.setFont(FontFactory.getFontLarge());

        //dicas
        openButton.setToolTipText("Abre a caixa selecionada");
        addButton.setToolTipText("Abre formulário para cadastro de caixas");
        editButton.setToolTipText("Abre formulário para edição da caixa selecionada");
        eraseButton.setToolTipText("Deleta a caixa selecionada");
        generateSpine.setToolTipText("Gera arquivo com lombada da caixa");
        generateTag.setToolTipText("Gera arquivo com etiquetas dos livros da caixa");
        withdrawButton.setToolTipText("Abre formulário para retirada de documento");
        generateSheetButton.setToolTipText("Gera ficha com o conteúdo da caixa");
        sortButton.setToolTipText("<HTML>Ordena caixas pelo título.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos das caixas!</b></font></HTML>");
        upButton.setToolTipText("<HTML>Diminui em 1 o código da caixa e eleva sua posição.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos das caixas!</b></font></HTML>");
        downButton.setToolTipText("<HTML>Aumenta em 1 o código da caixa e abaixa sua posição.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos das caixas!<b></font></HTML>");



        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(iconLabel);
        panel.add(titleLabel);
        panel.add(openButton);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(eraseButton);
        panel.add(generateSpine);
        panel.add(generateTag);
        panel.add(withdrawButton);
        panel.add(generateSheetButton);
        panel.add(sortButton);
        panel.add(upButton);
        panel.add(downButton);

        //define panel como visivel
        panel.setVisible(true);
    }//fim do método createViewAdministrator

    /**
     * Cria a visão do panel para usuários padrão
     */
    private void createViewDefaultUser() {

        //iniicaliza componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(boxTableModel);
        String separator = System.getProperty("file.separator");
        boxIcon = new ImageIcon("image" + separator + "box.png");
        iconLabel = new JLabel();
        iconLabel.setIcon(boxIcon);
        titleLabel = new JLabel(title);
        openButton = new JButton("Abrir");
        requestButton = new JButton("Solicitar");

        //define layout e pega tamanho da tela
        panel.setLayout(null);
        takeScreenSize();

        //define tamanho e localização dos componentes
        panel.setBounds(0, 0, x - 150, y - 200);
        scrollPane.setBounds(50, 60, x - 450, y - 200);
        table.setBounds(0, 0, x - 400, y);
        iconLabel.setBounds(((x - 100) / 2) - 110, 2, 50, 50);
        titleLabel.setBounds((x - 200) / 2, 10, 200, 30);
        openButton.setBounds(x - 300, 50, 200, 30);
        requestButton.setBounds(x - 300, 100, 200, 30);

        //define cor e estilo da fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontLarge());
        openButton.setFont(FontFactory.getFontLarge());
        requestButton.setFont(FontFactory.getFontLarge());

        table.setAutoCreateRowSorter(true);

        //adiciona componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(iconLabel);
        panel.add(titleLabel);
        panel.add(openButton);
        panel.add(requestButton);

        //define panel como visivel
        panel.setVisible(true);
    }//fim do método createViewDefaultUser

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
     * Retorna o panel principal criiado
     *
     * @return <code>JPanel</code> panel principal
     */
    public JPanel getPanel() {
        return panel;
    }//fim do método getPanel

    /**
     * Atualiza tabela
     *
     * @param boxes lista com caixas
     */
    public void refresh(List<Box> boxes) {
        boxTableModel.refresh(boxes);
        table.repaint();
    }//fim do método refresh

    /**
     * Define evento para o botão abrir
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerOpenButton(ActionListener actionListener) {
        openButton.addActionListener(actionListener);
    }//fim do método setActionListenerOpenButton

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
     * Define evento para o botão apagar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerEraseButton(ActionListener actionListener) {
        eraseButton.addActionListener(actionListener);
    }//fim do método setActionListenerEraseButton

    /**
     * Define evento para o botão gerar lombada
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerGenerateSpine(ActionListener actionListener) {
        generateSpine.addActionListener(actionListener);
    }//fim do método setActionListenerGenerateSpine

    /**
     * Define evento para o botão gerar ficha
     *
     * @param actionlistener ouvinte
     */
    public void setActionListenerGenerateSheetButton(ActionListener actionlistener) {
        generateSheetButton.addActionListener(actionlistener);
    }//fim do método setActionListenerGenerateSpine

    /**
     * Define evento para o botão gerar etiqueta
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerGenerateTag(ActionListener actionListener) {
        generateTag.addActionListener(actionListener);
    }//fim do método setActionListenerGenerateTag

    /**
     * Define evento para o botão retirar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerWithdrawButton(ActionListener actionListener) {
        withdrawButton.addActionListener(actionListener);
    }//fim do método setActionListenerWithdrawButton

    /**
     * Define evento para o botão solicitar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerRequestButton(ActionListener actionListener) {
        requestButton.addActionListener(actionListener);
    }//fim do método setActionListenerRequestButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorFactory colorFactory = new ColorFactory(category);
        return colorFactory.getBoxColor();
    }//fim do método getColor

    /**
     * Define evento para o botão ordenar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerSortButton(ActionListener actionListener) {
        sortButton.addActionListener(actionListener);
    }//fim do método setActionListenerSortButton

    /**
     * Define evento para o botão ordenar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerUPButton(ActionListener actionListener) {
        upButton.addActionListener(actionListener);
    }//fim do método setActionListenerUPButton

    /**
     * Define evento para o botão ordenar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerDownButton(ActionListener actionListener) {
        downButton.addActionListener(actionListener);
    }//fim do método setActionListenerDownButton

    /**
     * Define evento para tabela
     *
     * @param mouseListener ouvinte
     */
    public void setMouseListenerTable(MouseListener mouseListener) {
        table.addMouseListener(mouseListener);
    }//fim do método setMouseListenerTable

    /**
     * Define listenr para visualização de caixa como tooltip
     *
     * @param mouseMotionListener evento para visualização de itens da tabela de
     * caixas
     */
    public void setVisualizeBoxListener(MouseMotionListener mouseMotionListener) {
        table.addMouseMotionListener(mouseMotionListener);
    }//fim do método setvisualizeBoxListener

    /**
     * Pega a caixa selecionada
     *
     * @return <code>Box</code> caixa
     */
    public Box getSelectedBox() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return boxTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));

    }//fim do método getSelectedBox

    /**
     * Obtêm a caixa da linha
     *
     * @return <code>Box</code> caixa
     */
    public Box getBox(int row) {
        if (row < 0) {
            return null;
        }
        return boxTableModel.getRow(table.convertRowIndexToModel(row));

    }//fim do método getBox

    //método para teste
    public static void main(String args[]) {
        AdministratorFrame administratorFrame = new AdministratorFrame();
        List<Box> boxes = new ArrayList();
        boxes.add(new Box("XP-45", " ", " ") {
        });
        BoxPanel boxPanel = new BoxPanel(Category.A, boxes, "X-00", new Administrator());
        administratorFrame.changePanel(boxPanel.getPanel());
    }

    /**
     * Seleciona caixa na table pelo código
     *
     * @param selectedCodeBox
     */
    public void setSelectedBox(int selectedCodeBox) {
        if (selectedCodeBox > 0) {
            int row = boxTableModel.getBoxPosition(selectedCodeBox);
            if (row != -1) {
                table.setRowSelectionInterval(table.convertRowIndexToView(row), table.convertRowIndexToView(row));
            } else {
                table.clearSelection();
            }

        }
    }//fim da classe setSelectedBox

    /**
     * Adiciona evento a todos os componestes focusables do panel
     *
     * @param keyListener ouvinte
     */
    public void setKeyLisyenerFrame(KeyListener keyListener) {
        panel.setFocusable(true);
        table.setFocusCycleRoot(true);
        table.addKeyListener(keyListener);
        scrollPane.addKeyListener(keyListener);
        openButton.addKeyListener(keyListener);
        if (requestButton != null) {
            requestButton.addKeyListener(keyListener);
        } else {
            addButton.addKeyListener(keyListener);
            editButton.addKeyListener(keyListener);
            eraseButton.addKeyListener(keyListener);
            generateSpine.addKeyListener(keyListener);
            generateTag.addKeyListener(keyListener);
            withdrawButton.addKeyListener(keyListener);
            generateSheetButton.addKeyListener(keyListener);
            sortButton.addKeyListener(keyListener);
            upButton.addKeyListener(keyListener);
            downButton.addKeyListener(keyListener);
        }
        panel.addKeyListener(keyListener);
    }//fim do método setKeyLisyenerFrame
}//fim da classe BoxPanel

