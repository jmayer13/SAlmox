
/*-
 * Classname:             RequestDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 13:16:22
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
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Request;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.TemporaryData;

/**
 * DAO de solicitações
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class RequestDAO {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public RequestDAO() {
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
     * Armazena registros de solicitações no banco de dados
     *
     * @param request solicitação
     */
    public void storeRequest(Request request) {
        try {
            PreparedStatement storer = connection.prepareStatement("SELECT * FROM armazena_solicitacao(?,?,?);");
            DefaultUser defaultUser = (DefaultUser) TemporaryData.getUser();
            storer.setInt(1, defaultUser.getCodeDefaultUserr());
            storer.setString(2, request.getMotiveRequest());
            storer.setString(3, request.getObservationRequest());
            ResultSet resultSet = storer.executeQuery();
            //pega código da solicitação recem cadastrada e adiciona documentos solicitados
            int codeRequest = 0;
            while (resultSet.next()) {
                codeRequest = resultSet.getInt(1);
            }
            List<Document> codeDocuments = request.getListDocumentRequest();
            for (int i = 0; i < codeDocuments.size(); i++) {
                int codeDocument = codeDocuments.get(i).getCodeDocument();
                PreparedStatement filler = connection.prepareStatement("SELECT solicita_documento(?,?); ");
                filler.setInt(1, codeRequest);
                filler.setInt(2, codeDocument);
                filler.executeQuery();
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível armazenar solicitação!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível armazenar solicitação!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método storeRequest

    /**
     * Busca solicitações do usuário
     *
     * @param codeDefaultUser código do usuário
     * @return <code>Iterator</code> com solicitações do usuário
     */
    public Iterator searchRequesByUser(int codeDefaultUser) {
        List<Request> requests = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_solicitacao, "
                    + "status, data_solicitacao, observacao_solicitacao, motivo_solicitacao, codigo_usuario_padrao "
                    + "FROM ver_solicitacao_usuario(?);");
            searcher.setInt(1, codeDefaultUser);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setCodeRequest(resultSet.getInt(1));
                request.setStatus(resultSet.getBoolean(2));
                request.setDateRequest(resultSet.getDate(3));
                request.setObservationRequest(resultSet.getString(4));
                request.setMotiveRequest(resultSet.getString(5));
                UserDAO userDAO = new UserDAO();
                request.setDefaultUser(userDAO.searchDefaultUser(resultSet.getInt(6)));
                requests.add(request);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitação por usuário!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitação!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return requests.iterator();
    }//fim do método searchRequesByUser

    /**
     * Cancela a solicitação
     *
     * @param request solicitação
     */
    public void cancelRequest(Request request) {
        PreparedStatement deleter;
        try {
            deleter = connection.prepareStatement("SELECT cancelar_solicitacao(?);");
            deleter.setInt(1, request.getCodeRequest());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível deletar solicitação!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível deletar a solicitação!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método cancelRequest

    /**
     * Busca solicitações sbertas
     *
     * @return <code>Iterator</code> com solicitações
     */
    public Iterator searchOpenRequest() {
        List<Request> requests = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT * FROM ver_solicitacao_aberta_administrador;");
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setCodeRequest(resultSet.getInt(1));
                request.setStatus(true);
                request.setDateRequest(resultSet.getDate(3));
                request.setObservationRequest(resultSet.getString(4));
                request.setMotiveRequest(resultSet.getString(5));
                UserDAO userDAO = new UserDAO();
                request.setDefaultUser(userDAO.searchDefaultUser(resultSet.getInt(2)));
                requests.add(request);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitações abertas!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitações abertas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return requests.iterator();
    }//fim do método searchRequesByUser

    /**
     * Busca documentos da solicitação
     *
     * @param codeRequest código da solicitação
     * @return <code>Iterator</code> com documentos
     */
    public Iterator getDocuments(int codeRequest) {
        List<Document> documents = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_documento, titulo_documento, codigo_livro, presente, observacao_documento FROM lista_documento_solicitacao(?);");
            searcher.setInt(1, codeRequest);
            ResultSet resultSet = searcher.executeQuery();
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
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar documentos de solicitações abertas!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar documentos de solicitações abertas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents.iterator();
    }//fim do método getDocuments

    /**
     * Busca documentos da solicitação
     *
     * @param codeRequest código da solicitação
     * @return <code>Iterator</code> com documentos
     */
    public List<Document> listDocuments(int codeRequest) {
        List<Document> documents = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_documento, titulo_documento, codigo_livro, presente, observacao_documento FROM lista_documento_solicitacao(?);");
            searcher.setInt(1, codeRequest);
            ResultSet resultSet = searcher.executeQuery();
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
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar documentos de solicitações abertas!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar documentos de solicitações abertas!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents;
    }//fim do método getDocuments

    /**
     * Disabilita a solicitação
     *
     * @param request solicitação
     */
    public void disableRequest(Request request) {
        PreparedStatement deleter;
        try {
            deleter = connection.prepareStatement("SELECT finaliza_solicitacao(?);");
            deleter.setInt(1, request.getCodeRequest());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível finalizar solicitação!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível finalizar a solicitação!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim dop método disableRequest
}//fim da classe RequestDAO

