package repository;

public interface ICourseRepository<Integer,Course> {
    int size();
    void save(Course entity);
    void delete(int id);
    void update(int id, Course entity);
    Course findOne(String destination, String dateOfDeparture);
    Iterable<Course> findAll();
}