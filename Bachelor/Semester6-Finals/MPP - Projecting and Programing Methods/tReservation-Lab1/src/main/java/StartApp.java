import model.Course;
import model.User;
import repository.CourseRepository;
import repository.SeatsRepository;
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
        SeatsRepository seatsRepository = new SeatsRepository(serverProps);
        //courseRepository.save(new Course(1,"Bucharest", "2019-03-10 12:00:00","Cluj",18));
        System.out.println("Numarul de intrari \n\n"+courseRepository.findAll());
        for(Course course:courseRepository.findAll())
            System.out.println(course.toString());

        System.out.println("Numarul de intrari \n\n"+userRepository.findAll());
        for(User user:userRepository.findAll())
            System.out.println(user.toString());

        System.out.println("\n\n Cautam raig0136\n");
        System.out.println(userRepository.findOne("raig0136"));

        System.out.println("\n\n Stergem raig0136\n");
        userRepository.delete(10);

        System.out.println("\n\n Cautam raig0136\n");
        System.out.println(userRepository.findOne("raig0136"));

        System.out.println("\n\n Creeam raig0136\n");
        userRepository.save(new User("raig0136","1234",10));

        System.out.println("\n\n Cautam raig0136\n");
        System.out.println(userRepository.findOne("raig0136"));


        System.out.println("\n\n Cautam o cursa\n");
        System.out.println(courseRepository.findOne("Timisoara","2018-01-05 18:00:00"));

        System.out.println("\n\n Stergem cursa\n");
        courseRepository.delete(6);

        System.out.println;



        System.out.println("\n\n Cautam o cursa\n");
        System.out.println(courseRepository.findOne("Cluj","2018-01-02 18:00:00"));


        seatsRepository.findAll().forEach(x->{System.out.println(x.toString());});
    }
}
