
/*-
 * Classname:             BoxDP.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 15:35:24
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa do departamento pessoal, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxDP extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "700.02.";

    /**
     * Construtor sem argumentos
     */
    public BoxDP() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim construtor sem argumentos

    /**
     * Construtor com código da caixa de departamento pessoal e com código da
     * caixa
     *
     * @param codeBoxDP código da caixa de departamento pessoal
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxDP(int codeBoxDP, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxDP);
    }//fim do construtor com código da caixa de departamento pessoal e com código da caixa

    /**
     * Construtor com código da caixa de departamento pessoal e sem código da
     * caixa
     *
     * @param codeBoxDP código da caixa de departamento pessoal
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxDP(int codeBoxDP, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxDP);
    }//fim do construtor com código da caixa do departamento pessoal e sem código da caixa

    /**
     * Construtor sem código da caixa de departamento pessoal e sem código da
     * caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxDP(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem código da caixa do departamento pessoal e sem código da caixa

    //sets
    /**
     * Define código da caixa de departamento pessoal
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxDP código caixa departamento pessoal
     */
    public void setCodeBoxDP(int codeBoxDP) {

        this.setCodeBoxSpecific(codeBoxDP);
    }//fim do metodo setCodeBoxDP

    //gets
    /**
     * Obtêm o código da caixa de departamento pessoal
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>integer</code> código caixa departamento pessoal
     */
    public int getCodeBoxDP() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxDP

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.DP.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxDP

