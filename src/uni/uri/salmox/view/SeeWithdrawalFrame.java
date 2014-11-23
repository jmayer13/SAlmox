/*- 
 * Classname:             SeeWithdrawalFrame.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  01/10/2013 - 14:29:56 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Visualiza uma retirada
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SeeWithdrawalFrame {

    //frame principal
    private JFrame frame;
    //scrollpane para tabela
    private JScrollPane scrollPane;
    //tabela 
    private JTable table;
    //modelo da tabela
    private WithdrawalDocumentTableModel tableModel;
    //label do frame
    private JLabel frameLabel;
    //label estado
    private JLabel statusLabel;
    //label data de retirada
    private JLabel dateWithdrawalLabel;
    //label data de entrega
    private JLabel dateDeliveryLabel;
    //label documento
    private JLabel documentsLabel;
    //label administrador
    private JLabel administratorLabel;
    //label solicitante
    private JLabel solicitorLabel;
    //label retirado por
    private JLabel deliveryGuyLabel;
    //label motivo
    private JLabel motiveLabel;
    //label observação
    private JLabel observationLabel;
    //campo codigo retirada
    private JTextField codeField;
    //campo estado
    private JTextField statusField;
    //campo data retirada
    private JTextField dateWithdrawalField;
    //campo data entrega
    private JTextField dateDeliveryField;
    //campo administrador
    private JTextField administratorField;
    //campo solicitante
    private JTextField solicitorField;
    //campo retirado por
    private JTextField deliveryGuyField;
    //campo motivo
    private JTextArea motiveField;
    //scrollpane para textarea motiveField
    private JScrollPane motivePane;
    //campo de texto observação
    private JTextArea observationField;
    //scrollpane para textarea observationField
    private JScrollPane observationPane;
    //botão fechar
    private JButton closeButton;
    //retirada
    private Withdrawal withdrawal;
    //largura
    private int x;
    //altura
    private int y;

    /**
     * Construtor sem parãmetros
     */
    public SeeWithdrawalFrame() {
        tableModel = new WithdrawalDocumentTableModel();
        createView();
    }//fim do construtor sem oparametros

    /**
     * Construtor com listas de documentos
     *
     * @param withdrawal retirada
     * @param boxes lista de caixa
     * @param books lista de livros
     * @param documents lista de documentos
     */
    public SeeWithdrawalFrame(Withdrawal withdrawal, List<Box> boxes, List<Book> books, List<Document> documents) {//atualizar classe view e checar DAO 
        tableModel = new WithdrawalDocumentTableModel(boxes, books, documents);
        createView();
        fillsView(withdrawal);
    }//fim do construtor com listas

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Retirada");
        frameLabel = new JLabel("Retirada:");
        scrollPane = new JScrollPane();
        table = new JTable(tableModel);
        motiveLabel = new JLabel("Motivo:");
        observationLabel = new JLabel("Observação:");
        statusLabel = new JLabel("Estado:");
        dateWithdrawalLabel = new JLabel("Data de retirada:");
        dateDeliveryLabel = new JLabel("Data de entrega:");
        documentsLabel = new JLabel("Documentos:");
        administratorLabel = new JLabel("Administrador:");
        solicitorLabel = new JLabel("Solicitante:");
        deliveryGuyLabel = new JLabel("Retirado por:");
        motiveLabel = new JLabel("Motivo:");
        observationLabel = new JLabel("Observação:");

        codeField = new JTextField();
        statusField = new JTextField();
        dateWithdrawalField = new JTextField();
        dateDeliveryField = new JTextField();
        administratorField = new JTextField();
        solicitorField = new JTextField();
        deliveryGuyField = new JTextField();
        motiveField = new JTextArea();
        motivePane = new JScrollPane();
        observationField = new JTextArea();
        observationPane = new JScrollPane();
        closeButton = new JButton("Fechar");

        //define layout
        frame.setLayout(null);

        //quebra de linha JTextArea
        motiveField.setLineWrap(true);
        observationField.setLineWrap(true);
        motiveField.setWrapStyleWord(true);
        observationField.setWrapStyleWord(true);

        //obtêm o tamanho da tela
        takeScreenSize();

        //define tamanho e posição
        frame.setBounds((x - (x - 100)) / 2, (y - (y - 100)) / 2, x - 100, y - 100);
        frameLabel.setBounds(20, 20, 80, 30);
        scrollPane.setBounds(20, 170, x - 300, 180);
        statusLabel.setBounds(200, 20, 100, 30);
        dateWithdrawalLabel.setBounds(20, 70, 120, 30);
        dateDeliveryLabel.setBounds(280, 70, 120, 30);
        dateDeliveryField.setBounds(400, 70, 100, 30);
        documentsLabel.setBounds(20, 120, 100, 30);
        administratorLabel.setBounds(20, 370, 100, 30);
        solicitorLabel.setBounds(20, 420, 100, 30);
        deliveryGuyLabel.setBounds(320, 420, 100, 30);
        motiveLabel.setBounds(20, 470, 100, 30);
        observationLabel.setBounds(420, 470, 100, 30);
        codeField.setBounds(100, 20, 50, 30);
        statusField.setBounds(260, 20, 100, 30);
        dateWithdrawalField.setBounds(140, 70, 100, 30);
        administratorField.setBounds(120, 370, 200, 30);
        solicitorField.setBounds(100, 420, 200, 30);
        deliveryGuyField.setBounds(420, 420, 200, 30);
        motivePane.setBounds(100, 470, 280, 150 - (frame.getHeight() - 620));
        observationPane.setBounds(520, 470, 280, 150 - (frame.getHeight() - 620));
        closeButton.setBounds(frame.getWidth() - 300, frame.getHeight() - 80, 200, 30);

        //configurações gráficas
        //cor
        frame.getContentPane().setBackground(getColor());
        frameLabel.setBackground(Color.BLACK);
        scrollPane.setBackground(getColor());
        table.setForeground(Color.BLACK);
        motiveLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        motiveField.setForeground(Color.BLACK);
        motiveField.setBackground(Color.WHITE);
        observationField.setBackground(Color.WHITE);
        observationField.setForeground(Color.BLACK);
        closeButton.setForeground(Color.BLACK);
        statusLabel.setForeground(Color.BLACK);
        dateWithdrawalLabel.setForeground(Color.BLACK);
        dateDeliveryLabel.setForeground(Color.BLACK);
        documentsLabel.setForeground(Color.BLACK);
        administratorLabel.setForeground(Color.BLACK);
        solicitorLabel.setForeground(Color.BLACK);
        deliveryGuyLabel.setForeground(Color.BLACK);
        motiveLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        statusField.setForeground(Color.BLACK);
        dateWithdrawalField.setForeground(Color.BLACK);
        administratorField.setForeground(Color.BLACK);
        solicitorField.setForeground(Color.BLACK);
        deliveryGuyField.setForeground(Color.BLACK);
        dateDeliveryField.setForeground(Color.BLACK);

        //bordas
        codeField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        statusField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        dateWithdrawalField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        administratorField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        solicitorField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        deliveryGuyField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        dateDeliveryField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        observationPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        motivePane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        //desativa campos
        codeField.setEditable(false);
        statusField.setEditable(false);
        dateWithdrawalField.setEditable(false);
        administratorField.setEditable(false);
        solicitorField.setEditable(false);
        deliveryGuyField.setEditable(false);
        dateDeliveryField.setEditable(false);
        observationField.setEditable(false);
        motiveField.setEditable(false);

        //fonte
        frameLabel.setFont(FontFactory.getFontDefault());
        table.setFont(FontFactory.getFontDefault());
        motiveLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        closeButton.setFont(FontFactory.getFontLarge());
        statusLabel.setFont(FontFactory.getFontDefault());
        dateWithdrawalLabel.setFont(FontFactory.getFontDefault());
        dateDeliveryLabel.setFont(FontFactory.getFontDefault());
        documentsLabel.setFont(FontFactory.getFontDefault());;
        administratorLabel.setFont(FontFactory.getFontDefault());
        solicitorLabel.setFont(FontFactory.getFontDefault());
        deliveryGuyLabel.setFont(FontFactory.getFontDefault());
        motiveLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        statusField.setFont(FontFactory.getFontDefault());
        dateWithdrawalField.setFont(FontFactory.getFontDefault());
        administratorField.setFont(FontFactory.getFontDefault());
        solicitorField.setFont(FontFactory.getFontDefault());
        deliveryGuyField.setFont(FontFactory.getFontDefault());
        dateDeliveryField.setFont(FontFactory.getFontDefault());

        //dicas
        closeButton.setToolTipText("Fecha a janela");

        //junção de componentes
        scrollPane.setViewportView(table);
        motivePane.setViewportView(motiveField);
        observationPane.setViewportView(observationField);
        frame.add(frameLabel);
        frame.add(scrollPane);
        frame.add(motiveLabel);
        frame.add(observationLabel);
        frame.add(motivePane);
        frame.add(closeButton);
        frame.add(observationPane);
        frame.add(statusLabel);
        frame.add(dateWithdrawalLabel);
        frame.add(dateDeliveryLabel);
        frame.add(documentsLabel);
        frame.add(administratorLabel);
        frame.add(solicitorLabel);
        frame.add(deliveryGuyLabel);
        frame.add(motiveLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(statusField);
        frame.add(dateWithdrawalField);
        frame.add(administratorField);
        frame.add(solicitorField);
        frame.add(deliveryGuyField);
        frame.add(dateDeliveryField);

        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(motiveField);
        contextPopupMenu.addInComponet(observationField);

        //confugurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
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
     * @param codeBoxes lista com códigos das caixas
     * @param documents lista com documentos
     */
    public void refresh(List<Box> boxes, List<Book> books, List<Document> documents) {
        tableModel.refresh(boxes, books, documents);
        table.repaint();
    }//fim do método refresh

    /**
     * Adiciona documento a lista
     *
     * @param box caixa
     * @param book livro
     * @param document documento
     */
    public void addDocument(Box box, Book book, Document document) {
        tableModel.addRow(box, book, document);
    }//fim do método refresh

    /**
     * Preenche campos com dados
     *
     * @param withdrawal retirada
     */
    private void fillsView(Withdrawal withdrawal) {
        codeField.setText("" + withdrawal.getCodeWithDrawal());
        if (withdrawal.getStatus()) {
            statusField.setText("Ativo");
        } else {
            statusField.setText("Desativado");
        }
        try {
            dateWithdrawalField.setText(new SimpleDateFormat("dd/MM/yyyy").format(withdrawal.getDateWithdrawal()));
            if (withdrawal.getDateDelivery() != null) {
                dateDeliveryField.setText(new SimpleDateFormat("dd/MM/yyyy").format(withdrawal.getDateDelivery()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        administratorField.setText(withdrawal.getNameadministrator());
        solicitorField.setText(withdrawal.getSolicitor());
        deliveryGuyField.setText(withdrawal.getDeliveryGuy());
        observationField.setText(withdrawal.getObservationWithdrawal());
        motiveField.setText(withdrawal.getMotiveWithdrawal());
    }//fim do método Withdrawal

    /**
     * Fecha a janela
     */
    public void close() {
        frame.dispose();
    }//fim do método close

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_WITHDRAWAL);
    }//fim do método getColor

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Define evento para botão fechar
     *
     * @param actionListener ouvinte
     */
    public void setCloseButtonActionListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }//fim do método setCloseButtonActionListener

    /**
     * Requisita o foco
     */
    public void requestFrameFocus() {
        frame.requestFocusInWindow();
    }//fim da classe requestFrameFocus

    //método para testes
    public static void main(String args[]) {
        new SeeWithdrawalFrame();
    }
}//fim da classe SeeWithdrawalFrame 

