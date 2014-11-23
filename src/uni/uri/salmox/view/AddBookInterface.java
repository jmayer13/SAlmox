/*-
 * Classname:             AddBookInterface.java
 *
 * Version information:   1.0
 *
 * Date:                  24/05/2013 - 16:03:13
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import uni.uri.salmox.model.Book;

/**
 * Interface para view de adição/edição de livros
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public interface AddBookInterface {

    /**
     * Fecha a view
     */
    public void close();

    /**
     * Define visibilidade do frame
     *
     * @param visible true-visivel false-invisivel
     */
    public void setVisible(boolean visible);

    /**
     * Define se a janela está ativa
     *
     * @param enable true-ativada false-desativada
     */
    public void enableFrame(boolean enable);

    /**
     * Retorna dados dos campos em forma de livro
     *
     * @return <code>Book</code> livro
     */
    public Book getBook();

    /**
     * Define ouvinte para o botão ok
     *
     * @param actionLiatener ouvinte
     */
    public void setActionListenerOKButton(ActionListener actionLiatener);

    /**
     * Define ouvinte para o botão cancelar
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
     * Define evento de fechamento de janela
     *
     * @param windowAdapter ouvinte
     */
    public void setCloseWindowListener(WindowAdapter windowAdapter);
}//fim da classe AddBookInterface

