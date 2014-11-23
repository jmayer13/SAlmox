/*-
 * Classname:             ConfigurationFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  06/05/2013 - 15:33:38
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;
import uni.uri.salmox.util.TemporaryData;

/**
 * Janela para configurações do sistema
 *
 * @see uni.uri.salmox.util.PreferencesManager
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ConfigurationFrame {

    //frame principal
    private JFrame frame;
    //abas
    private JTabbedPane tabbedPanel;
    //panels para configurações do sistema
    private JPanel systemPanel;
    private JScrollPane systemScrollPane;
    //panel para seleção de cores das janelas
    private JPanel colorPanel;
    private JScrollPane colorScrollPane;
    //panel para as demais configurações
    private JPanel otherPanel;
    private JScrollPane otherScrollPane;
    //botão OK
    private JButton okButton;
    //botão Cancelar
    private JButton cancelButton;
    //panel para a tabela de cores
    private JScrollPane scrollTable;
    //tabela de cores para as categorias das caixas
    private JTable colorTable;
    //modelo da tabela de cores 
    private ColorTableModel tableModel;
    //largura
    private int x;
    //altura
    private int y;
    //cor atual da célula selecionada
    private Color currentColor;
    //botão genérico para seleção de cores nas celulas
    private JButton button;
    //lista de cores das caixas
    private List<Color> colorsBoxes;
    //lista de cores dos livros
    private List<Color> colorsBooks;
    //lista de cores dos documentos
    private List<Color> colorsDocuments;
    //seleção de cores
    //label deletar lista
    private JLabel deleteListColorLabel;
    //botão deletar lista
    private JButton deleteListColorButton;
    //label entrega de documentos
    private JLabel devolutionColorLabel;
    //botão entrega de documentos
    private JButton devolutionColorButton;
    ////label historico
    private JLabel historyColorLabel;
    //botão historico
    private JButton historyColorButton;
    //label solicitação
    private JLabel requestColorLabel;
    //botão solicitação
    private JButton requestColorButton;
    //label buscar
    private JLabel searchColorLabel;
    //botão buscar
    private JButton searchColorButton;
    //label usuário
    private JLabel userColorLabel;
    //botão usuário
    private JButton userColorButton;
    //label retirada
    private JLabel withdrawalColorLabel;
    //botão retirada
    private JButton withdrawalColorButton;
    //configurações do sistema
    //label tela
    private JLabel screenLabel;
    //label resolução
    private JLabel resolitionLabel;
    //label X para tepresentar resolução Ex: 1024x768
    private JLabel xLabel;
    //campo comprimento x
    private JTextField xField;
    //campo altura y
    private JTextField yField;
    //botão atualizar tamanho da tela
    private JButton refreshButton;
    //label banco de dados
    private JLabel dataBaseLabel;
    //label local do banco
    private JLabel ipBaseLabel;
    //user usuário
    private JLabel userLabel;
    //label senha
    private JLabel passwordLabel;
    //campo ip/local do banco de dados
    private JTextField ipBaseField;
    //campo usuário
    private JTextField userField;
    //campo senha
    private JPasswordField passwordFeild;
    //caixa para ativação do plano de fundo ou não
    private JCheckBox wallpaperCheckBox;

    /**
     * Construtor com lista das cores
     *
     * @param colorsBoxes lista de cores de caixa por categoria
     * @param colorsBooks lista de cores de livros por categoria
     * @param colorsDocuments lista das cores de documentos por categotioa
     */
    public ConfigurationFrame() {
        createView();
        fiilsView();
    }//fim do construtor

    /**
     * Constroi visão
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame("Configurações");
        tabbedPanel = new JTabbedPane();
        systemPanel = new JPanel();
        systemScrollPane = new JScrollPane(systemPanel);
        colorPanel = new JPanel();
        colorScrollPane = new JScrollPane(colorPanel);
        otherPanel = new JPanel();
        otherScrollPane = new JScrollPane(otherPanel);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancelar");

        //inicializa componentes da aba cores
        tableModel = new ColorTableModel();
        colorTable = new JTable(tableModel);
        scrollTable = new JScrollPane(colorTable);
        deleteListColorLabel = new JLabel("Excluir lista:");
        deleteListColorButton = new JButton();
        devolutionColorLabel = new JLabel("Entrega:");
        devolutionColorButton = new JButton();
        historyColorLabel = new JLabel("Histórico:");
        historyColorButton = new JButton();
        requestColorLabel = new JLabel("Solicitação:");
        requestColorButton = new JButton();
        searchColorLabel = new JLabel("Buscar:");
        searchColorButton = new JButton();
        userColorLabel = new JLabel("Usuário:");
        userColorButton = new JButton();
        withdrawalColorLabel = new JLabel("Retirada:");
        withdrawalColorButton = new JButton();

        //inicializa componentes da aba sistema
        screenLabel = new JLabel("Tela");
        JSeparator screenSeparator = new JSeparator();
        resolitionLabel = new JLabel("Resolução:");
        xLabel = new JLabel("X");
        xField = new JTextField();
        yField = new JTextField();
        refreshButton = new JButton("Atualizar");
        dataBaseLabel = new JLabel("Banco de Dados");
        JSeparator databaseSeparator = new JSeparator();
        ipBaseLabel = new JLabel("IP do banco:");
        userLabel = new JLabel("Usuário:");
        passwordLabel = new JLabel("Senha:");
        ipBaseField = new JTextField();
        userField = new JTextField();
        passwordFeild = new JPasswordField();

        //inicializa componetes da aba outros
        wallpaperCheckBox = new JCheckBox();
        wallpaperCheckBox.setText("Plano de Fundo");

        //define layoue e opções iniciais
        frame.setLayout(null);
        button = new JButton();
        button.setBorderPainted(false);
        colorPanel.setLayout(null);
        systemPanel.setLayout(null);
        otherPanel.setLayout(null);
        xLabel.setHorizontalAlignment(JLabel.CENTER);

        //cria abas
        tabbedPanel.addTab("Sistema", systemScrollPane);
        tabbedPanel.addTab("Cores", colorScrollPane);
        tabbedPanel.addTab("Outras", otherScrollPane);

        //pega tamanho da tela
        takeScreenSize();

        //define tamanho e posição dos componentes
        frame.setBounds((x - 518) / 2, (y - 600) / 2, 518, 600);
        tabbedPanel.setBounds(0, 0, 505, 500);
        okButton.setBounds(150, 520, 150, 30);
        cancelButton.setBounds(320, 520, 150, 30);

        //da aba cores
        colorPanel.setBounds(0, 0, 500, 500);
        scrollTable.setBounds(0, 0, 500, 200);
        deleteListColorLabel.setBounds(5, 170, 100, 30);
        deleteListColorButton.setBounds(105, 170, 30, 30);
        devolutionColorLabel.setBounds(5, 210, 100, 30);
        devolutionColorButton.setBounds(105, 210, 30, 30);
        historyColorLabel.setBounds(5, 250, 100, 30);
        historyColorButton.setBounds(105, 250, 30, 30);
        requestColorLabel.setBounds(5, 290, 100, 30);
        requestColorButton.setBounds(105, 290, 30, 30);
        searchColorLabel.setBounds(5, 330, 100, 30);
        searchColorButton.setBounds(105, 330, 30, 30);
        userColorLabel.setBounds(5, 370, 100, 30);
        userColorButton.setBounds(105, 370, 30, 30);
        withdrawalColorLabel.setBounds(5, 410, 100, 30);
        withdrawalColorButton.setBounds(105, 410, 30, 30);

        //da aba sistema
        screenLabel.setBounds(5, 5, 45, 30);
        screenSeparator.setBounds(50, 20, 400, 5);
        resolitionLabel.setBounds(10, 40, 100, 30);
        xLabel.setBounds(215, 40, 10, 30);
        xField.setBounds(110, 40, 100, 30);
        yField.setBounds(230, 40, 100, 30);
        refreshButton.setBounds(350, 40, 100, 30);
        dataBaseLabel.setBounds(5, 85, 145, 30);
        databaseSeparator.setBounds(150, 100, 300, 5);
        ipBaseLabel.setBounds(10, 130, 100, 30);
        userLabel.setBounds(10, 170, 100, 30);
        passwordLabel.setBounds(10, 210, 100, 30);
        ipBaseField.setBounds(110, 130, 150, 30);
        userField.setBounds(110, 170, 150, 30);
        passwordFeild.setBounds(110, 210, 150, 30);

        //da aba outros
        wallpaperCheckBox.setBounds(5, 5, 150, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < colorTable.getColumnCount(); i++) {
            column = colorTable.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = (scrollTable.getWidth() / 5) * 2;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
        }


        //define fontes
        colorTable.setFont(FontFactory.getFontDefault());
        tabbedPanel.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());
        deleteListColorLabel.setFont(FontFactory.getFontDefault());
        devolutionColorLabel.setFont(FontFactory.getFontDefault());
        historyColorLabel.setFont(FontFactory.getFontDefault());
        requestColorLabel.setFont(FontFactory.getFontDefault());
        searchColorLabel.setFont(FontFactory.getFontDefault());
        userColorLabel.setFont(FontFactory.getFontDefault());
        withdrawalColorLabel.setFont(FontFactory.getFontDefault());

        screenLabel.setFont(FontFactory.getFontLarge());
        resolitionLabel.setFont(FontFactory.getFontDefault());
        xLabel.setFont(FontFactory.getFontDefault());
        xField.setFont(FontFactory.getFontDefault());
        yField.setFont(FontFactory.getFontDefault());
        refreshButton.setFont(FontFactory.getFontDefault());
        dataBaseLabel.setFont(FontFactory.getFontLarge());
        ipBaseLabel.setFont(FontFactory.getFontDefault());
        userLabel.setFont(FontFactory.getFontDefault());
        passwordLabel.setFont(FontFactory.getFontDefault());
        ipBaseField.setFont(FontFactory.getFontDefault());
        userField.setFont(FontFactory.getFontDefault());
        passwordFeild.setFont(FontFactory.getFontDefault());

        wallpaperCheckBox.setFont(FontFactory.getFontDefault());


        //define cores
        tabbedPanel.setBackground(Color.WHITE);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);
        scrollTable.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(Color.WHITE);
        colorPanel.setBackground(Color.WHITE);
        otherScrollPane.setBackground(Color.WHITE);
        otherPanel.setBackground(Color.WHITE);
        systemPanel.setBackground(Color.WHITE);
        systemScrollPane.setBackground(Color.WHITE);
        colorScrollPane.setBackground(Color.WHITE);
        colorTable.setBackground(Color.WHITE);
        deleteListColorLabel.setForeground(Color.BLACK);
        devolutionColorLabel.setForeground(Color.BLACK);
        historyColorLabel.setForeground(Color.BLACK);
        requestColorLabel.setForeground(Color.BLACK);
        searchColorLabel.setForeground(Color.BLACK);
        userColorLabel.setForeground(Color.BLACK);
        withdrawalColorLabel.setForeground(Color.BLACK);

        screenLabel.setForeground(Color.BLACK);
        screenSeparator.setForeground(Color.BLUE);
        resolitionLabel.setForeground(Color.BLACK);
        xLabel.setForeground(Color.BLACK);
        xField.setForeground(Color.BLACK);
        yField.setForeground(Color.BLACK);
        refreshButton.setForeground(Color.BLACK);
        dataBaseLabel.setForeground(Color.BLACK);
        databaseSeparator.setForeground(Color.BLUE);
        ipBaseLabel.setForeground(Color.BLACK);
        userLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        ipBaseField.setForeground(Color.BLACK);
        userField.setForeground(Color.BLACK);
        passwordFeild.setForeground(Color.BLACK);

        wallpaperCheckBox.setForeground(Color.BLACK);
        wallpaperCheckBox.setBackground(Color.WHITE);


        //junção dos componentes
        //da aba cores
        colorPanel.add(scrollTable);
        colorPanel.add(deleteListColorLabel);
        colorPanel.add(deleteListColorButton);
        colorPanel.add(devolutionColorLabel);
        colorPanel.add(devolutionColorButton);
        colorPanel.add(historyColorLabel);
        colorPanel.add(historyColorButton);
        colorPanel.add(requestColorLabel);
        colorPanel.add(requestColorButton);
        colorPanel.add(searchColorLabel);
        colorPanel.add(searchColorButton);
        colorPanel.add(userColorLabel);
        colorPanel.add(userColorButton);
        colorPanel.add(withdrawalColorLabel);
        colorPanel.add(withdrawalColorButton);

        //da aba sistema
        systemPanel.add(screenLabel);
        systemPanel.add(screenSeparator);
        systemPanel.add(resolitionLabel);
        systemPanel.add(xLabel);
        systemPanel.add(xField);
        systemPanel.add(yField);
        systemPanel.add(refreshButton);
        systemPanel.add(dataBaseLabel);
        systemPanel.add(databaseSeparator);
        systemPanel.add(ipBaseLabel);
        systemPanel.add(userLabel);
        systemPanel.add(passwordLabel);
        systemPanel.add(ipBaseField);
        systemPanel.add(userField);
        systemPanel.add(passwordFeild);

        //da aba outros
        otherPanel.add(wallpaperCheckBox);

        ///da janela principal
        frame.add(tabbedPanel);
        frame.add(okButton);
        frame.add(cancelButton);

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) xField.getDocument()).setDocumentFilter(numberDocumentFilter);
        ((AbstractDocument) yField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //define a cor das células
        //sobreescreve o método que obtêm o renderer padrão para o tipo cor
        colorTable.setDefaultRenderer(Color.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row,
                    int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ColorTableModel tableModel = (ColorTableModel) table.getModel();

                //deine a cor do componente de acordo com o modelo
                c.setBackground(tableModel.getColor(row, column));
                return c;
            }
        });

        //define o tipo de editor padrão do tipo cor e sobreescreve o método do componente de edição padrão
        colorTable.setDefaultEditor(Color.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                //pega a cor da célula selecionada
                currentColor = tableModel.getColor(row, column);
                //define button como componente de edição padrão
                return button;
            }
            /*  @Override
             public Object getCellEditorValue() {
             return currentColor;
             }*/
        });

        //define o evento para o editor de celula parão do tipo cor
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //define a mesma cor da célula para o botão
                button.setBackground(currentColor);
                Color c = JColorChooser.showDialog(colorPanel, "Escolha uma cor", currentColor);
                if (c != null) {
                    tableModel.setColor(colorTable.getSelectedRow(), colorTable.getSelectedColumn(), c);
                }
                //fecha o colorChoise
                AbstractCellEditor a = (AbstractCellEditor) colorTable.getDefaultEditor(Color.class);
                a.stopCellEditing();
            }
        });

        //evento para seleção de cor dos botões únicos
        ActionListener colorChanger = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                Color c = JColorChooser.showDialog(colorPanel, "Escolha uma cor", tableModel.getColor(colorTable.getSelectedRow(), colorTable.getSelectedColumn()));
                if (c != null) {
                    button.setBackground(c);
                }
            }
        };

        //define eventopara seleção de cor
        deleteListColorButton.addActionListener(colorChanger);
        devolutionColorButton.addActionListener(colorChanger);
        historyColorButton.addActionListener(colorChanger);
        requestColorButton.addActionListener(colorChanger);
        searchColorButton.addActionListener(colorChanger);
        userColorButton.addActionListener(colorChanger);
        withdrawalColorButton.addActionListener(colorChanger);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScreenResolution screenResolution = new ScreenResolution();
                xField.setText("" + screenResolution.getX());
                yField.setText("" + screenResolution.getY());
            }
        });

        //configirações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }//fim do método createView

    /**
     * Método de preechimento dos campos
     */
    private void fiilsView() {
        User user;
        if (TemporaryData.getUser() == null) {
            user = new User();
            user.setName("default");
        } else {
            user = TemporaryData.getUser();
        }
        Preferences preferenceSystem = PreferencesManager.getPreferencesSystem();
        ipBaseField.setText(preferenceSystem.get(PreferencesManager.KEY_LOCAL_DATABASE, ""));
        userField.setText(preferenceSystem.get(PreferencesManager.KEY_DATABASE_USER, ""));
        passwordFeild.setText(preferenceSystem.get(PreferencesManager.KEY_DATABASE_PASSWORD, ""));

        //wallpaper ativado
        Preferences preferences = PreferencesManager.getPrefetencesUser(user);
        wallpaperCheckBox.setSelected(preferences.getBoolean(PreferencesManager.KEY_WALLPAPER, true));
        //tamanho da tela predefinido
        xField.setText("" + x);
        yField.setText("" + y);
        //define cor dos botões
        ColorConverter colorConverter = new ColorConverter();
        colorsBoxes = new ArrayList();
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_A));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_AE));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_CC));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_DAC));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_DAD));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_DG));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_DP));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_DSG));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_EX));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_F));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_FC));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_FP));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_MC));
        colorsBoxes.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOX_SAE));


        colorsBooks = new ArrayList();
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_A));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_AE));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_CC));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_DAC));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_DAD));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_DG));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_DP));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_DSG));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_EX));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_F));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_FC));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_FP));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_MC));
        colorsBooks.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_BOOK_SAE));

        colorsDocuments = new ArrayList();
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_A));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_AE));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_CC));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DAC));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DAD));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DG));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DP));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_DSG));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_EX));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_F));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_FC));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_FP));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_MC));
        colorsDocuments.add(colorConverter.getColor(PreferencesManager.KEY_COLOR_DOCUMENT_SAE));

        tableModel.setColorList(colorsBoxes, colorsBooks, colorsDocuments);

        deleteListColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_DELETE_LIST));
        devolutionColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_DEVOLUTION));
        historyColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_HISTORY));
        requestColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_REQUEST));
        searchColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_SEARCH));
        userColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_USER));
        withdrawalColorButton.setBackground(colorConverter.getColor(PreferencesManager.KEY_COLOR_WITHDRAWAL));
    }//fim do método fiilsView

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
     * Define listener para o botão ok
     *
     * @param actionListener ouvinte
     */
    public void setOKButtonActionListener(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }//fim do método setOKButtonActionListener

    /**
     * Define listener para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setCancelButtonActionListener(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setCancelButtonActionListener

    /**
     * Obtêm se plano de fundo deve ser ativado
     */
    public boolean getWallpaperEnable() {
        return wallpaperCheckBox.isSelected();
    }//fim do método getWallpaperEnable

    /**
     * Obtêm o IP da banco
     *
     * @return <code>String</code> IP
     */
    public String getIP() {
        return ipBaseField.getText();
    }//fim do método getIP

    /**
     * Obtêm o usuário
     *
     * @return <code>String</code> usuário
     */
    public String getUser() {
        return userField.getText();
    }//fim do método de getUser

    /**
     * Obtêm a senha do banco
     *
     * @return <code>char[]</code> senha do banco
     */
    public char[] getPassword() {
        return passwordFeild.getPassword();
    }//fim do m[etodo getPassword

    /**
     * Obtêm o comprimento da tela
     *
     * @return <code>Interger</code> com x da tela
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    public int getWidthSize() throws NumberFormatException, NullPointerException {
        return Integer.parseInt(xField.getText());
    }//fim do método getWidthSize

    /**
     * Obtêm o altura da tela
     *
     * @return <code>Interger</code> com y da tela
     * @throws NumberFormatException
     * @throws NullPointerException
     */
    public int getHeightSize() throws NumberFormatException, NullPointerException {
        return Integer.parseInt(yField.getText());
    }//fim do método getHeightSize

    /**
     * Fecha a janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Define visibilidade do frame
     *
     * @param visible true-visivel false-invisivel
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }//fim do método setVisible

    /**
     * Define se a janela está ativa
     *
     * @param enable true-ativada false-desativada
     */
    public void enableFrame(boolean enable) {
        frame.setEnabled(enable);
    }//fim do método enableFrame

    /**
     * Obtêm lista de cores de caixas
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsBoxes() {
        return tableModel.getColorsBoxes();
    }//fim do método getColorsBoxes

    /**
     * Obtêm lista de cores de livros
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsBooks() {
        return tableModel.getColorsBooks();
    }//fim do método getColorsBooks

    /**
     * Obtêm lista de cores de documentos
     *
     * @return <code>List</code> lista de cores
     */
    public List<Color> getColorsDocuments() {
        return tableModel.getColorsDocuments();
    }//fim do método getColorsDocuments

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Obtêm cor de ista de deleção
     *
     * @return <code>Color</code> cor
     */
    public Color getColorDeleteList() {
        return deleteListColorButton.getBackground();
    }//fim do método getColorDeleteList

    /**
     * Obtêm cor de devolução
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorDevolution() {
        return devolutionColorButton.getBackground();
    }//fim do método getColorDevolution

    /**
     * Obtêm cor de histórico
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorHistory() {
        return historyColorButton.getBackground();
    }//fim do método getColorHistory

    /**
     * Obtêm cor de solicitação
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorRequest() {
        return requestColorButton.getBackground();
    }//fim do método getColorRequest

    /**
     * Obtêm cor de busca
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorSearch() {
        return searchColorButton.getBackground();
    }//fim do método getColorSearch

    /**
     * Obtêm cor de usuário
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorUser() {
        return userColorButton.getBackground();
    }//fim do método getColorUser

    /**
     * Obtêm cor de solicitação
     *
     * @return  <code>Color</code> cor
     */
    public Color getColorWithdrawal() {
        return withdrawalColorButton.getBackground();
    }//fim do método getColorWithdrawal

    //método para teste
    public static void main(String[] args) {
        new ConfigurationFrame();
    }
}//fim da classe ConfigurationFrame 

