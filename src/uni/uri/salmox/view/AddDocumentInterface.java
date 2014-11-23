/*-
 * Classname:             AddDocumentInterface.java
 *
 * Version information:   1.0
 *
 * Date:                  24/05/2013 - 16:08:39
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import uni.uri.salmox.model.Document;

/**
 * Descrição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public interface AddDocumentInterface {

    /**
     * Fecha a janela
     */
    public void close();

    /**
     * Define a visibilidade do frame
     *
     * @param visible true = visivel false = invisivel
     */
    public void setVisible(boolean visible);

    /**
     * Define se frame está ativo
     *
     * @param enable true=ativo false=inativo
     */
    public void enableFrame(boolean enable);

    /**
     * Pega o docuimento da view
     *
     * @return <code>Document</code> documento
     */
    public Document getDocument();

    /**
     * Define evento para o botão OK
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerOKButton(ActionListener actionLiatener);

    /**
     * Define evento para o botão calcelar
     *
     * @param actionListener ouvinte
     */
    public void setActionListenetCancelButton(ActionListener actionListener);

    /**
     * Define evento de teclas para o frame
     *
     * @param keyListener ouvinte
     */
    public void setKeyListenerFrame(KeyListener keyListener);

    /**
     * Define evento para fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter);
}//fim da classe AddDocumentInterface

