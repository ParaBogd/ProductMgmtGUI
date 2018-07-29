package ProductMgmtGUI.Controller;

import ProductMgmtGUI.data.DBconnector;
import ProductMgmtGUI.data.Produs;
import ProductMgmtGUI.data.Serii;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;



public class MainController {

    private double dragDeltaX;
    private double dragDeltaY;

    private static int idProdus;//variabila care stocheaza id-ul pentru un produs
    private static int idSerie;//variabila care stocheaza id-ul pentru o serie
    private static String numarSerie; // variabila inmagazineaza numarul serie selctate

    @FXML
    private Button closeMain;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menTimpi;

    @FXML
    private MenuItem exit;

    @FXML
    private Menu menSerii;

    @FXML
    private ListView<Serii> listSerii;

    @FXML
    private ListView<Produs> listProduse;

    @FXML
    private MenuItem addProdus;

    @FXML
    private MenuItem modProdus;

    @FXML
    private MenuItem delProdus;

    @FXML
    private MenuItem delSerie;

    @FXML
    private MenuItem addSerie;

    @FXML
    private MenuItem vizSerii;

    @FXML
    private MenuItem vizTimpi;

    @FXML
    private MenuItem addTimp;

    @FXML
    private MenuItem about;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private MenuItem instruct;

    @FXML
    void initialize() {

        listProduse.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produs>() {
            @Override
            public void changed(ObservableValue<? extends Produs> observable, Produs oldValue, Produs newValue) {
                modProdus.setDisable(false);
                vizSerii.setDisable(false);
                addSerie.setDisable(false);
                delProdus.setDisable(false);
                listSerii.setItems(null);
                if(listProduse.getSelectionModel().isEmpty()){
                    idProdus = 0;
                }else {
                    idProdus = listProduse.getSelectionModel().selectedItemProperty().getValue().getId();
                    System.out.println(getIDProdus());

                }
            }
        });

