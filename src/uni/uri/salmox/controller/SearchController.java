/*-
 * Classname:             SearchController.java
 *
 * Version information:   1.0
 *
 * Date:                  23/05/2013 - 16:16:53
 *
 * author:                Jonas Mayer (jmayer13@hotmail.com)
 * Copyright notice:      (informações do método, pra que serve, idéia principal)
 */
package uni.uri.salmox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import uni.uri.salmox.DAO.ADAO;
import uni.uri.salmox.DAO.AEDAO;
import uni.uri.salmox.DAO.CCDAO;
import uni.uri.salmox.DAO.DACDAO;
import uni.uri.salmox.DAO.DADDAO;
import uni.uri.salmox.DAO.DAOFactory;
import uni.uri.salmox.DAO.DGDAO;
import uni.uri.salmox.DAO.DPDAO;
import uni.uri.salmox.DAO.DSGDAO;
import uni.uri.salmox.DAO.EXDAO;
import uni.uri.salmox.DAO.FCDAO;
import uni.uri.salmox.DAO.FDAO;
import uni.uri.salmox.DAO.FPDAO;
import uni.uri.salmox.DAO.GenericBoxDAO;
import uni.uri.salmox.DAO.MCDAO;
import uni.uri.salmox.DAO.SAEDAO;
import uni.uri.salmox.model.Book;
import uni.uri.salmox.model.Box;
import uni.uri.salmox.model.Document;
import uni.uri.salmox.util.Category;
import uni.uri.salmox.util.ObserverInterface;
import uni.uri.salmox.util.SubjectInterface;
import uni.uri.salmox.view.ResultSearchBookTableModel;
import uni.uri.salmox.view.ResultSearchBoxTableModel;
import uni.uri.salmox.view.ResultSearchDocumentTableModel;
import uni.uri.salmox.view.SearchFrame;

/**
 * Controle de busca
 *
 * @see
 * @author Jonas Mayer (jmayer13@hotmail.com)
 */
public class SearchController implements SubjectInterface, ObserverInterface {

    //lista de observers
    private List<ObserverInterface> observers;
    //visão
    private SearchFrame view;
    //flag para seleção de exclusão 
    private boolean selectToDelete = false;
    //flag retirada aberta
    private boolean openWithdrawal = false;

    /**
     * Contrutor sem parâmetros
     *
     */
    public SearchController() {

        //cria lista de observadores
        observers = new ArrayList();
        //inicializa view
        view = new SearchFrame();

        //define eventos
        view.setActionListenerSearchButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        //cancelar
        view.setActionListenerCancelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        //fechar janela
        view.setCloseWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //evento para teclas
        view.setKeyListenerFrame(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    search();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });


    }//fim do método construtor

    /**
     * Trava seleção para caixas
     */
    public void lockForBoxes() {
        view.lockInBox();
        selectToDelete = true;
    }//fim do método lockForBoxes

    /**
     * Busca
     */
    private void search() {
        //tipo da busca
        int type = view.getTypeSearch();
        //categoria de caixa
        Category category = view.getCategory();
        //termo da busca
        String term = view.getSearchTerm();
        //formato de data
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //controle de resultados de buscas
        SearchResultController searchResultController = null;
        List<Box> boxes = new ArrayList();
        List<Book> books = new ArrayList();
        List<Document> documents = new ArrayList();

        if (term == null || term.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira um termo para busca!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //carrega parâmetros da busca
        Iterator iterator = null;
        if (type == 0) {
            if (Category.A == category) {
                ADAO aDAO = new ADAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxA;
                try {
                    codeBoxA = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxA = 0;
                }
                String title = parameters[1];
                String typeBox = parameters[2];
                String responsible = parameters[3];
                String observation = parameters[4];
                iterator = aDAO.searchBox(codeBoxA, title, typeBox, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.AE == category) {
                AEDAO aeDAO = new AEDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxAE;
                try {
                    codeBoxAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxAE = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = aeDAO.searchBox(codeBoxAE, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.CC == category) {
                CCDAO ccDAO = new CCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxCC;
                try {
                    codeBoxCC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxCC = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = ccDAO.searchBox(codeBoxCC, title, responsible, observation);

                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.DAC == category) {
                DACDAO dacDAO = new DACDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxDAC;
                try {
                    codeBoxDAC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxDAC = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = dacDAO.searchBox(codeBoxDAC, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.DAD == category) {
                DADDAO dacDAO = new DADDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxDAD;
                try {
                    codeBoxDAD = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxDAD = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = dacDAO.searchBox(codeBoxDAD, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.DG == category) {
                DGDAO dgDAO = new DGDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxDG;
                try {
                    codeBoxDG = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxDG = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = dgDAO.searchBox(codeBoxDG, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.DP == category) {
                DPDAO dpDAO = new DPDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxDP;
                try {
                    codeBoxDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxDP = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = dpDAO.searchBox(codeBoxDP, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.DSG == category) {
                DSGDAO dsgDAO = new DSGDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxDSG;
                try {
                    codeBoxDSG = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxDSG = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = dsgDAO.searchBox(codeBoxDSG, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.EX == category) {
                EXDAO exDAO = new EXDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxEX;
                try {
                    codeBoxEX = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxEX = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = exDAO.searchBox(codeBoxEX, title, responsible, observation);

                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.F == category) {
                FDAO fDAO = new FDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxF;
                try {
                    codeBoxF = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxF = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = fDAO.searchBox(codeBoxF, title, responsible, observation);

                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.FC == category) {
                FCDAO fcDAO = new FCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxFC;
                try {
                    codeBoxFC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxFC = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = fcDAO.searchBox(codeBoxFC, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.FP == category) {
                FPDAO fpDAO = new FPDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxFP;
                try {
                    codeBoxFP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxFP = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = fpDAO.searchBox(codeBoxFP, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.MC == category) {
                MCDAO mcDAO = new MCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxMC;
                try {
                    codeBoxMC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxMC = 0;
                }
                String title = parameters[1];
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = mcDAO.searchBox(codeBoxMC, title, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else if (Category.SAE == category) {
                SAEDAO saeDAO = new SAEDAO();
                String[] parameters = view.getSearchParameters();
                int codeBoxSAE;
                try {
                    codeBoxSAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBoxSAE = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                String responsible = parameters[2];
                String observation = parameters[3];
                iterator = saeDAO.searchBox(codeBoxSAE, title, year, responsible, observation);
                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(box);
                }
            } else {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                String[] parameters = view.getSearchParameters();
                String title = parameters[0];
                String responsible = parameters[1];
                String observation = parameters[2];
                iterator = genericBoxDAO.searchBox(title, responsible, observation);

                while (iterator.hasNext()) {
                    Box box = ((Box) iterator.next());
                    boxes.add(genericBoxDAO.discoveryCategory(box));
                }
            }
            close();
            ResultSearchBoxTableModel model = new ResultSearchBoxTableModel(boxes);
            searchResultController = new SearchResultController(model);
            if (selectToDelete) {
                searchResultController.setDeleteMode();
            }
            for (int i = 0; i < observers.size(); i++) {
                searchResultController.registerObserver(observers.get(i));
            }
            searchResultController.notifyObservers(true, null);

            //livro
        } else if (type == 1) {
            if (Category.A == category) {
                ADAO aDAO = new ADAO();
                String[] parameters = view.getSearchParameters();
                int codeBookA;
                try {
                    codeBookA = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookA = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                String typeBox = parameters[3];
                String observation = parameters[4];
                iterator = aDAO.searchBook(codeBookA, title, year, typeBox, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(aDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.AE == category) {
                AEDAO aeDAO = new AEDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookAE;
                try {
                    codeBookAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookAE = 0;
                }
                String title = parameters[1];
                int codeCourse = 0;
                try {
                    codeCourse = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    codeCourse = 0;
                }
                int year;
                try {
                    year = Integer.valueOf(parameters[3]);
                } catch (Exception exception) {
                    year = 0;
                }
                String observation = parameters[4];
                iterator = aeDAO.searchBook(codeBookAE, title, codeCourse, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(aeDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.CC == category) {
                CCDAO ccDAO = new CCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookCC;
                try {
                    codeBookCC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookCC = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = ccDAO.searchBook(codeBookCC, title, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(ccDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.DAC == category) {
                DACDAO dacDAO = new DACDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDAC;
                try {
                    codeBookDAC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDAC = 0;
                }
                String title = parameters[1];
                String year = parameters[2];

                String observation = parameters[3];
                iterator = dacDAO.searchBook(codeBookDAC, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dacDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.DAD == category) {
                DADDAO dacDAO = new DADDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDAD;
                try {
                    codeBookDAD = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDAD = 0;
                }
                String title = parameters[1];
                String year = parameters[2];

                String observation = parameters[3];
                iterator = dacDAO.searchBook(codeBookDAD, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dacDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.DG == category) {
                DGDAO dacDAO = new DGDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDAC;
                try {
                    codeBookDAC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDAC = 0;
                }
                String title = parameters[1];
                String year = parameters[2];

                String observation = parameters[3];
                iterator = dacDAO.searchBook(codeBookDAC, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dacDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.DP == category) {
                DPDAO dpDAO = new DPDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDP;
                try {
                    codeBookDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDP = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                String observation = parameters[3];
                iterator = dpDAO.searchBook(codeBookDP, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dpDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.DSG == category) {
                DSGDAO dacDAO = new DSGDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDAC;
                try {
                    codeBookDAC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDAC = 0;
                }
                String title = parameters[1];
                String year = parameters[2];

                String observation = parameters[3];
                iterator = dacDAO.searchBook(codeBookDAC, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dacDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.EX == category) {
                EXDAO exDAO = new EXDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookEX;
                try {
                    codeBookEX = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookEX = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = exDAO.searchBook(codeBookEX, title, observation);

                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(exDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.F == category) {
                FDAO fDAO = new FDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookF;
                try {
                    codeBookF = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookF = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                int semester;
                try {
                    semester = Integer.valueOf(parameters[3]);
                } catch (Exception exception) {
                    semester = 0;
                }
                String observation = parameters[4];
                iterator = fDAO.searchBook(codeBookF, title, year, semester, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(fDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.FC == category) {
                FCDAO dacDAO = new FCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookDAC;
                try {
                    codeBookDAC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookDAC = 0;
                }
                String title = parameters[1];
                String year = parameters[2];

                String observation = parameters[3];
                iterator = dacDAO.searchBook(codeBookDAC, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(dacDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.FP == category) {
                FPDAO fpDAO = new FPDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookFP;
                try {
                    codeBookFP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookFP = 0;
                }
                String title = parameters[1];
                String typeOfCourse = parameters[2];
                int year;
                try {
                    year = Integer.valueOf(parameters[3]);
                } catch (Exception exception) {
                    year = 0;
                }
                String observation = parameters[4];
                iterator = fpDAO.searchBook(codeBookFP, title, typeOfCourse, year, observation);

                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(fpDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.MC == category) {
                MCDAO mcDAO = new MCDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookMC;
                try {
                    codeBookMC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookMC = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                String observation = parameters[3];
                iterator = mcDAO.searchBook(codeBookMC, title, year, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(mcDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else if (Category.SAE == category) {
                SAEDAO saeDAO = new SAEDAO();
                String[] parameters = view.getSearchParameters();
                int codeBookSAE;
                try {
                    codeBookSAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeBookSAE = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = saeDAO.searchBook(codeBookSAE, title, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(saeDAO.searchBox(books.get(i).getCodeBox()));
                }
            } else {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                String[] parameters = view.getSearchParameters();
                String title = parameters[0];
                String observation = parameters[1];
                iterator = genericBoxDAO.searchBook(title, observation);
                while (iterator.hasNext()) {
                    books.add((Book) iterator.next());
                }
                for (int i = 0; i < books.size(); i++) {
                    boxes.add(genericBoxDAO.searchBox(books.get(i).getCodeBox()));
                }

                for (int i = 0; i < boxes.size(); i++) {
                    boxes.set(i, genericBoxDAO.discoveryCategory(boxes.get(i)));
                }

                //converte para especifico
                for (int i = 0; i < boxes.size(); i++) {
                    category = Category.getCategory(boxes.get(i));

                    for (int j = 0; j < books.size(); j++) {
                        if (books.get(j).getCodeBox() == boxes.get(i).getCodeBox()) {
                            DAOFactory daoFactory = new DAOFactory(category);
                            books.set(j, daoFactory.getDAO().searchBook(books.get(j).getCodeBook()));

                        }
                    }
                }
            }

            close();
            ResultSearchBookTableModel model = new ResultSearchBookTableModel(boxes, books);
            searchResultController = new SearchResultController(model);
            for (int i = 0; i < observers.size(); i++) {
                searchResultController.registerObserver(observers.get(i));
            }
            searchResultController.notifyObservers(true, null);

            //documentos
        } else if (type == 2) {
            if (Category.A == category) {
                ADAO aDAO = new ADAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentA;
                try {
                    codeDocumentA = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentA = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = aDAO.searchDocument(codeDocumentA, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = aDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(aDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.AE == category) {
                AEDAO aeDAO = new AEDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentAE;
                try {
                    codeDocumentAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentAE = 0;
                }
                String title = parameters[1];
                java.sql.Date dateExam;
                try {
                    dateExam = new java.sql.Date(df.parse(parameters[2]).getTime());
                } catch (Exception exception) {
                    dateExam = null;
                }
                String annex = parameters[3];
                String observation = parameters[4];
                iterator = aeDAO.searchDocument(codeDocumentAE, title, dateExam, annex, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = aeDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(aeDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.CC == category) {
                CCDAO ccDAO = new CCDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentCC;
                try {
                    codeDocumentCC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentCC = 0;
                }
                String title = parameters[1];
                int year;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                int semester;
                try {
                    semester = Integer.valueOf(parameters[3]);
                } catch (Exception exception) {
                    semester = 0;
                }
                String observation = parameters[4];
                iterator = ccDAO.searchDocument(codeDocumentCC, title, year, semester, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = ccDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(ccDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.DAC == category) {
                DACDAO dpDAO = new DACDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.DAD == category) {
                DADDAO dpDAO = new DADDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.DG == category) {
                DGDAO dpDAO = new DGDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.DP == category) {
                DPDAO dpDAO = new DPDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.DSG == category) {
                DSGDAO dpDAO = new DSGDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }

            } else if (Category.EX == category) {
                EXDAO exDAO = new EXDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentEX;
                try {
                    codeDocumentEX = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentEX = 0;
                }
                String title = parameters[1];
                int year = 0;
                try {
                    year = Integer.valueOf(parameters[2]);
                } catch (Exception exception) {
                    year = 0;
                }
                String observation = parameters[3];
                iterator = exDAO.searchDocument(codeDocumentEX, title, year, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = exDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(exDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.F == category) {
                FDAO fDAO = new FDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentF;
                try {
                    codeDocumentF = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentF = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = fDAO.searchDocument(codeDocumentF, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = fDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(fDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.FC == category) {
                FCDAO dpDAO = new FCDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentDP;
                try {
                    codeDocumentDP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentDP = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = dpDAO.searchDocument(codeDocumentDP, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = dpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(dpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.FP == category) {
                FPDAO fpDAO = new FPDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentFP;
                try {
                    codeDocumentFP = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentFP = 0;
                }
                String title = parameters[1];
                String studentStatus = parameters[2];
                String observation = parameters[3];
                iterator = fpDAO.searchDocument(codeDocumentFP, title, studentStatus, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = fpDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(fpDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.MC == category) {
                MCDAO mcDAO = new MCDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentMC;
                try {
                    codeDocumentMC = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentMC = 0;
                }
                String title = parameters[1];
                java.sql.Date date;
                try {
                    date = new java.sql.Date(df.parse(parameters[2]).getTime());
                } catch (Exception exception) {
                    date = null;
                }
                String observation = parameters[3];
                iterator = mcDAO.searchDocument(codeDocumentMC, title, date, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = mcDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(mcDAO.searchBox(book.getCodeBox()));
                }
            } else if (Category.SAE == category) {
                SAEDAO saeDAO = new SAEDAO();
                String[] parameters = view.getSearchParameters();
                int codeDocumentSAE;
                try {
                    codeDocumentSAE = Integer.valueOf(parameters[0]);
                } catch (Exception exception) {
                    codeDocumentSAE = 0;
                }
                String title = parameters[1];
                String observation = parameters[2];
                iterator = saeDAO.searchDocument(codeDocumentSAE, title, observation);
                while (iterator.hasNext()) {
                    documents.add((Document) iterator.next());
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = saeDAO.searchBook(documents.get(i).getCodeBook());
                    books.add(book);
                    boxes.add(saeDAO.searchBox(book.getCodeBox()));
                }
            } else {
                GenericBoxDAO genericBoxDAO = new GenericBoxDAO();
                String[] parameters = view.getSearchParameters();
                String title = parameters[0];
                String observation = parameters[1];
                iterator = genericBoxDAO.searchDocument(title, observation);

                while (iterator.hasNext()) {
                    Document document = (Document) iterator.next();
                    documents.add(document);
                }
                for (int i = 0; i < documents.size(); i++) {
                    Book book = genericBoxDAO.searchBook(documents.get(i).getCodeBook());
                    boolean haveBook = false;
                    for (int j = 0; j < books.size(); j++) {
                        if (books.get(j).getCodeBook() == book.getCodeBook()) {
                            haveBook = true;
                        }
                    }
                    if (!haveBook) {
                        books.add(book);
                    }

                    Box box = genericBoxDAO.searchBox(book.getCodeBox());
                    boolean haveBox = false;
                    for (int j = 0; j < boxes.size(); j++) {
                        if (boxes.get(j).getCodeBox() == book.getCodeBox()) {
                            haveBox = true;
                        }
                    }
                    if (!haveBox) {
                        boxes.add(box);
                    }
                }
                List<Box> tempBoxes = new ArrayList();
                for (int i = 0; i < boxes.size(); i++) {
                    tempBoxes.add(genericBoxDAO.discoveryCategory(boxes.get(i)));
                }
                boxes.clear();
                for (int i = 0; i < tempBoxes.size(); i++) {
                    boxes.add(tempBoxes.get(i));
                }
                List<Book> tempBooks = new ArrayList();
                List<Document> tempDocuments = new ArrayList();
                //converte para especifico
                for (int i = 0; i < boxes.size(); i++) {
                    category = Category.getCategory(boxes.get(i));

                    for (int j = 0; j < books.size(); j++) {

                        if (books.get(j).getCodeBox() == boxes.get(i).getCodeBox()) {

                            for (int k = 0; k < documents.size(); k++) {
                                if (documents.get(k).getCodeBook() == books.get(j).getCodeBook()) {
                                    DAOFactory daoFactory = new DAOFactory(category);
                                    tempDocuments.add(daoFactory.getDAO().searchDocument(documents.get(k).getCodeDocument()));

                                }
                            }
                            DAOFactory daoFactory = new DAOFactory(category);

                            tempBooks.add(daoFactory.getDAO().searchBook(books.get(j).getCodeBook()));

                        }
                    }
                }
                documents.clear();
                books.clear();
                for (int i = 0; i < tempDocuments.size(); i++) {
                    documents.add(tempDocuments.get(i));
                }
                for (int i = 0; i < tempBooks.size(); i++) {
                    books.add(tempBooks.get(i));
                }

            }


            close();
            ResultSearchDocumentTableModel model = new ResultSearchDocumentTableModel(boxes, books, documents);
            searchResultController = new SearchResultController(model);
            for (int i = 0; i < observers.size(); i++) {
                searchResultController.registerObserver(observers.get(i));
            }
            searchResultController.notifyObservers(true, null);
        }
        if (openWithdrawal) {
            searchResultController.justUpdate();
        }
    }//fim do método search

    /**
     * Cancela o cadastro/edição da caixa
     */
    private void cancel() {
        view.close();
        notifyObservers(false, null);
    }//fim do método cancel

    //fecha janela
    private void close() {
        cancel();
    }//fim do método close

    /**
     * Registra observador
     *
     * @param observer observador
     */
    public void registerObserver(ObserverInterface observer) {
        observers.add(observer);
    }//fim do método registerObserver

    /**
     * Remove observador da lista
     *
     * @param observer observador
     */
    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }//fim do método removeObserver

    /**
     * Notifica os observadores das mudanças
     *
     * @param active janela ativa
     * @param object objeto de retorno
     */
    public void notifyObservers(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método notifyObservers

    /**
     * Define se frame de retirada já está aberto
     *
     * @param flag
     */
    public void setOpenedWithdrawal(boolean flag) {
        openWithdrawal = flag;
    }//fim do método setOpenedWithdrawal

    /**
     * Atualiza
     *
     * @param active
     * @param object
     */
    public void update(boolean active, Object object) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(active, object);
        }
    }//fim do método update
}//fim da classe SearchController

