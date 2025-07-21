package uz.kruz.util.exceptions;

public class RowNotFoundException extends RepositoryException {

    public RowNotFoundException(String message) {
        super(message);
    }

    public RowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
