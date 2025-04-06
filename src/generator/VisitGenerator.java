package generator;

import java.io.Serializable;
import java.util.*;
import model.Visit;
import model.Patient;
import control.Hospital;

public class VisitGenerator implements Serializable {

    public static Visit generateRandomVisit() {
        Collection<Patient> patients = Hospital.getInstance().getPatients().values();
        List<Patient> patientList = new ArrayList<>(patients);
        
        if (patientList.isEmpty()) {
            throw new IllegalStateException("No patients available to generate a visit.");
        }
        
        // Get a random patient
        Random random = new Random();
        Patient randomPatient = patientList.get(random.nextInt(patientList.size()));

        // Generate random dates for the visit
        Date startVisit = GeneratorUtils.generateRandomDate(2022, 2023);
        Date endVisit = GeneratorUtils.generateRandomDate(2022, 2023);

        // Ensure the end date is after the start date
        if (endVisit.before(startVisit)) {
            Date temp = startVisit;
            startVisit = endVisit;
            endVisit = temp;
        }

        // Create and return the Visit object
        return new Visit(Hospital.getInstance().getNextPKNumber(),randomPatient, startVisit, endVisit);
    }
}
