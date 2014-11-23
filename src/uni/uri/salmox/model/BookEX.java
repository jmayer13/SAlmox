
/*-
 * Classname:             BookEX.java
 *
 * Version information:   1.0
 *
 * Date:                  10/12/2012 - 14:12:01
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de EX-alunos/desistentes, herda de livro
 *
 * @see uni.uri.salmox.model.Book
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookEX extends Book {

    /**
     * Construtor de BookEX sem argumentos
     */
    public BookEX() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de BookEX com código do livro de ex-alunos e com código de
     * livro
     *
     * @param codeBookEX código do livro de ex-alunos
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookEX(int codeBookEX, int codeBook, String titleBook, int codeBox,
            String observationBook) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookEX);
    }//fim do construtor com código do livro de ex-alunos e com código de livro

    /**
     * Construtor de BookEX sem código do livro de ex-alunos e com código de
     * livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookEX(int codeBook, String titleBook, int codeBox, String observationBook,
            int year, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
    }//fim do construtor sem código do livro de ex-alunos e com código de livro

    /**
     * Construtor de BookF sem código do livro de ex-alunos e sem código de
     * livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public BookEX(String titleBook, int codeBox, String observationBook, int year,
            int semester) {
        super(titleBook, codeBox, observationBook);
    }//fim do método construtor sem código do livro de ex-alunos e sem código de livro

    //sets
    /**
     * De
     *
     * @deprecated use setCodeBookSpecificfine código do livro de ex-alunos
     * @deprecated use setCodeBookSpecific
     * @param codeBookEX código do livro de ex-alunos
     */
    public void setCodeBookEX(int codeBookEX) {

        this.setCodeBookSpecific(codeBookEX);
    }//fim do método setCodeBookEX

    //gets
    /**
     * Obtêm código do livro de ex-alunos
     *
     * @deprecated use getCodeBookSpecific
     * @return <code>Integer</code> código do livro de ex-alunos
     */
    public int getCodeBookEX() {

        return this.getCodeBookSpecific();
    }//fim do método getCodeBook 
}//fim da classe BookEX

