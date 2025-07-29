package org.jedi_bachelor.bs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.jedi_bachelor.bs.config.AppConfig;
import org.jedi_bachelor.bs.viewmodel.MainViewModel;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BsApplication extends Application {
	private ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		springContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MainViewModel mainViewModel = springContext.getBean("mainViewModel", MainViewModel.class);
		Platform.runLater(mainViewModel::startApp);
	}

	@Override
	public void stop() {
		springContext.close();
	}
}
