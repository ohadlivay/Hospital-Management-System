package GUI.buttons;

import control.Hospital;
import model.Treatment;
import model.Visit;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.io.Serializable;

public class AddTreatmentToVisitButton extends ActionButton<AddTreatmentToVisitButton.TreatmentVisitInput> implements Serializable {

    public AddTreatmentToVisitButton() {
        super("Add Treatment to Visit");
    }

    // Inner class to hold the input data
    public static class TreatmentVisitInput {
        Treatment treatment;
        Visit visit;

        public TreatmentVisitInput(Treatment treatment, Visit visit) {
            this.treatment = treatment;
            this.visit = visit;
        }
    }

    @Override
    protected TreatmentVisitInput getInput() {
        // Create input fields for selecting the treatment and the visit
    	ShortToStringComboBox<Treatment> treatmentComboBox = new ShortToStringComboBox<>(Hospital.getInstance().getTreatments().values().toArray(new Treatment[0]));
    	ShortToStringComboBox<Visit> visitComboBox = new ShortToStringComboBox<>(Hospital.getInstance().getVisits().values().toArray(new Visit[0]));

        treatmentComboBox.setPreferredSize(new Dimension(200, 25));
        visitComboBox.setPreferredSize(new Dimension(200, 25));

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Select Treatment:", treatmentComboBox,
            "Select Visit:", visitComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add Treatment to Visit", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Treatment selectedTreatment = (Treatment) treatmentComboBox.getSelectedItem();
            Visit selectedVisit = (Visit) visitComboBox.getSelectedItem();

            if (selectedTreatment == null || selectedVisit == null) {
                throw new NullPointerException("Treatment or visit cannot be null");
            }

            return new TreatmentVisitInput(selectedTreatment, selectedVisit);
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(TreatmentVisitInput input) {
        // Add the treatment to the visit
        return input.visit.addTreatment(input.treatment);
    }
}
