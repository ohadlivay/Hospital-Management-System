package GUI.buttons;

import control.Hospital;
import exceptions.NegativeDosageException;
import exceptions.NegativeNumberOfDosesException;
import java.io.Serializable;
import model.Medication;

import javax.swing.*;
import java.awt.*;

public class AddMedicationButton extends ActionButton<AddMedicationButton.MedicationInputData>  implements Serializable{

    public AddMedicationButton() {
        super("Add Medication");
    }

    // Class to hold the input data (Name, Dosage, Number of Doses)
    public static class MedicationInputData {
        private String name;
        private double dosage;
        private int numberOfDose;

        public MedicationInputData(String name, double dosage, int numberOfDose) {
            this.name = name;
            this.dosage = dosage;
            this.numberOfDose = numberOfDose;
        }

        public String getName() {
            return name;
        }

        public double getDosage() {
            return dosage;
        }

        public int getNumberOfDose() {
            return numberOfDose;
        }
    }

    @Override
    protected MedicationInputData getInput() {
        // Create input fields
        JTextField nameField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField numberOfDoseField = new JTextField();

        // Set up the panel layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Medication Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Dosage:"));
        panel.add(dosageField);
        panel.add(new JLabel("Number of Doses:"));
        panel.add(numberOfDoseField);

        // Show the panel in a JOptionPane
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Medication", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                double dosage = Double.parseDouble(dosageField.getText().trim());
                int numberOfDose = Integer.parseInt(numberOfDoseField.getText().trim());

                if (!name.isEmpty()) {
                    return new MedicationInputData(name, dosage, numberOfDose);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for dosage and number of doses.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null; // Return null if the operation is canceled or input is invalid
    }

    @Override
    protected boolean processInput(MedicationInputData inputData) throws Exception {
        // Extract data from the input
        String name = inputData.getName();
        double dosage = inputData.getDosage();
        int numberOfDose = inputData.getNumberOfDose();

        // Assign a unique code to the medication
        int code = Hospital.getInstance().getNextPKNumber();

        try {
            // Create a new Medication object, which may throw exceptions
            Medication newMedication = new Medication(code, name, dosage, numberOfDose);

            // Add the medication to the hospital system
            return Hospital.getInstance().addMedication(newMedication);
        } catch (NegativeDosageException | NegativeNumberOfDosesException ex) {
            // Handle specific exceptions related to invalid medication attributes
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Invalid Medication",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

}
