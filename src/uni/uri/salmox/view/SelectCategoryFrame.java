/*- 
 * Classname:             SelectCategoryFrame.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  11/12/2013 - 14:28:24 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Visão para seleção de categoria
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectCategoryFrame {

    //janela
    private JFrame frame;
    //lista visual
    private JList categoryList;
    //scrollPane para a lista
    private JScrollPane scrollPane;
    //label título
    private JLabel titleLabel;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //lista categorias
    private List<Category> categories;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Método construtor sem parâmetros
     *
     * @param categories
     */
    public SelectCategoryFrame(List<Category> categories) {
        this.categories = categories;
        createView();
    }//fim do método construtor

    /**
     * Cria visão
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame("Selecione uma categoria");
        categoryList = new JList();
        scrollPane = new JScrollPane(categoryList);
        titleLabel = new JLabel("Selecione uma categoria");
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define consfigurações iniciais
        frame.setLayout(null);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        Object[] itens = new Object[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            itens[i] = categories.get(i).getName();
        }
        categoryList.setListData(itens);

        //define localização e tamanho
        takeScreenSize();
        frame.setBounds((x - 400) / 2, (y - 370) / 2, 400, 370);
        scrollPane.setBounds(50, 50, 250, 200);
        titleLabel.setBounds((400 - 200) / 2, 0, 200, 50);
        okButton.setBounds(50, 280, 130, 30);
        cancelButton.setBounds(210, 280, 130, 30);

        //define cor
        frame.getContentPane().setBackground(getColor());
        categoryList.setForeground(Color.BLACK);
        titleLabel.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //define fonte
        categoryList.setFont(FontFactory.getFontDefault());
        titleLabel.setFont(FontFactory.getFontLarge());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //aclopacomponentes
        scrollPane.setViewportView(categoryList);
        frame.add(scrollPane);
        frame.add(titleLabel);
        frame.add(okButton);
        frame.add(cancelButton);

        //define configurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }//fim do método createView

    /**
     * Define evento para o botão ok
     *
     * @param actionListener ouvinte
     */
    public void setOkButtonActionListener(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }//fim do método setOkButtonActionListener

    /**
     * Define evento para o botão cancelar
     *
     * @param actionslIstenr ouvionte
     */
    public void setCancelButtonActionListener(ActionListener actionslIstenr) {
        cancelButton.addActionListener(actionslIstenr);
    }//fim do método setCancelButtonActionListener

    /**
     * Define evento para fechar janela
     *
     * @param windowListener ouvinte
     */
    public void setFrameWindowListener(WindowListener windowListener) {
        frame.addWindowListener(windowListener);
    }//fim do método setFrameWindowListener

    /**
     * Obtêm categoria selecionada
     *
     * @return <code>Category</code> categoria
     */
    public Category getSelectedCategory() {
        int index = categoryList.getSelectedIndex();
        if (index >= 0) {
            return categories.get(index);
        }
        return null;
    }//fim do método getSelectedCategory

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
     * Fecha a janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

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

    //método para teste
    public static void main(String args[]) {
        List<Category> cat = new ArrayList();
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        cat.add(Category.A);
        new SelectCategoryFrame(cat);
    }
}//fim da classe SelectCategoryFrame 

