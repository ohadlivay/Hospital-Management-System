package generator;

import java.io.Serializable;
import java.util.*;

import control.Hospital;
import model.Department;

public class GeneratorUtils implements Serializable {

    private static final Random RANDOM = new Random();
    private static final List<String> EMAIL_DOMAINS = Arrays.asList("@gmail.com", "@yahoo.com", "@outlook.com", "@hotmail.com", "@live.com");

    // Method to generate a random date between the specified year range
    public static Date generateRandomDate(int startYear, int endYear) {
        Calendar calendar = Calendar.getInstance();
        int year = getRandomNumber(startYear, endYear);
        int dayOfYear = getRandomNumber(1, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
    }

    public static String generateRandomGender() {
        double randomValue = RANDOM.nextDouble();
        
        if (randomValue < 0.495) {
            return "Male";  // 49.5% chance
        } else if (randomValue < 0.99) {
            return "Female";  // 49.5% chance
        } else {
            return getRandomNonBinaryGender();  // 1% chance
        }
    }

    private static String getRandomNonBinaryGender() {
        // Filter out Male and Female from the enum
        OptionBank.Gender[] nonBinaryGenders = OptionBank.Gender.values();
        
        // Assuming Gender enum contains more than just Male/Female
        return nonBinaryGenders[RANDOM.nextInt(nonBinaryGenders.length)].name();
    }

    // Generic method to generate a random enum value
    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[RANDOM.nextInt(enumConstants.length)];
    }

    // Method to generate a random phone number
    public static String generateRandomPhoneNumber() {
        return String.format("+1-%03d-%03d-%04d", RANDOM.nextInt(1000), RANDOM.nextInt(1000), RANDOM.nextInt(10000));
    }

    // Method to generate a random salary
    public static double generateRandomSalary() {
        return 10000 + (RANDOM.nextDouble() * 10000);  // Generating a salary between 10,000 and 20,000
    }

    // Method to generate a random license number
    public static int generateRandomLicenseNumber() {
        return RANDOM.nextInt(100000);  // Generating a random 5-digit license number
    }

    // Method to generate a random number between min and max (inclusive)
    public static int getRandomNumber(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    // Method to generate a random email without names
    public static String generateRandomEmail() {
        String username = generateRandomUsername();
        String domain = EMAIL_DOMAINS.get(RANDOM.nextInt(EMAIL_DOMAINS.size()));
        return username + domain;
    }

    // Helper method to generate a random username
    private static String generateRandomUsername() {
        int length = getRandomNumber(6, 12); // Username length between 6 and 12 characters
        StringBuilder username = new StringBuilder(length);
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            username.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return username.toString();
    }

    public static Department getRandomDepartment() {
        Collection<Department> departments = Hospital.getInstance().getDepartments().values();

        // If there are no departments, generate a new one
        if (departments.isEmpty()) {
            Department newDepartment = DepartmentGenerator.generateRandomDepartment();
            Hospital.getInstance().addDepartment(newDepartment); // Add the new department to the hospital
            return newDepartment;
        }

        // Otherwise, return a random existing department
        ArrayList<Department> departmentList = new ArrayList<>(departments);
        return departmentList.get(RANDOM.nextInt(departmentList.size()));
    }

	public static double getRandomDosage() {
		return RANDOM.nextDouble()*RANDOM.nextInt(1,10);
	}

    
    
}
