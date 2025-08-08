package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.view.MonthStatWindow;

import java.util.Map;

public class MonthStatViewModel extends LocalViewModel {
    public MonthStatViewModel(MainViewModel mainViewModel) {
        super(mainViewModel);

        this.window = new MonthStatWindow(this);
    }

    public Map<Date, Integer> getData() {
        return this.mainViewModel.getStatisticStat();
    }
}
