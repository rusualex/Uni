package Service;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Exceptions.ValidationException;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import Validators.GradeValidator;
import javafx.util.Pair;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GradeService {
    StudentRepository studentRepository;
    HomeworkRepository homeworkRepository;
    GradeRepository gradeRepository;
    Integer currentWeek;
    GradeValidator gradeValidator = new GradeValidator();

    public GradeService(StudentRepository studentRepository, HomeworkRepository homeworkRepository, GradeRepository gradeRepository) throws SQLException, ClassNotFoundException {
        this.studentRepository = studentRepository;
        this.homeworkRepository = homeworkRepository;
        this.gradeRepository = gradeRepository;

        setCurrentWeek();
    }

    public Grade findOne(Pair<Integer, Integer> id) throws SQLException {
        return gradeRepository.findOne(id);
    }

    public Iterable<Grade> findAll() throws SQLException {
        return gradeRepository.findAll();
    }

    public Integer getCurrentWeek() {
        return this.currentWeek;
    }

    public Float penalizare(Integer idHomework) throws SQLException {
        Homework homework = homeworkRepository.findOne(idHomework);
        if (homework.getDeadline() < currentWeek) {
            return - (currentWeek - homework.getDeadline()) * 2.5f;
        }
        return 0f;
    }

    public int setCurrentWeek() {
        LocalDate localDate;
        localDate = LocalDate.now();
        int date = localDate.getDayOfYear();
        date = (date + 1) / 7 + 12;
        this.currentWeek = date;
        return this.currentWeek;
    }

    public Grade createGrade(Integer idStudent, Integer idHomework, Float value, String feedback, boolean motivated) throws ValidationException, SQLException, IllegalArgumentException {
        Student student = studentRepository.findOne(idStudent);
        Homework homework = homeworkRepository.findOne(idHomework);
        gradeValidator.validateGrade(new Pair<>(idStudent, idHomework), value);

        if (!motivated) {
            if (homework.getDeadline() < currentWeek) {
                value = value - 2.5f * (currentWeek - homework.getDeadline());
                feedback = feedback + "\n" + "Nota a fost diminuata cu " + (2.5f * Float.min(currentWeek - homework.getDeadline(), 9)) + " din cauza intarzierilor.";
            }

            if (value < 1) {
                value = 1f;
            }
        }
        Grade grade = new Grade(new Pair<Integer, Integer>(idStudent, idHomework), student, homework, value, currentWeek, feedback);

        return gradeRepository.save(grade);
    }

    public Iterable<Grade> filterAllGradesHomework(Integer id) throws SQLException {
        List<Grade> gradeList = new ArrayList<>();
        for (Grade grade : gradeRepository.findAll()) {
            if (grade.getId().getValue() == id) {
                gradeList.add(grade);
            }
        }
        return gradeList;
    }

    public Iterable<Grade> filterAllGradesStudent(Integer id) throws SQLException {
        List<Grade> gradeList = new ArrayList<>();
        for (Grade grade : gradeRepository.findAll()) {
            if (grade.getId().getKey() == id) {
                gradeList.add(grade);
            }
        }
        return gradeList;
    }

    public Iterable<Grade> filterAllGradesGroupHomework(Integer group, Integer idHomework) throws SQLException {
        List<Grade> gradeList = new ArrayList<>();
        for (Grade grade : gradeRepository.findAll()) {
            if (grade.getStudent().getGroup() == group && grade.getId().getValue() == idHomework) {
                gradeList.add(grade);
            }
        }
        return gradeList;
    }

    public Iterable<Grade> filterAllGradesDate(Integer beginDate, Integer endDate) throws SQLException {
        List<Grade> gradeList = new ArrayList<>();
        for (Grade grade : gradeRepository.findAll()) {
            if (grade.getDate() > beginDate && grade.getDate() < endDate) {
                gradeList.add(grade);
            }
        }
        return gradeList;
    }


}
