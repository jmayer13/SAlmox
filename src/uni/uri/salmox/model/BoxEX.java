
/*-
 * Classname:             BoxEX.java
 *
 * Version information:   1.1
 *
 * Date:                  05/12/2012 - 14:08:03
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa de EX-alunos/desistentes , herda de caixa
 *
 * @see uni.uri.salmox.model.Box
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxEX extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "0.";

    /**
     * Construtor sem argumentos
     */
    public BoxEX() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Construtor com código da caixa de ex-alunos e com código da caixa
     *
     * @param codeBoxEX código da caixa de formandos
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação da caixa
     */
    public BoxEX(int codeBoxEX, int codeBox, String titleBox, String responsible,
            String observationBox) {
        super(codeBox, titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxEX);
    }//fim do construtor com código da caixa de ex-alunos e com código da caixa

    /**
     * Construtor com código da caixa de ex-alunos e sem código da caixa
     *
     * @param codeBoxEX código da caixa de ex-alunos
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxEX(int codeBoxEX, String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);
        this.setCodeBoxSpecific(codeBoxEX);
    }//fim do construtor com código da caixa de ex-alunos e sem código da caixa

    /**
     * Construtor sem código da caixa de ex-alunos e sem código da caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsável
     * @param observationBox observação sobre a caixa
     */
    public BoxEX(String titleBox, String responsible, String observationBox) {
        super(titleBox, responsible, observationBox);
        this.setCodeClass(this.CODE_CLASS_BOX);

    }//fim do construtor sem código da caixa de ex-alunos e sem código da caixa

    //sets
    /**
     * Define código da caixa de ex-alunos
     *
     * @deprecated use setCodeBoxSpecific
     * @param codeBoxEX código caixa ex-alunos
     */
    public void setCodeBoxEX(int codeBoxEX) {

        this.setCodeBoxSpecific(codeBoxEX);
    }//fim do método setCodeBoxEX

    //gets
    /**
     * Obtêm o código da caixa de ex-alunos
     *
     * @deprecated use getCodeBoxSpecific
     * @return <code>Integer</code> código caixa ex-alunos
     */
    public int getCodeBoxEX() {

        return this.getCodeBoxSpecific();
    }//fim do método getCodeBoxEX

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.EX.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxEX

