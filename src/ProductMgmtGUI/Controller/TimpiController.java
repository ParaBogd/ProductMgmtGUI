package ProductMgmtGUI.Controller;

import ProductMgmtGUI.data.DBconnector;
import ProductMgmtGUI.data.Timpi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class TimpiController {

    @FXML
    private GridPane timpiGridPane;

    @FXML
    private Button closeTimp;

    @FXML
    private Label div_inceput;
    @FXML
    private Label div_sfarsit;
    @FXML
    private Label prepInceput;
    @FXML
    private Label prepSfarsit;
    @FXML
    private Label filtrInceput;
    @FXML
    private Label filtrSfarsit;
    @FXML
    private Label umplInceput;
    @FXML
    private Label umplSfarsit;
    @FXML
    private Label sterInceput;
    @FXML
    private Label sterSfarsit;
    @FXML
    private Label contrMcInceput;
    @FXML
    private Label controlMcSfarsit;
    @FXML
    private Label ambInceeput;
    @FXML
    private Label ambSfarsit;

    @FXML
    private Label labelSerie;

    @FXML
    private Button addDivIn;
    @FXML
    private Button addDivSf;
    @FXML
    private Button addPrepIn;
    @FXML
    private Button addPrepSf;
    @FXML
    private Button addFilIn;
    @FXML
    private Button addFilSf;
    @FXML
    private Button addUmpIn;
    @FXML
    private Button addUmpSf;
    @FXML
    private Button addSterIn;
    @FXML
    private Button addSterSf;
    @FXML
    private Button addCmIn;
    @FXML
    private Button addCmSf;
    @FXML
    private Button addAmbIn;
    @FXML
    private Button addAmbSf;

    public static int etapa;

    private double deltaDragY;
    private double deltaDragX;

    final ContextMenu contextMenu = new ContextMenu();

    MainController controller = new MainController();


    String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    @FXML
    void initialize() {

        MenuItem modifica = new MenuItem("Modifica");
        modifica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    vizAddTimpiDialog(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(etapa);
            }
        });

        MenuItem sterge = new MenuItem("Sterge");
        sterge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(etapa);
                try {
                    DBconnector.getInstance().executeQuerryTimpi(DBconnector.getInstance().stringStergeTimp(etapa, controller.getIDSerie()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("Se sterge");
                    initialize();
                }

            }
        });

        if (contextMenu.getItems().isEmpty()){
            contextMenu.getItems().addAll(modifica, sterge);
        }

//se atribuie contextMenu pentru fiecare camp cu timpi
        div_inceput.setContextMenu(contextMenu);
        div_sfarsit.setContextMenu(contextMenu);
        prepInceput.setContextMenu(contextMenu);
        prepSfarsit.setContextMenu(contextMenu);
        filtrInceput.setContextMenu(contextMenu);
        filtrSfarsit.setContextMenu(contextMenu);
        umplInceput.setContextMenu(contextMenu);
        umplSfarsit.setContextMenu(contextMenu);
        sterInceput.setContextMenu(contextMenu);
        sterSfarsit.setContextMenu(contextMenu);
        contrMcInceput.setContextMenu(contextMenu);
        controlMcSfarsit.setContextMenu(contextMenu);
        ambInceeput.setContextMenu(contextMenu);
        ambSfarsit.setContextMenu(contextMenu);

//   codul de jos populeaza lista de timpi prin metoda checkTimp
        div_inceput.setText(checkTimp(11));
        div_sfarsit.setText(checkTimp(12));
        prepInceput.setText(checkTimp(21));
        prepSfarsit.setText(checkTimp(22));
        filtrInceput.setText(checkTimp(31));
        filtrSfarsit.setText(checkTimp(32));
        umplInceput.setText(checkTimp(41));
        umplSfarsit.setText(checkTimp(42));
        sterInceput.setText(checkTimp(51));
        sterSfarsit.setText(checkTimp(52));
        contrMcInceput.setText(checkTimp(61));
        controlMcSfarsit.setText(checkTimp(62));
        ambInceeput.setText(checkTimp(71));
        ambSfarsit.setText(checkTimp(72));
        MainController controller = new MainController();
        labelSerie.setText(controller.getNumarSerie());

// Se verifica daca in baza de date exista un timp si daca da se blocheaza butonul de adugare
        Timpi timp = cautaTimp();
        if(timp.getDivizareInceput() != null){addDivIn.setDisable(true);}else{addDivIn.setDisable(false);}
        if(timp.getDivizareFinal() != null){addDivSf.setDisable(true);}else{addDivSf.setDisable(false);}
        if(timp.getPreparareInceput() != null) {addPrepIn.setDisable(true);}else{addPrepIn.setDisable(false);}
        if(timp.getPreparareSfarsit() != null) {addPrepSf.setDisable(true);}else{addPrepSf.setDisable(false);}
        if(timp.getFiltrareInceput() != null) {addFilIn.setDisable(true);}else{addFilIn.setDisable(false);}
        if(timp.getFiltrareSfarsit() != null) {addFilSf.setDisable(true);}else{addFilSf.setDisable(false);}
        if(timp.getUmplereInceput() != null) {addUmpIn.setDisable(true);}else{addUmpIn.setDisable(false);}
        if(timp.getUmplereFinal() != null) {addUmpSf.setDisable(true);}else{addUmpSf.setDisable(false);}
        if(timp.getSterilizareInceput() != null) {addSterIn.setDisable(true);}else{addSterIn.setDisable(false);}
        if(timp.getSterilizareFinal() != null) {addSterSf.setDisable(true);}else{addSterSf.setDisable(false);}
        if(timp.getControlMacroscopicInceput() != null) {addCmIn.setDisable(true);}else{addCmIn.setDisable(false);}
        if(timp.getControlMacroscopicFinal() != null) {addCmSf.setDisable(true);}else{addCmSf.setDisable(false);}
        if(timp.getAmbalareInceput() != null) {addAmbIn.setDisable(true);}else{addAmbIn.setDisable(false);}
        if(timp.getAmbalareFinal() != null) {addAmbSf.setDisable(true);}else{addAmbSf.setDisable(false);}



