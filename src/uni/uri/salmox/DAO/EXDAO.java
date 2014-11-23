
/*-
 * Classname:             EXDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:28:32
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
import uni.uri.salmox.model.BookEX;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxEX;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentEX;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de ex-alunos
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class EXDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public EXDAO() {
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
     * @param box caixa de ex-alunos
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de ex-alunos
            BoxEX boxEX = (BoxEX) box;
            //cria PreparedStatement com função de cadastro de caixa de ex-alunos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_ex(?,?,?);");
            register.setInt(1, boxEX.getCodeBoxEX());
            register.setString(2, boxEX.getResponsibleBox());
            register.setString(3, boxEX.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de ex-alunos
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de ex-alunos
                BoxEX boxEX = (BoxEX) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_ex(?,?,?);");
                register.setInt(1, boxEX.getCodeBoxEX());
                register.setString(2, boxEX.getResponsibleBox());
                register.setString(3, boxEX.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de ex-alunos no banco de dados
     *
     * @param book livro de caixa de ex-alunos
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de ex-alunos
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de ex-alunos
            BookEX bookEX = (BookEX) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_ex(?,?,?,?);");
            register.setInt(1, bookEX.getCodeBookEX());
            register.setString(2, bookEX.getTitleBook());
            register.setInt(3, bookEX.getCodeBox());
            register.setString(4, bookEX.getObservationBook());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de ex-alunos no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {
            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de ex-alunos
                BookEX bookEX = (BookEX) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_ex(?,?,?,?);");
                register.setInt(1, bookEX.getCodeBookEX());
                register.setString(2, bookEX.getTitleBook());
                register.setInt(3, bookEX.getCodeBox());
                register.setString(4, bookEX.getObservationBook());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de ex-alunos
     *
     * @param document documento de livro de caixa de ex-alunos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de ex-alunos
            DocumentEX documentEX = (DocumentEX) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_ex(?,?,?,?,?);");
            register.setInt(1, documentEX.getCodeDocumentEX());
            register.setString(2, documentEX.getTitleDocument());
            register.setInt(3, documentEX.getCodeBook());
            register.setString(4, documentEX.getObservationDocument());
            register.setInt(5, documentEX.getYear());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de ex-alunos no banco de
     * dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de ex-alunos
                DocumentEX documentEX = (DocumentEX) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de ex-alunos
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_ex(?,?,?,?,?);");
                register.setInt(1, documentEX.getCodeDocumentEX());
                register.setString(2, documentEX.getTitleDocument());
                register.setInt(3, documentEX.getCodeBook());
                register.setString(4, documentEX.getObservationDocument());
                register.setInt(5, documentEX.getYear());
                //retorna true se código de livro de caixa de ex-alunos já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de ex-alunos fornecida como parãmetro
     *
     * @param box caixa de ex-alunos
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de ex-alunos
            BoxEX boxEX = (BoxEX) box;

            //cria PreparedStatement com função de ediçao de caixa de ex-alunos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_ex(?,?,?,?);");
            changer.setInt(1, boxEX.getCodeBox());
            changer.setInt(2, boxEX.getCodeBoxEX());
            changer.setString(3, boxEX.getResponsibleBox());
            changer.setString(4, boxEX.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de ex-alunos que foi fornecida como
     * parãmetro
     *
     * @param book livro de caixa de ex-alunos
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de ex-alunos
            BookEX bookEX = (BookEX) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_ex(?,?,?,?,?);");
            changer.setInt(1, bookEX.getCodeBook());
            changer.setInt(2, bookEX.getCodeBookEX());
            changer.setString(3, bookEX.getTitleBook());
            changer.setInt(4, bookEX.getCodeBox());
            changer.setString(5, bookEX.getObservationBook());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de ex-alunos que foi
     * fornecido como parâmetro
     *
     * @param document documento de livro de caixa de ex-alunos
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de ex-alunos
            DocumentEX documentEX = (DocumentEX) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_ex(?,?,?,?,?,?);");
            changer.setInt(1, documentEX.getCodeDocument());
            changer.setInt(2, documentEX.getCodeDocumentEX());
            changer.setString(3, documentEX.getTitleDocument());
            changer.setInt(4, documentEX.getCodeBook());
            changer.setString(5, documentEX.getObservationDocument());
            changer.setInt(6, documentEX.getYear());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de ex-alunos com código fornecido como parámetro
     *
     * @param codeBox código do caixa de ex-alunos
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxEX boxEX = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_ex FROM ver_caixa_ex(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxEX = new BoxEX();
                boxEX.setCodeBox(resultSet.getInt(1));
                boxEX.setResponsibleBox(resultSet.getString(2));
                boxEX.setObservationBox(resultSet.getString(3));
                boxEX.setTitleBox(resultSet.getString(4));
                boxEX.setCodeBoxSpecific(resultSet.getInt(5));

            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxEX;
    }//fim do método searchBox

    /**
     * Busca caixas de ex-alunos
     *
     * @return <code>Iterator</code> coleção de caixas de ex-alunos
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de ex-alunos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_ex ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxEX boxEX = new BoxEX();
                boxEX.setCodeBox(resultSet.getInt(1));
                boxEX.setCodeBoxSpecific(resultSet.getInt(2));
                boxEX.setTitleBox(resultSet.getString(3));
                boxEX.setObservationBox(resultSet.getString(4));
                boxEX.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxEX);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de ex-alunos
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookEX bookEX = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_ex, titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_ex(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookEX = new BookEX();
                bookEX.setCodeBook(resultSet.getInt(1));
                bookEX.setCodeBookSpecific(resultSet.getInt(2));
                bookEX.setTitleBook(resultSet.getString(3));
                bookEX.setObservationBook(resultSet.getString(4));
                bookEX.setCodeBox(resultSet.getInt(5));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookEX;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de ex-alunos
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de ex-alunos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_ex ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookEX bookEX = new BookEX();
                bookEX.setCodeBook(resultSet.getInt(1));
                bookEX.setCodeBookSpecific(resultSet.getInt(2));
                bookEX.setTitleBook(resultSet.getString(3));
                bookEX.setObservationBook(resultSet.getString(4));
                bookEX.setCodeBox(resultSet.getInt(5));
                books.add(bookEX);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de ex-alunos que pertensam ao código da caixa de ex-alunos
     * fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de ex-alunos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_ex,"
                    + "titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_ex_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookEX bookEX = new BookEX();
                bookEX.setCodeBook(resultSet.getInt(1));
                bookEX.setCodeBookSpecific(resultSet.getInt(2));
                bookEX.setTitleBook(resultSet.getString(3));
                bookEX.setObservationBook(resultSet.getString(4));
                bookEX.setCodeBox(resultSet.getInt(5));
                books.add(bookEX);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de ex-alunos
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentEX documentEX = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_ex,titulo_documento, presente, observacao_documento, ano,codigo_livro FROM ver_documento_ex(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentEX = new DocumentEX();
                documentEX.setCodeDocument(resultSet.getInt(1));
                documentEX.setCodeDocumentEX(resultSet.getInt(2));
                documentEX.setTitleDocument(resultSet.getString(3));
                documentEX.setPresent(resultSet.getBoolean(4));
                documentEX.setObservationDocument(resultSet.getString(5));
                documentEX.setYear(resultSet.getInt(6));
                documentEX.setCodeBook(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentEX;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de ex-alunos
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de ex-alunos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_ex ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentEX documentEX = new DocumentEX();
                documentEX.setCodeDocument(resultSet.getInt(1));
                documentEX.setCodeDocumentEX(resultSet.getInt(2));
                documentEX.setYear(resultSet.getInt(3));
                documentEX.setTitleDocument(resultSet.getString(4));
                documentEX.setPresent(resultSet.getBoolean(5));
                documentEX.setObservationDocument(resultSet.getString(6));
                documentEX.setCodeBook(resultSet.getInt(7));
                documents.add(documentEX);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos EX!");
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
            //cria preparedStatement com visão de busca de documento de ex-alunos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_ex,ano, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_ex_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentEX documentEX = new DocumentEX();
                documentEX.setCodeDocument(resultSet.getInt(1));
                documentEX.setCodeDocumentEX(resultSet.getInt(2));
                documentEX.setYear(resultSet.getInt(3));
                documentEX.setTitleDocument(resultSet.getString(4));
                documentEX.setPresent(resultSet.getBoolean(5));
                documentEX.setObservationDocument(resultSet.getString(6));
                documentEX.setCodeBook(resultSet.getInt(7));
                documents.add(documentEX);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos EX!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param title título
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxEX, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_ex FROM busca_caixa_ex(?,?,?,?)");
            seacher.setInt(1, codeBoxEX);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxEX box = new BoxEX();
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
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookEX, String title, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_ex FROM busca_livro_ex(?,?,?)");
            seacher.setInt(1, codeBookEX);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookEX book = new BookEX();
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
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentEX, String title, int year, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento,codigo_documento_ex, ano FROM busca_documento_ex(?,?,?,?)");
            seacher.setInt(1, codeDocumentEX);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentEX document = new DocumentEX();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentEX(resultSet.getInt(6));
                document.setYear(resultSet.getInt(7));
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_ex (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_ex (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_ex (?,?)");
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
        BoxEX oldBoxEX = (BoxEX) oldBox;
        BoxEX newBoxEX = (BoxEX) newBox;
        if (oldBoxEX.getCodeBoxEX() != newBoxEX.getCodeBoxEX()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_ex(?);");
                verify.setInt(1, newBoxEX.getCodeBoxEX());
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
        BookEX oldBookEX = (BookEX) oldBook;
        BookEX newBookEX = (BookEX) newBook;
        if (oldBookEX.getCodeBookEX() != newBookEX.getCodeBookEX()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_ex(?,?);");
                verify.setInt(1, newBookEX.getCodeBox());
                verify.setInt(2, newBookEX.getCodeBookEX());
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
        DocumentEX oldDocumentEX = (DocumentEX) oldDocument;
        DocumentEX newDocumentEX = (DocumentEX) newDocument;
        if (oldDocumentEX.getCodeDocumentEX() != newDocumentEX.getCodeDocumentEX()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_ex(?,?);");
                verify.setInt(1, newDocumentEX.getCodeBook());
                verify.setInt(2, newDocumentEX.getCodeDocumentEX());
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
     * Lista cursos
     *
     * @return <code>List</code> com cursos
     */
    public List<String> listCourses() {
        List<String> courses = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_cursos_ex;");
            ResultSet resultset = list.executeQuery();

            while (resultset.next()) {
                courses.add(resultset.getString(1));
            }
            return courses;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível listar cursos!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível listar cursos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
            return null;
        }
    }//fim do método listCourses
}//fim da classe EXDAO

