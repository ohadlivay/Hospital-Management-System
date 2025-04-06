package GUI.CorePanels;

import javax.swing.*;
import GUI.BasePanel;
import GUI.HospitalSystem;
import GUI.UserType;
import GUI.UserTypeAwarePanel;
import GUI.buttons.*;
import GUI.buttons.PrintPatientButton;  // Import the PrintPatientButton class

import java.awt.*;
import java.io.Serializable;

public class QueryPanel extends BasePanel implements UserTypeAwarePanel, Serializable {

    private UserType userType;

    // Define all buttons as class members
    private JButton howManyVisitBeforeButton;
    private JButton countMedicationsButton;
    private JButton getNumberOfDoctorsBySpecializationButton;
    private JButton showInformationButton;
    private JButton appointANewManagerButton;
    private JButton printPatientButton; // Add PrintPatientButton

    public QueryPanel(HospitalSystem parent) {
        super(parent); // Ensure BackPanel constructor is called

        // Create a panel to hold the buttons with GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(6, 3, 44, 10)); // Updated to 6 rows, 3 columns, with spacing

        // Initialize buttons
        howManyVisitBeforeButton = new HowManyVisitBeforeButton("Visits ended before [DATE]");
        countMedicationsButton = new CountMedicationsButton("Medications with dose in [RANGE]");
        getNumberOfDoctorsBySpecializationButton = new GetNumberOfDoctorsBySpecializationButton("Doctors per Specialization");
        showInformationButton = new ShowInformationButton("Important Information");
        appointANewManagerButton = new AppointANewManagerButton("Appoint new Manager");
        printPatientButton = new PrintPatientButton(); // Initialize PrintPatientButton

        // Add all buttons to the panel initially (visibility will be managed by updateUserType)
        addButtonToPanel(buttonPanel, howManyVisitBeforeButton, 350, 40);
        addButtonToPanel(buttonPanel, countMedicationsButton, 350, 40);
        addButtonToPanel(buttonPanel, getNumberOfDoctorsBySpecializationButton, 350, 40);
        addButtonToPanel(buttonPanel, showInformationButton, 350, 40);
        addButtonToPanel(buttonPanel, appointANewManagerButton, 350, 40);
        addButtonToPanel(buttonPanel, printPatientButton, 350, 40); // Add PrintPatientButton to the panel

        // Add the button panel to the main panel
        add(buttonPanel);
    }

    /**
     * Helper method to create a button wrapper and add it to the main panel.
     */
    private void addButtonToPanel(JPanel panel, JButton button, int width, int height) {
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        button.setPreferredSize(new Dimension(width, height)); // Set preferred size for the button
        wrapperPanel.add(button);
        panel.add(wrapperPanel);
    }

    @Override
    public void updateUserType(UserType userType) {
        this.userType = userType;
        updateButtonsVisibility(); // Adjust the visibility of buttons based on the user type
    }

    private void updateButtonsVisibility() {
        if (userType == UserType.ADMIN) {
            // Admin has access to all buttons
            howManyVisitBeforeButton.setVisible(true);
            countMedicationsButton.setVisible(true);
            getNumberOfDoctorsBySpecializationButton.setVisible(true);
            showInformationButton.setVisible(true);
            appointANewManagerButton.setVisible(true);
            printPatientButton.setVisible(true); // Make PrintPatientButton visible for Admin
        } else if (userType == UserType.DOCTOR || userType == UserType.NURSE) {
            // Doctor and Nurse have access to a limited set of buttons
            howManyVisitBeforeButton.setVisible(true);
            countMedicationsButton.setVisible(true);
            showInformationButton.setVisible(true);
            getNumberOfDoctorsBySpecializationButton.setVisible(false);
            appointANewManagerButton.setVisible(false);
            printPatientButton.setVisible(true); // Make PrintPatientButton visible for Doctor/Nurse
        }
        revalidate();
        repaint();
    }
}
