package Validators;

import Domain.Homework;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import javafx.util.Pair;

import java.sql.SQLException;

public class GradeValidator {
    StudentRepository studentRepository = new StudentRepository();
    HomeworkRepository homeworkRepository = new HomeworkRepository();
    GradeRepository gradeRepository = new GradeRepository(studentRepository, homeworkRepository);

    public GradeValidator() throws SQLException, ClassNotFoundException {
    }

    public void validateGrade(Pair<Integer, Integer> id, Float value) throws SQLException {
        if (gradeRepository.findOne(id) != null) {
            throw new IllegalArgumentException("Un student are o singura nota.");
        }
        if (value > 10 || value < 1) {
            throw new IllegalArgumentException("Nota trebuie sa fie intre 1 si 10");
        }
    }
}
