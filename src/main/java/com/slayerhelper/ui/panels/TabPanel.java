package com.slayerhelper.ui.panels;

import com.slayerhelper.ui.components.Tab;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TabPanel {
    @Getter
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public TabPanel(Integer tabPlacement) {
        tabbedPane.setPreferredSize(new Dimension(250, 300));
        tabbedPane.setTabPlacement(tabPlacement);
    }

    private void addTab(Tab tab) {
        Objects.requireNonNull(tab, "tab cannot be null");

        JLabel label = new JLabel();
        label.setIcon(tab.getIcon());
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Component component = tabbedPane.add(tab.getContent());
        int index = tabbedPane.indexOfComponent(component);

        tabbedPane.setTabComponentAt(index, label);
    }

    public void addTabs(Tab[] tabs) {
        for (Tab tab : tabs) {
            addTab(tab);
        }
    }
}
