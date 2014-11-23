
/*-
 * Classname:             BoxSAE.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 15:42:38
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de filantropia, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxSAE extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "520.";
    //ano
    private int year;

    /**
     * Construtor sem argumentos
     */
    public BoxSAE() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de filantropia, com código da caixa e com
     * ano
     *
     * @param codeBoxSAE código da caixa de filantropia
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     * @param year ano da caixa
     */
    public BoxSAE(int codeBoxSAE, int codeBox, String titleBox, String responsible,
            String observationBox, int year) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxSAE);
        this.year = year;
    }//fim do construtor com código de caixa de filantropia, com código da caixa e com ano

    /**
     * Construtor com código da caixa de filantropia, sem código da caixa e com
     * ano
     *
     * @param codeBoxSAE código da caixa de filantropia
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     * @param year ano da caixa
     */
    public BoxSAE(int codeBoxSAE, String titleBox, String responsible, String observationBox,
            int year) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxSAE);
        this.year = year;
    }//fim do construtor com código de caixa de filantropia, sem código da caixa e com ano

    /**
     * Construtor sem código da caixa de filantropia, sem código da caixa e com
     * ano da caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     * @param year ano da caixa
     */
    public BoxSAE(String titleBox, String responsible, String observationBox, int year) {
        super(titleBox, responsible, observationBox);
        this.year = year;
    }//fim do construtor sem código de caixa de filantropia, sem código da caixa e com ano

    //sets
    /**
     * Define código da caixa de filantropia
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxSAE código caixa filantropia
     */
    public void setCodeBoxSAE(int codeBoxSAE) {

        this.setCodeBoxSpecific(codeBoxSAE);
    }//fim do método setCodeBoxSAE

    /**
     * Define ano da caixa
     *
     * @param year ano da caixa
     */
    public void setYear(int year) {
        this.year = year;
    }//fim do método setYear

    //gets
    /**
     * Obtêm o código da caixa de filantropia
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>integer</code> código caixa filantropia
     */
    public int getCodeBoxSAE() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxSAE

    /**
     * Obtêm ano da caixa
     *
     * @return <code>integer</code> ano da caixa
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
        String text = "Caixa " + Category.SAE.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Ano:" + this.getYear() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxSAE

