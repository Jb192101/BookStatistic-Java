package org.jedi_bachelor.bs.model;

public enum Rating {
    LOW("1"),
    LOW_MED("2"),
    MED("3"),
    HIGH_MED("4"),
    HIGH("5");

    private String nameOfRating;

    Rating(String rating) {
        this.nameOfRating = rating;
    }

    public String getNameOfRating() {
        return this.nameOfRating;
    }
}
