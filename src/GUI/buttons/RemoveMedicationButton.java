package GUI.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.Medication;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

public class RemoveMedicationButton extends ActionButton<RemoveMedicationButton.MedicationInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemoveMedicationButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Inner class to hold the input data for the medication
    public static class MedicationInputData {
        Medication selectedMedication;

        public MedicationInputData(Medication selectedMedication) {
            this.selectedMedication = selectedMedication;
        }
    }

    @Override
    protected MedicationInputData getInput() {
        // Fetch all medications from the Hospital instance
        Medication[] medicationsArray = Hospital.getInstance().getMedications().values().toArray(new Medication[0]);

        if (medicationsArray.length == 0) {
            showWarningMessage("No medications available to remove.");
            return null;
        }

        JComboBox<Medication> medicationComboBox = new JComboBox<>(medicationsArray);
        medicationComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Medication to Remove:"), BorderLayout.NORTH);
        panel.add(medicationComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Medication",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && medicationComboBox.getSelectedItem() != null) {
            return new MedicationInputData((Medication) medicationComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(MedicationInputData input) throws Exception {
        if (input == null || input.selectedMedication == null) {
            throw new NullPointerException("No medication selected.");
        }

        // Remove the selected medication using Hospital's removeMedication method
        boolean success = Hospital.getInstance().removeMedication(input.selectedMedication);

        if (!success) {
            throw new ObjectDoesNotExist(input.selectedMedication.getCode(), "Medication", "Hospital");
        }

        return true;
    }
}
