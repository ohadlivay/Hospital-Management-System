package GUI.buttons;

import control.Hospital;
import enums.Specialization;
import GUI.CorePanels.MainPanel;
import model.Doctor;
import model.IntensiveCareDoctor;
import model.Nurse;
import model.IntensiveCareNurse;

import javax.swing.*;

import GUI.HospitalSystem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class EditProfileButton extends ActionButton<Serializable>  implements Serializable{
    private final HospitalSystem parent; // Reference to HospitalSystem

    public EditProfileButton(HospitalSystem hospitalSystem) {
        super("Edit Profile");
        this.parent = hospitalSystem; // Store the HospitalSystem reference
    }

    // Inner class to hold the input data for the profile, separating `Doctor` and `Nurse`
    public static class DoctorProfileInputData implements Serializable {
        int id;
        String firstName;
        String lastName;
        String address;
        String phoneNumber;
        String email;
        String gender;
        Date workStartDate;
        boolean finishedInternship;
        Specialization specialization;
        boolean isIntensiveCare;

        public DoctorProfileInputData(int id, String firstName, String lastName, String address,
                                      String phoneNumber, String email, String gender, Date workStartDate,
                                      boolean finishedInternship, Specialization specialization, boolean isIntensiveCare) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.gender = gender;
            this.workStartDate = workStartDate;
            this.finishedInternship = finishedInternship;
            this.specialization = specialization;
            this.isIntensiveCare = isIntensiveCare;
        }
    }

    public static class NurseProfileInputData implements Serializable {
        int id;
        String firstName;
        String lastName;
        String address;
        String phoneNumber;
        String email;
        String gender;
        Date workStartDate;
        boolean isIntensiveCare;

        public NurseProfileInputData(int id, String firstName, String lastName, String address,
                                     String phoneNumber, String email, String gender, Date workStartDate,
                                     boolean isIntensiveCare) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.gender = gender;
            this.workStartDate = workStartDate;
            this.isIntensiveCare = isIntensiveCare;
        }
    }

    @Override
    protected Serializable getInput() {
        Object loggedUser = parent.getLoggedUser();
        if (loggedUser == null) {
            showErrorMessage("No user is logged in.");
            return null;
        }

        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField genderField = new JTextField(10);
        JComboBox<Integer> workStartDayComboBox = createDayComboBox();
        JComboBox<String> workStartMonthComboBox = createMonthComboBox();
        JComboBox<Integer> workStartYearComboBox = createYearComboBox();

        if (loggedUser instanceof Doctor) {
            JCheckBox finishedInternshipCheckBox = new JCheckBox("Has finished internship");
            JCheckBox intensiveCareCheckBox = new JCheckBox("Is trained in intensive care");
            JComboBox<Specialization> specializationComboBox = new JComboBox<>(Specialization.values());

            Doctor doctor = (Doctor) loggedUser;
            populateFieldsForDoctor(doctor, firstNameField, lastNameField, addressField, phoneNumberField, emailField, genderField,
                    workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox, finishedInternshipCheckBox,
                    intensiveCareCheckBox, specializationComboBox);

            Object[] inputFields = new Object[]{
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField,
                    "Address:", addressField,
                    "Phone Number:", phoneNumberField,
                    "Email:", emailField,
                    "Gender:", genderField,
                    "Work Start Date (Day, Month, Year):", workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox,
                    "Finished Internship:", finishedInternshipCheckBox,
                    "Specialization:", specializationComboBox,
                    "Intensive Care:", intensiveCareCheckBox
            };

            int result = JOptionPane.showConfirmDialog(null, inputFields, "Edit Profile", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                return new DoctorProfileInputData(
                        doctor.getId(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        addressField.getText(),
                        phoneNumberField.getText(),
                        emailField.getText(),
                        genderField.getText(),
                        createDateFromSelection(workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox),
                        finishedInternshipCheckBox.isSelected(),
                        (Specialization) specializationComboBox.getSelectedItem(),
                        intensiveCareCheckBox.isSelected()
                );
            }
        } else if (loggedUser instanceof Nurse) {
            JCheckBox intensiveCareCheckBox = new JCheckBox("Is trained in intensive care");

            Nurse nurse = (Nurse) loggedUser;
            populateFieldsForNurse(nurse, firstNameField, lastNameField, addressField, phoneNumberField, emailField, genderField,
                    workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox, intensiveCareCheckBox);

            Object[] inputFields = new Object[]{
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField,
                    "Address:", addressField,
                    "Phone Number:", phoneNumberField,
                    "Email:", emailField,
                    "Gender:", genderField,
                    "Work Start Date (Day, Month, Year):", workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox,
                    "Intensive Care:", intensiveCareCheckBox
            };

            int result = JOptionPane.showConfirmDialog(null, inputFields, "Edit Profile", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                return new NurseProfileInputData(
                        nurse.getId(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        addressField.getText(),
                        phoneNumberField.getText(),
                        emailField.getText(),
                        genderField.getText(),
                        createDateFromSelection(workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox),
                        intensiveCareCheckBox.isSelected()
                );
            }
        }

        return null;
    }

    @Override
    protected boolean processInput(Serializable input) throws Exception {
        Object loggedUser = parent.getLoggedUser();
        Hospital hospital = Hospital.getInstance();  // Access the singleton instance

        if (input instanceof DoctorProfileInputData && loggedUser instanceof Doctor) {
            DoctorProfileInputData data = (DoctorProfileInputData) input;
            Doctor doctor = (Doctor) loggedUser;

            updateDoctorData(doctor, data);

            if (data.isIntensiveCare && !(doctor instanceof IntensiveCareDoctor)) {
                hospital.replaceDoctor(doctor.getId(), new IntensiveCareDoctor(
                        doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                        doctor.getBirthDate(), doctor.getAddress(), doctor.getPhoneNumber(),
                        doctor.getEmail(), doctor.getGender(), doctor.getWorkStartDate(),
                        doctor.getSalary(), doctor.getLicenseNumber(), doctor.isFinishInternship()));
            }

            // Refresh the main panel to reflect changes
            parent.switchPanel("MainPanel");  // Switch to MainPanel
            MainPanel mainPanel = (MainPanel) parent.getPanelMap().get("MainPanel");
            mainPanel.refreshWelcomeMessage();  // Refresh the welcome message

        } else if (input instanceof NurseProfileInputData && loggedUser instanceof Nurse) {
            NurseProfileInputData data = (NurseProfileInputData) input;
            Nurse nurse = (Nurse) loggedUser;

            updateNurseData(nurse, data);

            if (data.isIntensiveCare && !(nurse instanceof IntensiveCareNurse)) {
                hospital.replaceNurse(nurse.getId(), new IntensiveCareNurse(
                        nurse.getId(), nurse.getFirstName(), nurse.getLastName(),
                        nurse.getBirthDate(), nurse.getAddress(), nurse.getPhoneNumber(),
                        nurse.getEmail(), nurse.getGender(), nurse.getWorkStartDate(),
                        nurse.getSalary(), nurse.getLicenseNumber()));
            }

            // Refresh the main panel to reflect changes
            parent.switchPanel("MainPanel");  // Switch to MainPanel
            MainPanel mainPanel = (MainPanel) parent.getPanelMap().get("MainPanel");
            mainPanel.refreshWelcomeMessage();  // Refresh the welcome message

        } else {
            showErrorMessage("User type not supported for profile editing.");
            return false;
        }
        
        return true;
    }



    private void populateFieldsForDoctor(Doctor doctor, JTextField firstNameField, JTextField lastNameField, JTextField addressField,
                                         JTextField phoneNumberField, JTextField emailField, JTextField genderField,
                                         JComboBox<Integer> workStartDayComboBox, JComboBox<String> workStartMonthComboBox,
                                         JComboBox<Integer> workStartYearComboBox, JCheckBox finishedInternshipCheckBox,
                                         JCheckBox intensiveCareCheckBox, JComboBox<Specialization> specializationComboBox) {
        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        addressField.setText(doctor.getAddress());
        phoneNumberField.setText(doctor.getPhoneNumber());
        emailField.setText(doctor.getEmail());
        genderField.setText(doctor.getGender());
        setDateFields(doctor.getWorkStartDate(), workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox);
        finishedInternshipCheckBox.setSelected(doctor.isFinishInternship());
        intensiveCareCheckBox.setSelected(doctor instanceof IntensiveCareDoctor);
        specializationComboBox.setSelectedItem(doctor.getSpecialization());
    }

    private void populateFieldsForNurse(Nurse nurse, JTextField firstNameField, JTextField lastNameField, JTextField addressField,
                                        JTextField phoneNumberField, JTextField emailField, JTextField genderField,
                                        JComboBox<Integer> workStartDayComboBox, JComboBox<String> workStartMonthComboBox,
                                        JComboBox<Integer> workStartYearComboBox, JCheckBox intensiveCareCheckBox) {
        firstNameField.setText(nurse.getFirstName());
        lastNameField.setText(nurse.getLastName());
        addressField.setText(nurse.getAddress());
        phoneNumberField.setText(nurse.getPhoneNumber());
        emailField.setText(nurse.getEmail());
        genderField.setText(nurse.getGender());
        setDateFields(nurse.getWorkStartDate(), workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox);
        intensiveCareCheckBox.setSelected(nurse instanceof IntensiveCareNurse);
    }

    private void updateDoctorData(Doctor doctor, DoctorProfileInputData data) {
        doctor.setFirstName(data.firstName);
        doctor.setLastName(data.lastName);
        doctor.setAddress(data.address);
        doctor.setPhoneNumber(data.phoneNumber);
        doctor.setEmail(data.email);
        doctor.setGender(data.gender);
        doctor.setWorkStartDate(data.workStartDate);
        doctor.setFinishInternship(data.finishedInternship);
        doctor.setSpecialization(data.specialization);
    }

    private void updateNurseData(Nurse nurse, NurseProfileInputData data) {
        nurse.setFirstName(data.firstName);
        nurse.setLastName(data.lastName);
        nurse.setAddress(data.address);
        nurse.setPhoneNumber(data.phoneNumber);
        nurse.setEmail(data.email);
        nurse.setGender(data.gender);
        nurse.setWorkStartDate(data.workStartDate);
    }

    // Helper methods to handle date fields (same as before)
    private void setDateFields(Date date, JComboBox<Integer> dayComboBox, JComboBox<String> monthComboBox, JComboBox<Integer> yearComboBox) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dayComboBox.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
        monthComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
        yearComboBox.setSelectedItem(calendar.get(Calendar.YEAR));
    }

    private JComboBox<Integer> createDayComboBox() {
        JComboBox<Integer> dayComboBox = new JComboBox<>();
        for (int day = 1; day <= 31; day++) {
            dayComboBox.addItem(day);
        }
        return dayComboBox;
    }

    private JComboBox<String> createMonthComboBox() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return new JComboBox<>(months);
    }

    private JComboBox<Integer> createYearComboBox() {
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= currentYear - 100; year--) {
            yearComboBox.addItem(year);
        }
        return yearComboBox;
    }

    private Date createDateFromSelection(JComboBox<Integer> dayComboBox, JComboBox<String> monthComboBox, JComboBox<Integer> yearComboBox) {
        int day = (Integer) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex(); // ComboBox index corresponds to month (0 = January, 11 = December)
        int year = (Integer) yearComboBox.getSelectedItem();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
