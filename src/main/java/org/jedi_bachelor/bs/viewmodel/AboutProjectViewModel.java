package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.AboutWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AboutProjectViewModel extends LocalViewModel {
    @Autowired
    @Qualifier("mainViewModel")
    private MainViewModel mainViewModel;

    public AboutProjectViewModel(MainViewModel mvm, String pathToImage, String pathToText) {
        super(mvm);

        this.window = new AboutWindow(pathToImage, pathToText);
    }

    @Override
    public void openWindow() {
        super.openWindow();
    }

    @Override
    public void closeWindow() {
        super.closeWindow();
    }
}
