package Controller;

import Utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class AllGradesHomeworkController {
    @FXML
    ComboBox<Integer> homeworkComboBox;
    FiltersController filtersController;

    @FXML
    Button okButton;
    ObservableList<Integer> homeworkId = FXCollections.observableArrayList();

    Alerts alerts = new Alerts();


    public void setFiltersController(FiltersController filtersController) {
        this.filtersController = filtersController;
        execute();

    }

    public void initialize() {

    }

    private void execute() {
        setHomeworkComboBox();

        okButtonClicked();
    }

    private void okButtonClicked() {
        okButton.setOnAction(event -> {
            Integer id = homeworkComboBox.getSelectionModel().getSelectedItem();
            filtersController.allGradesHomeworkSetTable(id);

        });
    }

    private void setHomeworkComboBox() {
            filtersController.getAllHomeoworkIds().forEach(hwid -> {
                homeworkId.add(hwid);
            });


        homeworkComboBox.setItems(homeworkId);
        try {
            homeworkComboBox.getSelectionModel().select(filtersController.getCurrentHomework());
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
    }
}
