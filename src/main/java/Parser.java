import java.util.function.Function;
import java.util.Map;
import java.util.Optional;

public class Parser {
    private final TaskList taskList;

    private final Map<
            String,
            CommandHandler> commandMap = Map.of(
            "list", this::handleListTasksCommand,
            "mark", this::handleMarkTaskCommand,
            "todo", this::handleAddTodoCommand,
            "deadline", this::handleAddDeadlineCommand,
            "event", this::handleAddEventCommand,
            "delete", this::handleDeleteTaskCommand
    );

    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    public Optional<TaskList> processCommand(String currLine) throws JanetException {
        String command = currLine.split("\\s+")[0].trim();
        String argsLine = currLine.substring(command.length()).trim(); // remaining string

        return Optional.ofNullable(this.commandMap.get(command).handle(argsLine))
                .orElseThrow(() -> new JanetException("Unrecognised command!"));
    }

    private Optional<TaskList> handleListTasksCommand(String argsLine) {
        System.out.println(this.taskList.listTasks().orElse("No tasks listed!"));
        return Optional.empty();
    }

    private Optional<TaskList> handleMarkTaskCommand(String argsLine) throws JanetException {
        return Optional.of(this.taskList.markTask(Integer.parseInt(argsLine)));
    }

    private Optional<TaskList> handleAddTodoCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Todo(false, argsLine));
    }

    private Optional<TaskList> handleAddDeadlineCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Deadline(false, argsLine));
    }

    private Optional<TaskList> handleAddEventCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Event(false, argsLine));
    }

    private Optional<TaskList> handleAddTaskCommand(Task task) {
        TaskList ret = this.taskList.addTask(task);
        System.out.printf("Added %s\n", task);
        return Optional.of(ret);
    }

    private Optional<TaskList> handleDeleteTaskCommand(String argsLine) throws JanetException {
        return Optional.of(this.taskList.deleteTask(Integer.parseInt(argsLine)));
    }
}
