package Domain;

import java.util.Objects;

public class Student implements HasId<Integer> {
    Integer id;
    String name;
    Integer group;
    String email;
    String assistant;

    public Student(Integer id, String name, Integer group, String email, String assistant) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.email = email;
        this.assistant = assistant;
    }

    public Student(String name, Integer group, String email, String assistant) {
        this.id = null;
        this.name = name;
        this.group = group;
        this.email = email;
        this.assistant = assistant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(group, student.group) &&
                Objects.equals(email, student.email) &&
                Objects.equals(assistant, student.assistant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group, email, assistant);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", email='" + email + '\'' +
                ", assistant='" + assistant + '\'' +
                '}';
    }

    public String toListView() {
        return  id +
                ", " + name +
                ", " + group +
                ", " + email +
                ", " + assistant;
    }

    public String toSQL() {
        return "'" + name + "'," + group + "," + "'" + email + "'," + "'" + assistant + "'";
    }

    public String updateSQL() {
        return "nume=" + "'" + name + "'" + ",grupa=" + "'" + group + "'" + ",email=" + "'" + email + "'" + ",asistent=" +
                "'" + assistant + "'";
    }
}
