package GUI.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.Department;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

public class RemoveDepartmentButton extends ActionButton<RemoveDepartmentButton.DepartmentInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemoveDepartmentButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Inner class to hold the input data for the department
    public static class DepartmentInputData {
        Department selectedDepartment;

        public DepartmentInputData(Department selectedDepartment) {
            this.selectedDepartment = selectedDepartment;
        }
    }

    @Override
    protected DepartmentInputData getInput() {
        // Fetch all departments from the Hospital instance
        Department[] departmentsArray = Hospital.getInstance().getDepartments().values().toArray(new Department[0]);

        if (departmentsArray.length == 0) {
            showWarningMessage("No departments available to remove.");
            return null;
        }

        JComboBox<Department> departmentComboBox = new JComboBox<>(departmentsArray);
        departmentComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Department to Remove:"), BorderLayout.NORTH);
        panel.add(departmentComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Department",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && departmentComboBox.getSelectedItem() != null) {
            return new DepartmentInputData((Department) departmentComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(DepartmentInputData input) throws Exception {
        if (input == null || input.selectedDepartment == null) {
            throw new NullPointerException("No department selected.");
        }

        // Remove the selected department using Hospital's removeDepartment method
        boolean success = Hospital.getInstance().removeDepartment(input.selectedDepartment);

        if (!success) {
        	System.out.println("in removedep");
            throw new ObjectDoesNotExist(input.selectedDepartment.getNumber(), "Department", "Hospital");
        }

        return true;
    }
}
