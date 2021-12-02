package business;

public enum BorrowPeriod {
	  DAYS_21(21), DAYS_7(7);
    
	private final int value;

    private BorrowPeriod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
