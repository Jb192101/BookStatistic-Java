package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.View;

abstract public class LocalViewModel implements InteractWindowsInterface {
    protected View window;

    @Override
    public void openWindow() {
        window.show();
    }

    @Override
    public void closeWindow() {
        window.close();
    }
}