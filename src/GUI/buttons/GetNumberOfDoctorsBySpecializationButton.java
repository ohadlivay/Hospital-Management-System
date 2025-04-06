package GUI.buttons;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import control.Hospital;
import enums.*;
import java.io.Serializable;

import java.awt.*;
import java.util.HashMap;

public class GetNumberOfDoctorsBySpecializationButton extends MyJButton  implements Serializable{

    public GetNumberOfDoctorsBySpecializationButton(String buttonLabel) {
        super(buttonLabel);

        this.addActionListener(e -> {
            // Create a new JDialog to hold the table
            JDialog dialog = new JDialog((Frame) null, "Number of Doctors by Specialization", true);
            dialog.setLayout(new BorderLayout());

            // Get data from the Hospital
            HashMap<Specialization, Integer> data = Hospital.getInstance().getNumberOfDoctorsBySpecialization();

            // Create column names
            String[] columnNames = {"Specialization", "Number of Doctors"};

            // Create a table model with non-editable cells
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make all cells non-editable
                }
            };

            // Populate the table model with data from the map
            for (HashMap.Entry<Specialization, Integer> entry : data.entrySet()) {
                Object[] row = {entry.getKey().toString(), entry.getValue()};
                tableModel.addRow(row);
            }

            // Create the table with the non-editable model
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table); // Make the table scrollable

            // Add the scroll pane to the dialog
            dialog.add(scrollPane, BorderLayout.CENTER);

            // Set dialog options
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null); // Center the dialog
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}
