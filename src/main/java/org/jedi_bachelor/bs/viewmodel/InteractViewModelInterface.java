package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;

public interface InteractViewModelInterface {
    Book getBook();
    void setBook(Book newBook);
}