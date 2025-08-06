package org.jedi_bachelor.bs.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Date implements Serializable {
    private int month;
    private int year;

    public Date() {

    }

    public Date(int _m, int _y) {
        if(_m >= 1 && _m <= 12 && _y > 0) {
            this.month = _m;
            this.year = _y;
        }
    }

    public static Date now() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        return new Date(month, year);
    }

    // Для отладки || Потом удали (!!!)
    @Override
    public String toString() {
        return month + " " + year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }
}