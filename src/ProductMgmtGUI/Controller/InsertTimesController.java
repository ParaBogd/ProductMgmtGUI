package ProductMgmtGUI.Controller;

import ProductMgmtGUI.data.DBconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class InsertTimesController {

    @FXML
    private Label labelSeria;

    @FXML
    private Button addDivIn;
    @FXML
    private DatePicker dateDivIn;
    @FXML
    private ComboBox<Integer> OraDivInc;
    @FXML
    private ComboBox<Integer> minDivInc;
    @FXML
    private ComboBox<Integer> secDivInc;

    @FXML
    private DatePicker dateDivSf;
    @FXML
    private ComboBox<Integer> OraDivSf;
    @FXML
    private ComboBox<Integer> minDivSf;
    @FXML
    private ComboBox<Integer> secDivSf;
    @FXML
    private Button addDivSf;


    private ObservableList<Integer> ora = FXCollections.observableArrayList();
    private ObservableList<Integer> min = FXCollections.observableArrayList();
    private ObservableList<Integer> sec = FXCollections.observableArrayList();


    @FXML
    void initialize () {
      ora.addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);

      for(int i = 1; i<=60; i++){
          min.add(i);
          sec.add(i);
      }

      OraDivInc.setItems(ora);
      minDivInc.setItems(min);
      secDivInc.setItems(sec);

      MainController controller = new MainController();
      labelSeria.setText(controller.getNumarSerie());

    addDivIn.setOnAction(event -> {
        getData(1);
    });

    }

    void getData (int etapa) {
        String date = dateDivIn.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ora = Integer.toString(OraDivInc.getValue());
        String min = Integer.toString(minDivInc.getValue());
        String sec = Integer.toString(secDivInc.getValue());

        try {
            DBconnector.getInstance().executeQuerryTimpi(DBconnector.getInstance().stringInsertTimp(etapa,date,ora,min,sec));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateRow (int etapa, String date, String ora, String min, String sec) {
        try {
            DBconnector.getInstance().executeQuerryTimpi(DBconnector.getInstance().stringInsertTimp(etapa,date,ora,min,sec));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
