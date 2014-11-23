/*-
 * Classname:             GenerateWithdrawalReportFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  29/04/2013 - 13:44:58
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JAutoSuggestComboBox;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para inserir informações de retiradas e gerar relatórios
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class GenerateWithdrawalReportFrame {

    //frame principal
    private JFrame frame;
    //label título do frame
    private JLabel titleFrameLabel;
    //label solicitante
    private JLabel solicitorLabel;
    //label retirado por
    private JLabel withdrawnByLabel;
    //label responsável
    private JLabel responsibleLabel;
    //label motivo
    private JLabel motiveLabel;
    //label observação
    private JLabel observationLabel;
    //campo para solicitante
    private JAutoSuggestComboBox solicitorField;
    //campo para retirado por
    private JAutoSuggestComboBox withdrawanByField;
    //campo para responsaceis
    private JAutoSuggestComboBox reponsibleField;
    //campo para motivo
    private JTextArea motiveField;
    //campo paea observação
    private JTextArea observationField;
    //scrollpanes para text areas
    private JScrollPane motivePane;
    private JScrollPane observationPane;
    //botão gerar relatório
    private JButton generateButton;
    //botão cancelar
    private JButton cancelButton;
    //largura
    private int x;
    //altura
    private int y;
    //requisição
    private Withdrawal withdrawal;

    /**
     * Construtor sem parâmetros
     *
     * @param withdrawal
     */
    public GenerateWithdrawalReportFrame(Withdrawal withdrawal) {
        withdrawal = new Withdrawal();
        createView();
    }//fim do construtor sem parâmetros

    /**
     * Cria visão
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame("Gerar relatório");
        titleFrameLabel = new JLabel("Gerar relatório");
        solicitorLabel = new JLabel("Solicitante: *");
        withdrawnByLabel = new JLabel("Retirado por: *");
        responsibleLabel = new JLabel("Renponsável: *");
        motiveLabel = new JLabel("Motivo:");
        observationLabel = new JLabel("Observação:");
        solicitorField = new JAutoSuggestComboBox();
        withdrawanByField = new JAutoSuggestComboBox();
        reponsibleField = new JAutoSuggestComboBox();
        motiveField = new JTextArea();
        observationField = new JTextArea();
        motivePane = new JScrollPane();
        observationPane = new JScrollPane();
        generateButton = new JButton("Gerar");
        cancelButton = new JButton("Cancelar");

        //define layout
        frame.setLayout(null);

        //configura textarea
        //quebra de linha
        motiveField.setLineWrap(true);
        observationField.setLineWrap(true);
        //impede separação de palavras
        motiveField.setWrapStyleWord(true);
        observationField.setWrapStyleWord(true);

        //define alinhamento
        titleFrameLabel.setHorizontalAlignment(JLabel.CENTER);

        //pega o tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds((x - 500) / 2, (y - 480) / 2, 500, 480);
        titleFrameLabel.setBounds((500 - 150) / 2, 10, 150, 30);
        solicitorLabel.setBounds(50, 50, 100, 30);
        withdrawnByLabel.setBounds(50, 100, 125, 30);
        responsibleLabel.setBounds(50, 150, 125, 30);
        motiveLabel.setBounds(50, 200, 100, 30);
        observationLabel.setBounds(50, 280, 100, 30);
        solicitorField.setBounds(175, 50, 200, 30);
        withdrawanByField.setBounds(175, 100, 200, 30);
        reponsibleField.setBounds(175, 150, 200, 30);
        motivePane.setBounds(175, 200, 250, 60);
        observationPane.setBounds(175, 280, 250, 60);
        generateButton.setBounds(100, 390, 150, 30);
        cancelButton.setBounds(270, 390, 150, 30);

        //define cor
        frame.getContentPane().setBackground(getColor());
        titleFrameLabel.setForeground(Color.BLACK);
        solicitorLabel.setForeground(Color.BLACK);
        withdrawnByLabel.setForeground(Color.BLACK);
        responsibleLabel.setForeground(Color.BLACK);
        motiveLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        solicitorField.setForeground(Color.BLACK);
        withdrawanByField.setForeground(Color.BLACK);
        reponsibleField.setForeground(Color.BLACK);
        motiveField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        generateButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //define fonte
        solicitorLabel.setFont(FontFactory.getFontDefault());
        titleFrameLabel.setFont(FontFactory.getFontLarge());
        withdrawnByLabel.setFont(FontFactory.getFontDefault());
        responsibleLabel.setFont(FontFactory.getFontDefault());
        motiveLabel.setFont(FontFactory.getFontDefault());;
        observationLabel.setFont(FontFactory.getFontDefault());
        solicitorField.setFont(FontFactory.getFontDefault());
        withdrawanByField.setFont(FontFactory.getFontDefault());
        reponsibleField.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        generateButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        solicitorField.setToolTipText("Insira o nome de quem solicitou a retirada");
        withdrawanByField.setToolTipText("Insira o nome de quem veio buscar os documentos solicitados");
        reponsibleField.setToolTipText("Insira o nome dos responsáveis pela retirada");
        motiveField.setToolTipText("Se desejado insira o motivo da requisição");
        observationField.setToolTipText("Se desejado insira uma observação");
        generateButton.setToolTipText("Gera o relatório de retirada");
        cancelButton.setToolTipText("Cancela");

        //adiciona os componentes ao frame
        motivePane.setViewportView(motiveField);
        observationPane.setViewportView(observationField);
        frame.add(titleFrameLabel);
        frame.add(solicitorLabel);
        frame.add(withdrawnByLabel);
        frame.add(responsibleLabel);
        frame.add(motiveLabel);
        frame.add(observationLabel);
        frame.add(solicitorField);
        frame.add(withdrawanByField);
        frame.add(reponsibleField);
        frame.add(motivePane);
        frame.add(observationPane);
        frame.add(generateButton);
        frame.add(cancelButton);

        //popup menu
        //cria popupMenu
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(solicitorField);
        contextPopupMenu.addInComponet(withdrawanByField);
        contextPopupMenu.addInComponet(reponsibleField);
        contextPopupMenu.addInComponet(motiveField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
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
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

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
     * Carrega listas para auto-sugestão nos campos
     *
     * @param withdrawals retiradas anteriores
     */
    public void setAutoSuggestList(List<Withdrawal> withdrawals) {

        //solicitantes
        List<String> solicitors = new ArrayList();
        //retirado por
        List<String> withdrawnBys = new ArrayList();
        //responsáveis
        List<String> responsibles = new ArrayList();
        //preenche listas, testando para não ter elementos repetidos 
        for (int i = 0; i < withdrawals.size(); i++) {
            Withdrawal withdrawal = withdrawals.get(x);
            if (!solicitors.contains(withdrawal.getSolicitor())) {
                solicitors.add(withdrawal.getSolicitor());
            }
            if (!withdrawnBys.contains(withdrawal.getDeliveryGuy())) {
                withdrawnBys.add(withdrawal.getDeliveryGuy());
            }
            if (!responsibles.contains(withdrawal.getNameadministrator())) {
                responsibles.add(withdrawal.getNameadministrator());
            }
        }
    }//fim do método setAutoSuggestList

    /**
     * Obtêm Retirada
     *
     * @return <code>Withdrawal</code> retirada
     */
    public Withdrawal getWithdrawal() {
        withdrawal.setMotiveWithdrawal(motiveField.getText());
        withdrawal.setObservationWithdrawal(observationField.getText());
        withdrawal.setDeliveryGuy(withdrawal.getDeliveryGuy());
        withdrawal.setSolicitor(solicitorField.getText());
        return withdrawal;
    }//fim do método getWithdrawal

    /**
     * Define ouvinte para o botão gerar
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerGenerateButton(ActionListener actionLiatener) {
        generateButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerGenerateButton

    /**
     * Define ouvinte para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenetCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenetCancelButton

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(PreferencesManager.KEY_COLOR_WITHDRAWAL);
    }//fim do método getColor

    //método para testes
    public static void main(String[] args) {
        GenerateWithdrawalReportFrame GenerateReportWithdrawalDefaultUserFrame = new GenerateWithdrawalReportFrame(null);
    }
}//fim da classe GenerateWithdrawalReportFrame 

