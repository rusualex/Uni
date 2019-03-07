package Service;

import Domain.Homework;
import Exceptions.ValidationException;
import Observers.Observable;
import Observers.Observer;
import Repository.HomeworkRepository;
import Utils.ChangeEventType;
import Utils.HomeworkEvent;
import Validators.HomeworkValidator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeworkService implements Observable<HomeworkEvent> {
    HomeworkValidator homeworkValidator = new HomeworkValidator();
    HomeworkRepository homeworkRepository;
    private List<Observer<HomeworkEvent>> observers = new ArrayList<>();

    public HomeworkService(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    public Homework createHomework(Homework hw) throws ValidationException, SQLException {
        homeworkValidator.validateHomework(hw.getDescription(), hw.getDateReceived(), hw.getDeadline());
        Homework tempHomework = homeworkRepository.save(hw);
        if (tempHomework == null) {
            notifyObservers(new HomeworkEvent(ChangeEventType.ADD, hw));
        }
        return tempHomework;

    }

    public Homework createHomework(String description, Integer dateReceived, Integer deadline) throws ValidationException, SQLException {
        Homework hw = new Homework(description, dateReceived, deadline);
        Homework tempHomework = homeworkRepository.save(hw);
        if (tempHomework == null) {
            notifyObservers(new HomeworkEvent(ChangeEventType.ADD, hw));
        }
        return tempHomework;
    }

    public Homework updateDeadline(Integer id, Integer deadline) throws SQLException {
        Homework hw = homeworkRepository.findOne(id);
        hw.setDeadline(deadline);
        homeworkValidator.validateHomework(hw.getDescription(), hw.getDateReceived(), hw.getDeadline());
        Homework tempHomework = homeworkRepository.update(hw);
        if (tempHomework == null) {
            notifyObservers(new HomeworkEvent(ChangeEventType.UPDATE, hw));
        }
        return tempHomework;
    }

    public Homework findOne(Integer id) throws SQLException {
        return homeworkRepository.findOne(id);

    }

    public Iterable<Homework> findAll() throws SQLException {
        return homeworkRepository.findAll();
    }

    public Homework getCurrentHomework() throws SQLException {
        LocalDate localDate;
        localDate = LocalDate.now();
        int date = localDate.getDayOfYear();
        date = (date + 1) / 7 + 12;

        for (Homework h : homeworkRepository.findAll()) {
            if (h.getDeadline() == date) {
                return h;
            }
        }

        return null;
    }

    @Override
    public void addObserver(Observer<HomeworkEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<HomeworkEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(HomeworkEvent t) {
        observers.forEach(observer -> {
            observer.update(t);
        });
    }
}
