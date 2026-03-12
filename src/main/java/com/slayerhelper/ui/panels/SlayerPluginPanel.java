package com.slayerhelper.ui.panels;

import com.slayerhelper.data.SlayerMaster;
import com.slayerhelper.data.SlayerTaskRepository;
import com.slayerhelper.domain.SlayerTask;
import com.slayerhelper.util.SlayerTasksFetcher;
import com.slayerhelper.ui.renderers.SlayerTasksRenderer;
import com.slayerhelper.ui.components.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

@Slf4j
public class SlayerPluginPanel extends PluginPanel {

    private final JPanel detailPanel = new JPanel();
    private final SlayerTasksFetcher slayerTasksFetcher;
    private final SearchBar searchBar;
    private final ItemManager itemManager;
    private final DefaultListModel<SlayerTask> listModel = new DefaultListModel<>();
    private final String[] tabImageNamesWithExtensions = {
            "world_map.png", "inventory.png", "protect_from_all.png", "combat.png", "slayer_icon.png"
    };

    @Inject
    public SlayerPluginPanel(SlayerTaskRepository slayerTaskRepository, ItemManager itemManager) {
        this.slayerTasksFetcher = new SlayerTasksFetcher(slayerTaskRepository);
        this.itemManager = itemManager;
        searchBar = new SearchBar(this::filterList, this::clearFilter);
        createTaskListPanel(new ArrayList<>());
    }

    private TabPanel createTabPanel(SlayerTask task) {
        TabPanel tabPanel = new TabPanel(JTabbedPane.TOP);

        List<ImageIcon> icons = new ArrayList<>();
        for (String imageNameWithExtension : tabImageNamesWithExtensions) {
            BufferedImage image = ImageUtil.loadImageResource(getClass(), String.format("/images/%s", imageNameWithExtension));
            BufferedImage resized = ImageUtil.resizeImage(image, 20, 20);
            ImageIcon imageIcon = new ImageIcon(resized);
            icons.add(imageIcon);
        }

        Tab locationTab = new Tab(icons.get(0), task.getLocations(), "Map Location");
        Tab itemTab = new Tab(icons.get(1), task.getItemsRequired(), "Items Needed", itemManager);
        Tab attackStylesTab = new Tab(icons.get(2), task.getAttackStyles(), "Monster Attack Style");
        Tab attributesTab = new Tab(icons.get(3), task.getAttributes(), "Monsters Attributes");
        String[] masterNames = task.getSlayerMasters().stream().map(SlayerMaster::getDisplayName).toArray(String[]::new);
        Tab masterTab = new Tab(icons.get(4), masterNames, "Slayer Master");
        tabPanel.addTabs(new Tab[]{locationTab, itemTab, attackStylesTab, attributesTab, masterTab});

        return tabPanel;
    }

