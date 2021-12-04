package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutRecord implements Serializable {
	
	private LibraryMember libraryMember;
	private BookCopy bookCopy;
	private Date checkoutDate;
	private Date dueDate;
	private List<Fine> fines = new ArrayList<>();

	public CheckoutRecord(LibraryMember libraryMember, BookCopy bookCopy, Date checkoutDate, Date dueDate) {
		this.libraryMember = libraryMember;
		this.bookCopy = bookCopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public List<Fine> getFines() {
		return fines;
	}

	public void setLibraryMember(LibraryMember libraryMember) {
		this.libraryMember = libraryMember;
	}

	public void setFines(List<Fine> fines) {
		this.fines = fines;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
