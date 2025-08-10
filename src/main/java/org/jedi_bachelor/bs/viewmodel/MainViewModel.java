package org.jedi_bachelor.bs.viewmodel;

import jakarta.annotation.PostConstruct;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import org.apache.commons.text.similarity.LevenshteinDistance;

import org.jedi_bachelor.bs.model.Model;
import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Date;

import org.jedi_bachelor.bs.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MainViewModel implements InteractWindowsInterface {
    @Autowired
    private Model model;
    private MainWindow mainWindow;

    @PostConstruct
    private void construct() {
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

    public void openChangeWindow(int index) {
        Book changedBook = searchBookByID(index);
        System.out.println(changedBook);
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

    private Book searchBookByID(int _index) {
        return model.searchBook(_index);
    }

    public void changeBook(Book _book) {
        this.model.changeBook(_book);
        fillingTable(getDataList());
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
        String searchLower = searchText.toLowerCase();
        LevenshteinDistance levenshtein = new LevenshteinDistance(); // <-- для определения расстояния между словами

        for (Book book : model.getBooks().values()) {
            String bookName = book.getName().toLowerCase();
            String authorName = book.getAuthor().toLowerCase();

            if (bookName.contains(searchLower) || authorName.contains(searchLower)) {
                searchResults.add(book);
                continue;
            }

            int nameDistance = levenshtein.apply(searchLower, bookName);
            int authorDistance = levenshtein.apply(searchLower, authorName);

            if (nameDistance <= 4 || authorDistance <= 4) {
                searchResults.add(book);
            }
        }

        mainWindow.getData().setAll(searchResults);
    }
}
