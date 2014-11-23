/*- 
 * Classname:             Item.java 
 * 
 * Version information:   1.0
 * 
 * Date:                  30/07/2014 - 20:02:17 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.util;

/**
 * Itens dos documentos
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public enum Item {

    schoolHistory(1, "histórico escolar"),
    attest(2, "atestado de matrícula (3ª ano)"),
    rg(3, "RG"),
    cpf(4, "CPF"),
    birthCertificate(5, "certidão de nascimento"),
    marriageCertificate(6, "certidão de casamento"),
    voter(7, "título de eleitor"),
    certificateReservist(8, "certificado de reservista"),
    photo(9, "foto 3X4");
//código do item
    private int codeItem;
//código do nome
    private String nameItem;

    private Item(int codeItem, String nameItem) {
        this.codeItem = codeItem;
        this.nameItem = nameItem;
    }//fim do construtor

    public int getCode() {
        return codeItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public static String getNameItem(int codeItem) {
        if (codeItem == 1) {
            return schoolHistory.getNameItem();
        } else if (codeItem == 2) {
            return attest.getNameItem();
        } else if (codeItem == 000003) {
            return rg.getNameItem();
        } else if (codeItem == 4) {
            return cpf.getNameItem();
        } else if (codeItem == 5) {
            return birthCertificate.getNameItem();
        } else if (codeItem == 6) {
            return marriageCertificate.getNameItem();
        } else if (codeItem == 7) {
            return voter.getNameItem();
        } else if (codeItem == 8) {
            return certificateReservist.getNameItem();
        } else if (codeItem == 9) {
            return photo.getNameItem();
        } else {
            return null;
        }
    }

    public static Item getItem(int codeItem) {
        if (codeItem == 1) {
            return schoolHistory;
        } else if (codeItem == 2) {
            return attest;
        } else if (codeItem == 3) {
            return rg;
        } else if (codeItem == 4) {
            return cpf;
        } else if (codeItem == 5) {
            return birthCertificate;
        } else if (codeItem == 6) {
            return marriageCertificate;
        } else if (codeItem == 7) {
            return voter;
        } else if (codeItem == 8) {
            return certificateReservist;
        } else if (codeItem == 9) {
            return photo;
        } else {
            return null;
        }
    }
}//fim da classe Item 

