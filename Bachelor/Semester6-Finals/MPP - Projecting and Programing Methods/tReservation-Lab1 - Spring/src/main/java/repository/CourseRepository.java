package repository;

import model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 *
 */
public class CourseRepository implements ICourseRepository<Integer,Course> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CourseRepository(Properties props){
        logger.info("Initializing CourseRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from main.Course")) {
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
    public void save(Course entity) {
        logger.traceEntry("saving Course {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into main.Course values (?,?,?,?,?)")){
            preStmt.setInt(1,entity.getCourseId());
            preStmt.setString(2,entity.getDestination());
            preStmt.setString(3,entity.getDateOfDeparture());
            preStmt.setString(5,entity.getDepartureCity());
            preStmt.setInt(4,entity.getFreeSeats());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(int courseId) {
        logger.traceEntry("deleting Course with id{}",courseId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from main.Course where courseId=?")){
            preStmt.setInt(1,courseId);
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(int integer, Course entity) {
        //To do
        logger.traceEntry("update Course with id{} ",integer+"with "+entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "update main.Course set main.Course.destination=?,main.Course.dateOfDeparture=?," +
                        "depatureCity=? where main.Course.courseId=integer")){
            preStmt.setString(1,entity.getDestination());
            preStmt.setString(2,entity.getDateOfDeparture());
            preStmt.setString(3,entity.getDepartureCity());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Course findOne(String destination,String dateOfDeparture) {
//        CourseSearchHelper cSH = (CourseSearchHelper) courseSearchHelper;
        logger.traceEntry("finding task with destination{ } ",
                destination+"or date"+dateOfDeparture);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "select * from main.Course where destination=? and dateOfDeparture=?")){
            preStmt.setString(1,destination);
            preStmt.setString(2,dateOfDeparture);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Course course = createCourse(result);
                    logger.traceExit(course);
                    return course;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", destination+"  Date: "+dateOfDeparture);
        return null;
    }

    @Override
    public Iterable<Course> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Course> courses=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from main.Course")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Course course = createCourse(result);
                    courses.add(course);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(courses);
        return courses;
    }


    private Course createCourse(ResultSet result) throws SQLException {
        int courseId = result.getInt("courseId");
        String destination = result.getString("destination");
        String dateOfDeparture = result.getString("dateOfDeparture");
        String departureCity = result.getString("departureCity");
        int availableSeats = result.getInt("availableSeats");
        return new Course(courseId,destination,dateOfDeparture,departureCity,availableSeats);
    }

}
