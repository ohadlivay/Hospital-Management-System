package GUI.CorePanels;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;
import GUI.BasePanel;
import GUI.HospitalSystem;
import GUI.UserType;
import GUI.UserTypeAwarePanel;
import GUI.buttons.ViewAllButton;
import control.Hospital;
import model.*;

public class ViewDataPanel extends BasePanel implements UserTypeAwarePanel , Serializable{

    private UserType userType;
    private JPanel buttonPanel;

    public ViewDataPanel(HospitalSystem parent) {
        super(parent); // Ensure BackPanel constructor is called

        // Initialize the button panel
        buttonPanel = new JPanel(new GridLayout(5, 3, 10, 10)); // 5 rows, 3 columns, with spacing
        add(buttonPanel); // Add button panel to the main panel

        reloadPanel(parent);
    }

    private void reloadPanel(HospitalSystem parent) {
        buttonPanel.removeAll(); // Clear existing buttons

        // Generic ViewAllButton for Patients
        buttonPanel.add(new ViewAllButton<>(
            "View All Patients",
            new ArrayList<>(Hospital.getInstance().getPatients().values()), 
            new String[] {"ID", "First Name", "Last Name", "Birth Date", "Address", "Phone Number", "Email", "Gender", "Health Fund", "Biological Sex"},
            patient -> new Object[] {
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getAddress(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getGender(),
                patient.getHealthFund(),
                patient.getBiologicalSex()
            },
            parent
        ));

        // Add ViewAllButton for Doctors if user is ADMIN
        if (userType == UserType.ADMIN) {
            buttonPanel.add(new ViewAllButton<>(
                "View All Doctors",
                new ArrayList<>(Hospital.getInstance().getAllDoctors()), 
                new String[] {"ID", "First Name", "Last Name", "Birth Date", "Address", "Phone Number", "Email", "Gender", "Work Start Date", "Departments", "Salary", "License Number", "Finished Internship", "Specialization"},
                doctor -> new Object[] {
                    doctor.getId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getBirthDate(),
                    doctor.getAddress(),
                    doctor.getPhoneNumber(),
                    doctor.getEmail(),
                    doctor.getGender(),
                    doctor.getWorkStartDate(),
                    doctor.getDepartments().stream().map(Department::getName).collect(Collectors.joining(", ")),
                    doctor.getSalary(),
                    doctor.getLicenseNumber(),
                    doctor.isFinishInternship(),
                    doctor.getSpecialization()
                },
                parent
            ));
        }

        // Generic ViewAllButton for Injuries
        buttonPanel.add(new ViewAllButton<>(
            "View All Injuries",
            new ArrayList<>(Hospital.getInstance().getAllInjuries()), 
            new String[] {"Code", "Name", "Department", "Treatments", "Common Recovery Time", "Location"},
            injury -> new Object[] {
                injury.getCode(),
                injury.getName(),
                injury.getDepartment().getName(),
                injury.getTreatmentsList().stream().map(Treatment::getDescription).collect(Collectors.joining(", ")),
                injury.getCommonRecoveryTime(),
                injury.getLocation()
            },
            parent
        ));

        // Generic ViewAllButton for Fractures
        buttonPanel.add(new ViewAllButton<>(
            "View All Fractures",
            new ArrayList<>(Hospital.getInstance().getAllFractures()), 
            new String[] {"Code", "Name", "Department", "Treatments", "Location", "Requires Cast"},
            fracture -> new Object[] {
                fracture.getCode(),
                fracture.getName(),
                fracture.getDepartment().getName(),
                fracture.getTreatmentsList().stream().map(Treatment::getDescription).collect(Collectors.joining(", ")),
                fracture.getLocation(),
                fracture.isRequiresCast()
            },
            parent
        ));
     // Generic ViewAllButton for Departments
        buttonPanel.add(new ViewAllButton<>(
            "View All Departments",
            new ArrayList<>(Hospital.getInstance().getDepartments().values()), 
            new String[] {"Number", "Name", "Manager", "Location", "Number of Staff Members", "Specialization"},
            department -> new Object[] {
                department.getNumber(),
                department.getName(),
                department.getmanager() != null ? department.getmanager().getFirstName() + " " + department.getmanager().getLastName() : "No Manager",
                department.getLocation(),
                department.getStaffMembersList().size(), // Show number of staff members
                department.getSpecialization()
            },
            parent
        ));
     // Generic ViewAllButton for Nurses
        if (userType == UserType.ADMIN) {
	        buttonPanel.add(new ViewAllButton<>(
	            "View All Nurses",
	            new ArrayList<>(Hospital.getInstance().getAllNurses()), 
	            new String[] {"ID", "First Name", "Last Name", "Birth Date", "Address", "Phone Number", "Email", "Gender", "Work Start Date", "Departments", "Salary", "License Number"},
	            nurse -> new Object[] {
	                nurse.getId(),
	                nurse.getFirstName(),
	                nurse.getLastName(),
	                nurse.getBirthDate(),
	                nurse.getAddress(),
	                nurse.getPhoneNumber(),
	                nurse.getEmail(),
	                nurse.getGender(),
	                nurse.getWorkStartDate(),
                    nurse.getDepartments().stream().map(Department::getName).collect(Collectors.joining(", ")),
	                nurse.getSalary(),
	                nurse.getLicenseNumber()
	            },
	            parent
	        ));
        }
       
     // Generic ViewAllButton for Diseases
        buttonPanel.add(new ViewAllButton<>(
            "View All Diseases",
            new ArrayList<>(Hospital.getInstance().getAllDiseases()), 
            new String[] {"Code", "Name", "Department", "Number of Treatments", "Description"},
            disease -> new Object[] {
            	    disease.getCode(),
            	    disease.getName(),
            	    disease.getDepartment().getName(),
            	    disease.getTreatmentsList().size(),
            	    disease.getDescription()
            	},
            parent
        ));

        // Generic ViewAllButton for Medications
        buttonPanel.add(new ViewAllButton<>(
            "View All Medications",
            new ArrayList<Medication>(Hospital.getInstance().getMedications().values()), 
            new String[] {"Code", "Name", "Dosage", "Number of Doses"},
            medication -> new Object[] {
                medication.getCode(),
                medication.getName(),
                medication.getDosage(),
                medication.getNumberOfDose()
            },
            parent
        ));

        // Generic ViewAllButton for Treatments
        buttonPanel.add(new ViewAllButton<>(
            "View All Treatments",
            new ArrayList<>(Hospital.getInstance().getTreatments().values()), 
            new String[] {"Serial Number", "Description", "Medications", "Doctors", "Medical Problems"},
            treatment -> new Object[] {
                treatment.getSerialNumber(),
                treatment.getDescription(),
                treatment.getMedicationsList().stream().map(Medication::getName).collect(Collectors.joining(", ")),
                treatment.getDoctorsList().stream().map(doctor -> doctor.getFirstName() + " " + doctor.getLastName()).collect(Collectors.joining(", ")),
                treatment.getMedicalProblemsList().stream().map(MedicalProblem::getName).collect(Collectors.joining(", "))
            },
            parent
        ));

        // Generic ViewAllButton for Visits
        buttonPanel.add(new ViewAllButton<>(
            "View All Visits",
            new ArrayList<>(Hospital.getInstance().getVisits().values()), 
            new String[] {"Patient", "Start Date", "End Date", "Medical Problems", "Treatments"},
            visit -> new Object[] {
                visit.getPatient().getFirstName() + " " + visit.getPatient().getLastName(),
                visit.getStartDate(),
                visit.getEndDate(),
                visit.getMedicalProblemsList().stream().map(MedicalProblem::getName).collect(Collectors.joining(", ")),
                visit.getTreatmentsList().stream().map(Treatment::getDescription).collect(Collectors.joining(", "))
            },
            parent
        ));

        // Revalidate and repaint to update the UI
        revalidate();
        repaint();
    }

    @Override
    public void updateUserType(UserType userType) {
        this.userType = userType;
        reloadPanel(getParentHospitalSystem());  // Rebuild the panel when the user type changes
    }

    private HospitalSystem getParentHospitalSystem() {
        return (HospitalSystem) SwingUtilities.getWindowAncestor(this);
    }
}
