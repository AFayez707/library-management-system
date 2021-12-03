package ui.uimodels;

import business.Address;
import javafx.beans.property.SimpleStringProperty;

public class MemberData {
    private SimpleStringProperty memberId;
    private SimpleStringProperty  fullName;
    private SimpleStringProperty  telephone;
    private SimpleStringProperty  address;

    public MemberData(String memberId, String firstName, String lastName, String telephone, String address) {
        this.memberId = new SimpleStringProperty(memberId);
        this.fullName = new SimpleStringProperty(firstName + " "+lastName);
        this.telephone = new SimpleStringProperty(telephone);;
        this.address = new SimpleStringProperty(address);;
    }

    public String getMemberId() {
        return memberId.get();
    }

    public SimpleStringProperty memberIdProperty() {
        return memberId;
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }
}
