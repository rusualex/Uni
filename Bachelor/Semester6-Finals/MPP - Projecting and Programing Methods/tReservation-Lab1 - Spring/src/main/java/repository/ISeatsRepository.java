package repository;
public interface ISeatsRepository<SeatsKey,Seats> {
    int size();
    void save(Seats entity);
    void delete(SeatsKey seatsKey);
    void update(SeatsKey seatsKey,Seats seats);
    Seats findOne(SeatsKey seatsKey);
    Iterable<Seats> findAll();
}
