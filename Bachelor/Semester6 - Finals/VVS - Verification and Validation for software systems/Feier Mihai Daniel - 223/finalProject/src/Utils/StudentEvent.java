package Utils;

import Domain.Student;

public class StudentEvent implements Event {
    private ChangeEventType type;
    private Student data, oldData;

    public StudentEvent(ChangeEventType type, Student data) {
        this.type = type;
        this.data = data;
    }

    public StudentEvent(ChangeEventType type, Student data, Student oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Student getData() {
        return data;
    }

    public Student getOldData() {
        return oldData;
    }
}
