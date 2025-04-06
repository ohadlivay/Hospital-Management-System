package GUI.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.Patient;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

public class RemovePatientButton extends ActionButton<RemovePatientButton.PatientInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemovePatientButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Inner class to hold the input data for the patient
    public static class PatientInputData {
        Patient selectedPatient;

        public PatientInputData(Patient selectedPatient) {
            this.selectedPatient = selectedPatient;
        }
    }

    @Override
    protected PatientInputData getInput() {
        // Fetch all patients from the Hospital instance
        Patient[] patientsArray = Hospital.getInstance().getPatients().values().toArray(new Patient[0]);

        if (patientsArray.length == 0) {
            showWarningMessage("No patients available to remove.");
            return null;
        }

        JComboBox<Patient> patientComboBox = new JComboBox<>(patientsArray);
        patientComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Patient to Remove:"), BorderLayout.NORTH);
        panel.add(patientComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Patient",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && patientComboBox.getSelectedItem() != null) {
            return new PatientInputData((Patient) patientComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(PatientInputData input) throws Exception {
        if (input == null || input.selectedPatient == null) {
            throw new NullPointerException("No patient selected.");
        }

        // Remove the selected patient using Hospital's removePatient method
        boolean success = Hospital.getInstance().removePatient(input.selectedPatient);

        if (!success) {
            throw new ObjectDoesNotExist(input.selectedPatient.getId(), "Patient", "Hospital");
        }

        return true;
    }
}
