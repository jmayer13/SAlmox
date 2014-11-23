
/*-
 * Classname:             CriptoSHA.java
 *
 * Version information:   1.0
 *
 * Date:                  10/10/2012 - 13:30:50
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

/**
 * Classse responsável pela criptografia SHA1 das senhas
 *
 * @see java.security.MessageDigest
 * @see java.security.NoSuchAlgorithmException
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class CriptoSHA {

    //Armazena temporariamente a senha criptografada
    private static StringBuilder passwordTemporary;

    /**
     * Método construtor sem argumentos
     */
    public CriptoSHA() {
    }//fim do método contrutor

    /**
     * Método responsável pela criptografia SHA1
     *
     * @param password senha que será criptografada
     * @return <code>String</code> com senha criptografada
     */
    public static String cripto(String password) {

        //inicializa StringBuilder para armazenamento temporario
        passwordTemporary = new StringBuilder();
        try {
            //seleciona tipo de criptografia
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            //criptografa bytes da string
            byte[] result = mDigest.digest(password.getBytes());

            //adiciona dados criptografados a StringBuilder();
            for (int i = 0; i < result.length; i++) {
                passwordTemporary.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            System.err.println("Erro ao criptografar!");
            noSuchAlgorithmException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: ao criptografar!", "Erro!", JOptionPane.WARNING_MESSAGE);
            LogMaker.create(noSuchAlgorithmException);
            return "";
        }
        //retorna string criptografada
        return passwordTemporary.toString();
    }//fim do método cripto

    //método para teste
    public static void main(String[] args) {
        System.out.println(CriptoSHA.cripto("password"));
    }
}//fim da classe CriptoSHA

