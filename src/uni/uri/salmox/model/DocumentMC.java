
/*-
 * Classname:             DocumentMC.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 14:38:37
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de movimento de caixa, herda de
 * Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentMC extends Document {

    //data
    private java.sql.Date date;

    /**
     * Construtor de DocumentMC sem argumentos
     */
    public DocumentMC() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentMC com código de documento de movimento de caixa e
     * com código de documento
     *
     * @param codeDocumentMC código de documento de movimento de caixa
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param date data
     */
    public DocumentMC(int codeDocumentMC, int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, java.sql.Date date) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentMC);
    }//fim do construtor com código de documento de movimento de caixa e com código de documento

    /**
     * Construtor de DocumentMC sem código de documento de movimento de caixa e
     * com código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param date data
     */
    public DocumentMC(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, java.sql.Date date) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de movimento de caixa e com código de documento

    /**
     * Construtor de DocumentMC sem código de documento de movimento de caixa e
     * sem código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param date data
     */
    public DocumentMC(String titleDocument, int codeBook,
            String observationDocument, boolean present, java.sql.Date date) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de movimento de caixa e sem código de documento

    //sets
    /**
     * Define código de documento de movimento de caixa
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentMC código de documentos de movimento de caixa
     */
    public void setCodeDocumentMC(int codeDocumentMC) {

        this.setCodeDocumentSpecific(codeDocumentMC);
    }//fim do método setCodeDocumetMC

    /**
     * Define data do documento
     *
     * @param date data
     */
    public void setDate(java.sql.Date date) {
        this.date = date;
    }//fim do método setDate

    //gets
    /**
     * Obtêm código de documento de movimento de caixa
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de movimento de caixa
     */
    public int getCodeDocumentMC() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentMC

    /**
     * Obtêm data do documento
     *
     * @return <code>String</code> data do documento
     */
    public java.sql.Date getDate() {
        return date;
    }//fim do método getDate

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
        String text = "Documento " + Category.MC.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Data:" + this.getDate() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentMC

