package GUI.buttons;

import javax.swing.*;
import control.Hospital;
import java.io.Serializable;

import java.awt.*;

public class ShowInformationButton extends MyJButton  implements Serializable{

    public ShowInformationButton(String buttonLabel) {
        super(buttonLabel);

        this.addActionListener(e -> {
            // Fetch the data from the Hospital
            int numberOfIntensiveCareStaffMembers = Hospital.getInstance().howManyIntensiveCareStaffMembers();
            double avgSalary = Hospital.getInstance().avgSalary();
            boolean compliesWithStandards = Hospital.getInstance().isCompliesWithTheMinistryOfHealthStandard();

            // Create a JPanel to display the information
            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            JLabel intensiveCareStaffLabel = new JLabel("Number of Intensive Care Staff Members: " + numberOfIntensiveCareStaffMembers);
            JLabel avgSalaryLabel = new JLabel("Average Salary: " + avgSalary);
            JLabel complianceLabel = new JLabel("Complies with Ministry of Health Standards: " + (compliesWithStandards ? "Yes" : "No"));

            // Add the labels to the panel
            infoPanel.add(intensiveCareStaffLabel);
            infoPanel.add(avgSalaryLabel);
            infoPanel.add(complianceLabel);

            // Display the panel in a dialog
            JOptionPane.showMessageDialog(
                null,
                infoPanel,
                "Hospital Information",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
