package com.slayerhelper.domain;

import lombok.Getter;

import java.util.Objects;

public class SlayerTask {
    @Getter
    private final String monster;
    @Getter
    private final String[] locations;
    @Getter
    private final String[] attributes;
    @Getter
    private final String[] attackStyles;
    @Getter
    private final String[] slayerMasters;
    @Getter
    private final String[] alternatives;
    @Getter
    private final int slayerLevel;
    @Getter
    private final Item[] itemsRequired;

    public SlayerTask(
            String monster,
            int slayerLevel,
            String[] locations,
            Item[] itemsRequired,
            String[] attributes,
            String[] attackStyles,
            String[] alternatives,
            String[] slayerMasters) {
        this.monster = Objects.requireNonNull(monster, "monster cannot be null");
        this.slayerLevel = slayerLevel;
        this.locations = Objects.requireNonNull(locations, "locations cannot be null");
        this.itemsRequired = Objects.requireNonNull(itemsRequired, "items required cannot be null");
        this.attributes = Objects.requireNonNull(attributes, "attributes cannot be null");
        this.attackStyles = Objects.requireNonNull(attackStyles, "attack styles cannot be null");
        this.alternatives = Objects.requireNonNull(alternatives, "alternatives cannot be null");
        this.slayerMasters = Objects.requireNonNull(slayerMasters, "slayer masters cannot be null");
    }

    public String getMonsterLowerCase() {
        return monster.toLowerCase();
    }

    public String getMonsterFileName() {
        String monsterImageName = monster.replace(" ", "_").concat(".png").toLowerCase();
        String fileName = String.format("/images/monsters/%s", monsterImageName);
        return fileName;
    }

    public String[] getItemsRequiredNames() {
        String[] itemsRequiredNames = new String[itemsRequired.length];
        for (int i = 0; i < itemsRequired.length; i++) {
            itemsRequiredNames[i] = itemsRequired[i].getName();
        }
        return itemsRequiredNames;
    }

    public String[] getItemsRequiredIcons() {
        String[] itemsRequiredIcons = new String[itemsRequired.length];
        for (int i = 0; i < itemsRequired.length; i++) {
            itemsRequiredIcons[i] = itemsRequired[i].getIcon();
        }
        return itemsRequiredIcons;
    }

    @Override
    public String toString() {
        return this.getMonster(); // Replace with the actual property you want to display
    }
}
