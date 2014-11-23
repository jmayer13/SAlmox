/*- 
 * Classname:             ConectDatabase.java 
 * 
 * Version information:   1.1 
 * 
 * Date:                  03/07/2014 - 20:19:38 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.DAO;

import java.lang.reflect.Constructor;
import javax.swing.JOptionPane;
import uni.uri.salmox.util.LogMaker;

/**
 * Fábrica de conexões com o banco
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ConectionFactory {

    //instãncia da classe que faz conexão
    private static ConnectionDatabase connectionDatabase;

    /**
     * Obtêm a classe que representa a conexão com o banco se não há conexão ela
     * é criada
     *
     * @return ConnectionDatabase classe de conexão com banco
     */
    public static ConnectionDatabase getConnection() {
        if (connectionDatabase == null) {
            try {
                Constructor constructors[] = ConnectionDatabase.class.getDeclaredConstructors();
                constructors[0].setAccessible(true);
                connectionDatabase = (ConnectionDatabase) constructors[0].newInstance();
            } catch (Exception exception) {
                System.err.println("Erro: não foi possivel criar a conexão com o banco de dados!");
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro: não foi possivel criar a conexão com o banco de dados!",
                        "Erro!", JOptionPane.WARNING_MESSAGE);
                LogMaker.create(exception);
            }
        }
        return connectionDatabase;
    }//fim do método getConnection
}//fim da classe ConectDatabase 
