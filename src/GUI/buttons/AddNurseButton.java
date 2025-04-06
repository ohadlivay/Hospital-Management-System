package GUI.buttons;

import javax.swing.*;

import exceptions.BirthdateAfterWorkdateException;
import exceptions.FutureDateException;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;
import model.Nurse;
import model.IntensiveCareNurse;
import control.Hospital;

import java.util.Calendar;
import java.util.Date;

public class AddNurseButton extends ActionButton<AddNurseButton.NurseInputData>  implements Serializable{

    public AddNurseButton() {
        super("Add Nurse");
    }

    // Inner class to hold the input data for the nurse
    public static class NurseInputData {
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
        boolean isIntensiveCare;

        public NurseInputData(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber, 
                              String email, String gender, Date workStartDate, double salary, int licenseNumber, 
                              boolean isIntensiveCare) {
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
            this.isIntensiveCare = isIntensiveCare;
        }
    }

    @Override
    protected NurseInputData getInput() {
        // Create input fields for the nurse attributes
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
        JCheckBox intensiveCareCheckBox = new JCheckBox("Is trained in intensive Care"); // Added checkbox for Intensive Care

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Nurse ID:", idField,
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
            "", intensiveCareCheckBox // Added intensive care checkbox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add New Nurse", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
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
                boolean isIntensiveCare = intensiveCareCheckBox.isSelected(); // Check if the nurse is intensive care

                return new NurseInputData(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, 
                                          workStartDate, salary, licenseNumber, isIntensiveCare);
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid input format.");
            }
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(NurseInputData input) throws ObjectAlreadyExistsException {
        try {
            if (input.isIntensiveCare) {
                // Use the IntensiveCareNurse constructor if the nurse is intensive care
                return Hospital.getInstance().addNurse(new IntensiveCareNurse(input.id, input.firstName, input.lastName, 
                                                                              input.birthDate, input.address, input.phoneNumber, 
                                                                              input.email, input.gender, input.workStartDate, 
                                                                              input.salary, input.licenseNumber));
            } else {
                // Use the regular Nurse constructor if not intensive care
                return Hospital.getInstance().addNurse(new Nurse(input.id, input.firstName, input.lastName, input.birthDate, 
                                                                 input.address, input.phoneNumber, input.email, input.gender, 
                                                                 input.workStartDate, input.salary, input.licenseNumber));
            }
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
}
