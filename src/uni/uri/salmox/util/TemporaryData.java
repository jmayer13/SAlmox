/*-
 * Classname:             TemporaryData.java
 *
 * Version information:   1.0
 *
 * Date:                  16/05/2013 - 15:56:31
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import uni.uri.salmox.model.User;

/**
 * Dados temporários
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class TemporaryData {

    //usuário logado
    private static User user;

    /**
     * Define usuário atualmente logado
     *
     * @param user usuário logado
     */
    public static void setUser(User user) {
        TemporaryData.user = user;
    }//fim dio método setUser

    /**
     * Obtêm usuário atualmente logado
     *
     * @return <code>User</code> usuário logado
     */
    public static User getUser() {
        //para testes
        if (user == null) {
            user = new User();
            user.setUserLogin("teste");
        }
        return user;
    }//fim do método getUser
}//fim da classe TemporaryData 

