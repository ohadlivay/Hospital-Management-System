package exceptions;

import java.io.Serializable;

public class EmptyInputException extends RuntimeException  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6917347396275480996L;

	public EmptyInputException() {
		super();
	}
	
    public EmptyInputException(String message) {
        super(message);
    }
}
