package org.jedi_bachelor.bs.viewmodel;

import jakarta.annotation.PostConstruct;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import org.jedi_bachelor.bs.model.Model;
import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.view.MainWindow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.Thread.sleep;

@Component
public class MainViewModel implements InteractWindowsInterface {
    @Autowired
    private Model model;
    private MainWindow mainWindow;

    @PostConstruct
    private void init() {
        this.mainWindow = new MainWindow(this);
    }

    // ViewModel-ы
    @Autowired
    @Qualifier("aboutProjectViewModel")
    private AboutProjectViewModel avm;
    @Autowired
    @Qualifier("inputDataViewModel")
    private InputDataViewModel idvm;
    @Autowired
    @Qualifier("inputIndexViewModel")
    private InputIndexViewModel iivm;
    @Autowired
    @Qualifier("changeViewModel")
    private ChangeViewModel cvm;
    @Autowired
    @Qualifier("monthStatViewModel")
    private MonthStatViewModel msvm;
    @Autowired
    @Qualifier("speedStatViewModel")
    private SpeedStatViewModel mtvm;
    @Autowired
    @Qualifier("settingsViewModel")
    private SettingsViewModel svm;
    @Autowired
    @Qualifier("splashViewModel")
    private SplashViewModel splashViewModel;
    @Autowired
    @Qualifier("ratingsViewModel")
    private RatingsViewModel ratingsViewModel;

    // Доп. окна
    // Окно ошибки
    private Alert alertWindow;

    public void startApp() {
        Platform.runLater(() -> splashViewModel.openWindow());
    }

    @Override
    public void openWindow() {
        Platform.runLater(() -> mainWindow.show());
    }

    @Override
    public void closeWindow() {
        Platform.runLater(() -> mainWindow.close());
    }

    public void openAboutWindow() {
        Platform.runLater(() -> avm.openWindow());
    }

    /*
    Методы работы в MainWindow
     */

    public void fillingTable(ObservableList<Book> data) {
        // Обновление основного списка
        data.clear();

        try {
            Map<Long, Book> bookList = model.getBooks();
            if(bookList.isEmpty()) {
                return;
            }

            for (long i : bookList.keySet()) {
                data.add(bookList.get(i));
            }
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }

        // Обновление кол-ва оценок (+)
        ratingsViewModel.updateData();

        // Обновление скорости
        mtvm.updateData();

        // Обновление статистики
        msvm.updateData();
    }

    // Метод, возвращающий кол-во прочитанных книг
    public int getCountCompleteBooks() {
        int res = 0;
        for(long key : model.getBooks().keySet()) {
            if(model.getBooks().get(key).getEndOfReading() != null) {
                res++;
            }
        }

        return res;
    }

    // Открытие окна для добавления новой книги
    public void openInputDataWindow() {
        Platform.runLater(() -> idvm.openWindow());
    }

    public void openChangeWindow(long index) {
        Book changedBook = searchBookByID(index);
        if(changedBook != null) {
            cvm.setBookWithoutClosingWindow(changedBook);
            iivm.closeWindow();
            cvm.openWindow();
        } else {
            // Вывод сообщения об ошибке
            alertWindow  = new Alert(Alert.AlertType.CONFIRMATION, "Ошибка!", ButtonType.OK);
            alertWindow.showAndWait();
        }
        fillingTable(getDataList());
    }

    public void openInputIndexWindow() {
        Platform.runLater(() -> iivm.openWindow());
    }

    public void openMonthStatWindow() {
        Platform.runLater(() -> msvm.openWindow());
    }

    public void openMonthTempsWindow() {
        Platform.runLater(() -> mtvm.openWindow());
    }

    public void openSettingsWindow() {
        Platform.runLater(() -> svm.openWindow());
    }

    public void openRatingsWindow() {
        Platform.runLater(() -> ratingsViewModel.openWindow());
    }

    /*
    Методы для работы с моделью
     */

    public void updateBookModel(Book newBook) {
        this.model.updateDataAddBook(newBook);
    }

    private Book searchBookByID(long index) {
        return model.searchBook(index);
    }

    public void changeBook(Book book) {
        this.model.changeBook(book);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fillingTable(getDataList());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public Map<Date, Integer> getStatisticTemps() {
        return model.getSpeed();
    }

    public Map<Date, Integer> getStatisticStat() {
        return model.getStat();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ObservableList<Book> getDataList() {
        return this.mainWindow.getData();
    }

    public Map<Long, Book> getBooks() {
        return this.model.getBooks();
    }

    // Метод для поиска книг по названию/автору
    public void searchBooks(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            fillingTable(getDataList());
            return;
        }

        ObservableList<Book> searchResults = FXCollections.observableArrayList();

        searchResults.addAll(model.searchingBooksByNameAuthor(searchText));

        mainWindow.getData().setAll(searchResults);
    }

    // Для стилей
    public void setStyle(String css) {
        this.mainWindow.getScene().getStylesheets().clear();
        this.mainWindow.getScene().getStylesheets().add(css);

        // Остальные окна
        this.ratingsViewModel.getWindow().getScene().getStylesheets().clear();
        this.ratingsViewModel.getWindow().getScene().getStylesheets().add(css);

        this.avm.getWindow().getScene().getStylesheets().clear();
        this.avm.getWindow().getScene().getStylesheets().add(css);

        this.cvm.getWindow().getScene().getStylesheets().clear();
        this.cvm.getWindow().getScene().getStylesheets().add(css);

        this.idvm.getWindow().getScene().getStylesheets().clear();
        this.idvm.getWindow().getScene().getStylesheets().add(css);

        this.iivm.getWindow().getScene().getStylesheets().clear();
        this.iivm.getWindow().getScene().getStylesheets().add(css);

        this.svm.getWindow().getScene().getStylesheets().clear();
        this.svm.getWindow().getScene().getStylesheets().add(css);

        this.msvm.getWindow().getScene().getStylesheets().clear();
        this.msvm.getWindow().getScene().getStylesheets().add(css);

        this.mtvm.getWindow().getScene().getStylesheets().clear();
        this.mtvm.getWindow().getScene().getStylesheets().add(css);
    }
}
