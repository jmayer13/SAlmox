
/*-
 * Classname:             Administrator.java
 *
 * Version information:   1.0
 *
 * Date:                  11/10/2012 - 13:18:00
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

/**
 * Classe modelo de Administrador, herda de usuário
 *
 * @see uni.uri.salmox.model.User
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Administrator extends User {

    //código do administrador
    private int codeAdministrator;

    /**
     * Construtor de administrador sem argumentos
     */
    public Administrator() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de administrador com código do administrador
     *
     * @param codeAdministrator código do administrador
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome do usuário
     * @param email email
     */
    public Administrator(int codeAdministrator, String userLogin, String password,
            String name, String email) {
        super(userLogin, password, name, email);
        this.codeAdministrator = codeAdministrator;
    }//fim do construtor com código do administrador

    /**
     * Construtor de administrador sem código do administrador
     *
     * @param userLogin login do usuário
     * @param password senha
     * @param name nome
     * @param email email
     */
    public Administrator(String userLogin, String password, String name, String email) {
        super(userLogin, password, name, email);
    }//fim do construtor sem código do administrador

    //sets
    /**
     * Define cádigo administrator
     *
     * @param codeAdministrator código do administrador
     */
    public void setCodeAdministrator(int codeAdministrator) {
        this.codeAdministrator = codeAdministrator;
    }//fim do método setCodeAdministrator

    //gets
    /**
     * Obtêm código do administrador
     *
     * @return <code>Integer</code> código administrador
     */
    public int getCodeAdministrator() {
        return codeAdministrator;
    }//fim do método getCodeAdministrator
}//fim da classe Administrator

