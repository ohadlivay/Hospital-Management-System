package GUI.buttons;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import control.Hospital;
import model.Medication;
import model.Treatment;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;

public class AddMedicationToTreatmentButton extends ActionButton<AddMedicationToTreatmentButton.InputData>  implements Serializable{

    public AddMedicationToTreatmentButton() {
        super("Add Medication to Treatment");
    }

    // Class to hold the input data (Medication and Treatment)
    public static class InputData {
        private Medication medication;
        private Treatment treatment;

        public InputData(Medication medication, Treatment treatment) {
            this.medication = medication;
            this.treatment = treatment;
        }

        public Medication getMedication() {
            return medication;
        }

        public Treatment getTreatment() {
            return treatment;
        }
    }

    @Override
    protected InputData getInput() {
        // Fetch all medications and treatments from the Hospital instance
        List<Medication> medicationsList = Hospital.getInstance().getMedications().values().stream().collect(Collectors.toList());
        List<Treatment> treatmentsList = Hospital.getInstance().getTreatments().values().stream().collect(Collectors.toList());

        // Create the combo boxes
        JComboBox<Medication> medicationsComboBox = new ShortToStringComboBox<>(medicationsList.toArray(new Medication[0]));
        JComboBox<Treatment> treatmentsComboBox = new ShortToStringComboBox<>(treatmentsList.toArray(new Treatment[0]));

        // Limit the size of the combo boxes
        medicationsComboBox.setPreferredSize(new Dimension(200, 25));
        treatmentsComboBox.setPreferredSize(new Dimension(200, 25));

        // Set up the panel layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Add:"));
        panel.add(medicationsComboBox);
        panel.add(new JLabel("To:"));
        panel.add(treatmentsComboBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Medication to Treatment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Medication selectedMedication = (Medication) medicationsComboBox.getSelectedItem();
            Treatment selectedTreatment = (Treatment) treatmentsComboBox.getSelectedItem();

            if (selectedMedication != null && selectedTreatment != null) {
                return new InputData(selectedMedication, selectedTreatment);
            }
        }

        return null; // Return null if canceled or invalid input
    }

    @Override
    protected boolean processInput(InputData inputData) throws Exception {
        // Extract the Medication and Treatment from the inputData object
        Medication selectedMedication = inputData.getMedication();
        Treatment selectedTreatment = inputData.getTreatment();

        // Handle adding the selected medication to the selected treatment
        boolean addedSuccessfully = selectedTreatment.addMedication(selectedMedication);

        return addedSuccessfully; // Indicate success
    }

}
