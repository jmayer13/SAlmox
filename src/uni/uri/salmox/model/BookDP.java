
/*-
 * Classname:             BookDP.java
 *
 * Version information:   1.0
 *
 * Date:                  11/12/2012 - 13:11:49
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de departamento pessoal, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookDP extends Book {

    //ano do livro
    private int year;

    /**
     * Construtor de BookDP sem argumentos
     */
    public BookDP() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookDP com código do livro de formandos e com código de
     * livro
     *
     * @param codeBookDP código do livro de departamento pessoal
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookDP(int codeBookDP, int codeBook, String titleBook, int codeBox,
            String observationBook, int year) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookDP);
        this.year = year;
    }//fim do construtor com código do livro de departamento pessoal e com código de livro

    /**
     * Construtor de BookDP sem código do livro de departamento pessoal e com
     * código de livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookDP(int codeBook, String titleBook, int codeBox, String observationBook,
            int year) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
    }//fim do construtor sem código do livro de departamento pessoal e com código de livro

    /**
     * Construtor de BookDP sem código do livro de departamento pessoal e sem
     * código de livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookDP(String titleBook, int codeBox, String observationBook, int year) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
    }//fim do método construtor sem código do livro de departamento pessoal e sem código de livro

    //sets
    /**
     * Define código do livro de departamento pessoal
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookDP código do livro de departamento pessoal
     */
    public void setCodeBookDP(int codeBookDP) {

        this.setCodeBookSpecific(codeBookDP);
    }//fim do método setCodeBookDP

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear;

    //gets
    /**
     * Obtêm código do livro de formandos
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de formandos
     */
    public int getCodeBookDP() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBookDP

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
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookDP

