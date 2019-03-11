package repository;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 *
 */
public class UserRepository implements IRepository<String,User> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props){
        logger.info("Initializing UserRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from main.User")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
        logger.traceEntry("saving User {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into main.User values (?,?)")){
            preStmt.setString(1,entity.getUserName());
            preStmt.setString(2,entity.getPassWord());
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(String userName) {
        logger.traceEntry("deleting User with {}",userName);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from main.User where userName=?")){
            preStmt.setString(1,userName);
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String userName, User entity) {
        //To do
    }

    @Override
    public User findOne(String userName) {
        logger.traceEntry("finding task with id {} ",userName);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from main.User where userName=?")){
            preStmt.setString(1,userName);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    User User = createUser(result);
                    logger.traceExit(User);
                    return User;
                }
            }

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", userName);

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<User> Users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from main.User")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    User User = createUser(result);
                    Users.add(User);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(Users);
        return Users;
    }


    private User createUser(ResultSet result) throws SQLException {
        String userName = result.getString("userName");
        String userPassword = result.getString("userPassword");
        return new User(userName,userPassword);
    }

}
