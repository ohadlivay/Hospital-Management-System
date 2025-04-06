package GUI.CorePanels;

import javax.swing.*;
import GUI.BasePanel;
import GUI.HospitalSystem;
import GUI.UserType;
import GUI.UserTypeAwarePanel;
import GUI.buttons.*;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class AddPanel extends BasePanel implements UserTypeAwarePanel , Serializable{

    private final JPanel assignPanel;
    private UserType userType;
    private JTabbedPane tabbedPane;

    public AddPanel(HospitalSystem parent) {
        super(parent);

        tabbedPane = new JTabbedPane();
        assignPanel = new JPanel();
        assignPanel.setLayout(new BoxLayout(assignPanel, BoxLayout.Y_AXIS));

        add(tabbedPane);

        reloadPanel(); // Initial load
    }

    private void reloadPanel() {
        tabbedPane.removeAll(); // Clear all tabs
        tabbedPane.addTab("Create", createScrollablePanel(getCreateButtons()));
        if(userType != UserType.NURSE) { //nurses have no assign powers by definition of ex3
            tabbedPane.addTab("Assign", createScrollablePanel(getAssignButtons()));
        }

        revalidate();  // Revalidate the entire panel
        repaint();     // Repaint to reflect the changes
    }

    private List<JButton> getCreateButtons() {
        List<JButton> buttons = new ArrayList<>();

        if (userType != null) {
            switch (userType) {
                case ADMIN:
                    buttons.add(new AddVisitButton());
                    buttons.add(new AddPatientButton());
                    buttons.add(new AddMedicationButton());
                    buttons.add(new AddDepartmentButton());
                    buttons.add(new AddDoctorButton());
                    buttons.add(new ChooseMedicalProblemTypeButton());
                    buttons.add(new AddNurseButton());
                    buttons.add(new AddTreatmentButton());
                    break;
                case DOCTOR:
                    buttons.add(new AddTreatmentButton());
                    buttons.add(new AddMedicationButton());
                    buttons.add(new ChooseMedicalProblemTypeButton());
                    break;
                case NURSE:
                    buttons.add(new AddVisitButton());
                    buttons.add(new AddPatientButton());
                    break;
            }
        }

        return buttons;
    }

    private List<JButton> getAssignButtons() {
        List<JButton> buttons = new ArrayList<>();

        if (userType != null) {
            switch (userType) {
                case ADMIN:
                    buttons.add(new AddXToYButton());

                    
                    break;
                case DOCTOR:
                    buttons.add(new AddMedicationToTreatmentButton());
                    buttons.add(new AddTreatmentToMedicalProblemButton());
                    buttons.add(new AddMedicalProblemToVisitButton());
                    buttons.add(new AddTreatmentToVisitButton());

                    break;
                // NURSE does not have access to "Assign" buttons
            }
        }

        return buttons;
    }

    private JScrollPane createScrollablePanel(List<JButton> buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(300, 30);

        for (JButton button : buttons) {
            button.setMaximumSize(buttonSize);
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    @Override
    public void updateUserType(UserType userType) {
        this.userType = userType;
        reloadPanel();  // Rebuild the panel when the user type changes
    }
}
