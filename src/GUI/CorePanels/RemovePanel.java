package GUI.CorePanels;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

import GUI.BasePanel;
import GUI.HospitalSystem;
import GUI.buttons.*;

public class RemovePanel extends BasePanel  implements Serializable{

    public RemovePanel(HospitalSystem parent) {
        super(parent); // Ensure BackPanel constructor is called

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 3, 10, 10)); // 5 rows, 3 columns, with spacing

        // Create buttons for different removal actions
        JButton removeDepartmentButton = new RemoveDepartmentButton("Remove Department", parent);
        JButton removeMedicalProblemButton = new RemoveMedicalProblemButton("Remove Medical Problem", parent);
        JButton removeMedicationButton = new RemoveMedicationButton("Remove Medication", parent);
        JButton removeStaffMemberButton = new RemoveStaffMemberButton("Remove Staff Member", parent);
        JButton removePatientButton = new RemovePatientButton("Remove Patient", parent);
        JButton removeTreatmentButton = new RemoveTreatmentButton("Remove Treatment", parent);
        JButton removeVisitButton = new RemoveVisitButton("Remove Visit", parent);

        // Add buttons to the panel
        buttonPanel.add(removeDepartmentButton);
        buttonPanel.add(removeMedicalProblemButton);
        buttonPanel.add(removeMedicationButton);
        buttonPanel.add(removeStaffMemberButton);
        buttonPanel.add(removePatientButton);
        buttonPanel.add(removeTreatmentButton);
        buttonPanel.add(removeVisitButton);

        // Add the button panel to the main panel
        add(buttonPanel);
    }
}
