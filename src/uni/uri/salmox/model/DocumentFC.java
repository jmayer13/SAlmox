
/*-
 * Classname:             DocumentFC.java
 *
 * Version information:   1.1
 *
 * Date:                  11/12/2012 - 13:45:47
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import java.util.ArrayList;
import java.util.List;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.Item;

/**
 * Classe modelo de documento de livro de caixa de formandos, herda de Document
 *
 * @see uni.uri.salmox.model.Document
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DocumentFC extends Document {

    //lista de documentos
    private List<Item> items;

    /**
     * Construtor de DocumentF sem argumentos
     */
    public DocumentFC() {
        super();
        items = new ArrayList();
    }//fim do construtor sem argumentos

    /**
     * Construtor de DocumentF com código de documento de formandos e com código
     * de documento
     *
     * @param codeDocumentF código de documento de formando
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentFC(int codeDocumentF, int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        this.setCodeDocumentSpecific(codeDocumentF);
        items = new ArrayList();
    }//fim do construtor com código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de formandos e com código
     * de documento
     *
     * @param codeDocument código de documento
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentFC(int codeDocument, String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(codeDocument, titleDocument, codeBook, observationDocument, present);
        items = new ArrayList();
    }//fim do construtor sem código de documento de formandos e com código de documento

    /**
     * Construtor de DocumentF sem código de documento de formandos e sem código
     * de documento
     *
     * @param titleDocument título de documento
     * @param codeBook código de livro
     * @param observationDocument observação de documento
     * @param present presente
     */
    public DocumentFC(String titleDocument, int codeBook,
            String observationDocument, boolean present) {
        super(titleDocument, codeBook, observationDocument, present);
        items = new ArrayList();
    }//fim do construtor sem código de documento de formandos e sem código de documento

    //sets
    /**
     * Adiciona item ao documento
     *
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }//fim do método addItem

    /**
     * Obtêm lista de itens
     *
     * @return <code>List</code> de itens
     */
    public List<Item> getItems() {
        return items;
    }//fim do método getItems

    /**
     * Remove todos os items da lista
     */
    public void removeAllItems() {
        items.clear();
    }//fim do método removeAllItems

    /**
     * Remove item pelo seu código
     *
     * @param codeItem código do item a ser removido
     */
    public void removeItemPerCode(int codeItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCode() == codeItem) {
                items.remove(i);
                return;
            }
        }
    }//fim dop método removeItemPerCode

    /**
     * Remove item especifico
     *
     * @param item a ser removido
     * @return <code>Boolean</code> item enconstado e removido
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }//fim do método removeItem

    public void addItems(List<Item> items) {
        this.items = items;
    }

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
        String text = "Documento " + Category.FC.getAbbreviation() + " - " + this.getCodeDocumentSpecific()
                + ": " + this.getTitleDocument() + " Presente:" + presente + " Observação:" + this.getObservationDocument();
        return text;
    }//fim do método toString
}//fim da classe DocumentF

