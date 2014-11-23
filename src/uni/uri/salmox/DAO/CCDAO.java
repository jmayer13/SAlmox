
/*-
 * Classname:             CCDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  08/01/2013 - 14:28:59
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
import uni.uri.salmox.model.BookCC;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxCC;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentCC;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de cadernos de chamada
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class CCDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public CCDAO() {
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
     * Registra a caixa de cadernos de chamada no banco de dados
     *
     * @param box caixa de cadernos de chamada
     * @throws PreviouslyRegisteredException lançada caso o código da caixa de
     * almoxatifado já esteja cadastrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de cadernos de chamada
            BoxCC boxCC = (BoxCC) box;
            //cria PreparedStatement com função de cadastro de caixa de cadernos de chamada
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_cc(?,?,?);");
            register.setInt(1, boxCC.getCodeBoxCC());
            register.setString(2, boxCC.getResponsibleBox());
            register.setString(3, boxCC.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra coleção de caixas de cadernos de chamada
     *
     * @param boxes caixas
     * @throws PreviouslyRegisteredException lançada caso algum código de caixa
     * já esteja cadastado
     */
    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        //percorre o iterator
        try {
            while (boxes.hasNext()) {
                //converte objeto para caixa de cadernos de chamada
                BoxCC boxCC = (BoxCC) boxes.next();

                //cria PreparedStatement com função de cadastro de caixa
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_cc(?,?,?);");
                register.setInt(1, boxCC.getCodeBoxCC());
                register.setString(2, boxCC.getResponsibleBox());
                register.setString(3, boxCC.getObservationBox());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar conjunto de caixas CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerBoxes

    /**
     * Registra livro de caixa de cadernos de chamada no banco de dados
     *
     * @param book livro de caixa de cadernos de chamada
     * @throws PreviouslyRegisteredException lançada caso o código do livro já
     * esteja cadastrado na caixa de cadernos de chamada
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de cadernos de chamada
            BookCC bookCC = (BookCC) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_cc(?,?,?,?);");
            register.setInt(1, bookCC.getCodeBookCC());
            register.setString(2, bookCC.getTitleBook());
            register.setInt(3, bookCC.getCodeBox());
            register.setString(4, bookCC.getObservationBook());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra coleção de livros de cadernos de chamada no banco de dados
     *
     * @param books coleção de livros
     * @throws PreviouslyRegisteredException lançada se algum código do livro já
     * estiver cadastrado
     */
    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        //percorre iterator
        try {
            while (books.hasNext()) {
                //converte objeto para livro de cadernos de chamada
                BookCC bookCC = (BookCC) books.next();

                //cria PreparedStatement com função para cadastro de livro
                PreparedStatement register = connection.prepareStatement("SELECT * cadastra_livro_cc(?,?,?,?);");
                register.setInt(1, bookCC.getCodeBookCC());
                register.setString(2, bookCC.getTitleBook());
                register.setInt(3, bookCC.getCodeBox());
                register.setString(4, bookCC.getObservationBook());

                //retorna true se código de livro já está cadastrado
                ResultSet resultset = register.executeQuery();
                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim da classe registerBooks

    /**
     * Registra documento de livro de caixa de cadernos de chamada
     *
     * @param document documento de livro de caixa de cadernos de chamada
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já exista
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de cadernos de chamada
            DocumentCC documentCC = (DocumentCC) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_cc(?,?,?,?,?,?);");
            register.setInt(1, documentCC.getCodeDocumentCC());
            register.setString(2, documentCC.getTitleDocument());
            register.setInt(3, documentCC.getCodeBook());
            register.setString(4, documentCC.getObservationDocument());
            register.setInt(5, documentCC.getYear());
            register.setInt(6, documentCC.getSemester());
            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Registra coleção de documentos de livro de caixa de cadernos de chamada
     * no banco de dados
     *
     * @param documents coleção de documentos
     * @throws PreviouslyRegisteredException lançada caso o código de documento
     * já esteja cadastrado
     */
    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        try {
            while (documents.hasNext()) {
                //converte objeto em documento de livro de caixa de cadernos de chamada
                DocumentCC documentCC = (DocumentCC) documents.next();

                //cria PreparedStatement com função de cadastro de documento de caixa de cadernos de chamada
                PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_cc(?,?,?,?,?,?);");
                register.setInt(1, documentCC.getCodeDocumentCC());
                register.setString(2, documentCC.getTitleDocument());
                register.setInt(3, documentCC.getCodeBook());
                register.setString(4, documentCC.getObservationDocument());
                register.setInt(5, documentCC.getYear());
                register.setInt(6, documentCC.getSemester());
                //retorna true se o código já está cadastrado
                ResultSet resultset = register.executeQuery();

                while (resultset.next()) {
                    if (resultset.getBoolean(1)) {
                        throw new PreviouslyRegisteredException();
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);

        }
    }//fim do método registerDocuments

    /**
     * Edita os dados da caixa de cadernos de chamada fornecida como parãmetro
     *
     * @param box caixa de cadernos de chamada
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de cadernos de chamada
            BoxCC boxCC = (BoxCC) box;

            //cria PreparedStatement com função de ediçao de caixa de cadernos de chamada
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_cc(?,?,?,?);");
            changer.setInt(1, boxCC.getCodeBox());
            changer.setInt(2, boxCC.getCodeBoxCC());
            changer.setString(3, boxCC.getResponsibleBox());
            changer.setString(4, boxCC.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita os dados do livro de caixa de cadernos de chamada que foi fornecida
     * como parãmetro
     *
     * @param book livro de caixa de cadernos de chamada
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de cadernos de chamada
            BookCC bookCC = (BookCC) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_cc(?,?,?,?,?);");
            changer.setInt(1, bookCC.getCodeBook());
            changer.setInt(2, bookCC.getCodeBookCC());
            changer.setString(3, bookCC.getTitleBook());
            changer.setInt(4, bookCC.getCodeBox());
            changer.setString(5, bookCC.getObservationBook());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita os dados do documento de livro de caixa de cadernos de chamada que
     * foi fornecido como parâmetro
     *
     * @param document documento de livro de caixa de cadernos de chamada
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de cadernos de chamada
            DocumentCC documentCC = (DocumentCC) document;

            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_cc(?,?,?,?,?,?,?);");
            changer.setInt(1, documentCC.getCodeDocument());
            changer.setInt(2, documentCC.getCodeDocumentCC());
            changer.setString(3, documentCC.getTitleDocument());
            changer.setInt(4, documentCC.getCodeBook());
            changer.setString(5, documentCC.getObservationDocument());
            changer.setInt(6, documentCC.getYear());
            changer.setInt(7, documentCC.getSemester());
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fom do método de editDocument

    /**
     * Busca caixa de cadernos de chamada com código fornecido como parámetro
     *
     * @param codeBox código do caixa de cadernos de chamada
     * @return <code>Box</code> caixa
     */
    public Box searchBox(int codeBox) {
        BoxCC boxCC = null;
        try {

            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_cc FROM ver_caixa_cc(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxCC = new BoxCC();
                boxCC.setCodeBox(resultSet.getInt(1));
                boxCC.setResponsibleBox(resultSet.getString(2));
                boxCC.setObservationBox(resultSet.getString(3));
                boxCC.setTitleBox(resultSet.getString(4));
                boxCC.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxCC;
    }//fim do método searchBox

    /* Busca caixas de cadernos de chamada
     *
     * @return <code>Iterator</code> coleção de caixas de cadernos de
     * chamada
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de cadernos de chamada
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_cc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxCC boxCC = new BoxCC();
                boxCC.setCodeBox(resultSet.getInt(1));
                boxCC.setCodeBoxSpecific(resultSet.getInt(2));
                boxCC.setTitleBox(resultSet.getString(3));
                boxCC.setObservationBox(resultSet.getString(4));
                boxCC.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxCC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }//fim do método searchBoxes

    /**
     * Busca livro com código fornecido como parâmetro
     *
     * @param codeBook código de livro de caixa de cadernos de chamada
     * @return <code>Book</code> livro
     */
    public Book searchBook(int codeBook) {
        BookCC bookCC = null;
        try {
            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_cc, titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_cc(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookCC = new BookCC();
                bookCC.setCodeBook(resultSet.getInt(1));
                bookCC.setCodeBookSpecific(resultSet.getInt(2));
                bookCC.setTitleBook(resultSet.getString(3));
                bookCC.setObservationBook(resultSet.getString(4));
                bookCC.setCodeBox(resultSet.getInt(5));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookCC;
    }//fim do método searchBook

    /**
     * Busca livros de caixa de cadernos de chamada
     *
     * @return <code>Book</code> livro
     */
    public Iterator searchBooks() {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de cadernos de chamada
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_livro_cc ;");
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookCC bookCC = new BookCC();
                bookCC.setCodeBook(resultSet.getInt(1));
                bookCC.setCodeBookSpecific(resultSet.getInt(2));
                bookCC.setTitleBook(resultSet.getString(3));
                bookCC.setObservationBook(resultSet.getString(4));
                bookCC.setCodeBox(resultSet.getInt(5));
                books.add(bookCC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca livros de cadernos de chamada que pertensam ao código da caixa de
     * cadernos de chamada fornecido como pâmetro
     *
     * @param codeBox código do livro
     * @return <code>Iterator</code> com livros da caixa
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de cadernos de chamada
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_cc,"
                    + "titulo_livro,observacao_livro,codigo_caixa FROM ver_livro_cc_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookCC bookCC = new BookCC();
                bookCC.setCodeBook(resultSet.getInt(1));
                bookCC.setCodeBookSpecific(resultSet.getInt(2));
                bookCC.setTitleBook(resultSet.getString(3));
                bookCC.setObservationBook(resultSet.getString(4));
                bookCC.setCodeBox(resultSet.getInt(5));
                books.add(bookCC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os livros CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os livros!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return books.iterator();
    }//fim do método searchBooks

    /**
     * Busca documento com código fornecido como parâmetro
     *
     * @param codeDocument código do documento de livro de caixa de cadernos de
     * chamada
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentCC documentCC = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_cc,titulo_documento, presente, observacao_documento, ano, semestre,codigo_livro FROM ver_documento_cc(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentCC = new DocumentCC();
                documentCC.setCodeDocument(resultSet.getInt(1));
                documentCC.setCodeDocumentCC(resultSet.getInt(2));
                documentCC.setTitleDocument(resultSet.getString(3));
                documentCC.setPresent(resultSet.getBoolean(4));
                documentCC.setObservationDocument(resultSet.getString(5));
                documentCC.setYear(resultSet.getInt(6));
                documentCC.setSemester(resultSet.getInt(7));
                documentCC.setCodeBook(resultSet.getInt(8));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentCC;
    }//fim do método searchDocument

    /**
     * Busca documentos de caixa de cadernos de chamada
     *
     * @return <code>Iterator</code> com documentos
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de cadernos de chamada
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_cc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentCC documentCC = new DocumentCC();
                documentCC.setCodeDocument(resultSet.getInt(1));
                documentCC.setCodeDocumentCC(resultSet.getInt(2));
                documentCC.setTitleDocument(resultSet.getString(3));
                documentCC.setYear(resultSet.getInt(4));
                documentCC.setSemester(resultSet.getInt(5));
                documentCC.setPresent(resultSet.getBoolean(6));
                documentCC.setObservationDocument(resultSet.getString(7));
                documentCC.setCodeBook(resultSet.getInt(8));
                documents.add(documentCC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos CC!");
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
            //cria preparedStatement com visão de busca de documento de cadernos de chamada
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_cc, titulo_documento, ano, semestre, presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_cc_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentCC documentCC = new DocumentCC();
                documentCC.setCodeDocument(resultSet.getInt(1));
                documentCC.setCodeDocumentCC(resultSet.getInt(2));
                documentCC.setTitleDocument(resultSet.getString(3));
                documentCC.setYear(resultSet.getInt(4));
                documentCC.setSemester(resultSet.getInt(5));
                documentCC.setPresent(resultSet.getBoolean(6));
                documentCC.setObservationDocument(resultSet.getString(7));
                documentCC.setCodeBook(resultSet.getInt(8));
                documents.add(documentCC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar os documentos CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar os documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método searchDocuments

    /**
     * Busca caixas pelos atributos
     *
     * @param codeBoxCC código da caixa <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxCC, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_cc FROM busca_caixa_cc(?,?,?,?)");
            seacher.setInt(1, codeBoxCC);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxCC box = new BoxCC();
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
     * @param codeBookCC código do livro <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> livros
     */
    public Iterator searchBook(int codeBookCC, String title, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_cc FROM busca_livro_cc(?,?,?)");
            seacher.setInt(1, codeBookCC);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookCC book = new BookCC();
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
     * @param código do documento <b>pode ser nulo</b>
     * @param title título <b>pode ser nulo</b>
     * @param year ano <b>pode ser nulo</b>
     * @param semester semestre <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> documentos
     */
    public Iterator searchDocument(int codeDocument, String title, int year, int semester, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_cc, "
                    + "ano, semestre FROM busca_documento_cc(?,?,?,?,?)");
            seacher.setInt(1, codeDocument);
            seacher.setString(2, title);
            seacher.setInt(3, year);
            seacher.setInt(4, semester);
            seacher.setString(5, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentCC document = new DocumentCC();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentCC(resultSet.getInt(6));
                document.setYear(resultSet.getInt(7));
                document.setSemester(resultSet.getInt(8));
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_cc (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_cc (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_cc (?,?)");
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
        BoxCC oldBoxCC = (BoxCC) oldBox;
        BoxCC newBoxCC = (BoxCC) newBox;
        if (oldBoxCC.getCodeBoxCC() != newBoxCC.getCodeBoxCC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_cc(?);");
                verify.setInt(1, newBoxCC.getCodeBoxCC());
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
        BookCC oldBookCC = (BookCC) oldBook;
        BookCC newBookCC = (BookCC) newBook;
        if (oldBookCC.getCodeBookCC() != newBookCC.getCodeBookCC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_cc(?,?);");
                verify.setInt(1, newBookCC.getCodeBox());
                verify.setInt(2, newBookCC.getCodeBookCC());

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
        DocumentCC oldDocumentCC = (DocumentCC) oldDocument;
        DocumentCC newDocumentCC = (DocumentCC) newDocument;
        if (oldDocumentCC.getCodeDocumentCC() != newDocumentCC.getCodeDocumentCC()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_cc(?,?);");
                verify.setInt(1, newDocumentCC.getCodeBook());
                verify.setInt(2, newDocumentCC.getCodeDocumentCC());

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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_cursos_cc;");
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
     * Lista títulos
     *
     * @return <code>List</code> com títulos
     */
    public List<String> listTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_cc;");
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
}//fim da classe CCDAO

