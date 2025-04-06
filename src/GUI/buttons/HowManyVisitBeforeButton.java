package GUI.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import control.Hospital;
import java.io.Serializable;

public class HowManyVisitBeforeButton extends MyJButton  implements Serializable{

    public HowManyVisitBeforeButton(String buttonLabel) {
        super(buttonLabel);

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame for date input
                JFrame frame = new JFrame("Select Date");
                frame.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);  // Set padding
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Combo boxes for day, month, year selection
                JComboBox<Integer> dayComboBox = new JComboBox<>(getDays());
                JComboBox<Integer> monthComboBox = new JComboBox<>(getMonths());
                JComboBox<Integer> yearComboBox = new JComboBox<>(getYears());

                // Add combo boxes to the frame
                gbc.gridx = 0;
                gbc.gridy = 0;
                frame.add(new JLabel("Day:"), gbc);
                gbc.gridx = 1;
                frame.add(dayComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                frame.add(new JLabel("Month:"), gbc);
                gbc.gridx = 1;
                frame.add(monthComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                frame.add(new JLabel("Year:"), gbc);
                gbc.gridx = 1;
                frame.add(yearComboBox, gbc);

                // Submit button
                JButton submitButton = new JButton("Submit");
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;  // Span both columns
                frame.add(submitButton, gbc);

                // Label to display result
                JLabel resultLabel = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 2;  // Span both columns
                frame.add(resultLabel, gbc);

                // ActionListener for the submit button
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int day = (int) dayComboBox.getSelectedItem();
                            int month = (int) monthComboBox.getSelectedItem();
                            int year = (int) yearComboBox.getSelectedItem();

                            // Create a Date object from selected values
                            Date selectedDate = new Date(year - 1900, month - 1, day);  // Date constructor deprecated, used for simplicity

                            // Get the number of visits before the selected date
                            int numberOfVisits = Hospital.getInstance().howManyVisitBefore(selectedDate);

                            // Update the result label with the number of visits
                            resultLabel.setText("Number of visits: " + numberOfVisits);
                        } catch (Exception ex) {
                            resultLabel.setText("Error: " + ex.getMessage());
                        }
                    }
                });

                // Set frame options
                frame.pack();
                frame.setLocationRelativeTo(null);  // Center the frame
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    private Integer[] getDays() {
        Integer[] days = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        return days;
    }

    private Integer[] getMonths() {
        Integer[] months = new Integer[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = i;
        }
        return months;
    }

    private Integer[] getYears() {
        Integer[] years = new Integer[100];  // You can adjust the range as needed
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }
        return years;
    }
}
