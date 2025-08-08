package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.view.InputDataWindow;
import org.springframework.stereotype.Component;

@Component
public class InputDataViewModel extends LocalViewModel implements InteractViewModelInterface {
    private Book newBook;

    public InputDataViewModel(MainViewModel mvm) {
        super(mvm);

        this.window = new InputDataWindow(this);
    }

    @Override
    public Book getBook() {
        return null;
    }

    @Override
    public void setBook(Book newBook) {
        this.newBook = newBook;

        this.mainViewModel.updateBookModel(this.newBook);
        this.mainViewModel.fillingTable(this.mainViewModel.getDataList());
    }
}