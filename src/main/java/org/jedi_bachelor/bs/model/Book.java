package org.jedi_bachelor.bs.model;

import org.jedi_bachelor.bs.exceptions.AllPagesLowThanCompleteException;
import org.jedi_bachelor.bs.exceptions.NegativePagesException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

@Component
@Scope("prototype")
public class Book implements Serializable, Comparable<Book> {
    static long countOfBooks = 0;

    private long id;
    private String name;
    private String author;

    private int completePages;
    private int allPages;
    private float procentOfReaded;

    private LocalDate startOfReading;
    private LocalDate endOfReading;

    private Rating rating;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void calculateProcentOfReaded() {
        this.procentOfReaded = (float) this.completePages / this.allPages;
    }

    public float getProcentOfReaded() {
        return procentOfReaded;
    }
}
