import java.util.Optional;

interface CommandHandler {
    Optional<TaskList> handle(String argsLine) throws JanetException;
}
