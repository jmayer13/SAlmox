
/*-
 * Classname:             DPDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:29:42
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
import uni.uri.salmox.model.BookDP;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxDP;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de departamento pessoal
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DPDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public DPDAO() {
        takeConnection();
    }//fim do método construtor sem parâmetros

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
     * @param box caixa de departamento pessoal
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de departamento pessoal
            BoxDP boxDP = (BoxDP) box;
            //cria PreparedStatement com função de cadastro de caixa de departamento pessoal
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_dp(?,?,?);");
            register.setInt(1, boxDP.getCodeBoxDP());
            register.setString(2, boxDP.getResponsibleBox());
            register.setString(3, boxDP.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de departamento pessoal
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de departamento pessoal
                BoxDP boxDP = (BoxDP) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_dp(?,?,?);");
                register.setInt(1, boxDP.getCodeBoxDP());
                register.setString(2, boxDP.getResponsibleBox());
                register.setString(3, boxDP.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de departamento pessoal no banco de dados
     *
     * @param book livro de caixa de departamento pessoal
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de departamento pessoal
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de departamento pessoal
            BookDP bookDP = (BookDP) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_dp(?,?,?,?,?);");
            register.setInt(1, bookDP.getCodeBookDP());
            register.setString(2, bookDP.getTitleBook());
            register.setInt(3, bookDP.getCodeBox());
            register.setString(4, bookDP.getObservationBook());
            register.setInt(5, bookDP.getYear());
            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de departamento pessoal no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {
            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de departamento pessoal
                BookDP bookDP = (BookDP) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_dp(?,?,?,?,?);");
                register.setInt(1, bookDP.getCodeBookDP());
                register.setString(2, bookDP.getTitleBook());
                register.setInt(3, bookDP.getCodeBox());
                register.setString(4, bookDP.getObservationBook());
                register.setInt(5, bookDP.getYear());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de departamento pessoal
     *
     * @param document documento de livro de caixa de departamento pessoal
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de departamento pessoal
            DocumentDP documentDP = (DocumentDP) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_dp(?,?,?,?);");
            register.setInt(1, documentDP.getCodeDocumentDP());
            register.setString(2, documentDP.getTitleDocument());
            register.setInt(3, documentDP.getCodeBook());
            register.setString(4, documentDP.getObservationDocument());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de departamento pessoal
     * no banco de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {

        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de departamento pessoal
                DocumentDP documentDP = (DocumentDP) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de departamento pessoal
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_dp(?,?,?,?);");
                register.setInt(1, documentDP.getCodeDocumentDP());
                register.setString(2, documentDP.getTitleDocument());
                register.setInt(3, documentDP.getCodeBook());
                register.setString(4, documentDP.getObservationDocument());
                //retorna true se código de livro de caixa de departamento pessoal já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de departamento pessoal fornecida como parãmetro
     *
     * @param box caixa de departamento pessoal
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de departamento pessoal
            BoxDP boxDP = (BoxDP) box;

            //cria PreparedStatement com função de ediçao de caixa de departamento pessoal
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_dp(?,?,?,?);");
            changer.setInt(1, boxDP.getCodeBox());
            changer.setInt(2, boxDP.getCodeBoxDP());
            changer.setString(3, boxDP.getResponsibleBox());
            changer.setString(4, boxDP.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de departamento pessoal que foi
     * fornecida como parãmetro
     *
     * @param book livro de caixa de departamento pessoal
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de departamento pessoal
            BookDP bookDP = (BookDP) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_dp(?,?,?,?,?,?);");
            changer.setInt(1, bookDP.getCodeBook());
            changer.setInt(2, bookDP.getCodeBookDP());
            changer.setString(3, bookDP.getTitleBook());
            changer.setInt(4, bookDP.getCodeBox());
            changer.setString(5, bookDP.getObservationBook());
            changer.setInt(6, bookDP.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de departamento pessoal que
     * foi fornecido como parâmetro
     *
     * @param document documento de livro de caixa de departamento pessoal
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de departamento pessoal
            DocumentDP documentDP = (DocumentDP) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_dp(?,?,?,?,?);");
            changer.setInt(1, documentDP.getCodeDocument());
            changer.setInt(2, documentDP.getCodeDocumentDP());
            changer.setString(3, documentDP.getTitleDocument());
            changer.setInt(4, documentDP.getCodeBook());
            changer.setString(5, documentDP.getObservationDocument());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método de editDocument

    /**
     * Busca caixa de departamento pessoal com código fornecido como parámetro
     *
     * @param codeBox código do caixa de departamento pessoal
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxDP boxDP = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dp FROM ver_caixa_dp(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxDP = new BoxDP();
                boxDP.setCodeBox(resultSet.getInt(1));
                boxDP.setResponsibleBox(resultSet.getString(2));
                boxDP.setObservationBox(resultSet.getString(3));
                boxDP.setTitleBox(resultSet.getString(4));
                boxDP.setCodeBoxSpecific(resultSet.getInt(5));

            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxDP;
    }//fim do método searchBox

    /**
     * Busca caixas de departamento pessoal
     *
     * @return <code>Iterator</code> coleção de caixas de departamento pessoal
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de departamento pessoal
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_dp ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxDP boxDP = new BoxDP();
                boxDP.setCodeBox(resultSet.getInt(1));
                boxDP.setCodeBoxSpecific(resultSet.getInt(2));
                boxDP.setTitleBox(resultSet.getString(3));
                boxDP.setObservationBox(resultSet.getString(4));
                boxDP.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxDP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de departamento pessoal
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookDP bookDP = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dp, titulo_livro, observacao_livro, ano,codigo_caixa FROM ver_livro_dp(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookDP = new BookDP();
                bookDP.setCodeBook(resultSet.getInt(1));
                bookDP.setCodeBookSpecific(resultSet.getInt(2));
                bookDP.setTitleBook(resultSet.getString(3));
                bookDP.setObservationBook(resultSet.getString(4));
                bookDP.setYear(resultSet.getInt(5));
                bookDP.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookDP;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de departamento pessoal
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de departamento pessoal
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_dp ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookDP bookDP = new BookDP();
                bookDP.setCodeBook(resultSet.getInt(1));
                bookDP.setCodeBookSpecific(resultSet.getInt(2));
                bookDP.setTitleBook(resultSet.getString(3));
                bookDP.setYear(resultSet.getInt(4));
                bookDP.setObservationBook(resultSet.getString(5));
                bookDP.setCodeBox(resultSet.getInt(6));
                books.add(bookDP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de departamento pessoal que pertensam ao código da caixa de
     * departamento pessoal fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de departamento pessoal
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dp,"
                    + "titulo_livro, ano,  observacao_livro,codigo_caixa FROM ver_livro_dp_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookDP bookDP = new BookDP();
                bookDP.setCodeBook(resultSet.getInt(1));
                bookDP.setCodeBookSpecific(resultSet.getInt(2));
                bookDP.setTitleBook(resultSet.getString(3));
                bookDP.setYear(resultSet.getInt(4));
                bookDP.setObservationBook(resultSet.getString(5));
                bookDP.setCodeBox(resultSet.getInt(6));
                books.add(bookDP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros DP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de departamento
     * pessoal
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentDP documentDP = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_dp,titulo_documento, presente, observacao_documento,codigo_livro FROM ver_documento_dp(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentDP = new DocumentDP();
                documentDP.setCodeDocument(resultSet.getInt(1));
                documentDP.setCodeDocumentDP(resultSet.getInt(2));
                documentDP.setTitleDocument(resultSet.getString(3));
                documentDP.setPresent(resultSet.getBoolean(4));
                documentDP.setObservationDocument(resultSet.getString(5));
                documentDP.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentDP;
    }

    /**
     * Busca documentos de caixa de departamento pessoal
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de departamento pessoal
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_dp ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDP documentDP = new DocumentDP();
                documentDP.setCodeDocument(resultSet.getInt(1));
                documentDP.setCodeDocumentDP(resultSet.getInt(2));
                documentDP.setTitleDocument(resultSet.getString(3));
                documentDP.setPresent(resultSet.getBoolean(4));
                documentDP.setObservationDocument(resultSet.getString(5));
                documentDP.setCodeBook(resultSet.getInt(6));
                documents.add(documentDP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos DP!");
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
            //cria preparedStatement com visão de busca de documento de departamento pessoal
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_dp, titulo_documento ,presente, observacao_documento, codigo_livro "
                    + " FROM ver_documento_dp_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDP documentDP = new DocumentDP();
                documentDP.setCodeDocument(resultSet.getInt(1));
                documentDP.setCodeDocumentDP(resultSet.getInt(2));
                documentDP.setTitleDocument(resultSet.getString(3));
                documentDP.setPresent(resultSet.getBoolean(4));
                documentDP.setObservationDocument(resultSet.getString(5));
                documentDP.setCodeBook(resultSet.getInt(6));
                documents.add(documentDP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos DP!");
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
    public Iterator searchBox(int codeBoxDP, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dp FROM busca_caixa_dp(?,?,?,?)");
            seacher.setInt(1, codeBoxDP);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxDP box = new BoxDP();
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
    public Iterator searchBook(int codeBookDP, String title, int year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_dp, ano  FROM busca_livro_dp(?,?,?,?)");
            seacher.setInt(1, codeBookDP);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookDP book = new BookDP();
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
    public Iterator searchDocument(int codeDocumentDP, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_dp FROM busca_documento_dp(?,?,?)");
            seacher.setInt(1, codeDocumentDP);
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_dp (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_dp (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_dp (?,?)");
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
        BoxDP oldBoxDP = (BoxDP) oldBox;
        BoxDP newBoxDP = (BoxDP) newBox;
        if (oldBoxDP.getCodeBoxDP() != newBoxDP.getCodeBoxDP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_dp(?);");
                verify.setInt(1, newBoxDP.getCodeBoxDP());
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
        BookDP oldBookDP = (BookDP) oldBook;
        BookDP newBookDP = (BookDP) newBook;
        if (oldBookDP.getCodeBookDP() != newBookDP.getCodeBookDP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_dp(?,?);");
                verify.setInt(1, newBookDP.getCodeBox());
                verify.setInt(2, newBookDP.getCodeBookDP());
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
        DocumentDP oldDocumentDP = (DocumentDP) oldDocument;
        DocumentDP newDocumentDP = (DocumentDP) newDocument;
        if (oldDocumentDP.getCodeDocumentDP() != newDocumentDP.getCodeDocumentDP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_dp(?,?);");
                verify.setInt(1, newDocumentDP.getCodeBook());
                verify.setInt(2, newDocumentDP.getCodeDocumentDP());
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
}//fim da classe DPDAO

