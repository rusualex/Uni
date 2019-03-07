package Domain;

import java.util.Objects;

public class Homework implements HasId<Integer> {
    Integer id;
    String description;
    Integer dateReceived;
    Integer deadline;


    public Homework(Integer id, String description, Integer dateReceived, Integer deadline) {
        this.id = id;
        this.description = description;
        this.dateReceived = dateReceived;
        this.deadline = deadline;
    }

    public Homework(String description, Integer dateReceived, Integer deadline) {
        this.id = null;
        this.description = description;
        this.dateReceived = dateReceived;
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Integer dateReceived) {
        this.dateReceived = dateReceived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homework homework = (Homework) o;
        return Objects.equals(id, homework.id) &&
                Objects.equals(description, homework.description) &&
                Objects.equals(deadline, homework.deadline) &&
                Objects.equals(dateReceived, homework.dateReceived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, deadline, dateReceived);
    }

    @Override
    public String toString() {
        return "Homework{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", dateReceived=" + dateReceived +
                ", deadline=" + deadline +
                '}';
    }

    public String toListView() {
        return "Id: " + id + "\n" +
                "Descriere: " + description + "\n" +
                "Data primire: " + dateReceived + "\n" +
                "Deadline: " + deadline;
    }

    public String toSQL() {
        return "'" + description + "'," +
                dateReceived + "," +
                deadline;
    }

    public String updateSQL() {
        return "description=" + "'" + description + "'," +
                "dateReceived=" + dateReceived + "," +
                "deadline=" + deadline;
    }
}
