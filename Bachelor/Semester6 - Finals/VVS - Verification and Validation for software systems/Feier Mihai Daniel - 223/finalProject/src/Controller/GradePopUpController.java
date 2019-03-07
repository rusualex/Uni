package Controller;

import Domain.Grade;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class GradePopUpController {
    @FXML
    TextField idStudentTextField, nameStudentTextField, idHomeworkTextField, gradeTextField, dateTextField;

    @FXML
    TextArea feedbackTextArea;

    public void initialize() {
        idStudentTextField.setEditable(false);
        nameStudentTextField.setEditable(false);
        idHomeworkTextField.setEditable(false);
        gradeTextField.setEditable(false);
        dateTextField.setEditable(false);
        feedbackTextArea.setEditable(false);
    }

    public void setValues(Grade grade) {
        idStudentTextField.setText(grade.getId().getKey().toString());
        nameStudentTextField.setText(grade.getStudentName());
        idHomeworkTextField.setText(grade.getId().getValue().toString());
        gradeTextField.setText(grade.getValue().toString());
        dateTextField.setText(grade.getDate().toString());
        feedbackTextArea.setText(grade.getFeedback());
    }


}
