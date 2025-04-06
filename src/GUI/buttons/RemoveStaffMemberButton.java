package GUI.buttons;

import javax.swing.*;

import GUI.HospitalSystem;
import control.Hospital;
import model.Doctor;
import model.Nurse;
import model.StaffMember;
import exceptions.ObjectDoesNotExist;
import java.io.Serializable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RemoveStaffMemberButton extends ActionButton<RemoveStaffMemberButton.StaffMemberInputData>  implements Serializable{

    private final HospitalSystem parent;

    public RemoveStaffMemberButton(String text, HospitalSystem parent) {
        super(text);
        this.parent = parent;
    }

    // Inner class to hold the input data for the staff member
    public static class StaffMemberInputData {
        StaffMember selectedStaffMember;

        public StaffMemberInputData(StaffMember selectedStaffMember) {
            this.selectedStaffMember = selectedStaffMember;
        }
    }

    @Override
    protected StaffMemberInputData getInput() {
        // Fetch all staff members from the Hospital instance
    	List<StaffMember> staffMembers = new ArrayList<>(Hospital.getInstance().getStaffMembers().values());

        if (staffMembers.isEmpty()) {
            showWarningMessage("No staff members available to remove.");
            return null;
        }

        JComboBox<StaffMember> staffMemberComboBox = new JComboBox<>(staffMembers.toArray(new StaffMember[0]));
        staffMemberComboBox.setPreferredSize(new Dimension(200, 25));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a Staff Member to Remove:"), BorderLayout.NORTH);
        panel.add(staffMemberComboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Remove Staff Member",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Check if the user selected OK and made a valid selection
        if (result == JOptionPane.OK_OPTION && staffMemberComboBox.getSelectedItem() != null) {
            return new StaffMemberInputData((StaffMember) staffMemberComboBox.getSelectedItem());
        } else {
            return null;  // Return null if no valid selection is made
        }
    }

    @Override
    protected boolean processInput(StaffMemberInputData input) throws Exception {
        if (input == null || input.selectedStaffMember == null) {
            throw new NullPointerException("No staff member selected.");
        }

        // Determine if the staff member is a doctor or nurse and remove accordingly
        boolean success;
        if (input.selectedStaffMember instanceof Doctor) {
            success = Hospital.getInstance().removeDoctor((Doctor) input.selectedStaffMember);
        } else if (input.selectedStaffMember instanceof Nurse) {
            success = Hospital.getInstance().removeNurse((Nurse) input.selectedStaffMember);
        } else {
            throw new IllegalArgumentException("Unknown staff member type.");
        }

        if (!success) {
            throw new ObjectDoesNotExist(input.selectedStaffMember.getId(), input.selectedStaffMember.getClass().getSimpleName(), "Hospital");
        }

        return true;
    }
}
