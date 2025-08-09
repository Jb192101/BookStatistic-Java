package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.view.ChangeWindow;
import org.springframework.stereotype.Component;

@Component
public class ChangeViewModel extends LocalViewModel implements InteractViewModelInterface {
    private Book changedBook = null;

    public ChangeViewModel(MainViewModel mvm) {
        super(mvm);
    }

    @Override
    public void openWindow() {
        this.window = new ChangeWindow(this);
        this.window.show();
    }

    @Override
    public void closeWindow() {
        this.window.close();
        this.window = null;
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

    public void setBookWithoutClosingWindow(Book _newBook) {
        this.changedBook = _newBook;
    }
}