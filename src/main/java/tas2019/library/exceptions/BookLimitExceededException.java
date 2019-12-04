package tas2019.library.exceptions;

public class BookLimitExceededException extends Exception {
    public BookLimitExceededException(String errorMessage) {
        super(errorMessage);
    }
}
