package Controller;

import Observers.Observable;
import Service.FiltersService;
import Utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class AllGradesStudentController {
    Alerts alerts = new Alerts();
    FiltersController filtersController;

    ObservableList<String> students = FXCollections.observableArrayList();
    ObservableList<String> tempStudents = FXCollections.observableArrayList();
    ObservableList<Integer> groups = FXCollections.observableArrayList();

    @FXML
    ComboBox<Integer> studentGroupComboBox;

    @FXML
    ComboBox<String> studentComboBox;

    @FXML
    Button okButton;


    public void setFiltersController(FiltersController filtersController) {
        this.filtersController = filtersController;
        execute();

    }

    public void initialize() {


    }

    private void execute() {
        configureStudentComboBox();

        setGroupComboBoxValues();

        setStudentComboBoxValues();

        setStudentComboBoxValuesOnStudentGroupChanged();

        studentComboBoxSearch();

        okButtonClicked();
    }

    private void okButtonClicked() {
        okButton.setOnAction(event -> {
            try {
                Integer id = Integer.parseInt(studentComboBox.getSelectionModel().getSelectedItem().split(" # ")[1]);
                filtersController.allGradesStudentSetTable(id);
            } catch (Exception e) {
                alerts.showError("Va rugam selectati studentul.");
            }
        });
    }

    private void studentComboBoxSearch() {
        studentComboBox.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {

            String search = studentComboBox.getEditor().getText();
            tempStudents.clear();
            students.forEach(st -> {
                if (st.toLowerCase().contains(search.toLowerCase())) {
                    tempStudents.add(st);
                }
            });

            setStudentComboBox();
        }));
    }

    private void configureStudentComboBox() {
        studentComboBox.setEditable(true);
        studentComboBox.setDisable(false);
    }

    private void setStudentComboBoxValuesOnStudentGroupChanged() {
        studentGroupComboBox.setOnAction(event -> {
            setStudentComboBoxValues();
        });
    }

    private void setStudentComboBox() {
        studentComboBox.setItems(tempStudents);
    }

    private void setStudentComboBoxValues() {
        students.clear();
        try {
            Integer group = studentGroupComboBox.getSelectionModel().getSelectedItem();
            filtersController.getAllStudentsFromGroup(group).forEach(student -> {
                students.add(student.getName() + " # " + student.getId());
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        studentComboBox.setItems(students); }

    private void setGroupComboBoxValues() {
        try {
            filtersController.getAllGroups().forEach(group -> {
                groups.add(group);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }

        studentGroupComboBox.setItems(groups);
        studentGroupComboBox.getSelectionModel().selectFirst();
    }

}
