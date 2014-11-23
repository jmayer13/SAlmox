
/*-
 * Classname:             DocumentF.java
 *
 * Version information:   1.1
 *
 * Date:                  11/12/2012 - 13:45:47
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
public class DocumentF extends Document {

    /**
     * Construtor de DocumentF sem argumentos
     */
    public DocumentF() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentF com código de documento de formandos e com código
     * de documento
     *
     * @param codeDocumentF código de documento de formando
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentF(int codeDocumentF, int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentF);
    }//fim do construtor com código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de formandos e com código
     * de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentF(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de formandos e sem código
     * de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentF(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e sem código de documento

    //sets
    /**
     * Define código de documento de formandos
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentF código de documentos de formandos
     */
    public void setCodeDocumentF(int codeDocumentF) {
        this.setCodeDocumentSpecific(codeDocumentF);
    }//fim do método setCodeDocumet

    //gets
    /**
     * Obtêm código de documento de formandos
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de formandos
     */
    public int getCodeDocumentF() {
        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocument

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
        String text = "Documento " + Category.F.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentF

