
/*-
 * Classname:             DocumentSAE.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 15:44:03
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de filantropia, herda de
 * Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentSAE extends Document {

    /**
     * Construtor de DocumentSAE sem argumentos
     */
    public DocumentSAE() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentSAE com código de documento de filantropia e com
     * código de documento
     *
     * @param codeDocumentSAE código de documento de filantropia
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentSAE(int codeDocumentSAE, int codeDocument, String titleDocument,
            int codeBook, String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentSAE);
    }//fim do construtor com código de documento de filantropia e com código de documento

    /**
     * Construtor de DocumentSAE sem código de documento de filantropia e com
     * código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentSAE(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de filantropia e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de filantropia e sem
     * código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentSAE(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de filantropia e sem código de documento

    //sets
    /**
     * Define código de documento de filantropia
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentF código de documentos de filantropia
     */
    public void setCodeDocumentSAE(int codeDocumentSAE) {

        this.setCodeDocumentSpecific(codeDocumentSAE);
    }//fim do método setCodeDocumetSAE

    //gets
    /**
     * Obtêm código de documento de filantropia
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de filantropia
     */
    public int getCodeDocumentSAE() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentSAE

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
        String text = "Documento " + Category.SAE.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentSAE

