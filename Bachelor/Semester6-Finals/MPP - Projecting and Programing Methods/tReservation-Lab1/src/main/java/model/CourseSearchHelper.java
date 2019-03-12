package model;

public class CourseSearchHelper {
    private String destination;
    private String dateOfDeparture;

    public CourseSearchHelper(String destination, String dateOfDeparture) {
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }
}
