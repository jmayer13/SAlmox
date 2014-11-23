
/*-
 * Classname:             BoxA.java
 *
 * Version information:   1.1
 *
 * Date:                  06/12/2012 - 13:18:17
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de Almoxarifado, herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxA extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "1000.";
    //tipo de caixa
    private String typeBox;

    /**
     * Construtor sem argumentos
     */
    public BoxA() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de almoxarifado e com código da caixa
     *
     * @param codeBoxA código da caixa de almoxarifado
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxA(int codeBoxA, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxA);
    }//fim do construtor com código de caixa de almoxarifado e com código caixa

    /**
     * Construtor com código da caixa de almoxarifado e sem código da caixa
     *
     * @param codeBoxA código da caixa de almoxarifado
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     * @param typeBox tipo de caixa
     */
    public BoxA(int codeBoxA, String titleBox, String responsible, String observationBox,
            String typeBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxA);
        this.typeBox = typeBox;
    }//fim do construtor com código da caixa de almoxarifado sem código de caixa

    /**
     * Construtor sem código da caixa de almoxarifado e sem código da caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     * @param typeBox tipo de caixa
     */
    public BoxA(String titleBox, String responsible, String observationBox, String typeBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.typeBox = typeBox;
    }//fim do construtor sem código de caixa de almoxarifado e sem código de caixa

    //sets
    /**
     * Define código da caixa de almoxarifado
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxF código caixa almoxarifado
     */
    public void setCodeBoxA(int codeBoxA) {
        this.setCodeBoxSpecific(codeBoxA);
    }//fim do método setCodeBoxA

    /**
     * Define código da caixa de almoxarifado
     *
     * @param typeBox tipo da caixa
     */
    public void setTypeBox(String typeBox) {
        this.typeBox = typeBox;
    }//fim do método setTypeBox

    //gets
    /**
     * Obtêm o código da caixa de almoxarifado
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>Integer</code> código caixa almoxarifado
     */
    public int getCodeBoxA() {
        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxA

    /**
     * Obtêm o tipo da caixa
     *
     * @return <code>String</code> tipo da caixa
     */
    public String getTypeBox() {
        return typeBox;
    }//fim do método getTypeBox

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.A.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Tipo:" + this.getTypeBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxA

