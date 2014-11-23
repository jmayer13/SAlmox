/*-
 * Classname:             SearchFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  02/05/2013 - 13:27:45
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Janela responsável por buscar documentos, livros e caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SearchFrame {

    //panel principal
    private JFrame frame;
    //título da janela
    private JLabel titlePanel;
    //label categoria
    private JLabel categoryLabel;
    //label tipo de busca
    private JLabel typeLabel;
    //label buscar por
    private JLabel searchByLabel;
    //label termo da busca
    private JLabel termLabel;
    //label categoria
    private JComboBox categoryComboBox;
    //campo tipo de busca
    private JComboBox typeSearchComboBox;
    //campo buscar por
    private JComboBox searchByComboBox;
    //campo termo da busca
    private JTextField termField;
    //botão buscar
    private JButton searchButton;
    //botão cancelar
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;
    //esquemas de busca
    private final String[] ALL_BOX = {"Título", "Responsáveis", "Observação"};
    private final String[] A_BOX = {"Código", "Título", "Tipo", "Responsáveis", "Observação"};
    private final String[] AE_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] CC_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] DAC_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] DAD_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] DP_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] DG_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] DSG_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] EX_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] F_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] FC_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] FP_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] MC_BOX = {"Código", "Título", "Responsáveis", "Observação"};
    private final String[] SAE_BOX = {"Código", "Título", "Ano", "Responsáveis", "Observação"};
    private final String[] ALL_BOOK = {"Título", "Observação"};
    private final String[] A_BOOK = {"Código", "Título", "Ano", "Tipo", "Observação"};
    private final String[] AE_BOOK = {"Código", "Título", "Código do curso", "Ano", "Observação"};
    private final String[] CC_BOOK = {"Código", "Título", "Observação"};
    private final String[] DAC_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] DAD_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] DG_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] DP_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] DSG_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] EX_BOOK = {"Código", "Título", "Observação"};
    private final String[] F_BOOK = {"Código", "Título", "Ano", "Semestre", "Observação"};
    private final String[] FC_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] FP_BOOK = {"Código", "Título", "Tipo de Curso", "Ano", "Observação"};
    private final String[] MC_BOOK = {"Código", "Título", "Ano", "Observação"};
    private final String[] SAE_BOOK = {"Código", "Título", "Observação"};
    private final String[] ALL_DOCUMENT = {"Título", "Observação"};
    private final String[] A_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] AE_DOCUMENT = {"Código", "Título", "Data do Exame", "Anexo", "Observação"};
    private final String[] CC_DOCUMENT = {"Código", "Título", "Ano", "Semestre", "Observação"};
    private final String[] DAC_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] DAD_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] DG_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] DP_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] DSG_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] EX_DOCUMENT = {"Código", "Título", "Ano", "Observação"};
    private final String[] F_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] FC_DOCUMENT = {"Código", "Título", "Observação"};
    private final String[] FP_DOCUMENT = {"Código", "Título", "Estado do Aluno", "Observação"};
    private final String[] MC_DOCUMENT = {"Código", "Título", "Data", "Observação"};
    private final String[] SAE_DOCUMENT = {"Código", "Título", "Observação"};
    //varival de armazenamento temporádio de esquema de busca
    private String[] contendTemp = null;

    /**
     * Construtor sem parametros
     */
    public SearchFrame() {
        createView();
    }//fim do construtor sem parametros

    /**
     * Cria a visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Buscar");
        titlePanel = new JLabel("Buscar");
        categoryLabel = new JLabel("Categoria:");
        typeLabel = new JLabel("Tipo:");
        searchByLabel = new JLabel("Buscar por:");
        termLabel = new JLabel("Termo:");
        categoryComboBox = new JComboBox(new Object[]{"Todas", Category.A.getName(),
            Category.AE.getName(), Category.CC.getName(), Category.DAC.getName(), Category.DAD.getName(),
            Category.DG.getName(), Category.DP.getName(), Category.DSG.getName(),
            Category.EX.getName(), Category.F.getName(), Category.FC.getName(), Category.FP.getName(), Category.MC.getName(), Category.SAE.getName()});


        typeSearchComboBox = new JComboBox(new Object[]{"Caixa", "Livro", "Documento"});
        searchByComboBox = new JComboBox(ALL_BOX);
        termField = new JTextField();
        searchButton = new JButton("Buscar");
        cancelButton = new JButton("Cancelar");

        contendTemp = new String[10];

        //pega tamanho da tela e define layout 
        frame.setLayout(null);
        takeScreenSize();

        //centraliza título
        titlePanel.setHorizontalAlignment(JLabel.CENTER);

        //define tamanho e posição dos componentes
        frame.setBounds((x - 500) / 2, (y - 400) / 2, 500, 400);
        titlePanel.setBounds((500 - 200) / 2, 10, 200, 30);
        categoryLabel.setBounds(50, 70, 100, 30);
        typeLabel.setBounds(50, 120, 100, 30);
        searchByLabel.setBounds(50, 170, 100, 30);
        termLabel.setBounds(50, 220, 100, 30);
        categoryComboBox.setBounds(150, 70, 220, 30);
        typeSearchComboBox.setBounds(150, 120, 200, 30);
        searchByComboBox.setBounds(150, 170, 200, 30);
        termField.setBounds(150, 220, 230, 30);
        searchButton.setBounds(75, 320, 150, 30);
        cancelButton.setBounds(245, 320, 150, 30);


        //define cor
        frame.getContentPane().setBackground(getColor());
        categoryComboBox.setBackground(Color.WHITE);
        typeSearchComboBox.setBackground(Color.WHITE);
        searchByComboBox.setBackground(Color.WHITE);
        termField.setBackground(Color.WHITE);
        titlePanel.setForeground(Color.BLACK);
        categoryLabel.setForeground(Color.BLACK);
        typeLabel.setForeground(Color.BLACK);
        searchByLabel.setForeground(Color.BLACK);
        termLabel.setForeground(Color.BLACK);
        categoryComboBox.setForeground(Color.BLACK);
        typeSearchComboBox.setForeground(Color.BLACK);
        searchByComboBox.setForeground(Color.BLACK);
        termField.setForeground(Color.BLACK);


        //define fonte
        titlePanel.setFont(FontFactory.getFontLarge());
        categoryLabel.setFont(FontFactory.getFontDefault());
        typeLabel.setFont(FontFactory.getFontDefault());
        searchByLabel.setFont(FontFactory.getFontDefault());
        termLabel.setFont(FontFactory.getFontDefault());
        categoryComboBox.setFont(FontFactory.getFontDefault());
        typeSearchComboBox.setFont(FontFactory.getFontDefault());
        searchByComboBox.setFont(FontFactory.getFontDefault());
        termField.setFont(FontFactory.getFontDefault());
        searchButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        categoryComboBox.setToolTipText("Selecione a categoria para a busca");
        typeSearchComboBox.setToolTipText("Selecione o alvo da busca");
        searchByComboBox.setToolTipText("Selecione o tipo de dado para a busca");
        termField.setToolTipText("Defina um termo para busca");
        searchButton.setToolTipText("Realiza busca com os critérios selecionados");
        cancelButton.setToolTipText("Cancela a busca");

        //adiciona os componentes
        frame.add(titlePanel);
        frame.add(categoryLabel);
        frame.add(typeLabel);
        frame.add(searchByLabel);
        frame.add(termLabel);
        frame.add(categoryComboBox);
        frame.add(typeSearchComboBox);
        frame.add(searchByComboBox);
        frame.add(termField);
        frame.add(searchButton);
        frame.add(cancelButton);


        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        ActionListener searchByListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchSelector();
            }
        };

        categoryComboBox.addActionListener(searchByListener);
        typeSearchComboBox.addActionListener(searchByListener);
    }//fim do método createView

    /**
     * Seleciona os padrões de pesquisa
     */
    private void searchSelector() {
        Category category = getCategory();
        int selectedType = typeSearchComboBox.getSelectedIndex();

        if (selectedType == 0) {
            if (category == null) {
                contendTemp = ALL_BOX;
            } else if (category == Category.A) {
                contendTemp = A_BOX;
            } else if (category == Category.AE) {
                contendTemp = AE_BOX;
            } else if (category == Category.CC) {
                contendTemp = CC_BOX;
            } else if (category == Category.DAC) {
                contendTemp = DAC_BOX;
            } else if (category == Category.DAD) {
                contendTemp = DAD_BOX;
            } else if (category == Category.DG) {
                contendTemp = DG_BOX;
            } else if (category == Category.DP) {
                contendTemp = DP_BOX;
            } else if (category == Category.DSG) {
                contendTemp = DSG_BOX;
            } else if (category == Category.EX) {
                contendTemp = EX_BOX;
            } else if (category == Category.F) {
                contendTemp = F_BOX;
            } else if (category == Category.FC) {
                contendTemp = FC_BOX;
            } else if (category == Category.FP) {
                contendTemp = FP_BOX;
            } else if (category == Category.MC) {
                contendTemp = MC_BOX;
            } else if (category == Category.SAE) {
                contendTemp = SAE_BOX;

            }
        } else if (selectedType == 1) {
            if (category == null) {
                contendTemp = ALL_BOOK;
            } else if (category == Category.A) {
                contendTemp = A_BOOK;
            } else if (category == Category.AE) {
                contendTemp = AE_BOOK;
            } else if (category == Category.CC) {
                contendTemp = CC_BOOK;
            } else if (category == Category.DAC) {
                contendTemp = DAC_BOOK;
            } else if (category == Category.DAD) {
                contendTemp = DAD_BOOK;
            } else if (category == Category.DG) {
                contendTemp = DG_BOOK;
            } else if (category == Category.DP) {
                contendTemp = DP_BOOK;
            } else if (category == Category.DSG) {
                contendTemp = DSG_BOOK;
            } else if (category == Category.EX) {
                contendTemp = EX_BOOK;
            } else if (category == Category.F) {
                contendTemp = F_BOOK;
            } else if (category == Category.FC) {
                contendTemp = FC_BOOK;
            } else if (category == Category.FP) {
                contendTemp = FP_BOOK;
            } else if (category == Category.MC) {
                contendTemp = MC_BOOK;
            } else if (category == Category.SAE) {
                contendTemp = SAE_BOOK;

            }
        } else if (selectedType == 2) {
            if (category == null) {
                contendTemp = ALL_DOCUMENT;
            } else if (category == Category.A) {
                contendTemp = A_DOCUMENT;
            } else if (category == Category.AE) {
                contendTemp = AE_DOCUMENT;
            } else if (category == Category.CC) {
                contendTemp = CC_DOCUMENT;
            } else if (category == Category.DAC) {
                contendTemp = DAC_DOCUMENT;

            } else if (category == Category.DAD) {
                contendTemp = DAD_DOCUMENT;

            } else if (category == Category.DG) {
                contendTemp = DG_DOCUMENT;

            } else if (category == Category.DP) {
                contendTemp = DP_DOCUMENT;
            } else if (category == Category.DSG) {
                contendTemp = DSG_DOCUMENT;

            } else if (category == Category.EX) {
                contendTemp = EX_DOCUMENT;
            } else if (category == Category.F) {
                contendTemp = F_DOCUMENT;
            } else if (category == Category.FC) {
                contendTemp = FC_DOCUMENT;

            } else if (category == Category.FP) {
                contendTemp = FP_DOCUMENT;
            } else if (category == Category.MC) {
                contendTemp = MC_DOCUMENT;
            } else if (category == Category.SAE) {
                contendTemp = SAE_DOCUMENT;

            }
        }
        searchByComboBox.removeAllItems();
        for (int i = 0; i < contendTemp.length; i++) {
            searchByComboBox.addItem(contendTemp[i]);
        }
        searchByComboBox.repaint();

    }//fim do método searchSelect

    /**
     * Obtêm parâmetros da busca
     *
     * @return <code>String[]</code> constante com parâmetros da busca
     */
    public String[] getSearchParameters() {
        int index = searchByComboBox.getSelectedIndex();
        String[] parameters = new String[contendTemp.length];
        for (int i = 0; i < parameters.length; i++) {
            if (i == index) {
                parameters[i] = termField.getText();
            } else {
                parameters[i] = null;
            }
        }
        return parameters;
    }//fim do método getSearchParameters

    /**
     * Obtêm termo para busca
     *
     * @return <code>String</code> termo da busca
     */
    public String getSearchTerm() {
        return termField.getText();
    }//fim do método getSearchTerm

    /**
     * Obtêm tipo de busca
     *
     * @return <code>Integer</code>
     */
    public int getTypeSearch() {
        return typeSearchComboBox.getSelectedIndex();
    }//fim do método getTypeSearch

    /**
     * Obtêm a categoria
     *
     * @return <code>Category</code> categoria
     */
    public Category getCategory() {
        int selectedCategory = categoryComboBox.getSelectedIndex();

        if (selectedCategory == 0) {
            return null;
        } else if (selectedCategory == 1) {
            return Category.A;
        } else if (selectedCategory == 2) {
            return Category.AE;
        } else if (selectedCategory == 3) {
            return Category.CC;
        } else if (selectedCategory == 4) {
            return Category.DAC;
        } else if (selectedCategory == 5) {
            return Category.DAD;
        } else if (selectedCategory == 6) {
            return Category.DG;
        } else if (selectedCategory == 7) {
            return Category.DP;
        } else if (selectedCategory == 8) {
            return Category.DSG;
        } else if (selectedCategory == 9) {
            return Category.EX;

        } else if (selectedCategory == 10) {
            return Category.F;
        } else if (selectedCategory == 11) {
            return Category.F;
        } else if (selectedCategory == 12) {
            return Category.FP;
        } else if (selectedCategory == 13) {
            return Category.MC;
        } else if (selectedCategory == 14) {
            return Category.SAE;
        } else {
            return null;
        }
    }//fim do método getCategory

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
     * Define evento para o botão buscar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerSearchButton(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }//fim do método setActionListenerSearchButton

    /**
     * Define evento para botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenerCancelButton

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        categoryComboBox.addKeyListener(keyListener);
        typeSearchComboBox.addKeyListener(keyListener);
        searchByComboBox.addKeyListener(keyListener);
        termField.addKeyListener(keyListener);
        searchButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_SEARCH);
    }//fim do método getColor

    //método para teste
    public static void main(String args[]) {
        SearchFrame searchFrame = new SearchFrame();
    }

    /**
     * Bloqueia combo de tipo de busca em caixas
     */
    public void lockInBox() {
        typeSearchComboBox.setSelectedIndex(0);
        typeSearchComboBox.setEnabled(false);
    }//fim do método lockInBox
}//fim da classe SearchFrame 

