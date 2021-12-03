package ui;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewMemberEntry extends Stage implements Initializable, EventHandler<Event> {
    public static final NewMemberEntry INSTANCE = new NewMemberEntry();

    DataAccess da;
    @FXML
    TextField txtFirstName, txtLastName, txtStreet, txtCity, txtZip, txtState,txtPhone;

    @FXML
    Label lblError;

    @FXML
    Button btnSave, btnCancel;

    MemberController memberController;

    public NewMemberEntry() {
        da = DataAccessProvider.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initBehavior();
        btnSave.setOnAction(this::handle);
        btnCancel.setOnAction(this::handle);

    }

    @Override
    public void handle(Event event) {
        String ctrl = ((Control) event.getSource()).getId();
        if (ctrl.equals(btnSave.getId())) {
           saveMember();
           memberController.refreshTable();
        }else if(ctrl.equals(btnCancel.getId())){
            closeWindow();
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void saveMember() {
        String newMemberIdt = getNewMemberId();
        if(validateForm()){
            String newMemberId = getNewMemberId();
            LibraryMember libraryMember = new LibraryMember(newMemberId,
                    this.txtFirstName.getText(),
                    this.txtLastName.getText().toString(),
                    this.txtPhone.getText().toString(),
                    new Address(this.txtStreet.getText(),txtCity.getText().toString(),txtState.getText().toString(),txtZip.getText().toString())
                    );
            da.saveNewMember(libraryMember);
            memberController.refreshTable();
            closeWindow();
        }

    }

    private String getNewMemberId() {
        HashMap<String, LibraryMember> stringLibraryMemberHashMap = da.readMemberMap();
        return String.valueOf(Integer.parseInt(Collections.max(stringLibraryMemberHashMap.keySet()))+1);
    }

    public boolean validateForm() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isValid = true;
        if (txtFirstName.getText().isEmpty()) {
            stringBuilder.append("FirstName required\n");
            isValid = false;
        } else if (txtLastName.getText().isEmpty()) {
            stringBuilder.append("LastName required\n");
            isValid = false;
        } else if (txtStreet.getText().isEmpty()) {
            stringBuilder.append("Street required\n");
            isValid = false;
        } else if (txtCity.getText().isEmpty()) {
            stringBuilder.append("City required\n");
            isValid = false;
        } else if (txtZip.getText().isEmpty() || txtZip.getText().matches(".*[a-z].*")) {
            stringBuilder.append("Zip should be valid\n");
            isValid = false;
        } else if (txtState.getText().isEmpty()) {
            stringBuilder.append("Zip required\n");
            isValid = false;
        }
        else if (txtPhone.getText().isEmpty()) {
            stringBuilder.append("Phone num is required\n");
            isValid = false;
        }

        lblError.setText(stringBuilder.toString());

        return isValid;
    }

    void initBehavior(){
        txtZip.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtZip.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        txtPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtZip.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }


    public void setMemberController(MemberController memberController) {
        this.memberController = memberController;
    }
}
