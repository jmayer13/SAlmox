
/*-
 * Classname:             AEDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:28:45
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
import uni.uri.salmox.model.BookAE;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxAE;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentAE;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de atas e exames
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class AEDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public AEDAO() {
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
     * Registra a caixa de atas e exames no banco de dados
     *
     * @param box caixa de atas e exames
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de atas e exames
            BoxAE boxAE = (BoxAE) box;
            //cria PreparedStatement com função de cadastro de caixa de atas e exames
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_ae(?,?,?);");
            register.setInt(1, boxAE.getCodeBoxAE());
            register.setString(2, boxAE.getResponsibleBox());
            register.setString(3, boxAE.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de atas e exames
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        //percorre o iterator
        try {
            while (boxes.hasNext()) {
                //converte objeto para caixa de atas e exames
                BoxAE boxAE = (BoxAE) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_ae(?,?,?);");
                register.setInt(1, boxAE.getCodeBoxAE());
                register.setString(2, boxAE.getResponsibleBox());
                register.setString(3, boxAE.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de atas e exames no banco de dados
     *
     * @param book livro de caixa de atas e exames
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de atas e exames
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de atas e exames
            BookAE bookAE = (BookAE) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_ae(?,?,?,?,?,?);");
            register.setInt(1, bookAE.getCodeBookAE());
            register.setString(2, bookAE.getTitleBook());
            register.setInt(3, bookAE.getCodeBox());
            register.setString(4, bookAE.getObservationBook());
            register.setInt(5, bookAE.getCodeCourseYear());
            register.setInt(6, bookAE.getYear());
            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de atas e exames no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        //percorre iterator
        try {
            while (books.hasNext()) {
                //converte objeto para livro de atas e exames
                BookAE bookAE = (BookAE) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_ae(?,?,?,?,?,?);");
                register.setInt(1, bookAE.getCodeBookAE());
                register.setString(2, bookAE.getTitleBook());
                register.setInt(3, bookAE.getCodeBox());
                register.setString(4, bookAE.getObservationBook());
                register.setInt(5, bookAE.getCodeCourseYear());
                register.setInt(6, bookAE.getYear());
                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de atas e exames
     *
     * @param document documento de livro de caixa de atas e exames
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de atas e exames
            DocumentAE documentAE = (DocumentAE) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_ae(?,?,?,?,?,?);");
            register.setInt(1, documentAE.getCodeDocumentAE());
            register.setString(2, documentAE.getTitleDocument());
            register.setInt(3, documentAE.getCodeBook());
            register.setString(4, documentAE.getObservationDocument());
            register.setString(5, documentAE.getAnnex());
            register.setDate(6, documentAE.getDateExam());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de atas e exames no
     * banco de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de atas e exames
                DocumentAE documentAE = (DocumentAE) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de atas e exames
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_ae(?,?,?,?,?,?);");
                register.setInt(1, documentAE.getCodeDocumentAE());
                register.setString(2, documentAE.getTitleDocument());
                register.setInt(3, documentAE.getCodeBook());
                register.setString(4, documentAE.getObservationDocument());
                register.setString(5, documentAE.getAnnex());
                register.setDate(6, documentAE.getDateExam());
                //retorna true se código de livro de caixa de atas e exames já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de atas e exames fornecida como parãmetro
     *
     * @param box caixa de atas e exames
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de atas e exames
            BoxAE boxAE = (BoxAE) box;

            //cria PreparedStatement com função de ediçao de caixa de atas e exames
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_ae(?,?,?,?);");
            changer.setInt(1, boxAE.getCodeBox());
            changer.setInt(2, boxAE.getCodeBoxAE());
            changer.setString(3, boxAE.getResponsibleBox());
            changer.setString(4, boxAE.getObservationBox());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar a caixa AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de atas e exames que foi fornecida como
     * parãmetro
     *
     * @param book livro de caixa de atas e exames
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de atas e exames
            BookAE bookAE = (BookAE) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_ae(?,?,?,?,?,?,?);");
            changer.setInt(1, bookAE.getCodeBook());
            changer.setInt(2, bookAE.getCodeBookAE());
            changer.setString(3, bookAE.getTitleBook());
            changer.setInt(4, bookAE.getCodeBox());
            changer.setString(5, bookAE.getObservationBook());
            changer.setInt(6, bookAE.getCodeCourseYear());
            changer.setInt(7, bookAE.getYear());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar o livro AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de atas e exames que foi
     * fornecido como parâmetro
     *
     * @param document documento de livro de caixa de atas e exames
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de atas e exames
            DocumentAE documentAE = (DocumentAE) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_ae(?,?,?,?,?,?,?);");
            changer.setInt(1, documentAE.getCodeDocument());
            changer.setInt(2, documentAE.getCodeDocumentAE());
            changer.setString(3, documentAE.getTitleDocument());
            changer.setInt(4, documentAE.getCodeBook());
            changer.setString(5, documentAE.getObservationDocument());
            changer.setString(6, documentAE.getAnnex());
            changer.setDate(7, documentAE.getDateExam());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método de editDocument

    /**
     * Busca caixa de atas e exames com código fornecido como parámetro
     *
     * @param codeBox código do caixa de atas e exames
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxAE boxAE = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_ae FROM ver_caixa_ae(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxAE = new BoxAE();
                boxAE.setCodeBox(resultSet.getInt(1));
                boxAE.setResponsibleBox(resultSet.getString(2));
                boxAE.setObservationBox(resultSet.getString(3));
                boxAE.setTitleBox(resultSet.getString(4));
                boxAE.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxAE;
    }//fim do método searchBox

    /**
     * Busca caixas de atas e exames
     *
     * @return <code>Iterator</code> coleção de caixas de atas e exames
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de atas e exames
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_ae ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxAE boxAE = new BoxAE();
                boxAE.setCodeBox(resultSet.getInt(1));
                boxAE.setCodeBoxSpecific(resultSet.getInt(2));
                boxAE.setTitleBox(resultSet.getString(3));
                boxAE.setObservationBox(resultSet.getString(4));
                boxAE.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de atas e exames
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookAE bookAE = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_ae, titulo_livro, ano,codigo_curso_ano, observacao_livro,codigo_caixa FROM ver_livro_ae(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookAE = new BookAE();
                bookAE.setCodeBook(resultSet.getInt(1));
                bookAE.setCodeBookSpecific(resultSet.getInt(2));
                bookAE.setTitleBook(resultSet.getString(3));
                bookAE.setYear(resultSet.getInt(4));
                bookAE.setCodeCourseYear(resultSet.getInt(5));
                bookAE.setObservationBook(resultSet.getString(6));
                bookAE.setCodeBox(resultSet.getInt(7));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookAE;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de atas e exames
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de atas e exames
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_ae ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookAE bookAE = new BookAE();
                bookAE.setCodeBook(resultSet.getInt(1));
                bookAE.setCodeBookSpecific(resultSet.getInt(2));
                bookAE.setCodeCourseYear(resultSet.getInt(3));
                bookAE.setYear(resultSet.getInt(4));
                bookAE.setTitleBook(resultSet.getString(5));
                bookAE.setObservationBook(resultSet.getString(6));
                bookAE.setCodeBox(resultSet.getInt(7));
                books.add(bookAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de atas e exames que pertensam ao código da caixa de atas e
     * exames fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de atas e exames
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro,"
                    + "codigo_livro_ae, codigo_curso_ano, ano, titulo_livro, observacao_livro, codigo_caixa FROM ver_livro_ae_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookAE bookAE = new BookAE();
                bookAE.setCodeBook(resultSet.getInt(1));
                bookAE.setCodeBookSpecific(resultSet.getInt(2));
                bookAE.setCodeCourseYear(resultSet.getInt(3));
                bookAE.setYear(resultSet.getInt(4));
                bookAE.setTitleBook(resultSet.getString(5));
                bookAE.setObservationBook(resultSet.getString(6));
                bookAE.setCodeBox(resultSet.getInt(7));
                books.add(bookAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de atas e
     * exames
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentAE documentAE = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_ae,titulo_documento, presente, observacao_documento, anexo, data_exame, codigo_livro FROM ver_documento_ae(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentAE = new DocumentAE();
                documentAE.setCodeDocument(resultSet.getInt(1));
                documentAE.setCodeDocumentAE(resultSet.getInt(2));
                documentAE.setTitleDocument(resultSet.getString(3));
                documentAE.setPresent(resultSet.getBoolean(4));
                documentAE.setObservationDocument(resultSet.getString(5));
                documentAE.setAnnex(resultSet.getString(6));
                documentAE.setDateExam(resultSet.getDate(7));
                documentAE.setCodeBook(resultSet.getInt(8));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentAE;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de atas e exames
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão de busca de documento de atas e exames
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_ae ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentAE documentAE = new DocumentAE();
                documentAE.setCodeDocument(resultSet.getInt(1));
                documentAE.setCodeDocumentAE(resultSet.getInt(2));
                documentAE.setTitleDocument(resultSet.getString(3));
                documentAE.setAnnex(resultSet.getString(4));
                documentAE.setDateExam(resultSet.getDate(5));
                documentAE.setPresent(resultSet.getBoolean(6));
                documentAE.setObservationDocument(resultSet.getString(7));
                documentAE.setCodeBook(resultSet.getInt(8));
                documents.add(documentAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos AE!");
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
            //cria preparedStatement com visão  de busca de documento de atas e exames
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_ae, titulo_documento, anexo, data_exame, presente,"
                    + " observacao_documento, codigo_livro FROM ver_documento_ae_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentAE documentAE = new DocumentAE();
                documentAE.setCodeDocument(resultSet.getInt(1));
                documentAE.setCodeDocumentAE(resultSet.getInt(2));
                documentAE.setTitleDocument(resultSet.getString(3));
                documentAE.setAnnex(resultSet.getString(4));
                documentAE.setDateExam(resultSet.getDate(5));
                documentAE.setPresent(resultSet.getBoolean(6));
                documentAE.setObservationDocument(resultSet.getString(7));
                documentAE.setCodeBook(resultSet.getInt(8));
                documents.add(documentAE);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos AE!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBokAE código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxAE, String title, String responsible, String observation) {
        //cria lista de caixas
        List<BoxAE> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_ae FROM busca_caixa_ae(?,?,?,?)");
            seacher.setInt(1, codeBoxAE);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxAE box = new BoxAE();
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
     * @param codeBookAe código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param codeCourse código do curso <b>pode ser nulo</b>
     * @param year ano <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookAE, String title, int codeCourse, int year, String observation) {
        //cria lista de livros
        List<BookAE> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_ae , codigo_curso_ano  FROM busca_livro_ae(?,?,?,?,?)");
            seacher.setInt(1, codeBookAE);
            seacher.setString(2, title);
            seacher.setInt(3, codeCourse);
            seacher.setInt(4, year);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookAE book = new BookAE();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setCodeCourseYear(resultSet.getInt(6));
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
     * @param codeDocumentAE código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param dateExam data <b>pode ser nulo</b>
     * @param annex anexo <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentAE, String title, java.sql.Date dateExam, String annex, String observation) {
        //cria lista de documentos
        List<DocumentAE> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, "
                    + "codigo_documento_ae, anexo, data_exame FROM busca_documento_ae(?,?,?,?,?)");
            seacher.setInt(1, codeDocumentAE);
            seacher.setString(2, title);
            seacher.setDate(3, dateExam);
            seacher.setString(4, annex);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentAE document = new DocumentAE();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentAE(resultSet.getInt(6));
                document.setAnnex(resultSet.getString(7));
                document.setDateExam(resultSet.getDate(8));

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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_ae (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_ae (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_ae (?,?)");
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
        BoxAE oldBoxAE = (BoxAE) oldBox;
        BoxAE newBoxAE = (BoxAE) newBox;
        if (oldBoxAE.getCodeBoxAE() != newBoxAE.getCodeBoxAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_ae(?);");
                verify.setInt(1, newBoxAE.getCodeBoxAE());
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
        BookAE oldBookAE = (BookAE) oldBook;
        BookAE newBookAE = (BookAE) newBook;
        if (oldBookAE.getCodeBookAE() != newBookAE.getCodeBookAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_ae(?,?);");
                verify.setInt(1, newBookAE.getCodeBox());
                verify.setInt(2, newBookAE.getCodeBookAE());
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
        DocumentAE oldDocumentAE = (DocumentAE) oldDocument;
        DocumentAE newDocumentAE = (DocumentAE) newDocument;
        if (oldDocumentAE.getCodeDocumentAE() != newDocumentAE.getCodeDocumentAE()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_ae(?,?);");
                verify.setInt(1, newDocumentAE.getCodeBook());
                verify.setInt(2, newDocumentAE.getCodeDocumentAE());
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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_cursos_ae;");
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
     * Lista diciplinas
     *
     * @return <code>List</code> com diciplinas
     */
    public List<String> listDiciplines() {
        List<String> disciplines = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_diciplina_ae;");
            ResultSet resultset = list.executeQuery();

            while (resultset.next()) {
                disciplines.add(resultset.getString(1));
            }
            return disciplines;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível listar disciplinas!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível listar disciplinas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
            return null;
        }
    }//fim do método listCourses
}//fim da classe AEDAO

