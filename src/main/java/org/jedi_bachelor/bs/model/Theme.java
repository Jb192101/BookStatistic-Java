package org.jedi_bachelor.bs.model;

public enum Theme {
    LIGHT_THEME("light"),
    DARK_THEME("dark");

    private String name;

    Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
