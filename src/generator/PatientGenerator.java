package generator;

import model.Patient;
import enums.BiologicalSex;
import enums.HealthFund;
import model.Visit;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import control.Hospital;

public class PatientGenerator implements Serializable {

    private static final Random RANDOM = new Random();

    public static Patient generateRandomPatient() {
    	Random random = new Random();
    	int id = random.nextInt(400000000 - 200000000 + 1) + 200000000;
        String firstName = GeneratorUtils.getRandomEnum(OptionBank.FirstName.class).name();
        String lastName = GeneratorUtils.getRandomEnum(OptionBank.LastName.class).name();
        Date birthDate = GeneratorUtils.generateRandomDate(1950, 2005);
        String address = GeneratorUtils.getRandomEnum(OptionBank.Address.class).name().replace('_', ' ') + " " + RANDOM.nextInt(1000);
        String phoneNumber = GeneratorUtils.generateRandomPhoneNumber();
        String email = GeneratorUtils.generateRandomEmail();
        String gender = GeneratorUtils.generateRandomGender().toString();
        HashSet<Visit> visitsList = new HashSet<>();
        HealthFund healthFund = GeneratorUtils.getRandomEnum(HealthFund.class);
        BiologicalSex biologicalSex = GeneratorUtils.getRandomEnum(BiologicalSex.class);

        return new Patient(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, visitsList, healthFund, biologicalSex);
    }
}
