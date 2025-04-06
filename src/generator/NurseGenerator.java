package generator;

import java.io.Serializable;
import java.util.*;
import model.Nurse;
import enums.Specialization;
import model.Department;
import control.Hospital;

public class NurseGenerator implements Serializable {

    public static Nurse generateRandomNurse() {
        int id = Hospital.getInstance().getNextPKNumber();
        String firstName = OptionBank.getRandomFirstName().toString();
        String lastName = OptionBank.getRandomLastName().toString();
        Date birthDate = GeneratorUtils.generateRandomDate(1950, 2000);  // Generating a birth date between 1950 and 2000
        String address = OptionBank.getRandomAddress().toString();
        String phoneNumber = GeneratorUtils.generateRandomPhoneNumber();
        String email = GeneratorUtils.generateRandomEmail();  
        String gender = GeneratorUtils.generateRandomGender().toString();
        Date workStartDate = GeneratorUtils.generateRandomDate(2005, 2020);  // Generating a work start date between 2005 and 2020
        double salary = GeneratorUtils.generateRandomSalary();
        int licenseNumber = GeneratorUtils.generateRandomLicenseNumber();


        // Creating and returning the Doctor object
        return new Nurse(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate, salary, licenseNumber);
    }

}
