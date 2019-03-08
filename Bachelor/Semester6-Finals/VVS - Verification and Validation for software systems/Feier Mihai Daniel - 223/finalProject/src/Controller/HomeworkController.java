package Controller;

import Domain.Homework;
import Domain.Student;
import Exceptions.ValidationException;
import Observers.Observer;
import Repository.HomeworkRepository;
import Service.HomeworkService;
import Utils.Alerts;
import Utils.HomeworkEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HomeworkController implements Observer<HomeworkEvent> {
    HomeworkRepository homeworkRepository = new HomeworkRepository();
    HomeworkService homeworkService = new HomeworkService(homeworkRepository);
    ObservableList<Homework> homeworks = FXCollections.observableArrayList();
    Alerts alerts = new Alerts();

    @FXML
    TableColumn<Homework, Integer> idColumn, dateReceivedColumn, deadlineColumn;

    @FXML
    TableColumn<Homework, String> descriptionColumn;

    @FXML
    TableView<Homework> homeworkTable;

    @FXML
    Button addButton, modifyButton;

    Stage primaryStage;

    public HomeworkController() throws SQLException, ClassNotFoundException {
    }


    @FXML
    public void initialize() {
        homeworkService.addObserver(this);
        setCellValueFactory();

        update();

        add();

        modify();

    }


    public void modify(Integer id, Integer deadline) {
        primaryStage.close();
        Object ob = null;
        try {
            ob = homeworkService.updateDeadline(id, deadline);
            if (ob == null) {
                alerts.showInfo("Deadline-ul a fost modificat cu succes!");
            } else {
                alerts.showError("Deadline-ul nu a putut fi modificat.");
            }
        } catch (Exception e) {
            alerts.showError(e.getMessage());
        }

    }

    private void modify() {
        modifyButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyDeadline.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            ModifyDeadlineController modifyDeadlineController = loader.getController();
            modifyDeadlineController.setHomeworkController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    public void add(Homework hw) {
        primaryStage.close();
        Object ob = null;
        try {
            ob = homeworkService.createHomework(hw);
            if (ob == null) {
                alerts.showInfo("Tema a fost adaugata cu succes.");
            } else {
                alerts.showError("Tema nu a putut fi adaugata.");
            }
        } catch (Exception e) {
            alerts.showError(e.getMessage());
        }

    }

    private void add() {
        addButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addHomework.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AddHomeworkController addHomeworkController = loader.getController();
            addHomeworkController.setHomeworkController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    private void setCellValueFactory() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateReceivedColumn.setCellValueFactory(new PropertyValueFactory<>("dateReceived"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
    }

    public void update() {
        homeworks.clear();
        try {
            homeworkService.findAll().forEach(hw -> {
                homeworks.add(hw);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        homeworkTable.setItems(homeworks);
    }

    @Override
    public void update(HomeworkEvent homeworkEvent) {
        homeworks.clear();
        try {
            homeworkService.findAll().forEach(hw -> {
                homeworks.add(hw);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        homeworkTable.setItems(homeworks);
    }
}
