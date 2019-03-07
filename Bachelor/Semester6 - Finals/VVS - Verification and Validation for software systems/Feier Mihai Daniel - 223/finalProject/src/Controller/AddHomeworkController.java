package Controller;

import Domain.Homework;
import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddHomeworkController {
    Alerts alerts = new Alerts();
    @FXML
    TextField dateReceivedTextField, deadlineTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    Button addButton, clearButton;

    HomeworkController homeworkController;

    public void initialize() {

    }

    public void setHomeworkController(HomeworkController homeworkController) {
        this.homeworkController = homeworkController;
        execute();
    }

    public void execute() {
        clear();
        add();
    }

    private void add() {
        addButton.setOnAction(event -> {
            Integer dateReceived = null;
            Integer deadline = null;
            try {
                if (!dateReceivedTextField.getText().equals("")) {
                    dateReceived = Integer.parseInt(dateReceivedTextField.getText());
                }
                if (!deadlineTextField.getText().equals("")) {
                    deadline = Integer.parseInt(deadlineTextField.getText());
                }
                String description = descriptionTextArea.getText();
                Homework hw = new Homework(description, dateReceived, deadline);
                homeworkController.add(hw);
            } catch (Exception e) {
                alerts.showError("Campurile 'Data primire' si 'Deadline' pot contine doar cifre.");
            }
        });
    }

    private void clear() {
        clearButton.setOnAction(event -> {
            dateReceivedTextField.clear();
            deadlineTextField.clear();
            descriptionTextArea.clear();
        });
    }
}
