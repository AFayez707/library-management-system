package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Librarian extends StaffRole {
	
	public Librarian(String f, String l, String t, Address a) {
		super(f, l, t, a);
		super.setRoleName(StaffRoleEnum.LIBRARIAN.toString());
	}
	
	@Override
	List<String> getPermission() {
		// TODO Auto-generated method stub
		return  new ArrayList<>(Arrays.asList(
				Permission.CHECKOUT_BOOK.toString(), 
				Permission.DETERMINE_OVERDUE.toString(),
				Permission.PRINT_CHECKOUT_RECORD.toString()
				));
	}

}
