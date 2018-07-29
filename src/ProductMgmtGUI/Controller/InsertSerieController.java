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
        String numarSerie = inputSerie.getText();

        if(numarSerie.length()<= 8 && numarSerie.length() >1){
            MainController controller = new MainController();
            try{
                DBconnector.getInstance().insertSerie(numarSerie, controller.getIDProdus());
            }catch (SQLException e){
                System.out.println("Couldn't add serie trhough dialog");
                e.printStackTrace();
            }
        }else {
            MainController controller = new MainController();
            controller.showAlert("Format serie incorect", "Formatul standard al seriei trebuie sa fie de maxim 8 caractere");
        }

        }
    }
//}
