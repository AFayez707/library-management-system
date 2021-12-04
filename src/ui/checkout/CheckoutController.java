package ui.checkout;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CheckoutController extends Stage implements Initializable {

    @FXML
    public Button checkAvailabilityButton, newCheckoutButton, printButton;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField memberIdET, isbnET;
    @FXML
    public TableView tableView;
    @FXML
    public Label resultTV;

    DataAccess da;

    public CheckoutController(){
        da = DataAccessProvider.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn copyNumCol = new TableColumn("CopyNum");
        copyNumCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));
        copyNumCol.setPrefWidth(100);
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        dateCol.setPrefWidth(100);
        TableColumn dueDateCol = new TableColumn("DueDate");
        dueDateCol.setPrefWidth(100);
        dueDateCol.setCellValueFactory(new PropertyValueFactory("dueDate"));

        tableView.getColumns().addAll(isbnCol, copyNumCol, dateCol, dueDateCol);



        checkAvailabilityButton.setOnAction(event -> {
            checkAvailabilityClick();
        });

        newCheckoutButton.setOnAction(event -> {
            onNewCheckoutClick();
        });

        printButton.setOnAction(event -> {
            onPrintButton();
        });

        newCheckoutButton.setDisable(true);
        printButton.setDisable(true);
    }

    ObservableList<CheckoutRecordData> data = FXCollections.observableArrayList();

    private void checkAvailabilityClick() {
        HashMap<String, LibraryMember> members = da.readMemberMap();
        HashMap<String, Book> books = da.readBooksMap();

        member = members.get(memberIdET.getText());
        book = books.get(isbnET.getText());
        data.clear();
        printButton.setDisable(false);
        if(member == null){
            resultTV.setText("member not found");
            printButton.setDisable(true);
        }else if(book == null){
            showData();
            resultTV.setText("book not found");
            newCheckoutButton.setDisable(true);
        } else if(!book.isAvailable()){
            showData();
            resultTV.setText("book is not available");
            book = null;
            newCheckoutButton.setDisable(true);
        } else{
            showData();
            resultTV.setText(book.getIsbn() + " copy is available");
            newCheckoutButton.setDisable(false);
            printButton.setDisable(false);
        }

    }

    private void showData() {
        data.clear();
        for (CheckoutRecord rec: member.getCheckoutRecords()){
                data.add(new CheckoutRecordData(
                        rec.getBookCopy().getBook().getIsbn(),
                        rec.getBookCopy().getCopyNum(),
                        rec.getCheckoutDate(),
                        rec.getDueDate()
                ));
        }
        tableView.setItems(data);
    }

    private LibraryMember member = null;
    private Book book = null;

    private void onNewCheckoutClick() {
        if(member != null && book != null){
            member.checkout(book);
            da.updateMember(member);
            da.updateBook(book);
            resultTV.setText("");
            showData();
            if(book.getNextAvailableCopy() == null)
                book = null;
        }
    }

    private void onPrintButton() {
        HashMap<String, LibraryMember> members = da.readMemberMap();
        LibraryMember member = members.get(memberIdET.getText());
        if(member != null){
            if(!member.getCheckoutRecords().isEmpty()) {
                System.out.printf("%-10s %-10s %-10s %-10s\n", "ISBN","CopyNum", "Date", "DueDate");
                for (CheckoutRecord record : member.getCheckoutRecords()) {
                    System.out.printf("%-10s %-10s %-10s %-10s\n", record.getBookCopy().getBook().getIsbn(), record.getBookCopy().getCopyNum(), date2String(record.getCheckoutDate()), date2String(record.getDueDate()));
                }
            } else{
                System.out.println("No records for current member id");
            }
        }
    }

    private String date2String(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }


}
