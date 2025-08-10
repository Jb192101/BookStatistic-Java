package org.jedi_bachelor.bs.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.viewmodel.SpeedStatViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

public class SpeedStatWindow extends View {
    @Autowired
    @Qualifier("speedStatViewModel")
    SpeedStatViewModel speedStatViewModel;

    public SpeedStatWindow(SpeedStatViewModel speedStatViewModel) {
        this.speedStatViewModel = speedStatViewModel;

        setupUI();
    }

    private void setupUI() {
        this.setTitle("BookStatistic - Скорость чтения");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        xAxis.setLabel("ММ/ГГГГ");
        yAxis.setLabel("Количество прочитанных страниц (в месяц)");

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Скорость чтения");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Скорость чтения");

        Map<Date, Integer> stats = speedStatViewModel.getData();

        for (Date d : stats.keySet()) {
            String monthYear = String.format("%02d/%d", d.getMonth(), d.getYear());
            series.getData().add(new XYChart.Data<>(monthYear, stats.get(d)));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        this.setScene(scene);
    }
}
