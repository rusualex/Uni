package Validators;

public class NullValidator {
    public void check(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("The given argument can't be null");
        }
    }
}
