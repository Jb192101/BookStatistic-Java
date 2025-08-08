package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.InputIndexWindow;
import org.springframework.stereotype.Component;

@Component
public class InputIndexViewModel extends LocalViewModel {
    public InputIndexViewModel(MainViewModel mvm) {
        super(mvm);

        this.window = new InputIndexWindow(this);
    }

    public void setIndex(int index) {
        this.mainViewModel.openChangeWindow(index);
    }
}