package exceptions;

import java.io.Serializable;

public class ObjectDoesNotExist extends RuntimeException implements Serializable{

	public <T> ObjectDoesNotExist(String id, String type,T target) {
		super("The "+type+" with ID "+id+" does not exist in "+target);
	}
	public <T> ObjectDoesNotExist(int id, String type,T target) {
		super("The "+type+" with ID "+id+" does not exist in "+target);
	}	

	
	
}
