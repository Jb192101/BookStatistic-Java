package org.jedi_bachelor.bs.config;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        // Получение значений из settings.json
        try (FileReader reader = new FileReader(pathToSettings)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            this.currentLang = jsonObject.get("currentLang").getAsString();
            this.currentTheme = jsonObject.get("currentTheme").getAsString();

            reader.close();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении настроек: " + e.getMessage());

            this.currentLang = "Ru";
            this.currentTheme = "Light";
        }
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
        try (FileWriter writer = new FileWriter(pathToSettings)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(currentLang, writer);
            gson.toJson(currentTheme, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
