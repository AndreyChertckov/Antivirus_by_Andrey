package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sun.font.TextLabel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    Button buttonOK,buttonCancel;
    @FXML
    TextField UserIn;
    @FXML
    TextField textFieldPath;
    @FXML
    Label Progress;

    public void clickOK(ActionEvent actionEvent) {
        Main.USER = UserIn.getText();
        System.out.println(Main.USER);
        FileWorker.write(new File(Main.PATH+"USERNAME.txt"), Main.USER);
        Main.window();

    }

    public void clickCancel(ActionEvent actionEvent) {
        System.exit(42);
    }

    public void clickSearch(ActionEvent actionEvent) {
        Progress.setText("Поиск начат");
        Main.scannerFile.scanFile(new File(textFieldPath.getText()),Progress);
    }


}
