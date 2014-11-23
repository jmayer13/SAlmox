
/*-
 * Classname:             DateDocumentFilter.java
 *
 * Version information:   1.0
 *
 * Date:                  20/03/2013 - 15:32:56
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
 * Filtro para campo de texto no formato de data
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DateDocumentFilter extends DocumentFilter {
    /*
     * Nota: 2º e 5º posição se referem as / das datas 
     */

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
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
            throws BadLocationException {
        Document document = fb.getDocument();
        if (offset < 10) {
            fb.remove(offset, length);
            //se é um caracter
            if (string.length() == 1) {
                //se é um número
                if (Character.isDigit(string.charAt(0))) {
                    //se estiver na posição 2 ou 5
                    if (offset == 2 || offset == 5) {
                        //e não existir barra depois é inserida uma barra ao inicio da string do caracter atual
                        if (document.getLength() >= offset + 1) {
                            if (!document.getText(offset + 1, 1).equals("/")) {
                                string = "/" + string;
                            }
                        } else {
                            string = "/" + string;
                        }

                    }
                    //insere a string
                    fb.replace(offset, 0, string, attrs);

                    //se é uma barra
                } else if (string.equals("/")) {
                    //se a barra for digitada antes do 2 digito
                    if (offset == 1 || offset == 4) {
                        //adiciona-se um 0 a esquerda do digito anterior
                        fb.replace(offset - 1, length, "0", attrs);
                        //e uma barra 
                        fb.replace(offset + 1, length, "/", attrs);
                    }
                    //se estiver na 2 ou 5 posição 
                    if (offset == 2 || offset == 5) {
                        fb.replace(offset, length, "/", attrs);
                    }
                }
                //se for mais de um caractere
            } else if (string.length() > 1) {
                //se estiver na 0 posição e nada for deletado
                if (offset == 0 && length == 0) {
                    //se o campo não estiver vazio
                    if (document.getLength() > 0) {
                        //pega o texto atual do campo
                        String text = document.getText(0, document.getLength());
                        //remove / do campo e adiciona o texto sem /
                        string = string.replaceAll("/", "") + text.replaceAll("/", "");
                        //limpa campo
                        fb.remove(0, document.getLength());
                    }
                    //percorre string
                    for (int i = 0; i < string.length(); i++) {
                        //se estiver fora do limite ou a yyyy 
                        if (offset > 10 || document.getLength() >= 10) {
                            return;
                        }
                        //pega char atual
                        char charNow = string.charAt(i);

                        //se o character for um número
                        if (Character.isDigit(charNow)) {
                            //se estiver na posição 2 ou 5
                            if (offset == 2 || offset == 5) {
                                //adiciona uma / e incrementa uma posição
                                fb.replace(offset, 0, "/", attrs);
                                offset++;
                            }
                            //insere digito
                            fb.replace(offset, 0, String.valueOf(charNow), attrs);
                            //se for /
                        } else if (charNow == '/') {
                            //se estiver na 1 ou 4 posição (antes das /)
                            if (offset == 1 || offset == 4) {
                                //adiciona 0 antes do caractere
                                fb.replace(offset - 1, length, "0", attrs);
                            }
                            //se estiver na 2 ou 5 posição
                            if (offset == 2 || offset == 5) {
                                //adiciona /
                                fb.replace(offset, 0, "/", attrs);
                            }
                        }
                        offset++;
                    }//fim do for

                } else {
                    //se tiver texto e nada for deletado
                    if (document.getLength() > 0 && length == 0) {
                        //pega texto atual do campo
                        String text = document.getText(0, document.getLength());
                        //desformata, retirando /
                        text = text.replaceAll("/", "");
                        //retira / do texto atual
                        string = string.replaceAll("/", "");
                        string = text.substring(0, offset - 1) + string + text.substring(offset - 1);
                        //se ultrapassar o limite 
                        if (string.length() >= 10) {
                            string = string.substring(0, 9);
                        }
                        //posiciona na posição 0
                        offset = 0;

                        //limpa campo
                        fb.remove(0, document.getLength());
                    }
                    //percorre string
                    for (int i = 0; i < string.length(); i++) {
                        //se ultrapassar o limite retorna
                        if (offset > 10 || fb.getDocument().getLength() >= 10) {
                            return;
                        }
                        //pega char atual
                        char charNow = string.charAt(i);
                        //se for um digito 
                        if (Character.isDigit(charNow)) {
                            //se estiver na posição 2 ou 5
                            if (offset == 2 || offset == 5) {
                                //adiciona /
                                fb.replace(offset, 0, "/", attrs);
                                offset++;
                            }
                            //passa para o campo 
                            fb.replace(offset, 0, String.valueOf(charNow), attrs);
                            //se for /
                        } else if (charNow == '/') {
                            //se estiver na posição 2 ou 5
                            if (offset == 2 || offset == 5) {
                                fb.replace(offset, 0, "/", attrs);
                            }
                        }
                        offset++;
                    }//fim do for
                }
            }
        }
    }//fim do método replace
}//fim da classe DateDocumentFilter

