package business;

import java.io.Serializable;

public class Fine implements Serializable {
	private double value;
	private boolean isPaid = false;
	
	public Fine(double value) {
		this.value = value;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
