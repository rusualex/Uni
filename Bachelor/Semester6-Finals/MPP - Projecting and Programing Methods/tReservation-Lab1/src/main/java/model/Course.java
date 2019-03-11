package model;

import java.util.Objects;

public class Course {
    private int courseId;
    private String destination;
    private String dateOfDeparture;
    private String departureCity;
    private int freeSeats;

    public Course(int courseId, String destination, String dateOfDeparture, String departureCity, int freeSeats) {
        this.courseId = courseId;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.departureCity = departureCity;
        this.freeSeats = freeSeats;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseId() == course.getCourseId() &&
                getFreeSeats() == course.getFreeSeats() &&
                getDestination().equals(course.getDestination()) &&
                getDateOfDeparture().equals(course.getDateOfDeparture()) &&
                getDepartureCity().equals(course.getDepartureCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getDestination(), getDateOfDeparture(), getDepartureCity(), getFreeSeats());
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", destination='" + destination + '\'' +
                ", dateOfDeparture='" + dateOfDeparture + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
