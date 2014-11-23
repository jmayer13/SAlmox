
/*-
 * Classname:             Withdrawal.java
 *
 * Version information:   1.0
 *
 * Date:                  17/10/2012 - 13:31:37
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe modelo de Retirada
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Withdrawal implements Comparable {

    //código da retirada
    private int codeWithdrawal;
    //aministrador
    private Administrator administrator;
    //data do retirada
    private Date dateWithdrawal;
    //data entrega
    private Date dateDelivery;
    //motivo retirada
    private String motiveWithdrawal;
    //solicitante
    private String solicitor;
    //nome de quem retirou
    private String deliveryGuy;
    //observação sobre a retirada
    private String observationWithdrawal;
    //estado da retirada
    //aberto=true fechada=false
    private boolean status;
    //lista com código dos documentos
    private List<Document> listDocument;

    /**
     * Construtor de retirada sem argumentos
     */
    public Withdrawal() {
        this.status = true;
        listDocument = new ArrayList();
    }//fim do construtor Withdrawal sem argumentos

    /**
     * Construtor de retirada com código da retirada
     *
     * @param codeWithdrawal código da retirada
     * @param administrator administrador
     * @param dateWithdrawal data da retirada
     * @param dateDelivery data da entrega
     * @param motiveWithdrawal motivo da retirada
     * @param solicitor solicitante
     * @param deliveryGuy nome de quem retirou
     * @param observationWithdrawal observação sobre a retirada
     * @param status estado
     */
    public Withdrawal(int codeWithdrawal, Administrator administrator, Date dateWithdrawal,
            Date dateDelivery, String motiveWithdrawal, String solicitor,
            String deliveryGuy, String observationWithdrawal, boolean status) {
        this.codeWithdrawal = codeWithdrawal;
        this.administrator = administrator;
        this.dateWithdrawal = dateWithdrawal;
        this.dateDelivery = dateDelivery;
        this.motiveWithdrawal = motiveWithdrawal;
        this.solicitor = solicitor;
        this.deliveryGuy = deliveryGuy;
        this.observationWithdrawal = observationWithdrawal;
        this.status = status;
        listDocument = new ArrayList();
    }//fim do construtor Withdrawal

    /**
     * Construtor de retirada sem código da retirada
     *
     * @param administrator administrador
     * @param dateWithdrawal data da retirada
     * @param dateDelivery data da entrega
     * @param motiveWithdrawal motivo da retirada
     * @param solicitor solicitante
     * @param deliveryGuy nome de quem retirou
     * @param observationWithdrawal observação sobre a retirada
     * @param status estado
     */
    public Withdrawal(Administrator administrator, Date dateWithdrawal,
            Date dateDelivery, String motiveWithdrawal, String solicitor,
            String deliveryGuy, String observationWithdrawal, boolean status) {
        this.administrator = administrator;
        this.dateWithdrawal = dateWithdrawal;
        this.dateDelivery = dateDelivery;
        this.motiveWithdrawal = motiveWithdrawal;
        this.solicitor = solicitor;
        this.deliveryGuy = deliveryGuy;
        this.observationWithdrawal = observationWithdrawal;
        this.status = status;
        listDocument = new ArrayList();
    }// fim do contrutor Withdrawal

    /**
     * Construtor de retirada com lista de documentos e com código da retirada
     *
     * @param codeWithdrawal código da retirada
     * @param Administrator administrador
     * @param dateWithdrawal data da retirada
     * @param dateDelivery data da entrega
     * @param motiveWithdrawal motivo da retirada
     * @param solicitor solicitante
     * @param deliveryGuy nome de quem retirou
     * @param observationWithdrawal observação sobre a retirada
     * @param status estado
     * @param listDocument lista com códigos de documentos
     */
    public Withdrawal(int codeWithdrawal, Administrator administrator, Date dateWithdrawal,
            Date dateDelivery, String motiveWithdrawal, String solicitor, String deliveryGuy,
            String observationWithdrawal, boolean status, List listDocument) {
        this.codeWithdrawal = codeWithdrawal;
        this.administrator = administrator;
        this.dateWithdrawal = dateWithdrawal;
        this.dateDelivery = dateDelivery;
        this.motiveWithdrawal = motiveWithdrawal;
        this.solicitor = solicitor;
        this.deliveryGuy = deliveryGuy;
        this.observationWithdrawal = observationWithdrawal;
        this.status = status;
        this.listDocument = listDocument;
    }//fim do construtor Withdrawal

    /**
     * Construtor de retirada com lista de documentos e sem código da retirada
     *
     * @param administrator administrador
     * @param dateWithdrawal data da retirada
     * @param dateDelivery data da entrega
     * @param motiveWithdrawal motivo da retirada
     * @param solicitor solicitante
     * @param deliveryGuy nome de quem retirou
     * @param observationWithdrawal observação sobre a retirada
     * @param status estado
     * @param listDocument lista com código dedocumento
     */
    public Withdrawal(Administrator administrator, Date dateWithdrawal, Date dateDelivery,
            String motiveWithdrawal, String solicitor, String deliveryGuy,
            String observationWithdrawal, boolean status, List listDocument) {
        this.administrator = administrator;
        this.dateWithdrawal = dateWithdrawal;
        this.dateDelivery = dateDelivery;
        this.motiveWithdrawal = motiveWithdrawal;
        this.solicitor = solicitor;
        this.deliveryGuy = deliveryGuy;
        this.observationWithdrawal = observationWithdrawal;
        this.status = status;
        this.listDocument = listDocument;
    }//fim do método Withdrawal

    //sets
    /**
     * Define cpodigo da retirada
     *
     * @param codeWithdrawal código retirada
     */
    public void setCodeWithDrawal(int codeWithdrawal) {
        this.codeWithdrawal = codeWithdrawal;
    }//fim do método setCodeWithDrawal

    /**
     * Define código do administrador
     *
     * @param administrator administrador
     */
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }//fim do método setAdministrator

    /**
     * Define data da retirada
     *
     * @param dateWithdrawal
     */
    public void setDateWithdrawal(Date dateWithdrawal) {
        this.dateWithdrawal = dateWithdrawal;
    }//fim do método setDateWithdrawal

    /**
     * Define data da entrega
     *
     * @param dateDelivery data entrega
     */
    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }//fim do setDateDelivery

    /**
     * Define motivo da retirada
     *
     * @param motiveWithdrawal motivo da retirada
     */
    public void setMotiveWithdrawal(String motiveWithdrawal) {
        this.motiveWithdrawal = motiveWithdrawal;
    }//fim do método setMotiveWithdrawal

    /**
     * Define solicitante
     *
     * @param solicitor solicitante
     */
    public void setSolicitor(String solicitor) {
        this.solicitor = solicitor;
    }//dim do método setSolicitor

    /**
     * Define nome de quem retirou
     *
     * @param deliveryGuy quem retirou
     */
    public void setDeliveryGuy(String deliveryGuy) {
        this.deliveryGuy = deliveryGuy;
    }//fim do método setDeliveryGuy

    /**
     * Define observação sobre a retirada
     *
     * @param observationWithdrawal observação sobre a retirada
     */
    public void setObservationWithdrawal(String observationWithdrawal) {
        this.observationWithdrawal = observationWithdrawal;
    }//fim do método setObservationWithdrawal

    /**
     * Define estado
     *
     * @param status esyado
     */
    public void setStatus(boolean status) {
        this.status = status;
    }//fim do método setStatus

    /**
     * Define documento
     *
     * @param document documento
     */
    public void setDocument(Document document) {
        listDocument.add(document);
    }//fim do método setDocument

    /**
     * Define lista com documentos
     *
     * @param listDocument lista com código dod documentos
     */
    public void setListDocument(List listDocument) {
        this.listDocument = listDocument;
    }//fim do método setlistDocument

    //gets
    /**
     * Obtêm código da retirada
     *
     * @return <code>integer</code> códogo da retirada
     */
    public int getCodeWithDrawal() {
        return codeWithdrawal;
    }//fim do método getCodeWithDrawal

    /**
     * Obtêm código do administrador
     *
     * @return <code>integer</code> código do administrador
     */
    public int getCodeAdministrator() {
        return administrator.getCodeAdministrator();
    }//fim do método getCodeAdministrator

    /**
     * Obtêm data de retirada
     *
     * @return <code>Date</code> data retirada
     */
    public java.sql.Date getDateWithdrawal() {

        return new java.sql.Date(dateWithdrawal.getTime());
    }//fim do método getDateWithdrawal

    /**
     * Obtêm data da entrega
     *
     * @return <code>Date</code> data entrega
     */
    public java.sql.Date getDateDelivery() {
        if (dateDelivery != null) {
            return new java.sql.Date(dateDelivery.getTime());
        }
        return null;
    }//fim do método getdateDelivery

    /**
     * Obtêm motivo da retirada
     *
     * @return <code>integer</code> motivo retirada
     */
    public String getMotiveWithdrawal() {
        return motiveWithdrawal;
    }//fim do método getMotiveWithdrawal

    /**
     * Obtêm solicitante
     *
     * @return <code>integer</code> solicitante
     */
    public String getSolicitor() {
        return solicitor;
    }//fim do método getSolicitor

    /**
     * Obtêm nome de quem retirou
     *
     * @return <code>integer</code> quem retirou
     */
    public String getDeliveryGuy() {
        return deliveryGuy;
    }//fim do método getDeliveryGuy

    /**
     * Obtêm observação sobre retirada
     *
     * @return <code>integer</code> observação retirada
     */
    public String getObservationWithdrawal() {
        return observationWithdrawal;
    }//fim do método getObservationWithdrawal

    /**
     * Obtêm estado da retirada
     *
     * @return <code>integer</code> estado
     */
    public boolean getStatus() {
        return status;
    }//fim do método getStatus

    /**
     * Obtêm lista de documentos retirados
     *
     * @return <code>ArrayList</code> com os documetos
     */
    public List getListDocument() {
        return listDocument;
    }//fim do método getlistDocument

    /**
     * Obtêm o nome do administrador que efetuou a retirada
     *
     * @return <code>String</code> nome do administrador
     */
    public String getNameadministrator() {
        return administrator.getName();
    }//fim do método getNameadministrator

    /**
     * Método para comparação de retirada
     *
     * @param withdrawal retirada
     * @return <code>Integer</code> parâmetro de comparação seguindo a convenção
     * da interface Comparable
     */
    public int compareTo(Object withdrawal) {
        Withdrawal otherWithdrawal = (Withdrawal) withdrawal;
        if (this.getCodeWithDrawal() < otherWithdrawal.getCodeWithDrawal()) {
            return -1;
        } else if (this.getCodeWithDrawal() == otherWithdrawal.getCodeWithDrawal()) {
            return 0;
        } else {
            return 1;
        }
    }//fim do método compareTo
}//fim da classe Withdrawal

