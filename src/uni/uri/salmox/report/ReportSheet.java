/*- 
 * Classname:             ReportSheet.java 
 * 
 * Version information:   1.1
 * 
 * Date:                  05/05/2014 - 13:20:39 
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
 * Relatório ficha para caixa FC
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class ReportSheet {

    //caixa que terá ficha gerada
    private Box box;

    /**
     * Construtor com caixa
     *
     * @param box
     */
    public ReportSheet(Box box) {
        this.box = box;
        createReport();
    }//fim do método box

    /**
     * Cria o relatório
     */
    private void createReport() {
        try {
            //separador de caminos do SO
            String separator = System.getProperty("file.separator");
            String file = "";
            Category category = Category.getCategory(box);
            if (category == Category.DAC) {
                file = "ficha_DAC.jasper";
            } else if (category == Category.DAD) {
                file = "ficha_DAD.jasper";
            } else if (category == Category.FC) {
                file = "ficha_FC.jasper";
            } else if (category == Category.DSG) {
                file = "ficha_DSG.jasper";
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("reports" + separator + file));

            ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
            Connection connection = connectionDatabase.getConnection();

            Map parametros = new HashMap();
            parametros.put("codigo_caixa", box.getCodeBox());
            Box espBox = new GenericBoxDAO().discoveryCategory(box);
            parametros.put("categoria", Category.getCategory(espBox).getName());
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
}//fim da classe ReportSheet 

