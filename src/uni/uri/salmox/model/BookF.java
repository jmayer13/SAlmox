
/*-
 * Classname:             BookF.java
 *
 * Version information:   1.0)
 *
 * Date:                  10/12/2012 - 13:33:42
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de Formandos, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookF extends Book {

    //ano do livro
    private int year;
    //semestre do livro
    private int semester;

    /**
     * Construtor de BookF sem argumentos
     */
    public BookF() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookF com código do livro de formandos e com código de
     * livro
     *
     * @param codeBookF código do livro de formandos
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param semester semestre
     */
    public BookF(int codeBookF, int codeBook, String titleBook, int codeBox,
            String observationBook, int year, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookF);
        this.year = year;
        this.semester = semester;
    }//fim do construtor com código do livro de formandos e com código de livro

    /**
     * Construtor de BookF sem código do livro de formandos e com código de
     * livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param semester semestre
     */
    public BookF(int codeBook, String titleBook, int codeBox, String observationBook,
            int year, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
        this.semester = semester;
    }//fim do construtor sem código do livro de formandos e com código de livro

    /**
     * Construtor de BookF sem código do livro de formandos e sem código de
     * livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param semester semestre
     */
    public BookF(String titleBook, int codeBox, String observationBook, int year,
            int semester) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
        this.semester = semester;
    }//fim do método construtor sem código do livro de formandos e sem código de livro

    //sets
    /**
     * Define código do livro de formandos
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookF código do livro de formandos
     */
    public void setCodeBookF(int codeBookF) {

        this.setCodeBookSpecific(codeBookF);
    }//fim do método setCodeBookF

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear;

    /**
     * Define semestre
     *
     * @param semester semestre
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }//fim do método setSemester

    //gets
    /**
     * Obtêm código do livro de formandos
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de formandos
     */
    public int getCodeBookF() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBook

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
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Semestre:" + this.getSemester() + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookF

