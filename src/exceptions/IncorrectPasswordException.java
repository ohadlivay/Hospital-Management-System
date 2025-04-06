package exceptions;

import java.io.Serializable;

public class IncorrectPasswordException extends RuntimeException  implements Serializable{

	public IncorrectPasswordException() {
		super("Wrong password!");
	}

}
