/*-
 * Classname:             OptionsController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:09:51
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.ConfigurationFrame;

/**
 * Controle de configurações do sistema
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class OptionsController implements SubjectInterface {

    //observadores
    private List<ObserverInterface> observers;
    //visão
    private ConfigurationFrame view;

    /**
     * Construtor sem parâmetros
     */
    public OptionsController() {
        observers = new ArrayList();
        view = new ConfigurationFrame();

        //define eventos
        view.setOKButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveOptions();
            }
        });
        //cancelar
        view.setCancelButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        //define evento de teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveOptions();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });

    }//fim do construtor

    /**
     * Salva as configurações
     */
    private void saveOptions() {
        Preferences preferencesSystem = PreferencesManager.getPreferencesSystem();
        preferencesSystem.put(PreferencesManager.KEY_LOCAL_DATABASE, view.getIP());
        preferencesSystem.put(PreferencesManager.KEY_DATABASE_USER, view.getUser());
        preferencesSystem.put(PreferencesManager.KEY_DATABASE_PASSWORD, String.valueOf(view.getPassword()));
        preferencesSystem.put(PreferencesManager.KEY_SCREEN_WIDTH, String.valueOf(view.getWidthSize()));
        preferencesSystem.put(PreferencesManager.KEY_SCREEN_HEIGTH, String.valueOf(view.getHeightSize()));
        if (TemporaryData.getUser() != null) {
            Preferences preferences = PreferencesManager.getPrefetencesUser(TemporaryData.getUser());
            preferences.putBoolean(PreferencesManager.KEY_WALLPAPER, view.getWallpaperEnable());

            List<Color> colorBoxes = view.getColorsBoxes();
            List<Color> colorBooks = view.getColorsBooks();
            List<Color> colorDocuments = view.getColorsDocuments();

            ColorConverter colorConverter = new ColorConverter();
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_A, colorBoxes.get(0));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_AE, colorBoxes.get(1));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_CC, colorBoxes.get(2));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_DP, colorBoxes.get(3));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_EX, colorBoxes.get(4));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_F, colorBoxes.get(5));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_FP, colorBoxes.get(6));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_MC, colorBoxes.get(7));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOX_SAE, colorBoxes.get(8));

            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_A, colorBooks.get(0));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_AE, colorBooks.get(1));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_CC, colorBooks.get(2));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_DP, colorBooks.get(3));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_EX, colorBooks.get(4));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_F, colorBooks.get(5));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_FP, colorBooks.get(6));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_MC, colorBooks.get(7));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_BOOK_SAE, colorBooks.get(8));

            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_A, colorDocuments.get(0));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_AE, colorDocuments.get(1));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_CC, colorDocuments.get(2));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_DP, colorDocuments.get(3));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_EX, colorDocuments.get(4));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_F, colorDocuments.get(5));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_FP, colorDocuments.get(6));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_MC, colorDocuments.get(7));
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DOCUMENT_SAE, colorDocuments.get(8));

            colorConverter.setColor(PreferencesManager.KEY_COLOR_DELETE_LIST, view.getColorDeleteList());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_DEVOLUTION, view.getColorDevolution());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_HISTORY, view.getColorHistory());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_REQUEST, view.getColorRequest());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_SEARCH, view.getColorSearch());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_USER, view.getColorUser());
            colorConverter.setColor(PreferencesManager.KEY_COLOR_WITHDRAWAL, view.getColorWithdrawal());
        }
        view.close();
        notifyObservers(false, true);

    }//fim do método saveOptions

    /**
     * Cancela o cadastro/edição da caixa
     */
    private void cancel() {
        view.close();
        notifyObservers(false, null);
    }//fim do método cancel

    /**
     * Registra observador
     *
     * @param observer observador
     */
    public void registerObserver(ObserverInterface observer) {
        observers.add(observer);
    }//fim do método registerObserver

    /**
     * Remove observador da lista
     *
     * @param observer observador
     */
    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }//fim do método removeObserver

    /**
     * Notifica os observadores das mudanças
     *
     * @param active janela ativa
     * @param object objeto de retorno
     */
    public void notifyObservers(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método notifyObservers
}//fim da classe OptionsController

