
/*-
 * Classname:             DecimalNumberDocumentFilter.java
 *
 * Version information:   1.0
 *
 * Date:                  12/04/2013 - 16:01:42
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Filtro para campo de texto de números decimais
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DecimalNumberDocumentFilter extends DocumentFilter {

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
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        //obtêm documento
        Document document = fb.getDocument();
        //se um texto for removido 
        if (length > 0) {
            //apaga texto
            fb.remove(offset, length);
        }
        //prcorre string inserida
        for (int i = 0; i < string.length(); i++) {
            //obtêm char atual
            char charNow = string.charAt(i);
            //se for digito insere
            if (Character.isDigit(charNow)) {
                fb.replace(offset, 0, String.valueOf(charNow), attrs);
                //se for ponto ou virgula (a prova de noobs)
            } else if (charNow == '.' || charNow == ',') {
                //se ja não houer um ponto
                if (!document.getText(0, document.getLength()).contains(".")) {
                    //insere ponto
                    fb.replace(offset, 0, ".", attrs);
                }
            }
            offset++;
        }
    }//fim do método replace
}//fim da classe DecimalNumberDocumentFilter

