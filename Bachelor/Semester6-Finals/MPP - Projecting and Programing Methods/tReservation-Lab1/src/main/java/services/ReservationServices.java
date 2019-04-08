package services;

import model.Course;
import model.Seats;
import model.User;
import repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReservationServices {
    private ICourseRepository courseRepository;
    private IUserRepository userRepository;
    private ISeatsRepository seatsRepository;
    private JdbcUtils dbUtils;


    public ReservationServices(Properties props){
        this.courseRepository = new CourseRepository(props);
        this.userRepository = new UserRepository(props);
        this.seatsRepository = new SeatsRepository(props);
        dbUtils=new JdbcUtils(props);
    }


    public void createCourse(String departureCity,String destination, String dateOfDeparture){
        int cId = courseRepository.size() + 1;
        courseRepository.save(new Course(cId, destination, dateOfDeparture,departureCity,18));
        initSeatsForCourse(cId);
    }

    public boolean logInService(String username, String password){
        return userRepository.logIn(new User(username,password,userRepository.size()+1));
    }

    public List<Course> showAllCourses(){
        List<Course> courses = courseRepository.findAll();
        for(Course course:courses)
            initSeatsForCourse(course.getCourseId());
        return courses;
    }

    public void reserveSeat(SeatsHelper seatsHelper,String seatClient){
        seatsRepository.update(seatsHelper,seatClient);
    }

    public Iterable<Seats> findSeats(int courseId){
        Course ourCourse = (Course) courseRepository.findOne(courseId);
        if (ourCourse != null)
            return seatsRepository.findAllByCourse(ourCourse.getCourseId());
        return null;
    }

    public List<Course> findCourse(String departureCity, String destination, String dateOfDeparture){
        List<Course> courses = new ArrayList<Course>();
        ((ArrayList<Course>) courses).add((Course)courseRepository.findOne(departureCity,destination,dateOfDeparture));
        return courses;
    }

    private void initSeatsForCourse(int courseId){
        List<Seats> seats = (List<Seats>)seatsRepository.findAllByCourse(courseId);
        if (seats.size()==0){
            for(int i=1;i<=18;i++){
                seatsRepository.save(new Seats(i,courseId,"-"));
            }
        }
    }



}
