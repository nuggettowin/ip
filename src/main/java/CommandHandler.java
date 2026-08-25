import java.util.Optional;

@FunctionalInterface
interface CommandHandler {
    Optional<TaskList> handle(String argsLine) throws JanetException;
}
