package org.jedi_bachelor.bs.model;

import jakarta.annotation.PostConstruct;

import org.apache.commons.text.similarity.LevenshteinDistance;

import org.jedi_bachelor.bs.factory.BookFactory;
import org.jedi_bachelor.bs.utils.DataBaseConnectivity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

        System.out.println(book);
        Book existingBook = dataBaseConnectivity.getBook(book.getId());
        if(existingBook == null) {
            return;
        }

        int changedPages = book.getCompletePages() - existingBook.getCompletePages();
        if(changedPages != 0) {
            changedPages = Math.abs(changedPages);
            addPagesAtMonthStat(changedPages);
            addPagesAtMonthSpeed(changedPages);
        }

        this.books.put(book.getId(), book);

        updateBookAtDB(book);
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

        if(!isKey) {
            Date lastDate = Date.now();
            if(lastDate.getMonth() == 1) {
                lastDate.setMonth(12);
                lastDate.setYear(lastDate.getYear()-1);
            } else {
                lastDate.setMonth(Date.now().getMonth()-1);
            }

            if(monthStat.get(lastDate) != null)
                this.monthStat.put(Date.now(), changed + monthStat.get(lastDate));
            else
                this.monthStat.put(Date.now(), changed);
        } else
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
        System.out.println(newBook);

        addPagesAtMonthSpeed(newBook.getCompletePages());
        addPagesAtMonthStat(newBook.getCompletePages());

        updateFileBooks();
    }

    private void updateFileBooks() {
        dataBaseConnectivity.updateData(this.books, this.monthStat, this.monthSpeed);
    }

    private void updateBookAtDB(Book book) {
        dataBaseConnectivity.changeBook(book);
    }

    public List<Book> searchingBooksByNameAuthor(String searchText) {
        String searchLower = searchText.toLowerCase();
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        List<Book> searchResults = new ArrayList<>();

        for (Book book : this.books.values()) {
            String bookName = book.getName().toLowerCase();
            String authorName = book.getAuthor().toLowerCase();

            if (bookName.contains(searchLower) || authorName.contains(searchLower)) {
                searchResults.add(book);
                continue;
            }

            int nameDistance = levenshtein.apply(searchLower, bookName);
            int authorDistance = levenshtein.apply(searchLower, authorName);

            if (nameDistance <= 4 || authorDistance <= 4) {
                searchResults.add(book);
            }
        }

        return searchResults;
    }
}
