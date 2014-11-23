/*-
 * Classname:             BookPanel.java
 *
 * Version information:   1.0
 *
 * Date:                  18/03/2013 - 14:16:51
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableColumn;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Panel de visualização de livros
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookPanel {

    //panel principal
    private JPanel panel;
    //scrollpane
    private JScrollPane scrollPane;
    //modelo da tabela
    private BookTableModel bookTableModel;
    //tabela com livros
    private JTable table;
    //título do panel 
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
    //botão retirar
    private JButton withdrawalButton;
    //botão solicitar
    private JButton requestButton;
    //botão voltar
    private JButton backButton;
    //botão ordenar
    private JButton sortButton;
    //botão subir
    private JButton upButton;
    //botão descer 
    private JButton downButton;
    //título
    private String title;
    //lista de livros
    private List<Book> bookes;
    //largura
    private int x;
    //altura
    private int y;
    //icone de documento
    private ImageIcon bookIcon;
    //categoria
    private Category category;

    /**
     * Construtor com lista de livros, título da caixa e usuário
     *
     * @param category categoria
     * @param bookes lista de livros
     * @param title título da caixa
     * @param user usuário
     */
    public BookPanel(Category category, List<Book> bookes, String title, User user) {
        this.category = category;
        bookTableModel = new BookTableModel(bookes);
        this.bookes = bookes;
        this.title = title;

        //se for administrador cria o panel para administradores
        if (user instanceof Administrator) {
            createViewAdministrator();
        } else {
            createViewDefaultUser();
        }
    }//fim do construtor

    /**
     * Cria a visão para Administradores
     */
    private void createViewAdministrator() {
        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(bookTableModel);
        titleLabel = new JLabel(title);
        String separator = System.getProperty("file.separator");
        bookIcon = new ImageIcon("image" + separator + "book.png");
        sortButton = new JButton(new ImageIcon("image" + separator + "sort.png"));
        upButton = new JButton(new ImageIcon("image" + separator + "up.png"));
        downButton = new JButton(new ImageIcon("image" + separator + "down.png"));
        iconLabel = new JLabel();
        iconLabel.setIcon(bookIcon);
        openButton = new JButton("Abrir");
        addButton = new JButton("Adicionar");
        editButton = new JButton("Editar");
        eraseButton = new JButton("Excluir");
        withdrawalButton = new JButton("Retirar");
        backButton = new JButton("Voltar");

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
        withdrawalButton.setBounds(x - 300, 250, 200, 30);
        backButton.setBounds(x - 300, 300, 200, 30);
        sortButton.setBounds(x - 225, 350, 50, 50);
        upButton.setBounds(x - 225, 420, 50, 50);
        downButton.setBounds(x - 225, 490, 50, 50);


        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = (x - 500) / 6;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
        }

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontLarge());
        openButton.setFont(FontFactory.getFontLarge());
        addButton.setFont(FontFactory.getFontLarge());
        editButton.setFont(FontFactory.getFontLarge());
        eraseButton.setFont(FontFactory.getFontLarge());
        withdrawalButton.setFont(FontFactory.getFontLarge());
        backButton.setFont(FontFactory.getFontLarge());

        //dicas
        openButton.setToolTipText("Abre o livro selecionado");
        addButton.setToolTipText("Abre formulário para cadastro de livros");
        editButton.setToolTipText("Abre formulário para editar informações do livro selecionado");
        eraseButton.setToolTipText("Exclui livro selecionado");
        withdrawalButton.setToolTipText("Abre formulário para retirada do livro selecionado");
        backButton.setToolTipText("Voltar para as caixas");
        sortButton.setToolTipText("<HTML>Ordena livros pelo título.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos dos livros!</b></font></HTML>");
        upButton.setToolTipText("<HTML>Diminui em 1 o código do livro e eleva sua posição.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos dos livross!</b></font></HTML>");
        downButton.setToolTipText("<HTML>Aumenta em 1 o código do livro e abaixa sua posição.<br /><font color='red'><b>Atenção! Essa operação modificará os códigos dos livros!<b></font></HTML>");

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    Book book = bookTableModel.getRow(table.convertRowIndexToModel(row));
                    DAOFactory daoFactory = new DAOFactory(Category.getCategory(book));

                    Book bookSpecific = daoFactory.getDAO().searchBook(book.getCodeBook());

                    table.setToolTipText(bookSpecific.toString());
                } else {
                    table.setToolTipText("");
                }
            }
        });

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(titleLabel);
        panel.add(iconLabel);
        panel.add(openButton);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(eraseButton);
        panel.add(withdrawalButton);
        panel.add(backButton);
        panel.add(sortButton);
        panel.add(upButton);
        panel.add(downButton);

        //define panel como visivel
        panel.setVisible(true);

    }//fim do método createViewAdministrator

    /**
     * Cria a visão para usuários padrão
     */
    private void createViewDefaultUser() {
        //inicializa componentes
        panel = new JPanel();
        scrollPane = new JScrollPane();
        table = new JTable(bookTableModel);
        titleLabel = new JLabel(title);
        String separator = System.getProperty("file.separator");
        bookIcon = new ImageIcon("image" + separator + "book.png");
        iconLabel = new JLabel();
        iconLabel.setIcon(bookIcon);
        openButton = new JButton("Abrir");
        requestButton = new JButton("Solicitar");
        backButton = new JButton("Voltar");


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
        requestButton.setBounds(x - 300, 100, 200, 30);
        backButton.setBounds(x - 300, 150, 200, 30);


        //define o tamnho das colunas
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = (x - 500) / 6;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
        }

        //define cor e fonte
        panel.setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontLarge());
        openButton.setFont(FontFactory.getFontLarge());
        requestButton.setFont(FontFactory.getFontLarge());
        backButton.setFont(FontFactory.getFontLarge());

        //dicas
        openButton.setToolTipText("Abre o livro selecionado");
        requestButton.setToolTipText("Abre formulário para solicitação do livro selecionado");
        backButton.setToolTipText("Voltar para as caixas");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        panel.add(scrollPane);
        panel.add(titleLabel);
        panel.add(iconLabel);
        panel.add(openButton);
        panel.add(requestButton);
        panel.add(backButton);

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
    public void refresh(List<Book> bookes) {
        bookTableModel.refresh(bookes);
        table.repaint();
    }//fim do método refresh

    /**
     * Define evento para o botão abrir
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerOpenButton(ActionListener actionListener) {
        openButton.addActionListener(actionListener);
    }//fim do metodo setActionListenerOpenButton

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
     * Define evento para o botão retirar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerWithdrawalButton(ActionListener actionListener) {
        withdrawalButton.addActionListener(actionListener);
    }//fim do método setActionListenerWithdrawalButton

    /**
     * Define evento para o botão solicitar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerRequestButton(ActionListener actionListener) {
        requestButton.addActionListener(actionListener);
    }//fim do método setActionListenerRequestButton

    /**
     * Define evento para botão voltar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerBackButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }//fim do método setActionListenerBackButton

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorFactory colorFactory = new ColorFactory(category);
        return colorFactory.getBookColor();

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
     * Pega o livro selecionado
     *
     * @return <code>Book</code> livro
     */
    public Book getSelectedBook() {
        if (table.getSelectedRow() < 0) {
            return null;
        }
        return bookTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
    }//fim do método getSelectedBook

    //método para teste
    public static void main(String args[]) {
        AdministratorFrame administratorFrame = new AdministratorFrame();
        List<Book> bookes = new ArrayList();
        bookes.add(new Book("XP-45", 1, " ") {
        });
        BookPanel bookPanel = new BookPanel(Category.A, bookes, "45", new Administrator());
        administratorFrame.changePanel(bookPanel.getPanel());
    }

    /**
     * Define evento para tabela
     *
     * @param mouseListener ouvinte
     */
    public void setMouseListenerTable(MouseAdapter mouseAdapter) {
        table.addMouseListener(mouseAdapter);
    }//fim do método setMouseListenerTable

    /**
     * Adiciona evento de tecla
     *
     * @param keyListener ouvinte do evento
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
            openButton.addKeyListener(keyListener);
            addButton.addKeyListener(keyListener);
            editButton.addKeyListener(keyListener);
            eraseButton.addKeyListener(keyListener);
            withdrawalButton.addKeyListener(keyListener);
            sortButton.addKeyListener(keyListener);
            upButton.addKeyListener(keyListener);
            downButton.addKeyListener(keyListener);
        }
        backButton.addKeyListener(keyListener);
        panel.addKeyListener(keyListener);
    }//fim do método setKeyLisyenerFrame

    /**
     * Seleciona livro pelo código
     *
     * @param selectedCodeBook
     */
    public void setSelectedBook(int selectedCodeBook) {
        if (selectedCodeBook > 0) {
            int row = bookTableModel.getBookPosition(selectedCodeBook);
            if (row != -1) {
                table.setRowSelectionInterval(table.convertRowIndexToView(row), table.convertRowIndexToView(row));
            } else {
                table.clearSelection();
            }

        }
    }//fim do método setSelectedBook

    /**
     * Obtêm a livro da linha
     *
     * @return <code>Book</code> livro
     */
    public Book getBook(int row) {
        if (row < 0) {
            return null;
        }
        return bookTableModel.getRow(table.convertRowIndexToModel(row));

    }//fim do método getBook

    /**
     * Define listenr para visualização de livro como tooltip
     *
     * @param mouseMotionListener evento para visualização de itens da tabela de
     * caixas
     */
    public void setVisualizeBookListener(MouseMotionListener mouseMotionListener) {
        table.addMouseMotionListener(mouseMotionListener);
    }//fim do método setvisualizeBoxListener
}//fim da classe BookPanel

