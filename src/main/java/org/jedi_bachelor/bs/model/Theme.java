package org.jedi_bachelor.bs.model;

public enum Theme {
    LIGHT_THEME("Light"),
    DARK_THEME("Dark");

    private String name;

    Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
