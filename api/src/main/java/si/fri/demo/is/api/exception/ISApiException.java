package si.fri.demo.is.api.exception;

public class ISApiException extends Exception {

    public ISApiException() {
    }

    public ISApiException(String message) {
        super(message);
    }

    public ISApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
