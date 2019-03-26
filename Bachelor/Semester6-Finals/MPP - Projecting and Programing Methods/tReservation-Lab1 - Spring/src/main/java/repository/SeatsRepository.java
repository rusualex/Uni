package repository;

import model.Course;
import model.Seats;
import model.SeatsKey;
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
public class SeatsRepository implements ISeatsRepository<SeatsKey,Seats> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public SeatsRepository(Properties props){
        logger.info("Initializing SeatsRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from main.Seats")) {
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
    public void save(Seats entity) {
        logger.traceEntry("saving Seat {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into main.Seats values (?,?,?)")){
            preStmt.setInt(1,entity.getSeatNumber());
            preStmt.setInt(2,entity.getCourseId());
            preStmt.setString(3,entity.getSeatClient());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(SeatsKey seatsKey) {
        logger.traceEntry("deleting Seats with {}",seatsKey.getSeatsNr()+"and" + seatsKey.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "update main.Seats set Seats.seatClient = '-' where Seats.seatNumber=? AND main.Seats.courseId=?")){
            preStmt.setInt(1,seatsKey.getSeatsNr());
            preStmt.setInt(2,seatsKey.getCourseId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(SeatsKey seatsKey, Seats entity) {
        //To do
        logger.traceEntry("reserving Seats with {}",seatsKey.getSeatsNr()+"and" + seatsKey.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "update main.Seats set Seats.seatClient = ? where Seats.seatNumber=? AND main.Seats.courseId=?")){
            preStmt.setString(1,entity.getSeatClient());
            preStmt.setInt(2,seatsKey.getSeatsNr());
            preStmt.setInt(3,seatsKey.getCourseId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Seats findOne(SeatsKey seatsKey) {
        logger.traceEntry("finding Seats with id {} ",seatsKey.getSeatsNr()
                +"from course: "+seatsKey.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "select * from main.Seats where seatNumber=? AND courseId=?")){
            preStmt.setInt(1,seatsKey.getSeatsNr());
            preStmt.setInt(2,seatsKey.getCourseId());
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Seats Seats = createSeats(result);
                    logger.traceExit(Seats);
                    return Seats;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No Seat found with id {}", seatsKey.getSeatsNr()
                +"from course: "+seatsKey.getCourseId());

        return null;
    }

    @Override
    public Iterable<Seats> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Seats> seats=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from main.Seats")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Seats seat = createSeats(result);
                    seats.add(seat);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(seats);
        return seats;
    }


    private Seats createSeats(ResultSet result) throws SQLException {
        int SeatsNumber = result.getInt("seatNumber");
        int courseId = result.getInt("courseId");
        String seatClient = result.getString("seatClient");
        return new Seats(SeatsNumber,courseId,seatClient);
    }

    public Iterable<Seats> getSeatsFrom(Course course){
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Seats> seats=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(
                "select * from main.Seats where courseId=?")) {
            preStmt.setInt(1,course.getCourseId());
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Seats seat = createSeats(result);
                    seats.add(seat);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(seats);
        return seats;
    }

}
