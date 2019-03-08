package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class Alerts {
    public void showError(String message) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setHeaderText("Eroare");
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("alerts.css");
        dialogPane.getStyleClass().add("alerts");
        alert.showAndWait();

    }
    public void showInfo(String message) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setHeaderText("Info");
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("alerts.css");
        dialogPane.getStyleClass().add("alerts");
        alert.showAndWait();
    }

}
