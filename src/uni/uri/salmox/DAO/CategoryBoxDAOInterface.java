
/*-
 * Classname:             CategoryBoxDAOInterface.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:34:48
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.DAO;

import java.util.Iterator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * Interface do DAO das categorias de caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public interface CategoryBoxDAOInterface {

    /**
     * Método abstrato responsável por obter a conexão com o banco
     */
    public void takeConnection();

    /**
     * Registra a caixa que recebe como parâmetro no banco de dados
     *
     * @param box caixa
     * @throws PreviouslyRegisteredException exceção lançada caso o código da
     * caixa já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException;

    /**
     * Registra a coleção de caixas que foi fornecida como parâmetro no banco de
     * dados
     *
     * @param boxes coleção de caixas
     * @throws PreviouslyRegisteredException exceção lançada caso o código da
     * caixa já esteja cadastrado
     *
     */
    // public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException;
    /**
     * Registra o livro que recebe como parâmetro no banco de dados
     *
     * @param book livro
     * @throws PreviouslyRegisteredException exceção lançada caso o código do
     * livro já esteja cadastrado
     *
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException;

    /**
     * Registra a coleção de livros que foi fornecida como parâmetro
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException exceção lançada caso o código do
     * livro já esteja cadastrado
     *
     */
    // public void registerBooks(Iterator books) throws PreviouslyRegisteredException;
    /**
     * Registra o documento que recebe como parâmetro no banco de dados
     *
     * @param document documento
     * @throws PreviouslyRegisteredException exceção lançada caso o código do
     * livro já esteja cadastrado
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException;

    /**
     * Registra a coleção de documentos que foi fornecida como parâmetro no
     * banco de dados
     *
     * @param documents
     * @throws PreviouslyRegisteredException exceção lançada caso o código do
     * documento já esteja cadastrado
     */
    //  public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException;
    /**
     * Edita os dados da caixa que foi enviada como parâmetro
     *
     * @param box caixa
     */
    public void editBox(Box box);

    /**
     * Edita os dados do livro que foi envidado como parâmetro
     *
     * @param book livro
     */
    public void editBook(Book book);

    /**
     * Edita os documentos do livro que foi enviado como parâmetro
     *
     * @param document documento
     *
     */
    public void editDocument(Document document);

    /**
     * Busca caixa com código fornecido como parâmetro
     *
     * @param codeBox código da ciaxa que deve ser buscada
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox);

    /**
     * Busca as caixas
     *
     * @return <code>Iterator</code> com as caixas
     */
    public Iterator searchBoxes();

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código do livro
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook);

    /**
     * Busca os livros
     *
     * @return <code>Iterator</code> com os livros
     */
    // public Iterator searchBooks();
    /**
     * Busca livros pertencentes a caixa com código fornecida como parâmetro
     *
     * @param codeBox código da caixa
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox);

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento
     * @return <code>Document</code> documento
     */
    public Document searchDocument(int codeDocument);

    /**
     * Busca os documentos
     *
     * @return <code>Iterator</code> com os documentos
     */
    public Iterator searchDocuments();

    /**
     * Busca documentos pertencentes ao livro com código fornecido como
     * parãmetro
     *
     * @param codeBook código do livro
     * @return <code>Iterator</code> com documentos do livro
     */
    public Iterator searchDocuments(int codeBook);

    /**
     * Muda o código da caixa para o especificado
     *
     * @param code código da caixa do documento
     * @param newCode novo código da categoria do documento
     */
    public void changeCodeBox(int code, int newCode);

    /**
     * Muda o código do livro para o especificado
     *
     * @param code código da livro do documento
     * @param newCode novo código da categoria do documento
     */
    public void changeCodeBook(int code, int newCode);

    /**
     * Muda o código do documento para o código especificado
     *
     * @param code código do documento
     * @param newCode novo código da categoria do documento
     */
    public void changeCodeDocument(int code, int newCode);

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldBox caixa antiga não editada
     * @param newBox nova caixa editada
     * @return <code>boolean</code> resposta
     */
    public boolean checkBoxCode(Box oldBox, Box newBox);

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldBook livro antigo não editado
     * @param newBook livro novo editado
     * @return <code>boolean</code> resposta
     */
    public boolean checkBookCode(Book oldBook, Book newBook);

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldDocument documento antigo não editado
     * @param newDocument documento novo editado
     * @return <code>boolean</code> resposta
     */
    public boolean checkDocumentCode(Document oldDocument, Document newDocument);
}//fim da classe CategoryBoxDAOInterface

