package uz.kruz.util.exceptions;

public class DuplicateRowException extends RepositoryException {

    public DuplicateRowException(String message) {
        super(message);
    }

    public DuplicateRowException(String message, Throwable cause) {
        super(message, cause);
    }
}
