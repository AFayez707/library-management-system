package ui;

import business.SystemController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.utils.ScreenUtils;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class Dashboard extends Stage implements Initializable, EventHandler<Event> {

    public static final Dashboard INSTANCE = new Dashboard();
    @FXML
    AnchorPane mainAnchor;

    @FXML
    TabPane tabPane;

    @FXML
    Label lblGreet;

    @FXML
    Button signOut;

    @FXML
    Tab memberTab, bookTab, checkoutTab,bookCopyTab;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showHideTab();
        lblGreet.setText("Welcome "+SystemController.userId);
        signOut.setOnAction(this::handle);

    }



    public void showHideTab() throws NoSuchElementException {
        if(SystemController.currentAuth==null){
            throw new NoSuchElementException("No active sessions");
        }
        switch (SystemController.currentAuth){
            case ADMIN:
                tabPane.getTabs().remove(checkoutTab);
                break;
            case LIBRARIAN:{
                tabPane.getTabs().remove(memberTab);
                tabPane.getTabs().remove(bookTab);
                tabPane.getTabs().remove(bookCopyTab);
            }
        }

    }
    @Override
    public void handle(Event event) {
      String ctrl =  ((Control) event.getSource()).getId();
      if(ctrl.equals(signOut.getId())){
          ScreenUtils.closeAllAndReturnToPrimeScreen(signOut);
      }

    }
}
