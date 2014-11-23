/*-
 * Classname:             AddBoxFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  19/03/2013 - 14:13:13
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.model.BoxAE;
import uni.uri.salmox.model.BoxCC;
import uni.uri.salmox.model.BoxDAC;
import uni.uri.salmox.model.BoxDAD;
import uni.uri.salmox.model.BoxDG;
import uni.uri.salmox.model.BoxDP;
import uni.uri.salmox.model.BoxDSG;
import uni.uri.salmox.model.BoxEX;
import uni.uri.salmox.model.BoxF;
import uni.uri.salmox.model.BoxFC;
import uni.uri.salmox.model.BoxFP;
import uni.uri.salmox.model.BoxMC;
import uni.uri.salmox.model.BoxSAE;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.JContextPopupMenu;
import uni.uri.salmox.util.NumberDocumentFilter;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame para cadastro e edição de caixas normais
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AddBoxFrame implements AddBoxInterface {

    //frame principal
    private JFrame frame;
    //label com categoria
    private JLabel titleLabel;
    //label código
    private JLabel codeLabel;
    //label responsáveis
    private JLabel responsibleLabel;
    //label observação
    private JLabel observationLabel;
    //campo de texto para código
    private JTextField codeField;
    //campo de texto para responsáveis
    private JTextField responsibleField;
    //área de texto para observação
    private JTextArea observationField;
    //scrollPane para o área de texto
    private JScrollPane scrollPane;
    //botão ok
    private JButton okButton;
    //botão cancelar
    private JButton cancelButton;
    //categoria da caixa
    private Category category;
    //título da caixa
    private String title;
    //caixa
    private Box box;
    //largura da tela
    private int x;
    //altura da tela
    private int y;

    /**
     * Construtor para cadastro de caixa
     *
     * @param category categoria da caixa
     * @param codeSugered código sugerido (ultimo código cadastrado +1)
     */
    public AddBoxFrame(Category category, int codeSugered) {
        title = "Adicionar Caixa " + category.getName();
        this.category = category;
        createView();
        codeField.setText(codeSugered + "");

    }//fim do construtor para cadastro

    /**
     * Construtor para edição de caixa
     *
     * @param category categoria
     * @param box caixa
     */
    public AddBoxFrame(Category category, Box box) {
        this.box = box;
        title = "Editar Caixa " + category.getName();
        this.category = category;
        createView();
        fillsView();
    }//fim do construtor para edição

    /**
     * Cria a janela
     */
    private void createView() {

        //inicializa componentes
        frame = new JFrame(title);
        titleLabel = new JLabel(category.getAbbreviation() + "-");
        codeLabel = new JLabel("Código: *");
        responsibleLabel = new JLabel("Responsáveis: *");
        observationLabel = new JLabel("Observação:");
        codeField = new JTextField();
        responsibleField = new JTextField();
        observationField = new JTextArea();
        scrollPane = new JScrollPane();
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCELAR");

        //define layout
        frame.setLayout(null);

        //configura textarea
        //quebra de linha
        observationField.setLineWrap(true);
        //impede separação de palavras
        observationField.setWrapStyleWord(true);

        //pega tamanho da tela
        takeScreenSize();

        //define localização e tamanho dos componentes
        frame.setBounds((x - 500) / 2, (y - 480) / 2, 500, 480);
        titleLabel.setBounds(130, 30, 50, 30);
        codeLabel.setBounds(30, 30, 90, 30);
        responsibleLabel.setBounds(30, 80, 120, 30);
        observationLabel.setBounds(30, 130, 100, 30);
        codeField.setBounds(182, 30, 100, 30);
        responsibleField.setBounds(150, 80, 250, 30);
        scrollPane.setBounds(150, 130, 250, 150);
        okButton.setBounds(120, 390, 140, 30);
        cancelButton.setBounds(300, 390, 140, 30);

        //define configurações visuais
        //cor
        frame.getContentPane().setBackground(getColor());
        titleLabel.setForeground(Color.BLACK);
        codeLabel.setForeground(Color.BLACK);
        responsibleLabel.setForeground(Color.BLACK);
        observationLabel.setForeground(Color.BLACK);
        codeField.setForeground(Color.BLACK);
        responsibleField.setForeground(Color.BLACK);
        observationField.setForeground(Color.BLACK);
        okButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.BLACK);

        //aligamento
        titleLabel.setHorizontalAlignment(JLabel.RIGHT);

        //fonte
        titleLabel.setFont(FontFactory.getFontLarge());
        codeLabel.setFont(FontFactory.getFontDefault());
        responsibleLabel.setFont(FontFactory.getFontDefault());
        observationLabel.setFont(FontFactory.getFontDefault());
        codeField.setFont(FontFactory.getFontDefault());
        responsibleField.setFont(FontFactory.getFontDefault());
        observationField.setFont(FontFactory.getFontDefault());
        okButton.setFont(FontFactory.getFontLarge());
        cancelButton.setFont(FontFactory.getFontLarge());

        //dicas
        codeField.setToolTipText("Insira o código da caixa (OBRIGATÓRIO)");
        responsibleField.setToolTipText("Insira o nome dos responsáveis pela caixa (OBRIGATÓRIO)");
        observationField.setToolTipText("Se desejado insira uma observação");
        if (box == null) {
            okButton.setToolTipText("Cadastra caixa");
            cancelButton.setToolTipText("Cancela o cadastro da caixa");
        } else {
            okButton.setToolTipText("Salva alterações da caixa");
            cancelButton.setToolTipText("Cancela a edição da caixa");
        }

        //define filtros
        DocumentFilter numberDocumentFilter = new NumberDocumentFilter();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(numberDocumentFilter);

        //adiciona os componentes ao frame
        frame.add(titleLabel);
        frame.add(codeLabel);
        frame.add(responsibleLabel);
        frame.add(observationLabel);
        frame.add(codeField);
        frame.add(responsibleField);
        scrollPane.setViewportView(observationField);
        frame.add(scrollPane);
        frame.add(okButton);
        frame.add(cancelButton);

        //define menu contexto
        JContextPopupMenu contextPopupMenu = new JContextPopupMenu();

        //adiciona evento aos itens do popupmenu
        contextPopupMenu.addInComponet(codeField);
        contextPopupMenu.addInComponet(responsibleField);
        contextPopupMenu.addInComponet(observationField);

        //configurações finais do frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        //define focus primario
        responsibleField.requestFocusInWindow();
    }//fim do método createView

    /**
     * Preenche os campos com dados para edição
     */
    private void fillsView() {
        codeField.setText("" + box.getCodeBoxSpecific());


        responsibleField.setText(box.getResponsibleBox());
        observationField.setText(box.getObservationBox());
    }//fim do método fillsView

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
     * Retorna dados dos campos em forma de caixa
     *
     * @return <code>Box</code> caixa cadastrada/editada
     */
    public Box getBox() {

        if (codeField.getText() == null || codeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o código da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (responsibleField.getText() == null || responsibleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o responsável da caixa!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            if (category == Category.A) {
                BoxA box = new BoxA();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxA(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;

            } else if (category == Category.AE) {
                BoxAE box = new BoxAE();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxAE(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.CC) {
                BoxCC box = new BoxCC();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxCC(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.DP) {
                BoxDP box = new BoxDP();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxDP(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.EX) {
                BoxEX box = new BoxEX();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxEX(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.F) {
                BoxF box = new BoxF();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxF(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.FC) {
                BoxFC box = new BoxFC();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSpecific(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.FP) {
                BoxFP box = new BoxFP();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxFP(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.MC) {
                BoxMC box = new BoxMC();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxMC(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.DAC) {
                BoxDAC box = new BoxDAC();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSpecific(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.DAD) {
                BoxDAD box = new BoxDAD();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSpecific(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.DG) {
                BoxDG box = new BoxDG();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSpecific(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.DSG) {
                BoxDSG box = new BoxDSG();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSpecific(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            } else if (category == Category.SAE) {
                BoxSAE box = new BoxSAE();
                if (this.box != null) {
                    box.setCodeBox(this.box.getCodeBox());
                }
                box.setCodeBoxSAE(Integer.valueOf(codeField.getText()));
                box.setTitleBox(category.getAbbreviation() + "-" + codeField.getText());
                box.setResponsibleBox(responsibleField.getText());
                box.setObservationBox(observationField.getText());
                return box;
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Formato incorreto!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return null;
    }//fim do método getBox

    /**
     * Define ouvinte para o botão ok
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerOKButton(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
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
        codeField.addKeyListener(keyListener);
        responsibleField.addKeyListener(keyListener);
        okButton.addKeyListener(keyListener);
        observationField.addKeyListener(keyListener);
        scrollPane.addKeyListener(keyListener);
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
     * Obtêm cor definida para essa janela
     *
     * @return <code>Color</code> cor
     */
    private Color getColor() {
        ColorFactory colorFactory = new ColorFactory(category);
        return colorFactory.getBoxColor();

    }//fim do método getColor

    //método para testes
    public static void main(String args[]) {
        AddBoxFrame addBoxFrame = new AddBoxFrame(Category.A, 0);
    }
}//fim da classe AddBoxFrame

