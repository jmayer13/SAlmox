
/*-
 * Classname:             PreferencesManager.java
 *
 * Version information:   1.0
 *
 * Date:                  27/11/2012 - 14:25:20
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.util;

import java.util.prefs.Preferences;
import uni.uri.salmox.model.User;

/**
 * Preferências do aplicativo
 *
 * @see java.util.prefs.Preferences
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class PreferencesManager {

    //preferências do sistema
    private static Preferences systemPreferences = Preferences.systemRoot();
    //largura da tela (x)
    public static final String KEY_SCREEN_WIDTH = "ScreenWidth";
    //altura da tela (y)
    public static final String KEY_SCREEN_HEIGTH = "ScreenHeight";
    //local do banco de dados
    public static final String KEY_LOCAL_DATABASE = "LocalDatabase";
    //usuário conexão com banco
    public static final String KEY_DATABASE_USER = "DatabaseUser";
    //senha do banco
    public static final String KEY_DATABASE_PASSWORD = "DatabasePassword";
    //preferências dos usuários
    public static Preferences userPreferences = Preferences.userRoot();
    //cores
    //caixas
    public static final String KEY_COLOR_BOX_ALL = "ColorBoxALL";
    public static final String KEY_COLOR_BOX_A = "ColorBoxA";
    public static final String KEY_COLOR_BOX_AE = "ColorBoXAE";
    public static final String KEY_COLOR_BOX_CC = "ColorBoXCC";
    public static final String KEY_COLOR_BOX_DAC = "ColorBoXDAC";
    public static final String KEY_COLOR_BOX_DAD = "ColorBoXDAD";
    public static final String KEY_COLOR_BOX_DG = "ColorBoXDG";
    public static final String KEY_COLOR_BOX_DSG = "ColorBoXDSG";
    public static final String KEY_COLOR_BOX_DP = "ColorBoXDP";
    public static final String KEY_COLOR_BOX_EX = "ColorBoXEX";
    public static final String KEY_COLOR_BOX_F = "ColorBoXF";
    public static final String KEY_COLOR_BOX_FC = "ColorBoXFC";
    public static final String KEY_COLOR_BOX_FP = "ColorBoXFP";
    public static final String KEY_COLOR_BOX_MC = "ColorBoXMC";
    public static final String KEY_COLOR_BOX_SAE = "ColorBoxSAE";
    //livros
    public static final String KEY_COLOR_BOOK_A = "ColorBookA";
    public static final String KEY_COLOR_BOOK_AE = "ColorBookAE";
    public static final String KEY_COLOR_BOOK_CC = "ColorBookCC";
    public static final String KEY_COLOR_BOOK_DAC = "ColorBookDAC";
    public static final String KEY_COLOR_BOOK_DAD = "ColorBookDAD";
    public static final String KEY_COLOR_BOOK_DG = "ColorBookDG";
    public static final String KEY_COLOR_BOOK_DSG = "ColorBookDSG";
    public static final String KEY_COLOR_BOOK_DP = "ColorBookDP";
    public static final String KEY_COLOR_BOOK_EX = "ColorBookEX";
    public static final String KEY_COLOR_BOOK_F = "ColorBookF";
    public static final String KEY_COLOR_BOOK_FC = "ColorBookFC";
    public static final String KEY_COLOR_BOOK_FP = "ColorBookFP";
    public static final String KEY_COLOR_BOOK_MC = "ColorBookMC";
    public static final String KEY_COLOR_BOOK_SAE = "ColorBookSAE";
    public static final String KEY_COLOR_BOOK_ALL = "ColorBookALL";
    //documentos
    public static final String KEY_COLOR_DOCUMENT_A = "ColorDocumentA";
    public static final String KEY_COLOR_DOCUMENT_AE = "ColorDocument";
    public static final String KEY_COLOR_DOCUMENT_CC = "ColorDocumentCC";
    public static final String KEY_COLOR_DOCUMENT_DAC = "ColorDocumentDAC";
    public static final String KEY_COLOR_DOCUMENT_DAD = "ColorDocumentDAD";
    public static final String KEY_COLOR_DOCUMENT_DG = "ColorDocumentDG";
    public static final String KEY_COLOR_DOCUMENT_DSG = "ColorDocumentDSG";
    public static final String KEY_COLOR_DOCUMENT_DP = "ColorDocumentDP";
    public static final String KEY_COLOR_DOCUMENT_EX = "ColorDocumentEX";
    public static final String KEY_COLOR_DOCUMENT_F = "ColorDocumentF";
    public static final String KEY_COLOR_DOCUMENT_FC = "ColorDocumentFC";
    public static final String KEY_COLOR_DOCUMENT_FP = "ColorDocumentFP";
    public static final String KEY_COLOR_DOCUMENT_MC = "ColorDocumentMC";
    public static final String KEY_COLOR_DOCUMENT_SAE = "ColorDocumentSAE";
    public static final String KEY_COLOR_DOCUMENT_ALL = "ColorDocumentALL";
    //lista de caixas para deletar
    public static final String KEY_COLOR_DELETE_LIST = "ColorDeleteList";
    //devolução de documentos
    public static final String KEY_COLOR_DEVOLUTION = "ColorDevolution";
    //histórico
    public static final String KEY_COLOR_HISTORY = "ColorHistory";
    //solicitação
    public static final String KEY_COLOR_REQUEST = "ColorRequest";
    //busca
    public static final String KEY_COLOR_SEARCH = "ColorSearch";
    //usuário
    public static final String KEY_COLOR_USER = "ColorUser";
    //retirada
    public static final String KEY_COLOR_WITHDRAWAL = "ColorWithdrawal";
    //wallpaper ativado/desativado
    public static String KEY_WALLPAPER = "WallpaperActive";

    /**
     * Retorna as preferências do sistema
     *
     * @return <code>Preferences</code> preferências
     */
    public static Preferences getPreferencesSystem() {
        return systemPreferences;
    }//fim do método getPreferences

    /**
     * Retorna as preferências dos usuários
     *
     * @param user
     * @return <code>Preferences</code> preferências
     */
    public static Preferences getPrefetencesUser(User user) {
        return userPreferences.node(user.getUserLogin());
    }//fim do métoodo getPrefetencesUser

    /**
     * Define preferência do sistema - tamanho da tela
     *
     * @param x largura da tela
     * @param y altura da tela
     */
    public static void putScreenSize(int x, int y) {
        systemPreferences.putInt(KEY_SCREEN_WIDTH, x);
        systemPreferences.putInt(KEY_SCREEN_HEIGTH, y);
    }//fim do método putScreenSize

    /**
     * Define preferência do sistema - local da base de dados
     *
     * @param localDatabase local da base de dados
     */
    public static void putLocalDatabase(String localDatabase) {
        systemPreferences.put(KEY_LOCAL_DATABASE, localDatabase);
    }//fim do método putLocalDatabase

    /**
     * Define preferência do sistema - usuário da base de dados
     *
     * @param databaseUser usuário da base de dados
     */
    public static void putDatabaseUser(String databaseUser) {
        systemPreferences.put(KEY_DATABASE_USER, databaseUser);
    }//fim do método putDatabaseUser

    /**
     * Define preferência do sistema - senha da base de dados
     *
     * @param databasePassword senha da base de dados
     */
    public static void putDatabasePassword(String databasePassword) {
        systemPreferences.put(KEY_DATABASE_PASSWORD, databasePassword);
    }//fim do método putDatabasePassword

    //prefencias dos usuários
    /**
     * Define cor de interface para a caixa
     *
     * @param color cor serializada
     */
    public static void putColorBox(String keyColor, byte[] color) {
        userPreferences.putByteArray(keyColor, color);
    }//fim do método putColorBoxA

    /**
     * Define cor de interface para a livro
     *
     * @param color cor serializada
     */
    public static void putColorBook(String keyColor, byte[] color) {
        userPreferences.putByteArray(keyColor, color);
    }//fim do método putColorBook

    /**
     * Define cor de interface para a documento
     *
     * @param color cor serializada
     */
    public static void putColorDocument(String keyColor, byte[] color) {
        userPreferences.putByteArray(keyColor, color);
    }//fim do método putColorDocumentA

    /**
     * Define cor de interface para a deletar lista
     *
     * @param color cor serializada
     */
    public static void putColorDeleteList(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_DELETE_LIST, color);
    }//fim do método putColorDeleteList

    /**
     * Define cor de interface para a entrega de documentos
     *
     * @param color cor serializada
     */
    public static void putColorDevolution(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_DEVOLUTION, color);
    }//fim do método putColorDevolution

    /**
     * Define cor de interface para a o histórico
     *
     * @param color cor serializada
     */
    public static void putColorHistory(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_HISTORY, color);
    }//fim do método putColorHistory

    /**
     * Define cor de interface para a solicitação
     *
     * @param color cor serializada
     */
    public static void putColorRequest(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_REQUEST, color);
    }//fim do método putColorRequest

    /**
     * Define cor de interface para a busca
     *
     * @param color cor serializada
     */
    public static void putColorSearch(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_SEARCH, color);
    }//fim do método putColorSearch

    /**
     * Define cor de interface para o usuário
     *
     * @param color cor serializada
     */
    public static void putColorUser(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_USER, color);
    }//fim do método putColorUser

    /**
     * Define cor de interface para a retirada
     *
     * @param color cor serializada
     */
    public static void putColorWithdrawal(byte[] color) {
        userPreferences.putByteArray(KEY_COLOR_WITHDRAWAL, color);
    }//fim do método putColorWithdrawal

    /**
     * Define se o plano de fundo vai aparecer ou não
     *
     * @param active true ativa; false desativa
     */
    public static void putWallpaperActive(boolean active) {
        systemPreferences.putBoolean(KEY_WALLPAPER, active);
    }//fim do método putWallpaperActive

    /**
     * Define a cor de interface com a chave especificada
     *
     * @param key chave
     * @param color cor
     */
    public static void putGenericColor(String key, byte[] color) {
        userPreferences.putByteArray(key, color);
    }//fim do método putGenericColor
}//fim da classe PreferencesManager

