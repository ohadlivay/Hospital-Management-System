package generator;

import control.Hospital;
import enums.Specialization;
import model.Department;
import model.Doctor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class DepartmentGenerator implements Serializable {

    private static final Random RANDOM = new Random();

    public static Department generateRandomDepartment() {
        int departmentNumber = Hospital.getInstance().getNextPKNumber(); // Assuming Hospital has a method to generate the next primary key number.
        String departmentName = GeneratorUtils.getRandomEnum(OptionBank.HospitalDepartmentName.class).toString(); // Use a random hospital location as the department name.
        String location = OptionBank.getRandomAddress().toString(); // Use a random address for the department location.
        Specialization specialization = GeneratorUtils.getRandomEnum(Specialization.class);

        HashSet<Doctor> allDoctors = Hospital.getInstance().getAllDoctors();
        // by design a generated department has no manager.
        return new Department(departmentNumber, departmentName, null, location, specialization);
    }

    // Helper method to get a random doctor from the set of all doctors
    private static Doctor getRandomDoctor(HashSet<Doctor> doctors) {
        int index = RANDOM.nextInt(doctors.size());
        return doctors.stream().skip(index).findFirst().orElse(null);
    }
}
