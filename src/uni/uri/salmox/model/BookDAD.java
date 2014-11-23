/*- 
 * Classname:             BookDAD.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  02/08/2014 - 08:31:18 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de livro da caixa de diretor administrativa, herda de livro
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BookDAD extends Book {

    //ano do livro
    private String years;

    /**
     * Construtor de BookF sem argumentos
     */
    public BookDAD() {
        super();
    }//fim do construtor sem argumentos

    //sets
    /**
     * Define ano
     *
     * @param year ano
     */
    public void setYear(String years) {
        this.years = years;
    }//fim do método setYear;

    //gets
    /**
     * Obtêm ano
     *
     * @return <code>String</code> ano
     */
    public String getYear() {
        return years;
    }//fim do método getYear 

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Livro " + this.getCodeBookSpecific()
                + ": " + this.getTitleBook() + " Ano:" + this.getYear() + " Observação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe BookDAD

