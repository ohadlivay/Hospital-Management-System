package generator;

import model.Treatment;

import java.io.Serializable;

import control.Hospital;
import generator.OptionBank;

public class TreatmentGenerator implements Serializable {

    public static Treatment generateRandomTreatment() {
        int serialNumber = Hospital.getInstance().getNextPKNumber(); 
        String description = OptionBank.Treatment.getRandom().name().replace('_', ' ');

        return new Treatment(serialNumber, description);
    }
}
