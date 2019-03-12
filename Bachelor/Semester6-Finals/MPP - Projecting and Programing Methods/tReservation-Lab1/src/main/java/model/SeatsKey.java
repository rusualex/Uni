package model;

public class SeatsKey {
    private int seatsNr;
    private int courseId;

    public SeatsKey(int seatsNr, int courseId) {
        this.seatsNr = seatsNr;
        this.courseId = courseId;
    }

    public int getSeatsNr() {
        return seatsNr;
    }

    public void setSeatsNr(int seatsNr) {
        this.seatsNr = seatsNr;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
