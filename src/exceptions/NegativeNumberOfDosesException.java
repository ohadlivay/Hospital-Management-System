package exceptions;

import java.io.Serializable;

public class NegativeNumberOfDosesException extends RuntimeException implements Serializable{

	public NegativeNumberOfDosesException(int numberOfDose) {
		super("The number of doses "+numberOfDose+" is negative. Please make sure to enter a non-negative number of doses");
	}

}
