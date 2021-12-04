package ui.overdue;

import business.CheckoutRecord;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import ui.checkout.CheckoutRecordData;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OverdueController extends Stage implements Initializable {
    DataAccess da = DataAccessProvider.getInstance();
    @FXML
    public Button searchButton;
    @FXML
    public TextField isbnET;
    @FXML
    public TableView tableView;

    ObservableList<OverdueRecordData> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn copyNumCol = new TableColumn("CopyNum");
        copyNumCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));
        copyNumCol.setPrefWidth(100);
        TableColumn titleCol = new TableColumn("BookTitle");
        titleCol.setCellValueFactory(new PropertyValueFactory("bookTitle"));
        titleCol.setPrefWidth(100);
        TableColumn dueDateCol = new TableColumn("DueDate");
        dueDateCol.setPrefWidth(100);
        dueDateCol.setCellValueFactory(new PropertyValueFactory("dueDate"));

        TableColumn memberIdCol = new TableColumn("MemberID");
        memberIdCol.setPrefWidth(100);
        memberIdCol.setCellValueFactory(new PropertyValueFactory("memberId"));

        tableView.getColumns().addAll(isbnCol, titleCol, copyNumCol, memberIdCol, dueDateCol);

        searchButton.setOnAction(event -> {
            data.clear();
            da.readMemberMap().forEach((s, libraryMember) -> {
                List<CheckoutRecord> overdueRecords = libraryMember.getOverdueRecords(isbnET.getText());
                if(!overdueRecords.isEmpty()){
                    for (CheckoutRecord rec: overdueRecords){
                        data.add(new OverdueRecordData(
                                rec.getBookCopy().getBook().getIsbn(),
                                rec.getBookCopy().getBook().getTitle(),
                                rec.getBookCopy().getCopyNum(),
                                rec.getLibraryMember().getMemberId(),
                                date2String(rec.getDueDate())
                        ));
                    }
                }
            });
            tableView.setItems(data);
        });
    }

    private String date2String(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
