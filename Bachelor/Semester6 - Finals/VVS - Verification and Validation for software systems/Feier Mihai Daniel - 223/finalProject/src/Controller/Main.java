package Controller;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        HomeworkRepository homeworkRepository = new HomeworkRepository();
//        StudentRepository studentRepository = new StudentRepository();
//        GradeRepository gradeRepository = new GradeRepository(studentRepository, homeworkRepository);


        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("Meniu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
