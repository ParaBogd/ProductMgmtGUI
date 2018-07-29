package ProductMgmtGUI.Controller;

import ProductMgmtGUI.data.DBconnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProductInputDialogueController {

    @FXML
    private TextField prodInputField;

    public void modifProd () {
        MainController controller = new MainController();

        String nume = prodInputField.getText().trim();
        if(nume.isEmpty()){
            controller.showAlert("Completati campul", "Numele produsului nu poate fi gol");
        }
        else {

            try {
                DBconnector.getInstance().modifProdus(nume, controller.getIDProdus());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void processResult () {
        MainController controller = new MainController();

        String nume = prodInputField.getText().trim();// trim folosit pentru a scapa de spatiile albe din fata si spatele textului
        if (nume.isEmpty()) {
            controller.showAlert("Completati campul", "Numele produsului nu poate fi gol");
        } else {
            try {
                DBconnector.getInstance().insertProdus(nume);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
