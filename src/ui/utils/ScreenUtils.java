package ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            stage.show();
//            setStageIcon(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static void closeAllAndReturnToPrimeScreen(Control control){
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
        Start.hideAllWindows();
        Start.primStage().show();
    }
}
