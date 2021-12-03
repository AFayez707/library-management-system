package ui.checkout;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.uimodels.MemberData;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        TableColumn copyNumCol = new TableColumn("CopyNumber");
        copyNumCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));
        copyNumCol.setPrefWidth(100);
        TableColumn dateCol = new TableColumn("CheckoutDate");
        dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        dateCol.setPrefWidth(100);
        TableColumn dueDateCol = new TableColumn("DueDate");
        dueDateCol.setPrefWidth(100);
        dueDateCol.setCellValueFactory(new PropertyValueFactory("dueDate"));

        tableView.getColumns().addAll(copyNumCol, dateCol, dueDateCol);



        checkAvailabilityButton.setOnAction(event -> {
            checkAvailabilityClick();
        });

        newCheckoutButton.setOnAction(event -> {
            onNewCheckoutButton();
        });

        printButton.setOnAction(event -> {
            onPrintButton();
        });
    }

    ObservableList<CheckoutRecordData> data = FXCollections.observableArrayList();

    private void checkAvailabilityClick() {
        HashMap<String, LibraryMember> members = da.readMemberMap();
        HashMap<String, Book> books = da.readBooksMap();

        member = members.get(memberIdET.getText());
        book = books.get(isbnET.getText());
        tableView.setItems(data);
        if(member == null){
            resultTV.setText("member not found");
        }else if(book == null){
            resultTV.setText("book not found");
        } else if(!book.isAvailable()){
            resultTV.setText("book is not available");
        } else{

            // TODO remove this block
//            ArrayList<CheckoutRecord> list = new ArrayList<>();
//            CheckoutRecord c = new CheckoutRecord(member, book.getCopies()[0], new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
//            list.add(c);
//            member.setCheckoutRecords(list);

            showData();
            resultTV.setText("A copy is available");
        }

    }

    private void showData() {
        data.clear();
        for (CheckoutRecord rec: member.getCheckoutRecords()){
                data.add(new CheckoutRecordData(
                        rec.getBookCopy().getCopyNum(),
                        rec.getCheckoutDate(),
                        rec.getDueDate()
                ));
        }
        tableView.setItems(data);
    }

    private LibraryMember member = null;
    private Book book = null;

    private void onNewCheckoutButton() {
        if(member != null && book != null){
            member.checkout(book);
            resultTV.setText("");
            showData();
            if(book.getNextAvailableCopy() == null)
                book = null;
        }
    }

    private void onPrintButton() {
        if(member != null){
            for (CheckoutRecord record: member.getCheckoutRecords()) {
                System.out.printf("%-10s %-10s %-10s\n", record.getBookCopy().getCopyNum(), date2String(record.getCheckoutDate()), date2String(record.getDueDate()));
            }
        }
    }

    private String date2String(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }


}
