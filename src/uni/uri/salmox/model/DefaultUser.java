
/*-
 * Classname:             DefaultUser.java
 *
 * Version information:   1.0
 *
 * Date:                  16/10/2012 - 13:17:38
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de Usuário padrão, herda de usuário
 * @see uni.uri.salmox.model.User
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DefaultUser extends User {

    //código do usuário padrão
    private int codeDefaultUser;

    /**
     * Construtor de usuário padrão sem argumentos
     */
    public DefaultUser() {
        super();
    }//fim do método construtor sem argumentos

    /**
     * Construtor de usuário padrão com código do usuário padrão
     * @param codeDefaultUser código usuário padrão
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome do usuário
     * @param email email
     */
    public DefaultUser(int codeDefaultUser, String userLogin, String password,
            String name, String email) {
        super(userLogin, password, name, email);
        this.codeDefaultUser = codeDefaultUser;
    }//fim do construtor com código do usuário padrão

    /**
     * Construtor de usuario padrão sem código do usuário padrão
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome
     * @param email email
     */
    public DefaultUser(String userLogin, String password, String name, String email) {
        super(userLogin, password, name, email);
    }//fim do construtor sem código de usuário padrão

    //sets
    /**
     * Define código do usuário padrão
     * @param codeDefaultUser código do usuário padrão
     */
    public void setCodeDefaultUser(int codeDefaultUser) {
        this.codeDefaultUser = codeDefaultUser;
    }//fim do método setCodeDefaultUser

    //gets
    /**
     * Obter código do usuário padrão
     * @return <code>integer</code> código usuário padrão
     */
    public int getCodeDefaultUserr() {
        return codeDefaultUser;
    }//fim do método getCodeDefaultUser
}//fim da classe Administrator

