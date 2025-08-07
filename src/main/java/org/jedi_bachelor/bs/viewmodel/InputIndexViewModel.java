package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.InputIndexWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputIndexViewModel extends LocalViewModel {
    @Autowired
    private final MainViewModel mvm;

    public InputIndexViewModel(MainViewModel _mvm) {
        this.mvm = _mvm;

        this.window = new InputIndexWindow(this);
    }

    public void setIndex(int index) {
        mvm.openChangeWindow(index);
    }
}