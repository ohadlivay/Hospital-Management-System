package exceptions;

import java.io.Serializable;
import java.util.Date;

public class FutureDateException extends RuntimeException implements Serializable{
    private final Date date;

    public FutureDateException(Date date) {
        super("The date " + date + " is in the future.");
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
