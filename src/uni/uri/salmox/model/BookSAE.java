
/*-
 * Classname:             BookSAE.java
 *
 * Version information:   1.0
 *
 * Date:                  11/12/2012 - 13:18:50
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de filantropia, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookSAE extends Book {

    /**
     * Construtor de BookSAE sem argumentos
     */
    public BookSAE() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookSAE com código do livro de filantropia e com código de
     * livro
     *
     * @param codeBookSAE código do livro de filantropia
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookSAE(int codeBookSAE, int codeBook, String titleBook, int codeBox,
            String observationBook) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookSAE);
    }//fim do construtor com código do livro de filantropia e com código de livro

    /**
     * Construtor de BookSAE sem código do livro de filantropia e com código de
     * livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     *
     */
    public BookSAE(int codeBook, String titleBook, int codeBox, String observationBook) {
        super(codeBook, titleBook, codeBox, observationBook);

    }//fim do construtor sem código do livro de filantropia e com código de livro

    /**
     * Construtor de BookSAE sem código do livro de filantropia e sem código de
     * livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     *
     */
    public BookSAE(String titleBook, int codeBox, String observationBook) {
        super(titleBook, codeBox, observationBook);

    }//fim do método construtor sem código do livro de filantropia e sem código de livro

    //sets
    /**
     * Define código do livro de filantropia
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookSAE código do livro de filantropia
     */
    public void setCodeBookSAE(int codeBookSAE) {

        this.setCodeBookSpecific(codeBookSAE);
    }//fim do método setCodeBookSAE

    //gets
    /**
     * Obtêm código do livro de filantropia
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de filantropia
     */
    public int getCodeBookSAE() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBook
}//fim da classe BookSAE

