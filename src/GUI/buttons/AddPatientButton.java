package GUI.buttons;

import javax.swing.*;
import enums.HealthFund;
import enums.BiologicalSex;
import exceptions.IDAlreadyAssociatedException;
import exceptions.ObjectAlreadyExistsException;
import java.io.Serializable;
import model.Patient;
import control.Hospital;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class AddPatientButton extends ActionButton<AddPatientButton.PatientInputData>  implements Serializable{

    public AddPatientButton() {
        super("Add Patient");
    }

    // Inner class to hold the input data for the patient
    public static class PatientInputData {
        int id;
        String firstName;
        String lastName;
        Date birthDate;
        String address;
        String phoneNumber;
        String email;
        String gender;
        HealthFund healthFund;
        BiologicalSex biologicalSex;

        public PatientInputData(int id, String firstName, String lastName, Date birthDate, String address, 
                                String phoneNumber, String email, String gender, HealthFund healthFund, 
                                BiologicalSex biologicalSex) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.gender = gender;
            this.healthFund = healthFund;
            this.biologicalSex = biologicalSex;
        }
    }

    @Override
    protected PatientInputData getInput() {
        // Create input fields for the patient attributes
        JTextField idField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);

        // Date fields: Day, Month, Year
        JComboBox<Integer> birthDayComboBox = createDayComboBox();
        JComboBox<String> birthMonthComboBox = createMonthComboBox();
        JComboBox<Integer> birthYearComboBox = createYearComboBox();

        JTextField addressField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField genderField = new JTextField(10);
        JComboBox<HealthFund> healthFundComboBox = new JComboBox<>(HealthFund.values());
        JComboBox<BiologicalSex> biologicalSexComboBox = new JComboBox<>(BiologicalSex.values());

        // Display the input fields in a dialog
        Object[] inputFields = {
            "Patient ID:", idField,
            "First Name:", firstNameField,
            "Last Name:", lastNameField,
            "Birth Date (Day, Month, Year):", birthDayComboBox, birthMonthComboBox, birthYearComboBox,
            "Address:", addressField,
            "Phone Number:", phoneNumberField,
            "Email:", emailField,
            "Gender:", genderField,
            "Health Fund:", healthFundComboBox,
            "Biological Sex:", biologicalSexComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add New Patient", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText()) ;
                if(id < 100000000 || id > 999999999) {
                    throw new IllegalArgumentException("ID must have 9 digits.");
                }
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                Date birthDate = createDateFromSelection(birthDayComboBox, birthMonthComboBox, birthYearComboBox);
                String address = addressField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                String gender = genderField.getText();
                HealthFund healthFund = (HealthFund) healthFundComboBox.getSelectedItem();
                BiologicalSex biologicalSex = (BiologicalSex) biologicalSexComboBox.getSelectedItem();
                //check if ID is taken by staffie. 

                return new PatientInputData(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, healthFund, biologicalSex);
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid input format.");
            } catch(IllegalArgumentException e) {
            	showErrorMessage(e.getMessage());
            }
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(PatientInputData input) throws ObjectAlreadyExistsException {
        try {
           
            return Hospital.getInstance().addPatient(new Patient(input.id, input.firstName, input.lastName, input.birthDate, input.address, 
                                                                 input.phoneNumber, input.email, input.gender, input.healthFund, input.biologicalSex));

        } catch (ObjectAlreadyExistsException e) {
            showErrorMessage(e.getMessage());
            return false;
	    } catch (IDAlreadyAssociatedException e) {
	        showErrorMessage("ID is already associated with a user, please log in.");
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
