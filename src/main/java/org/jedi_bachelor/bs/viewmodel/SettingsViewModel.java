package org.jedi_bachelor.bs.viewmodel;

import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import org.jedi_bachelor.bs.config.Settings;
import org.jedi_bachelor.bs.view.SettingsWindow;

public class SettingsViewModel extends LocalViewModel {
    private Settings settings;

    public SettingsViewModel(MainViewModel mainViewModel, Settings settings) {
        super(mainViewModel);
        this.settings = settings;

        this.window = new SettingsWindow(this);
    }

    @PostConstruct
    private void init() {
        Platform.runLater(() -> {
                applyTheme(settings.getCurrentTheme());
                applyLang(settings.getCurrentLang());
            }
        );
    }

    public void applySettings(String lang, String theme) {
        settings.setCurrentLang(lang);
        settings.setCurrentTheme(theme);

        applyLang(lang);
        applyTheme(theme);
    }

    private void applyLang(String lang) {
        switch(lang) {
            case "ru":
                applyRuLang();
            default:
                applyEnLang();
        }
    }

    private void applyTheme(String theme) {
        switch (theme) {
            case "dark":
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

    // Языки
    private void applyRuLang() {

    }

    private void applyEnLang() {

    }

    // Геттеры
    public String getCurrentTheme() {
        return settings.getCurrentTheme();
    }

    public String getCurrentLang() {
        return settings.getCurrentLang();
    }
}
