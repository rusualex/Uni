package Repository;

import Domain.Student;
import Exceptions.ValidationException;
import Validators.NullValidator;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements CrudRepository<Integer, Student> {
    String DATABASE_URL = "jdbc:sqlserver://localhost;instance=SQLEXPRESS;databaseName=Catalog;";
    String USER = "catalogdata";
    String PASSWORD = "catalogdata";
    Connection connection;
    NullValidator nullValidator;

    public StudentRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        nullValidator = new NullValidator();
    }

    @Override
    public Student findOne(Integer s) throws SQLException {
        nullValidator.check(s);
        String sql = "SELECT * FROM Student WHERE id=" + s;
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String name = rs.getString("nume");
            Integer group = rs.getInt("grupa");
            String email = rs.getString("email");
            String assistant = rs.getString("asistent");

            return new Student(id, name, group, email, assistant);
        }

        st.close();
        return null;
    }

    @Override
    public Iterable<Student> findAll() throws SQLException {
        String sql = "SELECT * FROM Student";
        List<Student> studentList = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {
            Integer id = rs.getInt("id");
            String name = rs.getString("nume");
            Integer group = rs.getInt("grupa");
            String email = rs.getString("email");
            String assistant = rs.getString("asistent");

            Student student = new Student(id, name, group, email, assistant);
            studentList.add(student);
        }

        st.close();
        return studentList;
    }

    @Override
    public Student save(Student entity) throws ValidationException, SQLException {
        nullValidator.check(entity);
        String sql = "INSERT INTO Student(nume, grupa, email, asistent) VALUES (" + entity.toSQL() + ")";
        connection.prepareStatement(sql).execute();
        return null;
    }

    @Override
    public Student delete(Integer s) throws SQLException {
        nullValidator.check(s);
        String sql = "DELETE FROM Student WHERE id=" + s;
        Student student = findOne(s);

        connection.prepareStatement(sql).execute();

        return student;

    }

    @Override
    public Student update(Student entity) throws SQLException {
        nullValidator.check(entity);
        String sql = "UPDATE Student SET " + entity.updateSQL() + " WHERE id=" + entity.getId();

        if (findOne(entity.getId()) == null) return entity;

        connection.prepareStatement(sql).execute();

        return null;
    }
}
