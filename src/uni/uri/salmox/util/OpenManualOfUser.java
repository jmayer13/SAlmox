
/*-
 * Classname:             OpenManualOfUser.java
 *
 * Version information:   1.0
 *
 * Date:                  27/11/2012 - 16:04:08
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.io.File;
import javax.swing.JOptionPane;

/**
 * Abre o manual do usuário
 *
 * @see java.io.File
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class OpenManualOfUser {

    /**
     * Método <tt>static</tt> que abre o manual.<br />
     * <i>O Manual deve estar presente no local especificado!</i>
     */
    public void openManualUser() {
        try {
            //abre o arquivo com programa especifico da extensão
            java.awt.Desktop.getDesktop().open(new File("Manual do usuário.pdf"));
        } catch (Exception iOException) {
            System.out.println("Manual não encontrado!");
            System.err.println("Erro: Manual não encontrado!");
            iOException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Manual não encontrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(iOException);
        }
    }//fim do método openManualUser
}//fim da classe OpenManualOfUser

