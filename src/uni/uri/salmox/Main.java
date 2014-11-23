
/*-
 * Classname:             Main.java
 *
 * Version information:   1.0
 *
 * Date:                  08/10/2012 - 13:21:46
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox;

import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import uni.uri.salmox.controller.MainController;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;
import uni.uri.salmox.util.SingleInstanceApplication;

/**
 * Classe principal
 *
 * @see
 * @author Jonas
 */
public class Main {

    /**
     * Método principal da aplicação
     *
     * @param args
     */
    public static void main(String[] args) {

        //verifica se o programa já está aberto
        //checa se o arquivo pode ser deletado (não está em uso)
        if (SingleInstanceApplication.check()) {
            //bloqueia o arquivo
            SingleInstanceApplication.lock();

            //se for a primeira vez salva configurações do sistema básicas para inicialização
            Preferences preferences = PreferencesManager.getPreferencesSystem();
            if (preferences.getInt(PreferencesManager.KEY_SCREEN_HEIGTH, 0) == 0) {
                ScreenResolution screenResolution = new ScreenResolution();
                PreferencesManager.putScreenSize(screenResolution.getX(), screenResolution.getY());
                //abre controller de configuração para configurações iniciais de banco
                // OptionsController pptionsController = new OptionsController();
            }
            if (Integer.parseInt(System.getProperty("java.version").split("\\.")[1]) < 7) {
                JOptionPane.showMessageDialog(null, "Atualize o Java para a versão 7 ou superior!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
            //abre controller principal
            MainController mainController = new MainController();

        } else {
            //se o arquivo está em uso não permite o acessoa ao programa
            System.out.println("O programa já foi aberto!");
            JOptionPane.showMessageDialog(null, "O programa já está em execuação!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }//fim do método main
}//fim da classe Main

