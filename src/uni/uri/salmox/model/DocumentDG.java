/*- 
 * Classname:             DocumentDG.java 
 * 
 * Version information:   (versão) 
 * 
 * Date:                  02/08/2014 - 08:29:00 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de formandos, herda de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentDG extends Document {

    /**
     * Método construtor sem parâmetros
     */
    public DocumentDG() {
        super();
    }//fim do método construtor

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String presente = "";
        if (this.getPresent()) {
            presente = "SIM";
        } else {
            presente = "NÃO";
        }
        String text = "Documento " + Category.DG.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentDG 

