package com.slayerhelper.data;

import com.slayerhelper.domain.SlayerTask;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import static org.junit.Assert.*;

public class SlayerDataLoaderTest {

    private SlayerDataLoader dataLoader;

    @Before
    public void setUp() {
        dataLoader = new SlayerDataLoader();
    }

    @Test
    public void testLoad() {
        // Load Slayer tasks
        Collection<SlayerTask> slayerTasks = dataLoader.load();

        // Assert that the loaded tasks are not null
        assertNotNull(slayerTasks);

        // Assert that there are some tasks loaded
        assertFalse(slayerTasks.isEmpty());

        SlayerTask firstTask = slayerTasks.iterator().next();
        assertEquals("Aberrant spectre", firstTask.getMonster());
        assertTrue(firstTask.getSlayerLevel() >= 0);
    }

    @Test
    public void testLoadEmptyJsonFile() {
        // Load Slayer tasks from an empty JSON file
        String emptyJsonFilePath = "/data/emptySlayerTasks.json";
        // Load Slayer tasks from the empty JSON file
        dataLoader.setJsonFilePathForTesting(emptyJsonFilePath);
        Collection<SlayerTask> slayerTasks = dataLoader.load();

        // Assert that the loaded tasks collection is empty
        assertTrue(slayerTasks.isEmpty());
    }

    @Test
    public void testLoadInvalidJsonFormat() {
        // Load Slayer tasks from a JSON file with an invalid format (create this for testing)
        String invalidJsonFilePath = "/data/invalidSlayerTasks.json";
        // Load Slayer tasks from the file with invalid format
        dataLoader.setJsonFilePathForTesting(invalidJsonFilePath);
        Collection<SlayerTask> slayerTasks = dataLoader.load();

        // Assert that the loaded tasks collection is empty or handle the error as expected
        assertTrue(slayerTasks.isEmpty());
    }

    @Test
    public void testLoadMissingJsonFile() {
        // Load Slayer tasks from a missing JSON file (a file that doesn't exist)
        String missingJsonFilePath = "/data/nonExistentSlayerTasks.json";
        // Load Slayer tasks from the non-existent JSON file
        dataLoader.setJsonFilePathForTesting(missingJsonFilePath);
        Collection<SlayerTask> slayerTasks = dataLoader.load();

        assertTrue(slayerTasks.isEmpty());
    }
}
