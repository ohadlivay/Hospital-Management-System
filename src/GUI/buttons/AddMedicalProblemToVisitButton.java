package GUI.buttons;

import control.Hospital;
import model.MedicalProblem;
import model.Visit;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.io.Serializable;

public class AddMedicalProblemToVisitButton extends ActionButton<AddMedicalProblemToVisitButton.MedicalProblemVisitInput> implements Serializable {

    public AddMedicalProblemToVisitButton() {
        super("Add Medical Problem to Visit");
    }

    // Inner class to hold the input data
    public static class MedicalProblemVisitInput {
        MedicalProblem medicalProblem;
        Visit visit;

        public MedicalProblemVisitInput(MedicalProblem medicalProblem, Visit visit) {
            this.medicalProblem = medicalProblem;
            this.visit = visit;
        }
    }

    @Override
    protected MedicalProblemVisitInput getInput() {
        // Create input fields for selecting the medical problem and the visit
    	ShortToStringComboBox<MedicalProblem> medicalProblemComboBox = new ShortToStringComboBox<>(Hospital.getInstance().getMedicalProblems().values().toArray(new MedicalProblem[0]));
    	ShortToStringComboBox<Visit> visitComboBox = new ShortToStringComboBox<>(Hospital.getInstance().getVisits().values().toArray(new Visit[0]));

        medicalProblemComboBox.setPreferredSize(new Dimension(200, 25));
        visitComboBox.setPreferredSize(new Dimension(200, 25));

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Select Medical Problem:", medicalProblemComboBox,
            "Select Visit:", visitComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add Medical Problem to Visit", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            MedicalProblem selectedMedicalProblem = (MedicalProblem) medicalProblemComboBox.getSelectedItem();
            Visit selectedVisit = (Visit) visitComboBox.getSelectedItem();

            if (selectedMedicalProblem == null || selectedVisit == null) {
                throw new NullPointerException("Medical problem or visit cannot be null");
            }

            return new MedicalProblemVisitInput(selectedMedicalProblem, selectedVisit);
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(MedicalProblemVisitInput input) {
        // Add the medical problem to the visit
        return input.visit.addMedicalProblem(input.medicalProblem);
    }
}
