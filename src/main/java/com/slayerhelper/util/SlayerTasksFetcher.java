package com.slayerhelper.util;

import com.slayerhelper.data.SlayerDataLoader;
import com.slayerhelper.domain.SlayerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SlayerTasksFetcher {
    private Collection<SlayerTask> slayerTasks;

    public SlayerTasksFetcher(SlayerDataLoader dataLoader) {
        try {
            slayerTasks = Objects.requireNonNull(dataLoader.load());
        } catch (RuntimeException e) {
            log.error("Failed to load Slayer Task Data... \n", e);
             slayerTasks = Collections.emptyList();
        }
    }

    public Collection<SlayerTask> getAllSlayerTasks() {
        return slayerTasks;
    }

    public Collection<SlayerTask> getSlayerTasksByFilter(String searchText) {
        String lowerCaseSearchText = searchText.toLowerCase();
        return slayerTasks.stream()
                .filter(slayerTask -> slayerTask.getMonsterLowerCase().contains(lowerCaseSearchText))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
