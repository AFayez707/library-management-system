package ui.checkout;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutRecordData {

    private SimpleStringProperty copyNum, date, dueDate;

    public CheckoutRecordData(int copyNum, Date date, Date dueDate) {
        this.copyNum = new SimpleStringProperty(copyNum + "");
        this.date = new SimpleStringProperty(date2String(date));
        this.dueDate = new SimpleStringProperty(date2String(dueDate));
    }

    private String date2String(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public String getCopyNum() {
        return copyNum.get();
    }

    public SimpleStringProperty copyNumProperty() {
        return copyNum;
    }

    public void setCopyNum(String copyNum) {
        this.copyNum.set(copyNum);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public SimpleStringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }
}