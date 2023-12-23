package com.slayerhelper.data;

import com.google.gson.Gson;
import com.slayerhelper.domain.SlayerTask;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
public class SlayerDataLoader {

    private String jsonFilePath = "/data/slayerTasks.json";

    public Collection<SlayerTask> load() {
        try (InputStream inputStream = Objects.requireNonNull(
                this.getClass().getResourceAsStream(jsonFilePath));
             Reader reader = new InputStreamReader(inputStream)) {

            SlayerTask[] tasks = new Gson().fromJson(reader, SlayerTask[].class);
            return Arrays.asList(tasks);

        } catch (IOException e) {
            log.error("Could not read JSON from slayerTasks.json", e);
            return Collections.emptyList();
        }
    }

    public void setJsonFilePathForTesting(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }
}