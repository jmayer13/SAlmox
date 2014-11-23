
/*-
 * Classname:             MCDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:29:27
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookMC;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxMC;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentMC;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de movimentos de caixa
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 *
 */
public class MCDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public MCDAO() {
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
     * @param box caixa de movimentos de caixa
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de movimentos de caixa
            BoxMC boxMC = (BoxMC) box;
            //cria PreparedStatement com função de cadastro de caixa de movimentos de caixa
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_mc(?,?,?);");
            register.setInt(1, boxMC.getCodeBoxMC());
            register.setString(2, boxMC.getResponsibleBox());
            register.setString(3, boxMC.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de movimentos de caixa
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de movimentos de caixa
                BoxMC boxMC = (BoxMC) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_mc(?,?,?);");
                register.setInt(1, boxMC.getCodeBoxMC());
                register.setString(2, boxMC.getResponsibleBox());
                register.setString(3, boxMC.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de movimentos de caixa no banco de dados
     *
     * @param book livro de caixa de movimentos de caixa
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de movimentos de caixa
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de movimentos de caixa
            BookMC bookMC = (BookMC) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_mc(?,?,?,?,?);");
            register.setInt(1, bookMC.getCodeBookMC());
            register.setString(2, bookMC.getTitleBook());
            register.setInt(3, bookMC.getCodeBox());
            register.setString(4, bookMC.getObservationBook());
            register.setInt(5, bookMC.getYear());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de movimentos de caixa no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {

            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de movimentos de caixa
                BookMC bookMC = (BookMC) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_mc(?,?,?,?,?);");
                register.setInt(1, bookMC.getCodeBookMC());
                register.setString(2, bookMC.getTitleBook());
                register.setInt(3, bookMC.getCodeBox());
                register.setString(4, bookMC.getObservationBook());
                register.setInt(5, bookMC.getYear());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de movimentos de caixa
     *
     * @param document documento de livro de caixa de movimentos de caixa
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de movimentos de caixa
            DocumentMC documentMC = (DocumentMC) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_mc(?,?,?,?,?);");
            register.setInt(1, documentMC.getCodeDocumentMC());
            register.setString(2, documentMC.getTitleDocument());
            register.setInt(3, documentMC.getCodeBook());
            register.setString(4, documentMC.getObservationDocument());
            register.setDate(5, documentMC.getDate());

            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de movimentos de caixa
     * no banco de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de movimentos de caixa
                DocumentMC documentMC = (DocumentMC) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de movimentos de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_mc(?,?,?,?,?);");
                register.setInt(1, documentMC.getCodeDocumentMC());
                register.setString(2, documentMC.getTitleDocument());
                register.setInt(3, documentMC.getCodeBook());
                register.setString(4, documentMC.getObservationDocument());
                register.setDate(5, documentMC.getDate());
                //retorna true se código de livro de caixa de movimentos de caixa já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de movimentos de caixa fornecida como parãmetro
     *
     * @param box caixa de movimentos de caixa
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de movimentos de caixa
            BoxMC boxMC = (BoxMC) box;

            //cria PreparedStatement com função de ediçao de caixa de movimentos de caixa
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_mc(?,?,?,?);");
            changer.setInt(1, boxMC.getCodeBox());
            changer.setInt(2, boxMC.getCodeBoxMC());
            changer.setString(3, boxMC.getResponsibleBox());
            changer.setString(4, boxMC.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de movimentos de caixa que foi fornecida
     * como parãmetro
     *
     * @param book livro de caixa de movimentos de caixa
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de movimentos de caixa
            BookMC bookMC = (BookMC) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_mc(?,?,?,?,?,?);");
            changer.setInt(1, bookMC.getCodeBook());
            changer.setInt(2, bookMC.getCodeBookMC());
            changer.setString(3, bookMC.getTitleBook());
            changer.setInt(4, bookMC.getCodeBox());
            changer.setString(5, bookMC.getObservationBook());
            changer.setInt(6, bookMC.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de movimentos de caixa que
     * foi fornecido como parâmetro
     *
     * @param document documento de livro de caixa de movimentos de caixa
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de movimentos de caixa
            DocumentMC documentMC = (DocumentMC) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_mc(?,?,?,?,?,?);");
            changer.setInt(1, documentMC.getCodeDocument());
            changer.setInt(2, documentMC.getCodeDocumentMC());
            changer.setString(3, documentMC.getTitleDocument());
            changer.setInt(4, documentMC.getCodeBook());
            changer.setString(5, documentMC.getObservationDocument());
            changer.setDate(6, documentMC.getDate());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de movimentos de caixa com código fornecido como parámetro
     *
     * @param codeBox código do caixa de movimentos de caixa
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxMC boxMC = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_mc FROM ver_caixa_mc(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxMC = new BoxMC();
                boxMC.setCodeBox(resultSet.getInt(1));
                boxMC.setResponsibleBox(resultSet.getString(2));
                boxMC.setObservationBox(resultSet.getString(3));
                boxMC.setTitleBox(resultSet.getString(4));
                boxMC.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxMC;
    }//fim do método searchBox

    /**
     * Busca caixas de movimentos de caixa
     *
     * @return <code>Iterator</code> coleção de caixas de movimentos de caixa
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de movimentos de caixa
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_mc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxMC boxMC = new BoxMC();
                boxMC.setCodeBox(resultSet.getInt(1));
                boxMC.setCodeBoxSpecific(resultSet.getInt(2));
                boxMC.setTitleBox(resultSet.getString(3));
                boxMC.setObservationBox(resultSet.getString(4));
                boxMC.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxMC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de movimentos de caixa
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookMC bookMC = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_mc, titulo_livro, observacao_livro, ano,codigo_caixa FROM ver_livro_mc(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookMC = new BookMC();
                bookMC.setCodeBook(resultSet.getInt(1));
                bookMC.setCodeBookSpecific(resultSet.getInt(2));
                bookMC.setTitleBook(resultSet.getString(3));
                bookMC.setObservationBook(resultSet.getString(4));
                bookMC.setYear(resultSet.getInt(5));
                bookMC.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookMC;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de movimentos de caixa
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de movimentos de caixa
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_mc ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookMC bookMC = new BookMC();
                bookMC.setCodeBook(resultSet.getInt(1));
                bookMC.setCodeBookSpecific(resultSet.getInt(2));
                bookMC.setYear(resultSet.getInt(3));
                bookMC.setTitleBook(resultSet.getString(4));
                bookMC.setObservationBook(resultSet.getString(5));
                bookMC.setCodeBox(resultSet.getInt(6));
                books.add(bookMC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de movimentos de caixa que pertensam ao código da caixa de
     * movimentos de caixa fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de movimentos de caixa
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_mc,"
                    + "ano, titulo_livro, observacao_livro, codigo_caixa FROM ver_livro_mc_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookMC bookMC = new BookMC();
                bookMC.setCodeBook(resultSet.getInt(1));
                bookMC.setCodeBookSpecific(resultSet.getInt(2));
                bookMC.setYear(resultSet.getInt(3));
                bookMC.setTitleBook(resultSet.getString(4));
                bookMC.setObservationBook(resultSet.getString(5));
                bookMC.setCodeBox(resultSet.getInt(6));
                books.add(bookMC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de movimentos
     * de caixa
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentMC documentMC = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_mc,titulo_documento, presente, observacao_documento, data_mc,codigo_livro FROM ver_documento_mc(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentMC = new DocumentMC();
                documentMC.setCodeDocument(resultSet.getInt(1));
                documentMC.setCodeDocumentMC(resultSet.getInt(2));
                documentMC.setTitleDocument(resultSet.getString(3));
                documentMC.setPresent(resultSet.getBoolean(4));
                documentMC.setObservationDocument(resultSet.getString(5));
                documentMC.setDate(resultSet.getDate(6));
                documentMC.setCodeBook(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentMC;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de movimentos de caixa
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de movimentos de caixa
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_mc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentMC documentMC = new DocumentMC();
                documentMC.setCodeDocument(resultSet.getInt(1));
                documentMC.setCodeDocumentMC(resultSet.getInt(2));
                documentMC.setTitleDocument(resultSet.getString(3));
                documentMC.setDate(resultSet.getDate(4));
                documentMC.setPresent(resultSet.getBoolean(5));
                documentMC.setObservationDocument(resultSet.getString(6));
                documentMC.setCodeBook(resultSet.getInt(7));

                documents.add(documentMC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos MC!");
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
            //cria preparedStatement com visão de busca de documento de movimentos de caixa
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_mc, titulo_documento, data_mc ,presente, observacao_documento, codigo_livro "
                    + " FROM ver_documento_mc_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentMC documentMC = new DocumentMC();
                documentMC.setCodeDocument(resultSet.getInt(1));
                documentMC.setCodeDocumentMC(resultSet.getInt(2));
                documentMC.setTitleDocument(resultSet.getString(3));
                documentMC.setDate(resultSet.getDate(4));
                documentMC.setPresent(resultSet.getBoolean(5));
                documentMC.setObservationDocument(resultSet.getString(6));
                documentMC.setCodeBook(resultSet.getInt(7));
                documents.add(documentMC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos MC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBoxMC código da caixa <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxMC, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_mc FROM busca_caixa_mc(?,?,?,?)");
            seacher.setInt(1, codeBoxMC);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxMC box = new BoxMC();
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
     * @param codeBookMC código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookMC, String title, int year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_mc, ano FROM busca_livro_mc(?,?,?,?)");
            seacher.setInt(1, codeBookMC);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookMC book = new BookMC();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setYear(resultSet.getInt(6));
                //adiciona livro a lista
                bookes.add(book);
            }
            //retorna iterator
            return bookes.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar livro!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBook

    /**
     * Busca documentos pelos atributos
     *
     * @param codeDocumentMC código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param date data <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentMC, String title, Date date, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_mc, data_mc FROM busca_documento_mc(?,?,?,?)");
            seacher.setInt(1, codeDocumentMC);
            seacher.setString(2, title);
            seacher.setDate(3, date);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentMC document = new DocumentMC();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentMC(resultSet.getInt(6));
                document.setDate(resultSet.getDate(7));
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_mc (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_mc (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_mc (?,?)");
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
        BoxMC oldBoxMC = (BoxMC) oldBox;
        BoxMC newBoxMC = (BoxMC) newBox;
        if (oldBoxMC.getCodeBoxMC() != newBoxMC.getCodeBoxMC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_mc(?);");
                verify.setInt(1, newBoxMC.getCodeBoxMC());
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
        BookMC oldBookMC = (BookMC) oldBook;
        BookMC newBookMC = (BookMC) newBook;
        if (oldBookMC.getCodeBookMC() != newBookMC.getCodeBookMC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_mc(?,?);");
                verify.setInt(1, newBookMC.getCodeBox());
                verify.setInt(2, newBookMC.getCodeBookMC());
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
        DocumentMC oldDocumentMC = (DocumentMC) oldDocument;
        DocumentMC newDocumentMC = (DocumentMC) newDocument;
        if (oldDocumentMC.getCodeDocumentMC() != newDocumentMC.getCodeDocumentMC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_mc(?,?);");
                verify.setInt(1, newDocumentMC.getCodeBook());
                verify.setInt(2, newDocumentMC.getCodeDocumentMC());
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
}//fim da classe MCDAO

