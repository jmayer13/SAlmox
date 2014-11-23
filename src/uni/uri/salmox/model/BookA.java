
/*-
 * Classname:             BookA.java
 *
 * Version information:   1.0
 *
 * Date:                  11/12/2012 - 13:32:04
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de almoxarifado, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookA extends Book {

    //ano do livro
    private int year;
    //tipo de livro
    private String typeBook;

    /**
     * Construtor de BookA sem argumentos
     */
    public BookA() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookA com código do livro de almoxarifado e com código de
     * livro
     *
     * @param codeBookA código do livro de almoxarifado
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param typeBook tipo de livro
     */
    public BookA(int codeBookA, int codeBook, String titleBook, int codeBox,
            String observationBook, int year, String typeBook) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookA);
        this.year = year;
        this.typeBook = typeBook;
    }//fim do construtor com código do livro de almoxarifado e com código de livro

    /**
     * Construtor de BookA sem código do livro de almoxarifado e com código de
     * livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param typeBook tipo de livro
     */
    public BookA(int codeBook, String titleBook, int codeBox, String observationBook,
            int year, String typeBook) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.year = year;
        this.typeBook = typeBook;
    }//fim do construtor sem código do livro de almoxarifado e com código de livro

    /**
     * Construtor de BookA sem código do livro de almoxarifado e sem código de
     * livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param year ano
     * @param semester semestre
     */
    public BookA(String titleBook, int codeBox, String observationBook, int year,
            String typeBook) {
        super(titleBook, codeBox, observationBook);
        this.year = year;
        this.typeBook = typeBook;
    }//fim do construtor sem código do livro de almoxarifado e sem código de livro

    //sets
    /**
     * Define código do livro de almoxarifado
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookA código do livro de almoxarifado
     */
    public void setCodeBookA(int codeBookA) {
        this.setCodeBookSpecific(codeBookA);
    }//fim do método setCodeBookA

    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear;

    /**
     * Define tipo de livro
     *
     * @param typeBook tipo de livro
     */
    public void setTypeBook(String typeBook) {
        this.typeBook = typeBook;
    }//fim do método setTypeBook

    //gets
    /**
     * Obtêm código do livro de almoxarifado
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de almoxarifado
     */
    public int getCodeBookA() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBookA

    /**
     * Obtêm ano
     *
     * @return <code>Integer</code> ano
     */
    public int getYear() {
        return year;
    }//fim do método getYear

    /**
     * Obtêm tipo de livro
     *
     * @return <code>String</code> tipo de livro
     */
    public String getTypeBook() {
        return typeBook;
    }//fim do método getTypeBook

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Tipo:" + this.getTypeBook()
                + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookA

