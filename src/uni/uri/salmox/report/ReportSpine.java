/*-
 * Classname:             ReportSpine.java
 *
 * Version information:   1.1
 *
 * Date:                  27/05/2013 - 13:32:35
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.report;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import uni.uri.salmox.DAO.ConectionFactory;
import uni.uri.salmox.DAO.ConnectionDatabase;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.LogMaker;

/**
 * Classe responsável por gerar a lombada da caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ReportSpine {
    //caixa

    private Box box;

    /**
     * Método contrutor com caixa
     *
     * @param box
     */
    public ReportSpine(Box box) {
        this.box = box;

    }//fim do construtor

    /**
     * Cria o relatório
     */
    public void createReport() {
        try {
            Category category = Category.getCategory(box);
            String sector = "";
            if (category == Category.A) {
                sector = "1000 - ALMOXARIFADO/COMPRAS";
            } else if (category == Category.AE) {
                sector = "100 - SECRETARIA";
            } else if (category == Category.CC) {
                sector = "100 - SECRETARIA";
            } else if (category == Category.DAC) {
                sector = "600 - DIREÇÃO";
            } else if (category == Category.DAD) {
                sector = "600 - DIREÇÃO";
            } else if (category == Category.DG) {
                sector = "600 - DIREÇÃO";
            } else if (category == Category.DP) {
                sector = "700 - DEPARTAMENTO PESSOAL";
            } else if (category == Category.DSG) {
                sector = "600 - DIREÇÃO";
            } else if (category == Category.EX) {
                sector = "100 - SECRETARIA";
            } else if (category == Category.F) {
                sector = "100 - SECRETARIA";
            } else if (category == Category.FC) {
                sector = "900 - FINANCEIRO/JURÍDICO";
            } else if (category == Category.FP) {
                sector = "100 - SECRETARIA";
            } else if (category == Category.MC) {
                sector = "800 - CONTABILIDADE";
            } else if (category == Category.SAE) {
                sector = "500 - SAE";
            }


            //separador de caminos do SO
            String separator = System.getProperty("file.separator");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("reports" + separator + "lombada.jasper"));

            ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
            Connection connection = connectionDatabase.getConnection();

            Map parametros = new HashMap();
            parametros.put("codigo_caixa", box.getCodeBox());
            Box espBox = new GenericBoxDAO().discoveryCategory(box);
            parametros.put("categoria", category.getName());
            parametros.put("setor", sector);
            parametros.put("codigo_classe", box.getCodeClass());
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);

            File directory = new File("temp");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File reportFile = new File(directory, "" + new java.util.Date().getTime() + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(reportFile);
            JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);
            fileOutputStream.close();
            java.awt.Desktop.getDesktop().open(reportFile);

        } catch (Exception exception) {
            System.err.println("Erro: não foi possível gerar relatório!");
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: não foi possível gerar relatório!" + exception.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            LogMaker.create(exception);
        }
    }//fim do método createReport
}//fim da classe ReportSpine

