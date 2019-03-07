package Repository;

import Domain.Homework;
import Domain.Student;
import Exceptions.ValidationException;
import Validators.NullValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeworkRepository implements CrudRepository<Integer, Homework> {
    String DATABASE_URL = "jdbc:sqlserver://localhost;instance=SQLEXPRESS;databaseName=Catalog;";
    String USER = "catalogdata";
    String PASSWORD = "catalogdata";
    Connection connection;
    NullValidator nullValidator;

    public HomeworkRepository() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        nullValidator = new NullValidator();
    }

    public Homework findOne(Integer s) throws SQLException {
        nullValidator.check(s);
        String sql = "SELECT * FROM Homework WHERE id=" + s;
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String description = rs.getString("description");
            Integer dateReceived = rs.getInt("dateReceived");
            Integer deadline = rs.getInt("deadline");

            return new Homework(id, description, dateReceived, deadline);
        }

        st.close();
        return null;
    }

    @Override
    public Iterable<Homework> findAll() throws SQLException {
        String sql = "SELECT * FROM Homework";
        List<Homework> homeworkList = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {
            Integer id = rs.getInt("id");
            String description = rs.getString("description");
            Integer dateReceived = rs.getInt("dateReceived");
            Integer deadline = rs.getInt("deadline");

            Homework homework = new Homework(id, description, dateReceived, deadline);
            homeworkList.add(homework);
        }

        st.close();
        return homeworkList;
    }

    @Override
    public Homework save(Homework entity) throws ValidationException, SQLException {
        nullValidator.check(entity);
        String sql = "INSERT INTO Homework(description, dateReceived, deadline) VALUES (" + entity.toSQL() + ");";
        connection.prepareStatement(sql).execute();
        return null;
    }

    @Override
    public Homework delete(Integer integer) throws SQLException {
        nullValidator.check(integer);
        String sql = "DELETE FROM Homework WHERE id=" + integer;
        Homework homework = findOne(integer);

        connection.prepareStatement(sql).execute();

        return homework;
    }

    @Override
    public Homework update(Homework entity) throws SQLException {
        nullValidator.check(entity);
        String sql = "UPDATE Homework SET " + entity.updateSQL() + " WHERE id=" + entity.getId();
        if (findOne(entity.getId()) == null) return entity;

        connection.prepareStatement(sql).execute();

        return null;
    }
}
