package ProductMgmtGUI.Controller;

import ProductMgmtGUI.MainController;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimpiController {

    @FXML
    private GridPane timpiGridPane;

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
    private Label sterilizat;
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

    final ContextMenu contextMenu = new ContextMenu();


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
                System.out.println("Se sterge");
            }
        });

        if (contextMenu.getItems().isEmpty()){
            contextMenu.getItems().addAll(modifica, sterge);
        }

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


        Timpi timp = cautaTimp();
        if(timp.getDivizare_inceput() != null){addDivIn.setDisable(true);}
        if(timp.getDivizare_final() != null){addDivSf.setDisable(true);}
        if(timp.getPreparare_inceput() != null) {addPrepIn.setDisable(true);}
        if(timp.getPreparare_sfarsit() != null) {addPrepSf.setDisable(true);}
        if(timp.getFiltrare_inceput() != null) {addFilIn.setDisable(true);}
        if(timp.getFiltrare_sfarsit() != null) {addFilSf.setDisable(true);}
        if(timp.getUmplere_inceput() != null) {addUmpIn.setDisable(true);}
        if(timp.getUmplere_final() != null) {addUmpSf.setDisable(true);}
        if(timp.getSterilizare_inceput() != null) {addSterIn.setDisable(true);}
        if(timp.getSterilizare_final() != null) {addSterSf.setDisable(true);}
        if(timp.getControl_macroscopic_inceput() != null) {addCmIn.setDisable(true);}
        if(timp.getControl_macroscopic_final() != null) {addCmSf.setDisable(true);}
        if(timp.getAmbalare_inceput() != null) {addAmbIn.setDisable(true);}
        if(timp.getAmbalare_final() != null) {addAmbSf.setDisable(true);}

        div_inceput.setText(checkTimp(11));
        div_sfarsit.setText(checkTimp(12));
        prepInceput.setText(checkTimp(21));
        prepSfarsit.setText(checkTimp(22));
        filtrInceput.setText(checkTimp(31));
        filtrSfarsit.setText(checkTimp(32));
        umplInceput.setText(checkTimp(41));
        umplSfarsit.setText(checkTimp(42));
        sterilizat.setText(checkTimp(50));
        sterInceput.setText(checkTimp(51));
        sterSfarsit.setText(checkTimp(52));
        contrMcInceput.setText(checkTimp(61));
        controlMcSfarsit.setText(checkTimp(62));
        ambInceeput.setText(checkTimp(71));
        ambSfarsit.setText(checkTimp(72));
        MainController controller = new MainController();
        labelSerie.setText(controller.getNumarSerie());

        div_inceput.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                etapa=11;
                System.out.println(etapa);
            }
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

        addDivIn.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=11;
            System.out.println(etapa);
        });

        addDivSf.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=12;
            System.out.println(etapa);
        });

        addPrepIn.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=21;
            System.out.println(etapa);
        });

        addPrepSf.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=22;
            System.out.println(etapa);
        });

        addFilIn.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=31;
            System.out.println(etapa);
        });

        addFilSf.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=32;
            System.out.println(etapa);
        });

        addUmpIn.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=41;
            System.out.println(etapa);
        });

        addUmpSf.setOnAction(event -> {
            try {
                vizAddTimpiDialog(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etapa=42;
            System.out.println(etapa);
        });

        addSterIn.setOnAction(event -> { try {
            vizAddTimpiDialog(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
            etapa=51;
            System.out.println(etapa);} );

        addSterSf.setOnAction(event -> { try {
            vizAddTimpiDialog(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
            etapa=52;
            System.out.println(etapa);});

       addCmIn.setOnAction(event -> {
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           etapa=61;
           System.out.println(etapa);
       });

       addCmSf.setOnAction(event -> {
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           etapa=62;
           System.out.println(etapa);
       });

       addAmbIn.setOnAction(event -> {
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           etapa=71;
           System.out.println(etapa);
       });

       addAmbSf.setOnAction(event -> {
           try {
               vizAddTimpiDialog(event);
           } catch (IOException e) {
               e.printStackTrace();
           }
           etapa=72;
           System.out.println(etapa);
       });
    }


    public String populateLabels (int etapa) {
        String timpRet = null;
        Boolean sterilizare = false;

        Timpi timp = cautaTimp();
                switch(etapa) {
                    case 11:
                        timpRet = simpleDateFormat.format(timp.getDivizare_inceput());
                        System.out.println(timpRet);
                        break;
                    case 12:
                        timpRet = simpleDateFormat.format(timp.getDivizare_final());
                        System.out.println(timpRet);
                        break;
                    case 21:
                        timpRet = simpleDateFormat.format(timp.getPreparare_inceput());
                        break;
                    case 22:
                        timpRet = simpleDateFormat.format(timp.getPreparare_sfarsit());
                        break;
                    case 31:
                        timpRet = simpleDateFormat.format(timp.getFiltrare_inceput());
                        break;
                    case 32:
                        timpRet = simpleDateFormat.format(timp.getFiltrare_sfarsit());
                        break;
                    case 41:
                        timpRet = simpleDateFormat.format(timp.getUmplere_inceput());
                        break;
                    case 42:
                        timpRet = simpleDateFormat.format(timp.getUmplere_final());
                        break;
                    case 50:
                        if(timp.isSterilizat()){
                           timpRet = "DA";
                        }else{
                            timpRet = "NU";
                        }
                        break;
                    case 51:
                        timpRet = simpleDateFormat.format(timp.getSterilizare_inceput());
                        break;
                    case 52:
                        timpRet = simpleDateFormat.format(timp.getSterilizare_final());
                        break;
                    case 61:
                        timpRet = simpleDateFormat.format(timp.getControl_macroscopic_inceput());
                        break;
                    case 62:
                        timpRet = simpleDateFormat.format(timp.getControl_macroscopic_final());
                        break;
                    case 71:
                        timpRet = simpleDateFormat.format(timp.getAmbalare_inceput());
                        break;
                    case 72:
                        timpRet = simpleDateFormat.format(timp.getAmbalare_final());
                        break;
                }
        return timpRet;
    }

    public Timestamp getTimes (int etapa) {
        Timestamp timestamp = null;

        Timpi timp = cautaTimp();
        switch(etapa) {
            case 11:
                timestamp = timp.getDivizare_inceput();
                System.out.println(timp);
                break;
            case 12:
                timestamp = timp.getDivizare_final();
                System.out.println(timp);
                break;
            case 21:
                timestamp = timp.getPreparare_inceput();
                break;
            case 22:
                timestamp = timp.getPreparare_sfarsit();
                break;
            case 31:
                timestamp = timp.getFiltrare_inceput();
                break;
            case 32:
                timestamp = timp.getFiltrare_sfarsit();
                break;
            case 41:
                timestamp = timp.getUmplere_inceput();
                break;
            case 42:
                timestamp = timp.getUmplere_final();
                break;
            case 50:
                if(timp.isSterilizat()){
                    System.out.println("DA");
                }else{
                    System.out.println("NU");
                }
                break;
            case 51:
                timestamp = timp.getSterilizare_inceput();
                break;
            case 52:
                timestamp = timp.getSterilizare_final();
                break;
            case 61:
                timestamp = timp.getControl_macroscopic_inceput();
                break;
            case 62:
                timestamp = timp.getControl_macroscopic_final();
                break;
            case 71:
                timestamp = timp.getAmbalare_inceput();
                break;
            case 72:
                timestamp = timp.getAmbalare_final();
                break;
        }
        return timestamp;
    }

    public String checkTimp (int etapa) {
        String returned = null;
        String timpRet;
        String noTimp = "Nu exista timp inregistrat";
        String sterilizat = "Produsul nu se sterilizeaza";

        if(etapa == 50 && cautaTimp().isSterilizat() == false){
           returned = "Nu se sterilizeaza";
           if(etapa == 51 || etapa == 52){
               returned = sterilizat;
           }
        }else {
            if (getTimes(etapa) != null) {
                returned = simpleDateFormat.format(getTimes(etapa));
            } else {
                returned = noTimp;
                if(etapa == 50 && cautaTimp().isSterilizat())
                {
                    returned = "Se sterilizeaza";
                }
            }
        }
        return returned;
    }

    public static Timpi cautaTimp () {
        Timpi timpRet = new Timpi();
        ArrayList<Timpi> timpi = DBconnector.getInstance().querryTimpi();
        MainController controller = new MainController();
        int idSerie = controller.getIDSerie();
        for(Timpi timp:timpi) {
            if (idSerie == timp.getIdSerie()) {
                timpRet = timp;
            }
            else{
                System.out.println("Timpul cautat nu exista");
            }
        }
        return timpRet;
    }

//    public void checkNull () {
//        String zero = null;
//        if( simpleDateFormat.format(DBconnector.getInstance().querryTimpi().get(1).getFiltrare_inceput()) != null && simpleDateFormat.format(DBconnector.getInstance().querryTimpi().get(1).getFiltrare_inceput()).isEmpty()){
//            System.out.println("Succes");
//        }
//        else{
//            System.out.println("FAIL!");
//        }
//    }

    public void vizAddTimpiDialog (javafx.event.ActionEvent event) throws IOException {

        Parent root;

        root = FXMLLoader.load(getClass().getClassLoader().getResource("ProductMgmtGui/View/InsertTimeDialog.fxml"));
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

    public void updateLabels () {
        div_inceput.setText(checkTimp(11));
        div_sfarsit.setText(checkTimp(12));
        prepInceput.setText(checkTimp(21));
        prepSfarsit.setText(checkTimp(22));
        filtrInceput.setText(checkTimp(31));
        filtrSfarsit.setText(checkTimp(32));
        umplInceput.setText(checkTimp(41));
        umplSfarsit.setText(checkTimp(42));
        sterilizat.setText(checkTimp(50));
        sterInceput.setText(checkTimp(51));
        sterSfarsit.setText(checkTimp(52));
        contrMcInceput.setText(checkTimp(61));
        controlMcSfarsit.setText(checkTimp(62));
        ambInceeput.setText(checkTimp(71));
        ambSfarsit.setText(checkTimp(72));
    }

    public static int getEtapa() {
        return etapa;
    }
}
