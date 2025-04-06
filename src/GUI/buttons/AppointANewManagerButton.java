package GUI.buttons;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import control.Hospital;
import java.io.Serializable;
import model.Department;
import model.Doctor;

public class AppointANewManagerButton extends MyJButton implements Serializable {
//admin only
    public AppointANewManagerButton(String buttonLabel) {
        super(buttonLabel);

        // Add an ActionListener to handle the button click
        this.addActionListener(e -> {
            // Get the list of departments from the Hospital
            Map<Integer, Department> departmentMap = Hospital.getInstance().getDepartments();
            List<Department> departments = new ArrayList<>(departmentMap.values());

            // Create a JComboBox for selecting a department
            ShortToStringComboBox<Department> departmentComboBox = new ShortToStringComboBox<>(departments.toArray(new Department[0]));
            departmentComboBox.setPreferredSize(new Dimension(200, 30));
            // Show a dialog to select a department
            int result = JOptionPane.showConfirmDialog(null, departmentComboBox, "Select a Department", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

                if (selectedDepartment != null) {
                    // Attempt to appoint a new manager
                    try {
                        Doctor newManager = Hospital.getInstance().AppointANewManager(selectedDepartment);

                        if (newManager != null) {
                            // Display a success message with the name of the new manager
                            JOptionPane.showMessageDialog(
                                null,
                                "New manager appointed: " + newManager.getFirstName() + " " + newManager.getLastName(),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            // No suitable doctor found
                            JOptionPane.showMessageDialog(
                                null,
                                "Failed to appoint a new manager. No suitable doctor found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        // Handle any exceptions that occur during the appointment process
                        JOptionPane.showMessageDialog(
                            null,
                            "An error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }
}
