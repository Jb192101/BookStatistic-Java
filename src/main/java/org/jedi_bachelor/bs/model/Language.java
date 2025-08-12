package org.jedi_bachelor.bs.model;

public enum Language {
    RUSSIAN_LANG("ru"),
    ENGLISH_LANG("en");

    private String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
