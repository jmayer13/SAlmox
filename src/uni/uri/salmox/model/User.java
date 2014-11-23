
/*-
 * Classname:             User.java
 *
 * Version information:   1.0
 *
 * Date:                  10/10/2012 - 13:20:44
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import uni.uri.salmox.util.CriptoSHA;

/**
 * Superclasse modelo de usuário
 * @see uni.uri.salmox.util.CriptoMD5
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class User implements Comparable {

    //código do usuário
    private int codeUser;
    //login do usuário
    private String userLogin;
    //senha do usuário em MD5
    private String password;
    //nome do usuário
    private String name;
    //email do usuário
    private String email;

    /**
     * Construtor sem argumentos
     */
    public User() {
    }//fim do construtor sem argumentos

    /**
     * Construtor de usuário com código do usuário
     * @param codeUser código do usuário
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome do usuário
     * @param email email
     */
    public User(int codeUser, String userLogin, String password, String name, String email) {
        this.codeUser = codeUser;
        this.userLogin = userLogin;
        this.password = CriptoSHA.cripto(password);
        this.name = name;
        this.email = email;
    }//fim do construtor com código do usuário

    /**
     * Construtor de usuário sem código de usuário
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome do usuário
     * @param email email
     */
    public User(String userLogin, String password, String name, String email) {
        this.userLogin = userLogin;
        this.password = CriptoSHA.cripto(password);
        this.name = name;
        this.email = email;
    }//fim do construtor sem código do usuário

    //sets
    /**
     * Define código do usuário
     * @param codeUser código do usuário
     */
    public void setCodeUser(int codeUser) {
        this.codeUser = codeUser;
    }//fim do método setCodeUser

    /**
     * Define login do usuário
     * @param userLogin login do usuário
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }//fim do método setUserLogin

    /**
     * Define senha e criptografa senha em MD54
     * @param password senha
     */
    public void setPassword(String password) {
        this.password = CriptoSHA.cripto(password);
    }//fim do método setPassword

    /**
     * Define senha já em MD5
     * @param password senha
     */
    public void setPasswordMD5(String password) {
        this.password = password;
    }//fim do método setPassword

    /**
     * Define nome do usuário
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }//fim do método setName

    /**
     * Define email
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }//fim do método setEmail

    //gets
    /**
     * Obtêm código do usuário
     * @return <code>integer</code> código do usuário
     */
    public int getCodeUser() {
        return codeUser;
    }//fim do método getCodeUser

    /**
     * Obtêm login do usuário
     * @return <code>String</code> login do usuário
     */
    public String getUserLogin() {
        return userLogin;
    }//fim do método getUserLogin

    /**
     * Obtêm senha
     * @return <code>String</code>senha
     */
    public String getPassword() {
        return password;
    }//fim do método getPassword

    /**
     * Obter nome do usuário
     * @return <code>String</code>
     */
    public String getName() {
        return name;
    }//fim do método getName

    /**
     * Obter email
     * @return <code>String</code> email
     */
    public String getEmail() {
        return email;
    }//fim do método getEmail

    /**
     * Método para comparação de usuários
     * @param user usuário
     * @return <code>Integer</code> parâmetro de comparação seguindo a convenção da interface Comparable
     */
    public int compareTo(Object user) {
        User otherUser = (User) user;
        if (this instanceof Administrator && otherUser instanceof DefaultUser) {
            return 1;
        } else if (this instanceof DefaultUser && otherUser instanceof Administrator) {
            return -1;
        } else {
            return this.getName().compareTo(otherUser.getName());
        }
    }//fim do método compareTo
}//fim da classe User

