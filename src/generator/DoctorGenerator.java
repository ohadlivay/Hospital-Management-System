package generator;

import java.io.Serializable;
import java.util.*;
import model.Doctor;
import enums.Specialization;
import model.Department;
import control.Hospital;

public class DoctorGenerator implements Serializable {

    public static Doctor generateRandomDoctor() {
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
        boolean isFinishInternship = GeneratorUtils.getRandomNumber(0, 1) == 1;  // Random boolean
        Specialization specialization = getRandomSpecialization();

        // Creating and returning the Doctor object
        return new Doctor(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate, salary, licenseNumber, isFinishInternship, specialization);
    }

    // Helper method to get a random specialization
    private static Specialization getRandomSpecialization() {
        Specialization[] specializations = Specialization.values();
        return specializations[GeneratorUtils.getRandomNumber(0, specializations.length - 1)];
    }
}
