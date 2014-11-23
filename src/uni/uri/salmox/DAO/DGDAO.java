/*- 
 * Classname:             DGDAO.java 
 * 
 * Version information:   1.1 
 * 
 * Date:                  04/08/2014 - 19:22:34 
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
import uni.uri.salmox.model.BookDG;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxDG;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentDG;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO da categoria DG
 *
 * @see CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DGDAO implements CategoryBoxDAOInterface {
    //classe responsável pela conexão com o banco de dados

    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public DGDAO() {
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
            BoxDG boxDG = (BoxDG) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_dg(?,?,?);");
            register.setInt(1, boxDG.getCodeBoxSpecific());
            register.setString(2, boxDG.getResponsibleBox());
            register.setString(3, boxDG.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método DADDAO

    /**
     * Registra livro
     *
     * @param book livro DG
     * @throws PreviouslyRegisteredException lança exceção se código já existe
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {//converte livro para livro de caixa de DG
            BookDG bookDG = (BookDG) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_dg(?,?,?,?,?);");
            register.setInt(1, bookDG.getCodeBookSpecific());
            register.setString(2, bookDG.getTitleBook());
            register.setInt(3, bookDG.getCodeBox());
            register.setString(4, bookDG.getObservationBook());
            register.setString(5, bookDG.getYear());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra documento
     *
     * @param document documento DG
     * @throws PreviouslyRegisteredException lança exceção se código já está
     * egistrado
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos
            DocumentDG documentDG = (DocumentDG) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_dg(?,?,?,?);");
            register.setInt(1, documentDG.getCodeDocumentSpecific());
            register.setString(2, documentDG.getTitleDocument());
            register.setInt(3, documentDG.getCodeBook());
            register.setString(4, documentDG.getObservationDocument());

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
     * @param box caixa DG
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de  
            BoxDG boxDG = (BoxDG) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_dg(?,?,?,?);");
            changer.setInt(1, boxDG.getCodeBox());
            changer.setInt(2, boxDG.getCodeBoxSpecific());
            changer.setString(3, boxDG.getResponsibleBox());
            changer.setString(4, boxDG.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBox

    /**
     * Edita livro
     *
     * @param book livro DG
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos
            BookDG bookDG = (BookDG) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_dg(?, ?,?,?,?,?);");
            changer.setInt(1, bookDG.getCodeBook());
            changer.setInt(2, bookDG.getCodeBookSpecific());
            changer.setString(3, bookDG.getTitleBook());
            changer.setInt(4, bookDG.getCodeBox());
            changer.setString(5, bookDG.getObservationBook());
            changer.setString(6, bookDG.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro DG!");
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
            DocumentDG documentDG = (DocumentDG) document;
            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_dg(?,?,?,?,?);");
            changer.setInt(1, documentDG.getCodeDocument());
            changer.setInt(2, documentDG.getCodeDocumentSpecific());
            changer.setString(3, documentDG.getTitleDocument());
            changer.setInt(4, documentDG.getCodeBook());
            changer.setString(5, documentDG.getObservationDocument());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editDocument

    /**
     * Busca caixa
     *
     * @param codeBox código da caixa
     * @return <code>Box</code> caixa DG
     */
    public Box searchBox(int codeBox) {
        BoxDG boxDG = null;
        try {
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dg FROM ver_caixa_dg(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxDG = new BoxDG();
                boxDG.setCodeBox(resultSet.getInt(1));
                boxDG.setResponsibleBox(resultSet.getString(2));
                boxDG.setObservationBox(resultSet.getString(3));
                boxDG.setTitleBox(resultSet.getString(4));
                boxDG.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxDG;
    }//FIM DO MÉTODO searchBox

    /**
     * Busca todas as caixas DAD
     *
     * @return <code>Iterator</code> documentos DAD
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_dg ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxDG boxDG = new BoxDG();
                boxDG.setCodeBox(resultSet.getInt(1));
                boxDG.setCodeBoxSpecific(resultSet.getInt(2));
                boxDG.setTitleBox(resultSet.getString(3));
                boxDG.setObservationBox(resultSet.getString(4));
                boxDG.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxDG);
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
     * @return <code>Book</code> livro DG
     */
    public Book searchBook(int codeBook) {
        BookDG bookDG = null;
        try {

            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dg, titulo_livro, observacao_livro, ano,  codigo_caixa FROM ver_livro_dg(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookDG = new BookDG();
                bookDG.setCodeBook(resultSet.getInt(1));
                bookDG.setCodeBookSpecific(resultSet.getInt(2));
                bookDG.setTitleBook(resultSet.getString(3));
                bookDG.setObservationBook(resultSet.getString(4));
                bookDG.setYear(resultSet.getString(5));
                bookDG.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookDG;
    }//fim do método searchBook

    /**
     * Busca livro pela caixa
     *
     * @param codeBox código da caixa
     * @return <code>Iterator</code> livros DG
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_dg,"
                    + "ano, titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_dg_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookDG bookDG = new BookDG();
                bookDG.setCodeBook(resultSet.getInt(1));
                bookDG.setCodeBookSpecific(resultSet.getInt(2));
                bookDG.setYear(resultSet.getString(3));
                bookDG.setTitleBook(resultSet.getString(4));
                bookDG.setObservationBook(resultSet.getString(5));
                bookDG.setCodeBox(resultSet.getInt(6));
                books.add(bookDG);
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
        DocumentDG documentDG = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_dg,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_dg(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentDG = new DocumentDG();
                documentDG.setCodeDocument(resultSet.getInt(1));
                documentDG.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDG.setTitleDocument(resultSet.getString(3));
                documentDG.setPresent(resultSet.getBoolean(4));
                documentDG.setObservationDocument(resultSet.getString(5));
                documentDG.setCodeBook(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentDG;
    }//fim do método searchDocument

    /**
     * Busta todos os documentos
     *
     * @return <code>Iterator</code> documentos DG
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_dg ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDG documentDG = new DocumentDG();
                documentDG.setCodeDocument(resultSet.getInt(1));
                documentDG.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDG.setTitleDocument(resultSet.getString(3));
                documentDG.setPresent(resultSet.getBoolean(4));
                documentDG.setObservationDocument(resultSet.getString(5));
                documentDG.setCodeBook(resultSet.getInt(6));
                documents.add(documentDG);
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
     * @param codeBook código do livro DG
     * @return <code>Iterator</code> documentos DG
     */
    public Iterator searchDocuments(int codeBook) {
        List documents = new ArrayList();


        try {
            //cria preparedStatement com visão de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_dg, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_dg_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            //preenche lista com dados
            while (resultSet.next()) {
                DocumentDG documentDG = new DocumentDG();
                documentDG.setCodeDocument(resultSet.getInt(1));
                documentDG.setCodeDocumentSpecific(resultSet.getInt(2));
                documentDG.setTitleDocument(resultSet.getString(3));
                documentDG.setPresent(resultSet.getBoolean(4));
                documentDG.setObservationDocument(resultSet.getString(5));
                documentDG.setCodeBook(resultSet.getInt(6));
                documents.add(documentDG);
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_dg (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_dg (?,?)");
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
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_dg (?,?)");
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
        BoxDG oldBoxDG = (BoxDG) oldBox;
        BoxDG newBoxDG = (BoxDG) newBox;
        if (oldBoxDG.getCodeBoxSpecific() != newBoxDG.getCodeBoxSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_dg(?);");
                verify.setInt(1, newBoxDG.getCodeBoxSpecific());
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
        BookDG oldBookDG = (BookDG) oldBook;
        BookDG newBookDG = (BookDG) newBook;
        if (oldBookDG.getCodeBookSpecific() != newBookDG.getCodeBookSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_dg(?,?);");
                verify.setInt(1, newBookDG.getCodeBox());
                verify.setInt(2, newBookDG.getCodeBookSpecific());
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
        DocumentDG oldDocumentDG = (DocumentDG) oldDocument;
        DocumentDG newDocumentDG = (DocumentDG) newDocument;
        if (oldDocumentDG.getCodeDocumentSpecific() != newDocumentDG.getCodeDocumentSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_dg(?,?);");
                verify.setInt(1, newDocumentDG.getCodeBook());
                verify.setInt(2, newDocumentDG.getCodeDocumentSpecific());
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
    public Iterator searchBox(int codeBoxDG, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_dg  FROM busca_caixa_dg(?,?,?,?)");
            seacher.setInt(1, codeBoxDG);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxDG box = new BoxDG();
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
    public Iterator searchBook(int codeBookDG, String title, String year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_dg, ano  FROM busca_livro_dg(?,?,?,?)");
            seacher.setInt(1, codeBookDG);
            seacher.setString(2, title);
            seacher.setString(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookDG book = new BookDG();
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
    public Iterator searchDocument(int codeDocumentDG, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_dg FROM busca_documento_dg(?,?,?)");
            seacher.setInt(1, codeDocumentDG);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentDG document = new DocumentDG();
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
     * Lista títulos livros
     *
     * @return <code>List</code> com cursos
     */
    public List<String> listBookTitle() {
        List<String> courses = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_livro_titulo_dg;");
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
    }//fim do método listBookTitle

    /**
     * Lista títulos document
     *
     * @return <code>List</code> com títulos
     */
    public List<String> listDocumentsTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_documento_dg;");
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
}//fim da classe DGDAO 