        listSerii.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Serii>() {

            @Override
            public void changed(ObservableValue<? extends Serii> observable, Serii oldValue, Serii newValue) {
                delSerie.setDisable(false);
                vizTimpi.setDisable(false);
                if(listSerii.getSelectionModel().isEmpty()) {
                    idProdus = 0;
                }else {
                    numarSerie = listSerii.getSelectionModel().selectedItemProperty().getValue().getNumarSerie();
                    idSerie = listSerii.getSelectionModel().selectedItemProperty().getValue().getIdSerii();
                    System.out.println("Id serie:" + idSerie);
                    System.out.println(numarSerie);
                }
            }
        });

        listProduse.setItems(DBconnector.getInstance().queryProduseObservList());
        listProduse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listSerii.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listProduse.setCellFactory(new Callback<ListView<Produs>, ListCell<Produs>>() {
            @Override
            public ListCell<Produs> call(ListView<Produs> param) {
                ListCell<Produs> cell = new ListCell<Produs>() {
                    @Override
                    protected void updateItem(Produs item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });

        listSerii.setCellFactory(new Callback<ListView<Serii>, ListCell<Serii>>() {
            @Override
            public ListCell<Serii> call(ListView<Serii> param) {
                ListCell<Serii> cell = new ListCell<Serii>() {
                    @Override
                    protected void updateItem(Serii item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getNumarSerie());
                        }
                    }
                };
                return cell;
            }
        } );

        modProdus.setOnAction(event -> {
            modifProdus();
        });


        delProdus.setOnAction(event -> {
            if(showAlert("Doriti sa stergeti produsul", "Stergerea produsului va duce la stergerea" +
                    "tuturor seriilor asociate si a a timpilor asociati acestora")){
                try {
                    DBconnector.getInstance().deleteProdus(idProdus);
                    listProduse.setItems(DBconnector.getInstance().queryProduseObservList());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("S-a sters");
            }else{
                System.out.println("Nu s-a sters");
            }
        });

        delSerie.setOnAction(event -> {
            if(showAlert("Doriti sa stergeti seria?", "Stergerea seriei va duce la stergerea " +
                    "tuturor timpilor asociati!")){
                try {
                    DBconnector.getInstance().deleteSerie(idSerie);
                    vizSeriiPress();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("S-a sters");
            }else{
                System.out.println("Nu s-a sters");
            }
        });

        vizSerii.setOnAction(event -> {
            vizSeriiPress();
        });

        about.setOnAction(event -> {
            aboutdialog();
        });

        exit.setOnAction(event -> {
            Platform.exit();
        });

        closeMain.setOnAction(event -> {
            Platform.exit();
        });

        vizTimpi.setOnAction(event -> {
            try{
                vizTimpipress(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



   menuBar.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
       @Override
       public void handle(MouseEvent event) {
            dragDeltaY = getStage().getY() - event.getScreenY();
            dragDeltaX = getStage().getX() - event.getScreenX();
       }
   });


   menuBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
       @Override
       public void handle(MouseEvent event) {
             getStage().setX(event.getScreenX() + dragDeltaX);
             getStage().setY(event.getScreenY() + dragDeltaY );
       }
   });

    }

    public Stage getStage () {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        return stage;
    }


//Alerta
    @FXML
    public boolean showAlert (String headerTxt, String contentTxt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alerta Stergere");
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }else{
            return false;
        }
    }


//Metoda care creeza casuta de dialog si apeleaza ProductInputDialogue.fxml
    @FXML
    public void showProdusNou() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Adauga un Produs nou");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ProductMgmtGUI/View/ProductInputDialogue.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ProductInputDialogueController controller = fxmlLoader.getController();
            controller.processResult();
            DBconnector.getInstance().open();
            listProduse.setItems(DBconnector.getInstance().queryProduseObservList()); //pentru a face vizibila noua aditie trebuie reinitializata lista din listview

        }
    }

    @FXML
    public void modifProdus () {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Modifica  denumirea produsului selectat");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ProductMgmtGUI/View/ProductInputDialogue.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ProductInputDialogueController controller = fxmlLoader.getController();
            controller.modifProd();
//            DBconnector.getInstance().open();
            listProduse.setItems(DBconnector.getInstance().queryProduseObservList());

        }
    }

    @FXML
    public void showSerieNoua () {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Adauga o serie noua");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("View/InsertSerieDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            InsertSerieController controller = fxmlLoader.getController();
            controller.processResult();
            vizSeriiPress();
        }
    }

    public void vizSeriiPress () {
        int id = getIDProdus();

        ObservableList<Serii> serii = DBconnector.getInstance().querrySerii();
        ObservableList<Serii> seriiAfisat = FXCollections.observableArrayList();

        for(Serii serie:serii){
            if(id == serie.getIdProdus()){
                seriiAfisat.add(serie);
            }
        }
        listSerii.setItems(seriiAfisat);
    }

    public void vizTimpipress (javafx.event.ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/ProductMgmtGUI/View/Timpi.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Vizualizare timpi");
        stage.setScene(new Scene(root, 600, 600));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainBorderPane.getScene().getWindow());
        stage.show();
    }

//    public void vizAddTimpi (javafx.event.ActionEvent event) throws IOException {
//        Parent root;
//        root = FXMLLoader.load(getClass().getResource("View/InsertTimes.fxml"));
//        Stage stage = new Stage();
//        stage.setTitle("Adauga timpi");
//        stage.setScene(new Scene(root, 800, 600));
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initOwner(mainBorderPane.getScene().getWindow());
//        stage.show();
//    }

    public void aboutdialog () {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Despre Program");
        alert.setHeaderText(null);
        alert.setContentText("Acest program este un manager simplu de productie pentru industria farmaceutica. " +
                "Programat de Parau Bogdan pentru cursul de Introducere in programare in cadrul FastTrackIT pentru " +
                "a servi drept proiect final. 07/2018");
        alert.showAndWait();
    }

    public void instructiuniDialog () {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructiuni");
        alert.setHeaderText(null);
        alert.setContentText("Instructiuni de utilizare\n" +
                "Produsele sunt afisate in casuta din stanga, slectati produsul si vedeti seriile in lista din dreapta" +
                " prin butoanele din meniu. Selectati seria si accesati meniul timpi pentru a vizualiza timpii asociati cu " +
                "seria selectata. Din meniul de vizualizare a timpiilor se pot adauga timpii, iar printr-un meniu contextual " +
                "accesat cu click dreapta pe timp se poate modifica sau sterge timpul inregistrat.");
        alert.showAndWait();
    }


    public int getIDProdus () {
        return idProdus;
    }

    public int getIDSerie () {
        return idSerie;
    }

    public static String getNumarSerie() {
        return numarSerie;
    }
}


