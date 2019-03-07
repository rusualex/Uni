package Controller;

import Domain.Student;
import Observers.Observable;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import Service.StudentService;
import Utils.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ModifyStudentController {
    @FXML
    ComboBox<Integer> groupComboBox;
    @FXML
    ComboBox<String> studentNameComboBox;
    @FXML
    TextField nameTextField, groupTextField, emailTextField, assistantTextField;
    @FXML
    Button modifyButton, clearButton;

    Alerts alerts = new Alerts();
    ObservableList<Integer> groups = FXCollections.observableArrayList();
    ObservableList<String> students = FXCollections.observableArrayList();
    ObservableList<String> tempStudentList = FXCollections.observableArrayList();

    StudentController studentController = new StudentController();
    StudentRepository studentRepository = new StudentRepository();
    StudentService studentService = new StudentService(studentRepository);


    public ModifyStudentController() throws SQLException, ClassNotFoundException {
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
        execute();

    }

    public void execute() {
        setStudentNameComboBoxProperties();

        clear();
        setGroupComboBoxValues();


        setStudentNameComboBoxAfterGroupSelectionChanged();


        setGroupComboBoxListener();

        setStudentNameComboBoxSearchModifiers();

        setTextFields();


        modify();
    }

    private void modify() {
        modifyButton.setOnAction(event -> {
            Integer id = null;
            try {
                if (studentNameComboBox.getSelectionModel().getSelectedItem() != null) {
                    id = Integer.parseInt(studentNameComboBox.getSelectionModel().getSelectedItem().split(" # ")[1]);
                }
                String name = nameTextField.getText();
                Integer group = null;
                if (!groupTextField.getText().equals("")) {
                    group = Integer.parseInt(groupTextField.getText());
                }
                String email = emailTextField.getText();
                String assistant = assistantTextField.getText();
                Student tempStudent = new Student(id, name, group, email, assistant);
                studentController.modify(tempStudent);
            } catch (Exception e) {
                alerts.showError("Grupa poate contine doar cifre.");
            }
        });
    }

    private void setTextFields() {
        if (studentNameComboBox.getSelectionModel().getSelectedItem() != null) {
            String st = studentNameComboBox.getSelectionModel().getSelectedItem();

            Student student;
            try {
                student = studentService.findStudent(Integer.parseInt(st.split(" # ")[1]));
                nameTextField.setText(student.getName());
                groupTextField.setText(student.getGroup().toString());
                emailTextField.setText(student.getEmail());
                assistantTextField.setText(student.getAssistant());
            } catch (SQLException e) {
                alerts.showError(e.getMessage());
            }
        }
    }

    private void setStudentNameComboBoxSearchModifiers() {
        studentNameComboBox.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            setTextFields();
            String search = studentNameComboBox.getEditor().getText();
            tempStudentList.clear();
            students.forEach(st -> {
                if (st.toLowerCase().contains(search.toLowerCase())) {
                    tempStudentList.add(st);
                }
            });
            runLater();

        }));
    }

    private void setGroupComboBoxListener() {
        groupComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            setStudentNameComboBoxAfterGroupSelectionChanged();
        });
    }

    private void setGroupComboBoxValues() {
        try {
            studentService.getAllGroups().forEach(group -> {
                groups.add(group);
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        groupComboBox.setItems(groups);
        groupComboBox.getSelectionModel().selectFirst();
    }

    private void clear() {
        clearButton.setOnAction(event -> {
            nameTextField.clear();
            groupTextField.clear();
            emailTextField.clear();
            assistantTextField.clear();
        });
    }

    private void setStudentNameComboBoxProperties() {
        studentNameComboBox.setEditable(true);
        studentNameComboBox.setDisable(false);
    }

    private void runLater() {
        studentNameComboBox.setItems(tempStudentList);
    }

    private void setStudentNameComboBoxAfterGroupSelectionChanged() {
        Integer group = groupComboBox.getSelectionModel().getSelectedItem();
        students.clear();
        try {
            studentService.getAllStudentFromGroup(group).forEach(student -> {
                students.add(student.getName() + " # " + student.getId());
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
        studentNameComboBox.setItems(students);
    }


}
