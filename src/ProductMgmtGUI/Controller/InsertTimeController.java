package ProductMgmtGUI.Controller;

import ProductMgmtGUI.data.DBconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class InsertTimeController {

    @FXML
    private Label etapaLabel;

    @FXML
    private DatePicker dateIn;

    @FXML
    private ComboBox<Integer> oraIn;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Integer> secIn;

    @FXML
    private ComboBox<Integer> minIn;

    @FXML
    private Label seriaLabel;

    @FXML
    private Button okButton;



    private ObservableList<Integer> ora = FXCollections.observableArrayList();
    private ObservableList<Integer> min = FXCollections.observableArrayList();
    private ObservableList<Integer> sec = FXCollections.observableArrayList();


    @FXML
    void initialize () {
        for(int i=0; i<=24;i++){
            ora.add(i);
        }

        for(int i=0; i<=60; i++){
            min.add(i);
            sec.add(i);
        }

        oraIn.setItems(ora);
        minIn.setItems(min);
        secIn.setItems(sec);

        TimpiController controllerTimpi = new TimpiController();
        int etapa = controllerTimpi.getEtapa();
        etapaLabel.setText(getEtapaLabel(etapa));
//        System.out.println(etapa);

        MainController controller = new MainController();
        seriaLabel.setText(controller.getNumarSerie());

        cancelButton.setOnAction(event -> {
            cancelButtonPress();
        });

        okButton.setOnAction(event -> {
            procesResult();
            cancelButtonPress();

        });


    }

    void procesResult () {

        if(dateIn.getValue()==null || oraIn.getValue() == null || minIn.getValue() == null || secIn.getValue() == null){
            MainController controller1 = new MainController();
            controller1.showAlert("Campuri goale","Unele dintre campurile din casuta de dialog sunt goale, " +
                    "va rugam completati-le integral");
        }else {
        String date = dateIn.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ora = Integer.toString(oraIn.getValue());
        String min = Integer.toString(minIn.getValue());
        String sec = Integer.toString(secIn.getValue());

        TimpiController controller = new TimpiController();
        int etapa = controller.getEtapa();


            try {
                DBconnector.getInstance().executeQuerryTimpi(DBconnector.getInstance().stringInsertTimp(etapa, date, ora, min, sec));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private String getEtapaLabel (int etapa) {
        String etapaStr = null;
        switch (etapa) {
            case 11:
                etapaStr = "Divizare inceput";
                break;
            case 12:
                etapaStr = "Divizare sfarsit";
                break;
            case 21:
                etapaStr = "Preparare inceput";
                break;
            case 22:
                etapaStr = "Preparare sfarsit";
                break;
            case 31:
                etapaStr = "Filtrare inceput";
                break;
            case 32:
                etapaStr = "Filtrare sfarsit";
                break;
            case 41:
                etapaStr ="Umplere inceput";
                break;
            case 42:
                etapaStr = "Umplere sfarsit";
                break;
            case 51:
                etapaStr= "Sterilizare inceput";
                break;
            case 52:
                etapaStr = "Sterilizare sfarsit";
                break;
            case 61:
                etapaStr = "Control macroscopic inceput";
                break;
            case 62:
                etapaStr = "Control macroscopic sfarsit";
                break;
            case 71:
                etapaStr="Ambalare inceput";
                break;
            case 72:
                etapaStr="Ambalare sfarsit";
                break;
        }
        return etapaStr;
    }

    @FXML
    public void cancelButtonPress () {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
