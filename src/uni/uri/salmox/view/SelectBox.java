/*-
 * Classname:             SelectBox.java
 *
 * Version information:   1.0
 *
 * Date:                  20/06/2013 - 14:48:41
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Descrição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SelectBox {

    //frame principal
    private JFrame frame;
    //tabela com resultados
    private JTable table;
    //modelo da tabela com resultados
    private TableModel tableModel;
    //crollPane para a tabela com resultado
    private JScrollPane scrollPane;
    //botão adicionar
    private JButton addButton;
    //botão abrir
    private JButton openButton;
    //botão cancelar
    private JButton cancelButton;//largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor com resultados da busca
     *
     * @param tableModel modelo da tabela
     */
    public SelectBox(TableModel tableModel) {
        this.tableModel = tableModel;
        createView();
    }//fim do construtor com resultados da busca

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Selecione as caixas desejadas");
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        addButton = new JButton("Adicionar");
        openButton = new JButton("Abrir");
        cancelButton = new JButton("Cancelar");

        //pega tamanho da tela e define layout 
        frame.setLayout(null);
        takeScreenSize();

        //define tamanho e posição dos componentes
        frame.setBounds(0, 0, x, y);
        scrollPane.setBounds(50, 50, x - 450, y - 150);
        table.setBounds(0, 0, x - 400, y);
        addButton.setBounds(x - 300, 100, 200, 30);
        openButton.setBounds(x - 300, 150, 200, 30);
        cancelButton.setBounds(x - 300, 200, 200, 30);

        //define tamanho das colunas da tabela
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                int sizeColumnCode = 20;
                column.setPreferredWidth(sizeColumnCode);
                column.setMaxWidth(sizeColumnCode);
            }
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
        }

        //define cor e fonte
        frame.getContentPane().setBackground(getColor());
        table.setFont(FontFactory.getFontDefault());
        openButton.setFont(FontFactory.getFontLarge());
        addButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        addButton.setToolTipText("Adiciona caixas selecionadas a lista");
        openButton.setToolTipText("Abre caixa selecionada");
        cancelButton.setToolTipText("Cancela a seleção de caixas");

        table.setAutoCreateRowSorter(true);

        //adiciona os componentes
        scrollPane.setViewportView(table);
        frame.add(scrollPane);
        frame.add(addButton);
        frame.add(openButton);
        frame.add(cancelButton);

        //define panel como visivel
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
     * Define evento para o botão adicionar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerAddButton(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }//fim do método setActionListenerAddButton

    /**
     * Define evento para o botão abrir
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerOpenButton(ActionListener actionListener) {
        openButton.addActionListener(actionListener);
    }//fim do método setActionListenerOpenButton

    /**
     * Define evento para o botão cancel
     *
     * @param actionListener ouvinte
     */
    public void setActionListenerCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenerCancelButton

    /**
     * Obtêm caixa selecionada se for o caso
     *
     * @return <code>Box</code> caixa
     */
    public Box getBoxSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchBoxTableModel resultSearchBoxTableModel = (ResultSearchBoxTableModel) tableModel;
            if (table.getSelectedRow() >= 0) {
                return resultSearchBoxTableModel.getRow(table.convertRowIndexToModel(table.getSelectedRow()));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }//fim do método getBoxSelected

    /**
     * Obtêm livro selecionado se for o caso
     *
     * @return <code>Book</code> livro
     */
    public Book getBookSelected() {
        if (tableModel instanceof ResultSearchBookTableModel) {
            ResultSearchBookTableModel resultSearchBookTableModel = (ResultSearchBookTableModel) tableModel;
            return resultSearchBookTableModel.getRow(table.convertColumnIndexToModel(table.getSelectedColumn()));
        } else {
            return null;
        }
    }//fim do método getBookSelected

    /**
     * Obtêm documento selecionade se for o caso
     *
     * @return <code>Document</code>
     */
    public Document getDocumentSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchDocumentTableModel resultSearchDocumentTableModel = (ResultSearchDocumentTableModel) tableModel;
            return resultSearchDocumentTableModel.getRow(table.convertColumnIndexToModel(table.getSelectedColumn()));
        } else {
            return null;
        }
    }//fim do método getDocumentSelected

    /**
     * Obtêm lista de caixas com a caixa de combinação marcada
     *
     * @return <code>List</code> caixas marcadas
     */
    public List<Box> getBoxesSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchBoxTableModel resultSearchBoxTableModel = (ResultSearchBoxTableModel) tableModel;
            return resultSearchBoxTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getBoxesSelected

    /**
     * Obtêm lista de livros com a caixa de combinação marcada
     *
     * @return <code>List</code> livros marcados
     */
    public List<Book> getBooksSelected() {
        if (tableModel instanceof ResultSearchBookTableModel) {
            ResultSearchBookTableModel resultSearchBookTableModel = (ResultSearchBookTableModel) tableModel;
            return resultSearchBookTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getBooksSelected

    /**
     * Obtêm lista de documentos com a caixa de combinação marcada
     *
     * @return <code>List</code> documentos marcados
     */
    public List<Document> getDocumentsSelected() {
        if (tableModel instanceof ResultSearchBoxTableModel) {
            ResultSearchDocumentTableModel resultSearchDocumentTableModel = (ResultSearchDocumentTableModel) tableModel;
            return resultSearchDocumentTableModel.getCheckedRows();
        } else {
            return null;
        }
    }//fim do método getDocumentsSelected

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowListener windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener
    //metodo para teste

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
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

    //método para testes
    public static void main(String args[]) {
        new SelectBox(new BoxTableModel(new ArrayList()));
    }

    /**
     * Define se botão abrir é visível
     *
     * @param active
     */
    public void setShowOpenButton(boolean active) {
        openButton.setVisible(active);
    }//fim do método setShowOpenButton
}//fim da classe SelectBox

