package org.jedi_bachelor.bs.model;

public enum Language {
    RUSSIAN_LANG("Ru"),
    ENGLISH_LANG("En");

    private String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
