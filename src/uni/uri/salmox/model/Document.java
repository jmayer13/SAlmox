
/*-
 * Classname:             Document.java
 *
 * Version information:   1.1
 *
 * Date:                  (09/10/2012 - 13:18:48) 28/07/2014
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import java.util.Comparator;
import uni.uri.salmox.util.Category;

/**
 * Superclasse modelo de documento
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Document implements Comparable {

    //código do documento
    private int codeDocument;
    //código especifico da categoria
    private int codeDocumentSpecific;
    //título do documento
    private String titleDocument;
    //codigo do livro que o documento pertence
    private int codeBook;
    //observação sobre o documento
    private String observationDocument;
    //presente no almoxarifado
    //true=presente false=retirado
    private boolean present;

    /**
     * Construtor de Document sem arfumentos
     */
    public Document() {
    }//fim do construtor sem argumentos

    /**
     * Construtor de Document com código do documento
     *
     * @param codeDocument código do documento
     * @param titleDocument título do docuumento
     * @param codeBook codigo do livro
     * @param observationDocument observação sobre o documento
     * @param present presente
     */
    public Document(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        this.codeDocument = codeDocument;
        this.titleDocument = titleDocument;
        this.codeBook = codeBook;
        this.observationDocument = observationDocument;
        this.present = present;
    }//fim do construtor com código do documento

    /**
     * Construtor de Document sem código do documento
     *
     * @param titleDocument título do docuumento
     * @param codeBook codigo do livro
     * @param observationDocument observação sobre o documento
     * @param present presente
     */
    public Document(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        this.titleDocument = titleDocument;
        this.codeBook = codeBook;
        this.observationDocument = observationDocument;
        this.present = present;
    }//fim do construtor sem código do documento

    //sets
    /**
     * Define código do docomento
     *
     * @param codeDocument código do documento
     */
    public void setCodeDocument(int codeDocument) {
        this.codeDocument = codeDocument;
    }//fim do método setCodeDocument

    /**
     * Define o código especifico do documento
     *
     * @param codeDocumentSpecific
     */
    public void setCodeDocumentSpecific(int codeDocumentSpecific) {
        this.codeDocumentSpecific = codeDocumentSpecific;
    }//fim do método setCodeDocumentSpecific

    /**
     * Define título do documento
     *
     * @param titleDocument título do documento
     */
    public void setTitleDocument(String titleDocument) {
        this.titleDocument = titleDocument;
    }//fim do método setTitleDocument

    /**
     * Define código do livro que o documento pretence
     *
     * @param codeBook código do livro
     */
    public void setCodeBook(int codeBook) {
        this.codeBook = codeBook;
    }//fim do método setCodeBook

    /**
     * Define observação sobre o documento
     *
     * @param observationDocument observação dobre o documento
     */
    public void setObservationDocument(String observationDocument) {
        this.observationDocument = observationDocument;
    }//fim do método setObservationDocument

    /**
     * Define presença do documento
     *
     * @param present presença
     */
    public void setPresent(boolean present) {
        this.present = present;
    }//fim do método setPresent

    //gets
    /**
     * Obtêm código do documento
     *
     * @return <code>integer</code> código documento
     */
    public int getCodeDocument() {
        return codeDocument;
    }//fim do método getCodeDocument

    /**
     * Obtêm o código especifico do documento
     *
     * @return <code>Integer</code> código especifico
     */
    public int getCodeDocumentSpecific() {
        return codeDocumentSpecific;
    }//fim do método getCodeDocumentSpecific

    /**
     * Obtêm título do documento
     *
     * @return <code>String</code> título do documento
     */
    public String getTitleDocument() {
        return titleDocument;
    }//fim do método getTitleDocument

    /**
     * Obtêm código do livro
     *
     * @return <code>integer</code> código do livro
     */
    public int getCodeBook() {
        return codeBook;
    }//fim do método getCodeBook

    /**
     * Obtêm observação sobre o documento
     *
     * @return <code>String</code> observação sobre o documento
     */
    public String getObservationDocument() {
        return observationDocument;
    }//fim do método getObservationDocument

    /**
     * Obtêm presença
     *
     * @return <code>boolean</code> presente
     */
    public boolean getPresent() {
        return present;
    }//fim do método getPresent

    /**
     * Método para comparação de documentos pelo código
     *
     * @param document documento
     * @return <code><Integer</code> parâmetro de comparação seguindo a
     * convenção da interface Comparable
     */
    public int compareTo(Object document) {
        Document otherDocument = (Document) document;
        if (this.getCodeDocument() < otherDocument.getCodeDocument()) {
            return -1;
        } else if (this.getCodeDocument() == otherDocument.getCodeDocument()) {
            return 0;
        } else {
            return 1;
        }
    }//fim do método compareTo
    /**
     * Chamada para a classe com o método de comparação por título
     */
    public final static Comparator<Document> forTitle = new ForTitle();

    /**
     * Classe interna que contém o método de comparação por título
     */
    private static class ForTitle implements Comparator<Document> {

        /**
         * Método de compara o título de dois documentos
         *
         * @param document1 documento
         * @param document2 documento
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Document document1, Document document2) {
            return document1.getTitleDocument().compareToIgnoreCase(document2.getTitleDocument());
        }//fim do método compareTo
    }//fim da classe interna For Title
    /**
     * Chamada para a classe com o método de comparação por código da categoria
     */
    public final static Comparator<Document> forCategoryCode = new Document.ForCategoryCode();

    /**
     * Classe interna que contém o método de comparação por código da categoria
     */
    private static class ForCategoryCode implements Comparator<Document> {

        /**
         * Método de compara o código da categoria de duis livros
         *
         * @param document1 livro
         * @param document2 livro
         * @return <code>Integer</code> parâmetro de comparação seguindo a
         * convenção da interface Comparable
         */
        public int compare(Document document1, Document document2) {
            Category category = Category.getCategory(document1);
            if (category == Category.getCategory(document2)) {
                if (document1.getCodeDocumentSpecific() < document2.getCodeDocumentSpecific()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (document1.getCodeDocument() < document2.getCodeDocument()) {
                    return -1;
                } else if (document1.getCodeDocument() == document2.getCodeDocument()) {
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
        String presente = "";
        if (this.getPresent()) {
            presente = "SIM";
        } else {
            presente = "NÃO";
        }
        String text = "Documento " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe Document

