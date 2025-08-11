package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.view.ChangeWindow;
import org.springframework.stereotype.Component;

@Component
public class ChangeViewModel extends LocalViewModel implements InteractViewModelInterface {
    private Book changedBook = new Book(); // чтобы избежать NullPointerException

    public ChangeViewModel(MainViewModel mvm) {
        super(mvm);

        this.window = new ChangeWindow(this);
    }

    @Override
    public void openWindow() {
        this.window.show();
    }

    @Override
    public Book getBook() {
        return this.changedBook;
    }

    @Override
    public void setBook(Book newBook) {
        this.mainViewModel.changeBook(newBook);
        closeWindow();
        changedBook = null;
    }

    public void setBookWithoutClosingWindow(Book newBook) {
        this.changedBook = newBook;
        this.window.setData(newBook);
    }
}