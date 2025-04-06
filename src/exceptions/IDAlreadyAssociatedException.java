package exceptions;

import java.io.Serializable;

public class IDAlreadyAssociatedException extends RuntimeException  implements Serializable{
	public IDAlreadyAssociatedException() {
		super();
	}
	
    public IDAlreadyAssociatedException(String message) {
        super(message);
    }
}
