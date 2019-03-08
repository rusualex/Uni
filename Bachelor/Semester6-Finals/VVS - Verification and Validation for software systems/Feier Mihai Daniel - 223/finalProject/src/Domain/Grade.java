package Domain;

import javafx.util.Pair;

import java.util.Objects;

public class Grade implements HasId<Pair<Integer, Integer>> {
    Pair<Integer, Integer> id;
    Student student;
    Homework homework;
    Float value;
    Integer date;
    String feedback;

    public Grade(Pair<Integer, Integer> id, Student student, Homework homework, Float value, Integer date, String feedback) {
        this.id = id;
        this.student = student;
        this.homework = homework;
        this.value = value;
        this.date = date;
        this.feedback = feedback;
    }

    @Override
    public Pair<Integer, Integer> getId() {
        return id;
    }

    public Integer getStudentId() {
        return id.getKey();
    }

    public Integer getHomeworkId() {
        return id.getValue();
    }

    public String getStudentName() {
        return student.getName();
    }

    public String getIdFormatted() {
        return id.getKey() + ", " + id.getValue();
    }

    @Override
    public void setId(Pair<Integer, Integer> id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(id, grade.id) &&
                Objects.equals(student, grade.student) &&
                Objects.equals(homework, grade.homework) &&
                Objects.equals(value, grade.value) &&
                Objects.equals(date, grade.date) &&
                Objects.equals(feedback, grade.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, homework, value, date, feedback);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", student=" + student +
                ", homework=" + homework +
                ", value=" + value +
                ", date=" + date +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    public String toSQL() {
        return id.getKey() + "," +
                id.getValue() + "," +
                value + "," +
                date + ",'" +
                feedback + "'";
    }

    public String toAlert() {
        return "Id Student: " + id.getKey() + "\n" +
                "Nume student: " + student.getName() + "\n" +
                "Id tema: " + id.getValue() + "\n" +
                "Nota: " + value + "\n" +
                "Data: " + date + "\n" +
                "Feedback: " + feedback;
    }

    public String toMail() {
        return  "<p>" + "Id Student: " + id.getKey() + "\n" + "</p>" +
                "<p>" + "Nume student: " + student.getName() + "\n" + "</p>" +
                "<p>" + "Id tema: " + id.getValue() + "\n" + "</p>" +
                "<p>" + "Nota: " + value + "\n" + "</p>" +
                "<p>" + "Data: " + date + "\n" + "</p>" +
                "<p>" + "Feedback: " + feedback +"</p>";

    }

    public String updateSQL() {
        return "idStudent='" + id.getKey() + "',idHomework='" + id.getValue() + "',value='" + value + "',date='" + date + "',feedback='" + feedback + "'";
    }
}
