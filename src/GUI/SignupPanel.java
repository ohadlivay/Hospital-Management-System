package GUI;

import javax.swing.*;
import GUI.buttons.MyJButton;
import control.Hospital;
import exceptions.*;
import model.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The SignupPanel class is a JPanel that provides a UI for staff members to sign up 
 * by selecting their name and setting a password. It includes input validation 
 * and error handling for the signup process.
 */
public class SignupPanel extends JPanel implements Serializable {

    private ShortToStringComboBox<StaffMember> staffMemberComboBox;

    public SignupPanel(HospitalSystem parent) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel staffMemberLabel = new JLabel("Staff Member:");
        JLabel passwordLabel = new JLabel("Password:");

        List<StaffMember> staffMembersList = Hospital.getInstance().getStaffMembers().values()
                .stream().collect(Collectors.toList());
        ShortToStringComboBox<StaffMember> staffMemberComboBox = new ShortToStringComboBox<>(staffMembersList.toArray(new StaffMember[0]));
        this.staffMemberComboBox = staffMemberComboBox;
        staffMemberComboBox.setPreferredSize(new Dimension(200, 20));

        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(staffMemberLabel, gbc);

        gbc.gridx = 1;
        add(staffMemberComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton infoButton = new JButton(new ImageIcon("info_icon.png"));
        infoButton.setPreferredSize(new Dimension(20, 20));
        infoButton.setToolTipText("Password must be between 6 and 30 characters long and cannot be blank.");
        
        infoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Password must meet the following criteria:\n" +
                    "1. Must be between 6 and 30 characters long.\n" +
                    "2. Cannot be blank.",
                    "Password Criteria",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(infoButton, gbc);

        MyJButton signupButton = new MyJButton("Submit");
        MyJButton backButton = new MyJButton("Back");
        backButton.addActionListener(e -> parent.goBack());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(signupButton, gbc);

        gbc.gridx = 1;
        add(backButton, gbc);

        signupButton.addActionListener(e -> {
            StaffMember selectedStaffMember = (StaffMember) staffMemberComboBox.getSelectedItem();
            String password = new String(passwordField.getPassword());

            try {
                if (selectedStaffMember != null) {
                    selectedStaffMember.signUpUser(selectedStaffMember, password);
                    JOptionPane.showMessageDialog(parent, "Signup Successful!");
                    parent.switchPanel("Landing");
                } else {
                    JOptionPane.showMessageDialog(parent, "Please select a staff member", "Signup Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (InvalidPasswordException ex) {
                JOptionPane.showMessageDialog(parent, "Invalid password! Password must be between 6 and 30 characters long and cannot be blank.", "Signup Error", JOptionPane.ERROR_MESSAGE);
            } catch (UserIsAlreadySignedUp ex) {
                JOptionPane.showMessageDialog(parent, "This user is already signed up.", "Signup Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "An unexpected error occurred: " + ex.getMessage(), "Signup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void refreshDoctorsList() {
        staffMemberComboBox.removeAllItems();

        for (StaffMember staffie : Hospital.getInstance().getStaffMembers().values()) {
            staffMemberComboBox.addItem(staffie);
        }
    }
}
