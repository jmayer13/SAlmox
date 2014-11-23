/*- 
 * Classname:             FCDAO.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  02/08/2014 - 08:24:29 
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
import uni.uri.salmox.model.BookFC;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxFC;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentFC;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreviouslyRegisteredException;

/**
 * DAO de caixa de FC
 *
 * @see uni.uri.salmox.DAO.CategoryBoxDAOInterface
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class FCDAO implements CategoryBoxDAOInterface {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public FCDAO() {
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
     * Registra caixa FC
     *
     * @param box caixa FC
     * @throws PreviouslyRegisteredException exceção em caso de código repetido
     */
    public void registerBox(Box box) throws PreviouslyRegisteredException {
        try {
            //converte caixa para o tipo caixa de formandos
            BoxFC boxFC = (BoxFC) box;
            //cria PreparedStatement com função de cadastro de caixa de formandos
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_caixa_fc(?,?,?);");
            register.setInt(1, boxFC.getCodeBoxSpecific());
            register.setString(2, boxFC.getResponsibleBox());
            register.setString(3, boxFC.getObservationBox());
            //retorna true se o código da caixa já está cadastrado
            ResultSet resultset = register.executeQuery();

            //verifica se código já está registrdo e lança exceção em caso positivo
            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa FC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBox

    /**
     * Registra livo FC
     *
     * @param book livro FC
     * @throws PreviouslyRegisteredException lança exceção se código já está
     * cadastrado na caixa
     */
    public void registerBook(Book book) throws PreviouslyRegisteredException {
        try {
            //converte livro para livro de caixa de FC
            BookFC bookFC = (BookFC) book;
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_livro_fc(?,?,?,?,?);");
            register.setInt(1, bookFC.getCodeBookSpecific());
            register.setString(2, bookFC.getTitleBook());
            register.setInt(3, bookFC.getCodeBox());
            register.setString(4, bookFC.getObservationBook());
            register.setString(5, bookFC.getYear());

            //retorna true se código de livro já está cadastrado
            ResultSet resultset = register.executeQuery();

            //executa e verifica se livro já está cadastrado
            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar livro FC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerBook

    /**
     * Registra documento FC
     *
     * @param document documento FC
     * @throws PreviouslyRegisteredException exceção é lançada se código já está
     * cadstrado no livro
     */
    public void registerDocument(Document document) throws PreviouslyRegisteredException {
        try {
            //converte documento para documento de livro decaixa de formandos
            DocumentFC documentFC = (DocumentFC) document;
            //cria PreparedStatement com função de criar documento
            PreparedStatement register = connection.prepareStatement("SELECT * FROM cadastra_documento_fc(?,?,?,?);");
            register.setInt(1, documentFC.getCodeDocumentSpecific());
            register.setString(2, documentFC.getTitleDocument());
            register.setInt(3, documentFC.getCodeBook());
            register.setString(4, documentFC.getObservationDocument());

            //retorna true se o código já está cadastrado
            ResultSet resultset = register.executeQuery();

            while (resultset.next()) {
                if (resultset.getBoolean(1)) {
                    throw new PreviouslyRegisteredException();
                }
            }

            new ItemDAO().setItems(documentFC.getCodeDocument(), documentFC.getItems());

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerDocument

    /**
     * Edita caixa FC
     *
     * @param box caixa FC
     */
    public void editBox(Box box) {
        try {
            //converte a caixa em caixa de  
            BoxFC boxFC = (BoxFC) box;

            //cria PreparedStatement com função de ediçao de caixa de formandos
            PreparedStatement changer = connection.prepareStatement("SELECT editar_caixa_fc(?,?,?,?);");
            changer.setInt(1, boxFC.getCodeBox());
            changer.setInt(2, boxFC.getCodeBoxSpecific());
            changer.setString(3, boxFC.getResponsibleBox());
            changer.setString(4, boxFC.getObservationBox());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar caixa FC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }///fim do método editBox

    /**
     * Edita livro FC
     *
     * @param book livro FC
     */
    public void editBook(Book book) {
        try {
            //converte livro para livro de caixa de formandos
            BookFC bookFC = (BookFC) book;

            //cria PreparedStatement com
            PreparedStatement changer = connection.prepareStatement("SELECT editar_livro_fc(?, ?,?,?,?,?);");
            changer.setInt(1, bookFC.getCodeBook());
            changer.setInt(2, bookFC.getCodeBookSpecific());
            changer.setString(3, bookFC.getTitleBook());
            changer.setInt(4, bookFC.getCodeBox());
            changer.setString(5, bookFC.getObservationBook());
            changer.setString(6, bookFC.getYear());

            changer.executeQuery();

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar livro FC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editBook

    /**
     * Edita documento FC
     *
     * @param document documento FC
     */
    public void editDocument(Document document) {
        try {
            //converte documento para documento de caixa de formandos
            DocumentFC documentFC = (DocumentFC) document;
            PreparedStatement changer = connection.prepareStatement("SELECT editar_documento_fc(?,?,?,?,?);");
            changer.setInt(1, documentFC.getCodeDocument());
            changer.setInt(2, documentFC.getCodeDocumentSpecific());
            changer.setString(3, documentFC.getTitleDocument());
            changer.setInt(4, documentFC.getCodeBook());
            changer.setString(5, documentFC.getObservationDocument());

            changer.executeQuery();

            new ItemDAO().setItems(documentFC.getCodeDocument(), documentFC.getItems());
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível atualizar documento F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível atualizar a documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editDocument

    /**
     * Busca caixa FC
     *
     * @param codeBox caixa FC
     * @return <code>BoxFC</code>
     */
    public Box searchBox(int codeBox) {
        BoxFC boxFC = null;
        try {
            //define função sql de busca por codigo de caixa
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_fc FROM ver_caixa_fc(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                boxFC = new BoxFC();
                boxFC.setCodeBox(resultSet.getInt(1));
                boxFC.setResponsibleBox(resultSet.getString(2));
                boxFC.setObservationBox(resultSet.getString(3));
                boxFC.setTitleBox(resultSet.getString(4));
                boxFC.setCodeBoxSpecific(resultSet.getInt(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar a caixa FC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa !", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxFC;
    }//fim do método searchBox

    /**
     * Busca por todas as caixas FC
     *
     * @return <code>Interator</code> com caixas FC
     */
    public Iterator searchBoxes() {
        List boxes = new ArrayList();

        try {
            //cria PreparedStement com visão de busca de caixas de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_caixa_fc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche ArrayList com resultados
            while (resultSet.next()) {
                BoxFC boxFC = new BoxFC();
                boxFC.setCodeBox(resultSet.getInt(1));
                boxFC.setCodeBoxSpecific(resultSet.getInt(2));
                boxFC.setTitleBox(resultSet.getString(3));
                boxFC.setObservationBox(resultSet.getString(4));
                boxFC.setResponsibleBox(resultSet.getString(5));
                boxes.add(boxFC);
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar as caixas F!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar as caixas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return boxes.iterator();
    }///fim do método searchBoxes

    /**
     * Busca livro FC pelo código
     *
     * @param codeBook caixa FC
     * @return <code>Book</code> livro FC
     */
    public Book searchBook(int codeBook) {
        BookFC bookFC = null;
        try {
            //cria PreparedStatement com visão de busva de livros de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_fc, titulo_livro, observacao_livro, ano,  codigo_caixa FROM ver_livro_fc(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                bookFC = new BookFC();
                bookFC.setCodeBook(resultSet.getInt(1));
                bookFC.setCodeBookSpecific(resultSet.getInt(2));
                bookFC.setTitleBook(resultSet.getString(3));
                bookFC.setObservationBook(resultSet.getString(4));
                bookFC.setYear(resultSet.getString(5));
                bookFC.setCodeBox(resultSet.getInt(6));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o livro CC!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return bookFC;
    }//fim do método searchBook

    /**
     * Busca por livro FC pelo código da caixa
     *
     * @param codeBox código caixa
     * @return <code>Iterator</code> com livro FC
     */
    public Iterator searchBooks(int codeBox) {
        List books = new ArrayList();

        try {
            //cria PreparedStatement com visão de busva de livros de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_livro, codigo_livro_fc,"
                    + "ano,titulo_livro, observacao_livro,codigo_caixa FROM ver_livro_fc_caixa(?) ;");
            seeker.setInt(1, codeBox);
            ResultSet resultSet = seeker.executeQuery();

            while (resultSet.next()) {
                BookFC bookFC = new BookFC();
                bookFC.setCodeBook(resultSet.getInt(1));
                bookFC.setCodeBookSpecific(resultSet.getInt(2));
                bookFC.setYear(resultSet.getString(3));
                bookFC.setTitleBook(resultSet.getString(4));
                bookFC.setObservationBook(resultSet.getString(5));
                bookFC.setCodeBox(resultSet.getInt(6));
                books.add(bookFC);
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
     * @param codeDocument código do documento
     * @return <code>Document</code> document FC
     */
    public Document searchDocument(int codeDocument) {
        DocumentFC documentFC = null;
        try {
            //cria preparedStatement com visão de busca de documento de almoxarifado
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento, codigo_documento_fc,titulo_documento, presente, observacao_documento, codigo_livro FROM ver_documento_fc(?) ;");
            seeker.setInt(1, codeDocument);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                documentFC = new DocumentFC();
                documentFC.setCodeDocument(resultSet.getInt(1));
                documentFC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentFC.setTitleDocument(resultSet.getString(3));
                documentFC.setPresent(resultSet.getBoolean(4));
                documentFC.setObservationDocument(resultSet.getString(5));
                documentFC.setCodeBook(resultSet.getInt(6));
                documentFC.addItems(new ItemDAO().getItems(documentFC.getCodeDocument()));
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar o documentos CC   !");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o documentos!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documentFC;
    }//fim do método searchDocument

    /**
     * Busca por todos os documentos FC
     *
     * @return <code>Iterator</code> documento FC
     */
    public Iterator searchDocuments() {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão  de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT * FROM ver_documento_fc ;");
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentFC documentFC = new DocumentFC();
                documentFC.setCodeDocument(resultSet.getInt(1));
                documentFC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentFC.setTitleDocument(resultSet.getString(3));
                documentFC.setPresent(resultSet.getBoolean(4));
                documentFC.setObservationDocument(resultSet.getString(5));
                documentFC.setCodeBook(resultSet.getInt(6));
                documentFC.addItems(new ItemDAO().getItems(documentFC.getCodeDocument()));

                documents.add(documentFC);
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
     * Busca todos os documentos do livro
     *
     * @param codeBook código do livro
     * @return <code>Iterator</code> com documentos FC
     */
    public Iterator searchDocuments(int codeBook) {
        List documents = new ArrayList();

        try {
            //cria preparedStatement com visão de busca de documento de formandos
            PreparedStatement seeker = connection.prepareStatement("SELECT codigo_documento,"
                    + " codigo_documento_fc, titulo_documento ,presente, observacao_documento,codigo_livro "
                    + " FROM ver_documento_fc_livro(?) ;");
            seeker.setInt(1, codeBook);
            ResultSet resultSet = seeker.executeQuery();

            //preenche lista com dados
            while (resultSet.next()) {
                DocumentFC documentFC = new DocumentFC();
                documentFC.setCodeDocument(resultSet.getInt(1));
                documentFC.setCodeDocumentSpecific(resultSet.getInt(2));
                documentFC.setTitleDocument(resultSet.getString(3));
                documentFC.setPresent(resultSet.getBoolean(4));
                documentFC.setObservationDocument(resultSet.getString(5));
                documentFC.setCodeBook(resultSet.getInt(6));
                documentFC.addItems(new ItemDAO().getItems(documentFC.getCodeDocument()));

                documents.add(documentFC);
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
     * Troca código da caixa pelo novo
     *
     * @param code código da caixa
     * @param newCode novo codigo especifico
     */
    public void changeCodeBox(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_caixa_fc (?,?)");
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
     * Muda código do livro FC
     *
     * @param code código do livro
     * @param newCode novo código do livro
     */
    public void changeCodeBook(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_livro_fc (?,?)");
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
     * Muda código do documento FC
     *
     * @param code código do documento
     * @param newCode novo código do documento
     */
    public void changeCodeDocument(int code, int newCode) {
        try {
            //busca documentos
            PreparedStatement changer = connection.prepareStatement("SELECT troca_codigo_documento_fc (?,?)");
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
     * Verifica se novo código da caixa FC está em uso
     *
     * @param oldBox código da caixa antigo FC
     * @param newBox código da caixa novo FC
     * @return <code>Boolean</code> verdadeiro se caixa existe
     */
    public boolean checkBoxCode(Box oldBox, Box newBox) {
        BoxFC oldBoxFC = (BoxFC) oldBox;
        BoxFC newBoxFC = (BoxFC) newBox;
        if (oldBoxFC.getCodeBoxSpecific() != newBoxFC.getCodeBoxSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_caixa_fc(?);");
                verify.setInt(1, newBoxFC.getCodeBoxSpecific());
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
    }//fim método checkBoxCode

    /**
     * Checa código do livro FC
     *
     * @param oldBook código antigo do livro FC
     * @param newBook código nvo do livro FC
     * @return <code>Boolean</code> verdadeiro se livro existe
     */
    public boolean checkBookCode(Book oldBook, Book newBook) {
        BookFC oldBookFC = (BookFC) oldBook;
        BookFC newBookFC = (BookFC) newBook;
        if (oldBookFC.getCodeBookSpecific() != newBookFC.getCodeBookSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_livro_fc(?,?);");
                verify.setInt(1, newBookFC.getCodeBox());
                verify.setInt(2, newBookFC.getCodeBookSpecific());
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
     * Checa se código do documento FC esttá em uso
     *
     * @param oldDocument antigo código do documento FC
     * @param newDocument novo código do documento FC
     * @return <code>Boolean</code> true se codigo do documento está em uso
     */
    public boolean checkDocumentCode(Document oldDocument, Document newDocument) {
        DocumentFC oldDocumentFC = (DocumentFC) oldDocument;
        DocumentFC newDocumentFC = (DocumentFC) newDocument;
        if (oldDocumentFC.getCodeDocumentSpecific() != newDocumentFC.getCodeDocumentSpecific()) {
            try {
                PreparedStatement verify = connection.prepareStatement("SELECT * FROM checa_codigo_documento_fc(?,?);");
                verify.setInt(1, newDocumentFC.getCodeBook());
                verify.setInt(2, newDocumentFC.getCodeDocumentSpecific());
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
     * Busca caixas pelos atributos
     *
     * @param title título
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(int codeBoxFC, String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa, codigo_caixa_fc  FROM busca_caixa_fc(?,?,?,?)");
            seacher.setInt(1, codeBoxFC);
            seacher.setString(2, title);
            seacher.setString(3, responsible);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                BoxFC box = new BoxFC();
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
    public Iterator searchBook(int codeBookFC, String title, String year, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro, codigo_livro_fc, ano  FROM busca_livro_fc(?,?,?,?)");
            seacher.setInt(1, codeBookFC);
            seacher.setString(2, title);
            seacher.setString(3, year);
            seacher.setString(4, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                BookFC book = new BookFC();
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
    public Iterator searchDocument(int codeDocumentFC, String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento, codigo_documento_fc FROM busca_documento_fc(?,?,?)");
            seacher.setInt(1, codeDocumentFC);
            seacher.setString(2, title);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                DocumentFC document = new DocumentFC();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
                document.setCodeDocumentSpecific(resultSet.getInt(6));
                document.addItems(new ItemDAO().getItems(document.getCodeDocument()));

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
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_livro_titulo_fc;");
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
     * Lista títulos documentos
     *
     * @return <code>List</code> com títulos
     */
    public List<String> listDocumentsTitles() {
        List<String> titles = new ArrayList();
        try {
            PreparedStatement list = connection.prepareStatement("SELECT * FROM lista_titulo_documento_fc;");
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
}//fim da classe FCDAO 

