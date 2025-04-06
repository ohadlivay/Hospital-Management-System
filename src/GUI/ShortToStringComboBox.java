package GUI;

import javax.swing.*;

import model.ShortToStringable;

import java.awt.*;

public class ShortToStringComboBox<T extends ShortToStringable> extends JComboBox<T> {

    public ShortToStringComboBox(T[] items) {
        super(items);
        setRenderer(new ShortToStringListCellRenderer<>());
    }

    private static class ShortToStringListCellRenderer<T extends ShortToStringable> extends JLabel implements ListCellRenderer<T> {

        public ShortToStringListCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                // Directly call the shortToString() method
                setText(value.shortToString());
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }
}
