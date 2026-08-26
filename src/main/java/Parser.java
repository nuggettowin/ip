import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Map;

import javax.print.DocFlavor;

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
        int fromIndex = argsLine.indexOf(Event.FROM_SEP);
        int toIndex = argsLine.indexOf(Event.TO_SEP);

        if (fromIndex == -1 || toIndex == -1) {
            throw new JanetException("From or To not found!");
        }
        String taskLabel = argsLine.substring(0, fromIndex).trim();
        String from = argsLine.substring(fromIndex + Event.FROM_SEP.length(), toIndex).trim();
        String to = argsLine.substring(toIndex + Event.TO_SEP.length()).trim();

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
        return this.taskList.addTask(task);
    }

    private TaskList.CommandResult handleDeleteTaskCommand(String argsLine) throws JanetException {
        return this.taskList.deleteTask(Integer.parseInt(argsLine));
    }

    public TaskList.CommandResult processStorageCommand(String storageCommand, String sep) throws JanetException {
        String[] args = storageCommand.split(sep);
        String taskType = args[0];
        boolean isDone = Integer.parseInt(args[1]) == 1;
        String taskLabel = args[2];

        if (taskType == "T") {
            return this.handleStorageAddTodo();
        } else if (taskType == "D") {
            return this.handleStorageAddDeadline();
        } else {
            return this.handleStorageAddEvent();
        }
        return this.commandMap.get(taskType).handle(remainingArgs);

    }

    private TaskList.CommandResult handleStorageAddTodo(String isDone, String taskLabel) throws JanetException {
        return this.taskList.addTask(new Todo(isDone.equals("1"), taskLabel));
    }

    private TaskList.CommandResult handleStorageAddDeadline(boolean isDone, String taskLabel, ) throws JanetException {

        return this.taskList.addTask(new Deadline(isDone.equals("1"), remainingArgs));
    }

    private TaskList.CommandResult handleStorageAddEvent(String storageCommand, String sep) {
        return this.taskList.addTask(new Todo(isDone.equals("1"), taskLabel));
    }
}
