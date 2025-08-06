package org.jedi_bachelor.bs.utils;

import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.jedi_bachelor.bs.exceptions.AllPagesLowThanCompleteException;
import org.jedi_bachelor.bs.exceptions.NegativePagesException;
import org.jedi_bachelor.bs.model.*;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class DataBaseConnectivity implements DataConnectivity {
    private SessionFactory factory;

    public DataBaseConnectivity() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(MonthStat.class)
                .addAnnotatedClass(SpeedStat.class)
                .buildSessionFactory();
    }

    // Обновление всех данных
    @Transactional
    public void updateData(Map<Long, Book> listBooks, Map<Date, Integer> listStat, Map<Date, Integer> listSpeed) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        for (Book b : listBooks.values()) {
            session.persist(b);
        }

        for (Date d : listStat.keySet()) {
            MonthStat m = new MonthStat(d, listStat.get(d));
            session.persist(m);
        }

        for (Date d : listSpeed.keySet()) {
            SpeedStat m = new SpeedStat(d, listStat.get(d));
            session.persist(m);
        }

        session.getTransaction().commit();
    }

    // Добавление книги в таблицу
    public void addBook(Book book) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        session.persist(book);

        session.getTransaction().commit();
    }

    public @NotNull Map<Long, Book> getBooks() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Book> list = session.createQuery("from Book", Book.class).getResultList();

        Map<Long, Book> map = new HashMap<>();
        for(Book b : list) {
            map.put(b.getId(), b);
        }

        session.getTransaction().commit();

        return map;
    }

    // Нахождение книги по id
    public Book getBook(long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        session.getTransaction().commit();

        return book;
    }

    public void updateName(long id, String name) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            book.setName(name);
        }

        session.getTransaction().commit();
    }

    public void updateAuthor(long id, String author) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            book.setAuthor(author);
        }

        session.getTransaction().commit();
    }

    public void updateCompletePages(long id, int completePages) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            try {
                book.setCompletePages(completePages);
            } catch (NegativePagesException e) {
                throw new RuntimeException(e);
            } catch (AllPagesLowThanCompleteException e) {
                throw new RuntimeException(e);
            }
        }

        session.getTransaction().commit();
    }

    public void updateAllPages(long id, int allPages) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            try {
                book.setAllPages(allPages);
            } catch (NegativePagesException e) {
                throw new RuntimeException(e);
            }
        }

        session.getTransaction().commit();
    }

    public void updateEndOfReading(long id, LocalDate date) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            book.setEndOfReading(date);
        }

        session.getTransaction().commit();
    }

    public void updateRating(long id, Rating rating) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Book book = session.find(Book.class, id);
        if(book != null) {
            book.setRating(rating);
        }

        session.getTransaction().commit();
    }

    // Методы для работы с таблицей темпов чтения
    public @NotNull Map<Date, Integer> getSpeedStat() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<SpeedStat> list = session.createQuery("from SpeedStat", SpeedStat.class).getResultList();

        Map<Date, Integer> map = new HashMap<>();
        for(SpeedStat b : list) {
            map.put(b.getDate(), b.getCountOfReaded());
        }

        session.getTransaction().commit();

        return map;
    }


    // Методы для работы с таблицей статистики чтения
    public @NotNull Map<Date, Integer> getMonthStat() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<MonthStat> list = session.createQuery("from MonthStat", MonthStat.class).getResultList();

        Map<Date, Integer> map = new HashMap<>();
        for(MonthStat b : list) {
            map.put(b.getDate(), b.getCountOfReaded());
        }

        session.getTransaction().commit();

        return map;
    }
}
