package GUI.buttons;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import control.SettingsManager;
import java.util.ArrayList;
import java.util.List;

public class MyJButton extends JButton implements Serializable {
    private static final List<MyJButton> allButtons = new ArrayList<>();

    public MyJButton(String buttonLabel) {
        super(buttonLabel);
        allButtons.add(this);
        updateButtonStyle();
    }

    private void updateButtonStyle() {
        setFont(SettingsManager.getButtonFont());
        setBackground(SettingsManager.getButtonBackground());
        setForeground(SettingsManager.getButtonForeground());

        setFocusPainted(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SettingsManager.getButtonBackground().darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
    }

    public static void updateButtonStyles() {
        for (MyJButton button : allButtons) {
            button.updateButtonStyle();
        }
    }
}
