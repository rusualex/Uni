package Validators;

public class StudentValidator {
    public void validateStudent(String name, Integer group, String email, String assistant) {
        validateName(name);
        validateGroup(group);
        validateEmail(email);
        validateName(assistant);
    }

    public void validateName(String name) {
        if (name.split(" ").length < 2) {
            throw new IllegalArgumentException("Numele trebuie sa contina minim 2 cuvinte.");
        }
    }

    public void validateGroup(Integer group) {
        if (group < 221 || group > 227) {
            throw new IllegalArgumentException("Grupa trebuie sa fie in intervalul 221-227.");
        }
    }

    public void validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Emailul este incorect");
        }
    }
}
