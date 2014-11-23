
/*-
 * Classname:             BoxCC.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 14:29:25
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de Cadernos de Chamada, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxCC extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "0.";

    /**
     * Construtor sem argumentos
     */
    public BoxCC() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor

    /**
     * Construtor com código da caixa de Cadernos de Chamada e com código da
     * caixa
     *
     * @param codeBoxCC código da caixa de Cadernos de Chamada
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxCC(int codeBoxCC, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxCC);
    }//fim do construtor com código da caixa de caderno de chamade e com código da caixa

    /**
     * Construtor com código da caixa de Cadernos de Chamada e sem código da
     * caixa
     *
     * @param codeBoxCC código da caixa de Cadernos de Chamada
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxCC(int codeBoxCC, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxCC);
    }//fim do construtor com código da caixa de cadernos de chamada e sem código da caixa

    /**
     * Construtor sem código da caixa de Cadernos de Chamada e sem código da
     * caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxCC(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
    }//fim do construtor sem código da ciaxa de cadernos de chamada e sem código da ciaxa

    //sets
    /**
     * Define código da caixa de Cadernos de Chamada
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxF código caixa Cadernos de Chamada
     */
    public void setCodeBoxCC(int codeBoxCC) {

        this.setCodeBoxSpecific(codeBoxCC);
    }//fim do método setCodeBoxCC

    //gets
    /**
     * Obtêm o código da caixa de Cadernos de Chamada
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>Integer</code> código caixa Cadernos de Chamada
     */
    public int getCodeBoxCC() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxCC

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.CC.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxCC

