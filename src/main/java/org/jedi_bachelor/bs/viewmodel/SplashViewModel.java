package org.jedi_bachelor.bs.viewmodel;

import org.jedi_bachelor.bs.view.SplashScreen;
import org.springframework.beans.factory.annotation.Qualifier;
import javafx.concurrent.Task;
import javafx.application.Platform;
import org.springframework.stereotype.Component;

@Component
public class SplashViewModel implements InteractWindowsInterface {
    private SplashScreen splashScreen = new SplashScreen();
    @Qualifier("mainViewModel")
    private final MainViewModel mvm;

    public SplashViewModel(MainViewModel _mvm) {
        this.mvm = _mvm;
    }

    @Override
    public void openWindow() {
        Platform.runLater(() -> {
            splashScreen.show();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(i < 70 ? 30 : 60);
                        final int progress = i;
                        Platform.runLater(() ->
                                splashScreen.updateProgress(progress / 100.0));
                    }
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                closeWindow();
                Platform.runLater(mvm::openWindow);
            });

            new Thread(task).start();
        });
    }

    @Override
    public void closeWindow() {
        Platform.runLater(splashScreen::close);
    }
}
