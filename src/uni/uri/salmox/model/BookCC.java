
/*-
 * Classname:             BookCC.java
 *
 * Version information:   1.0
 *
 * Date:                  10/12/2012 - 14:36:54
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de cadernos de chamada, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookCC extends Book {

    /**
     * Construtor de BookCC sem argumentos
     */
    public BookCC() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookCC com código do livro de cadernos de chamada e com
     * código de livro
     *
     * @param codeBookCC código do livro de cadernos de chamada
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookCC(int codeBookCC, int codeBook, String titleBook, int codeBox,
            String observationBook) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookCC);
    }//fim do construtor com código do livro de cadernos de chamada e com código de livro

    /**
     * Construtor de BookEX sem código do livro de cadernos de chamada e com
     * código de livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookCC(int codeBook, String titleBook, int codeBox, String observationBook,
            int year, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
    }//fim do construtor sem código do livro de cadernos de chamada e com código de livro

    /**
     * Construtor de BookCC sem código do livro de cadernos de chamada e sem
     * código de livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookCC(String titleBook, int codeBox, String observationBook, int year,
            int semester) {
        super(titleBook, codeBox, observationBook);
    }//fim do construtor sem código do livro de cadernos de chamada e sem código de livro

    //sets
    /**
     * Define código do livro de cadernos de chamada
     *
     * @deprecated use setCodeBookSpecific
     * @param codeBookCC código do livro de cadernos de chamada
     */
    public void setCodeBookCC(int codeBookCC) {

        this.setCodeBookSpecific(codeBookCC);
    }//fim do método setCodeBookCC

    //gets
    /**
     * Obtêm código do livro de cadernos de chamada
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de cadernos de chamada
     */
    public int getCodeBookCC() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBookCC
}//fim da classe BookCC

