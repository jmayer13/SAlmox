/*- 
 * Classname:             BoxDG.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  02/08/2014 - 08:28:35 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.Category;

/**
 * Classe modelo da Caixa do diretor geral, herda de caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxDG extends Box {

    //code class box
    private final String CODE_CLASS_BOX = "600.03.";

    /**
     * Construtor sem argumentos
     */
    public BoxDG() {
        super();
        this.setCodeClass(this.CODE_CLASS_BOX);
    }//fim do construtor sem argumentos

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + Category.DG.getAbbreviation() + " - " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe BoxDG 

