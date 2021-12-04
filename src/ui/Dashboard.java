package ui;

import business.SystemController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    AnchorPane bookCopyTabAnchor;

    @FXML
    Tab memberTab, bookTab, checkoutTab,bookCopyTab;

    private AddBookCopyController bookCopyController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showHideTab();
        lblGreet.setText("Welcome "+SystemController.userId);
        signOut.setOnAction(this::handle);
        loadBookCopy();
        bookTab.setOnSelectionChanged (e ->
                loadBookCopy()
        );
    }

    private void loadBookCopy() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/AddBookCopyView.fxml"));
        try {
            Parent root = loader.load();
            bookCopyController = loader.getController();
            bookCopyTab.setContent(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
