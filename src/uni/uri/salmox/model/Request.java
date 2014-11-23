
/*-
 * Classname:             Request.java
 *
 * Version information:   1.0
 *
 * Date:                  16/10/2012 - 13:28:28
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*  +Lista
 *      -Recebe lista inteira
 *      -Recebe valor único
 *  +Construtor Lista
 *      -Lista inteira
 *      -Sem Lista
 */
/**
 * Modelo da classe solicitação
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class Request implements Comparable {

    //código solicitação
    private int codeRequest;
    //usuário padrão
    private DefaultUser defaultUser;
    //estado da requisiçãp
    private boolean status;
    //data
    private Date dateRequest;
    //observação solicitação
    private String observationRequest;
    //motivo da solicitação
    private String motiveRequest;
    //lista dos documentos requisitados
    private List<Document> listDocumentRequest;

    /**
     * Construtor sem argumentos de Request
     */
    public Request() {
        this.status = true;
        listDocumentRequest = new ArrayList();
    }//fim do construtor sem argumentos

    /**
     * Construtor dr Solicitação com código solicitação e com Lista de códigos
     * de documentos requeridos
     *
     * @param codeRequest código da soçicitação
     * @param DefaultUser usuário padrão
     * @param status estado
     * @param dateRequest data da solicitação
     * @param observationRequest observação sobre a requisição
     * @param motiveRequest motivo solicitação
     * @param listDocumentRequest lista dos documentos requisitados
     */
    public Request(int codeRequest, DefaultUser defaultUser, boolean status,
            Date dateRequest, String observationRequest, String motiveRequest,
            List listDocumentRequest) {
        this.codeRequest = codeRequest;
        this.defaultUser = defaultUser;
        this.status = status;
        this.dateRequest = dateRequest;
        this.observationRequest = observationRequest;
        this.motiveRequest = motiveRequest;
        this.listDocumentRequest = listDocumentRequest;
    }//fim do construtor com código de solicitação e com lista

    /**
     * Construtor dr Solicitação com código solicitação e sem Lista de códigos
     * de documentos requeridos
     *
     * @param codeRequest código da solicitação
     * @param defaultUser usuário padrão
     * @param status estado
     * @param dateRequest data da solicitação
     * @param observationRequest observação sobre a solicitação
     * @param motiveRequest motivo da solicitação
     */
    public Request(int codeRequest, DefaultUser defaultUser, boolean status,
            Date dateRequest, String observationRequest, String motiveRequest) {
        this.codeRequest = codeRequest;
        this.defaultUser = defaultUser;
        this.status = status;
        this.dateRequest = dateRequest;
        this.observationRequest = observationRequest;
        this.motiveRequest = motiveRequest;
        listDocumentRequest = new ArrayList();
    }//fim do construtor com código de solicitação e sem lista

    /**
     * Construtor sem código Solicitação e com Lista de códigos de Documentos
     * Requisitados
     *
     * @param defaultUser usuário padrão
     * @param status estado
     * @param dateRequest data da solicitação
     * @param observationRequest observação sobre a requisição
     * @param motiveRequest motivo solicitação
     * @param listDocumentRequest lista dos documentos requisitados
     */
    public Request(DefaultUser defaultUser, boolean status, Date dateRequest,
            String observationRequest, String motiveRequest, List listDocumentRequest) {
        this.defaultUser = defaultUser;
        this.status = status;
        this.dateRequest = dateRequest;
        this.observationRequest = observationRequest;
        this.motiveRequest = motiveRequest;
        this.listDocumentRequest = listDocumentRequest;
    }//fim do construtor sem código de solicitação e com lista

    /**
     * Construtor sem código Solicitação e sem Lista de códigos de Documentos
     * Requisitados
     *
     * @param defaultUser usuário padrão
     * @param status estado
     * @param dateRequest data da solicitação
     * @param observationRequest observação sobre a requisição
     * @param motiveRequest motivo solicitação
     */
    public Request(DefaultUser defaultUser, boolean status, Date dateRequest,
            String observationRequest, String motiveRequest) {
        this.defaultUser = defaultUser;
        this.status = status;
        this.dateRequest = dateRequest;
        this.observationRequest = observationRequest;
        this.motiveRequest = motiveRequest;
        listDocumentRequest = new ArrayList();
    }//fim do construtor sem código de solicitação e sem lista

    //sets
    /**
     * Define código solicitação
     *
     * @param codeRequest código solicitação
     */
    public void setCodeRequest(int codeRequest) {
        this.codeRequest = codeRequest;
    }//fim do método setCodeRequest

    /**
     * Define código usuário padrão
     *
     * @param defaultUser padrão
     */
    public void setDefaultUser(DefaultUser defaultUser) {
        this.defaultUser = defaultUser;
    }//fim do método setCodeDefaultUser

    /**
     * Define estado solicitação
     *
     * @param status estado solicitação
     */
    public void setStatus(boolean status) {
        this.status = status;
    }//fim do método setStatus

    /**
     * Define data da requisição
     *
     * @param dateRequest data requisição
     */
    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }//fim do método setDateRequest

    /**
     * Define observação sobre solicitação
     *
     * @param observationRequest observação sobre solicitação
     */
    public void setObservationRequest(String observationRequest) {
        this.observationRequest = observationRequest;
    }//fim do método setObservationRequest

    /**
     * Define motivo solicitação
     *
     * @param motiveRequest motivo solicitação
     */
    public void setMotiveRequest(String motiveRequest) {
        this.motiveRequest = motiveRequest;
    }//fom do método setMotiveRequest

    /**
     * Define lista de codigos de documentos solicitados
     *
     * @param listDocumentRequest lista de codigos de documentos solicitados
     */
    public void setListDocumentRequest(List listDocumentRequest) {
        this.listDocumentRequest = listDocumentRequest;
    }//fim do método setListDocumentRequest

    /**
     * Define documento solicitado
     *
     * @param document documento solicitado
     */
    public void setDocument(Document document) {
        this.listDocumentRequest.add(document);
    }//fim do método setDocument

    //gets
    /**
     * Obtêm código da solicitação
     *
     * @return <code>integer</code> código da solicitação
     */
    public int getCodeRequest() {
        return codeRequest;
    }//fim do método getCodeRequest

    /**
     * Obtêm código usuário padrão
     *
     * @return <code>integer</code> código usuário padrão
     */
    public int getCodeDefaultUser() {
        return defaultUser.getCodeDefaultUserr();
    }//fim do método getCodeDefaultUser

    /**
     * Obtêm estado da solicitação
     *
     * @return <code>boolean</code> estado da solicitação
     */
    public boolean getStatus() {
        return status;
    }//fim do método getStatus

    /**
     * Obtêm data requisição
     *
     * @return <code>Date</code> data requisição
     */
    public Date getDateRequest() {
        return dateRequest;
    }//fim do método getDateRequest

    /**
     * Obtêm observação requisição
     *
     * @return <code>String</code> observação requisição
     */
    public String getObservationRequest() {
        return observationRequest;
    }//fim do método getObservationRequest

    /**
     * Obtêm motivo requisição
     *
     * @return <code>String</code> motivo requisição
     */
    public String getMotiveRequest() {
        return motiveRequest;
    }//fim do método getMotiveRequest

    /**
     * Obtêm código documento requisitado
     *
     * @return <code>List</code> documento requisitado
     */
    public List getListDocumentRequest() {
        return listDocumentRequest;
    }//fim do método getListDocumentRequest

    /**
     * Obtêm nome do usuário padrão
     *
     * @return <code>String</code> com o nome do usuário padrão
     */
    public String getNameDefaultUser() {
        return defaultUser.getName();
    }//fim do método getNameDefaultUser

    /**
     * Método para comparação de solicitacão
     *
     * @param request solicitação
     * @return <code>Integer</code> parâmetro de comparação seguindo a convenção
     * da interface Comparable
     */
    public int compareTo(Object request) {
        Request otherRequest = (Request) request;
        if (this.getCodeRequest() < otherRequest.getCodeRequest()) {
            return -1;
        } else if (this.getCodeRequest() == otherRequest.getCodeRequest()) {
            return 0;
        } else {
            return 1;
        }
    }//fim do método compareTo
}//fim da classe Request

