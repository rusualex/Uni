package Controller;

import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModifyDeadlineController {
    Alerts alerts = new Alerts();
    @FXML
    TextField idTextField, deadlineTextField;
    @FXML
    Button modifyButton, clearButton;

    HomeworkController homeworkController;

    public void initialize() {

    }

    public void setHomeworkController(HomeworkController homeworkController) {
        this.homeworkController = homeworkController;
        execute();
    }

    public void execute() {
        clearButton.setOnAction(event -> {
            idTextField.clear();
            deadlineTextField.clear();
        });
        modifyButton.setOnAction(event -> {
            Integer id = null;
            Integer deadline = null;
            try {
                if (!idTextField.getText().equals("")) {
                    id = Integer.parseInt(idTextField.getText());
                }
                if (!deadlineTextField.getText().equals("")) {
                    deadline = Integer.parseInt(deadlineTextField.getText());
                }
                if (id != null && deadline != null) {
                    homeworkController.modify(id, deadline);
                }
            } catch (Exception e) {
                alerts.showError("Campurile pot contine doar cifre.");
            }
        });
    }
}
