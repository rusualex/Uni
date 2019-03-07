package Controller;


import Domain.Student;
import Exceptions.ValidationException;
import Observers.Observer;
import Repository.StudentRepository;
import Service.StudentService;
import Utils.Alerts;
import Utils.Event;
import Utils.StudentEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class StudentController implements Observer<StudentEvent> {
    ObservableList<Student> students = FXCollections.observableArrayList();
    Alerts alerts = new Alerts();
    StudentRepository studentRepository = new StudentRepository();
    StudentService studentService = new StudentService(studentRepository);
    Stage primaryStage;

    @FXML
    TableColumn<Student, Integer> idColumn;
    @FXML
    TableColumn<Student, String> nameColumn;
    @FXML
    TableColumn<Student, Integer> groupColumn;
    @FXML
    TableColumn<Student, Double> emailColumn;
    @FXML
    TableColumn<Student, String> assistantColumn;
    @FXML
    TableView<Student> studentsTable;

    @FXML
    Button addButton, modifyButton, deleteButton;


    public StudentController() throws SQLException, ClassNotFoundException {
    }


    public void initialize() {
        studentService.addObserver(this);

        setColumns();

        update();

        add();


        modify();

        delete();


    }

    private void delete() {
        deleteButton.setOnAction(e -> {
            Student st = studentsTable.getSelectionModel().getSelectedItem();
            if (st != null) {
                Integer id = st.getId();
                Object ob = null;
                try {
                    ob = studentService.deleteStudent(id);
                    if (ob != null) {
                        alerts.showInfo("Studentul a fost sters cu succes!");
                    } else {
                        alerts.showError("Studentul nu a putut fi sters.");
                    }
                } catch (Exception e1) {
                    alerts.showError(e1.getMessage());
                }
            }

        });
    }


    public void modify(Student student) {
        primaryStage.close();
        Object ob = null;
        try {
            ob = studentService.updateAll(student);
            if (ob == null) {
                alerts.showInfo("Studentul a fost modificat cu succes!");
            } else {
                alerts.showError("Studentul nu a putut fi modificat");
            }
        } catch (Exception e1) {
            alerts.showError(e1.getMessage());
        }

    }

    private void modify() {
        modifyButton.setOnAction(e -> {
            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyStudent.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            ModifyStudentController modifyStudentController = loader.getController();
            modifyStudentController.setStudentController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();


        });
    }


    public void add(Student student) {
        primaryStage.close();
        Object ob = null;
        try {
            ob = studentService.createStudent(student);
            if (ob == null) {
                alerts.showInfo("Studentul a fost adaugat cu succes!");
            } else {
                alerts.showError("Studentul nu a putut fi adaugat.");
            }
        } catch (Exception e) {
            alerts.showError(e.getMessage());
        }
    }

    private void add() {
        addButton.setOnAction(event -> {

            primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addStudent.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            AddStudentController addStudentController = loader.getController();
            addStudentController.setStudentController(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("");
            primaryStage.show();


        });
    }



    private void setColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        assistantColumn.setCellValueFactory(new PropertyValueFactory<>("assistant"));
    }

    private void update() {
        students.clear();

        try {
            studentService.findAll().forEach(student -> {
                students.add(student);
            });
        } catch (Exception e) {
            alerts.showError(e.getMessage());
        }

        studentsTable.setItems(students);
    }

    public void update(StudentEvent event) {
        students.clear();

        try {
            studentService.findAll().forEach(student -> {
                students.add(student);
            });
        } catch (Exception e) {
            alerts.showError(e.getMessage());
        }

        studentsTable.setItems(students);
    }

}
