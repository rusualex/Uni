using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tReservation_Lab1CS.repository
{
    class SeatsRepository : ISeatsRepository
    {
        private static readonly ILog log = LogManager.GetLogger("SeatsRepository");
        IDictionary<String, string> props;
        public SeatsRepository(IDictionary<string, string> props)
        {
            log.Info("Creating SeatsRepository");
            this.props = props;
        }
        
        public void delete(SeatsHelper seatsHelper)
        {
            log.InfoFormat("Entering deleteCourse by seatsHelper: {0}, {1}", seatsHelper.SeatNumber.ToString() + seatsHelper.CourseId.ToString());
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "DELETE FROM seats WHERE courseId = @courseId and seatNr = @seatNr";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                IDbDataParameter paramSeatNumber = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = seatsHelper.CourseId;
                comm.Parameters.Add(paramCourseId);
                paramSeatNumber.ParameterName = "@seatNr";
                paramSeatNumber.Value = seatsHelper.SeatNumber;
                comm.Parameters.Add(paramSeatNumber);
                rowsAffected = comm.ExecuteNonQuery();
            }
            log.InfoFormat("Exiting deleteSeats with {0} rows affected", rowsAffected);
            con.Close();
        }

        public List<Seats> findAllByCourse(int courseId)
        {
            log.InfoFormat("Entering findAllbyCourse-Seats with value {0}", courseId);
            IDbConnection con = DBUtils.GetConnection(props);
            List<Seats> seats = new List<Seats>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Seats where courseId = @courseId";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = courseId;
                comm.Parameters.Add(paramCourseId);
                using (var dataR = comm.ExecuteReader())
                {
                    
                    while (dataR.Read())
                    {
                        int cId = dataR.GetInt32(1);
                        int seatNumber = dataR.GetInt32(0);
                        string seatClient = dataR.GetString(2);
                        Seats seat = new Seats()
                        {
                            SeatNumber = seatNumber,
                            CourseId = cId,
                            SeatClient = seatClient
                        };
                        seats.Add(seat);
                    }

                }   
            }
            con.Close();
            log.InfoFormat("Exiting findAllbyCourse-Seats");
            return seats;
        }

        public Seats findOneBySeatNumber(SeatsHelper seatsHelper)
        {
            log.InfoFormat("Entering findAllbyCourse-Seats with value {0} and {1}",seatsHelper.SeatNumber,seatsHelper.CourseId );
            IDbConnection con = DBUtils.GetConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Seats where courseId = @courseId and seatNumber = @seatNumber";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = seatsHelper.CourseId;
                comm.Parameters.Add(paramCourseId);

                IDbDataParameter paramSeatNumber = comm.CreateParameter();
                paramSeatNumber.ParameterName = "@seatNumber";
                paramSeatNumber.Value = seatsHelper.SeatNumber;
                comm.Parameters.Add(paramSeatNumber);

                using (var dataR = comm.ExecuteReader())
                {

                    if (dataR.Read())
                    {
                        int cId = dataR.GetInt32(1);
                        int seatNumber = dataR.GetInt32(0);
                        string seatClient = dataR.GetString(2);
                        Seats seat = new Seats()
                        {
                            SeatNumber = seatNumber,
                            CourseId = cId,
                            SeatClient = seatClient
                        };
                        log.InfoFormat("Exiting findAllbyCourse-Seats with value {0}", seat);
                        con.Close();
                        return seat;
                    }

                }
            }
            con.Close();
            log.InfoFormat("Exiting findAllbyCourse-Seats");
            return null;
        }

        public void save(Seats seats)
        {
            log.InfoFormat("Entering saveSeats with values for {0}", seats.SeatClient);
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Seats(seatNr,courseId,seatClient) values(@seatNr,@courseId,@seatClient)";
                IDbDataParameter paramSeatNumber = comm.CreateParameter();
                IDbDataParameter paramCourseId = comm.CreateParameter();
                IDbDataParameter paramSeatClient = comm.CreateParameter();
                paramSeatNumber.ParameterName = "@seatNr";
                paramSeatNumber.Value = seats.SeatNumber;
                comm.Parameters.Add(paramSeatNumber);
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = seats.CourseId;
                comm.Parameters.Add(paramCourseId);
                paramSeatClient.ParameterName = "@seatClient";
                paramSeatClient.Value = seats.SeatClient;
                comm.Parameters.Add(paramSeatClient);
                rowsAffected = comm.ExecuteNonQuery();
            }
            con.Close();
            log.InfoFormat("Exiting saveSeats after values inserted {0}", rowsAffected );
        }

        public void update(SeatsHelper seatsHelper, string seatClient)
        {
            log.InfoFormat("Entering seatsNumber {0} and courseId {1}", seatsHelper.SeatNumber,seatsHelper.CourseId);
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Seats " +
                    "set seatClient=@seatClient " +
                    "where seatNr=@seatNr and courseId=@courseId";
                IDbDataParameter paramSeatNumber = comm.CreateParameter();
                IDbDataParameter paramCourseId = comm.CreateParameter();
                IDbDataParameter paramSeatClient = comm.CreateParameter();
                paramSeatNumber.ParameterName = "@seatNr";
                paramSeatNumber.Value = seatsHelper.SeatNumber;
                comm.Parameters.Add(paramSeatNumber);
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = seatsHelper.CourseId;
                comm.Parameters.Add(paramCourseId);
                paramSeatClient.ParameterName = "@seatClient";
                paramSeatClient.Value = seatClient;
                comm.Parameters.Add(paramSeatClient);
                rowsAffected = comm.ExecuteNonQuery();
            }
            con.Close();
            log.InfoFormat("Exiting after updateSeats with {0} rows affected", rowsAffected);
        }
        
    }
}
