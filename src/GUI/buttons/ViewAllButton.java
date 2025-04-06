package GUI.buttons;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import GUI.HospitalSystem;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ViewAllButton<T> extends MyJButton  implements Serializable{

    public ViewAllButton(String title, List<T> data, String[] columnNames, Function<T, Object[]> rowMapper, HospitalSystem parent) {
        super(title);
        addActionListener(e -> showDataDialog(title, data, columnNames, rowMapper, parent));
    }

    private void showDataDialog(String title, List<T> data, String[] columnNames, Function<T, Object[]> rowMapper, HospitalSystem parent) {
        // Convert the data into a 2D array for the table
        Object[][] tableData = data.stream()
                                   .map(rowMapper)
                                   .toArray(Object[][]::new);

        // Create the table model
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table uneditable
            }
        };

        // Create the JTable and enable sorting
        JTable table = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create the JDialog to display the table
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Set the dialog size and make it visible
        dialog.setSize(800, 600); // Adjust size as needed
        dialog.setLocationRelativeTo(parent); // Center on the parent window
        dialog.setVisible(true);
    }
}
