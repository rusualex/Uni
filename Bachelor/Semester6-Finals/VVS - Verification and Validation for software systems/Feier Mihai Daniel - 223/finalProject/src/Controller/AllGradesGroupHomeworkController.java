package Controller;

import Utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class AllGradesGroupHomeworkController {
    @FXML
    ComboBox<Integer> groupComboBox, homeworkComboBox;
    @FXML
    Button okButton;

    ObservableList<Integer> groups = FXCollections.observableArrayList();
    ObservableList<Integer> idhomework = FXCollections.observableArrayList();

    Alerts alerts = new Alerts();
    FiltersController filtersController;

    public AllGradesGroupHomeworkController() throws SQLException, ClassNotFoundException {
    }

    public void setFiltersController(FiltersController filtersController) {
        this.filtersController = filtersController;
        execute();
    }

    public void execute() {
        setGroupComboBox();

        setHomeworkComboBox();

        okButtonClicked();
    }

    private void okButtonClicked() {
        okButton.setOnAction(event -> {
            Integer idGroup = groupComboBox.getSelectionModel().getSelectedItem();
            Integer idHomework = homeworkComboBox.getSelectionModel().getSelectedItem();
            if (idGroup != null && idHomework != null) {
                filtersController.alLGradesFromGroupHomeworkSetTable(idGroup, idHomework);
            } else {
                alerts.showError("Va rugam selectati din nou.");
            }
        });
    }

    private void setHomeworkComboBox() {
        filtersController.getAllHomeoworkIds().forEach(hw -> {
            idhomework.add(hw);
        });
        homeworkComboBox.setItems(idhomework);
        try {
            homeworkComboBox.getSelectionModel().select(filtersController.getCurrentHomework());
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
    }

    private void setGroupComboBox() {
        try {
            filtersController.getAllGroups().forEach(group -> {
                groups.add(group);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        groupComboBox.setItems(groups);
        groupComboBox.getSelectionModel().selectFirst();
    }

    public void initialize() {

    }
}
