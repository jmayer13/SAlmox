
/*-
 * Classname:             ScreenResolution.java
 *
 * Version information:   1.0
 *
 * Date:                  26/11/2012 - 14:08:44
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Pega tamanho da tela
 * @see java.awt.Dimension
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ScreenResolution {

    //largura
    private int x = 0;
    //altura
    private int y = 0;
   
    /**
     * Construtor de ScreenResolution responsável por pegar a resolução da tela
     */
    public ScreenResolution() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int) dim.getWidth();//largura
        y = (int) dim.getHeight();//altura
    }//fim do método construtor
  
    /**
     * Obtêm a largura da tela
     * @return <code>Integer</code> com a largura
     */
    public int getX() {
        return x;
    }//fim do método getX

    /**
     * Obtêm a altura da tela
     * @return <code>Integer</code> com a altura
     */
    public int getY() {
        return y;
    }//fim do método getY
}//fim da classe ScreenResolution

