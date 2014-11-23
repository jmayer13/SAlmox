
/*-
 * Classname:             Box.java
 *
 * Version information:   1.1
 *
 * Date:                  (08/10/2012 - 13:23:37) 28/07/2014
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import uni.uri.salmox.util.Category;

/**
 * Superclasse Modelo das Caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Box implements Comparable {

    //classe da caixa (segundo a portaria 1224 as caixas devem vir com um pré código especifico)
    private final String CODE_CLASS_URI_SANTIAGO = "8.";
    private String codeClass;
    //código da caixa
    private int codeBox;
    //código especifico da categoria
    private int codeBoxSpecific;
    //título da caixa
    private String titleBox;
    //responsáveis pela caixa
    private String responsible;
    //observação sobre a caixa
    private String observationBox;
    //arraylist com os livros que a caixa contem
    private List<Book> listBook;

    /**
     * Construtor de Box sem argumentos
     */
    public Box() {
        listBook = new ArrayList();
    }//fim do construtor sem argumentos

    /**
     * Construtor de Box com código da caixa
     *
     * @param codeBox código da caixa
     * @param titleBox título da caixa
     * @param responsible responsáveis pela caixa
     * @param observationBox observação sobre a caixa
     */
    public Box(int codeBox, String titleBox, String responsible, String observationBox) {
        this.codeBox = codeBox;
        this.titleBox = titleBox;
        this.responsible = responsible;
        this.observationBox = observationBox;
        listBook = new ArrayList();
    }//fim do construtor com código da caixa

    /**
     * Construtor de Box sem código caixa
     *
     * @param titleBox título da caixa
     * @param responsible responsáveis pela caixa
     * @param observationBox observação sobre a caixa
     */
    public Box(String titleBox, String responsible, String observationBox) {
        this.titleBox = titleBox;
        this.responsible = responsible;
        this.observationBox = observationBox;
        listBook = new ArrayList();
    }//fim do construtor sem código caixa

    //sets
    /**
     * Define o pré codigo da classe
     *
     * @param codeClass
     */
    public void setCodeClass(String codeClass) {
        this.codeClass = this.CODE_CLASS_URI_SANTIAGO + codeClass;
    }//fim do método setCodeClass

    /**
     * Define código da caixa
     *
     * @param codeBox código da caixa
     */
    public void setCodeBox(int codeBox) {
        this.codeBox = codeBox;
    }//fim do método setCodeBox

    /**
     * Define o código especifico da caixa
     *
     * @param codeBoxSpecific
     */
    public void setCodeBoxSpecific(int codeBoxSpecific) {
        this.codeBoxSpecific = codeBoxSpecific;
    }//fim do método setCodeBoxSpecific

    /**
     * Define título da caixa
     *
     * @param titleBox titulo da caixa
     */
    public void setTitleBox(String titleBox) {
        this.titleBox = titleBox;
    }//fim do método setTitleBox

    /**
     * Define responsáveis pela caixa
     *
     * @param responsible responsáveis pela caixa
     */
    public void setResponsibleBox(String responsible) {
        this.responsible = responsible;
    }//fim do método setResponsibleBox

    /**
     * Define observação sobre a caixa
     *
     * @param observationBox observação sobre a caixa
     */
    public void setObservationBox(String observationBox) {
        this.observationBox = observationBox;
    }//fim do método setObservationBox

    /**
     * Define lista de livros que a caixa contem
     *
     * @param listBook lista de livros
     */
    public void setListBook(List listBook) {
        this.listBook = listBook;
    }//fim do método setListBook

    /**
     * Define livro que esteja contido na caixa
     *
     * @param book livro
     */
    public void setBook(Book book) {
        listBook.add(book);
    }//fim do método setBook

    //gets
    /**
     * Obtêm o pré codigo da classe
     *
     * @return <code>String</code> codeClass
     */
    public String getCodeClass() {
        return codeClass;
    }//fim do método getCodeClass

    /**
     * Obtêm código da caixa
     *
     * @return <code>Integer</code> código da caixa
     */
    public int getCodeBox() {
        return codeBox;
    }//fim do método getCodeBox

    /**
     * Obtêm o código especifico da caixa
     *
     * @return <code>Integer</code> código especifico
     */
    public int getCodeBoxSpecific() {
        return codeBoxSpecific;
    }//fim do método getCodeBoxSpecific

    /**
     * Obtêm título da caixa
     *
     * @return <code>String</code> título da caixa
     */
    public String getTitleBox() {
        return titleBox;
    }//fim do método getTitleBox

    /**
     * Obtêm responsáveis pela caixa
     *
     * @return <code>String</code> responsáveis pela caixa
     */
    public String getResponsibleBox() {
        return responsible;
    }//fim do método getResponsibleBox

    /**
     * Obtêm observação sobre a caixa
     *
     * @return <code>String</code> observação sobre a caixa
     */
    public String getObservationBox() {
        return observationBox;
    }//fim do método getObservationBox

    /**
     * Obtêm lista com livros que a caixa contem
     *
     * @return <code>List</code> lista de livros
     */
    public List getListBook() {
        return listBook;
    }//fim do método getListBook

    /**
     * Método para comparação de caixas por código
     *
     * @param box caixa
     * @return <code>Integer</code> parâmetro de comparação seguindo a convenção
     * da interface Comparable
     */
    public int compareTo(Object box) {
        Box otherBox = (Box) box;
        if (this.getCodeBox() < otherBox.getCodeBox()) {
            return -1;
        } else if (this.getCodeBox() == otherBox.getCodeBox()) {
            return 0;
        } else {
            return 1;
        }
    }//fim do método compareTo
    /**
     * Chamada para a classe com o método de comparação por título
     */
    public final static Comparator<Box> forTitle = new Box.ForTitle();

    /**
     * Classe interna que contém o método de comparação por título
     */
    private static class ForTitle implements Comparator<Box> {

        /**
         * Método de compara o título de duas caixas
         *
         * @param box1 caixa
         * @param box2 caixa
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Box box1, Box box2) {
            return box1.getTitleBox().compareToIgnoreCase(box2.getTitleBox());
        }//fim do método compareTo
    }//fim da classe interna ForTitle
    /**
     * Chamada para a classe com o método de comparação por código da categoria
     */
    public final static Comparator<Box> forCategoryCode = new Box.ForCategoryCode();

    /**
     * Classe interna que contém o método de comparação por código da categoria
     */
    private static class ForCategoryCode implements Comparator<Box> {

        /**
         * Método de compara o código da categoria de duas caixas
         *
         * @param box1 caixa
         * @param box2 caixa
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Box box1, Box box2) {
            Category category = Category.getCategory(box1);
            if (category == Category.getCategory(box2)) {
                if (box1.getCodeBoxSpecific() < box2.getCodeBoxSpecific()) {
                    return -1;
                } else {
                    return 1;
                }


            } else {
                if (box1.getCodeBox() < box2.getCodeBox()) {
                    return -1;
                } else if (box1.getCodeBox() == box2.getCodeBox()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }//fim do método compareTo
    }//fim da classe interna ForTitle

    /**
     * Converte texto para string
     *
     * @return <code>String</code> livro
     */
    @Override
    public String toString() {
        String text = "Caixa " + this.getCodeClass() + this.getCodeBoxSpecific()
                + ": " + this.getTitleBox() + " Responsáveis:" + this.getResponsibleBox() + " Observação:" + this.getObservationBox();
        return text;
    }//fim do método toString
}//fim da classe Box

