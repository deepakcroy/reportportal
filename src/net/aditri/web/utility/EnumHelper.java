package net.aditri.web.utility;

public class EnumHelper {
	public enum Status {
		/*INACTIVE,
	    ACTIVE,
	    PENDING,
	    DELETED;*/
	    Inactive(0),
	    Active(1),
	    Pending(2),
	    Deleted(3);
		private int value;
		private Status(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    @Override
	    public String toString(){
	        return this.name();
	    }

	    public String getName(){
	        return this.name();
	    }
	}
	
	public enum LoginAs {
	    User(0),
	    SystemAdmin(1),
	    Guest(2);
		
		private int value;
		private LoginAs(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
	}
}
