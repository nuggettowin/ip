package janet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * Parses user commands and maps them to their corresponding task operations.
 */
public class Parser {
    private final TaskList taskList;
    private static final String EXIT_WORD = "bye";

    private final Map<
            String,
            CommandHandler> commandMap = Map.of(
            "list", this::handleListTasksCommand,
            "find", this::handleFindTasksCommand,
            "mark", this::handleMarkTaskCommand,
            "todo", this::handleAddTodoCommand,
            "deadline", this::handleAddDeadlineCommand,
            "event", this::handleAddEventCommand,
            "delete", this::handleDeleteTaskCommand,
            "sort", this::handleSetTaskListComparatorCommand
    );

    /**
     * Creates a parser for the specified task list.
     *
     * @param taskList Task list to be modified by parsed commands.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    public static String formatString(String currLine) {
        return currLine.trim();
    }

    public static boolean isExitCommand(String currLine) {
        return currLine.equals(Parser.EXIT_WORD);
    }

    /**
     * Processes a command entered by the user and returns the corresponding result.
     *
     * @param currLine Command and arguments to process.
     * @return Result of processing the command.
     * @throws JanetException If the command is not recognized or cannot be processed.
     */
    public TaskList.CommandResult processCommand(String currLine) throws JanetException {
        String command = currLine.split("\\s+")[0].trim();
        String argsLine = currLine.substring(command.length()).trim(); // remaining string

        if (!this.commandMap.containsKey(command)) {
            throw new JanetException("Unrecognised command!");
        } else {
            CommandHandler handler = this.commandMap.get(command);
            assert handler != null : "Every registered command must have a handler";
            return handler.handle(argsLine);
        }
    }

    private TaskList.CommandResult handleListTasksCommand(String argsLine) {
        return this.taskList.listTasks();
    }

    private TaskList.CommandResult handleFindTasksCommand(String argsLine) {
        return this.taskList.findTasks(argsLine);
    }

    private TaskList.CommandResult handleMarkTaskCommand(String argsLine) throws JanetException {
        try {
            return this.taskList.markTask(Integer.parseInt(argsLine));
        } catch (NumberFormatException e) {
            throw new JanetException("Mark should contain integer!");
        }
    }

    private TaskList.CommandResult handleAddTodoCommand(String argsLine) throws JanetException {
        return this.handleAddTaskCommand(new Todo(false, argsLine));
    }

    private TaskList.CommandResult handleAddDeadlineCommand(String argsLine) throws JanetException {
        int deadlineIndex = argsLine.indexOf(Deadline.DEADLINE_SEP);
        if (deadlineIndex == -1) {
            throw new JanetException("Deadline not found!");
        }
        String taskLabel = argsLine.substring(0, deadlineIndex).trim();
        String deadline = argsLine.substring(deadlineIndex + Deadline.DEADLINE_SEP.length()).trim();

        if (deadline.isEmpty()) {
            throw new JanetException(String.format("Invalid deadline arguments! Deadline: %s\n", deadline));
        }

        try {
            LocalDate deadlineDate = LocalDate.parse(deadline);
            return this.handleAddTaskCommand(new Deadline(false, taskLabel, deadlineDate));
        } catch (DateTimeParseException e) {
            throw new JanetException("Invalid date format");
        }
    }

    private TaskList.CommandResult handleAddEventCommand(String argsLine) throws JanetException {
        int fromIndex = argsLine.indexOf(Event.EVENT_FROM_SEP);
        int toIndex = argsLine.indexOf(Event.EVENT_TO_SEP);

        if (fromIndex == -1 || toIndex == -1) {
            throw new JanetException("From or To not found!");
        }
        String taskLabel = argsLine.substring(0, fromIndex).trim();
        String from = argsLine.substring(fromIndex + Event.EVENT_FROM_SEP.length(), toIndex).trim();
        String to = argsLine.substring(toIndex + Event.EVENT_TO_SEP.length()).trim();

        if (from.isEmpty() || to.isEmpty()) {
            throw new JanetException(String.format("Invalid event arguments! From: %s, to: %s\n", from, to));
        }

        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            return this.handleAddTaskCommand(new Event(false, taskLabel, fromDate, toDate));
        } catch (DateTimeParseException e) {
            throw new JanetException("Invalid date format");
        }
    }

    private TaskList.CommandResult handleAddTaskCommand(Task task) {
        assert task != null : "Private parser helpers only receive constructed tasks";
        return this.taskList.addTask(task);
    }

    private TaskList.CommandResult handleDeleteTaskCommand(String argsLine) throws JanetException {
        try {
            return this.taskList.deleteTask(Integer.parseInt(argsLine));
        } catch (NumberFormatException e) {
            throw new JanetException("Mark should contain integer!");
        }
    }

    private TaskList.CommandResult handleSetTaskListComparatorCommand(String argsLine) throws JanetException {
        return this.taskList.setTaskListComparator(argsLine);
    }
}
