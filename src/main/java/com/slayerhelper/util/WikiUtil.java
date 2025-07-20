package com.slayerhelper.util;
import com.slayerhelper.ui.components.Tab;
import net.runelite.client.RuneLite;
import net.runelite.client.plugins.slayer.SlayerConfig;
import net.runelite.client.plugins.slayer.SlayerPlugin;
import net.runelite.http.api.RuneLiteAPI;
import net.runelite.http.api.chat.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WikiUtil {
    public enum TaskDetail {
        NONE("None"),
        MAP_LOCATION("Map Location"),
        ITEMS_NEEDED("Items Needed"),
        MONSTER_ATTACK_STYLE("Monster Attack Style"),
        SLAYER_MASTER("Slayer Master"),
        MONSTER_ATTRIBUTES("Monster Attributes");

        // Field to store the custom string description
        private String taskDetailName;

        // Constructor to initialize the custom string
        TaskDetail(String taskDetailName) {
            this.taskDetailName = taskDetailName;
        }

        // Override toString() to return the custom string
        @Override
        public String toString() {
            return this.taskDetailName;
        }
    }

    public static JButton createLinkButton(String text, final String url) {
        JButton button = new JButton(text);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        boolean enabled = !text.equalsIgnoreCase("None");
        button.setEnabled(enabled);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebpage(url);
            }
        });

        return button;
    }

    private static void openWebpage(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                // Handle the error according to your needs
                // For example, show a dialog to the user with the error message
                JOptionPane.showMessageDialog(null,
                        "Failed to open the URL. Please check your system settings.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle the case where the desktop is not supported
            JOptionPane.showMessageDialog(null,
                    "Desktop is not supported. Unable to open the browser.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static String getWikiUrl(TaskDetail type, String name) {
        String baseUrl = "https://oldschool.runescape.wiki/w/";
        String pattern = "_(.+)$"; // Matches an underscore followed by anything at the end
        switch (type) {
            case NONE:
                return "about:blank";
            case MAP_LOCATION:
            case ITEMS_NEEDED:
            case MONSTER_ATTACK_STYLE:
            case SLAYER_MASTER:
                return baseUrl + name.replace(" ", "_").replace(pattern, "");
            case MONSTER_ATTRIBUTES:
                return baseUrl + name + "_(attribute)";
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

