
/*-
 * Classname:             Subjectinterface.java
 *
 * Version information:   (versão)
 *
 * Date:                  15/03/2013 - 15:08:35
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

/**
 * Subject para controle e atualizações de interfaces
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public interface SubjectInterface {

    /**
     * Registra observador de interface
     *
     * @param observerInterface observador
     */
    public void registerObserver(ObserverInterface observerInterface);

    /**
     * Remove observador de interface
     *
     * @param observerInterface obdervador
     */
    public void removeObserver(ObserverInterface observerInterface);

    /**
     * Notifica observador de interface
     *
     * @param active se jabela está ativa
     * @param object objeto a ser atualizado
     */
    public void notifyObservers(boolean active, Object object);
}//fim da classe Subjectinterface

