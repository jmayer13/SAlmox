/*-
 * Classname:             Category.java
 *
 * Version information:   1.0
 *
 * Date:                  22/05/2013 - 15:50:43
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.BookA;
import uni.uri.salmox.model.BookAE;
import uni.uri.salmox.model.BookCC;
import uni.uri.salmox.model.BookDAC;
import uni.uri.salmox.model.BookDAD;
import uni.uri.salmox.model.BookDG;
import uni.uri.salmox.model.BookDP;
import uni.uri.salmox.model.BookDSG;
import uni.uri.salmox.model.BookEX;
import uni.uri.salmox.model.BookF;
import uni.uri.salmox.model.BookFC;
import uni.uri.salmox.model.BookFP;
import uni.uri.salmox.model.BookMC;
import uni.uri.salmox.model.BookSAE;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.BoxA;
import uni.uri.salmox.model.BoxAE;
import uni.uri.salmox.model.BoxCC;
import uni.uri.salmox.model.BoxDAC;
import uni.uri.salmox.model.BoxDAD;
import uni.uri.salmox.model.BoxDG;
import uni.uri.salmox.model.BoxDP;
import uni.uri.salmox.model.BoxDSG;
import uni.uri.salmox.model.BoxEX;
import uni.uri.salmox.model.BoxF;
import uni.uri.salmox.model.BoxFC;
import uni.uri.salmox.model.BoxFP;
import uni.uri.salmox.model.BoxMC;
import uni.uri.salmox.model.BoxSAE;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.DocumentA;
import uni.uri.salmox.model.DocumentAE;
import uni.uri.salmox.model.DocumentCC;
import uni.uri.salmox.model.DocumentDAC;
import uni.uri.salmox.model.DocumentDAD;
import uni.uri.salmox.model.DocumentDG;
import uni.uri.salmox.model.DocumentDP;
import uni.uri.salmox.model.DocumentDSG;
import uni.uri.salmox.model.DocumentEX;
import uni.uri.salmox.model.DocumentF;
import uni.uri.salmox.model.DocumentFC;
import uni.uri.salmox.model.DocumentFP;
import uni.uri.salmox.model.DocumentMC;
import uni.uri.salmox.model.DocumentSAE;

/**
 * Enumeração das categorias de caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public enum Category {

    /**
     * Almoxarifado
     */
    A("A", "Almoxarifado"),
    /**
     * Atas e Exames
     */
    AE("AE", "Atas e Exames"),
    /**
     * Caderno de Chamada
     */
    CC("CC", "Caderno de Chamada"),
    /**
     * Diretor(a) Acadêmico(a)
     */
    DAC("DAC", "Diretor(a) Acadêmico(a)"),
    /**
     * Diretor(a) Administrativo(a)
     */
    DAD("DAD", "Diretor(a) Administrativo(a)"),
    /**
     * Diretor(a) Geral
     */
    DG("DG", "Diretor(a) Geral"),
    /**
     * Secretaria do Gabinete
     */
    DSG("DSG", "Secretaria do Gabinete"),
    /**
     * Departamento Pessoal
     */
    DP("DP", "Departamento Pessoal"),
    /**
     * Ex-Alunos e Desistentes
     */
    EX("EX", "Ex-Alunos e Desistentes"),
    /**
     * Formandos
     */
    F("F ", "Formandos"),
    /**
     * Financeiro e Cobrança
     */
    FC("FC", "Financeiro e Cobrança"),
    /**
     * Formandos Pós-Graduação
     */
    FP("FP", "Formandos Pós-Graduação"),
    /**
     * Movimentos de Caixa
     */
    MC("MC", "Movimentos de Caixa"),
    /**
     * Filantropia
     */
    SAE("SAE", "Filantropia");
    //abreviatura da categoria
    private final String abbreviation;
    //nome da categoria
    private final String name;

    /**
     * Construtor de categoria pela abreviação
     *
     * @param abbreviation abreviação da categoria
     */
    private Category(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }//fim do construtor com parâmetros

    /**
     * Obtêm abreviação da categoria
     *
     * @return <code>String</code> abreviação da categoria
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }//fim do método abbreviation

    /**
     * Obtêm nome da categoria
     */
    public String getName() {
        return name;
    }//fim do método getName

    /**
     * Categoriza caixa
     *
     * @param box caixa
     * @return <code>Category</code> categoria da caixa
     */
    public static Category getCategory(Box box) {
        Category category = null;
        if (box != null) {

            if (box instanceof BoxA) {
                category = Category.A;
            } else if (box instanceof BoxAE) {
                category = Category.AE;
            } else if (box instanceof BoxCC) {
                category = Category.CC;
            } else if (box instanceof BoxDP) {
                category = Category.DP;
            } else if (box instanceof BoxEX) {
                category = Category.EX;
            } else if (box instanceof BoxF) {
                category = Category.F;
            } else if (box instanceof BoxFC) {
                category = Category.FC;
            } else if (box instanceof BoxFP) {
                category = Category.FP;
            } else if (box instanceof BoxMC) {
                category = Category.MC;
            } else if (box instanceof BoxSAE) {
                category = Category.SAE;
            } else if (box instanceof BoxDAC) {
                category = Category.DAC;
            } else if (box instanceof BoxDSG) {
                category = Category.DSG;
            } else if (box instanceof BoxDAD) {
                category = Category.DAD;
            } else if (box instanceof BoxDG) {
                category = Category.DG;
            }
        }
        return category;
    }//fim do método getCategory

    /**
     * Categoriza livro
     *
     * @param book livro
     * @return <code>Category</code> categoria de livro
     */
    public static Category getCategory(Book book) {
        Category category = null;
        if (book != null) {
            if (book instanceof BookA) {
                category = Category.A;
            } else if (book instanceof BookAE) {
                category = Category.AE;
            } else if (book instanceof BookCC) {
                category = Category.CC;
            } else if (book instanceof BookDP) {
                category = Category.DP;
            } else if (book instanceof BookEX) {
                category = Category.EX;
            } else if (book instanceof BookF) {
                category = Category.F;
            } else if (book instanceof BookFP) {
                category = Category.FP;
            } else if (book instanceof BookMC) {
                category = Category.MC;
            } else if (book instanceof BookSAE) {
                category = Category.SAE;
            } else if (book instanceof BookDAC) {
                category = Category.DAC;
            } else if (book instanceof BookDAD) {
                category = Category.DAD;
            } else if (book instanceof BookDG) {
                category = Category.DG;
            } else if (book instanceof BookDSG) {
                category = Category.DSG;
            } else if (book instanceof BookFC) {
                category = Category.FC;
            }
        }
        return category;
    }//fim do método  Category

    /**
     * Categoriza documento
     *
     * @param document documento
     * @return <code>Category</code> categoria do documento
     */
    public static Category getCategory(Document document) {
        Category category = null;
        if (document != null) {
            if (document instanceof DocumentA) {
                category = Category.A;
            } else if (document instanceof DocumentAE) {
                category = Category.AE;
            } else if (document instanceof DocumentCC) {
                category = Category.CC;
            } else if (document instanceof DocumentDP) {
                category = Category.DP;
            } else if (document instanceof DocumentEX) {
                category = Category.EX;
            } else if (document instanceof DocumentF) {
                category = Category.F;
            } else if (document instanceof DocumentFC) {
                category = Category.FC;
            } else if (document instanceof DocumentFP) {
                category = Category.FP;
            } else if (document instanceof DocumentMC) {
                category = Category.MC;
            } else if (document instanceof DocumentSAE) {
                category = Category.SAE;
            } else if (document instanceof DocumentDAC) {
                category = Category.DAC;
            } else if (document instanceof DocumentDAD) {
                category = Category.DAD;
            } else if (document instanceof DocumentDG) {
                category = Category.DG;
            } else if (document instanceof DocumentDSG) {
                category = Category.DSG;
            }
        }
        return category;
    }//fim do método getCategory
}//fim da classe Category

