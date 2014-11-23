
/*-
 * Classname:             UserDAO.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 13:15:00
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
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.User;
import uni.uri.salmox.util.LogMaker;

/**
 * DAO dos usuários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class UserDAO {

    //classe responsável pela conexão com o banco de dados
    private ConnectionDatabase connectionDatabase;
    //conexão com o banco
    private Connection connection;

    /**
     * Método construtor sem parâmetros
     */
    public UserDAO() {
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
     * Verifica-se o usuario está cadastrado e se a categoria do usuário
     *
     * @param user usuário
     * @return <code>Integer</code> <br />0= login não encontrado <br />1=senha
     * incorreta <br />2=user padrão <br />3=adiministrador <br />
     */
    public int login(User user) {
        int result = 0;
        try {
            PreparedStatement checker = connection.prepareStatement("SELECT * FROM logar(?,?);");
            checker.setString(1, user.getUserLogin());
            checker.setString(2, user.getPassword());
            ResultSet resultSet = checker.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            connectionDatabase.closeConnection();
            System.err.println("Erro: não foi possível consultar login!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível fazer login!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return result;
    }//fim do método login

    /**
     * Busca usuários
     *
     * @return <code>Iterator</code> com todos os usuários
     */
    public Iterator searchUsers() {
        List users = new ArrayList();
        Iterator administrators = this.searchAdministrator();
        Iterator defaults = this.searchDefaultUser();
        while (administrators.hasNext()) {
            users.add(administrators.next());
        }
        while (defaults.hasNext()) {
            users.add(defaults.next());
        }
        return users.iterator();
    }//fim do método searchUsers

    /**
     * Busca administradores
     *
     * @return <code>Iterator</code> com administradores
     */
    public Iterator searchAdministrator() {
        List<Administrator> administrators = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT * FROM ver_administradores;");
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                Administrator administrator = new Administrator();
                administrator.setCodeUser(resultSet.getInt(1));
                administrator.setCodeAdministrator(resultSet.getInt(2));
                administrator.setUserLogin(resultSet.getString(3));
                administrator.setName(resultSet.getString(4));
                administrator.setEmail(resultSet.getString(5));
                administrators.add(administrator);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar administradores!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar sdministradores!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return administrators.iterator();
    }//fim do método searchAdministrator

    /**
     * Busca usuários padrão
     *
     * @return <code>Iterator</code> com usuários padrão
     */
    public Iterator searchDefaultUser() {
        List<DefaultUser> defaultUsers = new ArrayList();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT * FROM ver_usuarios_padrao;");
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                DefaultUser defaultUser = new DefaultUser();
                defaultUser.setCodeUser(resultSet.getInt(1));
                defaultUser.setCodeDefaultUser(resultSet.getInt(2));
                defaultUser.setUserLogin(resultSet.getString(3));
                defaultUser.setName(resultSet.getString(4));
                defaultUser.setEmail(resultSet.getString(5));
                defaultUsers.add(defaultUser);
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar usuários padrão!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar usuários padrão!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return defaultUsers.iterator();
    }//fim do método searchDefaultUser

    /**
     * Busca administrador pelo código
     *
     * @param codeAdministrator código de administrador
     * @return <code>Administrator</code> administrador
     */
    public Administrator searchAdministrator(int codeAdministrator) {
        Administrator administrator = new Administrator();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_usuario, codigo_administrador , usuario_login,  nome, email FROM retorna_administrador(?);");
            searcher.setInt(1, codeAdministrator);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                administrator.setCodeUser(resultSet.getInt(1));
                administrator.setCodeAdministrator(resultSet.getInt(2));
                administrator.setUserLogin(resultSet.getString(3));
                administrator.setName(resultSet.getString(4));
                administrator.setEmail(resultSet.getString(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar administrador!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar administrador!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return administrator;
    }//fim do método searchAdministrator

    /**
     * Busca usuário padrão pelo código
     *
     * @param codeDefaultUser código de usuário padrão
     * @return <code>DefaultUser</code>
     */
    public DefaultUser searchDefaultUser(int codeDefaultUser) {
        DefaultUser defaultUser = new DefaultUser();
        try {

            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_usuario, codigo_usuario_padrao, usuario_login,  nome, email FROM retorna_usuario_padrao(?);");
            searcher.setInt(1, codeDefaultUser);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                defaultUser.setCodeUser(resultSet.getInt(1));
                defaultUser.setCodeDefaultUser(resultSet.getInt(2));
                defaultUser.setUserLogin(resultSet.getString(3));
                defaultUser.setName(resultSet.getString(4));
                defaultUser.setEmail(resultSet.getString(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar usuário padrão!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar usuário padrão!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return defaultUser;
    }//fim do método searchDefaultUser

    /**
     * Busca administrador pelo código
     *
     * @param login login de administrador
     * @return <code>Administrator</code> administrador
     */
    public Administrator searchAdministrator(String login) {
        Administrator administrator = new Administrator();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_usuario, codigo_administrador , usuario_login,  nome, email FROM retorna_administrador_login(?);");
            searcher.setString(1, login);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                administrator.setCodeUser(resultSet.getInt(1));
                administrator.setCodeAdministrator(resultSet.getInt(2));
                administrator.setUserLogin(resultSet.getString(3));
                administrator.setName(resultSet.getString(4));
                administrator.setEmail(resultSet.getString(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar administrador!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar administrador!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return administrator;
    }//fim do método searchAdministrator

    /**
     * Busca usuário padrão pelo código
     *
     * @param login login de usuário padrão
     * @return <code>DefaultUser</code>
     */
    public DefaultUser searchDefaultUser(String login) {
        DefaultUser defaultUser = new DefaultUser();
        try {
            PreparedStatement searcher = connection.prepareStatement("SELECT codigo_usuario, codigo_usuario_padrao, usuario_login,  nome, email FROM retorna_usuario_padrao_login(?);");
            searcher.setString(1, login);
            ResultSet resultSet = searcher.executeQuery();
            while (resultSet.next()) {
                defaultUser.setCodeUser(resultSet.getInt(1));
                defaultUser.setCodeDefaultUser(resultSet.getInt(2));
                defaultUser.setUserLogin(resultSet.getString(3));
                defaultUser.setName(resultSet.getString(4));
                defaultUser.setEmail(resultSet.getString(5));
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível buscar usuário padrão!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível buscar usuário padrão!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
        return defaultUser;
    }//fim do método searchDefaultUser

    /**
     * Cadastra usuário
     *
     * @param user usuário Adimistrador e usuário padrão
     */
    public void registerUser(User user) {
        int typeOfUser = 0;
        //verifica se é administrador ou usuário padrão
        if (user instanceof Administrator) {
            typeOfUser = 1;
        }
        if (user instanceof DefaultUser) {
            typeOfUser = 2;
        }
        try {
            PreparedStatement register = connection.prepareStatement("SELECT cadastra_usuario(?,?,?,?,?);");
            register.setInt(1, typeOfUser);
            register.setString(2, user.getUserLogin());
            register.setString(3, user.getPassword());
            register.setString(4, user.getName());
            register.setString(5, user.getEmail());
            register.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível cadastrar usuário!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível cadastrar usuário!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método registerUser

    /**
     * Edita senha e e-mail de usuário
     *
     * @param user usuário
     */
    public void editUser(User user) {
        try {
            PreparedStatement editer;
            if (user.getPassword() != null) {
                editer = connection.prepareStatement("SELECT edita_usuario(?,?,?,?,?);");
                editer.setString(5, user.getPassword());
            } else {
                editer = connection.prepareStatement("SELECT edita_usuario_sem_senha(?,?,?,?);");
            }
            editer.setInt(1, user.getCodeUser());
            editer.setString(2, user.getName());
            editer.setString(3, user.getUserLogin());
            editer.setString(4, user.getEmail());
            editer.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível editar usuário!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível editar usuário!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método editUser

    /**
     * Apaga o registro do usuário
     *
     * @param user
     */
    public void deleteUser(User user) {
        try {
            PreparedStatement deleter = connection.prepareStatement("SELECT exclui_usuario(?);");
            deleter.setInt(1, user.getCodeUser());
            deleter.executeQuery();
        } catch (SQLException sqlException) {
            System.err.println("Erro: não foi possível excluir usuário!");
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível excluir usuário!", "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(sqlException);
        }
    }//fim do método deleteUser

    /**
     * Verifica se login já está sendo usado
     *
     * @param login nome de usuário
     */
    public boolean checkLogin(String login) {
        Iterator users = searchUsers();
        while (users.hasNext()) {
            User user = (User) users.next();
            if (user.getUserLogin().equalsIgnoreCase(login)) {
                return true;
            }
        }
        return false;
    }//fim do método checkLogin
}//fim da classe UserDAO

