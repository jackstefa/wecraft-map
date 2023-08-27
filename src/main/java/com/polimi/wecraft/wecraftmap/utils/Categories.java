package com.polimi.wecraft.wecraftmap.utils;

public enum Categories {
    CARPET("carpet"),
    JEWEL("jewel"),
    SWEATER("sweater"),
    PAINTING("painting"),
    CERAMIC("ceramic"),
    BAG("bag"),
    WOOD("wood");


    private final String value;

    private Categories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String checkValue) {
        for (Categories item : Categories.values()) {
            if (item.getValue().equals(checkValue)) {
                return true;
            }
        }
        return false;
    }
}

