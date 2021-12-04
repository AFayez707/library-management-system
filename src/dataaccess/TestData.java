package dataaccess;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import business.*;

/**
 * This class loads data into the data repository and also
 * sets up the storage units that are used in the application.
 * The main method in this class must be run once (and only
 * once) before the rest of the application can work properly.
 * It will create three serialized objects in the dataaccess.storage
 * folder.
 * 
 *
 */
public class TestData {
	
	
	public static void main(String[] args) {
		TestData td = new TestData();
		td.bookData();
		td.libraryMemberData();
		td.userData();
		td.authorData();
		DataAccess da = new DataAccessFacade();
		System.out.println(da.readBooksMap());
		System.out.println(da.readUserMap());
	}

	///create books
	public void bookData() {
		allBooks.get(0).addCopy();
		allBooks.get(0).addCopy();
		allBooks.get(1).addCopy();
		allBooks.get(3).addCopy();
		allBooks.get(2).addCopy();
		allBooks.get(2).addCopy();
		DataAccessFacade.loadBookMap(allBooks);
	}
	
	public void userData() {
		DataAccessFacade.loadUserMap(allUsers);
	}
	private void authorData() {
		DataAccessFacade.loadAuthorMap(allAuthors);
	}
	
	//create library members
	public void libraryMemberData() {
		LibraryMember libraryMember = new LibraryMember("1001", "Andy", "Rogers", "641-223-2211", addresses.get(4));
		members.add(libraryMember);
		libraryMember = new LibraryMember("1002", "Drew", "Stevens", "702-998-2414", addresses.get(5));
		members.add(libraryMember);
		
		libraryMember = new LibraryMember("1003", "Sarah", "Eagleton", "451-234-8811", addresses.get(6));
		ArrayList<CheckoutRecord> records = new ArrayList<>();
		Date date = convertLocalDateToDate(LocalDate.now().minusDays(31));
		Date dueDate = convertLocalDateToDate(LocalDate.now().minusDays(5));
		BookCopy copy = allBooks.get(1).getNextAvailableCopy();
		copy.setIsAvailable(false);
		records.add(new CheckoutRecord(libraryMember, copy, date, dueDate));
		libraryMember.setCheckoutRecords(records);
		members.add(libraryMember);

		libraryMember = new LibraryMember("1004", "Ricardo", "Montalbahn", "641-472-2871", addresses.get(7));
		members.add(libraryMember);
		
		DataAccessFacade.loadMemberMap(members);	
	}

	public Date convertLocalDateToDate(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}
	
	///////////// DATA //////////////
	List<LibraryMember> members = new ArrayList<LibraryMember>();
	@SuppressWarnings("serial")
	
	static List<Address> addresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};
	@SuppressWarnings("serial")
	public static List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("10005","Joe", "Thomas", "641-445-2123", addresses.get(0), "A happy man is he."));
			add(new Author("10006","Sandra", "Thomas", "641-445-2123", addresses.get(0), "A happy wife is she."));
			add(new Author("10007","Nirmal", "Pugh", "641-919-3223", addresses.get(1), "Thinker of thoughts."));
			add(new Author("10008","Andrew", "Cleveland", "976-445-2232", addresses.get(2), "Author of childrens' books."));
			add(new Author("10009", "Sarah", "Connor", "123-422-2663", addresses.get(3), "Known for her clever style."));
		}
	};
	
	@SuppressWarnings("serial")
	public static List<Book> allBooks = new ArrayList<Book>() {
		{
			add(new Book("23-11451", "The Big Fish", 21, Arrays.asList(allAuthors.get(0), allAuthors.get(1))));
			add(new Book("28-12331", "Antartica", 7, Arrays.asList(allAuthors.get(2))));
			add(new Book("99-22223", "Thinking Java", 21, Arrays.asList(allAuthors.get(3))));
			add(new Book("48-56882", "Jimmy's First Day of School", 7, Arrays.asList(allAuthors.get(4))));		
		}
	};

//	private static boolean checkIfBookIsAlreadyExist(String ISBN) {
//		for(Book book : allBooks) {
//			if(book.getIsbn().equals(ISBN)) {
//				return true;
//			}
//		}
//		return false;
//	}

//	public static String addBook(String title, String ISBN, int maxCheckoutLength, ArrayList<Author> authors) {
//		boolean alreadyExists = checkIfBookIsAlreadyExist(ISBN);
//		String errorMessage = null;
//
//		if(!alreadyExists) {
//			allBooks.add(new Book(ISBN, title, maxCheckoutLength, authors));
//		} else {
//			errorMessage = new String("Book already exists!");
//		}
//		return errorMessage;
//	}
	
	@SuppressWarnings("serial")
	List<User> allUsers = new ArrayList<User>() {
		{
			add(new User("101", "xyz", Auth.LIBRARIAN));
			add(new User("102", "abc", Auth.ADMIN));
			add(new User("103", "111", Auth.BOTH));
		}
	};
}
