package GUI.buttons;

import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.Treatment;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

import java.awt.*;

public class RemoveTreatmentButton extends ActionButton<RemoveTreatmentButton.TreatmentInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemoveTreatmentButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Inner class to hold the input data for the treatment
    public static class TreatmentInputData {
        Treatment selectedTreatment;

        public TreatmentInputData(Treatment selectedTreatment) {
            this.selectedTreatment = selectedTreatment;
        }
    }

    @Override
    protected TreatmentInputData getInput() {
        // Fetch all treatments from the Hospital instance
        Treatment[] treatmentsArray = Hospital.getInstance().getTreatments().values().toArray(new Treatment[0]);

        if (treatmentsArray.length == 0) {
            showWarningMessage("No treatments available to remove.");
            return null;
        }

        JComboBox<Treatment> treatmentComboBox = new JComboBox<>(treatmentsArray);
        treatmentComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Treatment to Remove:"), BorderLayout.NORTH);
        panel.add(treatmentComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Treatment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && treatmentComboBox.getSelectedItem() != null) {
            return new TreatmentInputData((Treatment) treatmentComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(TreatmentInputData input) throws Exception {
        if (input == null || input.selectedTreatment == null) {
            throw new NullPointerException("No treatment selected.");
        }

        // Remove the selected treatment using Hospital's removeTreatment method
        boolean success = Hospital.getInstance().removeTreatment(input.selectedTreatment);

        if (!success) {
            throw new ObjectDoesNotExist(input.selectedTreatment.getSerialNumber(), "Treatment", "Hospital");
        }

        return true;
    }
}
