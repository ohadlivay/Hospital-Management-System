package GUI;

import enums.Settings;
import exceptions.ObjectAlreadyExistsException;
import generator.DepartmentGenerator;
import generator.DoctorGenerator;
import generator.MedicalProblemGenerator;
import generator.MedicationGenerator;
import generator.NurseGenerator;
import generator.PatientGenerator;
import generator.TreatmentGenerator;
import generator.VisitGenerator;
import control.SettingsManager;
import control.Hospital;
import model.Doctor;
import model.Nurse;
import model.MedicalProblem;
import model.Patient;
import model.Department;
import model.Treatment;
import model.Visit;
import model.Medication;

import javax.swing.*;

import GUI.buttons.AddXToYButton;
import GUI.buttons.MyJButton;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SettingsPanel extends BasePanel implements Serializable {

    public SettingsPanel(HospitalSystem parent) {
        super(parent);

        // Set up UI for managing settings and generating data
        setupUI();
    }

    // Create UI components for toggling settings and generating data
    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create checkboxes for settings
        JCheckBox testModeCheckbox = createSettingCheckbox("Test Mode", Settings.TESTMODE);

        // Add checkbox to the panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.add(testModeCheckbox);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some spacing
        add(settingsPanel);

        // Create and add buttons to generate data
        JPanel generateDataPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns with spacing
        generateDataPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        MyJButton generateDoctorButton = new MyJButton("Generate Doctor");
        MyJButton generateNurseButton = new MyJButton("Generate Nurse");
        MyJButton generateMedicalProblemButton = new MyJButton("Generate Medical Problem");
        MyJButton generatePatientButton = new MyJButton("Generate Patient");
        MyJButton generateDepartmentButton = new MyJButton("Generate Department");
        MyJButton generateMedicationButton = new MyJButton("Generate Medication");
        MyJButton generateTreatmentButton = new MyJButton("Generate Treatment");
        MyJButton generateVisitButton = new MyJButton("Generate Visit");

        MyJButton randomAddXToYButton = new MyJButton("Random Add X to Y");

        // Add action listeners for buttons
        generateDoctorButton.addActionListener(e -> generateAndAddDoctor());
        generateNurseButton.addActionListener(e -> generateAndAddNurse());
        generateMedicalProblemButton.addActionListener(e -> generateAndAddMedicalProblem());
        generatePatientButton.addActionListener(e -> generateAndAddPatient());
        generateDepartmentButton.addActionListener(e -> generateAndAddDepartment());
        generateMedicationButton.addActionListener(e -> generateAndAddMedication());
        generateTreatmentButton.addActionListener(e -> generateAndAddTreatment());
        generateVisitButton.addActionListener(e -> generateAndAddVisit());
        randomAddXToYButton.addActionListener(e -> randomAddXToY());

        // Add buttons to the grid layout panel
        generateDataPanel.add(generateDoctorButton);
        generateDataPanel.add(generateNurseButton);
        generateDataPanel.add(generateMedicalProblemButton);
        generateDataPanel.add(generatePatientButton);
        generateDataPanel.add(generateDepartmentButton);
        generateDataPanel.add(generateMedicationButton);
        generateDataPanel.add(generateTreatmentButton);
        generateDataPanel.add(randomAddXToYButton);
        generateDataPanel.add(generateVisitButton);

        
        // Add the grid layout panel to the main panel
        add(generateDataPanel);
    }

    // Helper method to create a checkbox for a setting
    private JCheckBox createSettingCheckbox(String label, Settings setting) {
        JCheckBox checkBox = new JCheckBox(label, SettingsManager.getSetting(setting)); // Get the value from SettingsManager
        checkBox.addActionListener(e -> {
            // Update the setting in the SettingsManager when the checkbox is toggled
            SettingsManager.setSetting(setting, checkBox.isSelected());
        });
        return checkBox;
    }

    // Method to generate and add a doctor to the hospital system
    private void generateAndAddDoctor() {
        Doctor newDoctor = DoctorGenerator.generateRandomDoctor();
        Hospital.getInstance().addDoctor(newDoctor);
        JOptionPane.showMessageDialog(this, "Doctor " + newDoctor.getFirstName() + " " + newDoctor.getLastName() + " has been added to the hospital.", "Doctor Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to generate and add a nurse to the hospital system
    private void generateAndAddNurse() {
        Nurse newNurse = NurseGenerator.generateRandomNurse();
        Hospital.getInstance().addNurse(newNurse);
        JOptionPane.showMessageDialog(this, "Nurse " + newNurse.getFirstName() + " " + newNurse.getLastName() + " has been added to the hospital.", "Nurse Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to generate and add a medical problem to the hospital system
    private void generateAndAddMedicalProblem() {
        try {
            MedicalProblem newMedicalProblem = MedicalProblemGenerator.generateRandomMedicalProblem();
            Hospital.getInstance().addMedicalProblem(newMedicalProblem);
            JOptionPane.showMessageDialog(this, "Medical Problem " + newMedicalProblem.getName() + " has been added to the hospital.", "Medical Problem Generated", JOptionPane.INFORMATION_MESSAGE);
        } catch (ObjectAlreadyExistsException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to generate and add a patient to the hospital system
    private void generateAndAddPatient() {
        Patient newPatient = PatientGenerator.generateRandomPatient();
        Hospital.getInstance().addPatient(newPatient);
        JOptionPane.showMessageDialog(this, "Patient " + newPatient.getFirstName() + " " + newPatient.getLastName() + " has been added to the hospital.", "Patient Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to generate and add a department to the hospital system
    private void generateAndAddDepartment() {
        Department newDepartment = DepartmentGenerator.generateRandomDepartment();
        Hospital.getInstance().addDepartment(newDepartment);
        JOptionPane.showMessageDialog(this, "Department " + newDepartment.getName() + " has been added to the hospital.", "Department Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to generate and add a medication to the hospital system
    private void generateAndAddMedication() {
        Medication newMedication = MedicationGenerator.generateRandomMedication();
        Hospital.getInstance().addMedication(newMedication);
        JOptionPane.showMessageDialog(this, "Medication " + newMedication.getName() + " has been added to the hospital.", "Medication Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to generate and add a treatment to the hospital system
    private void generateAndAddTreatment() {
        Treatment newTreatment = TreatmentGenerator.generateRandomTreatment();
        Hospital.getInstance().addTreatment(newTreatment);
        JOptionPane.showMessageDialog(this, "Treatment " + newTreatment.getDescription() + " has been added to the hospital.", "Treatment Generated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateAndAddVisit() {
        try {
            Visit newVisit = VisitGenerator.generateRandomVisit();
            Hospital.getInstance().addVisit(newVisit);
            JOptionPane.showMessageDialog(this, "Visit for patient " + newVisit.getPatient().getFirstName() + " " + newVisit.getPatient().getLastName() + " has been added to the hospital.", "Visit Generated", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, "Failed to generate visit: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    // Method to randomly add X to Y
    private void randomAddXToY() {
        Random random = new Random();
        AddXToYButton addXToYButton = new AddXToYButton();
        
        // Define possible X and Y combinations
        String[] xOptions = {"Doctor", "Nurse", "Medical Problem", "Treatment", "Visit", "Medication", "Department"};
        String selectedAdd = xOptions[random.nextInt(xOptions.length)];
        String selectedTo = "";

        switch (selectedAdd) {
            case "Doctor":
                selectedTo = random.nextBoolean() ? "Treatment" : "Department";
                break;
            case "Nurse":
                selectedTo = "Department";
                break;
            case "Medical Problem":
                selectedTo = random.nextBoolean() ? "Visit" : "Treatment";
                break;
            case "Treatment":
                selectedTo = random.nextBoolean() ? "Visit" : "Medical Problem";
                break;
            case "Visit":
                selectedTo = "Patient";
                break;
            case "Medication":
                selectedTo = "Treatment";
                break;
            case "Department":
                selectedTo = random.nextBoolean() ? "Doctor" : random.nextBoolean() ? "Nurse" : "Medical Problem";
                break;
        }
        
        
        
        // Generate random X and Y objects based on the selectedAdd and selectedTo
        Object selectedX = generateRandomX(selectedAdd);
        Object selectedY = generateRandomY(selectedTo);

        if (selectedX != null && selectedY != null) {
            addXToYButton.addXToY(selectedX, selectedY, selectedAdd, selectedTo);
        } else {
            JOptionPane.showMessageDialog(this, "Action failed. make sure all lists are populated.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to generate random X
    private Object generateRandomX(String selectedAdd) {
        Hospital hospital = Hospital.getInstance();
        Random random = new Random();

        switch (selectedAdd) {
            case "Doctor":
                List<Doctor> doctors = hospital.getAllDoctors().stream().collect(Collectors.toList());
                return !doctors.isEmpty() ? doctors.get(random.nextInt(doctors.size())) : null;
            case "Nurse":
                List<Nurse> nurses = hospital.getAllNurses().stream().collect(Collectors.toList());
                return !nurses.isEmpty() ? nurses.get(random.nextInt(nurses.size())) : null;
            case "Medical Problem":
                List<MedicalProblem> medicalProblems = hospital.getMedicalProblems().values().stream().collect(Collectors.toList());
                return !medicalProblems.isEmpty() ? medicalProblems.get(random.nextInt(medicalProblems.size())) : null;
            case "Treatment":
                List<Treatment> treatments = hospital.getTreatments().values().stream().collect(Collectors.toList());
                return !treatments.isEmpty() ? treatments.get(random.nextInt(treatments.size())) : null;
            case "Visit":
                List<Visit> visits = hospital.getVisits().values().stream().collect(Collectors.toList());
                return !visits.isEmpty() ? visits.get(random.nextInt(visits.size())) : null;
            case "Medication":
                List<Medication> medications = hospital.getMedications().values().stream().collect(Collectors.toList());
                return !medications.isEmpty() ? medications.get(random.nextInt(medications.size())) : null;
            case "Department":
                List<Department> departments = hospital.getDepartments().values().stream().collect(Collectors.toList());
                return !departments.isEmpty() ? departments.get(random.nextInt(departments.size())) : null;
        }
        return null;
    }

    // Helper method to generate random Y
    private Object generateRandomY(String selectedTo) {
        Hospital hospital = Hospital.getInstance();
        Random random = new Random();

        switch (selectedTo) {
            case "Treatment":
                List<Treatment> treatments = hospital.getTreatments().values().stream().collect(Collectors.toList());
                return !treatments.isEmpty() ? treatments.get(random.nextInt(treatments.size())) : null;
            case "Department":
                List<Department> departments = hospital.getDepartments().values().stream().collect(Collectors.toList());
                return !departments.isEmpty() ? departments.get(random.nextInt(departments.size())) : null;
            case "Visit":
                List<Visit> visits = hospital.getVisits().values().stream().collect(Collectors.toList());
                return !visits.isEmpty() ? visits.get(random.nextInt(visits.size())) : null;
            case "Patient":
                List<Patient> patients = hospital.getPatients().values().stream().collect(Collectors.toList());
                return !patients.isEmpty() ? patients.get(random.nextInt(patients.size())) : null;
            case "Medical Problem":
                List<MedicalProblem> medicalProblems = hospital.getMedicalProblems().values().stream().collect(Collectors.toList());
                return !medicalProblems.isEmpty() ? medicalProblems.get(random.nextInt(medicalProblems.size())) : null;
            case "Doctor":
                List<Doctor> doctors = hospital.getStaffMembers().values().stream()
                        .filter(Doctor.class::isInstance)
                        .map(Doctor.class::cast)
                        .collect(Collectors.toList());
                return !doctors.isEmpty() ? doctors.get(random.nextInt(doctors.size())) : null;
            case "Nurse":
                List<Nurse> nurses = hospital.getStaffMembers().values().stream()
                        .filter(Nurse.class::isInstance)
                        .map(Nurse.class::cast)
                        .collect(Collectors.toList());
                return !nurses.isEmpty() ? nurses.get(random.nextInt(nurses.size())) : null;
            default:
                // In case of an unknown relationship, return null
                return null;
        }
    }

}
