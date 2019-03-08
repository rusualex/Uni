package Controller;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Exceptions.ValidationException;
import Observers.Observer;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import Service.GradeService;
import Service.HomeworkService;
import Service.StudentService;
import Utils.Alerts;
import Utils.GradeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class GradeController implements Observer<GradeEvent> {
    StudentRepository studentRepository = new StudentRepository();
    HomeworkRepository homeworkRepository = new HomeworkRepository();
    GradeRepository gradeRepository = new GradeRepository(studentRepository, homeworkRepository);

    GradeService gradeService = new GradeService(studentRepository, homeworkRepository, gradeRepository);
    HomeworkService homeworkService = new HomeworkService(homeworkRepository);
    StudentService studentService = new StudentService(studentRepository);

    ObservableList<Integer> homeworkList = FXCollections.observableArrayList();
    ObservableList<Integer> groupList = FXCollections.observableArrayList();
    ObservableList<String> studentList = FXCollections.observableArrayList();
    ObservableList<String> tempStudentList = FXCollections.observableArrayList();

    Alerts alerts = new Alerts();

    @FXML
    Button addButton, clearButton, reportsButton, filtersButton;

    @FXML
    TextField gradeTextField;

    @FXML
    TextArea feedbackTextArea;

    @FXML
    CheckBox motivatedCheckBox;

    @FXML
    ComboBox<Integer> homeworkComboBox, groupComboBox;

    @FXML
    ComboBox<String> studentNameComboBox;


    public GradeController() throws SQLException, ClassNotFoundException {
    }


    public void initialize() {
        clear();

        add();

        setHomeworkComboBox();

        setGroupComboBox();

        setStudentNameComboBoxAfterGroupSelectionChanged();

        setStudentNameComboBoxGroupComboBoxListener();

        setStudentNameComboBoxOnValueChanged();

        reportsButtonClicked();

        filtersButtonClicked();


    }

    private void filtersButtonClicked() {
        filtersButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("filters.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Filtrari");
            primaryStage.show();
        });
    }

    private void reportsButtonClicked() {
        reportsButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("reports.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Rapoarte");
            primaryStage.show();
        });
    }


    private void setStudentNameComboBoxOnValueChanged() {
        studentNameComboBox.setEditable(true);
        studentNameComboBox.setDisable(false);


        studentNameComboBox.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {

            String search = studentNameComboBox.getEditor().getText();
            tempStudentList.clear();
            studentList.forEach(st -> {
                if (st.toLowerCase().contains(search.toLowerCase())) {
                    tempStudentList.add(st);
                }
            });
            runLater();

        }));

    }

    private void runLater() {
        studentNameComboBox.setItems(tempStudentList);
    }

    private void setStudentNameComboBoxGroupComboBoxListener() {
        groupComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            setStudentNameComboBoxAfterGroupSelectionChanged();
        }));
    }

    private void setStudentNameComboBoxAfterGroupSelectionChanged() {
        studentNameComboBox.getItems().clear();
        Integer group = groupComboBox.getValue();
        try {
            studentService.getAllStudentFromGroup(group).forEach(st -> {
                studentList.add(st.getName() + " # " + st.getId());
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        studentNameComboBox.setItems(studentList);
    }

    private void setGroupComboBox() {
        try {
            studentService.getAllGroups().forEach(g -> {
                groupList.add(g);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }

        groupComboBox.setItems(groupList);
        groupComboBox.getSelectionModel().selectFirst();
    }

    private void setHomeworkComboBox() {
        try {
            homeworkService.findAll().forEach(h -> {
                homeworkList.add(h.getId());
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }

        homeworkComboBox.setItems(homeworkList);

        try {
            homeworkComboBox.getSelectionModel().select((Integer) homeworkService.getCurrentHomework().getId());
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
    }

    private void add() {
        addButton.setOnAction(event -> {
            try {
                if (studentNameComboBox.getSelectionModel().getSelectedItem() == null) {
                    alerts.showError("Numele studentului nu poate fi gol.");
                } else {
                String student = studentNameComboBox.getSelectionModel().getSelectedItem();
                Integer idStudent = Integer.parseInt(student.split(" # ")[1]);
                Integer idHomework = homeworkComboBox.getSelectionModel().getSelectedItem();
                Float grade = Float.parseFloat(gradeTextField.getText());
                String feedback = feedbackTextArea.getText();
                boolean motivated = motivatedCheckBox.isSelected();
                Object ob = null;

                String penaltyMessage = "0";
                float penalizare = 0;

                    try {
                        penalizare = gradeService.penalizare(idHomework);
                    } catch (SQLException e) {
                        alerts.showError(e.getMessage());
                    }
                    float g = grade;
                    if (penalizare < 1 && !motivated) {
                        g = grade + penalizare;
                        if (g < 1) {
                            g = 1;
                        }
                        penaltyMessage = grade + " => " + g;
                    }

                    if (g < 1 || g > 10) {
                        alerts.showError("Nota trebuie sa fie intre 1 si 10.");
                    } else {

                        String infoMessage = "Id Student: " + idStudent + "\n" +
                                "Nume student: " + student.split(" # ")[0] + "\n" +
                                "Id tema: " + idHomework + "\n" +
                                "Nota: " + grade + "\n" +
                                "Penalizare: " + penaltyMessage + "\n" +
                                "Feedback: " + feedback;

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("");
                        alert.setHeaderText("Doriti sa adaugati aceasta nota?");
                        alert.setContentText(infoMessage);
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add("alerts.css");
                        dialogPane.getStyleClass().add("alerts");
                        Optional<ButtonType> buttonPressed = alert.showAndWait();
                        if (buttonPressed.get() == ButtonType.OK) {
                            try {
                                ob = gradeService.createGrade(idStudent, idHomework, grade, feedback, motivated);
                                if (ob == null) {
                                    alerts.showInfo("Nota a fost adaugata cu succes!");
                                } else {
                                    alerts.showError("Nota nu a putut fi adaugata!");
                                }
                            } catch (ValidationException e) {
                                alerts.showError(e.getMessage());
                            } catch (Exception e) {
                                alerts.showError(e.getMessage());
                            }


                        }
                    }
                }
                } catch(Exception e){
                    alerts.showError("Nota nu poate contine decat cifre si nu poate fi lasata goala." +
                            "\nNumele studentului nu poate fi lasat gol.");
                }

        });
    }



    private void clear() {
        clearButton.setOnAction(event -> {
            gradeTextField.clear();
            feedbackTextArea.clear();
        });
    }

    @Override
    public void update(GradeEvent gradeEvent) {

    }
}
