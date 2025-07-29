package org.jedi_bachelor.bs.config;

import org.jedi_bachelor.bs.factory.BookFactory;
import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.model.Model;
import org.jedi_bachelor.bs.utils.BinFileReader;
import org.jedi_bachelor.bs.utils.BinFileWriter;
import org.jedi_bachelor.bs.viewmodel.InputDataViewModel;
import org.jedi_bachelor.bs.viewmodel.MainViewModel;
import org.jedi_bachelor.bs.viewmodel.SplashViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Map;

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

    @Bean
    @Qualifier("bfrBooks")
    public BinFileReader<Map<Long, Book>> bfrBooks() {
        return new BinFileReader<>(paths().getPathToBooks());
    }

    @Bean
    @Qualifier("bfwBooks")
    public BinFileWriter<Map<Long, Book>> bfwBooks() {
        return new BinFileWriter<>(paths().getPathToBooks());
    }

    @Bean
    @Qualifier("bfrBooksSpeed")
    public BinFileReader<Map<Date, Integer>> bfrSpeedBooks() {
        return new BinFileReader<>(paths().getPathToSpeed());
    }

    @Bean
    @Qualifier("bfwBooksSpeed")
    public BinFileWriter<Map<Date, Integer>> bfwSpeedBooks() {
        return new BinFileWriter<>(paths().getPathToSpeed());
    }

    @Bean
    @Qualifier("bfrBooksStat")
    public BinFileReader<Map<Date, Integer>> bfrSpeedStat() {
        return new BinFileReader<>(paths().getPathToStat());
    }

    @Bean
    @Qualifier("bfwBooksStat")
    public BinFileWriter<Map<Date, Integer>> bfwSpeedStat() {
        return new BinFileWriter<>(paths().getPathToStat());
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
    public SplashViewModel splashViewModel(MainViewModel mainViewModel) {
        return new SplashViewModel(mainViewModel);
    }

    @Bean
    public InputDataViewModel inputDataViewModel(MainViewModel mainViewModel) {
        return new InputDataViewModel(mainViewModel);
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
