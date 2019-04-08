package repository;

import java.util.List;

public interface ICourseRepository<Integer,Course> {
    int size();
    void save(Course entity);
    void delete(int id);
    void update(int id, Course entity);
    Course findOne(String departureCity, String destination, String dateOfDeparture);
    Course findOne(int courseID);
    List<Course> findAll();
}