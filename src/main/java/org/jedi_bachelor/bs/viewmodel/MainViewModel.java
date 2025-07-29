package org.jedi_bachelor.bs.viewmodel;

import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.jedi_bachelor.bs.model.Model;
import org.jedi_bachelor.bs.model.Book;

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
    //private AboutViewModel avm;
    @Autowired
    @Qualifier("inputDataViewModel")
    private InputDataViewModel idvm;
    //private InputIndexViewModel iivm;
    //private ChangeViewModel cvm;
    //private MonthStatViewModel msvm;
    //private MonthTempsViewModel mtvm;
    //private SettingsViewModel svm;
    @Autowired
    @Qualifier("splashViewModel")
    private SplashViewModel splashViewModel;

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
        //avm.openWindow();
    }

    /*
    Методы работы в MainWindow
     */

    public void fillingTable(ObservableList<Book> _data) {
        _data.clear();

        try {
            Map<Long, Book> bookList = model.getBooks();
            if(bookList.isEmpty()) {
                return;
            }

            for (long i : bookList.keySet()) {
                _data.add(bookList.get(i));
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
        idvm.openWindow();
    }

    public void openChangeWindow(int _index) {
        //Book changedBook = searchBookByID(_index);
        //System.out.println(changedBook);
        //if(changedBook != null) {
        //    cvm.setBookWithoutClosingWindow(changedBook);
        //    iivm.closeWindow();
        //    cvm.openWindow();
        //} else {
        //    // Вывод сообщения об ошибке
        //    alertWindow  = new Alert(Alert.AlertType.CONFIRMATION, LocaleManager.getString("ERROR_MESSAGE_INDEX"), ButtonType.OK);
        //    alertWindow.showAndWait();
        //}
    }

    public void openInputIndexWindow() {
        //iivm.openWindow();
    }

    public void openMonthStatWindow() {
        //msvm.openWindow();
    }

    public void openMonthTempsWindow() {
        //mtvm.openWindow();
    }

    public void openSettingsWindow() {
        //svm.openWindow();
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
    }

    /*
    public Map<Date, Integer> getStatisticTemps() {
        return model.getTemps();
    }

    public Map<Date, Integer> getStatisticStat() {
        return model.getStat();
    }
     */

    // Геттеры, сеттеры
    public void setSplashViewModel(SplashViewModel splashViewModel) {
        this.splashViewModel = splashViewModel;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
