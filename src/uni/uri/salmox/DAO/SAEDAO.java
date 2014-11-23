
/*-
 * Classname:             SAEDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:29:54
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookSAE;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxSAE;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.model.DocumentSAE;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de filantropia
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SAEDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public SAEDAO() {
        takeConnection();
    }//fim do método construtor sem parãmetros

    /**
     * Pega conexão com o banco de dados
     */
    public void takeConnection() {
        connectionDatabase = ConectionFactory.getConnection();
        connection = connectionDatabase.getConnection();
    }//fim do método takeConnection

    /**
     * Registra a caixa de Almoxarifado no banco de dados
     *
     * @param box caixa de filantropia
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de filantropia
            BoxSAE boxSAE = (BoxSAE) box;
            //cria PreparedStatement com função de cadastro de caixa de filantropia
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_sae(?,?,?,?);");
            register.setInt(1, boxSAE.getCodeBoxSAE());
            register.setString(2, boxSAE.getResponsibleBox());
            register.setString(3, boxSAE.getObservationBox());
            register.setInt(4, boxSAE.getYear());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de filantropia
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de filantropia
                BoxSAE boxSAE = (BoxSAE) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_sae(?,?,?,?);");
                register.setInt(1, boxSAE.getCodeBoxSAE());
                register.setString(2, boxSAE.getResponsibleBox());
                register.setString(3, boxSAE.getObservationBox());
                register.setInt(4, boxSAE.getYear());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de filantropia no banco de dados
     *
     * @param book livro de caixa de filantropia
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de filantropia
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de filantropia
            BookSAE bookSAE = (BookSAE) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_sae(?,?,?,?);");
            register.setInt(1, bookSAE.getCodeBookSAE());
            register.setString(2, bookSAE.getTitleBook());
            register.setInt(3, bookSAE.getCodeBox());
            register.setString(4, bookSAE.getObservationBook());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de filantropia no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {
            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de filantropia
                BookSAE bookSAE = (BookSAE) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_sae(?,?,?,?);");
                register.setInt(1, bookSAE.getCodeBookSAE());
                register.setString(2, bookSAE.getTitleBook());
                register.setInt(3, bookSAE.getCodeBox());
                register.setString(4, bookSAE.getObservationBook());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de filantropia
     *
     * @param document documento de livro de caixa de filantropia
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de filantropia
            DocumentSAE documentSAE = (DocumentSAE) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_sae(?,?,?,?);");
            register.setInt(1, documentSAE.getCodeDocumentSAE());
            register.setString(2, documentSAE.getTitleDocument());
            register.setInt(3, documentSAE.getCodeBook());
            register.setString(4, documentSAE.getObservationDocument());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de filantropia no banco
     * de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de filantropia
                DocumentSAE documentSAE = (DocumentSAE) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de filantropia
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_sae(?,?,?,?);");
                register.setInt(1, documentSAE.getCodeDocumentSAE());
                register.setString(2, documentSAE.getTitleDocument());
                register.setInt(3, documentSAE.getCodeBook());
                register.setString(4, documentSAE.getObservationDocument());
                //retorna true se código de livro de caixa de filantropia já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de filantropia fornecida como parãmetro
     *
     * @param box caixa de filantropia
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de filantropia
            BoxSAE boxSAE = (BoxSAE) box;

            //cria PreparedStatement com função de ediçao de caixa de filantropia
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_sae(?,?,?,?,?);");
            changer.setInt(1, boxSAE.getCodeBox());
            changer.setInt(2, boxSAE.getCodeBoxSAE());
            changer.setString(3, boxSAE.getResponsibleBox());
            changer.setString(4, boxSAE.getObservationBox());
            changer.setInt(5, boxSAE.getYear());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de filantropia que foi fornecida como
     * parãmetro
     *
     * @param book livro de caixa de filantropia
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de filantropia
            BookSAE bookSAE = (BookSAE) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_sae(?,?,?,?,?);");
            changer.setInt(1, bookSAE.getCodeBook());
            changer.setInt(2, bookSAE.getCodeBookSAE());
            changer.setString(3, bookSAE.getTitleBook());
            changer.setInt(4, bookSAE.getCodeBox());
            changer.setString(5, bookSAE.getObservationBook());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de filantropia que foi
     * fornecido como parâmetro
     *
     * @param document documento de livro de caixa de filantropia
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de filantropia
            DocumentSAE documentSAE = (DocumentSAE) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_sae(?,?,?,?,?);");
            changer.setInt(1, documentSAE.getCodeDocument());
            changer.setInt(2, documentSAE.getCodeDocumentSAE());
            changer.setString(3, documentSAE.getTitleDocument());
            changer.setInt(4, documentSAE.getCodeBook());
            changer.setString(5, documentSAE.getObservationDocument());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de filantropia com código fornecido como parámetro
     *
     * @param codeBox código do caixa de filantropia
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxSAE boxSAE = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_sae, ano FROM ver_caixa_sae(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxSAE = new BoxSAE();
                boxSAE.setCodeBox(resultSet.getInt(1));
                boxSAE.setResponsibleBox(resultSet.getString(2));
                boxSAE.setObservationBox(resultSet.getString(3));
                boxSAE.setTitleBox(resultSet.getString(4));
                boxSAE.setCodeBoxSpecific(resultSet.getInt(5));
                boxSAE.setYear(resultSet.getInt(6));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxSAE;
    }//fim do método searchBox

    /**
     * Busca caixas de filantropia
     *
     * @return <code>Iterator</code> coleção de caixas de filantropia
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de filantropia
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_sae ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxSAE boxSAE = new BoxSAE();
                boxSAE.setCodeBox(resultSet.getInt(1));
                boxSAE.setCodeBoxSpecific(resultSet.getInt(2));
                boxSAE.setTitleBox(resultSet.getString(3));
                boxSAE.setYear(resultSet.getInt(4));
                boxSAE.setObservationBox(resultSet.getString(5));
                boxSAE.setResponsibleBox(resultSet.getString(6));
                boxes.add(boxSAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de filantropia
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookSAE bookSAE = null;
        try {
            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_sae, titulo_livro, observacao_livro, codigo_caixa FROM ver_livro_sae(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookSAE = new BookSAE();
                bookSAE.setCodeBook(resultSet.getInt(1));
                bookSAE.setCodeBookSpecific(resultSet.getInt(2));
                bookSAE.setTitleBook(resultSet.getString(3));
                bookSAE.setObservationBook(resultSet.getString(4));
                bookSAE.setCodeBox(resultSet.getInt(5));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookSAE;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de filantropia
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de filantropia
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_sae ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookSAE bookSAE = new BookSAE();
                bookSAE.setCodeBook(resultSet.getInt(1));
                bookSAE.setCodeBookSpecific(resultSet.getInt(2));
                bookSAE.setTitleBook(resultSet.getString(3));
                bookSAE.setObservationBook(resultSet.getString(4));
                bookSAE.setCodeBox(resultSet.getInt(5));
                books.add(bookSAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de filantropia que pertensam ao código da caixa de
     * filantropia fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de filantropia
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_sae,"
                    + "titulo_livro, observacao_livro, codigo_caixa FROM ver_livro_sae_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookSAE bookSAE = new BookSAE();
                bookSAE.setCodeBook(resultSet.getInt(1));
                bookSAE.setCodeBookSpecific(resultSet.getInt(2));
                bookSAE.setTitleBook(resultSet.getString(3));
                bookSAE.setObservationBook(resultSet.getString(4));
                bookSAE.setCodeBox(resultSet.getInt(5));
                books.add(bookSAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de filantropia
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentSAE documentSAE = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_sae,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_sae(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentSAE = new DocumentSAE();
                documentSAE.setCodeDocument(resultSet.getInt(1));
                documentSAE.setCodeDocumentSAE(resultSet.getInt(2));
                documentSAE.setTitleDocument(resultSet.getString(3));
                documentSAE.setPresent(resultSet.getBoolean(4));
                documentSAE.setObservationDocument(resultSet.getString(5));
                documentSAE.setCodeBook(resultSet.getInt(6));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentSAE;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de filantropia
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de filantropia
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_sae ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentSAE documentSAE = new DocumentSAE();
                documentSAE.setCodeDocument(resultSet.getInt(1));
                documentSAE.setCodeDocumentSAE(resultSet.getInt(2));
                documentSAE.setTitleDocument(resultSet.getString(3));
                documentSAE.setPresent(resultSet.getBoolean(4));
                documentSAE.setObservationDocument(resultSet.getString(5));
                documentSAE.setCodeBook(resultSet.getInt(6));
                documents.add(documentSAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca documentos que pertensam ao livro com código fornecido como
     * parâmetro
     *
     * @param codeBook código do livro
     * @return <code>Iterator</code> coleção de documentos
     */
    public Iterator searchDocuments(int codeBook) {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão de busca de documento de filantropia
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_sae, titulo_documento ,presente, observacao_documento, codigo_livro "
                    + " FROM ver_documento_sae_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentSAE documentSAE = new DocumentSAE();
                documentSAE.setCodeDocument(resultSet.getInt(1));
                documentSAE.setCodeDocumentSAE(resultSet.getInt(2));
                documentSAE.setTitleDocument(resultSet.getString(3));
                documentSAE.setPresent(resultSet.getBoolean(4));
                documentSAE.setObservationDocument(resultSet.getString(5));
                documentSAE.setCodeBook(resultSet.getInt(6));
                documents.add(documentSAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos SAE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBoxSAE código da caixa <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param year ano <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxSAE, String title, int year, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_sae FROM busca_caixa_sae(?,?,?,?,?)");
            seacher.setInt(1, codeBoxSAE);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setString(4, responsible);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxSAE box = new BoxSAE();
                box.setCodeBox(resultSet.getInt(1));
                box.setResponsibleBox(resultSet.getString(2));
                box.setObservationBox(resultSet.getString(3));
                box.setTitleBox(resultSet.getString(4));
                box.setCodeBoxSpecific(resultSet.getInt(5));
                //adiciona caixas a lista
                boxes.add(box);
            }
            //retorna iterator
            return boxes.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBox

    /**
     * Busca livros pelos atributos
     *
     * @param codeBookSAE código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookSAE, String title, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_sae FROM busca_livro_sae(?,?,?)");
            seacher.setInt(1, codeBookSAE);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookSAE book = new BookSAE();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                //adiciona livro a lista
                bookes.add(book);
            }
            //retorna iterator
            return bookes.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar livro!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBook

    /**
     * Busca documentos pelos atributos
     *
     * @param codeDocumentSAE código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentSAE, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_sae FROM busca_documento_sae(?,?,?)");
            seacher.setInt(1, codeDocumentSAE);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentDP document = new DocumentDP();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentDP(resultSet.getInt(6));
                //adiciona documento a lista
                documents.add(document);
            }
            //retorna iterator
            return documents.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar documento!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchDocument

    /**
     * Muda o código da caixa para o código especificado
     *
     * @param code código da caixa
     * @param newCode novo código
     */
    public void changeCodeBox(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_sae (?,?)");
            changer.setInt(1, code);
            changer.setInt(2, newCode);
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível alterar código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método changeCodeBox

    /**
     * Muda o código do livro para o código especificado
     *
     * @param code código da caixa
     * @param newCode novo código
     */
    public void changeCodeBook(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_sae (?,?)");
            changer.setInt(1, code);
            changer.setInt(2, newCode);
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível alterar código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método changeCodeBook

    /**
     * Muda o código do documento para o código especificado
     *
     * @param code código da caixa
     * @param newCode novo código
     */
    public void changeCodeDocument(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_sae (?,?)");
            changer.setInt(1, code);
            changer.setInt(2, newCode);
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível alterar código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método changeCodeDocument

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldBox caixa antiga não editada
     * @param newBox nova caixa editada
     * @return <code>boolean</code> resposta
     */
    public boolean checkBoxCode(Box oldBox, Box newBox) {
        BoxSAE oldBoxSAE = (BoxSAE) oldBox;
        BoxSAE newBoxSAE = (BoxSAE) newBox;
        if (oldBoxSAE.getCodeBoxSAE() != newBoxSAE.getCodeBoxSAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_sae(?);");
                verify.setInt(1, newBoxSAE.getCodeBoxSAE());
                ResultSet resultset = verify.executeQuery();

                while (resultset.next()) {
                    return !(resultset.getBoolean(1));
                }
                return false;
            } catch (SQLException sqlException) {
                System.err.println("Erro: não foi possível verificar código!");
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi possível verificar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(sqlException);
                return false;
            }
        } else {
            return true;
        }
    }//fim do método checkBoxCode

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldBook livro antigo não editado
     * @param newBook livro novo editado
     * @return <code>boolean</code> resposta
     */
    public boolean checkBookCode(Book oldBook, Book newBook) {
        BookSAE oldBookSAE = (BookSAE) oldBook;
        BookSAE newBookSAE = (BookSAE) newBook;
        if (oldBookSAE.getCodeBookSAE() != newBookSAE.getCodeBookSAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_sae(?,?);");
                verify.setInt(1, newBookSAE.getCodeBox());
                verify.setInt(2, newBookSAE.getCodeBookSAE());
                ResultSet resultset = verify.executeQuery();

                while (resultset.next()) {
                    return !(resultset.getBoolean(1));
                }
                return false;
            } catch (SQLException sqlException) {
                System.err.println("Erro: não foi possível verificar código!");
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi possível verificar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(sqlException);
                return false;
            }
        } else {
            return true;
        }
    }//fim do método checkBookCode

    /**
     * Checa se o código foi altedado e se pode modificar o código
     *
     * @param oldDocument documento antigo não editado
     * @param newDocument documento novo editado
     * @return <code>boolean</code> resposta
     */
    public boolean checkDocumentCode(Document oldDocument, Document newDocument) {
        DocumentSAE oldDocumentSAE = (DocumentSAE) oldDocument;
        DocumentSAE newDocumentSAE = (DocumentSAE) newDocument;
        if (oldDocumentSAE.getCodeDocumentSAE() != newDocumentSAE.getCodeDocumentSAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_sae(?,?);");
                verify.setInt(1, newDocumentSAE.getCodeBook());
                verify.setInt(2, newDocumentSAE.getCodeDocumentSAE());
                ResultSet resultset = verify.executeQuery();

                while (resultset.next()) {
                    return !(resultset.getBoolean(1));
                }
                return false;
            } catch (SQLException sqlException) {
                System.err.println("Erro: não foi possível verificar código!");
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi possível verificar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(sqlException);
                return false;
            }
        } else {
            return true;
        }
    }//fim do método checkDocumentCode

    /**
     * Lista títulos
     *
     * @return <code>List</code> com cursos
     */
    public List<String> listTitle() {
        List<String> courses = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_sae;");
            ResultSet resultset = list.executeQuery();

            while (resultset.next()) {
                courses.add(resultset.getString(1));
            }
            return courses;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível listar títulos!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível listar títulos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
            return null;
        }
    }//fim do método listTitle

    /**
     * Lista títulos
     *
     * @return <code>List</code> com títulos
     */
    public List<String> listDocTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_doc_sae;");
            ResultSet resultset = list.executeQuery();

            while (resultset.next()) {
                titles.add(resultset.getString(1));
            }
            return titles;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível listar títulos!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível listar títulos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
            return null;
        }
    }//fim do método listCourses
}//fim da classe SAEDAO

