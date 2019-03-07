package Validators;

public class HomeworkValidator {
    public void validateHomework(String description, Integer dateReceived, Integer deadline) {
        validateDescription(description);
        validateDate(dateReceived);
        validateDate(deadline);
        if (dateReceived > deadline) {
            throw new IllegalArgumentException("Data in care a fost primita tema nu poate fi mai mare decat deadline-ul");
        }
    }

    public void validateDescription(String description) {
        if (description.equals("")) {
            throw new IllegalArgumentException("Descrierea nu poate fi nula.");
        }
    }

    public void validateDate(Integer date) {
        if (date < 1 || date > 14) {
            throw new IllegalArgumentException("Data trebuie sa fie intre 1 si 14");
        }
    }
}
