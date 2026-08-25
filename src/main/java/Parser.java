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

    public TaskList.CommandResult processCommand(String currLine) throws JanetException {
        String command = currLine.split("\\s+")[0].trim();
        String argsLine = currLine.substring(command.length()).trim(); // remaining string

        System.out.printf("Command: %s, arg: %s\n", command, argsLine);

        if (!this.commandMap.containsKey(command)) {
            throw new JanetException("Unrecognised command!");
        } else {
            return this.commandMap.get(command).handle(argsLine);
        }
    }

    private TaskList.CommandResult handleListTasksCommand(String argsLine) {
        return this.taskList.listTasks();
    }

    private TaskList.CommandResult handleMarkTaskCommand(String argsLine) throws JanetException {
        return this.taskList.markTask(Integer.parseInt(argsLine));
    }

    private TaskList.CommandResult handleAddTodoCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Todo(false, argsLine));
    }

    private TaskList.CommandResult handleAddDeadlineCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Deadline(false, argsLine));
    }

    private TaskList.CommandResult handleAddEventCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Event(false, argsLine));
    }

    private TaskList.CommandResult handleAddTaskCommand(Task task) {
        return this.taskList.addTask(task);
    }

    private TaskList.CommandResult handleDeleteTaskCommand(String argsLine) throws JanetException {
        return this.taskList.deleteTask(Integer.parseInt(argsLine));
    }
}
