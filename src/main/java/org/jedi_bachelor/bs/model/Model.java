package org.jedi_bachelor.bs.model;

import jakarta.annotation.PostConstruct;

import org.jedi_bachelor.bs.factory.BookFactory;

import org.jedi_bachelor.bs.utils.BinFileReader;
import org.jedi_bachelor.bs.utils.BinFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Model {
    @Autowired
    private BookFactory bookFactory;

    /*
    * Чтецы и писцы бинарных файлов
     */
    // Для книг
    @Autowired
    @Qualifier("bfrBooks")
    private BinFileReader<Map<Long, Book>> bfrBooks;
    @Autowired
    @Qualifier("bfwBooks")
    private BinFileWriter<Map<Long, Book>> bfwBooks;

    // Для темпов чтения книг
    @Autowired
    @Qualifier("bfrBooksSpeed")
    private BinFileReader<Map<Date, Integer>> bfrBooksSpeed;
    @Autowired
    @Qualifier("bfwBooksSpeed")
    private BinFileWriter<Map<Date, Integer>> bfwBooksSpeed;

    // Для статистики чтения книг
    @Autowired
    @Qualifier("bfrBooksStat")
    private BinFileReader<Map<Date, Integer>> bfrBooksStat;
    @Autowired
    @Qualifier("bfwBooksStat")
    private BinFileWriter<Map<Date, Integer>> bfwBooksStat;

    private Map<Long, Book> books;
    private Map<Date, Integer> monthSpeed;
    private Map<Date, Integer> monthStat;

    @PostConstruct
    private void init() {
        books = bfrBooks.getObject();
        if(books == null)
            books = new HashMap<>();

        monthStat = bfrBooksStat.getObject();
        if(monthStat == null)
            monthStat = new HashMap<>();

        monthSpeed = bfrBooksSpeed.getObject();
        if(monthSpeed == null)
            monthSpeed = new HashMap<>();
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
        if(!monthStat.containsKey(Date.now()))
            monthStat.put(Date.now(), _changed);
        else
            monthStat.put(Date.now(), monthStat.get(Date.now()) + _changed);
    }

    private void addPagesAtMonthSpeed(int _changed) {
        if(!monthSpeed.containsKey(Date.now()))
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
        bfwBooks.setObject(books);
        bfwBooks.write();

        //System.out.println(monthTemps);
        bfwBooksSpeed.setObject(monthSpeed);
        bfwBooksSpeed.write();

        bfwBooksStat.setObject(monthStat);
        bfwBooksStat.write();
    }
}
