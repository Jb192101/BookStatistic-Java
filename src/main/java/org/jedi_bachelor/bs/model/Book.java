package org.jedi_bachelor.bs.model;

import jakarta.persistence.*;
import org.jedi_bachelor.bs.exceptions.AllPagesLowThanCompleteException;
import org.jedi_bachelor.bs.exceptions.NegativePagesException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

@Component
@Entity
@Scope("prototype")
@Table(name="books")
public class Book implements Serializable, Comparable<Book> {
    static long countOfBooks = 0;

    @Id
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="author")
    private String author;

    @Column(name="complete_pages")
    private int completePages;
    @Column(name="all_pages")
    private int allPages;
    @Column(name="percent_of_readed")
    private float procentOfReaded;

    @Column(name="start_of_reading")
    private LocalDate startOfReading;
    @Column(name="end_of_reading")
    private LocalDate endOfReading;

    @Column(name="rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    public Book() {

    }

    public Book(String name, String author, int completePages, int allPages) {
        setName(name);
        setAuthor(author);

        try {
            setAllPages(allPages);
        } catch (NegativePagesException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        try {
            setCompletePages(completePages);
        } catch(NegativePagesException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch(AllPagesLowThanCompleteException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        setStartOfReading(LocalDate.now());

        countOfBooks++;
        id = countOfBooks;

        calculateProcentOfReaded();
    }

    public void setAllPages(int allPages) throws NegativePagesException {
        if(allPages <= 0) {
            throw new NegativePagesException("количество всех страниц меньше нуля или равно нулю!");
        }

        this.allPages = allPages;
    }

    public void setCompletePages(int completePages) throws NegativePagesException, AllPagesLowThanCompleteException {
        if(completePages < 0) {
            throw new NegativePagesException("количество прочитанных страниц меньше нуля!");
        }
        if(completePages > this.allPages) {
            throw new AllPagesLowThanCompleteException("количество прочитанных страниц больше всех страниц в книге!");
        }

        this.completePages = completePages;

        if(this.completePages == this.allPages) {
            setEndOfReading(LocalDate.now());
        }
    }

    @Override
    public int compareTo(Book o) {
        return (int) (this.id - o.getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCompletePages() {
        return completePages;
    }

    public int getAllPages() {
        return allPages;
    }

    public LocalDate getStartOfReading() {
        return startOfReading;
    }

    public void setStartOfReading(LocalDate startOfReading) {
        this.startOfReading = startOfReading;
    }

    public LocalDate getEndOfReading() {
        return endOfReading;
    }

    public void setEndOfReading(LocalDate endOfReading) {
        this.endOfReading = endOfReading;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void calculateProcentOfReaded() {
        this.procentOfReaded = (float) this.completePages / this.allPages;
    }

    public float getProcentOfReaded() {
        return procentOfReaded;
    }
}
