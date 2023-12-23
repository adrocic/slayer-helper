package com.slayerhelper.domain;

import java.util.Objects;

public class Item {
    private final String name;
    private final String icon;

    public Item(String name, String icon) {
        this.name = Objects.requireNonNull(name, "item name cannot be null");
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}