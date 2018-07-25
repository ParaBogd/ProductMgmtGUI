package ProductMgmtGUI.Controller;

import ProductMgmtGUI.MainController;
import ProductMgmtGUI.data.DBconnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class InsertSerieController {
    @FXML
    private TextField inputSerie;

    public void processResult (){
        String numarSerie = inputSerie.getText().trim();
        MainController controller = new MainController();
//        if(controller.getIDProdus() == 0 ){
//            System.out.println("Nu a fost selectat un produs");
//            return;
//        }else{
        try{
            DBconnector.getInstance().insertSerie(numarSerie, controller.getIDProdus());
        }catch (SQLException e){
            System.out.println("Couldn't add serie trhough dialog");
            e.printStackTrace();
        }
        }
    }
//}
