package business;

import java.util.ArrayList;
import java.util.List;

public class Staff extends Person{
	
	private int id;
	private String password;
	private List<StaffRole> staffRole = new ArrayList();
	
	public Staff(String f, String l, String t, Address a) {
		super(f, l, t, a);
	}	
	
	
	public List<StaffRole> getStaffRole() {
		return staffRole;
	}
	public void setStaffRole(List<StaffRole> staffRole) {
		this.staffRole = staffRole;
	}
	public void addStaffRole(StaffRole role) {
		this.staffRole.add(role);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	
	

}
