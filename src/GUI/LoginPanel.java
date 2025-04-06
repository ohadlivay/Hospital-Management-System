package GUI;

import javax.swing.*;
import GUI.buttons.MyJButton;
import control.Hospital;
import control.SettingsManager;
import enums.Settings;
import exceptions.*;
import model.*;
import java.awt.*;
import java.io.Serializable;

import java.util.Optional;

/**
 * The LoginPanel class is a JPanel that manages the login interface for the hospital system.
 * It supports both regular and test modes, with UI elements and actions tailored to each mode.
 */
public class LoginPanel extends JPanel implements Serializable {

    private boolean testMode;
    private HospitalSystem parent;

    public LoginPanel(HospitalSystem parent) {
        this.parent = parent;
        this.testMode = SettingsManager.getSetting(Settings.TESTMODE);
        setupUI();
    }

    public void rebuildUI() {
        this.testMode = SettingsManager.getSetting(Settings.TESTMODE);
        removeAll();
        setupUI();
        revalidate();
        repaint();
    }

    private void setupUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        if (testMode) {
            MyJButton adminLoginButton = new MyJButton("Login as Admin");
            MyJButton doctorLoginButton = new MyJButton("Login as Doctor");
            MyJButton nurseLoginButton = new MyJButton("Login as Nurse");

            adminLoginButton.addActionListener(e -> {
                parent.setUserType(UserType.ADMIN);
                parent.switchPanel("MainPanel");
            });

            doctorLoginButton.addActionListener(e -> {
                StaffMember doctor = null;
                for (StaffMember staffie : Hospital.getInstance().getStaffMembers().values()) {
                    if (staffie instanceof Doctor) {
                        doctor = staffie;
                        parent.setLoggedUser(doctor);
                        parent.setUserType(UserType.DOCTOR);
                        parent.switchPanel("MainPanel");
                        break;
                    }
                }
                if (doctor == null) {
                    JOptionPane.showMessageDialog(parent, "No registered doctors found!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            nurseLoginButton.addActionListener(e -> {
                StaffMember nurse = null;
                for (StaffMember staffie : Hospital.getInstance().getStaffMembers().values()) {
                    if (staffie instanceof Nurse) {
                        nurse = staffie;
                        parent.setLoggedUser(nurse);
                        parent.setUserType(UserType.NURSE);
                        parent.switchPanel("MainPanel");
                        break;
                    }
                }
                if (nurse == null) {
                    JOptionPane.showMessageDialog(parent, "No registered nurses found!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(adminLoginButton, gbc);

            gbc.gridy = 1;
            add(doctorLoginButton, gbc);

            gbc.gridy = 2;
            add(nurseLoginButton, gbc);

        } else {
            JLabel staffIdLabel = new JLabel("ID:");
            JLabel passwordLabel = new JLabel("Password:");

            JTextField staffIdField = new JTextField(20);
            JPasswordField passwordField = new JPasswordField(20);

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(staffIdLabel, gbc);

            gbc.gridx = 1;
            add(staffIdField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(passwordLabel, gbc);

            gbc.gridx = 1;
            add(passwordField, gbc);

            MyJButton loginButton = new MyJButton("Submit");
            MyJButton backButton = new MyJButton("Back");
            backButton.addActionListener(e -> parent.goBack());

            gbc.gridy = 2;
            gbc.gridx = 0;
            add(loginButton, gbc);

            gbc.gridx = 1;
            add(backButton, gbc);

            loginButton.addActionListener(e -> {
                String staffId = staffIdField.getText().trim();
                String password = new String(passwordField.getPassword());

                try {
                    if (staffId.equals("ADMIN") && password.equals("ADMIN")) {
                        parent.setUserType(UserType.ADMIN);
                        parent.switchPanel("MainPanel");
                        return;
                    }

                    int parsedStaffId = Integer.parseInt(staffId);
                    StaffMember selectedStaffMember = Hospital.getInstance().getStaffMemberById(parsedStaffId);

                    if (selectedStaffMember != null) {
                        selectedStaffMember.loginUser(selectedStaffMember.getId(), password);
                        parent.setLoggedUser(selectedStaffMember);

                        if (selectedStaffMember instanceof Doctor) {
                            parent.setUserType(UserType.DOCTOR);
                        } else if (selectedStaffMember instanceof Nurse) {
                            parent.setUserType(UserType.NURSE);
                        } else {
                            JOptionPane.showMessageDialog(parent, "Unsupported user type!", "Login Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        parent.switchPanel("MainPanel");
                    } else {
                        JOptionPane.showMessageDialog(parent, "Please enter a valid ID", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Invalid Staff ID format!", "Login Error", JOptionPane.ERROR_MESSAGE);
                } catch (UserNotRegisteredException ex) {
                    JOptionPane.showMessageDialog(parent, "User is not registered!", "Login Error", JOptionPane.ERROR_MESSAGE);
                } catch (IncorrectPasswordException ex) {
                    JOptionPane.showMessageDialog(parent, "Incorrect password!", "Login Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parent, "An unexpected error occurred: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
        SettingsManager.setSetting(Settings.TESTMODE, testMode);
    }
}
