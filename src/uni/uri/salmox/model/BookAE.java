
/*-
 * Classname:             BookAE.java
 *
 * Version information:   1.0
 *
 * Date:                  10/12/2012 - 14:21:06
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de Atas de Exames, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookAE extends Book {

    //ano do livro
    private int year;
    //código curso no ano
    private int codeCourseYear;

    /**
     * Construtor de BookAE sem argumentos
     */
    public BookAE() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookAE com código do livro de atas de exames e com código
     * de livro
     *
     * @param codeBookAE código do livro de atas de exames
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param codeCourseYear código do curso no ano
     */
    public BookAE(int codeBookAE, int codeBook, String titleBook, int codeBox,
            String observationBook, int year, int codeCourseYear) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookAE);
        this.year = year;
        this.codeCourseYear = codeCourseYear;
    }//fim do construtor com código do livro de atas de exames e com código de livro

    /**
     * Construtor de BookAE sem código do livro de atas de exames e com código
     * de livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param codeCourseYear código do curso no ano
     */
    public BookAE(int codeBook, String titleBook, int codeBox, String observationBook,
            int year, int codeCourseYear) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
        this.codeCourseYear = codeCourseYear;
    }//fim do construtor sem código do livro de atas de exames e com código de livro

    /**
     * Construtor de BookF sem código do livro de atas de exames e sem código de
     * livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param codeCourseYear codeCourseYear
     */
    public BookAE(String titleBook, int codeBox, String observationBook, int year,
            int codeCourseYear) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
        this.codeCourseYear = codeCourseYear;
    }//fim do método construtor sem código do livro de atas de exames e sem código de livro

    //sets
    /**
     * Define código do livro de atas de exames
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookAE código do livro de atas de exames
     */
    public void setCodeBookAE(int codeBookAE) {

        this.setCodeBookSpecific(codeBookAE);
    }//fim do método setCodeBookAE

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear;

    /**
     * Define código do curso no ano
     *
     * @param CodeCourseYear código do curso no ano
     */
    public void setCodeCourseYear(int codeCourseYear) {
        this.codeCourseYear = codeCourseYear;
    }//fim do método setCodeCourseYear

    //gets
    /**
     * Obtêm código do livro de atas de exames
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de atas de exames
     */
    public int getCodeBookAE() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBookAE

    /**
     * Obtêm ano
     *
     * @return <code>Integer</code> ano
     */
    public int getYear() {
        return year;
    }//fim do método getYear

    /**
     * Obtêm código do curso no ano
     *
     * @return <code>Integer</code> código do curso no ano
     */
    public int getCodeCourseYear() {
        return codeCourseYear;
    }//fim do método getCodeCourseYear

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Código Curso:" + this.getCodeCourseYear() + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookAE

