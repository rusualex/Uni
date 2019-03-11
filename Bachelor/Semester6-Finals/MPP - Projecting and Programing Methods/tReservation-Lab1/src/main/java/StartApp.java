import model.Course;
import model.User;
import repository.CourseRepository;
import repository.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApp {
    public static void main(String[] args){
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CourseRepository courseRepository = new CourseRepository(serverProps);
        UserRepository userRepository = new UserRepository(serverProps);
        //courseRepository.save(new Course(1,"Bucharest", "2019-03-10 12:00:00","Cluj",18));
        System.out.println("Numarul de intrari \n\n\n\n"+courseRepository.findAll());
        for(Course course:courseRepository.findAll())
            System.out.println(course.toString());

        System.out.println("Numarul de intrari \n\n\n\n"+userRepository.findAll());
        for(User user:userRepository.findAll())
            System.out.println(user.toString());
    }
}
