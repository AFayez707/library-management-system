package business;

import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord {
	
	private LibraryMember libraryMember;
	private List<Fine> fines = new ArrayList<>();
	
	public CheckoutRecord(LibraryMember librarymember) {
		this.libraryMember = librarymember;
	}

}
