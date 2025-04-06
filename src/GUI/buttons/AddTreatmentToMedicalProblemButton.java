package GUI.buttons;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import control.Hospital;
import model.MedicalProblem;
import model.Treatment;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;

public class AddTreatmentToMedicalProblemButton extends ActionButton<AddTreatmentToMedicalProblemButton.InputData>  implements Serializable{

    public AddTreatmentToMedicalProblemButton() {
        super("Add Treatment to Medical Problem");
    }

    // Class to hold the input data (Treatment and MedicalProblem)
    public static class InputData {
        private Treatment treatment;
        private MedicalProblem medicalProblem;

        public InputData(Treatment treatment, MedicalProblem medicalProblem) {
            this.treatment = treatment;
            this.medicalProblem = medicalProblem;
        }

        public Treatment getTreatment() {
            return treatment;
        }

        public MedicalProblem getMedicalProblem() {
            return medicalProblem;
        }
    }

    @Override
    protected InputData getInput() {
        // Fetch all treatments and medical problems from the Hospital instance
        List<Treatment> treatmentsList = Hospital.getInstance().getTreatments().values().stream().collect(Collectors.toList());
        List<MedicalProblem> medicalProblemsList = Hospital.getInstance().getMedicalProblems().values().stream().collect(Collectors.toList());

        // Create the combo boxes
        ShortToStringComboBox<Treatment> treatmentsComboBox = new ShortToStringComboBox<>(treatmentsList.toArray(new Treatment[0]));
        ShortToStringComboBox<MedicalProblem> medicalProblemsComboBox = new ShortToStringComboBox<>(medicalProblemsList.toArray(new MedicalProblem[0]));

        // Limit the size of the combo boxes
        treatmentsComboBox.setPreferredSize(new Dimension(200, 25));
        medicalProblemsComboBox.setPreferredSize(new Dimension(200, 25));

        // Set up the panel layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Add:"));
        panel.add(treatmentsComboBox);
        panel.add(new JLabel("To:"));
        panel.add(medicalProblemsComboBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Treatment to Medical Problem",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Treatment selectedTreatment = (Treatment) treatmentsComboBox.getSelectedItem();
            MedicalProblem selectedMedicalProblem = (MedicalProblem) medicalProblemsComboBox.getSelectedItem();

            if (selectedTreatment != null && selectedMedicalProblem != null) {
                return new InputData(selectedTreatment, selectedMedicalProblem);
            }
        }

        return null; // Return null if canceled or invalid input
    }

    @Override
    protected boolean processInput(InputData inputData) throws Exception {
        // Extract the Treatment and MedicalProblem from the inputData object
        Treatment selectedTreatment = inputData.getTreatment();
        MedicalProblem selectedMedicalProblem = inputData.getMedicalProblem();

        // Handle adding the selected treatment to the selected medical problem
        selectedMedicalProblem.addTreatment(selectedTreatment);
        selectedTreatment.addMedicalProblem(selectedMedicalProblem);

        return true; // Indicate success
    }

}
