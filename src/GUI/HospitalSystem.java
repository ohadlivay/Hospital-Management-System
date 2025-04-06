//ID:208464289 OHAD LIVAY

package GUI;

import javax.swing.*;
import GUI.*;
import GUI.CorePanels.*;
import model.StaffMember;
import view.Main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * HospitalSystem is the main JFrame for the hospital management system application.
 * It manages different panels (screens) using a CardLayout, allowing navigation between them.
 * The system supports user login, panel switching, and handles application exit operations.
 * It also manages the logged-in user, the current user type, and panel history to enable "back" navigation.
 */

public class HospitalSystem extends JFrame implements Serializable {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private UserType userType;
    private Stack<String> panelHistory;
    private String currentPanel;
    private StaffMember loggedUser;
    private boolean testMode;
    private Map<String, JPanel> panelMap;

    public HospitalSystem() {
        setTitle("HaNamal Hospital Management System");
        
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png"));
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(icon.getImage());
        } else {
            System.out.println("Failed to load icon.");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panelHistory = new Stack<>();
        panelMap = new HashMap<>();

        initializePanels();

        currentPanel = "Landing";
        cardLayout.show(mainPanel, currentPanel);

        add(mainPanel);
        setVisible(true);

        testMode = ((LoginPanel)panelMap.get("Login")).isTestMode();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });
    }

    private void initializePanels() {
        addPanelToSystem("Landing", new LandingPanel(this));
        addPanelToSystem("Login", new LoginPanel(this));
        addPanelToSystem("Signup", new SignupPanel(this));
        addPanelToSystem("MainPanel", new MainPanel(this));
        addPanelToSystem("AddPanel", new AddPanel(this));
        addPanelToSystem("QueryPanel", new QueryPanel(this));
        addPanelToSystem("RemovePanel", new RemovePanel(this));
        addPanelToSystem("ViewDataPanel", new ViewDataPanel(this));
        addPanelToSystem("SettingsPanel", new SettingsPanel(this));
    }

    private void addPanelToSystem(String panelName, JPanel panel) {
        panelMap.put(panelName, panel);
        mainPanel.add(panel, panelName);
    }

    public void switchPanel(String panelName) {
        JPanel panel = panelMap.get(panelName);

        if (panel instanceof UserTypeAwarePanel) {
            ((UserTypeAwarePanel) panel).updateUserType(userType); // Update user type-specific elements
        }

        if (panel instanceof BasePanel) {
            ((BasePanel) panel).updateSettingsButtonVisibility(userType);
        }
        
        if(panel instanceof LoginPanel) {
        	((LoginPanel) panel).rebuildUI();
        }
        if(panel instanceof SignupPanel) {
        	((SignupPanel) panel).refreshDoctorsList();
        	refreshPanel("SignupPanel");
        }
        

        if (!panelName.equals(currentPanel)) {
            System.out.println("Switching from " + currentPanel + " to " + panelName);
            panelHistory.push(currentPanel); // Add current panel to history before switching
            currentPanel = panelName; // Update the current panel
        }

        cardLayout.show(mainPanel, panelName); // Show the selected panel
        panel.revalidate();
        panel.repaint();
    }
    //use of method overloading for panel history management for special cases
    public void switchPanel(String panelName, Boolean bool) {
        JPanel panel = panelMap.get(panelName);

        if (panel instanceof UserTypeAwarePanel) {
            ((UserTypeAwarePanel) panel).updateUserType(userType); // Update user type-specific elements
        }

        if (panel instanceof BasePanel) {
            ((BasePanel) panel).updateSettingsButtonVisibility(userType);
        }
        
        if(panel instanceof LoginPanel) {
        	((LoginPanel) panel).rebuildUI();
        }
        if(panel instanceof SignupPanel) {
        	((SignupPanel) panel).refreshDoctorsList();
        	refreshPanel("SignupPanel");

        }
        

        if (!panelName.equals(currentPanel)) {
            System.out.println( currentPanel + "-> " + panelName);
            if(bool) {
            	panelHistory.push(currentPanel); // Add current panel to history before switching
            }
            currentPanel = panelName; // Update the current panel
        }

        cardLayout.show(mainPanel, panelName); // Show the selected panel
        panel.revalidate();
        panel.repaint();
    }



    private JPanel refreshPanel(String currentPanel) {
    	System.out.println("Refreshing " + currentPanel);
        JPanel newPanel;
        switch (currentPanel) {
            case "Landing":
                newPanel = new LandingPanel(this);
                break;
            case "Login":
                newPanel = new LoginPanel(this);
                break;
            case "Signup":
                newPanel = new SignupPanel(this);
                break;
            case "MainPanel":
                newPanel = new MainPanel(this);
                break;
            case "AddPanel":
                newPanel = new AddPanel(this);
                break;
            case "QueryPanel":
                newPanel = new QueryPanel(this);
                break;
            case "RemovePanel":
                newPanel = new RemovePanel(this);
                break;
            case "ViewDataPanel":
                newPanel = new ViewDataPanel(this);
                break;
            case "SettingsPanel":
                newPanel = new SettingsPanel(this);
                break;
            default:
                return panelMap.get(currentPanel);
        }
        mainPanel.remove(panelMap.get(currentPanel));
        mainPanel.add(newPanel, currentPanel);
        panelMap.put(currentPanel, newPanel);
        return newPanel;
    }

    public void goBack() {
        if (!panelHistory.isEmpty()) {
            String previousPanelName = panelHistory.pop();
            System.out.println("Going back to: " + previousPanelName);
            refreshPanel(previousPanelName);
            switchPanel(previousPanelName, false); // to avoid alternation when pressing back, we don't save history.
        } else {
            System.out.println("No more panels in history.");
        }
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalSystem::new);
    }

    public StaffMember getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(StaffMember loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Map<String, JPanel> getPanelMap() {
        return panelMap;
    }

    private void handleExit() {
        int option = JOptionPane.showOptionDialog(
                this,
                "Would you like to save before exiting?",
                "Exit Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Save and Exit", "Exit"},
                "Save and Exit"
        );

        if (option == JOptionPane.YES_OPTION) {
            try {
                Main.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        } else if (option == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}