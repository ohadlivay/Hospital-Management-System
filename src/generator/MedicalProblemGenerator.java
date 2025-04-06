package generator;

import model.*;
import enums.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class MedicalProblemGenerator implements Serializable {

    private static final Random RANDOM = new Random();

    public static MedicalProblem generateRandomMedicalProblem() {
        int choice = RANDOM.nextInt(3); // Generate a random number between 0 and 2

        switch (choice) {
            case 0:
                return generateRandomInjury();
            case 1:
                return generateRandomFracture();
            default:
                return generateRandomDisease();
        }
    }

    private static Injury generateRandomInjury() {
        String name = OptionBank.Injury.getRandom().name().replace('_', ' ');
        Department department = GeneratorUtils.getRandomDepartment();
        double commonRecoveryTime = GeneratorUtils.getRandomNumber(1, 120); // Random recovery time in days
        String location = OptionBank.InjuryLocation.getRandom().name().replace('_', ' ');

        return new Injury(name, department, commonRecoveryTime, location);
    }

    private static Fracture generateRandomFracture() {
        String name = OptionBank.Fracture.getRandom().name().replace('_', ' ');
        Department department = GeneratorUtils.getRandomDepartment();
        String location = OptionBank.FractureLocation.getRandom().name().replace('_', ' ');
        boolean requiresCast = RANDOM.nextBoolean();

        return new Fracture(name, department, location, requiresCast);
    }

    private static Disease generateRandomDisease() {
        String name = OptionBank.Disease.getRandom().name().replace('_', ' ');
        Department department = GeneratorUtils.getRandomDepartment();
        String description = "Description for " + name;

        return new Disease(name, department, description);
    }

}
