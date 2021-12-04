package ui;

import business.Author;
import business.Book;
import business.BorrowPeriod;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.DataAccessProvider;
import dataaccess.TestData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewBookController implements Initializable {
    static DataAccess db = DataAccessProvider.getInstance();
    @FXML
    private TextField UI_BookTitle;
    @FXML
    private TextField UI_ISBN;
    @FXML
    private ComboBox UI_MaxCheckoutLength;
    @FXML
    private Button UI_AddBookButton;
    @FXML
    private ListView UI_Authors;
    @FXML
    private Label UI_Errors;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMaxCheckoutLength();
        initializeAuthors();
    }

    private void initializeMaxCheckoutLength() {
        String[] maxCheckoutLengths = new String[]{"1 Week", "2 Weeks"};

        UI_MaxCheckoutLength.getItems().clear();
        UI_MaxCheckoutLength.getItems().addAll(BorrowPeriod.DAYS_7, BorrowPeriod.DAYS_21);
    }

    private void initializeAuthors() {
        UI_Authors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        UI_Authors.getItems().clear();
        UI_Authors.getItems().addAll(DataAccessProvider.getInstance().readAuthorMap().values());
    }

//    private static boolean checkIfBookIsAlreadyExist(String ISBN) {
//        db.readBooksMap().get(ISBN) ?
//        if (db.readBooksMap().get(ISBN)) {
//            return true
//        }
//
//        for(Book book : allBooks) {
//            if(book.getIsbn().equals(ISBN)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String addBook(String title, String ISBN, int maxCheckoutLength, ArrayList<Author> authors) {
        String errorMessage = null;
        HashMap<String, Book> newDB = db.readBooksMap();

        if(db.readBooksMap().get(ISBN) == null) {
            newDB.put(ISBN, new Book(ISBN, title, maxCheckoutLength, authors));
            db.saveBooksMap(newDB);
        } else {
            errorMessage = "Book already exists!";
        }
        return errorMessage;
    }

    public void addNewBook() {
        if(validateForm()) {
            ObservableList selectedAuthorsObservableList = UI_Authors.getSelectionModel().getSelectedItems();
            Object[] authors = selectedAuthorsObservableList.toArray();
            ArrayList<Author> selectedAuthors = new ArrayList<Author>();
            for(Object author : authors)
                selectedAuthors.add((Author) author);

            String addBookResult = addBook(UI_BookTitle.getText(),
                    UI_ISBN.getText(),
                    Integer.parseInt(UI_MaxCheckoutLength.getValue().toString()),
                    selectedAuthors);

            if(addBookResult != null) {
                UI_Errors.setText("");
                errorAlert.setTitle("Error Adding Book!");
                errorAlert.setContentText(addBookResult);
                errorAlert.show();
            } else {
                UI_Errors.setText("");
                successAlert.setAlertType(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Success!");
                successAlert.setContentText("Book has been added successfully!");
                successAlert.show();
            }
        }
    }

    public boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        boolean isValid = true;

        if(UI_BookTitle.getText().isEmpty()) {
            isValid = false;
            errors.append("Book Name is required\n");
        }
        if(UI_ISBN.getText().isEmpty()) {
            isValid = false;
            errors.append("ISBN is required\n");
        } else if(!UI_ISBN.getText().matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")) {
            isValid = false;
            errors.append("Invalid ISBN!\n");
        }
        if(UI_MaxCheckoutLength.getSelectionModel().isEmpty()) {
            isValid = false;
            errors.append("Max Checkout Duration is required\n");
        }
        if(UI_Authors.getSelectionModel().isEmpty()) {
            isValid = false;
            errors.append("You must select at least one author\n");
        }

        if(!isValid) {
            UI_Errors.setText(errors.toString());
        }

        return isValid;
    }

}
