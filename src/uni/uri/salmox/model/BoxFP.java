
/*-
 * Classname:             BoxFP.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 14:47:16
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de Formandos pós-graduação, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxFP extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "0.";

    /**
     * Construtor sem argumentos
     */
    public BoxFP() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de formandos pós-graduação e com código da
     * caixa
     *
     * @param codeBoxFP código da caixa de formandos pós-graduação
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxFP(int codeBoxFP, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxFP);
    }//fim do construtor com código da caixa de formandos pós-graduação e com código da caixa

    /**
     * Construtor com código da caixa de formandos pós-graduação e sem código da
     * caixa
     *
     * @param codeBoxFP código da caixa de formandos pós-graduação
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxFP(int codeBoxFP, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxFP);
    }//fim do construtor com código da caixa de formandos pós-graduação e sem código da caixa

    /**
     * Construtor sem código da caixa de formandos pós-graduação e sem código da
     * caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxFP(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem código da caixa de formandos pós-graduação e sem código da caixa

    //sets
    /**
     * Define código da caixa de formandos pós-graduação
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxF código caixa formandos pós-graduação
     */
    public void setCodeBoxFP(int codeBoxFP) {

        this.setCodeBoxSpecific(codeBoxFP);
    }//fim do método setCodeBoxFP

    //gets
    /**
     * Obtêm o código da caixa de formandos pós-graduação
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>integer</code> código caixa formandos pós-graduação
     */
    public int getCodeBoxFP() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxFP

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.FP.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxFP

