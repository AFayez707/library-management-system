package ui.utils;

import business.Staff;

import java.util.NoSuchElementException;

public class SessionUtil {
    private static Staff staff;

    public static void setStaff(Staff staff){
        SessionUtil.staff = staff;
    }

    public Staff getStaff(){
        if(staff==null)
            throw new NoSuchElementException("No active session found");
        return this.staff;
    }

}
