package org.jedi_bachelor.bs.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.jedi_bachelor.bs.utils.FileUtils;

import java.io.IOException;

public class AboutWindow extends View {
    public AboutWindow(String imagePath, String textPath) {
        setupUI(imagePath, textPath);
    }

    private void setupUI(String imagePath, String textPath) {
        try {
            String developerInfo = FileUtils.readDescriptopnProjectFromFile(textPath);

            this.initModality(Modality.APPLICATION_MODAL);
            this.setTitle("BookStatistic - О проекте");

            Image image = new Image(imagePath);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);

            Label infoLabel = new Label(developerInfo);
            infoLabel.setWrapText(true);
            infoLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

            Button closeButton = new Button("Закрыть");
            closeButton.setOnAction(e -> this.close());

            HBox contentBox = new HBox(20, imageView, infoLabel);
            contentBox.setAlignment(Pos.CENTER);

            VBox root = new VBox(20, contentBox, closeButton);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(20));

            Scene scene = new Scene(root, 500, 300);
            this.setScene(scene);
            this.setResizable(false);
        } catch(IOException e) {
            System.err.println("Ошибка загрузки файла: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Не удалось загрузить информацию").show();
        }
    }
}
