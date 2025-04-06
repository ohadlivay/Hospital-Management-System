package GUI;

import javax.swing.*;
import GUI.buttons.MyJButton;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import view.Main;

/**
 * The BasePanel class is a custom JPanel that sets a strict size and layout, 
 * and includes a top panel with buttons for navigation and actions like 
 * "Back," "Log out," "Developer Tools," and "Save." 
 * The visibility of the "Developer Tools" button is dynamically controlled 
 * based on the user type.
 */
public class BasePanel extends JPanel implements Serializable {

    private MyJButton settingsButton;

    public BasePanel(HospitalSystem parent) {
        setPreferredSize(new Dimension(600, 450));
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(600, 40));

        Dimension buttonSize = new Dimension(100, 30);

        MyJButton backButton = createFixedSizeButton("Back", buttonSize);
        backButton.addActionListener(e -> parent.goBack());

        MyJButton logoutButton = createFixedSizeButton("Log out", buttonSize);
        logoutButton.addActionListener(e -> parent.switchPanel("Landing"));

        settingsButton = createFixedSizeButton("Developer Tools", new Dimension(230, 30));
        settingsButton.addActionListener(e -> parent.switchPanel("SettingsPanel"));
        settingsButton.setVisible(false);

        MyJButton saveButton = createFixedSizeButton("Save", buttonSize);
        saveButton.addActionListener(e -> {
            try {
                Main.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(settingsButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(saveButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(logoutButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(600, 400));
        add(contentPanel, BorderLayout.CENTER);
    }

    private MyJButton createFixedSizeButton(String text, Dimension size) {
        MyJButton button = new MyJButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        return button;
    }

    public void updateSettingsButtonVisibility(UserType userType) {
        if (userType == UserType.ADMIN) {
            settingsButton.setVisible(true);
        } else {
            settingsButton.setVisible(false);
        }
        revalidate();
        repaint();
    }
}
