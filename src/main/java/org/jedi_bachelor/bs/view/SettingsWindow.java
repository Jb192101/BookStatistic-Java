package org.jedi_bachelor.bs.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.jedi_bachelor.bs.model.Language;
import org.jedi_bachelor.bs.model.Theme;
import org.jedi_bachelor.bs.viewmodel.SettingsViewModel;

public class SettingsWindow extends View {
    private SettingsViewModel settingsViewModel;

    public SettingsWindow(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;

        setupUI();
    }

    private void setupUI() {
        setTitle("Настройки");

        Label languageLabel = new Label("Язык:");
        ComboBox<String> languageCombo = new ComboBox<>();
        for(Language lang : Language.values()) {
            languageCombo.getItems().add(lang.getName());
        }
        languageCombo.setValue(settingsViewModel.getCurrentLang());

        Label themeLabel = new Label("Тема:");
        ComboBox<String> themeCombo = new ComboBox<>();
        for(Theme theme : Theme.values()) {
            themeCombo.getItems().add(theme.getName());
        }
        themeCombo.setValue(settingsViewModel.getCurrentTheme());

        Button applyButton = new Button("Применить");
        applyButton.setOnAction(e -> {
            settingsViewModel.applySettings(
                    languageCombo.getValue(),
                    themeCombo.getValue()
            );
            close();
        });

        Button cancelButton = new Button("Отмена");
        cancelButton.setOnAction(e -> close());

        HBox languageBox = new HBox(10, languageLabel, languageCombo);
        HBox themeBox = new HBox(10, themeLabel, themeCombo);
        HBox buttonBox = new HBox(10, applyButton, cancelButton);

        languageBox.setAlignment(Pos.CENTER_LEFT);
        themeBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(20, languageBox, themeBox, buttonBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 200);
        setScene(scene);
        setResizable(false);
    }
}
