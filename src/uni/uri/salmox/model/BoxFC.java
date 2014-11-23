
/*-
 * Classname:             BoxFC.java
 *
 * Version information:   1.1
 *
 * Date:                  19/10/2012 - 14:03:08
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de Formandos, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxFC extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "0.";

    /**
     * Construtor sem argumentos
     */
    public BoxFC() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de formandos e com código da caixa
     *
     * @param codeBoxFC código da caixa de formandos
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxFC(int codeBoxFC, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxFC);
    }//fim do construtor com código da caixa de formandoe com código da caixa

    /**
     * Construtor com código da caixa de formandos e sem código da caixa
     *
     * @param codeBoxFC código da caixa de formandos
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxFC(int codeBoxFC, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxFC);
    }//fim do construtor com código da caixa de formandoe sem código da caixa

    /**
     * Construtor sem código da caixa de formandos e sem código da caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxFC(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem código da caixa de formandoe sem código da caixa

    //sets
    /**
     * Define código da caixa de formandos
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxFC código caixa formandos
     */
    public void setCodeBoxFC(int codeBoxFC) {

        this.setCodeBoxSpecific(codeBoxFC);
    }//fim do método construtor setCodeBoxFC

    //gets
    /**
     * Obtêm o código da caixa de formandos
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>integer</code> código caixa formandos
     */
    public int getCodeBoxFC() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxFC

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.FC.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxFC

