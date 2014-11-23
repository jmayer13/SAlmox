
/*-
 * Classname:             DocumentEX.java
 *
 * Version information:   1.1
 *
 * Date:                  11/12/2012 - 16:03:30
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de caixa de ex-alunos, herda de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentEX extends Document {

    //ano
    private int year;

    /**
     * Construtor de DocumentEX sem argumentos
     */
    public DocumentEX() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentEX com código de documento de ex-alunos e com
     * código de documento
     *
     * @param codeDocumentEX código de documento de ex-alunos
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param year ano;
     */
    public DocumentEX(int codeDocumentEX, int codeDocument, String titleDocument,
            int codeBook, String observationDocument, boolean present, int year) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentEX);
        this.year = year;
    }//fim do construtor com código de documento de ex-alunos e com código de documento

    /**
     * Construtor de DocumentEX sem código de documento de ex-alunos e com
     * código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param year ano
     */
    public DocumentEX(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, int year) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.year = year;
    }//fim do construtor sem código de documento de ex-alunos e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de ex-alunos e sem código
     * de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param year ano
     */
    public DocumentEX(String titleDocument, int codeBook, String observationDocument,
            boolean present, int year) {
        super(titleDocument, codeBook, observationDocument, present);
        this.year = year;
    }//fim do construtor sem código de documento de ex-alunos e sem código de documento

    //sets
    /**
     * Define código de documento de ex-alunos
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentEX código de documentos de ex-alunos
     */
    public void setCodeDocumentEX(int codeDocumentEX) {

        this.setCodeDocumentSpecific(codeDocumentEX);
    }//fim do método setCodeDocumetEX

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear

    //gets
    /**
     * Obtêm código de documento de atas de exames
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de atas de exames
     */
    public int getCodeDocumentEX() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentEX

    /**
     * Obtêm ano
     *
     * @return <code>Integer</code> ano
     */
    public int getYear() {
        return year;
    }//fim do método getYear

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
        String text = "Documento " + Category.EX.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Ano:" + this.getYear() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentEX

