/*-
 * Classname:             BoxController.java
 *
 * Version information:   1.0
 *
 * Date:                  22/05/2013 - 16:29:28
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import uni.uri.salmox.DAO.CategoryBoxDAOInterface;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.model.Administrator;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.DefaultUser;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.model.User;
import uni.uri.salmox.report.ReportSheet;
import uni.uri.salmox.report.ReportSpine;
import uni.uri.salmox.report.ReportTag;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.view.BoxPanel;

/**
 * Controle da caixa
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class BoxController implements ObserverInterface {

    //visão da caixa
    private BoxPanel boxPanel;
    //interface do DAO das categorias
    private CategoryBoxDAOInterface boxDAO;
    //enum categoria
    private Category category;
    //usuário
    private User user;
    //lista dee caixa
    private List<Box> boxes;
    //observador
    private ObserverInterface observer;
    //caixa selecionada
    private int selectedCodeBox = 0;

    /**
     * Construtor com categoria e usuário padrão
     *
     * @param category categoria
     * @param defaultUser usuário padrão
     */
    public BoxController(Category category, DefaultUser defaultUser) {
        this.category = category;
        this.user = defaultUser;

        //busca dados
        searchData();
        //inicializa e popula view
        boxPanel = new BoxPanel(category, boxes, category.getAbbreviation(), user);

        //define eventoos para os botões
        //abrir
        boxPanel.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                openBox();
            }
        });
        //solicitar
        boxPanel.setActionListenerRequestButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                request();
            }
        });
        //abrir caica xom mouse
        boxPanel.setMouseListenerTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    storeSelectedBox();
                    openBox();
                }
            }
        });
        //define os eventos de teclado doa janela
        boxPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    storeSelectedBox();
                    openBox();
                }

            }
        });
        //define evento para visualização de caixa
        boxPanel.setVisualizeBoxListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component object = e.getComponent();
                if (object instanceof JTable) {
                    JTable table = (JTable) object;

                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Box box = boxPanel.getBox(row);
                        DAOFactory daoFactory = new DAOFactory(Category.getCategory(box));
                        Box boxSpecific = daoFactory.getDAO().searchBox(box.getCodeBox());
                        table.setToolTipText(boxSpecific.toString());
                    } else {
                        table.setToolTipText("");
                    }
                }
            }
        });


    }///fim do controller 

    /**
     * Construtor com categoria e administrador
     *
     * @param category categoria
     * @param administrator administrador
     */
    public BoxController(Category category, Administrator administrator) {
        this.category = category;
        this.user = administrator;
        //busca dados
        searchData();
        //inicializa e popula view
        boxPanel = new BoxPanel(category, boxes, category.getAbbreviation(), user);

        //adiciona eventos a botões
        //adicionar
        boxPanel.setActionListenerAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                addBox();
            }
        });
        //editar
        boxPanel.setActionListenerEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                editBox();
            }
        });
        //deletar
        boxPanel.setActionListenerEraseButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                eraseBox();
            }
        });
        //gerar lombada
        boxPanel.setActionListenerGenerateSpine(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                generateSpine();
            }
        });
        //gerar etiquetas
        boxPanel.setActionListenerGenerateTag(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                generateTag();
            }
        });
        //abrir
        boxPanel.setActionListenerOpenButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                openBox();
            }
        });
        //retirar
        boxPanel.setActionListenerWithdrawButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                withdrawal();
            }
        });
        //subir posição do livro
        boxPanel.setActionListenerUPButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                up();
                restoreSelectedBox();
            }
        });
        //descer posição do livro
        boxPanel.setActionListenerDownButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                down();
                restoreSelectedBox();
            }
        });
        //ordenar livro
        boxPanel.setActionListenerSortButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeSelectedBox();
                sort();
                restoreSelectedBox();
            }
        });
        //abrir caica xom mouse
        boxPanel.setMouseListenerTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    storeSelectedBox();
                    openBox();
                }
            }
        });
        //define evemto para teclado da janela
        boxPanel.setKeyLisyenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    storeSelectedBox();
                    openBox();
                }
                if (KeyEvent.VK_DELETE == e.getKeyCode()) {
                    storeSelectedBox();
                    eraseBox();
                }
            }
        });
        //define gerar ficha

        boxPanel.setActionListenerGenerateSheetButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reportSheet();
            }
        });

        //define evento para visualização de caixa
        boxPanel.setVisualizeBoxListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component object = e.getComponent();
                if (object instanceof JTable) {
                    JTable table = (JTable) object;

                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Box box = boxPanel.getBox(row);
                        DAOFactory daoFactory = new DAOFactory(Category.getCategory(box));
                        Box boxSpecific = daoFactory.getDAO().searchBox(box.getCodeBox());
                        table.setToolTipText(boxSpecific.toString());
                    } else {
                        table.setToolTipText("");
                    }
                }
            }
        });

    }//fim do construtor

    /**
     * Retorna categoria
     *
     * @return category
     */
    private void reportSheet() {
        if (category == Category.F) {
            Box box = boxPanel.getSelectedBox();
            if (box == null) {
                JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                ReportSheet reportSheet = new ReportSheet(box);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Relatório não disponível para a categoria!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método getCategory

    /**
     * Busca dados
     */
    private void searchData() {
        DAOFactory dAOFactory = new DAOFactory(category);
        boxDAO = dAOFactory.getDAO();
        //busca caixas
        Iterator iterator = boxDAO.searchBoxes();
        boxes = new ArrayList();
        while (iterator.hasNext()) {
            boxes.add((Box) iterator.next());
        }

    }//fim do método searchData

    /**
     * Abre caixa
     */
    private void openBox() {
        Box box = boxPanel.getSelectedBox();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            BookController bookController;
            if (user instanceof Administrator) {
                bookController = new BookController(box, category, (Administrator) user);
            } else {
                bookController = new BookController(box, category, (DefaultUser) user);
            }
            bookController.registerAllObservers(observer);
            observer.update(false, bookController.getPanel());
        }
    }//fim do método openBox

    /**
     * Abre a requisição
     */
    private void request() {
        Box box = boxPanel.getSelectedBox();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);
        } else {
            boolean present = false;
            Iterator books = boxDAO.searchBooks(box.getCodeBox());
            while (books.hasNext()) {
                Iterator documents = boxDAO.searchDocuments(((Book) books.next()).getCodeBook());
                while (documents.hasNext() && !present) {
                    if (((Document) documents.next()).getPresent()) {
                        present = true;
                    }
                }
            }
            if (present) {
                RequestController requestController = new RequestController(category, box);
                requestController.registerObserver(observer);
                requestController.notifyObservers(true, false);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum documento da caixa está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método request

    /**
     * Adiciona caixa
     */
    private void addBox() {
        AddBoxController addBoxController;
        if (boxes != null && !boxes.isEmpty()) {
            Box box = boxes.get(boxes.size() - 1);

            addBoxController = new AddBoxController(category, box.getCodeBoxSpecific() + 1);


        } else {
            addBoxController = new AddBoxController(category, 1);
        }
        addBoxController.registerObserver(observer);
        addBoxController.registerObserver(this);
        addBoxController.notifyObservers(true, null);
    }//fim do método addBox

    /**
     * Edita caixa
     */
    private void editBox() {
        Box box = boxPanel.getSelectedBox();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddBoxController editBoxController = new AddBoxController(category, box);
            editBoxController.registerObserver(observer);
            editBoxController.registerObserver(this);
            editBoxController.notifyObservers(true, null);
        }
    }//fim do método editBox

    /**
     * Apaga caixa
     */
    private void eraseBox() {
        Box box = boxPanel.getSelectedBox();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "<HTML><font color=red>Caixas só devem ser excluídas por este meio no caso de erro!<br />   Para excluir caixas vá no menu 'Excluir Caixas'!</font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);

            int response = JOptionPane.showConfirmDialog(null, "Deseja continuar e excluir caixa? ", "Atenção!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == 0) {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                genericBoxDAO.deleteBox(box);
                update(false, null);
            }
        }
    }//fim do método eraseBox

    /**
     * Gera a lombada
     */
    private void generateSpine() {
        Box box = boxPanel.getSelectedBox();
        if (box != null) {
            ReportSpine reportSpine = new ReportSpine(box);
            reportSpine.createReport();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método generateSpine

    /**
     * Gera as etiquetas
     */
    private void generateTag() {
        Box box = boxPanel.getSelectedBox();
        if (box != null) {
            ReportTag reportTag = new ReportTag(box);
            reportTag.createReport();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//fim do método generateTag

    /**
     * Retira uma caixa
     */
    private void withdrawal() {
        Box box = boxPanel.getSelectedBox();
        if (box == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else if (!new GenericBoxDAO().checkActiveBox(box.getCodeBox())) {
            JOptionPane.showMessageDialog(null, "Está caixa já foi descartada!", "Atenção!", JOptionPane.WARNING_MESSAGE);

        } else {
            boolean present = false;
            Iterator books = boxDAO.searchBooks(box.getCodeBox());
            while (books.hasNext()) {
                Iterator documents = boxDAO.searchDocuments(((Book) books.next()).getCodeBook());
                while (documents.hasNext() && !present) {
                    if (((Document) documents.next()).getPresent()) {
                        present = true;
                    }
                }
            }
            if (present) {
                WithdrawalController withdrawalController = new WithdrawalController(category, box);
                withdrawalController.registerAllObservers(observer);
                withdrawalController.registerObserver(observer);
                withdrawalController.notifyObservers(true, null);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum documento da caixa está presente!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//fim do método withdrawal

    /**
     * Obtêm panel da visão
     *
     * @return <code>JPanel</code> panel
     */
    public JPanel getPanel() {
        return boxPanel.getPanel();
    }//fim do método getPanel

    /**
     * Sobe a caixa (diminui o código em 1)
     */
    private void up() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não] </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Box boxUp = boxPanel.getSelectedBox();
                if (boxUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int code;
                code = boxUp.getCodeBoxSpecific();
                if (code <= 1) {
                    return;
                }
                if (code > 1) {
                    for (int i = 0; i < boxes.size(); i++) {
                        if (boxes.get(i).getCodeBoxSpecific() == (code - 1)) {
                            boxDAO.changeCodeBox(boxes.get(i).getCodeBox(), code);
                        }
                    }
                    boxDAO.changeCodeBox(boxUp.getCodeBox(), code - 1);
                }


                searchData();
                boxPanel.refresh(boxes);
            }
        }
    }//fim do método up

    /**
     * Abaixa a caixa (diminui o código em 1)
     */
    private void down() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não]  </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                Box boxUp = boxPanel.getSelectedBox();
                if (boxUp == null) {
                    JOptionPane.showMessageDialog(null, "Selecione uma caixa!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int code = boxUp.getCodeBoxSpecific();
                if (code > 0) {
                    for (int i = 0; i < boxes.size(); i++) {
                        if (boxes.get(i).getCodeBoxSpecific() == (code + 1)) {
                            boxDAO.changeCodeBox(boxes.get(i).getCodeBox(), code);
                        }
                    }
                    boxDAO.changeCodeBox(boxUp.getCodeBox(), code + 1);
                }
                searchData();
                boxPanel.refresh(boxes);
            }
        }
    }//fim do método down

    /**
     * Ordena códigos por uma ordem especifica dererminada pelo tipo de caixa
     */
    private void sort() {
        int confirm = JOptionPane.showConfirmDialog(null, "<HTML><font color=red>Essa operação modificará o código do livro. Deseja continuar? </font></HTML>", "Atenção!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == 0) {
            String response = JOptionPane.showInputDialog(null, "<HTML><font color=red>Atenção! Você tem certeza?[sim/não]  </font></HTML>", "Atenção!", JOptionPane.WARNING_MESSAGE);
            if (response == null || response.isEmpty()) {
                return;
            }
            if (response.equalsIgnoreCase("sim")) {
                List<Box> tempBoxes = new ArrayList();
                for (int i = 0; i < tempBoxes.size(); i++) {
                    tempBoxes.add(boxes.get(i));
                }
                Collections.sort(tempBoxes, Box.forTitle);
                for (int i = 0; i < tempBoxes.size(); i++) {
                    tempBoxes.get(i).setCodeBoxSpecific(i);
                }
                for (int i = 0; i < tempBoxes.size(); i++) {
                    for (int j = 0; j < boxes.size(); j++) {
                        if (tempBoxes.get(i).getCodeBox() == boxes.get(j).getCodeBox()) {
                            boxDAO.changeCodeBox(boxes.get(j).getCodeBox(), tempBoxes.get(i).getCodeBoxSpecific());
                        }
                    }
                }
            }
        }
        searchData();
        boxPanel.refresh(boxes);

    }//fim do método sort

    /**
     * Armazenar caixa selecionada
     */
    private void storeSelectedBox() {
        if (boxPanel.getSelectedBox() != null) {
            selectedCodeBox = boxPanel.getSelectedBox().getCodeBox();
        } else {
            selectedCodeBox = 0;
        }
    }//fim do método storeSelectedBox

    /**
     * Define código da caixa selecionada
     *
     * @param selectedCodeBox código da caixa selecionada
     */
    public void setSelectedCodeBox(int selectedCodeBox) {
        this.selectedCodeBox = selectedCodeBox;
        restoreSelectedBox();
    }//fim do método setSelectedCodeBox

    /**
     * Restaura caixa selecionada
     */
    private void restoreSelectedBox() {
        boxPanel.setSelectedBox(selectedCodeBox);
    }//fim do método restoreSelectedBox

    /**
     * Recebe Observador para observados filhos
     *
     * @param observer observador
     */
    public void registerAllObservers(ObserverInterface observer) {
        this.observer = observer;
    }//fim do método registerAllObservers

    /**
     * Atualiza quando solicitado pelo observer
     *
     * @param active
     * @param object
     */
    public void update(boolean active, Object object) {
        if (!active) {
            searchData();
            boxPanel.refresh(boxes);
            boxPanel.getPanel().requestFocus();
            if (object instanceof Box) {
                Box box = (Box) object;
                int codeBox = 0;
                for (int i = 0; i < boxes.size(); i++) {
                    if (box.getCodeBoxSpecific() == boxes.get(i).getCodeBoxSpecific()) {
                        codeBox = boxes.get(i).getCodeBox();
                    }
                }
                setSelectedCodeBox(codeBox);
            }

            restoreSelectedBox();
        }
    }//fim do método update
}//fim da classe BoxController

