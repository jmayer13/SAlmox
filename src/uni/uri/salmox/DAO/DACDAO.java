/*- 
 * Classname:             DACDAO.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  04/08/2014 - 19:22:21 
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
import uni.uri.salmox.model.BookDAC;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxDAC;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentDAC;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO da categoria DAC
 *
 * @see CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DACDAO implements CategoryBoxDAOInterface {
    //classe responsável pela conexão com o banco de dados

    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public DACDAO() {
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
     * Registra caixa DAC
     *
     * @param box caixa DAC
     * @throws PreviouslyRegisteredException exceção lançada em caso de código
     * repetido
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de formandos
            BoxDAC boxDAC = (BoxDAC) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_dac(?,?,?);");
            register.setInt(1, boxDAC.getCodeBoxSpecific());
            register.setString(2, boxDAC.getResponsibleBox());
            register.setString(3, boxDAC.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DAC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra livro DAC
     *
     * @param book livro DAC
     * @throws PreviouslyRegisteredException lança exceção em caso de código
     * repetido
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {//converte livro para livro de caixa de DAC
            BookDAC bookDAC = (BookDAC) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_dac(?,?,?,?,?);");
            register.setInt(1, bookDAC.getCodeBookSpecific());
            register.setString(2, bookDAC.getTitleBook());
            register.setInt(3, bookDAC.getCodeBox());
            register.setString(4, bookDAC.getObservationBook());
            register.setString(5, bookDAC.getYear());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro DAC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra documento DAC
     *
     * @param document documento DAC
     * @throws PreviouslyRegisteredException lança exeção em caso de código
     * repetido
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos
            DocumentDAC documentDAC = (DocumentDAC) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_dac(?,?,?,?);");
            register.setInt(1, documentDAC.getCodeDocumentSpecific());
            register.setString(2, documentDAC.getTitleDocument());
            register.setInt(3, documentDAC.getCodeBook());
            register.setString(4, documentDAC.getObservationDocument());

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
     * Esita caixa DAC
     *
     * @param box caixa DAC
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de  
            BoxDAC boxDAC = (BoxDAC) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_dac(?,?,?,?);");
            changer.setInt(1, boxDAC.getCodeBox());
            changer.setInt(2, boxDAC.getCodeBoxSpecific());
            changer.setString(3, boxDAC.getResponsibleBox());
            changer.setString(4, boxDAC.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa DAC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita livro DAC
     *
     * @param book livro DAC
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos
            BookDAC bookDAC = (BookDAC) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_dac(?,?,?,?,?,? );");
            changer.setInt(1, bookDAC.getCodeBook());
            changer.setInt(2, bookDAC.getCodeBookSpecific());
            changer.setString(3, bookDAC.getTitleBook());
            changer.setInt(4, bookDAC.getCodeBox());
            changer.setString(5, bookDAC.getObservationBook());
            changer.setString(6, bookDAC.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro DAC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita documento DAC
     *
     * @param document DAC
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de formandos
            DocumentDAC documentDAC = (DocumentDAC) document;
            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_dac(?,?,?,?,?);");
            changer.setInt(1, documentDAC.getCodeDocument());
            changer.setInt(2, documentDAC.getCodeDocumentSpecific());
            changer.setString(3, documentDAC.getTitleDocument());
            changer.setInt(4, documentDAC.getCodeBook());
            changer.setString(5, documentDAC.getObservationDocument());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editDocument

    /**
     * Busca caixa DAC
     *
     * @param codeBox código da caixa
     * @return <code>Box</code> caixa DAC
     */
    public Box searchBox(int codeBox) {
        BoxDAC boxDAC = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dac FROM ver_caixa_dac(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxDAC = new BoxDAC();
                boxDAC.setCodeBox(resultSet.getInt(1));
                boxDAC.setResponsibleBox(resultSet.getString(2));
                boxDAC.setObservationBox(resultSet.getString(3));
                boxDAC.setTitleBox(resultSet.getString(4));
                boxDAC.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa DAC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxDAC;
    }//fim do método searchBox

    /**
     * Busca todas as caixas DAC
     *
     * @return <code>searchBoxes</code>
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_dac ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxDAC boxDAC = new BoxDAC();
                boxDAC.setCodeBox(resultSet.getInt(1));
                boxDAC.setCodeBoxSpecific(resultSet.getInt(2));
                boxDAC.setTitleBox(resultSet.getString(3));
                boxDAC.setObservationBox(resultSet.getString(4));
                boxDAC.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxDAC);
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
     * Busca livro DAC
     *
     * @param codeBook código livro DAC
     * @return <code>Book</code>
     */
    public Book searchBook(int codeBook) {
        BookDAC bookDAC = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dac, titulo_livro, observacao_livro, ano,  codigo_caixa FROM ver_livro_dac(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookDAC = new BookDAC();
                bookDAC.setCodeBook(resultSet.getInt(1));
                bookDAC.setCodeBookSpecific(resultSet.getInt(2));
                bookDAC.setTitleBook(resultSet.getString(3));
                bookDAC.setObservationBook(resultSet.getString(4));
                bookDAC.setYear(resultSet.getString(5));
                bookDAC.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookDAC;
    }//fim do método searchBook

    /**
     * Busca livros da caixa especificada
     *
     * @param codeBox código da caixa
     * @return <code>Iterator</code> lista de livros DAC
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dac,"
                    + "ano, titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_dac_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookDAC bookDAC = new BookDAC();
                bookDAC.setCodeBook(resultSet.getInt(1));
                bookDAC.setCodeBookSpecific(resultSet.getInt(2));
                bookDAC.setYear(resultSet.getString(3));
                bookDAC.setTitleBook(resultSet.getString(4));
                bookDAC.setObservationBook(resultSet.getString(5));
                bookDAC.setCodeBox(resultSet.getInt(6));
                books.add(bookDAC);
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
     * Busca documentos DAC
     *
     * @param codeDocument codigo documento
     * @return <code>Document</code> documento DAC
     */
    public Document searchDocument(int codeDocument) {
        DocumentDAC documentDAC = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_dac,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_dac(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentDAC = new DocumentDAC();
                documentDAC.setCodeDocument(resultSet.getInt(1));
                documentDAC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAC.setTitleDocument(resultSet.getString(3));
                documentDAC.setPresent(resultSet.getBoolean(4));
                documentDAC.setObservationDocument(resultSet.getString(5));
                documentDAC.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentDAC;
    }//fim do método searchDocument

    /**
     * Busca documento da caixa DAC
     *
     * @return <code>Iterator</code> doumentos DAC
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_dac ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDAC documentDAC = new DocumentDAC();
                documentDAC.setCodeDocument(resultSet.getInt(1));
                documentDAC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAC.setTitleDocument(resultSet.getString(3));
                documentDAC.setPresent(resultSet.getBoolean(4));
                documentDAC.setObservationDocument(resultSet.getString(5));
                documentDAC.setCodeBook(resultSet.getInt(6));
                documents.add(documentDAC);
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
     * Busca documentos DAC
     *
     * @param codeBook código livro
     * @return <code>Iterator</code> de documentos DAC
     */
    public Iterator searchDocuments(int codeBook) {
        List documents = new ArrayList();


        try {
            //cria preparedStatement com visão de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_dac, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_dac_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDAC documentDAC = new DocumentDAC();
                documentDAC.setCodeDocument(resultSet.getInt(1));
                documentDAC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDAC.setTitleDocument(resultSet.getString(3));
                documentDAC.setPresent(resultSet.getBoolean(4));
                documentDAC.setObservationDocument(resultSet.getString(5));
                documentDAC.setCodeBook(resultSet.getInt(6));
                documents.add(documentDAC);
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
     * Troca código da caixa
     *
     * @param code código da caixa
     * @param newCode novo código especifico
     */
    public void changeCodeBox(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_dac (?,?)");
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
     * Troca o código do livro
     *
     * @param code código do livro
     * @param newCode novo código especifico
     */
    public void changeCodeBook(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_dac (?,?)");
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
     * Troca o código do documento
     *
     * @param code código do documento
     * @param newCode novo código específico
     */
    public void changeCodeDocument(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_dac (?,?)");
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
     * Checa o código da caixa especifico
     *
     * @param oldBox código da caixa especifico antigo
     * @param newBox código novo da caixa especifico
     * @return <code>Boolean</code> true se código está sendo usado
     */
    public boolean checkBoxCode(Box oldBox, Box newBox) {
        BoxDAC oldBoxDAC = (BoxDAC) oldBox;
        BoxDAC newBoxDAC = (BoxDAC) newBox;
        if (oldBoxDAC.getCodeBoxSpecific() != newBoxDAC.getCodeBoxSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_dac(?);");
                verify.setInt(1, newBoxDAC.getCodeBoxSpecific());
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
     * Checa código especifico do livro
     *
     * @param oldBook código especifico antigo
     * @param newBook código especifico novo
     * @return <code>Boolean</code> true se código está sendo usado
     */
    public boolean checkBookCode(Book oldBook, Book newBook) {
        BookDAC oldBookDAC = (BookDAC) oldBook;
        BookDAC newBookDAC = (BookDAC) newBook;
        if (oldBookDAC.getCodeBookSpecific() != newBookDAC.getCodeBookSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_dac(?,?);");
                verify.setInt(1, newBookDAC.getCodeBox());
                verify.setInt(2, newBookDAC.getCodeBookSpecific());
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
     * Checa código especifico do documento
     *
     * @param oldDocument código especifico antigo
     * @param newDocument novo código especifico
     * @return <code>Boolean</code> true se código está sendo usado
     */
    public boolean checkDocumentCode(Document oldDocument, Document newDocument) {
        DocumentDAC oldDocumentDAC = (DocumentDAC) oldDocument;
        DocumentDAC newDocumentDAC = (DocumentDAC) newDocument;
        if (oldDocumentDAC.getCodeDocumentSpecific() != newDocumentDAC.getCodeDocumentSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_dac(?,?);");
                verify.setInt(1, newDocumentDAC.getCodeBook());
                verify.setInt(2, newDocumentDAC.getCodeDocumentSpecific());
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
    public Iterator searchBox(int codeBoxDAC, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dac  FROM busca_caixa_dac(?,?,?,?)");
            seacher.setInt(1, codeBoxDAC);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxDAC box = new BoxDAC();
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
    public Iterator searchBook(int codeBookDAC, String title, String year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_dac, ano  FROM busca_livro_dac(?,?,?,?)");
            seacher.setInt(1, codeBookDAC);
            seacher.setString(2, title);
            seacher.setString(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookDAC book = new BookDAC();
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
    public Iterator searchDocument(int codeDocumentDAC, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_dac FROM busca_documento_dac(?,?,?)");
            seacher.setInt(1, codeDocumentDAC);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentDAC document = new DocumentDAC();
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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_livro_titulo_dac;");
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
    }//fim do método listBookSTitle

    /**
     * Lista títulos documentos
     *
     * @return <code>List</code> com títulos
     */
    public List<String> listDocumentsTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_documento_dac;");
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
}//fim da classe DACDAO 

