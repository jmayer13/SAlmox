
/*-
 * Classname:             JContextPopupMenu.java
 *
 * Version information:   0.8
 *
 * Date:                  05/04/2013 - 15:49:25
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

/**
 * Menu de contexto
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class JContextPopupMenu {

    //menu popup
    private final JPopupMenu popupMenu = new JPopupMenu();
    //itens do menu
    //itens do menu recortar
    private JMenuItem cutMenuItem;
    //itens do menu copiar
    private JMenuItem copyMenuItem;
    //itens do menu colar
    private JMenuItem pasteMenuItem;
    //itens do menu selecionar tudo
    private JMenuItem selectAllMenuItem;

    /**
     * Construtor sem parâmetros
     */
    public JContextPopupMenu() {
        //inicializa componentes
        cutMenuItem = new JMenuItem("Recortar");
        copyMenuItem = new JMenuItem("Copiar");
        pasteMenuItem = new JMenuItem("Colar");
        selectAllMenuItem = new JMenuItem("Selecionar tudo");

        //compoe menu popup
        popupMenu.add(cutMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(selectAllMenuItem);

        //define funções para o menu popup        
        ActionListener popupMenuListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //obtêm componente do menu que foi acionado
                Object source = e.getSource();
                //se for recortar
                if (source == cutMenuItem) {
                    //se o invocador for um componente de texto
                    if (popupMenu.getInvoker() instanceof JTextComponent) {
                        //tranforma em campo de texto e corta
                        JTextComponent textComponent = (JTextComponent) popupMenu.getInvoker();
                        textComponent.cut();
                        //se for o componente criado por mim util.JAutoSuggestComboBox
                    } else if (popupMenu.getInvoker() instanceof JAutoSuggestComboBox) {
                        //transforma e recorta
                        JAutoSuggestComboBox autoSuggestComboBox = (JAutoSuggestComboBox) popupMenu.getInvoker();
                        autoSuggestComboBox.cut();
                    }

                    //se for copiar
                } else if (source == copyMenuItem) {
                    //se for um campo de texto
                    if (popupMenu.getInvoker() instanceof JTextComponent) {
                        //transforma em campo de texto e copia
                        JTextComponent textComponent = (JTextComponent) popupMenu.getInvoker();
                        textComponent.copy();
                        //se for o componente criado por mim util.JAutoSuggestComboBox
                    } else if (popupMenu.getInvoker() instanceof JAutoSuggestComboBox) {
                        //transforma e copia
                        JAutoSuggestComboBox autoSuggestComboBox = (JAutoSuggestComboBox) popupMenu.getInvoker();
                        autoSuggestComboBox.copy();
                    }

                    //se for colar
                } else if (source == pasteMenuItem) {
                    //se for campo de texto
                    if (popupMenu.getInvoker() instanceof JTextComponent) {
                        //transforma em campo de texto e cola
                        JTextComponent textComponent = (JTextComponent) popupMenu.getInvoker();
                        textComponent.paste();
                        //se for o componente criado por mim util.JAutoSuggestComboBox
                    } else if (popupMenu.getInvoker() instanceof JAutoSuggestComboBox) {
                        //trasforma e cola
                        JAutoSuggestComboBox autoSuggestComboBox = (JAutoSuggestComboBox) popupMenu.getInvoker();
                        autoSuggestComboBox.paste();
                    }

                    //se for selecionar tudo
                } else if (source == selectAllMenuItem) {
                    //se for campo de texto
                    if (popupMenu.getInvoker() instanceof JTextComponent) {
                        //transforma em campo de texto e seleciona todo o rexto
                        JTextComponent textComponent = (JTextComponent) popupMenu.getInvoker();
                        textComponent.selectAll();

                        //se for o componente criado por mim util.JAutoSuggestComboBox
                    } else if (popupMenu.getInvoker() instanceof JAutoSuggestComboBox) {
                        //transforma e seleciona
                        JAutoSuggestComboBox autoSuggestComboBox = (JAutoSuggestComboBox) popupMenu.getInvoker();
                        autoSuggestComboBox.selectAll();
                    }
                }
            }
        };

        //adiciona eventos ao menu
        cutMenuItem.addActionListener(popupMenuListener);
        copyMenuItem.addActionListener(popupMenuListener);
        pasteMenuItem.addActionListener(popupMenuListener);
        selectAllMenuItem.addActionListener(popupMenuListener);
    }//fim do construtor

    /**
     * Adiciona o menu de contexto ao componente
     *
     * @param component componente
     */
    public void addInComponet(Component component) {

        //- Nota de interoperabilidade: Alguns sistemas operacionais mostram o
        // munu popup no press e outras no release, por isso os dois casos
        //adiciona ouvinte ao component
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }//fim do método addInComponet
}//fim da classe JContextPopupMenu

