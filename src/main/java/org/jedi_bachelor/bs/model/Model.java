package org.jedi_bachelor.bs.model;

import jakarta.annotation.PostConstruct;

import org.jedi_bachelor.bs.factory.BookFactory;

import org.jedi_bachelor.bs.utils.DataBaseConnectivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Model {
    @Autowired
    private BookFactory bookFactory;

    private Map<Long, Book> books;
    private Map<Date, Integer> monthSpeed;
    private Map<Date, Integer> monthStat;

    @Autowired
    private DataBaseConnectivity dataBaseConnectivity;

    @PostConstruct
    private void init() {
        books = dataBaseConnectivity.getBooks();

        monthStat = dataBaseConnectivity.getMonthStat();

        monthSpeed = dataBaseConnectivity.getSpeedStat();
    }

    // Метод для изменения
    public void changeBook(Book book) {
        if(book == null)
            return;

        int changedPages = 0;
        long id = book.getId();
        Book medBook = this.books.get(id);

        if(medBook == null)
            return;
        else {
            changedPages = book.getCompletePages() - medBook.getCompletePages();
            if(changedPages < 0) {
                changedPages *= -1;
            }
            addPagesAtMonthStat(changedPages);
            addPagesAtMonthSpeed(changedPages);

            this.books.remove(id, medBook);
            this.books.put(id, book);

            updateFileBooks();
        }
    }

    public Book searchBook(long id) {
        if(this.books.containsKey(id))
            return this.books.get(id);

        return null;
    }

    private void addPagesAtMonthStat(int changed) {
        boolean isKey = false;
        for(Date d : this.monthStat.keySet()) {
            if(d.equals(Date.now())) {
                isKey = true;
                break;
            }
        }

        if(!isKey)
            this.monthStat.put(Date.now(), changed);
        else
            this.monthStat.put(Date.now(), this.monthStat.get(Date.now()) + changed);
    }

    private void addPagesAtMonthSpeed(int changed) {
        boolean isKey = false;
        for(Date d : this.monthSpeed.keySet()) {
            if(d.equals(Date.now())) {
                isKey = true;
                break;
            }
        }

        if(!isKey)
            this.monthSpeed.put(Date.now(), changed);
        else
            this.monthSpeed.put(Date.now(), monthSpeed.get(Date.now()) + changed);
    }

    public Map<Long, Book> getBooks() {
        return books;
    }

    public Map<Date, Integer> getSpeed() {
        return monthSpeed;
    }

    public Map<Date, Integer> getStat() {
        return monthStat;
    }

    public void updateDataAddBook(Book newBook) {
        newBook.setId(books.size() + 1);
        books.put((long) books.size() + 1, newBook);

        // Добавление прочитанных страниц в статистику
        addPagesAtMonthSpeed(newBook.getCompletePages());
        addPagesAtMonthStat(newBook.getCompletePages());

        updateFileBooks();
    }

    private void updateFileBooks() {
        dataBaseConnectivity.updateData(this.books, this.monthStat, this.monthSpeed);
    }
}
