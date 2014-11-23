/*- 
 * Classname:             ItemDAO.java 
 * 
 * Version information:   (versão) 
 * 
 * Date:                  23/08/2014 - 20:19:36 
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
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.util.Item;
import uni.uri.salmox.util.LogMaker;

/**
 * Descrição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ItemDAO {

    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    public ItemDAO() {
        takeConnection();
    }

    /**
     * Pega conexão com o banco de dados
     */
    public void takeConnection() {
        connectionDatabase = ConectionFactory.getConnection();
        connection = connectionDatabase.getConnection();
    }//fim do método takeConnection

    public List<Item> getItems(int codeDocument) {

        try {
            List<Item> items = new ArrayList();
            //cria PreparedStatement com função de  obtenção de itens
            PreparedStatement register = connection.prepareStatement("SELECT * FROM pega_item(? );");
            register.setInt(1, codeDocument);

            //retorna código de itens
            ResultSet resultset = register.executeQuery();


            while (resultset.next()) {

                items.add(Item.getItem(
                        resultset.getInt("codigo_item")));

            }
            return items;
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
        return null;
    }

    public void setItems(int codeDocument, List<Item> items) {
        try {
            PreparedStatement cleaner = connection.prepareStatement("SELECT * FROM limpa_item(?);");
            cleaner.setInt(1, codeDocument);

            cleaner.executeQuery();
            while (!items.isEmpty()) {
                PreparedStatement register = connection.prepareStatement("SELECT * FROM salva_itens(?,?);");
                register.setInt(1, codeDocument);
                register.setInt(2, items.remove(0).getCode());
                //retorna true se o código da caixa já está cadastrado
                ResultSet resultset = register.executeQuery();

            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível registrar caixa DG!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível registrar a caixa!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(sqlException);
        }
    }
}//fim da classe ItemDAO 

