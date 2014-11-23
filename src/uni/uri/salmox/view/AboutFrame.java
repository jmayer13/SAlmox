/*-
 * Classname:             AboutFrame.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 15:02:15
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import uni.uri.salmox.util.FontFactory;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;

/**
 * Frame responsável por mostrar algumas informações sobre o programa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AboutFrame {

    //janela principal
    private JFrame frame;
    //àrea de texto em que as informações são adicionadas
    private JTextArea textArea;
    //painel rolável para a textarea
    private JScrollPane scrollPane;
    //largura da tela
    private int x;
    //altura da tela
    private int y;
    //título da tela
    private String title;

    /**
     * Construtor sem parâmetros
     */
    public AboutFrame() {
        //define título
        title = "Sobre";
        //cria visão
        createView();
        //preenche visão
        fillsView();
    }//fim do construtor

    /**
     * Cria visão
     */
    private void createView() {
        //inicializa componentes
        frame = new JFrame(title);
        textArea = new JTextArea();
        scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //define opções de layout
        frame.setLayout(null);
        textArea.setEditable(false);
        textArea.setLineWrap(true);//quebra de linha
        textArea.setWrapStyleWord(true);//impede separação de palavras

        //define posição e tamanho
        takeScreenSize();
        frame.setBounds((x - 500) / 2, (y - 350) / 2, 500, 350);
        scrollPane.setBounds(10, 10, 460, 300);

        //configurações gráficas 
        textArea.setFont(FontFactory.getFontDefault());
        textArea.setBackground(Color.WHITE);

        //junção de componentes
        scrollPane.setViewportView(textArea);
        frame.add(scrollPane);

        //configurações finais
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //adiciona evento ao fechar a janela
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }//fim do método createView

    /**
     * Busca tamanho da tela
     */
    public void takeScreenSize() {
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
     * Preenche visão com dados
     */
    private void fillsView() {
        textArea.append("Sistema de Gerência de Documentos do Arquivo Central da URI\n");
        textArea.append("Versão: 1.1\n");
        textArea.append("Data de Inicio:10/11/2014 \n");
        textArea.append("Desenvoledor: Jonas Mayer (jmayer13@hotmail.com) \n");
        textArea.append("\n");
        textArea.append(" O Sistema de Gerência de Documentos do Arquivo Central da URI foi o produto do projeto de pesquisa \"Desenvolvimento de um Software para a Gestão de Documentos - da Análise até a Implantação.\"orientado pela Prof. Luciéli Tolfo Beque Guerra do curso de Ciência da Computação da URI - Campus Santiago.  ");
        textArea.append("\n");
        textArea.append(" O objetivo inicial desse software é prover o registro da localização de documentos e do trafego dos mesmos, a manutenção destes registros e a administração das solicitações e retiradas dos documentos.");
    }//fim do método fillsView

    //método para testes
    public static void main(String[] args) {
        new AboutFrame();
    }
}//fim da classe AboutFrame

