package Utils;

import Domain.Homework;

public class HomeworkEvent implements Event {
    private ChangeEventType type;
    private Homework data, oldData;

    public HomeworkEvent(ChangeEventType type, Homework data) {
        this.type = type;
        this.data = data;
    }

    public HomeworkEvent(ChangeEventType type, Homework data, Homework oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Homework getData() {
        return data;
    }

    public Homework getOldData() {
        return oldData;
    }
}
