package org.jedi_bachelor.bs.config;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:paths.properties")
@ConfigurationProperties(prefix = "paths")
public class Settings {
    @Value("${paths.pathToLightTheme}")
    private String lightTheme;
    @Value("${paths.pathToDarkTheme}")
    private String darkTheme;

    private String currentTheme;
    private String currentLang;

    @Value("${paths.pathToSettings}")
    private String pathToSettings;

    @PostConstruct
    private void init() {
        try {
            Path settingsPath = Paths.get(pathToSettings);

            if (!Files.exists(settingsPath)) {
                createDefaultSettings(settingsPath);
            }

            try (FileReader reader = new FileReader(settingsPath.toFile())) {
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                this.currentLang = jsonObject.get("currentLanguage").getAsString();
                this.currentTheme = jsonObject.get("currentTheme").getAsString();
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении настроек: " + e.getMessage());
            this.currentLang = "ru";
            this.currentTheme = "light";
            try {
                saveSettings();
            } catch (Exception ex) {
                System.err.println("Не удалось сохранить настройки по умолчанию: " + ex.getMessage());
            }
        }
    }

    private void createDefaultSettings(Path settingsPath) throws IOException {
        JsonObject defaultSettings = new JsonObject();
        defaultSettings.addProperty("currentLanguage", "ru");
        defaultSettings.addProperty("currentTheme", "light");

        Files.createDirectories(settingsPath.getParent());

        saveSettings();
    }

    public String getLightTheme() {
        return getClass().getResource(lightTheme).toExternalForm();
    }

    public String getDarkTheme() {
        return getClass().getResource(darkTheme).toExternalForm();
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public String getCurrentLang() {
        return currentLang;
    }

    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;

        saveSettings();
    }

    public void setCurrentLang(String currentLang) {
        this.currentLang = currentLang;

        saveSettings();
    }

    private void saveSettings() {
        try {
            Path settingsPath = Paths.get(pathToSettings);

            JsonObject settings = new JsonObject();
            settings.addProperty("currentLanguage", currentLang);
            settings.addProperty("currentTheme", currentTheme);

            try (FileWriter writer = new FileWriter(settingsPath.toFile())) {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                gson.toJson(settings, writer);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении настроек: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
