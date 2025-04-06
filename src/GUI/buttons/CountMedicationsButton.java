package GUI.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.Hospital;
import java.io.Serializable;

public class CountMedicationsButton extends MyJButton  implements Serializable{

    public CountMedicationsButton(String buttonLabel) {
        super(buttonLabel);

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame for input
                JFrame frame = new JFrame("Enter Range");
                frame.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Input fields for minimum and maximum values
                JTextField minTextField = new JTextField(10);
                JTextField maxTextField = new JTextField(10);

                // Labels for the input fields
                gbc.gridx = 0;
                gbc.gridy = 0;
                frame.add(new JLabel("Minimum Dosage:"), gbc);
                gbc.gridx = 1;
                frame.add(minTextField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                frame.add(new JLabel("Maximum Dosage:"), gbc);
                gbc.gridx = 1;
                frame.add(maxTextField, gbc);

                // Submit button
                JButton submitButton = new JButton("Submit");
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                frame.add(submitButton, gbc);

                // Label to display result
                JLabel resultLabel = new JLabel(" ");
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                frame.add(resultLabel, gbc);

                // ActionListener for the submit button
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Parse input values
                            double minValue = Double.parseDouble(minTextField.getText());
                            double maxValue = Double.parseDouble(maxTextField.getText());

                            // Validate input
                            if (minValue < 0 || maxValue < 0) {
                                throw new IllegalArgumentException("Dosage must be positive.");
                            }
                            if (maxValue < minValue) {
                                throw new IllegalArgumentException("Max can't be lower than min.");
                            }

                            // Process input and get the result from Hospital
                            int count = Hospital.getInstance().countMedications(minValue, maxValue);

                            // Update result label
                            resultLabel.setText("Number of medications: " + count);

                        } catch (NumberFormatException ex) {
                            resultLabel.setText("Error: Please enter valid numbers.");
                        } catch (IllegalArgumentException ex) {
                            resultLabel.setText(ex.getMessage());
                        } catch (Exception ex) {
                            resultLabel.setText(ex.getMessage());
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
}
