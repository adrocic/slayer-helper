package com.slayerhelper.ui.components;

import com.slayerhelper.util.WikiUtil;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Arrays;

public class Tab {
    @Getter
    private final ImageIcon icon;
    @Getter
    private final JPanel content;

    public Tab(ImageIcon icon, String[] content, String type) {
        Arrays.sort(content, (a, b) -> Integer.compare(b.length(), a.length()));
        Objects.requireNonNull(icon, "icon cannot be null");
        Objects.requireNonNull(content, "content cannot be null");
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(30, 30, 30));
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        typeLabel.setForeground(Color.ORANGE);
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        contentPanel.add(typeLabel);
        for (String s : content) {
            JLabel label = new JLabel(s);
            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            Container wikiButton = WikiUtil.createLinkButton(s, WikiUtil.getWikiUrl(type, s));
            contentPanel.add(wikiButton);
        }
        this.icon = icon;
        this.content = contentPanel;
    }

    private String convertStringsToLineSeparatedString(String[] strings) {
        return String.join(System.lineSeparator(), strings);
    }
}
