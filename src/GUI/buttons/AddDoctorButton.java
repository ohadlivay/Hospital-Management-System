package GUI.buttons;

import javax.swing.*;

import enums.Specialization;
import exceptions.BirthdateAfterWorkdateException;
import exceptions.EmptyInputException;
import exceptions.FutureDateException;
import exceptions.IDAlreadyAssociatedException;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;
import model.Doctor;
import model.IntensiveCareDoctor;
import control.Hospital;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class AddDoctorButton extends ActionButton<AddDoctorButton.DoctorInputData> implements Serializable {

    public AddDoctorButton() {
        super("Add Doctor");
    }

    // Inner class to hold the input data for the doctor
    public static class DoctorInputData {
        int id;
        String firstName;
        String lastName;
        Date birthDate;
        String address;
        String phoneNumber;
        String email;
        String gender;
        Date workStartDate;
        double salary;
        int licenseNumber;
        boolean finishedInternship;
        Specialization specialization;
        boolean isIntensiveCare; // Added for intensive care checkbox

        public DoctorInputData(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber, 
                               String email, String gender, Date workStartDate, double salary, int licenseNumber, 
                               boolean finishedInternship, Specialization specialization, boolean isIntensiveCare) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.gender = gender;
            this.workStartDate = workStartDate;
            this.salary = salary;
            this.licenseNumber = licenseNumber;
            this.finishedInternship = finishedInternship;
            this.specialization = specialization;
            this.isIntensiveCare = isIntensiveCare;
        }
    }

    @Override
    protected DoctorInputData getInput() {
        // Create input fields for the doctor attributes
        JTextField idField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);

        // Date fields: Day, Month, Year
        JComboBox<Integer> birthDayComboBox = createDayComboBox();
        JComboBox<String> birthMonthComboBox = createMonthComboBox();
        JComboBox<Integer> birthYearComboBox = createYearComboBox();
        JComboBox<Integer> workStartDayComboBox = createDayComboBox();
        JComboBox<String> workStartMonthComboBox = createMonthComboBox();
        JComboBox<Integer> workStartYearComboBox = createYearComboBox();

        JTextField addressField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField genderField = new JTextField(10);
        JTextField salaryField = new JTextField(20);
        JTextField licenseNumberField = new JTextField(20);
        JCheckBox finishedInternshipCheckBox = new JCheckBox("Has finished internship");
        JCheckBox intensiveCareCheckBox = new JCheckBox("Is trained in intensive care"); // Added checkbox for Intensive Care
        JComboBox<Specialization> specializationComboBox = new JComboBox<>(Specialization.values());

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Doctor ID:", idField,
            "First Name:", firstNameField,
            "Last Name:", lastNameField,
            "Birth Date (Day, Month, Year):", birthDayComboBox, birthMonthComboBox, birthYearComboBox,
            "Address:", addressField,
            "Phone Number:", phoneNumberField,
            "Email:", emailField,
            "Gender:", genderField,
            "Work Start Date (Day, Month, Year):", workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox,
            "Salary:", salaryField,
            "License Number:", licenseNumberField,
            "Finished Internship:", finishedInternshipCheckBox,
            "Specialization:", specializationComboBox,
            "Intensive Care:", intensiveCareCheckBox // Added intensive care checkbox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add New Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
                int id = Integer.parseInt(idField.getText());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                Date birthDate = createDateFromSelection(birthDayComboBox, birthMonthComboBox, birthYearComboBox);
                String address = addressField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                String gender = genderField.getText();
                Date workStartDate = createDateFromSelection(workStartDayComboBox, workStartMonthComboBox, workStartYearComboBox);
                double salary = Double.parseDouble(salaryField.getText());
                int licenseNumber = Integer.parseInt(licenseNumberField.getText());
                boolean finishedInternship = finishedInternshipCheckBox.isSelected();
                Specialization specialization = (Specialization) specializationComboBox.getSelectedItem();
                boolean isIntensiveCare = intensiveCareCheckBox.isSelected(); // Check if the doctor is intensive care

                return new DoctorInputData(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate, 
                                           salary, licenseNumber, finishedInternship, specialization, isIntensiveCare);
            
            	}

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(DoctorInputData input) throws ObjectAlreadyExistsException {
        try {
            // Check for null input fields
            if (input.firstName == null || input.firstName.isEmpty() ||
                input.lastName == null || input.lastName.isEmpty() ||
                input.birthDate == null ||
                input.address == null || input.address.isEmpty() ||
                input.phoneNumber == null || input.phoneNumber.isEmpty() ||
                input.email == null || input.email.isEmpty() ||
                input.gender == null || input.gender.isEmpty() ||
                input.workStartDate == null ||
                input.specialization == null) {
                throw new EmptyInputException("One or more input fields are empty.");
            }

            if (input.isIntensiveCare) {
                // Use the IntensiveCareDoctor constructor if the doctor is intensive care
                return Hospital.getInstance().addDoctor(new IntensiveCareDoctor(input.id, input.firstName, input.lastName,
                                                                                input.birthDate, input.address, input.phoneNumber,
                                                                                input.email, input.gender, input.workStartDate,
                                                                                input.salary, input.licenseNumber, input.finishedInternship));
            } else {
                // Use the regular Doctor constructor if not intensive care
                return Hospital.getInstance().addDoctor(new Doctor(input.id, input.firstName, input.lastName, input.birthDate,
                                                                   input.address, input.phoneNumber, input.email, input.gender,
                                                                   input.workStartDate, input.salary, input.licenseNumber,
                                                                   input.finishedInternship, input.specialization));
            }
        } catch (IDAlreadyAssociatedException e) {
            showErrorMessage("ID is already associated with a user, please log in.");
            return false;
        } catch (FutureDateException e) {
            showErrorMessage("Invalid birthdate.");
            return false;
        } catch (BirthdateAfterWorkdateException e) {
            showErrorMessage(e.getMessage());
            return false;
        }
    }

    // Helper method to create a JComboBox for days
    private JComboBox<Integer> createDayComboBox() {
        JComboBox<Integer> dayComboBox = new JComboBox<>();
        for (int day = 1; day <= 31; day++) {
            dayComboBox.addItem(day);
        }
        return dayComboBox;
    }

    // Helper method to create a JComboBox for months
    private JComboBox<String> createMonthComboBox() {
        String[] months = {"January", "February", "March", "April", "May", "June", 
                           "July", "August", "September", "October", "November", "December"};
        return new JComboBox<>(months);
    }

    // Helper method to create a JComboBox for years (100 years back from the current year)
    private JComboBox<Integer> createYearComboBox() {
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= currentYear - 100; year--) {
            yearComboBox.addItem(year);
        }
        return yearComboBox;
    }

    // Helper method to create a Date object from the selected day, month, and year
    private Date createDateFromSelection(JComboBox<Integer> dayComboBox, JComboBox<String> monthComboBox, JComboBox<Integer> yearComboBox) {
        int day = (Integer) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex(); // ComboBox index corresponds to month (0 = January, 11 = December)
        int year = (Integer) yearComboBox.getSelectedItem();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    // Helper method to display an error message
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
