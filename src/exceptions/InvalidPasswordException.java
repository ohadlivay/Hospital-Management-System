package exceptions;

import java.io.Serializable;

public class InvalidPasswordException extends RuntimeException  implements Serializable{

	public InvalidPasswordException() {
		super("Invalid password.");
	}
	
}
