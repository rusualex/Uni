package repository;

public interface ISeatsRepository<SeatsHelper,Seats> {
    int size();
    void save(Seats entity);
    void delete(SeatsHelper seatsHelper);
    void update(SeatsHelper seatsHelper,String clientName);
    Seats findOne(SeatsHelper seatsHelper);
    Iterable<Seats> findAll();
    Iterable<Seats> findAllByCourse(int courseId);
}