    public void createTaskListPanel(Collection<SlayerTask> tasks) {
        removeAllComponents(null);
        setLayout(new BorderLayout());

        // ====================================================================================
        // The Search Bar and Icon/Logo
        // ====================================================================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        BufferedImage image = ImageUtil.loadImageResource(getClass(), String.format("/images/slayer_icon.png"));
        ImageIcon imageIcon = new ImageIcon(image);

        JLabel searchBarIcon = new JLabel();
        searchBarIcon.setIcon(imageIcon);
        searchBarIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchBarIcon.setVerticalAlignment(SwingConstants.CENTER);
        searchBarIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel searchBarTitle = new JLabel("~ Slayer Helper ~");
        searchBarTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchBarTitle.setForeground(Color.ORANGE);
        searchBarTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        searchBarTitle.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        JLabel searchBarHelperMsg = new JLabel("Search for a monster...");
        searchBarHelperMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchBarHelperMsg.setForeground(Color.WHITE);
        searchBarHelperMsg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        searchBarHelperMsg.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        topPanel.add(searchBarIcon);
        topPanel.add(searchBarTitle);
        topPanel.add(searchBarHelperMsg);
        topPanel.add(searchBar.getSearchBar());
        topPanel.setName("searchBarAndLogo");

        this.clearFilter();
        this.updateListModel(tasks);

        add(topPanel, BorderLayout.NORTH);

        // ====================================================================================
        // The Scrollable List
        // ====================================================================================
        JList<SlayerTask> monsterNames = new JList<>(listModel);
        monsterNames.setCellRenderer(new SlayerTasksRenderer());
        monsterNames.setFocusable(true);
        monsterNames.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        monsterNames.setBackground(new Color(30, 30, 30));
        monsterNames.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                SlayerTask selectedTask = monsterNames.getSelectedValue();
                if (selectedTask != null) {
                    openTask(selectedTask);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(monsterNames);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setName("scrollableList");

        add(scrollPane, BorderLayout.CENTER);

        // ====================================================================================
        // The details view below the list - opened after clicking a monster
        // ====================================================================================
        add(detailPanel, BorderLayout.SOUTH);

        // ====================================================================================
        // End
        // ====================================================================================
        revalidate();
        repaint();
    }

    private void openTask(SlayerTask task) {
        updateListModel(List.of(task));
        searchBar.getSearchBar().setText(task.getMonster());
        detailPanel.removeAll();

        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFocusPainted(false);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> closeTask());
        // Margin (around button)
        JPanel closeWrapper = new JPanel();
        closeWrapper.setBackground(new Color(30, 30, 30));
        closeWrapper.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        closeWrapper.add(closeButton);

        // Monster
        JPanel monsterPanel = new JPanel(new BorderLayout());
        monsterPanel.setBackground(new Color(30, 30, 30));
        try {
            BufferedImage img = ImageUtil.loadImageResource(getClass(), task.getMonsterFileName());
            BufferedImage resizedImg = ImageUtil.resizeImage(img, img.getWidth() / 3, img.getHeight() / 3);
            JLabel monsterLabel = new JLabel(new ImageIcon(resizedImg));
            monsterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            monsterPanel.add(monsterLabel, BorderLayout.CENTER);
        } catch (NullPointerException e) {
            log.info("Couldn't find monster image: {}", task.getMonsterFileName());
            JLabel fallback = new JLabel(task.getMonster(), SwingConstants.CENTER);
            fallback.setForeground(Color.ORANGE);
            monsterPanel.add(fallback, BorderLayout.CENTER);
        }

        detailPanel.add(createTabPanel(task).getTabbedPane());
        detailPanel.add(monsterPanel);
        detailPanel.add(closeWrapper);

        revalidate();
        repaint();
    }

    private void closeTask() {
        clearFilter();
        detailPanel.removeAll();
        revalidate();
        repaint();
    }

    private void removeAllComponents(@Nullable Component[] excludedComponents) {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (excludedComponents != null && Arrays.stream(excludedComponents).anyMatch(c -> c == component)) {
                continue;
            }
            remove(component);
        }

        revalidate();
        repaint();
    }

    public void filterList(String searchText) {
        if (!searchText.isEmpty()) {
            Collection<SlayerTask> tasks = slayerTasksFetcher.getSlayerTasksByFilter(searchText);
            updateListModel(tasks);
        } else {
            updateListModel(slayerTasksFetcher.getAllSlayerTasks());
        }
    }

    public void clearFilter() {
        searchBar.getSearchBar().setText(""); // Clear the search bar text
        updateListModel(slayerTasksFetcher.getAllSlayerTasks()); // Reset the list
    }

    public void updateListModel(Collection<SlayerTask> tasks) {
        listModel.clear();
        tasks.stream()
                .sorted(Comparator.comparing(SlayerTask::getMonster, String.CASE_INSENSITIVE_ORDER))
                .forEach(listModel::addElement);
    }

    public void refresh() {
        createTaskListPanel(slayerTasksFetcher.getAllSlayerTasks());
    }
}
