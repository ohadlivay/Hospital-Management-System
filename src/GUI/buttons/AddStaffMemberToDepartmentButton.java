package GUI.buttons;

import javax.swing.*;
import model.Department;
import model.Doctor;
import model.Nurse;
import model.StaffMember;
import control.Hospital;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddStaffMemberToDepartmentButton extends ActionButton<AddStaffMemberToDepartmentButton.StaffDepartmentInputData>  implements Serializable{

    private List<StaffMember> staffMembers;
    private List<Department> departments;

    public AddStaffMemberToDepartmentButton() {
        super("Add Staff Member to Department");
        
        this.staffMembers = getStaffMembersList(); // Fetch the list of staff members
        this.departments = getDepartmentsList(); // Fetch the list of departments
    }


    // Inner class to hold the input data for staff member and department
    public static class StaffDepartmentInputData {
        StaffMember staffMember;
        Department department;

        public StaffDepartmentInputData(StaffMember staffMember, Department department) {
            this.staffMember = staffMember;
            this.department = department;
        }
    }

    @Override
    protected StaffDepartmentInputData getInput() {
        // Create combobox for staff members
        JComboBox<StaffMember> staffMemberComboBox = new JComboBox<>(staffMembers.toArray(new StaffMember[0]));
        staffMemberComboBox.setPreferredSize(new Dimension(200, 25));

        // Create combobox for departments
        JComboBox<Department> departmentComboBox = new JComboBox<>(departments.toArray(new Department[0]));
        departmentComboBox.setPreferredSize(new Dimension(200, 25));

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Select Staff Member:", staffMemberComboBox,
            "Select Department:", departmentComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add Staff Member to Department", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            StaffMember selectedStaffMember = (StaffMember) staffMemberComboBox.getSelectedItem();
            Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();
            return new StaffDepartmentInputData(selectedStaffMember, selectedDepartment);
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(StaffDepartmentInputData input) {
        try {
            // Check if the staff member is already in the department
            if (input.department.getStaffMembersList().contains(input.staffMember)) {
                throw new ObjectAlreadyExistsException(input.staffMember, input.department.getClass().getSimpleName());
            }

            if (input.staffMember instanceof Doctor) {
                // Add the doctor to the department
                return Hospital.getInstance().addDoctorToDepartment(input.department, (Doctor) input.staffMember);
            } else if (input.staffMember instanceof Nurse) {
                // Add the nurse to the department
                return Hospital.getInstance().addNurseToDepartment(input.department, (Nurse) input.staffMember);
            } else {
                showErrorMessage("Selected staff member is not a doctor or nurse.");
                return false;
            }
        } catch (NullPointerException e) {
            showErrorMessage("Staff member or department cannot be null.");
            return false;
        } catch (ObjectAlreadyExistsException e) {
            showWarningMessage("This staff member is already in the department.");
            return false;
        } catch (Exception e) {
            showErrorMessage("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    // Method to fetch the list of staff members from the hospital
    private List<StaffMember> getStaffMembersList() {
//    	return new ArrayList<StaffMember>();
        return new ArrayList<>(Hospital.getInstance().getStaffMembers().values());
    }

 // Method to fetch the list of departments from the hospital
    private List<Department> getDepartmentsList() {
//    	return new ArrayList<Department>();

        return new ArrayList<>(Hospital.getInstance().getDepartments().values());
    }

}
