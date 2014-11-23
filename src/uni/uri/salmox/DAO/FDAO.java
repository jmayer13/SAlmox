
/*-
 * Classname:             FDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:28:17
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
import uni.uri.salmox.model.BookF;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxF;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentF;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de formandos
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 *
 */
public class FDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public FDAO() {
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
     * @param box caixa de formandos
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de formandos
            BoxF boxF = (BoxF) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_f(?,?,?);");
            register.setInt(1, boxF.getCodeBoxF());
            register.setString(2, boxF.getResponsibleBox());
            register.setString(3, boxF.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de formandos
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        try {
            //percorre o iterator
            while (boxes.hasNext()) {
                //converte objeto para caixa de formandos
                BoxF boxF = (BoxF) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_f(?,?,?);");
                register.setInt(1, boxF.getCodeBoxF());
                register.setString(2, boxF.getResponsibleBox());
                register.setString(3, boxF.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de formandos no banco de dados
     *
     * @param book livro de caixa de formandos
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de formandos
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de formandos
            BookF bookF = (BookF) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_f(?,?,?,?,?,?);");
            register.setInt(1, bookF.getCodeBookF());
            register.setString(2, bookF.getTitleBook());
            register.setInt(3, bookF.getCodeBox());
            register.setString(4, bookF.getObservationBook());
            register.setInt(5, bookF.getYear());
            register.setInt(6, bookF.getSemester());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de formandos no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        try {
            //percorre iterator
            while (books.hasNext()) {
                //converte objeto para livro de formandos
                BookF bookF = (BookF) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_f(?,?,?,?,?,?);");
                register.setInt(1, bookF.getCodeBookF());
                register.setString(2, bookF.getTitleBook());
                register.setInt(3, bookF.getCodeBox());
                register.setString(4, bookF.getObservationBook());
                register.setInt(5, bookF.getYear());
                register.setInt(6, bookF.getSemester());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de formandos
     *
     * @param document documento de livro de caixa de formandos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos
            DocumentF documentF = (DocumentF) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_f(?,?,?,?);");
            register.setInt(1, documentF.getCodeDocumentF());
            register.setString(2, documentF.getTitleDocument());
            register.setInt(3, documentF.getCodeBook());
            register.setString(4, documentF.getObservationDocument());

            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de formandos no banco de
     * dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de formandos
                DocumentF documentF = (DocumentF) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de formandos
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_f(?,?,?,?);");
                register.setInt(1, documentF.getCodeDocumentF());
                register.setString(2, documentF.getTitleDocument());
                register.setInt(3, documentF.getCodeBook());
                register.setString(4, documentF.getObservationDocument());
                //retorna true se código de livro de caixa de formandos já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de formandos fornecida como parãmetro
     *
     * @param box caixa de formandos
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de formandos
            BoxF boxF = (BoxF) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_f(?,?,?,?);");
            changer.setInt(1, boxF.getCodeBox());
            changer.setInt(2, boxF.getCodeBoxF());
            changer.setString(3, boxF.getResponsibleBox());
            changer.setString(4, boxF.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de formandos que foi fornecida como
     * parãmetro
     *
     * @param book livro de caixa de formandos
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos
            BookF bookF = (BookF) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_f(?,?,?,?,?,?,?);");
            changer.setInt(1, bookF.getCodeBook());
            changer.setInt(2, bookF.getCodeBookF());
            changer.setString(3, bookF.getTitleBook());
            changer.setInt(4, bookF.getCodeBox());
            changer.setString(5, bookF.getObservationBook());
            changer.setInt(6, bookF.getYear());
            changer.setInt(7, bookF.getSemester());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de formandos que foi
     * fornecido como parâmetro
     *
     * @param document documento de livro de caixa de formandos
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de formandos
            DocumentF documentF = (DocumentF) document;
            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_f(?,?,?,?,?);");
            changer.setInt(1, documentF.getCodeDocument());
            changer.setInt(2, documentF.getCodeDocumentF());
            changer.setString(3, documentF.getTitleDocument());
            changer.setInt(4, documentF.getCodeBook());
            changer.setString(5, documentF.getObservationDocument());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de formandos com código fornecido como parámetro
     *
     * @param codeBox código do caixa de formandos
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxF boxF = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_f FROM ver_caixa_f(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxF = new BoxF();
                boxF.setCodeBox(resultSet.getInt(1));
                boxF.setResponsibleBox(resultSet.getString(2));
                boxF.setObservationBox(resultSet.getString(3));
                boxF.setTitleBox(resultSet.getString(4));
                boxF.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxF;
    }//fim do método searchBox

    /**
     * Busca caixas de formandos
     *
     * @return <code>Iterator</code> coleção de caixas de formandos
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_f ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxF boxF = new BoxF();
                boxF.setCodeBox(resultSet.getInt(1));
                boxF.setCodeBoxSpecific(resultSet.getInt(2));
                boxF.setTitleBox(resultSet.getString(3));
                boxF.setObservationBox(resultSet.getString(4));
                boxF.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxF);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de formandos
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookF bookF = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_f, titulo_livro, observacao_livro, ano, semestre, codigo_caixa FROM ver_livro_f(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookF = new BookF();
                bookF.setCodeBook(resultSet.getInt(1));
                bookF.setCodeBookSpecific(resultSet.getInt(2));
                bookF.setTitleBook(resultSet.getString(3));
                bookF.setObservationBook(resultSet.getString(4));
                bookF.setYear(resultSet.getInt(5));
                bookF.setSemester(resultSet.getInt(6));
                bookF.setCodeBox(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookF;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de formandos
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_f ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookF bookF = new BookF();
                bookF.setCodeBook(resultSet.getInt(1));
                bookF.setCodeBookSpecific(resultSet.getInt(2));
                bookF.setYear(resultSet.getInt(3));
                bookF.setSemester(resultSet.getInt(4));
                bookF.setTitleBook(resultSet.getString(5));
                bookF.setObservationBook(resultSet.getString(6));
                bookF.setCodeBox(resultSet.getInt(7));
                books.add(bookF);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de formandos que pertensam ao código da caixa de formandos
     * fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_f,"
                    + "ano,semestre,titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_f_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookF bookF = new BookF();
                bookF.setCodeBook(resultSet.getInt(1));
                bookF.setCodeBookSpecific(resultSet.getInt(2));
                bookF.setYear(resultSet.getInt(3));
                bookF.setSemester(resultSet.getInt(4));
                bookF.setTitleBook(resultSet.getString(5));
                bookF.setObservationBook(resultSet.getString(6));
                bookF.setCodeBox(resultSet.getInt(7));
                books.add(bookF);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros F!");
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
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentF documentF = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_f,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_f(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentF = new DocumentF();
                documentF.setCodeDocument(resultSet.getInt(1));
                documentF.setCodeDocumentF(resultSet.getInt(2));
                documentF.setTitleDocument(resultSet.getString(3));
                documentF.setPresent(resultSet.getBoolean(4));
                documentF.setObservationDocument(resultSet.getString(5));
                documentF.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentF;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de formandos
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_f ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentF documentF = new DocumentF();
                documentF.setCodeDocument(resultSet.getInt(1));
                documentF.setCodeDocumentF(resultSet.getInt(2));
                documentF.setTitleDocument(resultSet.getString(3));
                documentF.setPresent(resultSet.getBoolean(4));
                documentF.setObservationDocument(resultSet.getString(5));
                documentF.setCodeBook(resultSet.getInt(6));
                documents.add(documentF);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos F!");
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
            //cria preparedStatement com visão de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_f, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_f_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentF documentF = new DocumentF();
                documentF.setCodeDocument(resultSet.getInt(1));
                documentF.setCodeDocumentF(resultSet.getInt(2));
                documentF.setTitleDocument(resultSet.getString(3));
                documentF.setPresent(resultSet.getBoolean(4));
                documentF.setObservationDocument(resultSet.getString(5));
                documentF.setCodeBook(resultSet.getInt(6));

                documents.add(documentF);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos F!");
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
    public Iterator searchBox(int codeBoxF, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_f FROM busca_caixa_f(?,?,?,?)");
            seacher.setInt(1, codeBoxF);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxF box = new BoxF();
                box.setCodeBox(resultSet.getInt(1));
                box.setResponsibleBox(resultSet.getString(2));
                box.setObservationBox(resultSet.getString(3));
                box.setTitleBox(resultSet.getString(4));
                box.setCodeBoxF(resultSet.getInt(5));
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
    public Iterator searchBook(int codeBookF, String title, int year, int semester, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_f, ano, semestre FROM busca_livro_f(?,?,?,?,?)");
            seacher.setInt(1, codeBookF);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setInt(4, semester);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookF book = new BookF();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setYear(resultSet.getInt(6));
                book.setSemester(resultSet.getInt(7));
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
    public Iterator searchDocument(int codeDocumentF, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_f FROM busca_documento_f(?,?,?)");
            seacher.setInt(1, codeDocumentF);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentF document = new DocumentF();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentF(resultSet.getInt(6));
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
     * @param newCode novo código especifico
     */
    public void changeCodeBox(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_f (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_f (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_f (?,?)");
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
        BoxF oldBoxF = (BoxF) oldBox;
        BoxF newBoxF = (BoxF) newBox;
        if (oldBoxF.getCodeBoxF() != newBoxF.getCodeBoxF()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_f(?);");
                verify.setInt(1, newBoxF.getCodeBoxF());
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
        BookF oldBookF = (BookF) oldBook;
        BookF newBookF = (BookF) newBook;
        if (oldBookF.getCodeBookF() != newBookF.getCodeBookF()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_f(?,?);");
                verify.setInt(1, newBookF.getCodeBox());
                verify.setInt(2, newBookF.getCodeBookF());
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
        DocumentF oldDocumentF = (DocumentF) oldDocument;
        DocumentF newDocumentF = (DocumentF) newDocument;
        if (oldDocumentF.getCodeDocumentF() != newDocumentF.getCodeDocumentF()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_f(?,?);");
                verify.setInt(1, newDocumentF.getCodeBook());
                verify.setInt(2, newDocumentF.getCodeDocumentF());
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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_cursos_f;");
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
}//fim da classe FDAO

