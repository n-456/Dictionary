package exception;

public class DatabaseException extends DictionaryException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}