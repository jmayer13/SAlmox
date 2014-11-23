
/*-
 * Classname:             BookFC.java
 *
 * Version information:   1.1
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
public class BookFC extends Book {

    //ano do livro
    private String years;

    /**
     * Construtor de BookF sem argumentos
     */
    public BookFC() {
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
    public BookFC(int codeBookF, int codeBook, String titleBook, int codeBox,
            String observationBook, String years, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.setCodeBookSpecific(codeBookF);
        this.years = years;
    }//fim do construtor com código do livro de formandos e com código de livro

    /**
     * Construtor de BookF sem código do livro de formandos e com código de
     * livro
     *
     * @param codeBook código de livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     * @param years ano
     * @param semester semestre
     */
    public BookFC(int codeBook, String titleBook, int codeBox, String observationBook,
            String years, int semester) {
        super(codeBook, titleBook, codeBox, observationBook);
        this.years = years;
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
    public BookFC(String titleBook, int codeBox, String observationBook, String years,
            int semester) {
        super(titleBook, codeBox, observationBook);
        this.years = years;
    }//fim do método construtor sem código do livro de formandos e sem código de livro

    //sets 
    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(String years) {
        this.years = years;
    }//fim do método setYear;

    //gets
    /**
     * Obtêm ano
     *
     * @return <code>String</code> ano
     */
    public String getYear() {
        return years;
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
}//fim da classe BookF

