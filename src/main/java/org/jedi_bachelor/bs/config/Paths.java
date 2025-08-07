package org.jedi_bachelor.bs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:paths.properties")
@ConfigurationProperties(prefix = "paths")
public class Paths {
    @Value("${paths.pathToImageSplashScreen}")
    private String pathToImageSplashScreen;
    @Value("${paths.pathToImageAuthorOfProject}")
    private String pathToImageAuthorOfProject;
    @Value("${paths.pathToDescriptionOfProject}")
    private String pathToDescriptopnOfProject;

    public String getPathToImageSplashScreen() {
        return pathToImageSplashScreen;
    }

    public String getPathToImageAuthorOfProject() {
        return pathToImageAuthorOfProject;
    }

    public String getPathToDescriptionOfProject() {
        return pathToDescriptopnOfProject;
    }
}
