package com.slayerhelper.util;

import com.slayerhelper.data.SlayerDataLoader;
import com.slayerhelper.domain.SlayerTask;
import com.slayerhelper.ui.components.SearchBar;

import javax.swing.*;
import java.util.Collection;

public class SearchUtils {

    private final SlayerTasksFetcher slayerTasksFetcher;

    private final SearchBar searchBar;

    private final DefaultListModel<SlayerTask> listModel;

    public SearchUtils(SearchBar searchBar, DefaultListModel<SlayerTask> listModel) {
        slayerTasksFetcher = new SlayerTasksFetcher(new SlayerDataLoader());
        this.searchBar = searchBar;
        this.listModel = listModel;
    }

    public SearchUtils(){
        slayerTasksFetcher = new SlayerTasksFetcher(new SlayerDataLoader());
        searchBar = null;
        listModel = null;
    }

    public void filterList(String searchText) {
        if (!searchText.isEmpty()) {
            Collection<SlayerTask> tasks = slayerTasksFetcher.getSlayerTasksByFilter(searchText);
            updateListModel(tasks);
        } else {
            // Clear the list when the search text is empty
            listModel.clear();
        }
    }

    public void clearFilter() {
        searchBar.getSearchBar().setText(""); // Clear the search bar text
        updateListModel(slayerTasksFetcher.getAllSlayerTasks()); // Reset the list
    }

    // A helper method to update the list model
    public void updateListModel(Collection<SlayerTask> tasks) {
        listModel.clear();
        tasks.forEach(listModel::addElement);
    }
}
