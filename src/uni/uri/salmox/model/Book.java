
/*-
 * Classname:             Book.java
 *
 * Version information:   1.1
 *
 * Date:                  (08/10/2012 - 13:57:10) 28/07/2014
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
 * Superclasse modelo de livro
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Book implements Comparable {

    //código do livro
    private int codeBook;
    //código especifico da categoria
    private int codeBookSpecific;
    //título do livro
    private String titleBook;
    //código da caixa
    private int codeBox;
    //observação sobre o livro
    private String observationBook;
    //arraylist com os documentos que o livro contem
    private List<Document> listDocument;

    /**
     * Construtor de Book sem argumentos
     */
    public Book() {
        listDocument = new ArrayList();
    }//fim do construtor sem argumentos

    /**
     * Construtor de Book com código do livro
     *
     * @param codeBook código do livro
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public Book(int codeBook, String titleBook, int codeBox, String observationBook) {
        this.codeBook = codeBook;
        this.titleBook = titleBook;
        this.codeBox = codeBox;
        this.observationBook = observationBook;
        listDocument = new ArrayList();
    }//fim do construtor com código de livro

    /**
     * Construtor de Book sem código do livro
     *
     * @param titleBook título do livro
     * @param codeBox código da caixa
     * @param observationBook observação sobre o livro
     */
    public Book(String titleBook, int codeBox, String observationBook) {
        this.titleBook = titleBook;
        this.codeBox = codeBox;
        this.observationBook = observationBook;
        listDocument = new ArrayList();
    }//fim do construtor código de livro

    //sets
    /**
     * Define código do livro
     *
     * @param codeBook código do livro
     */
    public void setCodeBook(int codeBook) {
        this.codeBook = codeBook;
    }//fim do método setCodeBook

    /**
     * Define o código especifico do livro
     *
     * @param codeBookSpecific
     */
    public void setCodeBookSpecific(int codeBookSpecific) {
        this.codeBookSpecific = codeBookSpecific;
    }//fim do método setCodeBookSpecific

    /**
     * Define título do livro
     *
     * @param titleBook título do livro
     */
    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }//fim do método setTitleBook

    /**
     * Define código caixa
     *
     * @param codeBox código caixa
     */
    public void setCodeBox(int codeBox) {
        this.codeBox = codeBox;
    }//fim do método setCodeBox

    /**
     * Define observação caixa
     *
     * @param observationBook observação caixa
     */
    public void setObservationBook(String observationBook) {
        this.observationBook = observationBook;
    }//fim do método setObservationBook

    /**
     * Define lista de documentos
     *
     * @param listDocument lista de documentos
     */
    public void setListDocument(List listDocument) {
        this.listDocument = listDocument;
    }//fim do método setListDocument

    /**
     * Define um documento que esteja contido no livro
     *
     * @param document documento
     */
    public void setDocument(Document document) {
        listDocument.add(document);
    }//fim do método serDocument

    //gets
    /**
     * Obtêm código livro
     *
     * @return <code>Integer</code> código do livro
     */
    public int getCodeBook() {
        return codeBook;
    }//fim do método getCodeBook

    /**
     * Obtêm título livro
     *
     * @return <code>String</code> título livro
     */
    public String getTitleBook() {
        return titleBook;
    }//fim do método getTitleBook

    /**
     * Obtêm código da caixa
     *
     * @return <code>integer</code> código da caixa
     */
    public int getCodeBox() {
        return codeBox;
    }//fim do método getCodeBox

    /**
     * Obtêm o código especifico do livro
     *
     * @return <code>Integer</code> código especifico
     */
    public int getCodeBookSpecific() {
        return codeBookSpecific;
    }//fim do método getCodeBookSpecific

    /**
     * Obtêm obsservação sobre o livro
     *
     * @return <code>String</code> observação sobre livro
     */
    public String getObservationBook() {
        return observationBook;
    }//fim do método getObservationBook

    /**
     * Obtêm lista de documentos
     *
     * @return <code>List</code> lista de documentos
     */
    public List getListDocument() {
        return listDocument;
    }//fim do método getListDocument

    /**
     * Método para comparação de livros pelo código
     *
     * @param book livro
     * @return <code><Integer</code> parâmetro de comparação seguindo a
     * convenção da interface Comparable
     */
    public int compareTo(Object book) {
        Book otherBook = (Book) book;
        if (this.getCodeBox() < otherBook.getCodeBox()) {
            return -1;
        } else if (this.getCodeBox() == otherBook.getCodeBox()) {
            return 0;
        } else {
            return 1;
        }
    }//fim do método compareTo
    /**
     * Chamada para a classe com o método de comparação por título
     */
    public final static Comparator<Book> forTitle = new Book.ForTitle();

    /**
     * Classe interna que contém o método de comparação por título
     */
    private static class ForTitle implements Comparator<Book> {

        /**
         * Método de compara o título de dois livros
         *
         * @param book1 livro
         * @param book2 livro
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Book book1, Book book2) {
            return book1.getTitleBook().compareToIgnoreCase(book2.getTitleBook());
        }//fim do método compareTo
    }//fim da classe interna ForTitle
    /**
     * Chamada para a classe com o método de comparação por código da categoria
     */
    public final static Comparator<Book> forCategoryCode = new Book.ForCategoryCode();

    /**
     * Classe interna que contém o método de comparação por código da categoria
     */
    private static class ForCategoryCode implements Comparator<Book> {

        /**
         * Método de compara o código da categoria de duis livros
         *
         * @param book1 livro
         * @param book2 livro
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Book book1, Book book2) {
            Category category = Category.getCategory(book1);
            if (category == Category.getCategory(book2)) {
                if (book1.getCodeBookSpecific() < book2.getCodeBookSpecific()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (book1.getCodeBook() < book2.getCodeBook()) {
                    return -1;
                } else if (book1.getCodeBook() == book2.getCodeBook()) {
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
    public String toString() {
        String text = "Livro " + this.getCodeBookSpecific() + ": " + this.getTitleBook()
                + " Obervação:" + this.getObservationBook();
        return text;
    }//fim do método toString
}//fim da classe Book

