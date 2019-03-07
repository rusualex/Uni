package Utils;

import Domain.Grade;
import Domain.Homework;

public class GradeEvent implements Event {
    private ChangeEventType type;
    private Grade data, oldData;

    public GradeEvent(ChangeEventType type, Grade data) {
        this.type = type;
        this.data = data;
    }

    public GradeEvent(ChangeEventType type, Grade data, Grade oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Grade getData() {
        return data;
    }

    public Grade getOldData() {
        return oldData;
    }
}
