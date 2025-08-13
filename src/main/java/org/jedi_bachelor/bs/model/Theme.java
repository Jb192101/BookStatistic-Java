package org.jedi_bachelor.bs.model;

public enum Theme {
    LIGHT_THEME("Светлая"),
    DARK_THEME("Тёмная");

    private String name;

    Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
