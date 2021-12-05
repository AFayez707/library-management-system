package ui.overdue;

import javafx.beans.property.SimpleStringProperty;

public class OverdueRecordUiData {
    private final SimpleStringProperty isbn, bookTitle, copyNum, memberId, dueDate;

    public OverdueRecordUiData(String isbn, String bookTitle, int copyNum, String memberId, String dueDate) {

        this.isbn = new SimpleStringProperty(isbn);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.copyNum = new SimpleStringProperty(copyNum+"");
        this.memberId = new SimpleStringProperty(memberId+"");
        this.dueDate = new SimpleStringProperty(dueDate);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public SimpleStringProperty bookTitleProperty() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
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

    public String getMemberId() {
        return memberId.get();
    }

    public SimpleStringProperty memberIdProperty() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId.set(memberId);
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
