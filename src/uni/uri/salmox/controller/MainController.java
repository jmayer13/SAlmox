/*-
 * Classname:             MainController.java
 *
 * Version information:   1.0
 *
 * Date:                  21/05/2013 - 16:45:39
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.util.prefs.Preferences;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.PreferencesManager;

/**
 * Controle principal
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class MainController implements ObserverInterface {

    //controle de login
    private LoginController loginController;

    /**
     * Construtor sem parâmetros
     */
    public MainController() {
        //verifica se os parâmetros do banco já foram informados
        Preferences preferences = PreferencesManager.getPreferencesSystem();
        if (preferences.get(PreferencesManager.KEY_LOCAL_DATABASE, "").isEmpty()) {
            OptionsController optionsController = new OptionsController();
            optionsController.registerObserver(this);
        } else {
            loginController = new LoginController();
        }
    }//fim do construtor

    /**
     * Atualiza observador
     *
     * @param active subjeto ativo?
     * @param object resposta
     */
    public void update(boolean active, Object object) {
        if (!active) {
            if (object instanceof Boolean) {
                boolean notice = (Boolean) object;
                if (notice) {
                    loginController = new LoginController();
                }
            }
        }
    }//fim do método update
}//fim da classe MainController

