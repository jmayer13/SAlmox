
/*-
 * Classname:             FontFactory.java
 *
 * Version information:   1.0
 *
 * Date:                  23/01/2013 - 14:02:34
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.awt.Font;

/**
 * Classe responsável pelos principais estilos de fonte
 *
 * @see java.awt.Font
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class FontFactory {

    //fonte padrão
    private static Font fontDefault;
    //fonte grande
    private static Font fontLarge;

    /**
     * Construtor sem parâmetros
     */
    public FontFactory() {
    }//fim do construtor

    /**
     * Obtêm a fonte padrão
     *
     * @return <code>Font</code> fonte padrão
     */
    public static Font getFontDefault() {
        fontDefault = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        return fontDefault;
    }//fim do método getFontDefault

    /**
     * Obtêm a fonte pequena
     *
     * @return <code>Font</code> fonte pequena
     */
    public static Font getFontSmall() {
        fontDefault = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        return fontDefault;
    }//fim do método getFontSmall

    /**
     * Obtêm a fonte grande
     *
     * @return <code>Font</code> fonte grande
     */
    public static Font getFontLarge() {
        fontLarge = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        return fontLarge;
    }//fim do método getFontLarge
}//fim da classe FontFactory

