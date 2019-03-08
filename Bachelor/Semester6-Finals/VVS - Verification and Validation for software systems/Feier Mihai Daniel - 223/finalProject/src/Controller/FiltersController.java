package Controller;

import Domain.Grade;
import Domain.Student;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import Service.FiltersService;
import Service.GradeService;
import Service.HomeworkService;
import Service.StudentService;
import Utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FiltersController {
    StudentRepository studentRepository = new StudentRepository();
    HomeworkRepository homeworkRepository = new HomeworkRepository();
    GradeRepository gradeRepository = new GradeRepository(studentRepository, homeworkRepository);

    FiltersService filtersService = new FiltersService(studentRepository, homeworkRepository, gradeRepository);

    ObservableList<Grade> grades = FXCollections.observableArrayList();

    HomeworkService homeworkService = new HomeworkService(homeworkRepository);
    StudentService studentService = new StudentService(studentRepository);

    Alerts alerts = new Alerts();

    Stage primaryStage;

    @FXML
    TableView<Grade> gradeTable;
    @FXML
    TableColumn<Grade, Integer> idColumn, idStudentColumn, idHomeworkColumn, dateColumn, gradeColumn;
    @FXML
    TableColumn<Grade, String> nameStudentColumn, feedbackColumn;
    @FXML
    Button allGradesStudentButton, allGradesHomeworkButton, allGradesGroupHomeworkButton, allGradesDatePeriodButton;

    public FiltersController() throws SQLException, ClassNotFoundException {
    }

    public void initialize() {
        setTableCellProperty();

        askGradesHomeworkButtonOnClick();

        allGradesStudentButtonClicked();

        allGradesGroupHomeworkButtonClicked();

        allGradesDatePeriodButtonClicked();
    }

    private void allGradesDatePeriodButtonClicked() {
        allGradesDatePeriodButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("allGradesDatePeriod.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AllGradesDatePeriodController allGradesDatePeriodController = loader.getController();
            allGradesDatePeriodController.setFiltersController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    public void allGradesDatePeriodSetTable(Integer startDate, Integer endDate) {
        primaryStage.close();
        grades.clear();
        try {
            filtersService.allGradesDatePeriod(startDate, endDate).forEach(grade -> {
                grades.add(grade);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        gradeTable.setItems(grades);
    }

    private void allGradesGroupHomeworkButtonClicked() {
        allGradesGroupHomeworkButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("allGradesGroupHomework.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AllGradesGroupHomeworkController allGradesGroupHomeworkController = loader.getController();
            allGradesGroupHomeworkController.setFiltersController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    public void alLGradesFromGroupHomeworkSetTable(Integer idGroup, Integer idHomework) {
        primaryStage.close();
        grades.clear();
        try {
            filtersService.allGradesGroupHomework(idGroup, idHomework).forEach(grade -> {
                grades.add(grade);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        gradeTable.setItems(grades);
    }

    private void allGradesStudentButtonClicked() {
        allGradesStudentButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("allGradesStudent.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AllGradesStudentController allGradesStudentController = loader.getController();
            allGradesStudentController.setFiltersController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    public List<Integer> getAllGroups() throws SQLException {
        return studentService.getAllGroups();
    }

    public List<Student> getAllStudentsFromGroup(Integer group) throws SQLException {
        return studentService.getAllStudentFromGroup(group);
    }

    public void allGradesStudentSetTable(Integer id) {
        primaryStage.close();
        grades.clear();
        try {
            filtersService.allGradesStudent(id).forEach(grade -> {
                grades.add(grade);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        gradeTable.setItems(grades);
    }

    private void setTableCellProperty() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idStudentColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idHomeworkColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        nameStudentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
    }

    private void askGradesHomeworkButtonOnClick() {
        allGradesHomeworkButton.setOnAction(event -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("allGradesHomework.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AllGradesHomeworkController allGradesHomeworkController = loader.getController();
            allGradesHomeworkController.setFiltersController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();
        });
    }

    public List<Integer> getAllHomeoworkIds(){
        try {
            return filtersService.allHomeworkIds();
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }

        return null;
    }

    public void allGradesHomeworkSetTable(Integer id) {
        primaryStage.close();
        grades.clear();
        try {
            filtersService.allGradesHomework(id).forEach(grade -> {
                grades.add(grade);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        gradeTable.setItems(grades);
    }

    public Integer getCurrentHomework() throws SQLException {
        return homeworkService.getCurrentHomework().getId();
    }
}
