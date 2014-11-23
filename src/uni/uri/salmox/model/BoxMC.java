
/*-
 * Classname:             BoxMC.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 15:02:00
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de movimento de caixa, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxMC extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "800.01";

    /**
     * Construtor sem argumentos
     */
    public BoxMC() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de movimento de caixa e com código da
     * caixa
     *
     * @param codeBoxMC código da caixa de movimento de caixa
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxMC(int codeBoxMC, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxMC);
    }//fim do construtor com código da caixa de movimento de caixa e com código de caixa

    /**
     * Construtor com código da caixa de movimento de caixa e sem código da
     * caixa
     *
     * @param codeBoxMC código da caixa de movimento de caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxMC(int codeBoxMC, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxMC);
    }//fim do construtor com código da caixa de movimento de caixa e sem código de caixa

    /**
     * Construtor sem código da caixa de movimento de caixa e sem código da
     * caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxMC(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem código da caixa de movimento de caixa e sem código de caixa

    //sets
    /**
     * Define código da caixa de movimento de caixa
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxMC código caixa movimento de caixa
     */
    public void setCodeBoxMC(int codeBoxMC) {

        this.setCodeBoxSpecific(codeBoxMC);
    }//fim do método setCodeBoxMC

    //gets
    /**
     * Obtêm o código da caixa de movimento de caixa
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>integer</code> código caixa formandos
     */
    public int getCodeBoxMC() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxMC

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.MC.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxMC

