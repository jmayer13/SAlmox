/*- 
 * Classname:             WithdrawalGenerateFrame.java 
 * 
 * Version information:   1.0 
 * 
 * Date:                  08/01/2014 - 09:41:23 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.DateDocumentFilter;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.TemporaryData;

/**
 * Janela para finalização de retirada
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalGenerateFrame {

    //janela
    private JFrame frame;
    //label titulo 
    private JLabel titleFrameLabel;
    //label data retiradas
    private JLabel withdrawalDateLabel;
    //label motivos
    private JLabel motiveLabel;
    //label pessoa que retirou
    private JLabel deliveryGuyLabel;
    //label solicitante
    private JLabel solicitorLabel;
    //label observação
    private JLabel observationLabel;
    //campo data retirada
    private JTextField withdrawalDateField;
    //campo moyivo
    private JTextField motiveField;
    //campo pessoa que retirou
    private JTextField deliveryGuyField;
    //campo solicitante
    private JTextField solicitorField;
    //campo observacao
    private JTextArea observationField;
    //scrollPane para o área de texto
    private JScrollPane scrollPane;
    //botao ok
    private JButton okButton;
    //botao cancelar
    private JButton cancelButton;
    //retirar
    private Withdrawal withdrawal;

    /**
     * Construtor sem parâmetro
     */
    public WithdrawalGenerateFrame() {
        createView();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        withdrawalDateField.setText(df.format(new Date()));
    }//fim do construtor

    /**
     * Construtor com solicitação
     *
     * @param withdrawal
     */
    public WithdrawalGenerateFrame(Withdrawal withdrawal) {
        createView();
        this.withdrawal = withdrawal;
        fillsView();
    }//fim do construtor

    /**
     * Cria view
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame("Retirada - Gerar relatório");
        titleFrameLabel = new JLabel("Retirada - Gerar relatório");
        withdrawalDateLabel = new JLabel("Data da retirada:*");
        motiveLabel = new JLabel("Motivo:");
        deliveryGuyLabel = new JLabel("Pessoa que retirou:*");
        solicitorLabel = new JLabel("Solicitante:*");
        observationLabel = new JLabel("Observação:");
        withdrawalDateField = new JTextField();
        motiveField = new JTextField();
        deliveryGuyField = new JTextField();
        solicitorField = new JTextField();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(observationField);
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define configurações iniciais
        frame.setLayout(null);
        frame.setSize(500, 470);
        frame.setLocationRelativeTo(null);
        titleFrameLabel.setHorizontalAlignment(JLabel.CENTER);

        //define tamanho e localização
        titleFrameLabel.setBounds((500 - 200) / 2, 10, 200, 30);
        withdrawalDateLabel.setBounds(20, 70, 150, 30);
        motiveLabel.setBounds(20, 120, 150, 30);
        deliveryGuyLabel.setBounds(20, 170, 150, 30);
        solicitorLabel.setBounds(20, 220, 150, 30);
        observationLabel.setBounds(20, 270, 150, 30);
        withdrawalDateField.setBounds(170, 70, 150, 30);
        motiveField.setBounds(170, 120, 150, 30);
        deliveryGuyField.setBounds(170, 170, 150, 30);
        solicitorField.setBounds(170, 220, 150, 30);
        scrollPane.setBounds(170, 270, 200, 60);
        okButton.setBounds(100, 380, 150, 30);
        cancelButton.setBounds(270, 380, 150, 30);

        //define filtros
        DocumentFilter dateDocumentFilter = new DateDocumentFilter();
        ((AbstractDocument) withdrawalDateField.getDocument()).setDocumentFilter(dateDocumentFilter);

        //define fonte
        frame.getContentPane().setBackground(getColor());
        titleFrameLabel.setFont(FontFactory.getFontLarge());
        withdrawalDateLabel.setFont(FontFactory.getFontDefault());
        motiveLabel.setFont(FontFactory.getFontDefault());
        deliveryGuyLabel.setFont(FontFactory.getFontDefault());
        solicitorLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        withdrawalDateField.setFont(FontFactory.getFontDefault());
        motiveField.setFont(FontFactory.getFontDefault());
        deliveryGuyField.setFont(FontFactory.getFontDefault());
        solicitorField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //define cores
        titleFrameLabel.setBackground(Color.BLACK);
        withdrawalDateLabel.setBackground(Color.BLACK);
        motiveLabel.setBackground(Color.BLACK);
        deliveryGuyLabel.setBackground(Color.BLACK);
        solicitorLabel.setBackground(Color.BLACK);
        observationLabel.setBackground(Color.BLACK);
        withdrawalDateField.setForeground(Color.BLACK);
        motiveField.setForeground(Color.BLACK);
        deliveryGuyField.setForeground(Color.BLACK);
        solicitorField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //junçao de componentes
        frame.add(titleFrameLabel);
        frame.add(withdrawalDateLabel);
        frame.add(motiveLabel);
        frame.add(deliveryGuyLabel);
        frame.add(solicitorLabel);
        frame.add(observationLabel);
        frame.add(withdrawalDateField);
        frame.add(motiveField);
        frame.add(deliveryGuyField);
        frame.add(solicitorField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }//fim do método createView

    public static void main(String args[]) {
        new WithdrawalGenerateFrame();
    }

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        //formato de data
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        withdrawalDateField.setText(df.format(withdrawal.getDateWithdrawal()));
        motiveField.setText(withdrawal.getMotiveWithdrawal());
        deliveryGuyField.setText(withdrawal.getDeliveryGuy());
        solicitorField.setText(withdrawal.getSolicitor());
        observationField.setText(withdrawal.getObservationWithdrawal());
    }//fim do método fillsView

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
     * Define ouvinte para o botão ok
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerOKButton(ActionListener actionLiatener) {
        okButton.addActionListener(actionLiatener);
    }//fim do método setActionListenerOKButton

    /**
     * Define ouvinte para o botão cancelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenetCancelButton(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }//fim do método setActionListenetCancelButton

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        withdrawalDateField.addKeyListener(keyListener);
        motiveField.addKeyListener(keyListener);
        deliveryGuyField.addKeyListener(keyListener);
        solicitorField.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        scrollPane.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        cancelButton.addKeyListener(keyListener);
    }//fim do método setKeyListenerFrame

    /**
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter) {
        frame.addWindowListener(windowAdapter);
    }//fim do método setCloseWindowListener

    /**
     * Pega requisição
     */
    public Withdrawal getWithdrawal() {
        if (withdrawalDateField.getText() == null || withdrawalDateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira a data!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

            return null;
        }
        if (deliveryGuyField.getText() == null || deliveryGuyField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira o nome da pessoa que retirou!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        if (solicitorField.getText() == null || solicitorField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira o solicitante!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

            return null;
        }
        if (withdrawal == null) {
            withdrawal = new Withdrawal();
        }
        withdrawal.setAdministrator((Administrator) TemporaryData.getUser());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date date;
        try {
            date = new java.sql.Date(df.parse(withdrawalDateField.getText()).getTime());

        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, "Formato da data incorreto!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        withdrawal.setDateWithdrawal(date);
        withdrawal.setMotiveWithdrawal(motiveField.getText());
        withdrawal.setDeliveryGuy(deliveryGuyField.getText());
        withdrawal.setSolicitor(solicitorField.getText());
        withdrawal.setObservationWithdrawal(observationField.getText());
        return withdrawal;
    }//fim do método getWithdrawal

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
     * Define nome do usuário
     *
     * @param nameDefaultUser nome do usuário
     */
    public void setUserName(String nameDefaultUser) {
        solicitorField.setText(nameDefaultUser);
    }//fim do método setUserName

    /**
     * Define motivo
     *
     * @param motiveRequest motivo da requisição
     */
    public void setMotive(String motiveRequest) {
        motiveField.setText(motiveRequest);
    }//fim do método setMotive

    /**
     * Define observação
     *
     * @param observationRequest
     */
    public void setObservation(String observationRequest) {
        observationField.setText(observationRequest);
    }//fim do método setObservation
}//fim da classe WithdrawalGenerateFrame 

