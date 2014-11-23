
/*-
 * Classname:             WallpaperPanel.java
 *
 * Version information:   1.0
 *
 * Date:                  25/01/2013 - 14:23:56
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uni.uri.salmox.util.PreferencesManager;
import uni.uri.salmox.util.ScreenResolution;
import uni.uri.salmox.util.TemporaryData;

/**
 * Classe responsável por gerar panel com plano de fundo
 *
 * @see javax.swing.JPanel
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class WallpaperPanel extends JPanel {

    //constantes representando as funções
    private final int BACKGROUND_DEFAULT = 1;
    private final int BACKGROUND_BLUE = 2;
    int option = 1;

    /**
     * Construtor sem parâmetro
     */
    public WallpaperPanel() {
    }//fim do construtor sem parâmetros

    /**
     * Construtor com opção de tela
     *
     * @param option opção de tela
     */
    public WallpaperPanel(int option) {
        this.option = option;
    }//fim do construtor

    /**
     * Método sobresctrito paintComponent para aplicar fundo
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        ImageIcon tmpIcon = null;

        //pega tamanho da tela
        Preferences preferences = PreferencesManager.getPrefetencesUser(TemporaryData.getUser());
        int width = preferences.getInt(PreferencesManager.KEY_SCREEN_WIDTH, 0);
        int height = preferences.getInt(PreferencesManager.KEY_SCREEN_HEIGTH, 0);
        boolean wallpaperActive = preferences.getBoolean(PreferencesManager.KEY_WALLPAPER, true);
        if (width == 0 || height == 0) {
            ScreenResolution resolution = new ScreenResolution();
            width = resolution.getX();
            height = resolution.getY();
        }

        if (wallpaperActive) {
            option = 1;
        } else {
            option = 2;
        }

        //carrega imagem padrão
        if (option == BACKGROUND_DEFAULT) {
            String separator = System.getProperty("file.separator");
            tmpIcon = new ImageIcon("image" + separator + "background.png");

            //pega o tamanho da imagem
            double iconWidth = tmpIcon.getIconWidth();
            double iconHeight = tmpIcon.getIconHeight();
            //pega o tamnho do panel
            double widthPanel = this.getWidth();
            double heightPanel = this.getHeight();
            //taxa de redimensionamento
            double rateResize = 0;

            if (widthPanel < iconWidth || heightPanel < iconHeight) //calcula a diferença entre a imagem e o panel
            {
                if ((iconWidth - widthPanel) / widthPanel > (iconHeight - heightPanel) / heightPanel) {
                    //calcula a taxa de redimensionamento
                    rateResize = widthPanel * 100 / iconWidth;
                } else if ((iconWidth - widthPanel) / widthPanel == (iconHeight - heightPanel) / heightPanel) {
                    if (widthPanel < heightPanel) {
                        //calcula a taxa de redimensionamento
                        rateResize = widthPanel * 100 / iconWidth;
                    } else {
                        //calcula a taxa de redimensionamento
                        rateResize = heightPanel * 100 / iconHeight;
                    }
                } else {
                    //calcula a taxa de redimensionamento
                    rateResize = heightPanel * 100 / iconHeight;
                }

                //calcula a nova dimensão da imagem
                iconHeight = iconHeight * rateResize / 100;
                iconWidth = iconWidth * rateResize / 100;
            }
            //redefine a imagem com o algoritmo e o tamanho especificados
            Image image = new ImageIcon(tmpIcon.getImage().getScaledInstance((int) iconWidth, (int) iconHeight, Image.SCALE_SMOOTH)).getImage();

            //calcula novas coordenadas
            int x = (int) (widthPanel - iconWidth) / 2;
            int y = (int) (heightPanel - iconHeight) / 2;

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            //desenha imagem
            g.drawImage(image, x, y, (int) iconWidth, (int) iconHeight, Color.WHITE, this);

            /*     //tamanho da imagem
             int imageWidth = imageIcon.getIconWidth();
             int imageHeight = imageIcon.getIconHeight();
             //calcula coordenadas centralizadas
             int x = (width - imageWidth) / 2;
             int y = (height - imageHeight) / 2;
             //converte Imageicon para Image
             Image image = imageIcon.getImage();
             //define cor fundo
          
             //adiciona imagem
             g.drawImage(image, x, y, imageWidth, imageHeight, Color.WHITE, this);
             */ }
        //configura cor padrão da uri
        if (option == BACKGROUND_BLUE) {
            g.setColor(new Color(0, 100, 176));
            g.fillRect(0, 0, width, height);
        }
    }//fim do método paintComponent

    //método para teste da interface
    public static void main(String[] args) {
        JPanel wallpaperPanel = new WallpaperPanel();
        wallpaperPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        JFrame frameTest = new JFrame();
        frameTest.setLayout(null);

        ScreenResolution screenResolution = new ScreenResolution();
        frameTest.setBounds(0, 0, screenResolution.getX(), screenResolution.getY());
        wallpaperPanel.setBounds(0, 0, screenResolution.getX(), screenResolution.getY());
        frameTest.add(wallpaperPanel);
        frameTest.setVisible(true);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//fim do método main
}//fim da classe WallpaperPanel

