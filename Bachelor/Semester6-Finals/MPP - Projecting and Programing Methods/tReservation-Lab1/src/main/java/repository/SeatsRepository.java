package repository;

import model.Seats;
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
public class SeatsRepository implements IRepository<Integer,Seats> {
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
        logger.traceEntry("saving Seats {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into main.Seats values (?,?,?,?,?)")){
            preStmt.setInt(1,entity.getSeatsId());
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
    public void delete(Integer integer) {
        logger.traceEntry("deleting Seats with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from main.Seats where SeatsId=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();

        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Seats entity) {
        //To do
    }

    @Override
    public Seats findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from main.Seats where SeatsId=?")){
            preStmt.setInt(1,integer);
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
        logger.traceExit("No task found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Seats> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Seats> Seatss=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from main.Seats")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Seats Seats = createSeats(result);
                    Seatss.add(Seats);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(Seatss);
        return Seatss;
    }


    private Seats createSeats(ResultSet result) throws SQLException {
        int SeatsId = result.getInt("SeatsId");
        String destination = result.getString("destination");
        String dateOfDeparture = result.getString("dateOfDeparture");
        String departureCity = result.getString("departureCity");
        int availableSeats = result.getInt("availableSeats");
        return new Seats(SeatsId,destination,dateOfDeparture,departureCity,availableSeats);
    }

}
