
/*-
 * Classname:             DocumentFP.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 14:14:29
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de formandos pós-graduação,
 * herda de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentFP extends Document {

    //status do aluno
    private String statusStudent;

    /**
     * Construtor de DocumentFP sem argumentos
     */
    public DocumentFP() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentFP com código de documento de formandos
     * pós-graduação e com código de documento
     *
     * @param codeDocumentFP código de documento de formando pós-graduação
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param studentStatus estado do aluno
     */
    public DocumentFP(int codeDocumentFP, int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, String statusStudent) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentFP);
        this.statusStudent = statusStudent;
    }//fim do construtor com código de documento de formandos pós-graduação e com código de documento

    /**
     * Construtor de DocumentFP sem código de documento de formandos
     * pós-graduação e com código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param statusStudent estado do aluno
     */
    public DocumentFP(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, String statusStudent) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.statusStudent = statusStudent;
    }//fim do construtor sem código de documento de formandos pós-graduação e com código de documento

    /**
     * Construtor de DocumentFP sem código de documento de formandos
     * pós-graduação e sem código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @statusStudent status aluno
     */
    public DocumentFP(String titleDocument, int codeBook,
            String observationDocument, boolean present, String statusStudent) {
        super(titleDocument, codeBook, observationDocument, present);
        this.statusStudent = statusStudent;
    }//fim do construtor sem código de documento de formandos pós-graduação e sem código de documento

    //sets
    /**
     * Define código de documento de formandos pós-graduação
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentFP código de documentos de formandos pós-graduação
     */
    public void setCodeDocumentFP(int codeDocumentFP) {

        this.setCodeDocumentSpecific(codeDocumentFP);
    }//fim do método setCodeDocumetFP

    /**
     * Define status do aluno
     *
     * @param statusStudent
     */
    public void setStatusStudent(String statusStudent) {
        this.statusStudent = statusStudent;
    }//fim do método setStatusStudent

    //gets
    /**
     * Obtêm código de documento de formandos pós-graduação
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de formandos
     * pós-graduação
     */
    public int getCodeDocumentFP() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentFP

    public String getStatusStudent() {
        return statusStudent;
    }//fim do método getStatusStudent

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
        String text = "Documento " + Category.FP.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Estado Estudante:" + this.getStatusStudent() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentFP

