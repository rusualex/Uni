package Controller;
import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    Button studentButton, homeworkButton, gradeButton;

    Alerts alerts = new Alerts();


    @FXML
    public void initialize() {
        studentButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("students.fxml"));
            } catch (IOException e) {
                alerts.showError(e.getMessage());
            }
            primaryStage.setTitle("Studenti");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        });

        homeworkButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("homeworks.fxml"));
            } catch (IOException e) {
                alerts.showError(e.getMessage());
            }
            primaryStage.setTitle("Teme");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        });
        gradeButton.setOnAction(event -> {
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("grades.fxml"));
            } catch (IOException e) {
                alerts.showError(e.getMessage());
            }
            primaryStage.setTitle("Note");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        });
    }
}
