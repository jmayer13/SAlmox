
/*-
 * Classname:             ConnectionDatabase.java
 *
 * Version information:   1.1
 *
 * Date:                  07/01/2013 - 13:18:07
 *                        03/07/2014 - 20:21:00
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.PreferencesManager;

/**
 * Classe responsável pela conexão com o banco de dados
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ConnectionDatabase {

    //url completa para o banco
    private final String databaseUrl;
    //local do banco (ip)
    private final String localDatabase;
    //nome da base de dados
    private final String DATABASE_NAME = "SAlmox";
    //nome de usuário
    private final String username;
    //senha do usuário
    private final String password;
    //classe Preferences que pega dados
    private final Preferences preferences;
    //conexão com o banco
    private Connection connection;

    /**
     * Construtor privado de ConnectionDatabase
     */
    private ConnectionDatabase() {
        //obtêm os dados de preferences
        preferences = PreferencesManager.getPreferencesSystem();
        localDatabase = preferences.get(PreferencesManager.KEY_LOCAL_DATABASE, "localhost");
        username = preferences.get(PreferencesManager.KEY_DATABASE_USER, "postgres");
        password = preferences.get(PreferencesManager.KEY_DATABASE_PASSWORD, "123456");
        databaseUrl = "jdbc:postgresql://" + localDatabase + "/" + DATABASE_NAME;

        //carrega
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Erro: classe do Driver do banco não encontrada!");
            classNotFoundException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Driver do banco não encontrado!",
                    "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(classNotFoundException);
        }

    }//fim do construtor de ConnectionDatabase

    /**
     * Conecta com o banco, mostra exceção se falha
     *
     * @return <code>Connection</code> conexão
     */
    public Connection getConnection() {
        //estabelece uma conexão com o banco
        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possivel realizar uma conexão com o banco de dados!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possivel conectar-se ao banco de dados!",
                    "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return connection;
    }//fim do método getConnection

    /**
     * Fecha a conexão com o banco de dados
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                //fecha conexão
                connection.close();
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possivel fechar a conexão com o banco de dados!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possivel fechar a conexão ao banco de dados!",
                    "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método close

    public static void main(String[] args) {
        System.out.print("ddd");
        (new ConnectionDatabase()).getConnection();
    }
}//fim da classe ConnectionDatabase

