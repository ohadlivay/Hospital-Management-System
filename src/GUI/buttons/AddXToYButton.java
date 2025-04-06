package GUI.buttons;

import javax.swing.*;
import control.Hospital;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;
import model.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AddXToYButton is a custom button that allows the user to add one type of object (X) 
 * to another type of object (Y) within the hospital management system. The button 
 * dynamically populates lists of available options for both X and Y based on user 
 * selections and performs the appropriate operation to associate X with Y. */

public class AddXToYButton extends MyJButton  implements Serializable{

    public AddXToYButton() {
        super("Add X To Y");
        addActionListener(e -> openAddXToYDialog());
    }

    private void openAddXToYDialog() {
        // Define the options for "Add" and "To" lists
        String[] addOptions = {"Select", "Doctor", "Nurse", "Medical Problem", "Treatment", "Visit", "Medication", "Department"};
        JComboBox<String> addComboBox = new JComboBox<>(addOptions);
        addComboBox.setPreferredSize(new Dimension(150, 25));
        
        // Set initial selection to -1 (no selection)
        addComboBox.setSelectedIndex(-1);

        JLabel toLabel = new JLabel("To:");
        JComboBox<String> toComboBox = new JComboBox<>();
        toComboBox.setPreferredSize(new Dimension(150, 25));

        // Panel for the initial selection
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        selectionPanel.add(new JLabel("Add:"));
        selectionPanel.add(addComboBox);
        selectionPanel.add(new JLabel("To:"));
        selectionPanel.add(toComboBox);

        // Panel for the lists of X and Y
        JPanel listsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JComboBox<Object> listXComboBox = new JComboBox<>();
        JComboBox<Object> listYComboBox = new JComboBox<>();

        // Set preferred sizes for X and Y comboboxes to make them wider
        listXComboBox.setPreferredSize(new Dimension(300, 25));
        listYComboBox.setPreferredSize(new Dimension(300, 25));

        listsPanel.add(new JLabel("Select from X:"));
        listsPanel.add(listXComboBox);
        listsPanel.add(new JLabel("Select from Y:"));
        listsPanel.add(listYComboBox);

        // Combine both panels into one
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(selectionPanel, BorderLayout.NORTH);
        mainPanel.add(listsPanel, BorderLayout.CENTER);

        // Set up the dynamic behavior
        addComboBox.addActionListener(e -> {
            String selectedAdd = (String) addComboBox.getSelectedItem();
            if (selectedAdd != null && !selectedAdd.equals("Select")) {
                updateToComboBox(selectedAdd, toComboBox);
                updateListXComboBox(selectedAdd, listXComboBox);

                // Set the first item of toComboBox as the default selected item
                if (toComboBox.getItemCount() > 0) {
                    toComboBox.setSelectedIndex(0);
                }
            }
        });

        toComboBox.addActionListener(e -> {
            String selectedAdd = (String) addComboBox.getSelectedItem();
            String selectedTo = (String) toComboBox.getSelectedItem();

            // Check if selectedTo is not null before attempting to update the list
            if (selectedTo != null && !selectedTo.equals("Select")) {
                updateListYComboBox(selectedAdd, selectedTo, listYComboBox);
            }
        });

        int result = JOptionPane.showConfirmDialog(null, mainPanel, "Add X to Y", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Get selected X and Y objects
            Object selectedX = listXComboBox.getSelectedItem();
            Object selectedY = listYComboBox.getSelectedItem();

            // Process the add logic using Y's methods
            if (selectedX != null && selectedY != null) {
                addXToY(selectedX, selectedY, (String) addComboBox.getSelectedItem(), (String) toComboBox.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(null, "Please select both X and Y items.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void addXToY(Object x, Object y, String selectedAdd, String selectedTo) {
        try {
            switch (selectedAdd + " to " + selectedTo) {
                case "Doctor to Treatment":
                    ((Treatment) y).addDoctor((Doctor) x);
                    break;
                case "Doctor to Department":
                	Hospital.getInstance().addDoctorToDepartment((Department) y,(Doctor) x);
                    break;
                case "Nurse to Department":
                	Hospital.getInstance().addNurseToDepartment((Department) y,(Nurse) x);
                	break;
                case "Medical Problem to Visit":
                    ((Visit) y).addMedicalProblem((MedicalProblem) x);
                    break;
                case "Medical Problem to Treatment":
                    Hospital.getInstance().addTreatmentToMedicalProblem((Treatment) y, (MedicalProblem) x);
                    break;
                case "Treatment to Visit":
                    ((Visit) y).addTreatment((Treatment) x);
                    break;
                case "Treatment to Medical Problem":
                    Hospital.getInstance().addTreatmentToMedicalProblem((Treatment) x, (MedicalProblem) y);
                    break;
                case "Visit to Patient":
                    ((Patient) y).addVisit((Visit) x);
                    break;
                case "Medication to Treatment":
                    ((Treatment) y).addMedication((Medication) x);
                    break;
                case "Department to Doctor":
                	Hospital.getInstance().addDoctorToDepartment((Department) x,(Doctor) y);
                    break;
                case "Department to Nurse":
                	Hospital.getInstance().addNurseToDepartment((Department) x,(Nurse) y);
                    break;
                case "Department to Medical Problem":
                	((MedicalProblem) y).setDepartment((Department) x);
                	break;
                default:
                    throw new IllegalArgumentException("Invalid combination of X and Y types");
            }
            JOptionPane.showMessageDialog(null, selectedAdd + " added successfully to " + selectedTo);
        } catch (ObjectAlreadyExistsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while adding " + selectedAdd + " to " + selectedTo + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateToComboBox(String selectedAdd, JComboBox<String> toComboBox) {
        toComboBox.removeAllItems();
        switch (selectedAdd) {
            case "Doctor":
                toComboBox.addItem("Treatment");
                toComboBox.addItem("Department");
                break;
            case "Nurse":
                toComboBox.addItem("Department");
                break;
            case "Medical Problem":
                toComboBox.addItem("Visit");
                toComboBox.addItem("Treatment");
                break;
            case "Treatment":
                toComboBox.addItem("Visit");
                toComboBox.addItem("Medical Problem");
                break;
            case "Visit":
                toComboBox.addItem("Patient");
                break;
            case "Medication":
                toComboBox.addItem("Treatment");
                break;
            case "Department":
                toComboBox.addItem("Doctor");
                toComboBox.addItem("Nurse");
                toComboBox.addItem("Medical Problem");
                break;
        }
    }
    //Update according to user choice
    private void updateListXComboBox(String selectedAdd, JComboBox<Object> listXComboBox) {
        listXComboBox.removeAllItems();
        Hospital hospital = Hospital.getInstance();
        
        switch (selectedAdd) {
            case "Doctor":
                List<Doctor> doctors = hospital.getStaffMembers().values().stream()
                        .filter(Doctor.class::isInstance)
                        .map(Doctor.class::cast)
                        .collect(Collectors.toList());
                for (Doctor doctor : doctors) {
                    listXComboBox.addItem(doctor);
                }
                break;
            case "Nurse":
                List<Nurse> nurses = hospital.getStaffMembers().values().stream()
                        .filter(Nurse.class::isInstance)
                        .map(Nurse.class::cast)
                        .collect(Collectors.toList());
                for (Nurse nurse : nurses) {
                    listXComboBox.addItem(nurse);
                }
                break;
            case "Medical Problem":
                for (MedicalProblem problem : hospital.getMedicalProblems().values()) {
                    listXComboBox.addItem(problem);
                }
                break;
            case "Treatment":
                for (Treatment treatment : hospital.getTreatments().values()) {
                    listXComboBox.addItem(treatment);
                }
                break;
            case "Visit":
                for (Visit visit : hospital.getVisits().values()) {
                    listXComboBox.addItem(visit);
                }
                break;
            case "Medication":
                for (Medication medication : hospital.getMedications().values()) {
                    listXComboBox.addItem(medication);
                }
                break;
            case "Department":
                for (Department department : hospital.getDepartments().values()) {
                    listXComboBox.addItem(department);
                }
                break;
        }
    }

    private void updateListYComboBox(String selectedAdd, String selectedTo, JComboBox<Object> listYComboBox) {
        listYComboBox.removeAllItems();
        Hospital hospital = Hospital.getInstance();

        switch (selectedTo) {
            case "Treatment":
                for (Treatment treatment : hospital.getTreatments().values()) {
                    listYComboBox.addItem(treatment);
                }
                break;
            case "Department":
                for (Department department : hospital.getDepartments().values()) {
                    listYComboBox.addItem(department);
                }
                break;
            case "Visit":
                for (Visit visit : hospital.getVisits().values()) {
                    listYComboBox.addItem(visit);
                }
                break;
            case "Patient":
                for (Patient patient : hospital.getPatients().values()) {
                    listYComboBox.addItem(patient);
                }
                break;
            case "Medical Problem":
                for (MedicalProblem problem : hospital.getMedicalProblems().values()) {
                    listYComboBox.addItem(problem);
                }
                break;
            case "Doctor":
                List<Doctor> doctors = hospital.getStaffMembers().values().stream()
                        .filter(Doctor.class::isInstance)
                        .map(Doctor.class::cast)
                        .collect(Collectors.toList());
                for (Doctor doctor : doctors) {
                    listYComboBox.addItem(doctor);
                }
                break;
            case "Nurse":
                List<Nurse> nurses = hospital.getStaffMembers().values().stream()
                        .filter(Nurse.class::isInstance)
                        .map(Nurse.class::cast)
                        .collect(Collectors.toList());
                for (Nurse nurse : nurses) {
                    listYComboBox.addItem(nurse);
                }
                break;
        }
    }
}
