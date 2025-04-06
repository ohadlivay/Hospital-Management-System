package exceptions;

import java.io.Serializable;

public class NoIntensiveCareDepartmentForICStaffException extends RuntimeException implements Serializable{

    // Default constructor
    public NoIntensiveCareDepartmentForICStaffException() {
        super("No Intensive Care department found for Intensive Care staff.");
    }

    // Constructor with a custom message
    public NoIntensiveCareDepartmentForICStaffException(String message) {
        super(message);
    }
}
