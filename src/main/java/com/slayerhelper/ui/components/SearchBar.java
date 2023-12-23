package com.slayerhelper.ui.components;

import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchBar {
    @Getter
    private final IconTextField searchBar = new IconTextField();

    // Define the interfaces for the handlers
    public interface OnKeyTypedHandler {
        void run(String text);
    }

    public interface OnClearHandler {
        void run();
    }

    public SearchBar(OnKeyTypedHandler onKeyTypedHandler, OnClearHandler onClearHandler) {
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Run the handler with the current text of the search bar
                onKeyTypedHandler.run(searchBar.getText());

            }
        });

        searchBar.addClearListener(() -> {
            onClearHandler.run();
            searchBar.setIcon(IconTextField.Icon.SEARCH);
            searchBar.setEditable(true);
        });

        setSearchBarStyle();
    }

    private void setSearchBarStyle() {
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.setMinimumSize(new Dimension(0, 30));
    }
}
