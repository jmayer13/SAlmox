
/*-
 * Classname:             ObserverInterface.java
 *
 * Version information:   1.0
 *
 * Date:                  15/03/2013 - 15:08:17
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

/**
 * Observer para controle e atualização de interfaces
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public interface ObserverInterface {

    /**
     * Atualiza observador com novos dados
     *
     * @param active se a janela já concluiu true se não false
     * @param object objeto com atualização
     */
    public void update(boolean active, Object object);
}//fim da classe ObserverInterface

