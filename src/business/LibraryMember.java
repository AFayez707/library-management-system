package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecord> checkoutRecords = new ArrayList<>();
	
	public List<CheckoutRecord> getCheckoutRecords() {
		return checkoutRecords;
	}


	public void setCheckoutRecords(List<CheckoutRecord> checkoutRecords) {
		this.checkoutRecords = checkoutRecords;
	}


	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;		
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public void checkout(Book book) {
		BookCopy copy = book.getNextAvailableCopy();
		copy.changeAvailability();
		CheckoutRecord record = new CheckoutRecord(this,
				copy,
				new Date(System.currentTimeMillis()),
				convertLocalDateToDate(LocalDate.now().plusDays(book.getMaxCheckoutLength())));
		checkoutRecords.add(record);
	}

	public Date convertLocalDateToDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}


	public boolean hasActiveCheckout(){
		for (CheckoutRecord record: checkoutRecords) {
			if(record.getDueDate().getTime() > System.currentTimeMillis()){
				return true;
			}
		}
		return false;
	}

	public List<CheckoutRecord> getOverdueRecords(String isbn){
		ArrayList<CheckoutRecord> records = new ArrayList<>();
		for (CheckoutRecord record: checkoutRecords) {
			if(record.getBookCopy().getBook().getIsbn().equals(isbn) &&
					record.getDueDate().getTime() < System.currentTimeMillis() &&
					!record.getBookCopy().isAvailable()){
				records.add(record);
			}
		}
		return records;
	}

	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
