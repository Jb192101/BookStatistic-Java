package org.jedi_bachelor.bs.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javafx.stage.StageStyle;
import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.viewmodel.InputDataViewModel;

public class InputDataWindow extends View {
    protected final TextField titleField = new TextField();
    protected final TextField authorField = new TextField();
    protected Spinner<Integer> pagesReadSpinner;
    protected Spinner<Integer> totalPagesSpinner;
    protected final Label errorLabel = new Label();
    protected Button addButton;

    private InputDataViewModel idvm;

    public InputDataWindow(InputDataViewModel _idvm) {
        this.idvm = _idvm;
        initModality(Modality.WINDOW_MODAL);
        setTitle("Добавить книгу");
        initStyle(StageStyle.UTILITY);

        setupSpinners();
        setupUI();
        setupValidation();
    }

    protected void setupSpinners() {
        pagesReadSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        totalPagesSpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);
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

        grid.add(new Label("Название книги:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Автор книги:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Прочитано страниц:"), 0, 2);
        grid.add(pagesReadSpinner, 1, 2);
        grid.add(new Label("Всего страниц:"), 0, 3);
        grid.add(totalPagesSpinner, 1, 3);

        // Кнопка добавления
        addButton = new Button("Добавить");
        addButton.setOnAction(
                e -> validateAndAdd()
        );
        grid.add(addButton, 0, 4, 2, 1);

        // Метка для ошибок
        errorLabel.setStyle("-fx-text-fill: red;");
        grid.add(errorLabel, 0, 5, 2, 1);

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

    protected void validateAndAdd() {
        errorLabel.setText("");

        if (titleField.getText().trim().isEmpty()) {
            errorLabel.setText("Название книги не может быть пустым!");
            return;
        }

        if (authorField.getText().trim().isEmpty()) {
            errorLabel.setText("Имя автора не может быть пустым!");
            return;
        }

        int pagesRead = pagesReadSpinner.getValue();
        int totalPages = totalPagesSpinner.getValue();

        if (pagesRead > totalPages) {
            errorLabel.setText("Прочитано страниц не может быть больше общего количества!");
            return;
        }

        // Если все проверки пройдены
        idvm.setBook(new Book(
                titleField.getText().trim(),
                authorField.getText().trim(),
                pagesRead,
                totalPages
        ));

        errorLabel.setText("");
        titleField.setText("");
        authorField.setText("");
    }
}