package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.AboutWindow;

public class AboutProjectViewModel extends LocalViewModel {
    private MainViewModel mainViewModel;

    public AboutProjectViewModel(MainViewModel mvm, String pathToImage, String pathToText) {
        this.window = new AboutWindow(pathToImage, pathToText);
        this.mainViewModel = mvm;
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
