
/*-
 * Classname:             GenericBoxDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 13:12:36
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
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.LogMaker;

/**
 * DAO com funções genéricas de caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class GenericBoxDAO {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public GenericBoxDAO() {
        takeConnection();
    }//fim do método construtor sem parãmetros

    /**
     * Pega conexão com o banco de dados
     */
    private void takeConnection() {
        connectionDatabase = ConectionFactory.getConnection();
        connection = connectionDatabase.getConnection();
    }//fim do método takeConnection

    /**
     * Retorna título de caixa apartir de código de livro
     *
     * @param codeBook código de livro
     * @return <code>Integer</code> código livro
     */
    public String searchBoxBook(int codeBook) {
        String titleBox = "";
        try {
            //cria PreparedStatement com função que retorna título da caixa
            PreparedStatement titler = connection.prepareStatement("SELECT * FROM retorna_caixa_de_documento(?);");
            titler.setInt(1, codeBook);
            ResultSet resultSet = titler.executeQuery();
            titleBox = resultSet.getString(1);
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar caixa pelo código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível obtér título da caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return titleBox;
    }//fim do método searchBoxABook

    /**
     * Deleta caixa do banco de dados
     *
     * @param box caixa
     */
    public void deleteBox(Box box) {
        try {
            //cria PreparedStatement com função que apaga o registro da caixa
            PreparedStatement deleter = connection.prepareStatement("SELECT exclui_caixa(?)");
            deleter.setInt(1, box.getCodeBox());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível deletar caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível deletar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método deleteBox

    /**
     * Deleta livro do banco de dados
     *
     * @param book livro
     */
    public void deleteBook(Book book) {
        try {
            //cria PreparedStatement com função que apaga o registro do livro
            PreparedStatement deleter = connection.prepareStatement("SELECT exclui_livro(?)");
            deleter.setInt(1, book.getCodeBook());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível deletar livro!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível deletar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método deleteBook

    /**
     * Deleta documento do banco de dados
     *
     * @param document documento
     */
    public void deleteDocument(Document document) {
        try {
            //cria PreparedStatement com função que apaga o registro do documento
            PreparedStatement deleter = connection.prepareStatement("SELECT exclui_documento(?)");
            deleter.setInt(1, document.getCodeDocument());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível deletar documento!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível deletar o documento!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método deleteDocument

    /**
     * Busca caixas pelos atributos
     *
     * @param title título
     * @param responsible responsáveis <b>pode ser nulo</b>
     * @param observation observação <b>pode ser nulo</b>
     * @return <code>Iterator</code> caixas
     */
    public Iterator searchBox(String title, String responsible, String observation) {
        //cria lista de caixas
        List<Box> boxes = new ArrayList();
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa FROM busca_caixa(?,?,?)");
            seacher.setString(1, title);
            seacher.setString(2, responsible);
            seacher.setString(3, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                Box box = new Box();
                box.setCodeBox(resultSet.getInt(1));
                box.setResponsibleBox(resultSet.getString(2));
                box.setObservationBox(resultSet.getString(3));
                box.setTitleBox(resultSet.getString(4));
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
    public Iterator searchBook(String title, String observation) {
        //cria lista de livros
        List<Book> bookes = new ArrayList();
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro FROM busca_livro(?,?)");
            seacher.setString(1, title);
            seacher.setString(2, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                Book book = new Book();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
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
    public Iterator searchDocument(String title, String observation) {
        //cria lista de documentos
        List<Document> documents = new ArrayList();
        try {
            //busca documentos
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_documento, "
                    + "titulo_documento, codigo_livro, presente, observacao_documento FROM busca_documento(?,?)");
            seacher.setString(1, title);
            seacher.setString(2, observation);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula documento
            while (resultSet.next()) {
                Document document = new Document();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setCodeBook(resultSet.getInt(3));
                document.setPresent(resultSet.getBoolean(4));
                document.setObservationDocument(resultSet.getString(5));
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
     * Busca caixa pelo código de caixa
     *
     * @param codeBox código caixa
     * @return <Box> caixa
     */
    public Box searchBox(int codeBox) {
        Box box = null;
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_caixa, "
                    + "responsaveis, observacao_caixa, titulo_caixa FROM busca_caixa(?)");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula caixas
            while (resultSet.next()) {
                box = new Box();
                box.setCodeBox(resultSet.getInt(1));
                box.setResponsibleBox(resultSet.getString(2));
                box.setObservationBox(resultSet.getString(3));
                box.setTitleBox(resultSet.getString(4));
            }
            //retorna iterator
            return box;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBox

    /**
     * Busca livro pelo código do livro
     *
     * @param codeBook código livro
     * @return <Book> livro
     */
    public Book searchBook(int codeBook) {
        Book book = null;
        try {
            //faz busca de livros
            PreparedStatement seacher = connection.prepareStatement("SELECT codigo_livro, "
                    + "titulo_livro, codigo_caixa, observacao_livro FROM busca_livro(?)");
            seacher.setInt(1, codeBook);
            ResultSet resultSet = seacher.executeQuery();
            //cria e popula livro
            while (resultSet.next()) {
                book = new Book();
                book.setCodeBook(resultSet.getInt(1));
                book.setTitleBook(resultSet.getString(2));
                book.setCodeBox(resultSet.getInt(3));
                book.setObservationBook(resultSet.getString(4));
            }
            return book;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar livro!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar o livro!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }//fim do método searchBook

    /**
     * Decobre categoria da caxa
     *
     * @param box caixa
     * @return <code>Box</code> caixa especificada
     */
    public Box discoveryCategory(Box box) {
        Box specificBox = null;
        int code = box.getCodeBox();

        specificBox = new ADAO().searchBox(code);

        if (specificBox == null) {
            specificBox = new AEDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new CCDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new EXDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new DACDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new DADDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new DGDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new DPDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new DSGDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new FDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new FCDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new FPDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new MCDAO().searchBox(code);
        }
        if (specificBox == null) {
            specificBox = new SAEDAO().searchBox(code);
        }

        return specificBox;
    }//fim do método discoveryCategory

    /**
     * Desativa caixa (para exclusão por lista)
     *
     * @param codeBox código da caixa
     */
    public void desactiveBox(int codeBox) {
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT desativa_caixa(?)");
            seacher.setInt(1, codeBox);
            seacher.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível desativar caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível excluir a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método desactiveBox

    /**
     * Verifica de caixa está ativa (não excluida por lista)
     *
     * @param codeBox código da caixa
     * @return <code>Boolean </code> ativa
     */
    public boolean checkActiveBox(int codeBox) {
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT * FROM checa_caixa_ativa(?);");
            seacher.setInt(1, codeBox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível identificar estado da caixa!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível identificar estado da caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return false;
    }//fim do método desactiveBox

    /**
     * Busca o número de documentos na caixa
     *
     * @param codebox código caixa
     * @return <code>Integer</code> número de documentos
     */
    public int countDocuments(int codebox) {
        try {
            //faz busca de caixas
            PreparedStatement seacher = connection.prepareStatement("SELECT * FROM conta_documento_caixa(?);");
            seacher.setInt(1, codebox);
            ResultSet resultSet = seacher.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível converter código!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível  converter código!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return 0;
    }//fim do método countDocuments
}//fim da classe GenericBoxDAO

