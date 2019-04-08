package repository;

public class SeatsHelper {
    private int seatsNr;
    private int courseId;

    public SeatsHelper(int seatNumber, int courseId) {
        this.seatsNr = seatNumber;
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
