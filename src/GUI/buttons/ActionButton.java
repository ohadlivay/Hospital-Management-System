package GUI.buttons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import exceptions.*;

public abstract class ActionButton<T> extends MyJButton implements Serializable {

    public ActionButton(String buttonLabel) {
        super(buttonLabel);



        // Add hover effect
        addHoverEffect();

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    T input = getInput();
                    if (input != null) {
                        // Attempt to process the input
                        boolean success = processInput(input);

                        if (success) {
                            showSuccessMessage();
                        } else {
                            showErrorMessage("Failed to process input!");
                        }
                    } else {
                        // Handle null input (if needed)
                        handleNullInput();
                    }
                } catch (NullPointerException ex) {
                    showErrorMessage("Input cannot be null!");
                } catch (NoIntensiveCareDepartmentForICStaffException ex) {
                    showErrorMessage("Cannot assign IC Staff since no IC Department exists");
                } catch (ObjectAlreadyExistsException ex) {
//                    showWarningMessage("Duplicate entry detected!");
                	showWarningMessage(ex.getMessage());
                } catch (EmptyInputException ex) {
                    showWarningMessage("Input cannot be empty!");
                } catch (NumberFormatException ex) {
                        showErrorMessage("Invalid input format.");
                } catch (Exception ex) {
                    // Generic exception handler for unexpected issues
                    showErrorMessage("Wow, an unexpected error occurred: " + ex.getMessage());
                }
            }
        });
    }

    // Abstract method to retrieve input from the user (could be any type)
    protected abstract T getInput();

    // Abstract method to process the input (e.g., add entity, update data)
    protected abstract boolean processInput(T input) throws Exception;

    // Optional method to handle null input, can be overridden by subclasses if needed
    protected final void handleNullInput() {
        //showWarningMessage("No input provided");
    }

    // Helper methods for displaying messages
    protected void showSuccessMessage() {
        JOptionPane.showMessageDialog(this,
                "Action completed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showSuccessMessage(String string) {
        JOptionPane.showMessageDialog(this,
                string,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    protected void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    // Add hover effect for the button
    private void addHoverEffect() {
        // Store the original background color
        final Color originalBackground = getBackground();

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(originalBackground.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(originalBackground);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(originalBackground.darker());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(originalBackground);
            }
        });
    }
}
