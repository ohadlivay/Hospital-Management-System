package exceptions;

import java.io.Serializable;

public class UserIsAlreadySignedUp extends RuntimeException  implements Serializable{

	public UserIsAlreadySignedUp() {
		super("User is already signed up. Please try logging in.");
	}

}
