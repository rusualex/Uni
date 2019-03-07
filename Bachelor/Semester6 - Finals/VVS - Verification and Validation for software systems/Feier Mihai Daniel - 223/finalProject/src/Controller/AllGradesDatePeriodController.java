package Controller;

import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.time.temporal.IsoFields;

public class AllGradesDatePeriodController {
    Alerts alerts = new Alerts();
    @FXML
    DatePicker startDateDatePicker, endDateDatePicker;

    @FXML
    Button okButton;

    FiltersController filtersController;

    public void initialize() {
        okButtonClicked();
    }

    private void okButtonClicked() {
        okButton.setOnAction(event -> {
            Integer startDate = 0;
            Integer endDate = 0;
            try {
                startDate = startDateDatePicker.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                startDate = calculateWeek(startDate, startDateDatePicker);
            } catch (Exception e) {
                alerts.showError("Inceputul perioadei nu a fost selectat.");
            }

            try {
                endDate = endDateDatePicker.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                endDate = calculateWeek(endDate, endDateDatePicker);
            } catch (Exception e) {
                alerts.showError("Sfarsitul perioadei nu a fost selectat.");
            }


            filtersController.allGradesDatePeriodSetTable(startDate, endDate);

        });
    }

    private Integer calculateWeek(Integer date, DatePicker startDateDatePicker) {
        if (startDateDatePicker.getValue().getYear() == 2019) {
            date += 11;
        } else if (startDateDatePicker.getValue().getYear() == 2018) {
            date -= 39;
        } else if (startDateDatePicker.getValue().getYear() > 2019) {
            date = 14;
        } else if (startDateDatePicker.getValue().getYear() < 2018) {
            date = 1;
        }

        if (date > 14) {
            date = 14;
        } else if (date < 1) {
            date = 1;
        }

        return date;
    }

    public void setFiltersController(FiltersController filtersController) {
        this.filtersController = filtersController;
    }

}
