/*- 
 * Classname:             DAOFactory.java 
 * 
 * Version information:   (versão) 
 * 
 * Date:                  04/08/2014 - 20:10:43 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.DAO;

import uni.uri.salmox.util.Category;

/**
 * Fabrica para criação de DAOs
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DAOFactory {

    //categoria 
    private Category category;

    /**
     * Construtor com categoria
     *
     * @param category categoria
     */
    public DAOFactory(Category category) {
        this.category = category;
    }//fim do método construtor

    /**
     * Retorna o DAO
     *
     * @return <code>CategoryBoxDAOInterface</code> interface do DAO
     */
    public CategoryBoxDAOInterface getDAO() {
        CategoryBoxDAOInterface boxDAO;
        //cria a instancia de DAO dependento da categoria
        if (category == Category.A) {
            boxDAO = new ADAO();
        } else if (category == Category.AE) {
            boxDAO = new AEDAO();
        } else if (category == Category.CC) {
            boxDAO = new CCDAO();
        } else if (category == Category.DP) {
            boxDAO = new DPDAO();
        } else if (category == Category.EX) {
            boxDAO = new EXDAO();
        } else if (category == Category.F) {
            boxDAO = new FDAO();
        } else if (category == Category.FC) {
            boxDAO = new FCDAO();
        } else if (category == Category.FP) {
            boxDAO = new FPDAO();
        } else if (category == Category.MC) {
            boxDAO = new MCDAO();
        } else if (category == Category.SAE) {
            boxDAO = new SAEDAO();
        } else if (category == Category.DAD) {
            boxDAO = new DADDAO();
        } else if (category == Category.DAC) {
            boxDAO = new DACDAO();
        } else if (category == Category.DG) {
            boxDAO = new DGDAO();
        } else {
            boxDAO = new DSGDAO();
        }
        return boxDAO;
    }//fim do mátodo getDAO
}//fim da classe DAOFactory 

