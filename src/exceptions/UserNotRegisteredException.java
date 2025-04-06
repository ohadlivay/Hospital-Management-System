package exceptions;

import java.io.Serializable;

import model.StaffMember;

public class UserNotRegisteredException extends RuntimeException  implements Serializable{

	public UserNotRegisteredException() {
		super();
	}

}
