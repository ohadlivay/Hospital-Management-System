package GUI.buttons;

import control.Hospital;
import model.Visit;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

import javax.swing.*;

import GUI.HospitalSystem;

import java.awt.*;
import java.util.List;

public class RemoveVisitButton extends ActionButton<RemoveVisitButton.VisitInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemoveVisitButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Class to hold the input data (selected visit)
    public static class VisitInputData {
        private Visit selectedVisit;

        public VisitInputData(Visit selectedVisit) {
            this.selectedVisit = selectedVisit;
        }

        public Visit getSelectedVisit() {
            return selectedVisit;
        }
    }

    @Override
    protected VisitInputData getInput() {
        // Fetch all visits from the Hospital instance
        List<Visit> visitsList = Hospital.getInstance().getVisits().values().stream().toList();

        if (visitsList.isEmpty()) {
            showWarningMessage("No visits available to remove.");
            return null;
        }

        // Create JComboBox for visit selection
        JComboBox<Visit> visitComboBox = new JComboBox<>(visitsList.toArray(new Visit[0]));
        visitComboBox.setPreferredSize(new Dimension(200, 25));

        // Set up the panel layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select Visit to Remove:"), BorderLayout.NORTH);
        panel.add(visitComboBox, BorderLayout.CENTER);

        // Show the panel in a JOptionPane
        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Visit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && visitComboBox.getSelectedItem() != null) {
            return new VisitInputData((Visit) visitComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(VisitInputData inputData) throws Exception {
        if (inputData == null || inputData.getSelectedVisit() == null) {
            throw new NullPointerException("No visit selected.");
        }

        // Remove the selected visit using Hospital's removeVisit method
        boolean success = Hospital.getInstance().removeVisit(inputData.getSelectedVisit());

        if (!success) {
            throw new ObjectDoesNotExist(inputData.getSelectedVisit().getNumber(), "Visit", "Hospital");
        }

        return true;
    }
}
