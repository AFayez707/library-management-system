package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecord> checkoutRecords;
	
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

	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
