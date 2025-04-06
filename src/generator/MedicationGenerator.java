package generator;

import model.Medication;
import control.Hospital;
import generator.OptionBank;

import java.io.Serializable;

public class MedicationGenerator implements Serializable {

    public static Medication generateRandomMedication() {
        int code = Hospital.getInstance().getNextPKNumber(); // Assuming this method exists for generating unique codes
        String name = OptionBank.Medication.getRandom().name().replace('_', ' ');
        double dosage = GeneratorUtils.getRandomDosage();
        int numberOfDose = GeneratorUtils.getRandomNumber(1, 10); // Random number of doses between 1 and 10

        return new Medication(code, name, dosage, numberOfDose);
    }
}
