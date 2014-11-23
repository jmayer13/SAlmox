
/*-
 * Classname:             BoxAE.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 14:20:03
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de Atas de Exames, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxAE extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "125.31";

    /**
     * Construtor sem argumentos
     */
    public BoxAE() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de Atas de Exames e com código da caixa
     *
     * @param codeBoxAE código da caixa de Atas de Exames
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxAE(int codeBoxAE, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxAE);
    }//fim do construtor com código da caixa de atas e exames e com código da caica

    /**
     * Construtor com código da caixa de Atas de Exames e sem código da caixa
     *
     * @param codeBoxAE código da caixa de Atas de Exames
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxAE(int codeBoxAE, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxAE);
    }//fim do construtor com código da caixa de atas e exames e sem código da caixa

    /**
     * Construtor sem código da caixa de Atas de Exames e sem código da caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxAE(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem código da caixa de atas e exames e sem código de caixa

    //sets
    /**
     * Define código da caixa de Atas de Exames
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxAE código caixa Atas de Exames
     */
    public void setCodeBoxAE(int codeBoxAE) {
        this.setCodeBoxSpecific(codeBoxAE);
    }//fim do método setCodeBoxAE

    //gets
    /**
     * Obtêm o código da caixa de Atas de Exames
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>Integer</code> código caixa Atas de Exames
     */
    public int getCodeBoxAE() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxAE

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.AE.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxAE

