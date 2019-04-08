package repository;

/**
 *
 */
public interface IUserRepository<Integer, User> {
    int size();
    void save(User entity);
    void delete(int id);
    void update(int id, User entity);
    User findOne(String userName);
    Iterable<User> findAll();
    boolean logIn(User user);
}