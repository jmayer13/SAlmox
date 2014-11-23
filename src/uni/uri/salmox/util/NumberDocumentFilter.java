
/*-
 * Classname:             NumberDocumentFilter.java
 *
 * Version information:   1.0
 *
 * Date:                  15/02/2013 - 15:14:16
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro numérico
 *
 * @see javax.swing.text.DocumentFilter;
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class NumberDocumentFilter extends DocumentFilter {

    /**
     * Construtor vazio
     */
    public NumberDocumentFilter() {
    }//fim do construtor

    /**
     * Chamado antes da inserção do texto no documento
     *
     * @param filterBypass FilterBypass que podem ser usados para transformar o
     * Document
     * @param offset o deslocamento > = 0 no documento para inserir o conteúdo
     * @param string a string para inserção
     * @param attr os atributos para associar com o conteúdo inserido.
     * @throws BadLocationException a posição de inserção dada não é uma posição
     * válida dentro do documento
     */
    @Override
    public void insertString(FilterBypass filterBypass, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        for (int i = 0; i < string.length(); i++) {
            //verifica se é letra (se não continua)
            if (Character.isDigit(string.charAt(i)) == false) {
                return;
            }

        }
        filterBypass.insertString(offset, string, attr);

    }//fim do método insertString

    /**
     * Chamado antes de substituir uma região de texto no documento
     *
     * @param fb FilterBypass que podem ser usados para transformar o Document
     * @param offset Localização no Documento
     * @param length Comprimento de texto para excluir
     * @param string Texto para inserir, nulo indica nenhum texto para inserir
     * @param attrs indicando atributos do texto inserido,
     * @throws BadLocationException a posição de inserção dada não é uma posição
     * válida dentro do documento
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
            throws BadLocationException {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i)) == false) {
                return;
            }
        }

        fb.replace(offset, length, string, attrs);
    }
}//fim da classe NumberDocumentFilter

