using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tReservation_Lab1CS;

namespace tReservation_Lab1CS
{
    class CourseRepository : ICourseRepository
    {
        private static readonly ILog log = LogManager.GetLogger("CourseRepository");
        IDictionary<String, string> props;

        public CourseRepository(IDictionary<string, string> props)
        {
            log.Info("Creating CourseRepository");
            this.props = props;
        }


        public void delete(int id)
        {
            log.InfoFormat("Entering deleteCourse by ID: {0}", id);
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "DELETE FROM Course WHERE courseId = @courseId";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = id;
                rowsAffected = comm.ExecuteNonQuery();
            }
            log.InfoFormat("Exiting deleteCourse with {0} rows affected", rowsAffected);
            con.Close();
        }

        public List<Course> FindAll()
        {
            log.InfoFormat("Entering findAll Course");
            IDbConnection con = DBUtils.GetConnection(props);
            List<Course> courses = new List<Course>();


            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Course";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read()) {
                    int courseId = dataR.GetInt32(0);
                    string destin = dataR.GetString(1);
                    string dateOf = dataR.GetString(2);
                    string departureCity = dataR.GetString(3);
                    int availableSeats = dataR.GetInt32(4);
                    Course course = new Course()
                    {
                        CourseId = courseId,
                        Destination = destin,
                        DateOfDeparture = dateOf,
                        DepartureCity = departureCity,
                        AvailableSeats = availableSeats
                    };
                    courses.Add(course);
                }
                }
            }
            con.Close();
            log.InfoFormat("Exiting findAll Course");
            return courses;
        }

        public Course FindOne(int courseId)
        {
            log.InfoFormat("Entering findOne with value {0}", courseId);
            IDbConnection con = DBUtils.GetConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Course where courseId=@courseId";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = courseId;
                comm.Parameters.Add(paramCourseId);
                //IDbDataParameter paramDeartureCity = comm.CreateParameter();
                //IDbDataParameter paramDestination = comm.CreateParameter();
                //IDbDataParameter paramDateOfDeparture = comm.CreateParameter();
                //paramDestination.ParameterName = "@destination";
                //paramDestination.Value = destination;
                //paramDeartureCity.ParameterName = "@departureCity";
                //paramDeartureCity.Value = departureCity;
                //paramDateOfDeparture.ParameterName = "@dateOfDeparture";
                //paramDateOfDeparture.Value = dateOfDeparture;

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int cId = dataR.GetInt32(0);
                        string destin = dataR.GetString(1);
                        string dateOf = dataR.GetString(2);
                        string departureCit = dataR.GetString(3);
                        int availableSeats = dataR.GetInt32(4);
                        Course course = new Course()
                        {
                            CourseId = courseId,
                            Destination = destin,
                            DateOfDeparture = dateOf,
                            DepartureCity = departureCit,
                            AvailableSeats = availableSeats
                        };
                        log.InfoFormat("Exiting findOne with value {0}", course);
                        return course;
                    }
                }
            }
            con.Close();
            return null;
        }

        public Course FindOne(string departureCity, string destination, string dateOfDeparture)
        {
            log.InfoFormat("Entering findOne with values departure {0}, destination {1} and date {2}", departureCity, destination, dateOfDeparture);
            IDbConnection con = DBUtils.GetConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Course where departureCity=@departureCity and destination=@destination and dateOfDeparture=@dateOfDeparture";
                //IDbDataParameter paramCourseId = comm.CreateParameter();
                //paramCourseId.ParameterName = "@courseId";
                //paramCourseId.Value = courseId;
                //comm.Parameters.Add(paramCourseId);
                IDbDataParameter paramDeartureCity = comm.CreateParameter();
                IDbDataParameter paramDestination = comm.CreateParameter();
                IDbDataParameter paramDateOfDeparture = comm.CreateParameter();
                paramDestination.ParameterName = "@destination";
                paramDestination.Value = destination;
                paramDeartureCity.ParameterName = "@departureCity";
                paramDeartureCity.Value = departureCity;
                paramDateOfDeparture.ParameterName = "@dateOfDeparture";
                paramDateOfDeparture.Value = dateOfDeparture;
                comm.Parameters.Add(paramDeartureCity);
                comm.Parameters.Add(paramDestination);
                comm.Parameters.Add(paramDateOfDeparture);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int cId = dataR.GetInt32(0);
                        string destin = dataR.GetString(1);
                        string dateOf = dataR.GetString(2);
                        string departureCit = dataR.GetString(3);
                        int availableSeats = dataR.GetInt32(4);
                        Course course = new Course()
                        {
                            CourseId = cId,
                            Destination = destin,
                            DateOfDeparture = dateOf,
                            DepartureCity = departureCit,
                            AvailableSeats = availableSeats
                        };
                        log.InfoFormat("Exiting findOne with value {0}", course);
                        return course;
                    }
                }
            }
            con.Close();
            return null;
        }

        public void save(Course course)
        {
            log.InfoFormat("Entering findOne with value {0}", course);
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Course(destination,dateOfDeparture,departureCity,availableSeats) values(@destination,@dateOfDeparture,@departureCity,@availableSeats)";
                //IDbDataParameter paramCourseId = comm.CreateParameter();
                IDbDataParameter paramDestination = comm.CreateParameter();
                IDbDataParameter paramDateOfDeparture = comm.CreateParameter();
                IDbDataParameter paramDepartureCity = comm.CreateParameter();
                IDbDataParameter paramAvailableSeats = comm.CreateParameter();
                //paramCourseId.ParameterName = "@courseId";
                //paramCourseId.Value = "courseId";
                //comm.Parameters.Add(paramCourseId);
                paramDestination.ParameterName = "@destintation";
                paramDestination.Value = course.Destination;
                comm.Parameters.Add(paramDestination);
                paramDateOfDeparture.ParameterName = "@dateOfDeparture";
                paramDateOfDeparture.Value = course.DateOfDeparture;
                comm.Parameters.Add(paramDateOfDeparture);
                paramDepartureCity.ParameterName = "@departureCity";
                paramDepartureCity.Value = course.DepartureCity;
                comm.Parameters.Add(paramDepartureCity);
                paramAvailableSeats.ParameterName = "@availableSeats";
                paramAvailableSeats.Value = course.AvailableSeats;
                comm.Parameters.Add(paramAvailableSeats);
                rowsAffected = comm.ExecuteNonQuery();
            }
            con.Close();
            log.InfoFormat("Exiting after value inserted with {0} rows affected",rowsAffected);
        }

        public int size()
        {
            log.InfoFormat("Entering size Course");
            IDbConnection con = DBUtils.GetConnection(props);
            int courseNumber = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) over () as total_cnt,c.* from Course c";
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        courseNumber = dataR.GetInt32(0);
                    }
                }
            }
            con.Close();
            log.InfoFormat("Exiting size Course with value{0}", courseNumber);
            return courseNumber;
        }

        public void update(int id, Course course)
        {
            log.InfoFormat("Entering findOne with value {0}", course);
            IDbConnection con = DBUtils.GetConnection(props);
            int rowsAffected = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Course " +
                    "set destination=@destination,dateOfDeparture=@dateOfDeparture,departureCity=@departureCity,availableSeats=@availableSeats" +
                    "where courseId=@courseId";
                IDbDataParameter paramCourseId = comm.CreateParameter();
                IDbDataParameter paramDestination = comm.CreateParameter();
                IDbDataParameter paramDateOfDeparture = comm.CreateParameter();
                IDbDataParameter paramDepartureCity = comm.CreateParameter();
                IDbDataParameter paramAvailableSeats = comm.CreateParameter();
                paramCourseId.ParameterName = "@courseId";
                paramCourseId.Value = id;
                comm.Parameters.Add(paramCourseId);
                paramDestination.ParameterName = "@destintation";
                paramDestination.Value = course.Destination;
                comm.Parameters.Add(paramDestination);
                paramDateOfDeparture.ParameterName = "@dateOfDeparture";
                paramDateOfDeparture.Value = course.DateOfDeparture;
                comm.Parameters.Add(paramDateOfDeparture);
                paramDepartureCity.ParameterName = "@departureCity";
                paramDepartureCity.Value = course.DepartureCity;
                comm.Parameters.Add(paramDepartureCity);
                paramAvailableSeats.ParameterName = "@availableSeats";
                paramAvailableSeats.Value = course.AvailableSeats;
                comm.Parameters.Add(paramAvailableSeats);
                rowsAffected = comm.ExecuteNonQuery();
            }
            con.Close();
            log.InfoFormat("Exiting after update with {0} rows affected", rowsAffected);
        }

    }
}
