package ui;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddBookCopyController implements Initializable {
    static DataAccess db = DataAccessProvider.getInstance();
    @FXML
    private TextField UI_NumOfCopies;
    @FXML
    private ListView UI_Books;
    @FXML
    private Label UI_Errors;
    @FXML
    private Button UI_AddBookCopyButton;



    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBooks();
    }

    public  void initializeBooks() {
        UI_Books.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        UI_Books.getItems().clear();
        UI_Books.getItems().addAll(db.readBooksMap().values());
    }

    public void addNewBookCopies() {
        if(validateForm()) {
            UI_Errors.setText("");
            HashMap<String, Book> newDB = db.readBooksMap();
            Book book = (Book)UI_Books.getSelectionModel().getSelectedItem();
            book.addCopies(Integer.parseInt(UI_NumOfCopies.getText()));
            newDB.put(book.getIsbn(), book);
            db.saveBooksMap(newDB);

            successAlert.setTitle("Success!");
            successAlert.setContentText("Success! Current book copies are: " + book.getNumCopies());
            successAlert.show();
        }
    }

    public boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        boolean isValid = true;

        if(UI_Books.getSelectionModel().isEmpty()) {
            isValid = false;
            errors.append("You have to choose a book\n");
        }
        if(UI_NumOfCopies.getText().isEmpty()) {
            isValid = false;
            errors.append("You have to specify number of copies\n");
        } else if(!UI_NumOfCopies.getText().matches("^[1-9]\\d*$")) {
            isValid = false;
            errors.append("Number of copies must be a positive integer number!\n");
        }
        if(!isValid) {
            UI_Errors.setText(errors.toString());
        }

        return isValid;
    }
}
