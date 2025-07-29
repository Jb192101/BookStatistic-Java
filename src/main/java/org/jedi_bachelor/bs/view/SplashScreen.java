package org.jedi_bachelor.bs.view;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class SplashScreen extends Stage {
    private ProgressBar progressBar;
    private final String path = "/images/splash.png";

    public SplashScreen() {
        setupUI();
    }

    private void setupUI() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView splashImage = new ImageView(image);
        splashImage.setFitWidth(600);
        splashImage.setFitHeight(400);
        splashImage.setPreserveRatio(true);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(550);
        progressBar.setTranslateY(120);

        StackPane root = new StackPane(splashImage, progressBar);
        root.setStyle("-fx-background-color: transparent; -fx-padding: 10; -fx-alignment: center;");

        Scene scene = new Scene(root);
        scene.setFill(null);

        initStyle(StageStyle.TRANSPARENT);
        setScene(scene);
    }

    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }
}