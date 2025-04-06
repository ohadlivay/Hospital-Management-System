package GUI.buttons;

import javax.swing.*;

import exceptions.*;
import model.Treatment;
import control.Hospital;
import java.io.Serializable;

public class AddTreatmentButton extends ActionButton<AddTreatmentButton.TreatmentInputData>  implements Serializable{
	
    public AddTreatmentButton() {
        super("Add Treatment");
    }

    // Inner class to hold the input data for the treatment
    public static class TreatmentInputData {
        String description;

        public TreatmentInputData(String description) {
            this.description = description;
        }
    }

    @Override
    protected TreatmentInputData getInput() {
        // Create input field for the treatment description
        JTextField descriptionField = new JTextField(20);

        // Display the input field in a dialog
        Object[] inputFields = {
            "Description:", descriptionField
        };

        int result = JOptionPane.showConfirmDialog(null, inputFields, "Add New Treatment", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String description = descriptionField.getText();
            if(description.trim().equals("")) {
            	throw new EmptyInputException();
            }
            else {
            	System.out.println("its is not empty, see? : " + description);
            }
            return new TreatmentInputData(description);
        }

        return null;  // Return null if the user cancels the input
    }

    @Override
    protected boolean processInput(TreatmentInputData input) throws ObjectAlreadyExistsException {
        try {
            // Create a new Treatment object using the input data
            Treatment newTreatment = new Treatment(Hospital.getInstance().getNextPKNumber(), input.description);
            

            // Add the treatment to the hospital using the addTreatment method
            return Hospital.getInstance().addTreatment(newTreatment);
        } catch (NullPointerException e) {
            showErrorMessage("Treatment cannot be null.");
            return false;
        } catch (ObjectAlreadyExistsException e) {
            showWarningMessage("A treatment with this serial number already exists.");
            return false;
        }
    }
}
