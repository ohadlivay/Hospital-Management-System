package exceptions;

import java.io.Serializable;

public class NegativeDosageException extends RuntimeException implements Serializable{

	public NegativeDosageException(double dosage) {
		super("The dose "+dosage+" is negative. Please make sure to enter a non-negative dose");
	}

}
