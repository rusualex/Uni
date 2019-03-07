package Controller;

import Domain.Grade;
import Domain.StudentGrade;
import Repository.GradeRepository;
import Repository.HomeworkRepository;
import Repository.StudentRepository;
import Service.GradeService;
import Service.HomeworkService;
import Service.ReportService;
import Service.StudentService;
import Utils.Alerts;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Date;

public class ReportsController {
    Alerts alerts = new Alerts();
    StudentRepository studentRepository = new StudentRepository();
    HomeworkRepository homeworkRepository = new HomeworkRepository();
    GradeRepository gradeRepository = new GradeRepository(studentRepository, homeworkRepository);

    StudentService studentService = new StudentService(studentRepository);
    HomeworkService homeworkService = new HomeworkService(homeworkRepository);
    GradeService gradeService = new GradeService(studentRepository, homeworkRepository, gradeRepository);
    ReportService reportService = new ReportService(studentRepository, homeworkRepository, gradeRepository);

    ObservableList<String> list = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    String lastButtonClicked = "default";
    Integer studentNumber = 0;

    @FXML
    ListView<String> reportList;
    @FXML
    Button labGradeEachStudentButton, studentsEntryExamButton, hardestHomeworkButton, studentsSubmittedAllHomeworkButton, saveToPDF;
    @FXML
    PieChart pieChart;

    public ReportsController() throws SQLException, ClassNotFoundException {
    }

    public void initialize() {
        setStudentNumber();

        labGradeEachStudentButtonClicked();

        hardestHomeworkButtonClicked();

        studentsEntryExamButtonClicked();

        studentsSubmittedAllHomeworkButtonClicked();

        saveToPDFButton();

    }

    private void setStudentNumber() {
        try {
            studentService.findAll().forEach(student -> {
                studentNumber += 1;
            });
        } catch (SQLException e) {
            alerts.showError(e.getMessage());
        }
    }

    public void updatePieChart() {
        pieChart.setData(pieChartData);

    }

    private void saveToPDFButton() {
        saveToPDF.setOnAction(event -> {
            Date date = new Date();
            String file = lastButtonClicked + " " + date.toString().replace(":", ".").toLowerCase() + ".pdf";
            Document document = new Document();
            Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                document.addTitle(lastButtonClicked);
                Paragraph paragraph = new Paragraph(lastButtonClicked, titleFont);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.add(new Paragraph(""));
                list.forEach(element -> {
                    try {
                        document.add(new Paragraph(element, defaultFont));
                    } catch (DocumentException e) {
                        alerts.showError(e.getMessage());
                    }
                });
                document.close();

                alerts.showInfo("Fisierul a fost salvat cu succes.");

            } catch (Exception e) {
                alerts.showError(e.getMessage());
            }


        });
    }

    private void studentsSubmittedAllHomeworkButtonClicked() {
        studentsSubmittedAllHomeworkButton.setOnAction(event -> {
            lastButtonClicked = "Studenti care au predat temele la timp";
            list.clear();
            try {
                reportService.studentsSubmittedAllHomework().forEach(student -> {
                    list.add(student.toListView());

                });
            } catch (SQLException e) {
                alerts.showError(e.getMessage());
            }

            reportList.setItems(list);

            pieChartData.clear();
            pieChartData.add(new PieChart.Data("Studenti care si-au predat toate temele la timp", list.size()));
            pieChartData.add(new PieChart.Data("Studenti care nu si-au predat toate temele la timp", studentNumber - list.size()));
            updatePieChart();
        });
    }

    private void studentsEntryExamButtonClicked() {
        studentsEntryExamButton.setOnAction(event -> {
            lastButtonClicked = "Studenti care pot intra in examen";
            list.clear();
            try {
                reportService.studentsEntryExam().forEach(studentGrade -> {
                    list.add(studentGrade.toString());
                });
            } catch (SQLException e) {
                alerts.showError(e.getMessage());
            }

            reportList.setItems(list);

            pieChartData.clear();
            pieChartData.add(new PieChart.Data("Studenti care pot intra in examen", list.size()));
            pieChartData.add(new PieChart.Data("Studenti care nu pot intra in examen", studentNumber - list.size()));
            updatePieChart();

        });
    }

    private void hardestHomeworkButtonClicked() {
        hardestHomeworkButton.setOnAction(event -> {
            lastButtonClicked = "Cea mai grea tema";
            list.clear();
            try {
                list.add(reportService.hardestHomework().toListView());
            } catch (SQLException e) {
                alerts.showError(e.getMessage());
            }

            reportList.setItems(list);

            pieChartData.clear();
            pieChartData.add(new PieChart.Data(list.get(0), 1));
            updatePieChart();
        });
    }

    private void labGradeEachStudentButtonClicked() {
        labGradeEachStudentButton.setOnAction(event -> {
            lastButtonClicked = "Media fiecarui student";
            list.clear();
            pieChartData.clear();
            try {
                reportService.labGradeEachStudent().forEach(studentGrade -> {
                    if (!studentGrade.getGrade().isNaN()) {
                        pieChartData.add(new PieChart.Data(studentGrade.getName(), studentGrade.getGrade()));
                        list.add(studentGrade.toString());
                    }

               });
            } catch (SQLException e) {
                e.printStackTrace();
            }

            reportList.setItems(list);

            updatePieChart();

        });
    }


}
