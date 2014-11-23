
/*-
 * Classname:             DocumentDP.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 15:36:54
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de departamento pessoal, herda
 * de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentDP extends Document {

    /**
     * Construtor de DocumentDP sem argumentos
     */
    public DocumentDP() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentDP com código de documento de departamento pessoal
     * e com código de documento
     *
     * @param codeDocumentF código de documento de departamento pessoal
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentDP(int codeDocumentDP, int codeDocument, String titleDocument,
            int codeBook, String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentDP);
    }//fim do construtor com código de documento de departamento pessoal e com código de documento

    /**
     * Construtor de DocumentDP sem código de documento de formandos e com
     * código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentDP(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentDP sem código de documento de departamento pessoal
     * e sem código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentDP(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e sem código de documento

    //sets
    /**
     * Define código de documento de departamento pessoal
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentDP código de documentos de departamento pessoal
     */
    public void setCodeDocumentDP(int codeDocumentDP) {

        this.setCodeDocumentSpecific(codeDocumentDP);
    }//fim do método setCodeDocumetDP

    //gets
    /**
     * Obtêm código de documento de departamento pessoal
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de departamento pessoal
     */
    public int getCodeDocumentDP() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentDP

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
        String text = "Documento " + Category.DP.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentF

