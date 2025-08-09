package org.jedi_bachelor.bs.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import org.jedi_bachelor.bs.model.Rating;
import org.jedi_bachelor.bs.viewmodel.RatingsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatingsWindow extends View {
    private RatingsViewModel ratingsViewModel;

    public RatingsWindow(RatingsViewModel ratingsViewModel) {
        this.ratingsViewModel = ratingsViewModel;

        setupUI();
    }

    private void setupUI() {
        this.setTitle("BookStatistic - Оценки");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("ММ/ГГГГ");
        yAxis.setLabel("Количество оценок");

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Оценки");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Оценки");

        Map<Rating, Integer> stats = ratingsViewModel.getData();

        // Список кол-ва оценок (ratingList)
        // Индексы:
        //     0 - оценка LOW
        //     1 - оценка LOW_MED
        //     2 - оценка MED
        //     3 - оценка HIGH_MED
        //     4 - оценка HIGH
        List<Integer> ratingList = new ArrayList<>();
        for(Rating r : Rating.values()) {
            ratingList.add(stats.get(r));
        }

        // Рейтинги
        Rating templeRating = Rating.LOW;
        for(int i = 0; i < ratingList.size(); i++) {
            series.getData().add(new XYChart.Data<>(templeRating.getNameOfRating(), ratingList.get(i)));

            switch(templeRating) {
                case Rating.LOW -> templeRating = Rating.LOW_MED;
                case Rating.LOW_MED -> templeRating = Rating.MED;
                case Rating.MED -> templeRating = Rating.HIGH_MED;
                case Rating.HIGH_MED -> templeRating = Rating.HIGH;
            }
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        this.setScene(scene);
    }
}
