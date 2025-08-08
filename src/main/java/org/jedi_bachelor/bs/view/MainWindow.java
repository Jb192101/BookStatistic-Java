package org.jedi_bachelor.bs.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.jedi_bachelor.bs.model.Book;
import org.jedi_bachelor.bs.model.Date;
import org.jedi_bachelor.bs.viewmodel.MainViewModel;

public class MainWindow extends Stage {
    private MainViewModel mvm;
    private BorderPane root;
    private ObservableList<Book> data;
    private Label booksCountLabel;

    public MainWindow(MainViewModel mvm) {
        this.mvm = mvm;
        setupUI();
    }

    private void setupUI() {
        root = new BorderPane();

        createTopPanel();
        createTableView();
        createButtonPanel();
        createBottomPanel();

        Scene scene = new Scene(root, 850, 600);
        //scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        root.getStyleClass().add("root");

        setTitle("BookStatistic v.1.0");
        initStyle(StageStyle.UTILITY);
        setScene(scene);
        setResizable(false);
    }

    private void createTopPanel() {
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.TOP_RIGHT);

        Button settingsButton = new Button("Настройки");
        settingsButton.setOnAction(e ->
                mvm.openSettingsWindow()
        );

        Button aboutButton = new Button("О проекте");
        aboutButton.setOnAction(e ->
                mvm.openAboutWindow()
        );

        topPanel.getChildren().addAll(settingsButton, aboutButton);
        root.setTop(topPanel);
    }

    private void createTableView() {
        TableView<Book> table = new TableView<>();

        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Название книги");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Авторство книги");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> pagesReadColumn = new TableColumn<>("Прочитано страниц");
        pagesReadColumn.setCellValueFactory(new PropertyValueFactory<>("completePages"));

        TableColumn<Book, Integer> totalPagesColumn = new TableColumn<>("Всего страниц");
        totalPagesColumn.setCellValueFactory(new PropertyValueFactory<>("allPages"));

        TableColumn<Book, Date> startDateColumn = new TableColumn<>("Дата начала чтения");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startOfReading"));

        TableColumn<Book, Date> endDateColumn = new TableColumn<>("Дата окончания чтения");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endOfReading"));

        TableColumn<Book, Float> procColumn = new TableColumn<>("% прочитан.");
        procColumn.setCellValueFactory(new PropertyValueFactory<>("procentOfReaded"));

        table.getColumns().add(idColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(authorColumn);
        table.getColumns().add(pagesReadColumn);
        table.getColumns().add(totalPagesColumn);
        table.getColumns().add(startDateColumn);
        table.getColumns().add(endDateColumn);
        table.getColumns().add(procColumn);

        data = FXCollections.observableArrayList();
        mvm.fillingTable(data);
        table.setItems(data);

        root.setCenter(table);
    }

    private void createButtonPanel() {
        HBox buttonPanel = new HBox(10);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.setAlignment(Pos.CENTER);

        Button addButton = new Button("Добавить");
        Button editButton = new Button("Изменить");
        Button statMonthButton = new Button("Статистика");
        Button statTempsButton = new Button("Скорость чтения");

        addButton.setOnAction(e ->
            mvm.openInputDataWindow()
        );

        editButton.setOnAction(e ->
            mvm.openInputIndexWindow()
        );

        statMonthButton.setOnAction(e ->
                mvm.openMonthStatWindow()
        );

        statTempsButton.setOnAction(e ->
                mvm.openMonthTempsWindow()
        );

        buttonPanel.getChildren().addAll(addButton, editButton, statMonthButton, statTempsButton);

        VBox bottomContainer = new VBox(buttonPanel);
        root.setBottom(bottomContainer);
    }

    private void createBottomPanel() {
        HBox bottomPanel = new HBox();
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.BOTTOM_RIGHT);

        booksCountLabel = new Label("Всего прочитано: " + mvm.getCountCompleteBooks());
        bottomPanel.getChildren().add(booksCountLabel);

        ((VBox) root.getBottom()).getChildren().add(bottomPanel);
    }

    public ObservableList<Book> getData() {
        return data;
    }
}