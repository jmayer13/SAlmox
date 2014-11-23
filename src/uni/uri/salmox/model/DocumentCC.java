
/*-
 * Classname:             DocumentCC.java
 *
 * Version information:   1.1
 *
 * Date:                  13/12/2012 - 13:52:03
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo de documento de livro de cadernos de chamada, herda de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentCC extends Document {

    //ano
    private int year;
    //semestre
    private int semester;

    /**
     * Construtor de DocumentCC sem argumentos
     */
    public DocumentCC() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentCC com código de documento de caixa de chamada e
     * com código de documento
     *
     * @param codeDocumentCC código de documento de caixa de chamada
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param year ano
     * @param semester semestre
     */
    public DocumentCC(int codeDocumentCC, int codeDocument, String titleDocument,
            int codeBook, String observationDocument, boolean present, int year,
            int semester) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentCC);
    }//fim do construtor com código de documento de cadernos de chamada e com código de documento

    /**
     * Construtor de DocumentCC sem código de documento de cadernos de chamada e
     * com código de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     * @param year ano
     * @param semester semestre
     */
    public DocumentCC(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present, int year, int semester) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de cadernos de chamada e com código de documento

    /**
     * Construtor de DocumentCC sem código de documento de cadernos de chamada e
     * sem código de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentCC(String titleDocument, int codeBook,
            String observationDocument, boolean present, int year, int semester) {
        super(titleDocument, codeBook, observationDocument, present);
    }//fim do construtor sem código de documento de cadernos de chamada e sem código de documento

    //sets
    /**
     * Define código de documento de cadernos de chamada
     *
     * @deprecated use setCodeDocumentSpecific
     * @param codeDocumentCC código de documentos de cadernos de chamada
     */
    public void setCodeDocumentCC(int codeDocumentCC) {

        this.setCodeDocumentSpecific(codeDocumentCC);
    }//fim do método setCodeDocumetCC

    /**
     * Define ano
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear

    /**
     * Define semestre
     *
     * @param semester
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }//fim do semester

    //gets
    /**
     * Obtêm código de documento de caderno de chamada
     *
     * @deprecated use getCodeDocumentSpecific
     * @return <code>Integer</code> código de documento de caderno de chamada
     */
    public int getCodeDocumentCC() {

        return this.getCodeDocumentSpecific();
    }//fim do método getCodeDocumentCC

    /**
     * Obtêm ano
     *
     * @return <code>Integer</code> ano
     */
    public int getYear() {
        return year;
    }//fim do método getYear

    /**
     * Obtêm semestre
     *
     * @return <code>Integer</code> semestre
     */
    public int getSemester() {
        return semester;
    }//fim do método getSemester

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
        String text = "Documento " + Category.CC.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Ano:" + this.getYear() + " Semestre:" + this.getSemester() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentCC

