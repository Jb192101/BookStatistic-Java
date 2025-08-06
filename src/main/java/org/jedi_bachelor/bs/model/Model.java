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
        }
    }

    public Book searchBook(int id) {
        if(this.books.containsKey(id))
            return this.books.get(id);

        return null;
    }

    private void addPagesAtMonthStat(int _changed) {
        boolean isKey = false;
        for(Date d : monthStat.keySet()) {
            if(d.equals(Date.now())) {
                isKey = true;
                break;
            }
        }

        if(!isKey)
            monthStat.put(Date.now(), _changed);
        else
            monthStat.put(Date.now(), monthStat.get(Date.now()) + _changed);
    }

    private void addPagesAtMonthSpeed(int _changed) {
        boolean isKey = false;
        for(Date d : monthSpeed.keySet()) {
            if(d.equals(Date.now())) {
                isKey = true;
                break;
            }
        }

        if(!isKey)
            monthSpeed.put(Date.now(), _changed);
        else
            monthSpeed.put(Date.now(), monthSpeed.get(Date.now()) + _changed);
    }

    public Map<Long, Book> getBooks() {
        return books;
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

    public void setBookFactory(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
    }

    public void setDataBaseConnectivity(DataBaseConnectivity dataBaseConnectivity) {
        this.dataBaseConnectivity = dataBaseConnectivity;
    }
}
