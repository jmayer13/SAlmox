
/*-
 * Classname:             BookMC.java
 *
 * Version information:   1.0
 *
 * Date:                  10/12/2012 - 16:08:26
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de Movimentos de Caixa, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookMC extends Book {

    //ano do livro
    private int year;

    /**
     * Construtor de BookMC sem argumentos
     */
    public BookMC() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookMC com código do livro de movimentos de caixa e com
     * código de livro
     *
     * @param codeBookMC código do livro de movimentos de caixa
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookMC(int codeBookMC, int codeBook, String titleBook, int codeBox,
            String observationBook, int year) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookMC);
        this.year = year;

    }//fim do construtor com código do livro de movimentos de caixa e com código de livro

    /**
     * Construtor de BookMC sem código do livro de movimentos de caixa e com
     * código de livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookMC(int codeBook, String titleBook, int codeBox, String observationBook,
            int year) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
    }//fim do construtor sem código do livro de movimentos de caixa e com código de livro

    /**
     * Construtor de BookMC sem código do livro de movimentos de caixa e sem
     * código de livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     */
    public BookMC(String titleBook, int codeBox, String observationBook, int year) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
    }//fim do método construtor sem código do livro de movimentos de caixa e sem código de livro

    //sets
    /**
     * Define código do livro de movimentos de caixa
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookMC código do livro de movimentos de caixa
     */
    public void setCodeBookMC(int codeBookMC) {

        this.setCodeBookSpecific(codeBookMC);
    }//fim do método setCodeBookMC

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
     * Obtêm código do livro de movimentos de caixa
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de movimentos de caixa
     */
    public int getCodeBookMC() {

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
}//fim da classe BookMC

