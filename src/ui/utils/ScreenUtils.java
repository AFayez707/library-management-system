package ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Start;

import java.io.IOException;
import java.net.URL;

public class ScreenUtils {
    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return controller;
    }

    public static void closeAllAndReturnToPrimeScreen(Control control){
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
        Start.hideAllWindows();
        Start.primStage().show();
    }

    public static void showAlert(String s, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        //Setting the title
        alert.setTitle("Alert");
//        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.setContentText(s);
//        alert.getDialogPane().getButtonTypes().add(type);
        alert.showAndWait();
    }
}
