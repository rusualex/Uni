package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import model.Seats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.SeatsHelper;
import services.ReservationServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class Gui extends Application {
    private static ReservationServices ctrl;
    private static final Logger logger= LogManager.getLogger();

    @Override
    public void start(Stage stage) throws Exception {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        ctrl = new ReservationServices(serverProps);
        GridPane gridLogin = new GridPane();
        gridLogin.setPadding(new Insets(10, 10, 10, 10));
        gridLogin.setVgap(5);
        gridLogin.setHgap(5);

        Label lbUser = new Label("Utilizator: ");
        Label lbPwd = new Label("Parola: ");

        TextField txtUser = new TextField();
        txtUser.setPromptText("nume utilizator");
        TextField txtPwd = new TextField();
        txtPwd.setPromptText("parola");

        Button btnLogin = new Button("Login");

        GridPane.setConstraints(lbUser, 0, 1);
        gridLogin.getChildren().add(lbUser);
        GridPane.setConstraints(txtUser, 1, 1);
        gridLogin.getChildren().add(txtUser);
        GridPane.setConstraints(lbPwd, 0, 2);
        gridLogin.getChildren().add(lbPwd);
        GridPane.setConstraints(txtPwd, 1, 2);
        gridLogin.getChildren().add(txtPwd);
        GridPane.setConstraints(btnLogin, 1, 3);
        gridLogin.getChildren().add(btnLogin);

        btnLogin.setOnAction((xx) -> {
            logger.traceEntry("Entering btnLogin");
            if (txtUser.getText().isEmpty() || txtPwd.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertizare!");
                alert.setHeaderText(null);
                alert.setContentText("Lipseste numele utilizatorului sau parola!");
                alert.showAndWait();
            } else if (!ctrl.logInService(txtUser.getText(), txtPwd.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare!");
                alert.setHeaderText(null);
                alert.setContentText("Utilizatorul sau parola este gresita!");
                alert.showAndWait();
            } else {
                logger.traceEntry("Successfull Login");
                Alert alertLogin = new Alert(Alert.AlertType.INFORMATION);
                alertLogin.setTitle("Inregistrare cu succes!");
                alertLogin.setHeaderText(null);
                alertLogin.setContentText("Utilizatorul " + txtUser.getText() + " s-a logat cu succes!");
                alertLogin.showAndWait();
                stage.close();

                ListView<String> list = new ListView<String>();
                list.setPrefSize(350, 400);

                Label lbSeatClient = new Label("Customer Name: ");
                Label lbCourseDeparture = new Label("Departure City");
                Label lbCourseDestination = new Label("Destination");
                Label lbCourseDate = new Label("Date(yyyy-mm-dd hh:mm:ss)");

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);

                GridPane gridInner = new GridPane();
                gridInner.setPadding(new Insets(10, 10, 10, 10));
                gridInner.setVgap(5);
                gridInner.setHgap(5);

                TextField txtSeatClient = new TextField();
                txtSeatClient.setPromptText("Rocky Balboa");
                TextField txtCourseDeparture = new TextField();
                txtCourseDeparture.setPromptText("Cluj-Napoca");
                TextField txtCourseDestination = new TextField();
                txtCourseDestination.setPromptText("Bucuresti");
                TextField txtCourseDate = new TextField();
                txtCourseDate.setPromptText("2019-07-10 12:45:00");

                Button btnCourses = new Button("Show courses");
                Button btnSearchCourse = new Button("Search");
                Button btnAddSeatClient = new Button("Add client");
                Button btnLogout = new Button("Logout");
                Button btnSeats = new Button("Show seats ");

                GridPane.setConstraints(list, 0, 1);
                grid.getChildren().add(list);
                GridPane.setConstraints(gridInner, 1, 1);
                grid.getChildren().add(gridInner);


                ListView<String> listSeats = new ListView<String>();
                listSeats.setPrefSize(200, 200);
                GridPane.setConstraints(listSeats, 1, 11);
                gridInner.getChildren().add(listSeats);


                GridPane.setConstraints(lbSeatClient, 1, 1);
                gridInner.getChildren().add(lbSeatClient);
                GridPane.setConstraints(txtSeatClient, 2, 1);
                gridInner.getChildren().add(txtSeatClient);
                GridPane.setConstraints(btnSearchCourse, 2, 10);
                gridInner.getChildren().add(btnSearchCourse);
                GridPane.setConstraints(lbCourseDeparture, 1, 4);
                gridInner.getChildren().add(lbCourseDeparture);
                GridPane.setConstraints(txtCourseDeparture, 2, 4);
                gridInner.getChildren().add(txtCourseDeparture);
                GridPane.setConstraints(lbCourseDestination, 1, 6);
                gridInner.getChildren().add(lbCourseDestination);
                GridPane.setConstraints(txtCourseDestination, 2, 6);
                gridInner.getChildren().add(txtCourseDestination);
                GridPane.setConstraints(lbCourseDate, 1, 8);
                gridInner.getChildren().add(lbCourseDate);
                GridPane.setConstraints(txtCourseDate, 2, 8);
                gridInner.getChildren().add(txtCourseDate);
                GridPane.setConstraints(btnAddSeatClient, 2, 2);
                gridInner.getChildren().add(btnAddSeatClient);

                GridPane.setConstraints(btnCourses, 0, 3);
                grid.getChildren().add(btnCourses);
                GridPane.setConstraints(btnLogout, 2, 3);
                grid.getChildren().add(btnLogout);
                GridPane.setConstraints(btnSeats,1,3);
                grid.getChildren().add(btnSeats);

                Scene secondScene = new Scene(grid, 650, 460);

                Stage secondStage = new Stage();
                secondStage.setTitle("Spectacole");
                secondStage.setScene(secondScene);
                secondStage.show();


                btnSeats.setOnAction((x->{
                    logger.traceEntry("Entering populate seats list grid");
                    try{
                    Object object = list.getSelectionModel().getSelectedItems().get(0);
                        System.out.println(object);
                        Course courseId = (Course)object;
                        listSeats.setItems(FXCollections.observableList((List)ctrl.findSeats(courseId.getCourseId())));}
                    catch (Exception e){
                        Alert noSelection = new Alert(Alert.AlertType.ERROR);
                        noSelection.setTitle("Selection missing");
                        noSelection.setContentText("Select a Course plese");
                    }
                    logger.traceExit("Exiting populate seats list grid");
                }));

                btnSearchCourse.setOnAction((x)->{
                    logger.traceEntry("Entering search course function");
                    String destination = txtCourseDestination.getText();
                    String departure = txtCourseDeparture.getText();
                    String dateOfDeparture = txtCourseDate.getText();
                    List<Course> courses = ctrl.findCourse(departure,destination,dateOfDeparture);
                    list.setItems(FXCollections.observableList((List)courses));
                    logger.traceExit("Exiting search course function");
                });

                btnAddSeatClient.setOnAction((x)-> {
                    logger.traceEntry("Entering add client function");
                    Object object = listSeats.getSelectionModel().getSelectedItems().get(0);
                    System.out.println(object);
                    Seats seat = (Seats)object;
                    System.out.println("Object: "+object);
                    System.out.println("Seats: "+seat);
                    String clientName = txtSeatClient.getText().toString();
                    ctrl.reserveSeat(new SeatsHelper(seat.getSeatNumber(),seat.getCourseId()),clientName);
                    object = list.getSelectionModel().getSelectedItems().get(0);
                    Course courseId = (Course)object;
                    listSeats.setItems(FXCollections.observableList((List)ctrl.findSeats(courseId.getCourseId())));
                    logger.traceExit("Exiting add client function");
                });


                btnCourses.setOnAction((x) -> {
                    logger.traceEntry("Entering show courses function");
                    list.setItems(FXCollections.observableList((List)ctrl.showAllCourses()));
                    logger.traceExit("Exiting show courses function");
                });

                btnLogout.setOnAction((x) -> {
                    logger.traceEntry("Entering logout function");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delogare cu succes!");
                    alert.setHeaderText(null);
                    alert.setContentText("Ati fost delogat cu succes!");
                    alert.showAndWait();

                    secondStage.close();
                    stage.show();
                    logger.traceExit("Exiting logout function");
                });
                logger.traceExit("Exiting login function");
            }
        });

        Scene scene = new Scene(gridLogin, 300, 150);
        stage.setTitle("Logare");
        stage.setScene(scene);
        stage.show();
    }



    public void startApp(String[] args){
        launch(args);
    }

}
