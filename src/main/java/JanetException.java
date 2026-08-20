public class JanetException extends Exception {
    public JanetException(String message) {
        super(message);
    }

    public JanetException(String message, Throwable err) {
        super(message, err);
    }
}
