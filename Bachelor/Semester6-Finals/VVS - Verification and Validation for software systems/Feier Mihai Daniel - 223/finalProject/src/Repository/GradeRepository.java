package Repository;

import Domain.Grade;
import Domain.Homework;
import Exceptions.ValidationException;
import Validators.NullValidator;
import javafx.util.Pair;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GradeRepository implements CrudRepository<Pair<Integer, Integer>, Grade> {
    String DATABASE_URL = "jdbc:sqlserver://localhost;instance=SQLEXPRESS;databaseName=Catalog;";
    String USER = "catalogdata";
    String PASSWORD = "catalogdata";
    Connection connection;
    StudentRepository studentRepository;
    HomeworkRepository homeworkRepository;
    NullValidator nullValidator;

    public GradeRepository(StudentRepository studentRepository, HomeworkRepository homeworkRepository) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        this.homeworkRepository = homeworkRepository;
        this.studentRepository = studentRepository;
        nullValidator = new NullValidator();
    }

    @Override
    public Grade findOne(Pair<Integer, Integer> id) throws SQLException {
        nullValidator.check(id);
        String sql = "SELECT * FROM Grade WHERE idStudent=" + id.getKey() + " AND idHomework=" + id.getValue();
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Integer idStudent = rs.getInt("idStudent");
            Integer idHomework = rs.getInt("idHomework");
            Float value = rs.getFloat("value");
            Integer date = rs.getInt("date");
            String feedback = rs.getString("feedback");

            return new Grade(new Pair<>(idStudent, idHomework), studentRepository.findOne(idStudent),
                    homeworkRepository.findOne(idHomework), value, date, feedback);
        }

        st.close();
        return null;
    }

    @Override
    public Iterable<Grade> findAll() throws SQLException {
        String sql = "SELECT * FROM Grade";
        List<Grade> gradeList = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {
            Integer idStudent = rs.getInt("idStudent");
            Integer idHomework = rs.getInt("idHomework");
            Float value = rs.getFloat("value");
            Integer date = rs.getInt("date");
            String feedback = rs.getString("feedback");

            Grade grade =  new Grade(new Pair<>(idStudent, idHomework), studentRepository.findOne(idStudent),
                    homeworkRepository.findOne(idHomework), value, date, feedback);
            if (grade.getStudent() != null){
                gradeList.add(grade);
            }
        }

        st.close();

        return gradeList;
    }

    @Override
    public Grade save(Grade entity) throws ValidationException, SQLException {
        nullValidator.check(entity);
        if (findOne(entity.getId()) != null) return entity;

        String sql = "INSERT INTO Grade(idStudent, idHomework, value, date, feedback) VALUES (" + entity.toSQL() + ");";
        connection.prepareStatement(sql).execute();

        sendMail(entity);
        return null;
    }

    public void sendMail(Grade grade) throws ValidationException {
        final String FROM = "mihaifeier11@gmail.com";
        final String FROMNAME = "UBB INFORMATICA";
        final String TO = grade.getStudent().getEmail();
        final String SMTP_USERNAME = "mihaifeier11@gmail.com";
        final String SMTP_PASSWORD = "mfsezavaeakmixyi";
        final String CONFIGSET = "ConfigSet";
        final String HOST = "smtp.gmail.com";
        final int PORT = 587;

        final String SUBJECT = "Nota la tema" + grade.getHomeworkId();

        final String BODY = grade.toMail();


        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getDefaultInstance(props);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM, FROMNAME));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY, "text/html");

            msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

            Transport transport = session.getTransport();

            try {

                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

                transport.sendMessage(msg, msg.getAllRecipients());
            } catch (Exception ex) {
            } finally {
                transport.close();
            }
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public Grade delete(Pair<Integer, Integer> id) throws SQLException {
        nullValidator.check(id);
        String sql = "DELETE FROM Grade WHERE idStudent=" + id.getKey() + " AND idHomework=" + id.getValue();
        Grade grade = findOne(id);

        connection.prepareStatement(sql).execute();

        return grade;
    }

    @Override
    public Grade update(Grade entity) throws SQLException {
        nullValidator.check(entity);
        String sql = "UPDATE Grade SET " + entity.updateSQL() + " WHERE idStudent=" + entity.getId().getKey() + " AND idHomework=" + entity.getId().getValue();
        if (findOne(entity.getId()) == null) return entity;

        connection.prepareStatement(sql).execute();

        return null;
    }
}
