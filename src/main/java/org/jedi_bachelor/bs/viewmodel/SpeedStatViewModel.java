package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.view.SpeedStatWindow;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpeedStatViewModel extends LocalViewModel {
    public SpeedStatViewModel(MainViewModel mainViewModel) {
        super(mainViewModel);

        this.window = new SpeedStatWindow(this);
    }

    public Map<Date, Integer> getData() {
        return this.mainViewModel.getStatisticTemps();
    }

    public void updateData() {
        this.window.updateData();
    }
}
