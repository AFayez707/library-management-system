package business;

import dataaccess.Auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends StaffRole {
	
	public Admin(String f, String l, String t, Address a) {
		super(f, l, t, a);
		super.setRoleName(Auth.ADMIN.toString());
	}
	
@Override
List<String> getPermission() {
	// TODO Auto-generated method stub
	return  new ArrayList<>(Arrays.asList(
			Permission.ADD_BOOK.toString(), 
			Permission.ADD_LIBRARY_MEMBER.toString(),
			Permission.ADD_BOOK_COPY.toString(),
			Permission.DETERMINE_OVERDUE.toString()			
			));
}

}
