package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.view.InputDataWindow;
import org.springframework.beans.factory.annotation.Qualifier;

public class InputDataViewModel extends LocalViewModel implements InteractViewModelInterface {
    private Book newBook;
    @Qualifier("mainViewModel")
    private MainViewModel mvm;

    public InputDataViewModel(MainViewModel _mvm) {
        this.mvm = _mvm;
        this.window = new InputDataWindow(this);
    }

    @Override
    public Book getBook() {
        return null;
    }

    @Override
    public void setBook(Book _newBook) {
        this.newBook = _newBook;

        mvm.updateBookModel(this.newBook);
    }

    @Override
    public void openWindow() {
        this.window.show();
    }

    @Override
    public void closeWindow() {
        this.window.close();
    }
}