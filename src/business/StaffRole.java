package business;

import java.util.List;

public abstract class StaffRole extends Staff {
	
	private String roleName;
	
	abstract List<String> getPermission();
	
	public StaffRole(String f, String l, String t, Address a) {
		super(f, l, t, a);
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
		super.addStaffRole(this);
	}	
	

}
