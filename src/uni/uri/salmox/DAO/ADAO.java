
/*-
 * Classname:             ADAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:28:01
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
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentA;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de almoxarifado
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ADAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public ADAO() {
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
     * @param box caixa de almoxarifado
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de almoxarifado
            BoxA boxA = (BoxA) box;
            //cria PreparedStatement com função de cadastro de caixa de almoxarifado
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_a(?,?,?,?);");
            register.setInt(1, boxA.getCodeBoxA());
            register.setString(2, boxA.getResponsibleBox());
            register.setString(3, boxA.getObservationBox());
            register.setString(4, boxA.getTypeBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de almoxarifado
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        //percorre o iterator
        try {
            while (boxes.hasNext()) {
                //converte objeto para caixa de almoxarifado
                BoxA boxA = (BoxA) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_a(?,?,?,?);");
                register.setInt(1, boxA.getCodeBoxA());
                register.setString(2, boxA.getResponsibleBox());
                register.setString(3, boxA.getObservationBox());
                register.setString(4, boxA.getTypeBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de almoxarifado no banco de dados
     *
     * @param book livro de caixa de almoxarifado
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de almoxarifado
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de almoxarifado
            BookA bookA = (BookA) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_a(?,?,?,?,?,?);");
            register.setInt(1, bookA.getCodeBookA());
            register.setString(2, bookA.getTitleBook());
            register.setInt(3, bookA.getCodeBox());
            register.setString(4, bookA.getObservationBook());
            register.setInt(5, bookA.getYear());
            register.setString(6, bookA.getTypeBook());
            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    //código já cadastrado
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de almoxarifado no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        //percorre iterator
        try {
            while (books.hasNext()) {
                //converte objeto para livro de almoxarifado
                BookA bookA = (BookA) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_a(?,?,?,?,?,?);");
                register.setInt(1, bookA.getCodeBookA());
                register.setString(2, bookA.getTitleBook());
                register.setInt(3, bookA.getCodeBox());
                register.setString(4, bookA.getObservationBook());
                register.setInt(5, bookA.getYear());
                register.setString(6, bookA.getTypeBook());
                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de almoxarifado
     *
     * @param document documento de livro de caixa de almoxarifado
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de almoxarifado
            DocumentA documentA = (DocumentA) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_a(?,?,?,?);");
            register.setInt(1, documentA.getCodeDocumentA());
            register.setString(2, documentA.getTitleDocument());
            register.setInt(3, documentA.getCodeBook());
            register.setString(4, documentA.getObservationDocument());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de almoxarifado no banco
     * de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de almoxarifado
                DocumentA documentA = (DocumentA) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de almoxarifado
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_a(?,?,?,?);");
                register.setInt(1, documentA.getCodeDocumentA());
                register.setString(2, documentA.getTitleDocument());
                register.setInt(3, documentA.getCodeBook());
                register.setString(4, documentA.getObservationDocument());
                //retorna true se código de livro de caixa de almoxarifado já estpa cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de almoxarifado fornecida como parãmetro
     *
     * @param box caixa de almoxarifado
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de almoxarifado
            BoxA boxA = (BoxA) box;
            //cria PreparedStatement com função de ediçao de caixa de almoxarifado
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_a(?,?,?,?,?);");
            changer.setInt(1, boxA.getCodeBox());
            changer.setInt(2, boxA.getCodeBoxA());
            changer.setString(3, boxA.getResponsibleBox());
            changer.setString(4, boxA.getObservationBox());
            changer.setString(5, boxA.getTypeBox());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de almoxarifado que foi fornecida como
     * parãmetro
     *
     * @param book livro de caixa de almoxarifado
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de almoxarifado
            BookA bookA = (BookA) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_a(?,?,?,?,?,?,?);");
            changer.setInt(1, bookA.getCodeBook());
            changer.setInt(2, bookA.getCodeBookA());
            changer.setString(3, bookA.getTitleBook());
            changer.setInt(4, bookA.getCodeBox());
            changer.setString(5, bookA.getObservationBook());
            changer.setInt(6, bookA.getYear());
            changer.setString(7, bookA.getTypeBook());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de almoxarifado que foi
     * fornecido como parâmetro
     *
     * @param document documento de livro de caixa de almoxarifado
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de almoxarifado
            DocumentA documentA = (DocumentA) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_a(?,?,?,?,?);");
            changer.setInt(1, documentA.getCodeDocument());
            changer.setInt(2, documentA.getCodeDocumentA());
            changer.setString(3, documentA.getTitleDocument());
            changer.setInt(4, documentA.getCodeBook());
            changer.setString(5, documentA.getObservationDocument());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de almoxarifado com código fornecido como parámetro
     *
     * @param codeBox código do caixa de almoxarifado
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxA boxA = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_a , tipo_caixa_a FROM ver_caixa_a(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxA = new BoxA();
                boxA.setCodeBox(resultSet.getInt(1));
                boxA.setResponsibleBox(resultSet.getString(2));
                boxA.setObservationBox(resultSet.getString(3));
                boxA.setTitleBox(resultSet.getString(4));
                boxA.setCodeBoxSpecific(resultSet.getInt(5));
                boxA.setTypeBox(resultSet.getString(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxA;
    }//fim do método searchBox

    /**
     * Busca caixas de almoxarifado
     *
     * @return <code>Iterator</code> coleção de caixas de almoxarifado
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_a ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxA boxA = new BoxA();
                boxA.setCodeBox(resultSet.getInt(1));
                boxA.setCodeBoxSpecific(resultSet.getInt(2));
                boxA.setTitleBox(resultSet.getString(3));
                boxA.setTypeBox(resultSet.getString(4));
                boxA.setObservationBox(resultSet.getString(5));
                boxA.setResponsibleBox(resultSet.getString(6));
                boxes.add(boxA);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de almoxarifado
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookA bookA = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_a, titulo_livro, ano, tipo_livro_a, observacao_livro,codigo_caixa FROM ver_livro_a(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookA = new BookA();
                bookA.setCodeBook(resultSet.getInt(1));
                bookA.setCodeBookSpecific(resultSet.getInt(2));
                bookA.setTitleBook(resultSet.getString(3));
                bookA.setYear(resultSet.getInt(4));
                bookA.setTypeBook(resultSet.getString(5));
                bookA.setObservationBook(resultSet.getString(6));
                bookA.setCodeBox(resultSet.getInt(7));

            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookA;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de almoxarifado
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_a ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookA bookA = new BookA();
                bookA.setCodeBook(resultSet.getInt(1));
                bookA.setCodeBookSpecific(resultSet.getInt(2));
                bookA.setTitleBook(resultSet.getString(3));
                bookA.setYear(resultSet.getInt(4));
                bookA.setTypeBook(resultSet.getString(5));
                bookA.setObservationBook(resultSet.getString(6));
                bookA.setCodeBox(resultSet.getInt(7));
                books.add(bookA);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de almoxarifado que pertensam ao código da caixa de
     * almoxarifado fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_a,titulo_livro, ano, tipo_livro_a, observacao_livro, codigo_caixa FROM ver_livro_a_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookA bookA = new BookA();
                bookA.setCodeBook(resultSet.getInt(1));
                bookA.setCodeBookSpecific(resultSet.getInt(2));
                bookA.setTitleBook(resultSet.getString(3));
                bookA.setYear(resultSet.getInt(4));
                bookA.setTypeBook(resultSet.getString(5));
                bookA.setObservationBook(resultSet.getString(6));
                bookA.setCodeBox(resultSet.getInt(7));
                books.add(bookA);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de almoxarifado
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentA documentA = null;
        try {
            //cria preparedStatement com visão  de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_a,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_a(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentA = new DocumentA();
                documentA.setCodeDocument(resultSet.getInt(1));
                documentA.setCodeDocumentSpecific(resultSet.getInt(2));
                documentA.setTitleDocument(resultSet.getString(3));
                documentA.setPresent(resultSet.getBoolean(4));
                documentA.setObservationDocument(resultSet.getString(5));
                documentA.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentA;
    }

    /**
     * Busca documentos de caixa de almoxarifado
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_a ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentA documentA = new DocumentA();
                documentA.setCodeDocument(resultSet.getInt(1));
                documentA.setCodeDocumentA(resultSet.getInt(2));
                documentA.setTitleDocument(resultSet.getString(3));
                documentA.setPresent(resultSet.getBoolean(4));
                documentA.setObservationDocument(resultSet.getString(5));
                documentA.setCodeBook(resultSet.getInt(6));
                documents.add(documentA);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
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
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_a, titulo_documento ,presente, observacao_documento, codigo_livro FROM ver_documento_a_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentA documentA = new DocumentA();
                documentA.setCodeDocument(resultSet.getInt(1));
                documentA.setCodeDocumentSpecific(resultSet.getInt(2));
                documentA.setTitleDocument(resultSet.getString(3));
                documentA.setPresent(resultSet.getBoolean(4));
                documentA.setObservationDocument(resultSet.getString(5));
                documentA.setCodeBook(resultSet.getInt(6));
                documents.add(documentA);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Lista os tipos de caixas
     *
     * @return <code>List</code> lista dos tipos de caixas
     */
    public List<String> listTypeBox() {
        List<String> types = new ArrayList();
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM lista_tipo_caixa ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                types.add(resultSet.getString(1));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os tipos de documentos A!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os tipos de documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return types;
    }//fim do método listTypeBox

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBoxA código da caixa <b>pode ser nulo =0</b>
     * @param title título <b>pode ser nulo</b>
     * @param type tipo <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxA, String title, String type, String responsible, String observation) {
        //cria lista de caixas
        List<BoxA> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_a, tipo_caixa_a FROM busca_caixa_a(?,?,?,?,?)");
            seacher.setInt(1, codeBoxA);
            seacher.setString(2, title);
            seacher.setString(3, type);
            seacher.setString(4, responsible);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxA box = new BoxA();
                box.setCodeBox(resultSet.getInt(1));
                box.setResponsibleBox(resultSet.getString(2));
                box.setObservationBox(resultSet.getString(3));
                box.setTitleBox(resultSet.getString(4));
                box.setCodeBoxSpecific(resultSet.getInt(5));
                box.setTypeBox(resultSet.getString(6));
                //adiciona caixas a lista
                boxes.add(box);
            }
            //retorna iterator
            return boxes.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBox

    /**
     * Busca livros pelos atributos
     *
     * @param codeBookA código do documento <b>pode ser nulo =0</b>
     * @param title título <b>pode ser nulo</b>
     * @param year ano <b>pode ser nulo =0</b>
     * @param type tipo <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookA, String title, int year, String type, String observation) {
        //cria lista de livros
        List<BookA> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, titulo_livro, codigo_caixa, observacao_livro, codigo_livro_a, ano, tipo_livro_a  FROM busca_livro_a(?,?,?,?,?)");
            seacher.setInt(1, codeBookA);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setString(4, type);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookA book = new BookA();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setYear(resultSet.getInt(6));
                book.setTypeBook(resultSet.getString(7));
                //adiciona livro a lista
                bookes.add(book);
            }
            //retorna iterator
            return bookes.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar livro!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documento!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBook

    /**
     * Busca documentos pelos atributos
     *
     * @param codeDocumentA código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocumentA, String title, String observation) {
        //cria lista de documentos
        List<DocumentA> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_a FROM busca_documento_a(?,?,?)");
            seacher.setInt(1, codeDocumentA);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentA document = new DocumentA();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentA(resultSet.getInt(6));
                //adiciona documento a lista
                documents.add(document);
            }
            //retorna iterator
            return documents.iterator();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar documento!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documento!", "Erro!", JOptionPane.ERROR_MESSAGE);
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_a (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_a (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_a (?,?)");
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
        BoxA oldBoxA = (BoxA) oldBox;
        BoxA newBoxA = (BoxA) newBox;
        if (oldBoxA.getCodeBoxA() != newBoxA.getCodeBoxA()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_a(?);");
                verify.setInt(1, newBoxA.getCodeBoxA());
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
        BookA oldBookA = (BookA) oldBook;
        BookA newBookA = (BookA) newBook;
        if (oldBookA.getCodeBookA() != newBookA.getCodeBookA()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_a(?,?);");
                verify.setInt(1, newBookA.getCodeBox());
                verify.setInt(2, newBookA.getCodeBookA());
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
        DocumentA oldDocumentA = (DocumentA) oldDocument;
        DocumentA newDocumentA = (DocumentA) newDocument;
        if (oldDocumentA.getCodeDocumentA() != newDocumentA.getCodeDocumentA()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_a(?,?);");
                verify.setInt(1, newDocumentA.getCodeBook());
                verify.setInt(2, newDocumentA.getCodeDocumentA());
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
     * @return <code>List</code> com títulos
     */
    public List<String> listTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_a;");
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
}//fim da classe ADAO

