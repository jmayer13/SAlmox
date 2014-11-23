/*- 
 * Classname:             DADDAO.java 
 * 
 * Version information:   1.1 
 * 
 * Date:                  04/08/2014 - 19:21:59 
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
import uni.uri.salmox.model.BookDAD;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxDAD;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentDAD;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO da categoria DAD
 *
 * @see CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DADDAO implements CategoryBoxDAOInterface {
    //classe responsável pela conexão com o banco de dados

    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public DADDAO() {
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
     * Registra caixa
     *
     * @param box caixa DAD
     * @throws PreviouslyRegisteredException se código já foi registrado
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de formandos
            BoxDAD boxDAD = (BoxDAD) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_dad(?,?,?);");
            register.setInt(1, boxDAD.getCodeBoxSpecific());
            register.setString(2, boxDAD.getResponsibleBox());
            register.setString(3, boxDAD.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DAD!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método DADDAO

    /**
     * Registra livro
     *
     * @param book livro DAD
     * @throws PreviouslyRegisteredException lança exceção se código já existe
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {//converte livro para livro de caixa de DAD
            BookDAD bookDAD = (BookDAD) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_dad(?,?,?,?,?);");
            register.setInt(1, bookDAD.getCodeBookSpecific());
            register.setString(2, bookDAD.getTitleBook());
            register.setInt(3, bookDAD.getCodeBox());
            register.setString(4, bookDAD.getObservationBook());
            register.setString(5, bookDAD.getYear());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro DAD!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra documento
     *
     * @param document documento DAD
     * @throws PreviouslyRegisteredException lança exceção se código já está
     * egistrado
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos
            DocumentDAD documentDAD = (DocumentDAD) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_dad(?,?,?,?);");
            register.setInt(1, documentDAD.getCodeDocumentSpecific());
            register.setString(2, documentDAD.getTitleDocument());
            register.setInt(3, documentDAD.getCodeBook());
            register.setString(4, documentDAD.getObservationDocument());

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
     * Edita a caixa
     *
     * @param box caixa DAD
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de  
            BoxDAD boxDAD = (BoxDAD) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_dad(?,?,?,?);");
            changer.setInt(1, boxDAD.getCodeBox());
            changer.setInt(2, boxDAD.getCodeBoxSpecific());
            changer.setString(3, boxDAD.getResponsibleBox());
            changer.setString(4, boxDAD.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa DAD!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita livro
     *
     * @param book livro DAD
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos
            BookDAD bookDAD = (BookDAD) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_DAD(?,?,?,?,?,?);");
            changer.setInt(1, bookDAD.getCodeBook());
            changer.setInt(2, bookDAD.getCodeBookSpecific());
            changer.setString(3, bookDAD.getTitleBook());
            changer.setInt(4, bookDAD.getCodeBox());
            changer.setString(5, bookDAD.getObservationBook());
            changer.setString(6, bookDAD.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro DADDAD!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita documento
     *
     * @param document documento DAD
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de formandos
            DocumentDAD documentDAD = (DocumentDAD) document;
            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_dad(?,?,?,?,?);");
            changer.setInt(1, documentDAD.getCodeDocument());
            changer.setInt(2, documentDAD.getCodeDocumentSpecific());
            changer.setString(3, documentDAD.getTitleDocument());
            changer.setInt(4, documentDAD.getCodeBook());
            changer.setString(5, documentDAD.getObservationDocument());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editDocumento

    /**
     * Busca caixa
     *
     * @param codeBox código da caixa
     * @return <code>Box</code> caixa DAD
     */
    public Box searchBox(int codeBox) {
        BoxDAD boxDAD = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dad FROM ver_caixa_dad(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxDAD = new BoxDAD();
                boxDAD.setCodeBox(resultSet.getInt(1));
                boxDAD.setResponsibleBox(resultSet.getString(2));
                boxDAD.setObservationBox(resultSet.getString(3));
                boxDAD.setTitleBox(resultSet.getString(4));
                boxDAD.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa DAD!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxDAD;
    }//fim do método searchBox

    /**
     * Busca todas as caixas DAD
     *
     * @return <code>Iterator</code> documentos DAD
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_dad ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxDAD boxDAD = new BoxDAD();
                boxDAD.setCodeBox(resultSet.getInt(1));
                boxDAD.setCodeBoxSpecific(resultSet.getInt(2));
                boxDAD.setTitleBox(resultSet.getString(3));
                boxDAD.setObservationBox(resultSet.getString(4));
                boxDAD.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxDAD);
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
     * Busca livros pelo código
     *
     * @param codeBook código do livro
     * @return <code>Book</code> livro DAD
     */
    public Book searchBook(int codeBook) {
        BookDAD bookDAD = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dad, titulo_livro, observacao_livro, ano,  codigo_caixa FROM ver_livro_dad(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookDAD = new BookDAD();
                bookDAD.setCodeBook(resultSet.getInt(1));
                bookDAD.setCodeBookSpecific(resultSet.getInt(2));
                bookDAD.setTitleBook(resultSet.getString(3));
                bookDAD.setObservationBook(resultSet.getString(4));
                bookDAD.setYear(resultSet.getString(5));
                bookDAD.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookDAD;
    }//fim do método searchBook

    /**
     * Busca livro pela caixa
     *
     * @param codeBox código da caixa
     * @return <code>Iterator</code> livros DAD
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dad,"
                    + "ano, titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_dad_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookDAD bookDAD = new BookDAD();
                bookDAD.setCodeBook(resultSet.getInt(1));
                bookDAD.setCodeBookSpecific(resultSet.getInt(2));
                bookDAD.setYear(resultSet.getString(3));
                bookDAD.setTitleBook(resultSet.getString(4));
                bookDAD.setObservationBook(resultSet.getString(5));
                bookDAD.setCodeBox(resultSet.getInt(6));
                books.add(bookDAD);
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
     * Busca documento pelo código
     *
     * @param codeDocument código do documetno
     * @return <code>Document</code>
     */
    public Document searchDocument(int codeDocument) {
        DocumentDAD documentDAD = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_dad,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_dad(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentDAD = new DocumentDAD();
                documentDAD.setCodeDocument(resultSet.getInt(1));
                documentDAD.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAD.setTitleDocument(resultSet.getString(3));
                documentDAD.setPresent(resultSet.getBoolean(4));
                documentDAD.setObservationDocument(resultSet.getString(5));
                documentDAD.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentDAD;
    }//fim do método searchDocument

    /**
     * Busta todos os documentos
     *
     * @return <code>Iterator</code> documentos DAD
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_dad ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDAD documentDAD = new DocumentDAD();
                documentDAD.setCodeDocument(resultSet.getInt(1));
                documentDAD.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAD.setTitleDocument(resultSet.getString(3));
                documentDAD.setPresent(resultSet.getBoolean(4));
                documentDAD.setObservationDocument(resultSet.getString(5));
                documentDAD.setCodeBook(resultSet.getInt(6));
                documents.add(documentDAD);
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
     * Busca documenntos do livro
     *
     * @param codeBook código do livro DAD
     * @return <code>Iterator</code> documentos DAD
     */
    public Iterator searchDocuments(int codeBook) {
        List documents = new ArrayList();


        try {
            //cria preparedStatement com visão de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_dad, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_dad_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDAD documentDAD = new DocumentDAD();
                documentDAD.setCodeDocument(resultSet.getInt(1));
                documentDAD.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAD.setTitleDocument(resultSet.getString(3));
                documentDAD.setPresent(resultSet.getBoolean(4));
                documentDAD.setObservationDocument(resultSet.getString(5));
                documentDAD.setCodeBook(resultSet.getInt(6));
                documents.add(documentDAD);
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
     * Muda código da caixa
     *
     * @param code código da caixa
     * @param newCode novo código especifico da caixa
     */
    public void changeCodeBox(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_dad (?,?)");
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
     * Muda código do livro
     *
     * @param code código do livro
     * @param newCode novo código especifico do livro
     */
    public void changeCodeBook(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_dad (?,?)");
            changer.setInt(1, code);
            changer.setInt(2, newCode);
            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível alterar código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar código!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do metodo changeCodeBook

    /**
     * Muda código do documento
     *
     * @param code código antigo do documento
     * @param newCode novo código do cocumento
     */
    public void changeCodeDocument(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_dad (?,?)");
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
     * Checa código da caixa
     *
     * @param oldBox código especifico antigo da caixa
     * @param newBox novo código especifico do caixa
     * @return <code>Boolean</code> true se caixa já está cadastrada
     */
    public boolean checkBoxCode(Box oldBox, Box newBox) {
        BoxDAD oldBoxDAD = (BoxDAD) oldBox;
        BoxDAD newBoxDAD = (BoxDAD) newBox;
        if (oldBoxDAD.getCodeBoxSpecific() != newBoxDAD.getCodeBoxSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_dad(?);");
                verify.setInt(1, newBoxDAD.getCodeBoxSpecific());
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
     * Checa código da livro
     *
     * @param oldBook código especifico antigo do livro
     * @param newBook novo código especifico do livro
     * @return <code>Boolean</code> true se livro já está cadastrado
     */
    public boolean checkBookCode(Book oldBook, Book newBook) {
        BookDAD oldBookDAD = (BookDAD) oldBook;
        BookDAD newBookDAD = (BookDAD) newBook;
        if (oldBookDAD.getCodeBookSpecific() != newBookDAD.getCodeBookSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_dad(?,?);");
                verify.setInt(1, newBookDAD.getCodeBox());
                verify.setInt(2, newBookDAD.getCodeBookSpecific());
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
    }//fim do método checkBook

    /**
     * Checa código do documento
     *
     * @param oldDocument código especifico antigo do documento
     * @param newDocument novo código especifico
     * @return <code>Boolean</code> true se documento já está cadastrado
     */
    public boolean checkDocumentCode(Document oldDocument, Document newDocument) {
        DocumentDAD oldDocumentDAD = (DocumentDAD) oldDocument;
        DocumentDAD newDocumentDAD = (DocumentDAD) newDocument;
        if (oldDocumentDAD.getCodeDocumentSpecific() != newDocumentDAD.getCodeDocumentSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_dad(?,?);");
                verify.setInt(1, newDocumentDAD.getCodeBook());
                verify.setInt(2, newDocumentDAD.getCodeDocumentSpecific());
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
    }///fim do método checkDocumentCode
    //--

    public void registerBoxes(Iterator boxes) throws PreviouslyRegisteredException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void registerBooks(Iterator books) throws PreviouslyRegisteredException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void registerDocuments(Iterator documents) throws PreviouslyRegisteredException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterator searchBooks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busca caixas pelos atributos
     *
     * @param title título
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxDAD, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dad  FROM busca_caixa_dad(?,?,?,?)");
            seacher.setInt(1, codeBoxDAD);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxDAD box = new BoxDAD();
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
    public Iterator searchBook(int codeBookDAD, String title, String year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_dad, ano  FROM busca_livro_dad(?,?,?,?)");
            seacher.setInt(1, codeBookDAD);
            seacher.setString(2, title);
            seacher.setString(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookDAD book = new BookDAD();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
                book.setCodeBookSpecific(resultSet.getInt(5));
                book.setYear(resultSet.getString(6));
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
    public Iterator searchDocument(int codeDocumentDAD, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_dad FROM busca_documento_dad(?,?,?)");
            seacher.setInt(1, codeDocumentDAD);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentDAD document = new DocumentDAD();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentSpecific(resultSet.getInt(6));
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
     * Lista títulos livro
     *
     * @return <code>List</code> com cursos
     */
    public List<String> listBookTitle() {
        List<String> courses = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_livro_titulo_dad;");
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
    public List<String> listDocumentsTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_documento_dad;");
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
    }//fim do método listDocumentsTitles
}//fim da classe DADDAO 

