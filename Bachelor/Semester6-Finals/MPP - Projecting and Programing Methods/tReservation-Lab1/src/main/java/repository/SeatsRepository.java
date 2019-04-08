package repository;

import model.Seats;
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
public class SeatsRepository implements ISeatsRepository<SeatsHelper,Seats> {
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
    public void delete(SeatsHelper seatsHelper) {
        logger.traceEntry("deleting Seats with {}",seatsHelper.getSeatsNr()+"and" + seatsHelper.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "update main.Seats set Seats.seatClient = '-' where Seats.seatNumber=? AND main.Seats.courseId=?")){
            preStmt.setInt(1,seatsHelper.getSeatsNr());
            preStmt.setInt(2,seatsHelper.getCourseId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(SeatsHelper seatsHelper, String clientName) {
        //To do
        logger.traceEntry("reserving Seats with {}",seatsHelper.getSeatsNr()+"and" + seatsHelper.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "update Seats set seatClient = ? where seatNumber=? AND courseId=?")){
            preStmt.setString(1,clientName);
            preStmt.setInt(2,seatsHelper.getSeatsNr());
            preStmt.setInt(3,seatsHelper.getCourseId());
            int result=preStmt.executeUpdate();
            System.out.println("Seat: "+ seatsHelper.getSeatsNr()+" "+seatsHelper.getCourseId()+" "+clientName);
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Seats findOne(SeatsHelper seatsHelper) {
        logger.traceEntry("finding Seats with id {} ",seatsHelper.getSeatsNr()
                +"from course: "+seatsHelper.getCourseId());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement(
                "select * from main.Seats where seatNumber=? AND courseId=?")){
            preStmt.setInt(1,seatsHelper.getSeatsNr());
            preStmt.setInt(2,seatsHelper.getCourseId());
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
        logger.traceExit("No Seat found with id {}", seatsHelper.getSeatsNr()
                +"from course: "+seatsHelper.getCourseId());

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

    @Override
    public Iterable<Seats> findAllByCourse(int courseId){
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Seats> seats=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement(
                "select * from main.Seats where courseId=?")) {
            preStmt.setInt(1,courseId);
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
