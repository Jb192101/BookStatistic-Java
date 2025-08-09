package org.jedi_bachelor.bs.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javafx.stage.StageStyle;
import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Rating;
import org.jedi_bachelor.bs.viewmodel.ChangeViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

public class ChangeWindow extends View {
    @Autowired
    @Qualifier("changeViewModel")
    private final ChangeViewModel cvm;

    protected final TextField titleField = new TextField();
    protected final TextField authorField = new TextField();
    protected Spinner<Integer> pagesReadSpinner;
    protected Spinner<Integer> totalPagesSpinner;
    protected ComboBox<String> ratings;
    protected final Label errorLabel = new Label();
    protected Button addButton;

    public ChangeWindow(ChangeViewModel cvm) {
        this.cvm = cvm;
        initModality(Modality.WINDOW_MODAL);
        setTitle("Изменение книги");
        initStyle(StageStyle.UTILITY);

        setupSpinners();
        setupUI();
        fillingFields();
        setupValidation();
    }

    private void fillingFields() {
        this.titleField.setText(cvm.getBook().getName());
        this.authorField.setText(cvm.getBook().getAuthor());
    }

    protected void setupSpinners() {
        this.pagesReadSpinner = new Spinner<>();
        this.totalPagesSpinner = new Spinner<>();

        SpinnerValueFactory.IntegerSpinnerValueFactory factory1 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, cvm.getBook().getCompletePages());

        pagesReadSpinner.setValueFactory(factory1);

        SpinnerValueFactory.IntegerSpinnerValueFactory factory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, cvm.getBook().getAllPages());

        totalPagesSpinner.setValueFactory(factory2);
    }

    protected void validateAndAdd() {
        Book searchingBook = new Book();

        errorLabel.setText("");

        if (titleField.getText().trim().isEmpty()) {
            errorLabel.setText("Ошибка! Название книги пустое!");
            return;
        }

        if (authorField.getText().trim().isEmpty()) {
            errorLabel.setText("Ошибка! Автор книги не заполнен!");
            return;
        }

        if (pagesReadSpinner.getValue() > totalPagesSpinner.getValue()) {
            errorLabel.setText("Ошибка! Прочитано больше, чем страниц в книге!");
            return;
        }

        if(pagesReadSpinner.getValue().equals(totalPagesSpinner.getValue())) {
            searchingBook.setEndOfReading(LocalDate.now());
        }

        searchingBook = new Book(authorField.getText(), titleField.getText(), pagesReadSpinner.getValue(), totalPagesSpinner.getValue());
        searchingBook.setRating(ratings.getValue());

        cvm.setBook(searchingBook);

        close();
    }

    protected void setupUI() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        titleField.setPromptText("Название книги");
        authorField.setPromptText("Автор книги");
        pagesReadSpinner.setEditable(true);
        totalPagesSpinner.setEditable(true);

        grid.add(new Label("Название книги"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Автор книги"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Прочитано страниц"), 0, 2);
        grid.add(pagesReadSpinner, 1, 2);
        grid.add(new Label("Всего страниц"), 0, 3);
        grid.add(totalPagesSpinner, 1, 3);

        ObservableList<String> ratingsList = FXCollections.observableArrayList(Rating.HIGH.getNameOfRating(),
                Rating.HIGH_MED.getNameOfRating(), Rating.MED.getNameOfRating(),
                Rating.LOW_MED.getNameOfRating(), Rating.LOW.getNameOfRating());
        ratings = new ComboBox<>(ratingsList);
        ratings.setValue(Rating.HIGH.getNameOfRating());

        grid.add(new Label("Оценка книги:"), 0, 4);
        grid.add(ratings, 1, 4);

        // Кнопка добавления
        addButton = new Button("Добавить");
        addButton.setOnAction(
                e -> validateAndAdd()
        );
        grid.add(addButton, 0, 5, 2, 1);

        // Метка для ошибок
        errorLabel.setStyle("-fx-text-fill: red;");
        grid.add(errorLabel, 0, 6, 2, 1);

        // Настройка сцены
        Scene scene = new Scene(grid);
        setScene(scene);
        setResizable(false);
    }

    protected void setupValidation() {
        totalPagesSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (pagesReadSpinner.getValue() > newVal) {
                pagesReadSpinner.getValueFactory().setValue(newVal);
            }
        });
    }
}