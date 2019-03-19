using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tReservation_Lab1CS.repository;

namespace tReservation_Lab1CS.services
{
    class ReservationService
    {
        private ICourseRepository courseRepo;
        private IUserRepository userRepo;
        private ISeatsRepository seatsRepo;

        IDictionary<String, string> props;
        public ReservationService(IDictionary<String,string> props)
        {
            courseRepo = new CourseRepository(props);
            userRepo = new UserRepository(props);
            seatsRepo = new SeatsRepository(props);
            this.props = props;
        }

        public void CreateCourse(string departureCity, string destination, string dateOfDeparture)
        {
            int cId = courseRepo.size() + 1;
            courseRepo.save(new Course { CourseId = cId, DepartureCity = departureCity, Destination = destination, DateOfDeparture = dateOfDeparture, AvailableSeats = 18 });
            initSeatsForCourse(cId);
        }
        
        public bool LogInService(string userName, string password)
        {
            User user = new User { UserId = 0,UserName=userName,UserPassword=password };
            return userRepo.logIn(user);
        }

        public List<Course> ShowAllCourses()
        {
            foreach(var course in courseRepo.FindAll())
            {
                initSeatsForCourse(course.CourseId);
            }
            return courseRepo.FindAll();
        }

        public void reserveSeat(SeatsHelper seatsHelper,string seatClient)
        {
            seatsRepo.update(seatsHelper, seatClient);
        }


        public List<Seats> FindSeats(int courseId)
        {
            Course ourCourse = courseRepo.FindOne(courseId);
            if (ourCourse != null)
               return seatsRepo.findAllByCourse(ourCourse.CourseId);
            return null;
        }

        public List<Course> FindCourses(string departureCity,string destination,string dateOfDeparture)
        {
            List<Course> courses = new List<Course>();
            courses.Add(courseRepo.FindOne(departureCity,destination,dateOfDeparture));
            return courses;
        }


        private void initSeatsForCourse(int courseId)
        {
            List<Seats> seats = seatsRepo.findAllByCourse(courseId);
            if(seats.Count()==0)
                for(int i = 1; i <= 18; i++)
                {
                    seatsRepo.save(new Seats { CourseId = courseId, SeatNumber = i, SeatClient = "-" });
                }
        }
    }
}
