package GUI.CorePanels;

import javax.swing.*;
import GUI.BasePanel;
import GUI.HospitalSystem;
import GUI.UserType;
import GUI.UserTypeAwarePanel;
import GUI.buttons.EditProfileButton;
import GUI.buttons.MyJButton;

import java.awt.*;
import java.io.Serializable;

public class MainPanel extends BasePanel implements UserTypeAwarePanel , Serializable{
    private UserType userType;
    private JLabel welcomeLabel;
    private JButton addButton;
    private JButton removeButton;
    private JButton queryButton;
    private JButton viewDataButton;
    private JButton editProfileButton;
    private JPanel buttonPanel;
    private HospitalSystem parent;

    public MainPanel(HospitalSystem parent) {
        super(parent);  // Call the BasePanel constructor to set up the back, logout, and settings buttons
        this.parent = parent;

        // Initialize the content panel for the welcome message and buttons
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10)); // Use BorderLayout for content
        contentPanel.setPreferredSize(new Dimension(600, 400)); // Respect the fixed size from BasePanel

        // Initialize the welcome label
        welcomeLabel = new JLabel("", JLabel.CENTER);
        contentPanel.add(welcomeLabel, BorderLayout.NORTH); // Add the welcome label at the top of the content panel

        // Initialize buttons with preferred sizes
        Dimension buttonSize = new Dimension(150, 50); // Set the desired button size

        addButton = new MyJButton("Add");
        addButton.setPreferredSize(buttonSize);

        removeButton = new MyJButton("Remove");
        removeButton.setPreferredSize(buttonSize);

        queryButton = new MyJButton("Query");
        queryButton.setPreferredSize(buttonSize);

        viewDataButton = new MyJButton("View Data");
        viewDataButton.setPreferredSize(buttonSize);

        // Create the EditProfileButton and pass the parent (HospitalSystem)
        editProfileButton = new EditProfileButton(parent);
        editProfileButton.setPreferredSize(buttonSize);

        // Initialize button panel with GridBagLayout
        buttonPanel = new JPanel(new GridBagLayout());
        contentPanel.add(buttonPanel, BorderLayout.CENTER); // Add button panel in the center of the content panel

        // Add the content panel to the center of the BasePanel
        add(contentPanel, BorderLayout.CENTER);

        // Set up action listeners for the other buttons
        addButton.addActionListener(e -> parent.switchPanel("AddPanel"));
        removeButton.addActionListener(e -> parent.switchPanel("RemovePanel"));
        queryButton.addActionListener(e -> parent.switchPanel("QueryPanel"));
        viewDataButton.addActionListener(e -> parent.switchPanel("ViewDataPanel"));

        // Initial button setup
        updateButtons();
    }

    @Override
    public void updateUserType(UserType userType) {
        this.userType = userType;
        updateWelcomeMessage();
        updateButtons(); // Dynamically add/remove buttons based on user type
    }

    private void updateWelcomeMessage() {
    	if(userType == null) {
            welcomeLabel.setText("Logging in failed!");

    	}
    	else if (userType != UserType.ADMIN) {
            welcomeLabel.setText("Welcome, " + userType + " " + parent.getLoggedUser().getFirstName() + " " + parent.getLoggedUser().getLastName() + "!");
        } else {
            welcomeLabel.setText("Welcome ADMIN!");
        }
        revalidate();
        repaint();
    }
    
    public void refreshWelcomeMessage() {
        updateWelcomeMessage();  // This method already updates the welcome message based on the userType
    }

    private void updateButtons() {
        buttonPanel.removeAll(); // Clear all buttons before re-adding them

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between buttons
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Always add these buttons
        buttonPanel.add(addButton, gbc);
        gbc.gridy++;
        buttonPanel.add(queryButton, gbc);
        gbc.gridy++;
        buttonPanel.add(viewDataButton, gbc);

        // Add buttons based on user type
        if (userType == UserType.ADMIN) {
            gbc.gridy++;
            buttonPanel.add(removeButton, gbc);
        } else if (userType == UserType.DOCTOR || userType == UserType.NURSE) {
            gbc.gridy++;
            buttonPanel.add(editProfileButton, gbc); // Add the Edit Profile button for Doctors and Nurses
        }

        revalidate(); // Revalidate the layout
        repaint(); // Repaint the panel to reflect changes
    }
}
