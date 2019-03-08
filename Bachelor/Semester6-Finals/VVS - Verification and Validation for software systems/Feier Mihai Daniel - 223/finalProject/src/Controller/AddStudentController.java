package Controller;

import Domain.Student;
import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddStudentController {
    Alerts alerts = new Alerts();
    @FXML
    TextField nameTextField, groupTextField, emailTextField, assistantTextField;
    @FXML
    Button addButton, clearButton;
    StudentController studentController = new StudentController();

    public AddStudentController() throws SQLException, ClassNotFoundException {
    }

    public void initialize() {

    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
        execute();
    }

    public void execute() {
        clear();

        add();
    }

    private void add() {
        addButton.setOnAction(event -> {
            String name = nameTextField.getText();
            Integer group = 0;
            try {
                if (!groupTextField.getText().equals("")) {
                    group = Integer.parseInt(groupTextField.getText());
                }
                String email = emailTextField.getText();
                String assistant = assistantTextField.getText();
                Student student = new Student(name, group, email, assistant);
                studentController.add(student);
            } catch (Exception e) {
                alerts.showError("Grupa poate contine doar cifre.");
            }
        });
    }

    private void clear() {
        clearButton.setOnAction(event -> {
            nameTextField.clear();
            groupTextField.clear();
            emailTextField.clear();
            assistantTextField.clear();
        });
    }


}
