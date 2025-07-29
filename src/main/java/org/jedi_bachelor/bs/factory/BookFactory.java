package org.jedi_bachelor.bs.factory;

import org.jedi_bachelor.bs.model.Book;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class BookFactory implements AbstractFactory<Book> {
    private String nameOfBook;
    private String authorOfBook;
    private int completePages;
    private int allPages;

    @Override
    public Book create() {
        return new Book(
                nameOfBook,
                authorOfBook,
                completePages,
                allPages);
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public void setCompletePages(int completePages) {
        this.completePages = completePages;
    }

    public void setAuthorOfBook(String authorOfBook) {
        this.authorOfBook = authorOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }
}
