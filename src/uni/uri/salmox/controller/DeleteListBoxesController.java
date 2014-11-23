/*-
 * Classname:             DeleteListBoxesController.java
 *
 * Version information:   1.0
 *
 * Date:                  27/05/2013 - 14:40:42
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import uni.uri.salmox.DAO.ConectionFactory;
import uni.uri.salmox.DAO.ConnectionDatabase;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.util.LogMaker;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.TemporaryData;
import uni.uri.salmox.view.DeleteListBoxesPanel;
import uni.uri.salmox.view.SelectBox;

/**
 * Gera e deleta lista de descarte de caixas
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class DeleteListBoxesController implements ObserverInterface {

    //view
    private DeleteListBoxesPanel view;
    //observador
    private ObserverInterface observer;
    //DAO genérico
    private GenericBoxDAO genericBoxDAO;
    //seleção de caixas
    private SelectBox selectBoxFrame = null;

    /**
     * Construtor sem parâmetros
     */
    public DeleteListBoxesController() {
        //inicializa view
        view = new DeleteListBoxesPanel();
        genericBoxDAO = new GenericBoxDAO();
        //define eventos
        //adicionar
        view.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBox();
            }
        });
        //cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeBox();
            }
        });
        //gerar relatório
        view.setActionListenerGenerateReportButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
        //buscar
        view.setActionListenerSearchButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
    }//fim do construtor

    /**
     * Abre seleção de categorias
     */
    private void addBox() {
        SelectCategoryController selectCategoryController = new SelectCategoryController();
        selectCategoryController.registerObserver(this);
        selectCategoryController.registerObserver(observer);
        selectCategoryController.notifyObservers(true, null);
    }//fim do método addBox

    /**
     * Abre janela para busca
     */
    private void search() {
        SearchController searchController = new SearchController();
        searchController.registerObserver(this);
        searchController.registerObserver(observer);
        searchController.notifyObservers(true, null);
        searchController.lockForBoxes();
    }//fim do método search

    /**
     * Remove caixa da lista
     */
    private void removeBox() {
        Box box = view.getSelectedBox();

        if (box != null) {
            view.removeBox(box);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
        update(false, null);
    }//fim do método removeBox

    /**
     * Gera relatório e exclui caixas
     */
    private void generateReport() {

        //notifica pede confirmação
        int response = JOptionPane.showConfirmDialog(null, "As caixas serão desativadas. Deseja continuar?", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (response != 0) {
            return;
        }
        //gera relátório
        try {
            List<Box> boxes = view.getBoxes();

            if (boxes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Não há caixas para exclusão!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //List<Integer> codes = new ArrayList();
            int codes[] = new int[boxes.size()];
            int codeClass[] = new int[boxes.size()];
            for (int i = 0; i < boxes.size(); i++) {
                // codes.add(boxes.get(i).getCodeBox());
                codes[i] = boxes.get(i).getCodeBox();
                codeClass[i] = boxes.get(i).getCodeBoxSpecific();
            }
            //separador de caminos do SO
            String separator = System.getProperty("file.separator");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(new File("reports" + separator + "exclusao.jasper"));

            ConnectionDatabase connectionDatabase = ConectionFactory.getConnection();
            Connection connection = connectionDatabase.getConnection();

            Map parametros = new HashMap();
            parametros.put("codigos_caixa", codes);
            parametros.put("data", new java.util.Date());
            parametros.put("responsavel", TemporaryData.getUser().getName());
            //parametros.put("codigo_classe", codeClass);
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
        //exclui caixas
        List< Box> boxes = view.getBoxes();
        for (int i = 0; i < boxes.size(); i++) {
            genericBoxDAO.desactiveBox(boxes.get(i).getCodeBox());
        }
    }//fim do método generateReport

    /**
     * Define observador para todos os subjetos filhos
     *
     * @param observer
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Obtêm panel da visão
     *
     * @return <code>JPanel</code> visão
     */
    public JPanel getPanel() {
        return view.getPanel();
    }//fim do método getPanel

    /**
     * Atualiza observadores
     *
     * @param active se está ativo true=ativo false=desativado
     * @param object objeto a ser atualizado
     */
    public void update(boolean active, Object object) {
        observer.update(active, null);
        GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
        if (object instanceof List) {
            List<Box> boxes = (List<Box>) object;
            for (int i = 0; i < boxes.size(); i++) {
                if (genericBoxDAO.checkActiveBox(boxes.get(i).getCodeBox())) {
                    view.addBox(boxes.get(i));
                } else {
                    JOptionPane.showMessageDialog(null, "A caixa " + boxes.get(i).getTitleBox() + " já foi descartada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (object instanceof Box) {
            Box box = (Box) object;
            if (genericBoxDAO.checkActiveBox(box.getCodeBox())) {
                view.addBox(box);
            } else {
                JOptionPane.showMessageDialog(null, "Essa caixa já foi descartada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método update
}//fim da classe DeleteListBoxesController

