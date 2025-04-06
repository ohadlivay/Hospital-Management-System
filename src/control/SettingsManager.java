//ID:208464289 OHAD LIVAY

package control;

import enums.Settings;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.EnumMap;
import GUI.buttons.MyJButton;

public class SettingsManager implements Serializable {
    private static final EnumMap<Settings, Boolean> settingsMap = new EnumMap<>(Settings.class);

    static {
        settingsMap.put(Settings.TESTMODE, false);
    }

    public static boolean getSetting(Settings setting) {
        return settingsMap.getOrDefault(setting, false);
    }

    public static void setSetting(Settings setting, boolean value) {
        System.out.println("setting: " + setting.toString() + " to " + value);
        settingsMap.put(setting, value);
        notifySettingsChange();
    }

    // Centralized styling control
    public static Font getButtonFont() {
        if (getSetting(Settings.TESTMODE)) {
            return new Font("Arial", Font.BOLD, 14);
        } else {
            return new Font("Arial", Font.BOLD, 14); 
        }
    }

    public static Color getButtonBackground() {
        if (getSetting(Settings.TESTMODE)) {
            return new Color(90, 150, 125); 
        } else {
            return new Color(70, 130, 180); 
        }
    }

    public static Color getButtonForeground() {
        if (getSetting(Settings.TESTMODE)) {
            return Color.WHITE; 
        } else {
            return Color.WHITE; 
        }
    }

    public static void notifySettingsChange() {
        MyJButton.updateButtonStyles();
    }
}
