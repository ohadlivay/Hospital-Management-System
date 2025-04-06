package GUI;

import javax.swing.*;

import GUI.buttons.MyJButton;

import java.awt.*;
import java.io.Serializable;

public class LandingPanel extends JPanel  implements Serializable{

    public LandingPanel(HospitalSystem parent) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to HaNamal Hospital Management System");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);

        MyJButton loginButton = new MyJButton("Login");
        MyJButton signupButton = new MyJButton("Signup");

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(loginButton, gbc);

        gbc.gridx = 1;
        add(signupButton, gbc);

        // Add action listeners to switch panels
        loginButton.addActionListener(e -> parent.switchPanel("Login"));
        signupButton.addActionListener(e -> parent.switchPanel("Signup"));
    }
}
