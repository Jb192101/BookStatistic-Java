package org.jedi_bachelor.bs.config;

import org.jedi_bachelor.bs.factory.BookFactory;
import org.jedi_bachelor.bs.model.Model;
import org.jedi_bachelor.bs.utils.DataBaseConnectivity;
import org.jedi_bachelor.bs.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan("org.jedi_bachelor.bs")
public class AppConfig {
    @Autowired
    private Paths paths;

    @Bean
    public BookFactory bookFactory() {
        return new BookFactory();
    }

    @Bean
    public Paths paths() {
        return new Paths();
    }

    // Model
    @Bean
    public Model model() {
        return new Model();
    }

    // ViewModel-ы
    @Bean
    public MainViewModel mainViewModel() {
        return new MainViewModel();
    }

    @Bean
    public AboutProjectViewModel aboutProjectViewModel(MainViewModel mainViewModel) {
        return new AboutProjectViewModel(mainViewModel, paths.getPathToImageAuthorOfProject(),
                paths.getPathToDescriptionOfProject());
    }

    @Bean
    public SplashViewModel splashViewModel(MainViewModel mainViewModel) {
        return new SplashViewModel(mainViewModel, paths().getPathToImageSplashScreen());
    }

    @Bean
    public InputDataViewModel inputDataViewModel(MainViewModel mainViewModel) {
        return new InputDataViewModel(mainViewModel);
    }

    @Bean
    public ChangeViewModel changeViewModel(MainViewModel mainViewModel) {
        return new ChangeViewModel(mainViewModel);
    }

    @Bean
    public InputIndexViewModel inputIndexViewModel(MainViewModel mainViewModel) {
        return new InputIndexViewModel(mainViewModel);
    }

    @Bean
    public DataBaseConnectivity dataBaseConnectivity() {
        return new DataBaseConnectivity();
    }

    // ЭТО ОБЯЗАТЕЛЬНО СОХРАНИ!!!
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
