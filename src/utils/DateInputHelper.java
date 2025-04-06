package utils;

import utils.UtilsMethods;

import javax.swing.*;

import java.io.Serializable;
import java.util.Date;

public class DateInputHelper implements Serializable{

    // Method to get the date input from the user via dialog
    public static Date getDateInput(String prompt) {
        while (true) {
            String dateInput = JOptionPane.showInputDialog(null, prompt, "Enter Date", JOptionPane.PLAIN_MESSAGE);
            if (dateInput == null) {
                // User canceled the input
                return null;
            }
            Date parsedDate = UtilsMethods.parseDate(dateInput);
            if (parsedDate != null) {
                return parsedDate;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in dd/MM/yyyy format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // New method to parse a date from a JTextField input
    public static Date getDateFromInputField(String dateInput) {
        return UtilsMethods.parseDate(dateInput);
    }
}
