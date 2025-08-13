package org.jedi_bachelor.bs.viewmodel;

import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import org.jedi_bachelor.bs.config.Settings;
import org.jedi_bachelor.bs.view.SettingsWindow;

public class SettingsViewModel extends LocalViewModel {
    private final Settings settings;

    public SettingsViewModel(MainViewModel mainViewModel, Settings settings) {
        super(mainViewModel);
        this.settings = settings;

        this.window = new SettingsWindow(this);
    }

    @PostConstruct
    private void init() {
        Platform.runLater(() -> {
                applyTheme(settings.getCurrentTheme());
            }
        );
    }

    public void applySettings(String theme) {
        settings.setCurrentTheme(theme);

        applyTheme(theme);
    }

    private void applyTheme(String theme) {
        switch (theme) {
            case "Тёмная":
                applyDarkTheme();
                break;
            default:
                applyLightTheme();
        }
    }

    // Темы
    private void applyDarkTheme() {
        String css = settings.getDarkTheme();
        mainViewModel.setStyle(css);
    }

    private void applyLightTheme() {
        String css = settings.getLightTheme();
        mainViewModel.setStyle(css);
    }

    // Геттеры
    public String getCurrentTheme() {
        return settings.getCurrentTheme();
    }
}
