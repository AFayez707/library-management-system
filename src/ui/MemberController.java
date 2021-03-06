package ui;


import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import dataaccess.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.uimodels.MemberData;
import ui.utils.ScreenUtils;

import java.io.IOException;
import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MemberController extends Stage implements Initializable, EventHandler<Event> {
    public static final MemberController INSTANCE = new MemberController();
    @FXML
    AnchorPane mainPane;

    @FXML
    Button btnAddMember;

    DataAccess da;

    public MemberController(){
        da = DataAccessProvider.getInstance();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        da.readMemberMap();
        btnAddMember.setOnAction(this::handle);
        initTable();

        mainPane.setMaxHeight(mainPane.getHeight());
        mainPane.setMaxWidth(mainPane.getWidth());





    }

    private void initMenuItems(TableView<MemberData> table) {
        MenuItem mi1 = new MenuItem("Delete");
        mi1.setOnAction((ActionEvent event) -> {
            System.out.println("Menu item 1");
            MemberData item =   (MemberData) table.getSelectionModel().getSelectedItem();
            
            deleteMember(item.getMemberId());
            System.out.println(item.getMemberId());
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(mi1);
        table.setContextMenu(menu);
    }

    private void deleteMember(String memberId) {
        LibraryMember member = da.readMemberById(memberId);
        if(!member.hasActiveCheckout()){
            da.deleteMember(memberId);
            refreshTable();
        }else{
          ScreenUtils.showAlert("Member has active checkout association. Cannot delete!", Alert.AlertType.ERROR);
        }
    }


    private void initTable() {
        HashMap<String, LibraryMember> members = da.readMemberMap();
        ObservableList<MemberData> data = FXCollections.observableArrayList();

        members.forEach((s, user) -> {
            String fullAddress = user.getAddress().getCity() + " "+user.getAddress().getState();
            data.add(new MemberData(user.getMemberId(),user.getFirstName(),user.getLastName(),user.getTelephone(),fullAddress));
        });

        Label label = new Label("All Members");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label.setFont(font);
        //Creating a table view
        TableView<MemberData> table = new TableView<MemberData>();
        //Creating columns
        TableColumn fileNameCol = new TableColumn("Member ID");
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        TableColumn pathCol = new TableColumn("Full Name");
        pathCol.setCellValueFactory(new PropertyValueFactory("fullName"));
        TableColumn sizeCol = new TableColumn("Phone");
        sizeCol.setCellValueFactory(new PropertyValueFactory("telephone"));
        TableColumn dateCol = new TableColumn("Address");
        dateCol.setCellValueFactory(new PropertyValueFactory("address"));
        dateCol.setPrefWidth(250);
        //Adding data to the table
        ObservableList<String> list = FXCollections.observableArrayList();
        table.setItems(data);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(fileNameCol, pathCol, sizeCol, dateCol);
        //Setting the size of the table
        table.setMaxSize(700, 300);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 50, 50, 60));
        vbox.getChildren().addAll(label, table);

//        Scene scene = new Scene(vbox, 595, 230);
        mainPane.getChildren().add(vbox);

        initMenuItems(table);

        table.setRowFactory( tv -> {
            TableRow<MemberData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    MemberData rowData = row.getItem();
                    intentNewMemberScreen(true,rowData.getMemberId());
                }
            });
            return row ;
        });
    }

    @Override
    public void handle(Event event) {
        String ctrl =  ((Control) event.getSource()).getId();
        if(ctrl.equals(btnAddMember.getId())){
            // Open Create Member
//            ScreenUtils.loadWindow(getClass().getResource("NewMemberEntry.fxml"), "NewMember", null);
            intentNewMemberScreen(false, null);
        }
    }

    private void intentNewMemberScreen(boolean isUpdateRequest, String memberId) {

        if(isUpdateRequest && (memberId==null ||memberId.isEmpty())){
            throw new IllegalArgumentException("Need memberId in order to update");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewMemberEntry.fxml"));
            Parent root = loader.load();
            //The following both lines are the only addition we need to pass the arguments
            NewMemberEntry newMemberEntry = loader.getController();
            newMemberEntry.setMemberController(this);


            Stage stage =new Stage(StageStyle.DECORATED);
            stage.setTitle("Add Library Member");
            stage.setScene(new Scene(root));
            stage.show();

            if(isUpdateRequest){
                LibraryMember libraryMember =  da.readMemberById(memberId);
                newMemberEntry.updateRequestDetails(libraryMember);
//                newMemberEntry.update(String )
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable(){
        this.initTable();
    }




}
