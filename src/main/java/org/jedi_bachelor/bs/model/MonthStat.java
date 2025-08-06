package org.jedi_bachelor.bs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="month_stat")
public class MonthStat {
    @Id
    @Column(name = "month")
    private int month;

    @Id
    @Column(name = "year")
    private int year;

    @Column(name="count")
    int countOfReaded;

    private Date date; // транзитное поле

    public MonthStat() {

    }

    public MonthStat(Date date, int countOfReaded) {
        this.date = date;
        this.month = date.getMonth();
        this.year = date.getYear();
        this.countOfReaded = countOfReaded;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCountOfReaded() {
        return countOfReaded;
    }

    public void setCountOfReaded(int countOfReaded) {
        this.countOfReaded = countOfReaded;
    }
}
