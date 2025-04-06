package exceptions;

import java.io.Serializable;

public class ObjectAlreadyExistsException extends RuntimeException implements Serializable{

	public <T, G> ObjectAlreadyExistsException(T object,G target) {
		super("The "+object.getClass().getSimpleName()+ " already exists in "+target.getClass().getSimpleName());
	}

	
}
