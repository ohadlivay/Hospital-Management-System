package GUI.buttons;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import org.apache.poi.xwpf.usermodel.*;

import GUI.ShortToStringComboBox;
import control.Hospital;
import model.Patient;
import model.Visit;

public class PrintPatientButton extends MyJButton implements Serializable {

    public PrintPatientButton() {
        super("Print Patient Info");

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a combobox for selecting a patient
                JComboBox<Patient> patientComboBox = new ShortToStringComboBox<>(Hospital.getInstance().getPatients().values().toArray(new Patient[0]));
                patientComboBox.setPreferredSize(new Dimension(200,30));
                // Display the combobox in a dialog
                int option = JOptionPane.showConfirmDialog(null, patientComboBox, "Select a Patient", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    // Get the selected patient
                    Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
                    if (selectedPatient != null) {
                        // Create a Word document
                        try (XWPFDocument document = new XWPFDocument()) {
                            // Create a title for the document
                            XWPFParagraph titleParagraph = document.createParagraph();
                            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                            XWPFRun titleRun = titleParagraph.createRun();
                            titleRun.setBold(true);
                            titleRun.setFontSize(20);
                            titleRun.setText("Patient Information");
                            titleRun.addBreak();
                            titleRun.addBreak();

                            // Create a paragraph for patient information
                            XWPFParagraph paragraph = document.createParagraph();
                            paragraph.setAlignment(ParagraphAlignment.LEFT);
                            XWPFRun run = paragraph.createRun();
                            run.setFontSize(12);
                            run.setBold(true);
                            run.setText("Personal Information:");
                            run.addBreak();
                            run.setBold(false);
                            run.setText("ID: " + selectedPatient.getId());
                            run.addBreak();
                            run.setText("First Name: " + selectedPatient.getFirstName());
                            run.addBreak();
                            run.setText("Last Name: " + selectedPatient.getLastName());
                            run.addBreak();
                            run.setText("Birth Date: " + selectedPatient.getBirthDate());
                            run.addBreak();
                            run.setText("Address: " + selectedPatient.getAddress());
                            run.addBreak();
                            run.setText("Phone Number: " + selectedPatient.getPhoneNumber());
                            run.addBreak();
                            run.setText("Email: " + selectedPatient.getEmail());
                            run.addBreak();
                            run.setText("Gender: " + selectedPatient.getGender());
                            run.addBreak();
                            run.setText("Health Fund: " + selectedPatient.getHealthFund());
                            run.addBreak();
                            run.setText("Biological Sex: " + selectedPatient.getBiologicalSex());
                            run.addBreak();
                            run.addBreak();

                            // Add visits information
                            run.setBold(true);
                            run.setText("Visits:");
                            run.addBreak();
                            run.setBold(false);
                            HashSet<Visit> visitsList = selectedPatient.getVisitsList();
                            for (Visit visit : visitsList) {
                                run.setText(visit.toWordString());
                                run.addBreak();
                            }

                            // Create a more descriptive file name
                            String fileName = "Patient_" + selectedPatient.getFirstName() + "_" + selectedPatient.getLastName() + "_" + selectedPatient.getId() + ".docx";

                            // Save the document
                            try (FileOutputStream out = new FileOutputStream(fileName)) {
                                document.write(out);
                            }

                            // Show a success message
                            JOptionPane.showMessageDialog(null, "Patient information saved successfully!");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error saving patient information: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No patient selected.");
                    }
                }
            }
        });
    }
}
