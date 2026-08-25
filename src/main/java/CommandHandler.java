import java.util.Optional;

@FunctionalInterface
interface CommandHandler {
    TaskList.CommandResult handle(String argsLine) throws JanetException;
}
