package org.jedi_bachelor.bs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name="speed_stat")
public class SpeedStat {
    @Id
    @Column(name = "month")
    private int month;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name="count")
    private int countOfReaded;

    public SpeedStat() {

    }

    public SpeedStat(@NotNull Date date, int countOfReaded) {
        this.month = date.getMonth();
        this.year = date.getYear();
        this.countOfReaded = countOfReaded;
    }

    public int getCountOfReaded() {
        return countOfReaded;
    }

    public Date getDate() {
        return new Date(this.month, this.year);
    }

    public void setDate(Date date) {
        this.month = date.getMonth();
        this.year = date.getYear();
    }

    public void setCountOfReaded(int countOfReaded) {
        this.countOfReaded = countOfReaded;
    }
}
