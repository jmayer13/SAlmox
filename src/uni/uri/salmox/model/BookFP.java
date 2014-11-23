
/*-
 * Classname:             BookFP.java
 *
 * Version information:   1.0
 *
 * Date:                  10/12/2012 - 15:47:01
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de Formandos pós-graduação, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookFP extends Book {

    //ano do livro
    private String year;
    //tipo de curso
    private String typeCourse;

    /**
     * Construtor de BookFP sem argumentos
     */
    public BookFP() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookFP com código do livro de formandos pós-graduação e com
     * código de livro
     *
     * @param codeBookFP código do livro de formandos pós-graduação
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param typeCourse tipo de curso
     */
    public BookFP(int codeBookFP, int codeBook, String titleBook, int codeBox,
            String observationBook, String year, String typeCourse) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookFP);
        this.year = year;
        this.typeCourse = typeCourse;
    }//fim do construtor com código do livro de formandos pós-graduação e com código de livro

    /**
     * Construtor de BookFP sem código do livro de formandos pós-graduação e com
     * código de livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param typeCourse tipo de curso
     */
    public BookFP(int codeBook, String titleBook, int codeBox, String observationBook,
            String year, String typeCourse) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
        this.typeCourse = typeCourse;
    }//fim do construtor sem código do livro de formandos pós-graduação e com código de livro

    /**
     * Construtor de BookFP sem código do livro de formandos pós-graduação e sem
     * código de livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param typeCourse tipo de curso
     */
    public BookFP(String titleBook, int codeBox, String observationBook, String year,
            String typeCourse) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
        this.typeCourse = typeCourse;
    }//fim do método construtor sem código do livro de formandos pós-graduação e sem código de livro

    //sets
    /**
     * Define código do livro de formandos pós-graduação
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookFP código do livro de formandos pós-graduação
     */
    public void setCodeBookFP(int codeBookFP) {

        this.setCodeBookSpecific(codeBookFP);
    }//fim do método setCodeBookFP

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(String year) {
        this.year = year;
    }//fim do método setYear;

    /**
     * Define tipo de curso
     *
     * @param typeCourse tipo de curso
     */
    public void setTypeCourse(String typeCourse) {
        this.typeCourse = typeCourse;
    }//fim do método setTypeCourse

    //gets
    /**
     * Obtêm código do livro de formandos pós-graduação
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de formandos pós-graduação
     */
    public int getCodeBookFP() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBookFP

    /**
     * Obtêm ano
     *
     * @return <code>String</code> ano
     */
    public String getYear() {
        return year;
    }//fim do método getYear

    /**
     * Obtêm tipo de curso
     *
     * @return <code>String</code> tipo de curso
     */
    public String getTypeCourse() {
        return typeCourse;
    }//fim do método getTypeCourse 

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Tipo:" + this.getTypeCourse() + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookFP

