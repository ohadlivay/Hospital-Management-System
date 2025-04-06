package GUI.buttons;

import javax.swing.*;

import java.awt.Dimension;
import java.io.Serializable;

import enums.*;
import model.*;
import exceptions.*;
import control.*;

public class AddDepartmentButton extends ActionButton<AddDepartmentButton.DepartmentInputData> implements Serializable {

    public AddDepartmentButton() {
        super("Add Department");
    }

    // Inner class to hold the input data for the department
    public static class DepartmentInputData {
        int number;
        String name;
        String location;
        Specialization specialization;

        public DepartmentInputData(int number, String name, String location, Specialization specialization) {
            this.number = number;
            this.name = name;
            this.location = location;
            this.specialization = specialization;
        }
    }

    @Override
    protected DepartmentInputData getInput() {
        int departmentNumber = Hospital.getInstance().getNextPKNumber();

        // Create input fields for the department attributes
        JTextField nameField = new JTextField(20);
        JTextField locationField = new JTextField(20);
        JComboBox<Specialization> specializationComboBox = new JComboBox<>(Specialization.values());
        specializationComboBox.setPreferredSize(new Dimension(200, 25));

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Department Name:", nameField,
            "Location:", locationField,
            "Specialization:", specializationComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add New Department", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String location = locationField.getText();
            Specialization specialization = (Specialization) specializationComboBox.getSelectedItem();

            if(name.trim().equals("") || location.trim().equals("")) {
            	throw new EmptyInputException();
            }
            // Return the input data if valid
            return new DepartmentInputData(departmentNumber, name, location, specialization);
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(DepartmentInputData input) throws ObjectAlreadyExistsException {
        // Create a new Department object using the input data and provide null as the manager
        Department newDepartment = new Department(input.number, input.name, null, input.location, input.specialization);

        // Process the department creation by adding it to the hospital
        return processDepartmentCreation(newDepartment);
    }

    // Example method to handle the department creation (this method can take Hospital as a parameter)
    private boolean processDepartmentCreation(Department department) throws ObjectAlreadyExistsException {

        try{
        	Hospital.getInstance().AppointANewManager(department); 
        } catch (NullPointerException e) {
            showWarningMessage("No suitable manager found, creating department without a m");
        }
        return Hospital.getInstance().addDepartment(department);
    }
}
