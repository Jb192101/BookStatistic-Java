package org.jedi_bachelor.bs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:paths.properties")
@ConfigurationProperties(prefix = "paths")
public class Paths {
    @Value("${paths.pathToFileWithBooks}")
    private String pathToBooks;
    @Value("${paths.pathToFileWithSpeedOfReading}")
    private String pathToSpeed;
    @Value("${paths.pathToFileWithStatOfReading}")
    private String pathToStat;
    @Value("${paths.pathToImageSplashScreen}")
    private String pathToImageSplashScreen;

    public String getPathToBooks() {
        return pathToBooks;
    }

    public String getPathToSpeed() {
        return pathToSpeed;
    }

    public String getPathToStat() {
        return pathToStat;
    }

    public String getPathToImageSplashScreen() {
        return pathToImageSplashScreen;
    }
}
