package Service;

import Domain.Grade;
import Domain.Homework;
import Domain.Student;
import Domain.StudentGrade;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportService {
    StudentRepository studentRepository;
    HomeworkRepository homeworkRepository;
    GradeRepository gradeRepository;
    Integer currentWeek;

    public ReportService(StudentRepository studentRepository, HomeworkRepository homeworkRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.homeworkRepository = homeworkRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<StudentGrade> labGradeEachStudent() throws SQLException {
        List<StudentGrade> studentGrades = new ArrayList<>();

        studentRepository.findAll().forEach(st -> {
            Float avg = 0f;
            Integer p = 0;
            try {
                for (Grade grade : gradeRepository.findAll()) {
                    if (st.getId() == grade.getStudent().getId()) {
                        avg += grade.getValue() * (grade.getHomework().getDeadline() - grade.getHomework().getDateReceived());
                        p += 1 * (grade.getHomework().getDeadline() - grade.getHomework().getDateReceived());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            studentGrades.add(new StudentGrade(st.getId(), st.getName(), avg/p));
        });

        return studentGrades;
    }

    public Homework hardestHomework() throws SQLException {
        Homework hardestHw = null;
        Float minAvg = 11f;
        Float avgGrade = 0f;
        Integer p = 0;

        for (Homework hw : homeworkRepository.findAll()) {
            p = 0;
            avgGrade = 0f;
            for (Grade grade : gradeRepository.findAll()) {
                if (hw.getId() == grade.getHomework().getId()) {
                    avgGrade += grade.getValue();
                    p += 1;
                }
            }
            avgGrade /= p;

            if (avgGrade < minAvg) {
                hardestHw = hw;
            }
        }

        return hardestHw;

    }

    public List<StudentGrade> studentsEntryExam() throws SQLException {
        List<StudentGrade> studentGrades = new ArrayList<>();

        for (StudentGrade studentGrade : labGradeEachStudent()) {
            if (!studentGrade.getGrade().isNaN() && studentGrade.getGrade() >= 4f) {
                studentGrades.add( studentGrade);
            }
        }

        return studentGrades;
    }

    public List<Student> studentsSubmittedAllHomework() throws SQLException {
        List<Student> students = new ArrayList<>();

        for(Student student : studentRepository.findAll()) {
            boolean onTime = true;
            boolean hasGrade = false;
            for (Grade grade : gradeRepository.findAll()) {

                if (student.getId() == grade.getStudent().getId()) {
                    hasGrade = true;
                    if (grade.getDate() > grade.getHomework().getDeadline()) {
                        onTime = false;
                    }
                }
            }

            if (onTime && hasGrade) {
                students.add(student);
            }
        }
        return students;
    }
}
