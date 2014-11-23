/*-
 * Classname:             ReportTag.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 13:34:26
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
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.LogMaker;

/**
 * Classe responsável por gerar as etiquetas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ReportTag {
//caixa

    private Box box;

    /**
     * Método construtor com caixa
     *
     * @param box caixa
     */
    public ReportTag(Box box) {
        this.box = box;

    }//fim do método construtor

    /**
     * Cria o relatório
     */
    public void createReport() {
        try {
            //separador de caminos do SO
            String separator = System.getProperty("file.separator");

            JasperReport reporte;
            if (Category.getCategory(box) != Category.AE) {
                reporte = (JasperReport) JRLoader.loadObject(new File("reports" + separator + "etiqueta.jasper"));
            } else {
                reporte = (JasperReport) JRLoader.loadObject(new File("reports" + separator + "etiqueta_ae.jasper"));

            }
            ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
            Connection connection = connectionDatabase.getConnection();

            Map parametros = new HashMap();
            parametros.put("codigo_caixa", box.getCodeBox());
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
}//fim da classe ReportTag

