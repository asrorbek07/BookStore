package uz.kruz.util.exceptions;

public class DuplicateEntityException extends ServiceException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
