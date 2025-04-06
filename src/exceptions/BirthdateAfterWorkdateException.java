package exceptions;

import java.io.Serializable;
import java.util.Date;


public class BirthdateAfterWorkdateException extends RuntimeException  implements Serializable{

    public BirthdateAfterWorkdateException(Date workdate, Date birthdate) {
        super("The work start date: " + workdate + " cannot be before birthdate : " + birthdate);
    }

}
