
/*-
 * Classname:             FPDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:29:12
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
import uni.uri.salmox.model.BookFP;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxFP;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentFP;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de formandos de pós-graduação
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class FPDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public FPDAO() {
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
     * @param box caixa de formandos pós-graduação
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de formandos pós-graduação
            BoxFP boxFP = (BoxFP) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos pós-graduação
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_fp(?,?,?);");
            register.setInt(1, boxFP.getCodeBoxFP());
            register.setString(2, boxFP.getResponsibleBox());
            register.setString(3, boxFP.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de formandos pós-graduação
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de formandos pós-graduação
                BoxFP boxFP = (BoxFP) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_fp(?,?,?);");
                register.setInt(1, boxFP.getCodeBoxFP());
                register.setString(2, boxFP.getResponsibleBox());
                register.setString(3, boxFP.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de formandos pós-graduação no banco de dados
     *
     * @param book livro de caixa de formandos pós-graduação
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de formandos pós-graduação
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de formandos pós-graduação
            BookFP bookFP = (BookFP) book;
            PreparedStatement register = connection.prepareStatement("SELECT *  FROM cadastra_livro_fp(?,?,?,?,?,?);");
            register.setInt(1, bookFP.getCodeBookFP());
            register.setString(2, bookFP.getTitleBook());
            register.setInt(3, bookFP.getCodeBox());
            register.setString(4, bookFP.getObservationBook());
            register.setString(5, bookFP.getYear());
            register.setString(6, bookFP.getTypeCourse());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de formandos pós-graduação no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {
            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de formandos pós-graduação
                BookFP bookFP = (BookFP) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_fp(?,?,?,?,?,?);");
                register.setInt(1, bookFP.getCodeBookFP());
                register.setString(2, bookFP.getTitleBook());
                register.setInt(3, bookFP.getCodeBox());
                register.setString(4, bookFP.getObservationBook());
                register.setString(5, bookFP.getYear());
                register.setString(6, bookFP.getTypeCourse());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de formandos pós-graduação
     *
     * @param document documento de livro de caixa de formandos pós-graduação
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos pós-graduação
            DocumentFP documentFP = (DocumentFP) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_fp(?,?,?,?,?);");
            register.setInt(1, documentFP.getCodeDocumentFP());
            register.setString(2, documentFP.getTitleDocument());
            register.setInt(3, documentFP.getCodeBook());
            register.setString(4, documentFP.getObservationDocument());
            register.setString(5, documentFP.getStatusStudent());

            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de formandos
     * pós-graduação no banco de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de formandos pós-graduação
                DocumentFP documentFP = (DocumentFP) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de formandos pós-graduação
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_fp(?,?,?,?,?);");
                register.setInt(1, documentFP.getCodeDocumentFP());
                register.setString(2, documentFP.getTitleDocument());
                register.setInt(3, documentFP.getCodeBook());
                register.setString(4, documentFP.getObservationDocument());
                register.setString(5, documentFP.getStatusStudent());

                //retorna true se código de livro de caixa de formandos pós-graduação já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de formandos pós-graduação fornecida como
     * parãmetro
     *
     * @param box caixa de formandos pós-graduação
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de formandos pós-graduação
            BoxFP boxFP = (BoxFP) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos pós-graduação
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_fp(?,?,?,?);");
            changer.setInt(1, boxFP.getCodeBox());
            changer.setInt(2, boxFP.getCodeBoxFP());
            changer.setString(3, boxFP.getResponsibleBox());
            changer.setString(4, boxFP.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de formandos pós-graduação que foi
     * fornecida como parãmetro
     *
     * @param book livro de caixa de formandos pós-graduação
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos pós-graduação
            BookFP bookFP = (BookFP) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_fp(?,?,?,?,?,?,?);");
            changer.setInt(1, bookFP.getCodeBook());
            changer.setInt(2, bookFP.getCodeBookFP());
            changer.setString(3, bookFP.getTitleBook());
            changer.setInt(4, bookFP.getCodeBox());
            changer.setString(5, bookFP.getObservationBook());
            changer.setString(6, bookFP.getYear());
            changer.setString(7, bookFP.getTypeCourse());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de formandos pós-graduação
     * que foi fornecido como parâmetro
     *
     * @param document documento de livro de caixa de formandos pós-graduação
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de formandos pós-graduação
            DocumentFP documentFP = (DocumentFP) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_fp(?,?,?,?,?,?);");
            changer.setInt(1, documentFP.getCodeDocument());
            changer.setInt(2, documentFP.getCodeDocumentFP());
            changer.setString(3, documentFP.getTitleDocument());
            changer.setInt(4, documentFP.getCodeBook());
            changer.setString(5, documentFP.getObservationDocument());
            changer.setString(6, documentFP.getStatusStudent());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de formandos pós-graduação com código fornecido como
     * parámetro
     *
     * @param codeBox código do caixa de formandos pós-graduação
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxFP boxFP = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_fp FROM ver_caixa_fp(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxFP = new BoxFP();
                boxFP.setCodeBox(resultSet.getInt(1));
                boxFP.setResponsibleBox(resultSet.getString(2));
                boxFP.setObservationBox(resultSet.getString(3));
                boxFP.setTitleBox(resultSet.getString(4));
                boxFP.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxFP;
    }//fim do método searchBox

    /**
     * Busca caixas de formandos pós-graduação
     *
     * @return <code>Iterator</code> coleção de caixas de formandos
     * pós-graduação
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos pós-graduação
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_fp ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxFP boxFP = new BoxFP();
                boxFP.setCodeBox(resultSet.getInt(1));
                boxFP.setCodeBoxSpecific(resultSet.getInt(2));
                boxFP.setTitleBox(resultSet.getString(3));
                boxFP.setObservationBox(resultSet.getString(4));
                boxFP.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxFP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de formandos pós-graduação
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookFP bookFP = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_fp, titulo_livro, observacao_livro, tipo_curso, ano,codigo_caixa FROM ver_livro_fp(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookFP = new BookFP();
                bookFP.setCodeBook(resultSet.getInt(1));
                bookFP.setCodeBookSpecific(resultSet.getInt(2));
                bookFP.setTitleBook(resultSet.getString(3));
                bookFP.setObservationBook(resultSet.getString(4));
                bookFP.setTypeCourse(resultSet.getString(5));
                bookFP.setYear(resultSet.getString(6));
                bookFP.setCodeBox(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookFP;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de formandos pós-graduação
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos pós-graduação
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_fp ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookFP bookFP = new BookFP();
                bookFP.setCodeBook(resultSet.getInt(1));
                bookFP.setCodeBookSpecific(resultSet.getInt(2));
                bookFP.setTitleBook(resultSet.getString(3));
                bookFP.setYear(resultSet.getString(4));
                bookFP.setTypeCourse(resultSet.getString(5));
                bookFP.setObservationBook(resultSet.getString(6));
                bookFP.setCodeBox(resultSet.getInt(7));
                books.add(bookFP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de formandos pós-graduação que pertensam ao código da caixa
     * de formandos pós-graduação fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos pós-graduação
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_fp,"
                    + "titulo_livro, ano, tipo_curso, observacao_livro,codigo_caixa FROM ver_livro_fp_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookFP bookFP = new BookFP();
                bookFP.setCodeBook(resultSet.getInt(1));
                bookFP.setCodeBookSpecific(resultSet.getInt(2));
                bookFP.setTitleBook(resultSet.getString(3));
                bookFP.setYear(resultSet.getString(4));
                bookFP.setTypeCourse(resultSet.getString(5));
                bookFP.setObservationBook(resultSet.getString(6));
                bookFP.setCodeBox(resultSet.getInt(7));
                books.add(bookFP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de formandos
     * pós-graduação
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentFP documentFP = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_fp,titulo_documento, presente, observacao_documento, status_aluno,codigo_livro FROM ver_documento_fp(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentFP = new DocumentFP();
                documentFP.setCodeDocument(resultSet.getInt(1));
                documentFP.setCodeDocumentFP(resultSet.getInt(2));
                documentFP.setTitleDocument(resultSet.getString(3));
                documentFP.setPresent(resultSet.getBoolean(4));
                documentFP.setObservationDocument(resultSet.getString(5));
                documentFP.setStatusStudent(resultSet.getString(6));
                documentFP.setCodeBook(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentFP;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de formandos pós-graduação
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos pós-graduação
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_fp ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentFP documentFP = new DocumentFP();
                documentFP.setCodeDocument(resultSet.getInt(1));
                documentFP.setCodeDocumentFP(resultSet.getInt(2));
                documentFP.setTitleDocument(resultSet.getString(3));
                documentFP.setStatusStudent(resultSet.getString(4));
                documentFP.setPresent(resultSet.getBoolean(5));
                documentFP.setObservationDocument(resultSet.getString(6));
                documentFP.setCodeBook(resultSet.getInt(7));

                documents.add(documentFP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos FP!");
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
            //cria preparedStatement com visão de busca de documento de formandos pós-graduação
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_fp, titulo_documento, status_aluno, presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_fp_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentFP documentFP = new DocumentFP();
                documentFP.setCodeDocument(resultSet.getInt(1));
                documentFP.setCodeDocumentFP(resultSet.getInt(2));
                documentFP.setTitleDocument(resultSet.getString(3));
                documentFP.setStatusStudent(resultSet.getString(4));
                documentFP.setPresent(resultSet.getBoolean(5));
                documentFP.setObservationDocument(resultSet.getString(6));
                documentFP.setCodeBook(resultSet.getInt(7));

                documents.add(documentFP);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos FP!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBoxFP cóodigo da caixa <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxFP, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_fp FROM busca_caixa_fp(?,?,?,?)");
            seacher.setInt(1, codeBoxFP);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxFP box = new BoxFP();
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
     * @param codeBookFP código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param typeOfCourse <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookFP, String title, String typeOfCourse, int year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_fp, tipo_curso, ano FROM busca_livro_fp(?,?,?,?,?)");
            seacher.setInt(1, codeBookFP);
            seacher.setString(2, title);
            seacher.setString(3, typeOfCourse);
            seacher.setInt(4, year);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookFP book = new BookFP();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setTypeCourse(resultSet.getString(6));
                book.setYear(resultSet.getString(7));
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
     * @param codeDocumentFP código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param studentStatus estado do estudante <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentFP, String title, String studentStatus, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_fp, "
                    + "status_aluno FROM busca_documento_FP(?,?,?,?)");
            seacher.setInt(1, codeDocumentFP);
            seacher.setString(2, title);
            seacher.setString(3, studentStatus);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentFP document = new DocumentFP();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentFP(resultSet.getInt(6));
                document.setStatusStudent(resultSet.getString(7));
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_fp (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_fp (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_fp (?,?)");
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
        BoxFP oldBoxFP = (BoxFP) oldBox;
        BoxFP newBoxFP = (BoxFP) newBox;
        if (oldBoxFP.getCodeBoxFP() != newBoxFP.getCodeBoxFP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_fp(?);");
                verify.setInt(1, newBoxFP.getCodeBoxFP());
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
        BookFP oldBookFP = (BookFP) oldBook;
        BookFP newBookFP = (BookFP) newBook;
        if (oldBookFP.getCodeBookFP() != newBookFP.getCodeBookFP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_fp(?,?);");
                verify.setInt(1, newBookFP.getCodeBox());
                verify.setInt(2, newBookFP.getCodeBookFP());
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
        DocumentFP oldDocumentFP = (DocumentFP) oldDocument;
        DocumentFP newDocumentFP = (DocumentFP) newDocument;
        if (oldDocumentFP.getCodeDocumentFP() != newDocumentFP.getCodeDocumentFP()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_fp(?,?);");
                verify.setInt(1, newDocumentFP.getCodeBook());
                verify.setInt(2, newDocumentFP.getCodeDocumentFP());
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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_cursos_fp;");
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

    /**
     * Lista cursos
     *
     * @return <code>List</code> com cursos
     */
    public List<String> listTypeCourses() {
        List<String> courses = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_tipo_cursos_fp;");
            ResultSet resultset = list.executeQuery();

            while (resultset.next()) {
                courses.add(resultset.getString(1));
            }
            return courses;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível listar tipos cursos!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível listar tipos de cursos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
            return null;
        }
    }//fim do método listCourses
}//fim da classe FPDAO

