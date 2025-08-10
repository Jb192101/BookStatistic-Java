package org.jedi_bachelor.bs.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.viewmodel.MonthStatViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

public class MonthStatWindow extends View {
    @Autowired
    @Qualifier("monthStatViewModel")
    MonthStatViewModel monthStatViewModel;

    public MonthStatWindow(MonthStatViewModel monthStatViewModel) {
        this.monthStatViewModel = monthStatViewModel;

        setupUI();
    }

    private void setupUI() {
        this.setTitle("BookStatistic - Кол-во прочитанных страниц");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        xAxis.setLabel("ММ/ГГГГ");
        yAxis.setLabel("Количество прочитанных страниц (в общем)");

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Кол-во прочитанных страниц");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Кол-во прочитанных страниц");

        Map<Date, Integer> stats = monthStatViewModel.getData();

        for (Date d : stats.keySet()) {
            String monthYear = String.format("%02d/%d", d.getMonth(), d.getYear());
            series.getData().add(new XYChart.Data<>(monthYear, stats.get(d)));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        this.setScene(scene);
    }
}
