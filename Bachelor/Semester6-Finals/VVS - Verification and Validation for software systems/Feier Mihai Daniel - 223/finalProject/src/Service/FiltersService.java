package Service;

import Domain.Grade;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FiltersService {
    StudentRepository studentRepository;
    HomeworkRepository homeworkRepository;
    GradeRepository gradeRepository;

    public FiltersService(StudentRepository studentRepository, HomeworkRepository homeworkRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.homeworkRepository = homeworkRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> allGradesHomework(Integer id) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        gradeRepository.findAll().forEach(grade -> {
            if (grade.getHomework().getId() == id) {
                grades.add(grade);
            }
        });

        return grades;
    }

    public List<Grade> allGradesGroupHomework(Integer idGroup, Integer idHomework) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        gradeRepository.findAll().forEach(grade -> {
            if (grade.getStudent().getGroup().equals(idGroup) && grade.getHomeworkId().equals(idHomework)) {
                grades.add(grade);
            }
        });

        return grades;
    }

    public List<Integer> allHomeworkIds() throws SQLException {
        List<Integer> homeworkIds = new ArrayList<>();
        homeworkRepository.findAll().forEach(hw -> {
            homeworkIds.add(hw.getId());
        });

        return homeworkIds;
    }

    public List<Grade> allGradesStudent(Integer id) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        gradeRepository.findAll().forEach(grade -> {
            if (grade.getStudentId() == id) {
                grades.add(grade);
            }
        });

        return grades;
    }

    public List<Grade> allGradesDatePeriod(Integer startDate, Integer endDate) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        gradeRepository.findAll().forEach(grade -> {
            if (grade.getDate() >= startDate && grade.getDate() <= endDate) {
                grades.add(grade);
            }
        });

        return grades;
    }


}
