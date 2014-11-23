/*-
 * Classname:             ColorConverter.java
 *
 * Version information:   1.0
 *
 * Date:                  20/05/2013 - 14:13:49
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

/**
 * Serializador e deserializador de cores
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ColorConverter {

    //preferências
    private Preferences preferences;
    //cor serializada ou não
    private byte[] byteColor;

    /**
     * Construtor sem parâmetros
     */
    public ColorConverter() {
        //busca preferência do usuário atual
        preferences = PreferencesManager.getPrefetencesUser(TemporaryData.getUser());
    }//fim do construtor

    /**
     * Define cor para conversão e indexação as preferências
     *
     * @param key chave da cor
     * @param color cor
     */
    public void setColor(String key, Color color) {
        try {
            //cria stream de saida de array de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //cria stream de saida de objeto com stream de array de bytes
            ObjectOutputStream os = new ObjectOutputStream(out);
            //passa cor para o stream de saida de objetos
            os.writeObject(color);
            //recebe o array de bytes do stream de saida de array de bytes
            //PreferencesManager.putGenericColor(key, out.toByteArray());
            PreferencesManager.getPrefetencesUser(TemporaryData.getUser()).putByteArray(key, out.toByteArray());
        } catch (IOException ioException) {
            System.err.println("Erro: ao converter dados de cores de preferencias!");
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi definir cores personalizadas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(ioException);
        }
    }//fim do método setColor

    /**
     * Obtêm cor convertida das preferência
     *
     * @param key chave da cor
     * @return <code>Color</code> cor convertida
     */
    public Color getColor(String key) {

        //obtêm array de bytes da cor serializada
        byteColor = preferences.getByteArray(key, null);
        //se o array não estiver vazio
        if (byteColor != null) {
            //cria stream de entrada de array de bytes com a cor serializada
            ByteArrayInputStream in = new ByteArrayInputStream(byteColor);
            //cria stream de entrada de objeto
            ObjectInputStream is;
            Color color = null;
            try {
                //converte array de bytes em objeto
                is = new ObjectInputStream(in);
                //converte objetos em cor
                color = (Color) is.readObject();
            } catch (IOException exception) {
                color = null;
                System.err.println("Erro: ao converter dados de cores de preferencias!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi obter cores personalizadas!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(exception);
            } catch (ClassNotFoundException exception) {
                color = null;
                System.err.println("Erro: ao converter dados de cores de preferencias!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi obter cores personalizadas!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(exception);
            }

            //se ocorreu erro retorna branco
            if (color != null) {
                return color;
            } else {
                return Color.WHITE;
            }
        } else {
            //retorna branco se cor não foi definida
            return Color.WHITE;
        }
    }//fim do método getColor
}//fim da classe ColorConverter

