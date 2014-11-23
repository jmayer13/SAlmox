
/*-
 * Classname:             DocumentA.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 15:49:06
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de almoxarifado, herda de
 * Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentA extends Document {

    /**
     * Construtor de DocumentA sem argumentos
     */
    public DocumentA() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentA com código de documento de almoxarifado e com
     * código de documento
     *
     * @param codeDocumentA código de documento de almoxarifado
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentA(int codeDocumentA, int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentA);
    }//fim do construtor com código de documento de almoxarifado e com código de documento

    /**
     * Construtor de DocumentA sem código de documento de almoxarifado e com
     * código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentA(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de almoxarifado e com código de documento

    /**
     * Construtor de DocumentA sem código de documento de almoxarifado e sem
     * código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentA(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de almoxarifado e sem código de documento

    //sets
    /**
     * Define código de documento de almoxarifado
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentA código de documentos de almoxarifado
     */
    public void setCodeDocumentA(int codeDocumentA) {

        this.setCodeDocumentSpecific(codeDocumentA);
    }//fim do método setCodeDocumetA

    //gets
    /**
     * Obtêm código de documento de almoxarifado
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de almoxarifado
     */
    public int getCodeDocumentA() {
        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentA

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
        String text = "Documento " + Category.A.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentA

