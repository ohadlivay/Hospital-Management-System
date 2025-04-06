package GUI.buttons;

import control.Hospital;
import exceptions.FutureDateException;
import exceptions.InvalidDateRangeException;
import java.io.Serializable;
import model.Patient;
import model.Visit;

import javax.swing.*;

import GUI.ShortToStringComboBox;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddVisitButton extends ActionButton<AddVisitButton.VisitInputData>  implements Serializable{

    public AddVisitButton() {
        super("Add Visit");
    }

    // Class to hold the input data (Patient, Start Date, End Date)
    public static class VisitInputData {
        private Patient patient;
        private Date startDate;
        private Date endDate;

        public VisitInputData(Patient patient, Date startDate, Date endDate) {
            this.patient = patient;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Patient getPatient() {
            return patient;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }
    }

    @Override
    protected VisitInputData getInput() {
        // Fetch all patients from the Hospital instance
        List<Patient> patientsList = Hospital.getInstance().getPatients().values().stream().collect(Collectors.toList());
        if (patientsList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No patients available to add a visit.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Return null if there are no patients
        }
        // Create JComboBox for patient selection
        ShortToStringComboBox<Patient> patientComboBox = new ShortToStringComboBox<>(patientsList.toArray(new Patient[0]));
        patientComboBox.setPreferredSize(new Dimension(200, 25));

        // Create JComboBox components for selecting the date
        JComboBox<Integer> startDayComboBox = createDayComboBox();
        JComboBox<Integer> startMonthComboBox = createMonthComboBox();
        JComboBox<Integer> startYearComboBox = createYearComboBox();

        JComboBox<Integer> endDayComboBox = createDayComboBox();
        JComboBox<Integer> endMonthComboBox = createMonthComboBox();
        JComboBox<Integer> endYearComboBox = createYearComboBox();

        // Set up the panel layout
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Select Patient:"));
        panel.add(patientComboBox);
        panel.add(new JLabel("Enter Start Date:"));
        panel.add(createDatePanel(startDayComboBox, startMonthComboBox, startYearComboBox));
        panel.add(new JLabel("Enter End Date:"));
        panel.add(createDatePanel(endDayComboBox, endMonthComboBox, endYearComboBox));

        // Show the panel in a JOptionPane
        int result = JOptionPane.showConfirmDialog(
                null, panel, "Add Visit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

        	
        	Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
            Date startDate = createDateFromComboBoxes(startDayComboBox, startMonthComboBox, startYearComboBox);
            Date endDate = createDateFromComboBoxes(endDayComboBox, endMonthComboBox, endYearComboBox);

            if (selectedPatient != null && startDate != null && endDate != null) {
                return new VisitInputData(selectedPatient, startDate, endDate);
            }
        }

        return null; // Return null if the operation is canceled or input is invalid
    }

    @Override
    protected boolean processInput(VisitInputData inputData) throws Exception {
        // Extract data from the input
        Patient selectedPatient = inputData.getPatient();
        Date startDate = inputData.getStartDate();
        Date endDate = inputData.getEndDate();

        // Check for future dates
        if (startDate.after(Hospital.TODAY)) {
            throw new FutureDateException(startDate);
        }
        if (endDate.after(Hospital.TODAY)) {
            throw new FutureDateException(endDate);
        }
        if (startDate.after(endDate)) {
            throw new InvalidDateRangeException("Start date cannot be after the end date.");
        }

        // Create a new visit and add it to the hospital system
        Visit newVisit = new Visit(Hospital.getInstance().getNextPKNumber(), selectedPatient, startDate, endDate);
        return Hospital.getInstance().addVisit(newVisit);
    }

    private JPanel createDatePanel(JComboBox<Integer> dayComboBox, JComboBox<Integer> monthComboBox, JComboBox<Integer> yearComboBox) {
        JPanel datePanel = new JPanel();
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        return datePanel;
    }

    private JComboBox<Integer> createDayComboBox() {
        Integer[] days = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        return new JComboBox<>(days);
    }

    private JComboBox<Integer> createMonthComboBox() {
        Integer[] months = new Integer[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = i;
        }
        return new JComboBox<>(months);
    }

    private JComboBox<Integer> createYearComboBox() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }
        return new JComboBox<>(years);
    }

    private Date createDateFromComboBoxes(JComboBox<Integer> dayComboBox, JComboBox<Integer> monthComboBox, JComboBox<Integer> yearComboBox) {
        try {
            int day = (int) dayComboBox.getSelectedItem();
            int month = (int) monthComboBox.getSelectedItem() - 1; // Month is 0-based in Calendar
            int year = (int) yearComboBox.getSelectedItem();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }
}
