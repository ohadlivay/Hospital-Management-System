package GUI.buttons;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import GUI.ShortToStringComboBox;
import control.Hospital;
import exceptions.NegativeRecoveryTimeException;
import java.io.Serializable;
import model.Department;
import model.Injury;
import model.Disease;
import model.Fracture;

public class ChooseMedicalProblemTypeButton extends MyJButton  implements Serializable{

    public ChooseMedicalProblemTypeButton() {
        super("Add Medical Problem");

        this.addActionListener(e -> {
            String[] types = {"Injury", "Disease", "Fracture"};
            String selectedType = (String) JOptionPane.showInputDialog(
                    null,
                    "Select Medical Problem Type",
                    "Medical Problem Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    types,
                    types[0]);

            if (selectedType != null) {
                createMedicalProblem(selectedType);
            }
        });
    }

    private void createMedicalProblem(String type) {
        switch (type) {
            case "Injury":
                showInjuryInputPane();
                break;
            case "Disease":
                showDiseaseInputPane();
                break;
            case "Fracture":
                showFractureInputPane();
                break;
        }
    }

    private void showInjuryInputPane() {
        JTextField nameField = new JTextField();
        JTextField recoveryTimeField = new JTextField();
        JTextField locationField = new JTextField();

        // Fetch all departments from the Hospital instance
        List<Department> departmentsList = Hospital.getInstance().getDepartments().values().stream().collect(Collectors.toList());
        ShortToStringComboBox<Department> departmentComboBox = new ShortToStringComboBox<>(departmentsList.toArray(new Department[0]));
        departmentComboBox.setPreferredSize(new Dimension(200, 25));  // Limit the width of the JComboBox

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Injury Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Common Recovery Time (days):"));
        panel.add(recoveryTimeField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Select Department:"));
        panel.add(departmentComboBox);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Injury", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String recoveryTime = recoveryTimeField.getText().trim();
            String location = locationField.getText().trim();
            Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

            if (!name.isEmpty() && !recoveryTime.isEmpty() && !location.isEmpty() && selectedDepartment != null) {
                try {
                    double recoveryTimeValue = Double.parseDouble(recoveryTime);
                    Injury newInjury = new Injury(name, selectedDepartment, new HashSet<>(), recoveryTimeValue, location);
                    boolean added = Hospital.getInstance().addInjury(newInjury);

                    if (added) {
                        JOptionPane.showMessageDialog(null, "Injury added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add injury.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for recovery time.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (NegativeRecoveryTimeException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number for recovery time.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDiseaseInputPane() {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();

        // Fetch all departments from the Hospital instance
        List<Department> departmentsList = Hospital.getInstance().getDepartments().values().stream().collect(Collectors.toList());
        ShortToStringComboBox<Department> departmentComboBox = new ShortToStringComboBox<>(departmentsList.toArray(new Department[0]));
        departmentComboBox.setPreferredSize(new Dimension(200, 25));  // Limit the width of the JComboBox

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Disease Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Select Department:"));
        panel.add(departmentComboBox);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Disease", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

            if (!name.isEmpty() && !description.isEmpty() && selectedDepartment != null) {
                Disease newDisease = new Disease(name, selectedDepartment, new HashSet<>(), description);
                boolean added = Hospital.getInstance().addMedicalProblem(newDisease);

                if (added) {
                    JOptionPane.showMessageDialog(null, "Disease added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add disease.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFractureInputPane() {
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JCheckBox requiresCastCheckbox = new JCheckBox("Requires Cast");

        // Fetch all departments from the Hospital instance
        List<Department> departmentsList = Hospital.getInstance().getDepartments().values().stream().collect(Collectors.toList());
        ShortToStringComboBox<Department> departmentComboBox = new ShortToStringComboBox<>(departmentsList.toArray(new Department[0]));
        departmentComboBox.setPreferredSize(new Dimension(200, 25));  // Limit the width of the JComboBox

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Fracture Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel(""));
        panel.add(requiresCastCheckbox);
        panel.add(new JLabel("Select Department:"));
        panel.add(departmentComboBox);

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Fracture", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            boolean requiresCast = requiresCastCheckbox.isSelected();
            Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

            if (!name.isEmpty() && !location.isEmpty() && selectedDepartment != null) {
                Fracture newFracture = new Fracture(name, selectedDepartment, new HashSet<>(), location, requiresCast);
                boolean added = Hospital.getInstance().addMedicalProblem(newFracture);

                if (added) {
                    JOptionPane.showMessageDialog(null, "Fracture added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add fracture.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
