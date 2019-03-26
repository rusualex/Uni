package model;

import java.util.Objects;

public class Seats {
    private int seatNumber;
    private int courseId;
    private String seatClient;

    public Seats(int seatNumber, int courseId, String seatClient) {
        this.seatNumber = seatNumber;
        this.courseId = courseId;
        this.seatClient = seatClient;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSeatClient() {
        return seatClient;
    }

    public void setSeatClient(String seatClient) {
        this.seatClient = seatClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seats)) return false;
        Seats seats = (Seats) o;
        return getSeatNumber() == seats.getSeatNumber() &&
                getCourseId() == seats.getCourseId() &&
                getSeatClient().equals(seats.getSeatClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeatNumber(), getCourseId(), getSeatClient());
    }

    @Override
    public String toString() {
        return "Seats{" +
                "seatNumber=" + seatNumber +
                ", courseId=" + courseId +
                ", seatClient='" + seatClient + '\'' +
                '}';
    }
}