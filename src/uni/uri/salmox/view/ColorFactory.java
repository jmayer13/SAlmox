/*- 
 * Classname:             ColorFactory.java 
 * 
 * Version information:   (versão) 
 * 
 * Date:                  28/08/2014 - 20:38:22 
 * 
 * author:                Jonas Mayer (jmayer13@hotmail.com) 
 * Copyright notice:      (informações do método, pra que serve, idéia principal) 
 */
package uni.uri.salmox.view;

import java.awt.Color;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ColorConverter;
import uni.uri.salmox.util.PreferencesManager;

/**
 * Descrição
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ColorFactory {

    private Category category;

    public ColorFactory(Category category) {
        this.category = category;
    }

    public Color getBoxColor() {
        String preferenceIndex;
        if (category != null) {
            if (category == Category.A) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_A;
            } else if (category == Category.AE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_AE;
            } else if (category == Category.CC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_CC;
            } else if (category == Category.DAC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_DAC;
            } else if (category == Category.DAD) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_DAD;
            } else if (category == Category.DG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_DG;
            } else if (category == Category.DP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_DP;
            } else if (category == Category.DSG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_DSG;
            } else if (category == Category.EX) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_EX;
            } else if (category == Category.F) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_F;
            } else if (category == Category.FC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_FC;
            } else if (category == Category.FP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_FP;
            } else if (category == Category.MC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_MC;
            } else if (category == Category.SAE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_SAE;
            } else {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOX_ALL;
            }
        } else {
            preferenceIndex = PreferencesManager.KEY_COLOR_BOX_ALL;
        }
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(preferenceIndex);
    }

    public Color getBookColor() {
        String preferenceIndex;
        if (category != null) {
            if (category == Category.A) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_A;
            } else if (category == Category.AE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_AE;
            } else if (category == Category.CC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_CC;
            } else if (category == Category.DAC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_DAC;
            } else if (category == Category.DAD) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_DAD;
            } else if (category == Category.DG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_DG;
            } else if (category == Category.DP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_DP;
            } else if (category == Category.DSG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_DSG;
            } else if (category == Category.EX) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_EX;
            } else if (category == Category.F) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_F;
            } else if (category == Category.FC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_FC;
            } else if (category == Category.FP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_FP;
            } else if (category == Category.MC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_MC;
            } else if (category == Category.SAE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_SAE;
            } else {
                preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_ALL;
            }
        } else {
            preferenceIndex = PreferencesManager.KEY_COLOR_BOOK_ALL;
        }
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(preferenceIndex);

    }

    public Color getDocumentColor() {
        String preferenceIndex;
        if (category != null) {
            if (category == Category.A) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_A;
            } else if (category == Category.AE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_AE;
            } else if (category == Category.CC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_CC;
            } else if (category == Category.DAC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_DAC;
            } else if (category == Category.DAD) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_DAD;
            } else if (category == Category.DG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_DG;
            } else if (category == Category.DP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_DP;
            } else if (category == Category.DSG) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_DSG;
            } else if (category == Category.EX) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_EX;
            } else if (category == Category.F) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_F;
            } else if (category == Category.FC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_FC;
            } else if (category == Category.FP) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_FP;
            } else if (category == Category.MC) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_MC;
            } else if (category == Category.SAE) {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_SAE;
            } else {
                preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_ALL;
            }
        } else {
            preferenceIndex = PreferencesManager.KEY_COLOR_DOCUMENT_ALL;
        }
        ColorConverter colorConverter = new ColorConverter();
        return colorConverter.getColor(preferenceIndex);

    }
}//fim da classe ColorFactory 