// se seteaza etapa la momentul la care se da right-click pe label-ul cu timp
        div_inceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) { etapa=11; }
        });

        div_sfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=12;
            }
        });

        prepInceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=21;
            }
        });

        prepSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=22;
            }
        });

        filtrInceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=31;
            }
        });

        filtrSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=32;
            }
        });

        umplInceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=41;
            }
        });

        umplSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=42;
            }
        });

        sterInceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=51;
            }
        });

        sterSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=52;
            }
        });

        contrMcInceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=61;
            }
        });

        controlMcSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=62;
            }
        });

        ambInceeput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=71;
            }
        });

        ambSfarsit.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=72;
            }
        });

//  Se evoca dialogul de adaugare timpi si pentru fiecare buton se atribuie valoarea etapei

        addDivIn.setOnAction(event -> {
            etapa=11;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addDivSf.setOnAction(event -> {
            etapa=12;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addPrepIn.setOnAction(event -> {
            etapa=21;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addPrepSf.setOnAction(event -> {
            etapa=22;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addFilIn.setOnAction(event -> {
            etapa=31;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addFilSf.setOnAction(event -> {
            etapa=32;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addUmpIn.setOnAction(event -> {
            etapa=41;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addUmpSf.setOnAction(event -> {
            etapa=42;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);
        });

        addSterIn.setOnAction(event -> {
            etapa=51;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
            e.printStackTrace();
            }
            System.out.println(etapa);} );

        addSterSf.setOnAction(event -> {
            etapa=52;
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(etapa);});

       addCmIn.setOnAction(event -> {
           etapa=61;
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           System.out.println(etapa);
       });

       addCmSf.setOnAction(event -> {
           etapa=62;
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           System.out.println(etapa);
       });

       addAmbIn.setOnAction(event -> {
           etapa=71;
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           System.out.println(etapa);
       });

       addAmbSf.setOnAction(event -> {
           etapa=72;
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           System.out.println(etapa);
       });

       closeTimp.setOnAction(event -> {
           exitWindow();
       });

// Permite miscarea casutei de fialog
       timpiGridPane.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               deltaDragY = getStage().getY() - event.getScreenY();
               deltaDragX = getStage().getX() - event.getScreenX();
           }
       });

       timpiGridPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               getStage().setX(event.getScreenX() + deltaDragX);
               getStage().setY(event.getScreenY() + deltaDragY);
           }
       });

    }

    public Stage getStage () {
        Stage stage = (Stage)  timpiGridPane.getScene().getWindow();
        return stage;

    }

    public void exitWindow () {
        Stage stage = (Stage) closeTimp.getScene().getWindow();

        stage.close();
    }

    public Timestamp getTimes (int etapa) {
        Timestamp timestamp = null;

        Timpi timp = cautaTimp();
        switch(etapa) {
            case 11:
                timestamp = timp.getDivizareInceput();
                break;
            case 12:
                timestamp = timp.getDivizareFinal();
                break;
            case 21:
                timestamp = timp.getPreparareInceput();
                break;
            case 22:
                timestamp = timp.getPreparareSfarsit();
                break;
            case 31:
                timestamp = timp.getFiltrareInceput();
                break;
            case 32:
                timestamp = timp.getFiltrareSfarsit();
                break;
            case 41:
                timestamp = timp.getUmplereInceput();
                break;
            case 42:
                timestamp = timp.getUmplereFinal();
                break;
            case 51:
                timestamp = timp.getSterilizareInceput();
                break;
            case 52:
                timestamp = timp.getSterilizareFinal();
                break;
            case 61:
                timestamp = timp.getControlMacroscopicInceput();
                break;
            case 62:
                timestamp = timp.getControlMacroscopicFinal();
                break;
            case 71:
                timestamp = timp.getAmbalareInceput();
                break;
            case 72:
                timestamp = timp.getAmbalareFinal();
                break;
        }
        return timestamp;
    }

    public String checkTimp (int etapa) {
        String returned;
        String noTimp = "Nu exista timp inregistrat";

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            if (getTimes(etapa) != null) {
                returned = simpleDateFormat.format(getTimes(etapa));
            } else {
                returned = noTimp;
            }

        return returned;
    }

    public static Timpi cautaTimp () {
        Timpi timpRet = new Timpi();
        ArrayList<Timpi> timpi = DBconnector.getInstance().querryTimpi();
        MainController controller = new MainController();
        int idSerie = controller.getIDSerie();
        System.out.println(idSerie);
        for(Timpi timp:timpi) {
            if (idSerie == timp.getIdSerie()) {
                timpRet = timp;
            }
        }
        return timpRet;
    }


    public void vizAddTimpiDialog (javafx.event.ActionEvent event) throws IOException {

        Parent root;

        root = FXMLLoader.load(getClass().getResource("/ProductMgmtGUI/View/InsertTimeDialog.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Adauga timpi");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(timpiGridPane.getScene().getWindow());
        stage.show();
        stage.setOnHidden(new javafx.event.EventHandler<WindowEvent>(){
            public void handle (WindowEvent we) {
                initialize();
            }
        });

    }


    public static int getEtapa() {
        return etapa;
    }
}
