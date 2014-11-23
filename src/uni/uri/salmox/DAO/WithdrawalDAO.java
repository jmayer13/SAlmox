
/*-
 * Classname:             WithdrawalDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 13:17:38
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
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.Withdrawal;
import uni.uri.salmox.util.LogMaker;

/**
 * DAO de requisição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WithdrawalDAO {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public WithdrawalDAO() {
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
     * Armazena retirada de documento
     *
     * @param withdrawal requisição
     */
    public int withdrawsDocument(Withdrawal withdrawal) {
        try {
            PreparedStatement register = connection.prepareStatement("SELECT * FROM armazena_retirada(?,?,?,?,?,?);");
            register.setInt(1, withdrawal.getCodeAdministrator());
            register.setString(2, withdrawal.getMotiveWithdrawal());
            register.setString(3, withdrawal.getSolicitor());
            register.setString(4, withdrawal.getDeliveryGuy());
            register.setString(5, withdrawal.getObservationWithdrawal());
            register.setDate(6, withdrawal.getDateWithdrawal());
            ResultSet resultSet = register.executeQuery();
            int codeWithdrawal = 0;
            while (resultSet.next()) {
                codeWithdrawal = resultSet.getInt(1);
            }

            //preenche requisição com documentos
            List<Document> codeDocuments = withdrawal.getListDocument();
            for (int i = 0; i < codeDocuments.size(); i++) {
                int codeDocument = codeDocuments.get(i).getCodeDocument();
                PreparedStatement filler = connection.prepareStatement("SELECT retira_documento(?,?);");
                filler.setInt(1, codeWithdrawal);
                filler.setInt(2, codeDocument);
                filler.executeQuery();
            }
            return codeWithdrawal;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível armaxenar retirada!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível armaxenar retirada!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return 0;
    }//fim do método withdrawsDocument

    /**
     * Entrega documentos
     *
     * @param withdrawal requisição
     */
    public void returnsDocument(Withdrawal withdrawal) {
        try {
            PreparedStatement returner = connection.prepareStatement("SELECT * FROM entrega_documento(?);");
            returner.setInt(1, withdrawal.getCodeWithDrawal());
            returner.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível entregar de documentos!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível entregar documentos!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método returnsDocument

    /**
     * Busca retiradas de documentos abertas
     *
     * @return <code>Iterator</code> com as retiradas abertas
     */
    public Iterator searchOpenWithdrawal() {
        List<Withdrawal> withdrawals = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT * FROM ver_retirada_abertas;");
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {

                Withdrawal withdrawal = new Withdrawal();
                withdrawal.setCodeWithDrawal(resultSet.getInt(1));
                withdrawal.setSolicitor(resultSet.getString(2));
                withdrawal.setDateWithdrawal(resultSet.getDate(3));
                withdrawal.setObservationWithdrawal(resultSet.getString(4));
                withdrawal.setMotiveWithdrawal(resultSet.getString(5));
                withdrawal.setStatus(true);
                withdrawal.setListDocument(getDocuments(withdrawal.getCodeWithDrawal()));

                //armazena código de administrador´para busca
                int codeAdministrator = resultSet.getInt(6);
                withdrawal.setDeliveryGuy(resultSet.getString(7));
                //busca administrador
                UserDAO userDAO = new UserDAO();
                withdrawal.setAdministrator(userDAO.searchAdministrator(codeAdministrator));

                withdrawals.add(withdrawal);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitações!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitações!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return withdrawals.iterator();
    }//fim do método searchWithdrawal

    /**
     * Obtêm documentos da retirada
     *
     * @param codeWithdrawal código da retirada
     * @return <code>List</code> com documentos
     */
    public List<Document> getDocuments(int codeWithdrawal) {
        List<Document> documents = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_documento,  titulo_documento, presente, observacao_documento, codigo_livro FROM ver_retirada_documento(?);");
            searcher.setInt(1, codeWithdrawal);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                Document document = new Document();
                document.setCodeDocument(resultSet.getInt(1));
                document.setTitleDocument(resultSet.getString(2));
                document.setPresent(resultSet.getBoolean(3));
                document.setObservationDocument(resultSet.getString(4));
                document.setCodeBook(resultSet.getInt(5));

                documents.add(document);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitações!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitações!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return documents;
    }//fim do método getDocuments

    /**
     * Obtêm retiradas fechadas
     *
     * @return <code>Iterator</code> retiradas fechadas
     */
    public Iterator searchCloseWithdrawal() {
        List<Withdrawal> withdrawals = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT * FROM ver_retirada_fechadas;");
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {

                Withdrawal withdrawal = new Withdrawal();
                withdrawal.setCodeWithDrawal(resultSet.getInt(1));
                withdrawal.setSolicitor(resultSet.getString(2));
                withdrawal.setDateWithdrawal(resultSet.getDate(3));
                withdrawal.setObservationWithdrawal(resultSet.getString(4));
                withdrawal.setMotiveWithdrawal(resultSet.getString(5));

                //carrega documentos
                withdrawal.setListDocument(getDocuments(withdrawal.getCodeWithDrawal()));

                //armazena código de administrador´para busca
                int codeAdministrator = resultSet.getInt(6);
                withdrawal.setDeliveryGuy(resultSet.getString(7));
                withdrawal.setStatus(resultSet.getBoolean(8));
                withdrawal.setDateDelivery(resultSet.getDate(9));
                //busca administrador
                UserDAO userDAO = new UserDAO();
                withdrawal.setAdministrator(userDAO.searchAdministrator(codeAdministrator));
                withdrawals.add(withdrawal);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitações!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitações!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return withdrawals.iterator();
    }//fim do método searchCloseWithdrawal

    /**
     * Obtêm retiradas fechadas de usuário
     *
     * @param nameUser nome do usuário
     * @return <code>Iterator</code> retiradas fechadas
     */
    public Iterator searchUserCloseWithdrawal(String nameUser) {
        List<Withdrawal> withdrawals = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_retirada, solicitante, data_retirada,"
                    + "    observacao_retirada, motivo, codigo_administrador, nome_retirou, status, data_entrega FROM ver_retirada_solicitante(?);");
            searcher.setString(1, nameUser);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {

                Withdrawal withdrawal = new Withdrawal();
                withdrawal.setCodeWithDrawal(resultSet.getInt(1));
                withdrawal.setSolicitor(resultSet.getString(2));
                withdrawal.setDateWithdrawal(resultSet.getDate(3));
                withdrawal.setObservationWithdrawal(resultSet.getString(4));
                withdrawal.setMotiveWithdrawal(resultSet.getString(5));
                //carrega documentos
                withdrawal.setListDocument(getDocuments(withdrawal.getCodeWithDrawal()));

                //armazena código de administrador´para busca
                int codeAdministrator = resultSet.getInt(6);
                withdrawal.setDeliveryGuy(resultSet.getString(7));
                withdrawal.setStatus(resultSet.getBoolean(8));
                withdrawal.setDateDelivery(resultSet.getDate(9));
                //busca administrador
                UserDAO userDAO = new UserDAO();
                withdrawal.setAdministrator(userDAO.searchAdministrator(codeAdministrator));

                withdrawals.add(withdrawal);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar solicitações!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar solicitações!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return withdrawals.iterator();
    }//fim do método searchUserCloseWithdrawal
}//fim da classe WithdrawalDAO

