package GUI.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.*;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

public class RemoveMedicalProblemButton extends MyJButton  implements Serializable{

    private HospitalSystem parent;

    public RemoveMedicalProblemButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;

        // Add action listener for removing a medical problem
        this.addActionListener(e -> {
            MedicalProblemInputData inputData = getInput();
            if (inputData != null) {
                try {
                    boolean success = processInput(inputData);
                    if (success) {
                        JOptionPane.showMessageDialog(parent, "Medical problem removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Inner class to hold the input data for the medical problem
    public static class MedicalProblemInputData {
        MedicalProblem selectedMedicalProblem;

        public MedicalProblemInputData(MedicalProblem selectedMedicalProblem) {
            this.selectedMedicalProblem = selectedMedicalProblem;
        }
    }

    protected MedicalProblemInputData getInput() {
        // Fetch all medical problems from the Hospital instance
        MedicalProblem[] medicalProblemsArray = Hospital.getInstance().getMedicalProblems().values().toArray(new MedicalProblem[0]);

        if (medicalProblemsArray.length == 0) {
            JOptionPane.showMessageDialog(parent, "No medical problems available to remove.", "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        JComboBox<MedicalProblem> medicalProblemComboBox = new JComboBox<>(medicalProblemsArray);
        medicalProblemComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Medical Problem to Remove:"), BorderLayout.NORTH);
        panel.add(medicalProblemComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Medical Problem",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION && medicalProblemComboBox.getSelectedItem() != null) {
            return new MedicalProblemInputData((MedicalProblem) medicalProblemComboBox.getSelectedItem());
        }
        return null;
    }

    protected boolean processInput(MedicalProblemInputData input) throws Exception {

        if (input == null || input.selectedMedicalProblem == null) {
            throw new NullPointerException("No medical problem selected.");
        }

        MedicalProblem medicalProblem = input.selectedMedicalProblem;

        boolean success = Hospital.getInstance().removeMedicalProblem(medicalProblem);
		System.out.println("break");

        if (!success) {

            throw new ObjectDoesNotExist(medicalProblem.getCode(), medicalProblem.getClass().getSimpleName(), "Hospital");
        }

        return true;
    }
}
