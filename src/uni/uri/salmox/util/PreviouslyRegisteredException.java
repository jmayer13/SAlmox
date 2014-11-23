
/*-
 * Classname:             PreviouslyRegisteredException.java
 *
 * Version information:   1.0
 *
 * Date:                  09/01/2013 - 15:36:26
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

/**
 * Exceção lançada em caso de código já cadastrado
 *
 * @see java.lang.Exception;
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class PreviouslyRegisteredException extends Exception {

    /**
     * Construtor de PreviouslyRegisteredException sem argumentos
     */
    public PreviouslyRegisteredException() {
        super();
    }//fim do construtor sem argumentos

    /**
     * Construtor de PreviouslyRegisteredException com menssagem
     *
     * @param message mensagem
     */
    public PreviouslyRegisteredException(String message) {
        super(message);
    }//fim do construtor com menssagem
}//fim da classe PreviouslyRegisteredException

