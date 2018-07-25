package ProductMgmtGUI;

import ProductMgmtGUI.data.DBconnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProductInputDialogueController {

    @FXML
    private TextField prodInputField;

    public void modifProd () {
        String nume = prodInputField.getText().trim();
        MainController controller = new MainController();
        try {
            DBconnector.getInstance().modifProdus(nume,controller.getIDProdus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processResult () {
        String nume = prodInputField.getText().trim();// trim folosit pentru a scapa de spatiile albe din fata si spatele textului

        DBconnector connect = DBconnector.getInstance();
        connect.open();

        try {
            connect.insertProdus(nume);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
