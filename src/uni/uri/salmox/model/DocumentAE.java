
/*-
 * Classname:             DocumentAE.java
 *
 * Version information:   1.1
 *
 * Date:                  11/12/2012 - 15:50:19
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de atas de exames, herda de
 * Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentAE extends Document {

    //anexos do documento A-ata e E-exame
    private String annex;
    //dara do exame
    private java.sql.Date dateExam;

    /**
     * Construtor de DocumentAE sem argumentos
     */
    public DocumentAE() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentAE com código de documento de atas de exames e com
     * código de documento
     *
     * @param codeDocumentAE código de documento de atas de exames
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param annex anexo
     * @param dateExam data do exame
     */
    public DocumentAE(int codeDocumentAE, int codeDocument, String titleDocument,
            int codeBook, String observationDocument, boolean present, String annex,
            java.sql.Date dateExam) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentAE);
        this.annex = annex;
        this.dateExam = dateExam;
    }//fim do construtor com código de documento de atas de exames e com código de documento

    /**
     * Construtor de DocumentAE sem código de documento de atas de exames e com
     * código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param annex anexo
     * @param dateExam data do exame
     */
    public DocumentAE(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, String annex, java.util.Date dateExam) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentAE sem código de documento de atas de exames e sem
     * código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param annex anexo
     * @param dateExam data do exame
     */
    public DocumentAE(String titleDocument, int codeBook,
            String observationDocument, boolean present, String annex, java.util.Date dateExam) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de formandos e sem código de documento

    //sets
    /**
     * Define código de documento de atas de exames
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentAE código de documentos de atas de exames
     */
    public void setCodeDocumentAE(int codeDocumentAE) {

        this.setCodeDocumentSpecific(codeDocumentAE);
    }//fim do método setCodeDocumetAE

    /**
     * Define anexos do documento
     *
     * @param annex anexos do documento
     */
    public void setAnnex(String annex) {
        this.annex = annex;
    }//fim do método setAnnex

    /**
     * Define data do exame
     *
     * @param dateExam data do exame
     */
    public void setDateExam(java.sql.Date dateExam) {
        this.dateExam = dateExam;
    }//fim do método setDateExam

    //gets
    /**
     * Obtêm código de documento de atas de exame
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de atas de exame
     */
    public int getCodeDocumentAE() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentAE

    /**
     * Obtêm anexos do documento
     *
     * @return <code>String</code> anexos do documento
     */
    public String getAnnex() {
        return annex;
    }//fim do método getAnnex

    /**
     * Obtêm data do exame
     *
     * @return
     */
    public java.sql.Date getDateExam() {
        return dateExam;
    }//fim do método getdateExam

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
        String text = "Documento " + Category.AE.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Anexo:" + this.getAnnex() + " Data Exame:" + this.getDateExam() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentAE

