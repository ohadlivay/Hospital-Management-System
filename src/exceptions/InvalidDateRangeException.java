package exceptions;

import java.io.Serializable;

public class InvalidDateRangeException extends RuntimeException  implements Serializable{
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
