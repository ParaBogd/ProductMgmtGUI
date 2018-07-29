package ProductMgmtGUI;

import ProductMgmtGUI.data.DBconnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBconnector.getInstance().open();
        Parent root = FXMLLoader.load(getClass().getResource("View/MgmtWindow.fxml"));
        primaryStage.setTitle("Management Produs 3000");
        primaryStage.initStyle(StageStyle.UNDECORATED); //se face bara de windows invizibila
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();


    }




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        DBconnector.getInstance().open();   //la pornire se deschide conexiunea cu baza de date
    }

    @Override
    public void stop() throws Exception {
        DBconnector.getInstance().close(); //la oprire se inchide conexiunea cu baza de date
    }


}


