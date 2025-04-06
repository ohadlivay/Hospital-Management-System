package exceptions;

import java.io.Serializable;

public class NegativeRecoveryTimeException extends RuntimeException implements Serializable{

	public NegativeRecoveryTimeException(double recoveryTime) {
		super(recoveryTime + " is an illegal argument for recovery time as it must be positive");
	}

}
