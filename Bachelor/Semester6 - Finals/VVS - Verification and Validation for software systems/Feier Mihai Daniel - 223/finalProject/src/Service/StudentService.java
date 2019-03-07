package Service;

import Domain.Student;
import Exceptions.ValidationException;
import Observers.Observable;
import Observers.Observer;
import Repository.StudentRepository;
import Utils.ChangeEventType;
import Utils.StudentEvent;
import Validators.StudentValidator;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentService implements Observable<StudentEvent> {
    StudentRepository studentRepository;
    private List<Observer<StudentEvent>> observers = new ArrayList<>();

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentValidator studentValidator = new StudentValidator();

    public Student createStudent(Student student) throws ValidationException, SQLException {
        studentValidator.validateStudent(student.getName(), student.getGroup(), student.getEmail(), student.getAssistant());
        Student tempStudent =  studentRepository.save(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.ADD, tempStudent));
        }

        return tempStudent;
    }

    public Student findStudent(Integer id) throws SQLException {
        return studentRepository.findOne(id);
    }

    public Iterable<Student> findAll() throws SQLException {
        return studentRepository.findAll();
    }

    public Student updateName(Integer id, String name) throws SQLException {
        Student student = studentRepository.findOne(id);
        student.setName(name);
        Student tempStudent =  studentRepository.update(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.UPDATE, tempStudent));
        }

        return tempStudent;
    }

    public Student updateGroup(Integer id, Integer group) throws SQLException {
        Student student = studentRepository.findOne(id);
        student.setGroup(group);

        Student tempStudent =  studentRepository.update(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.UPDATE, tempStudent));
        }

        return tempStudent;
    }

    public Student updateEmail(Integer id, String email) throws SQLException {
        Student student = studentRepository.findOne(id);
        student.setEmail(email);
        Student tempStudent =  studentRepository.update(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.UPDATE, tempStudent));
        }

        return tempStudent;
    }

    public Student updateAssistant(Integer id, String assistant) throws SQLException {
        Student student = studentRepository.findOne(id);
        student.setAssistant(assistant);
        Student tempStudent =  studentRepository.update(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.UPDATE, tempStudent));
        }

        return tempStudent;
    }

    public Student deleteStudent(Integer id) throws SQLException {
        Student tempStudent =  studentRepository.delete(id);
        if (tempStudent != null) {
            notifyObservers(new StudentEvent(ChangeEventType.DELETE, tempStudent));
        }

        return tempStudent;
    }

    public Student updateAll(Student student) throws SQLException {
        studentValidator.validateStudent(student.getName(), student.getGroup(), student.getEmail(), student.getAssistant());
        Student tempStudent =  studentRepository.update(student);
        if (tempStudent == null) {
            notifyObservers(new StudentEvent(ChangeEventType.UPDATE, tempStudent));
        }

        return tempStudent;
    }

    public List<Integer> getAllGroups() throws SQLException {
        List<Integer> groupList = new ArrayList<>();
        for (Student st : studentRepository.findAll()) {
            if (!groupList.contains(st.getGroup())) {
                groupList.add(st.getGroup());
            }
        }
        Collections.sort(groupList);
        return groupList;
    }

    public List<Student> getAllStudentFromGroup(Integer group) throws SQLException {
        List<Student> studentList = new ArrayList<>();

        studentRepository.findAll().forEach(st -> {
            if (st.getGroup().equals(group)) {
                studentList.add(st);
            }
        });

        return studentList;
    }

    @Override
    public void addObserver(Observer<StudentEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentEvent t) {
        observers.forEach(observer -> {
            observer.update(t);
        });
    }

}
