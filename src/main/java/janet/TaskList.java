package janet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.IntStream;

/**
 * An ordered unmodifiable collection of <code>Task</code> elements.
 * The user can access elements by their integer index, and search for elements in the list.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Represents the return value of all <code>TaskList</code> operations.
     * A <code>CommandResult</code> corresponds to the new TaskList (if any) and a string representation of the operation.
     * @param updatedTaskList
     * @param message
     */
    public record CommandResult(Optional<TaskList> updatedTaskList, String message) {
    }

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = List.of();
    }

    /**
     * Creates an TaskList using the provided <code>List</code> of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns a new <code>CommandResult</code> with the <code>Task</code> appended to the previous <code>TaskList</code>.
     * @param task <code>Task</code> to be appended.
     * @return A <code>CommandResult</code> containing an updated <code>TaskList</code> and operation message.
     */
    public CommandResult addTask(Task task) {
        List<Task> addedTask = Stream.concat(
                        this.tasks.stream(),
                        Stream.of(task)
                )
                .toList();
        return new CommandResult(
                Optional.of(new TaskList(addedTask)), String.format("%s added!\n", task)
        );
    }

    /**
     * Deletes the task at the specified position and returns an updated <code>TaskList</code>
     *
     * @param pos The one-based position of the task to delete.
     * @return A <code>CommandResult</code> containing the updated <code>TaskList</code> and operation message.
     * @throws <code>JanetException</code> If the specified position is outside the TaskList.
     */
    public CommandResult deleteTask(int pos) throws JanetException {
        if (pos < 1 || pos > this.tasks.size()) {
            throw new JanetException(
                    String.format("Deletion out of index!")
            );
        }
        TaskList retTaskList = new TaskList(
                IntStream.range(0, this.tasks.size())
                        .filter(i -> i != pos - 1)
                        .mapToObj(i -> this.tasks.get(i))
                        .toList()
        );

        return new CommandResult(Optional.of(retTaskList), String.format("%d deleted!", pos));
    }

    /**
     * Lists all tasks currently stored in the <code>TaskList</code>.
     *
     * @return A <code>CommandResult</code> containing the current <code>TaskList</code> and formatted list.
     */
    public CommandResult listTasks() {
        Optional<String> retString = IntStream.range(1, this.tasks.size() + 1)
                .mapToObj(i -> Integer.toString(i) + ". " + this.tasks.get(i - 1))
                .reduce((a, b) -> a + "\n" + b);

        return new CommandResult(
                Optional.of(this), retString.orElse("No tasks listed!")
        );
    }

    /**
     * Lists all tasks currently stored in the <code>TaskList</code> and matching the provided searchTerm
     * Matches occur when the searchTerm is a substring of the task label.
     *
     * @return A <code>CommandResult</code> containing the current <code>TaskList</code> and formatted result.
     */
    public CommandResult findTasks(String searchTerm) {
        Optional<String> retString = IntStream.range(1, this.tasks.size() + 1)
                .mapToObj(i -> Integer.toString(i) + ". " + this.tasks.get(i - 1))
                .filter(x -> x.contains(searchTerm))
                .reduce((a, b) -> a + "\n" + b);

        return new CommandResult(
                Optional.of(this),
                retString.map(x -> "Here are your matching tasks!\n" + x)
                        .orElse("No tasks found!")
        );
    }

    /**
     * Marks the task at the specified position as done.
     *
     * @return A <code>CommandResult</code> containing the updated <code>TaskList</code> and operation message.
     * @throws JanetException If the specified position is outside the <code>TaskList</code>.
     */
    public CommandResult markTask(int pos) throws JanetException {
        if (pos < 1 || pos > this.tasks.size()) {
            throw new JanetException(
                    String.format("No mark index ")
            );
        }
        List<Task> front = this.tasks.subList(0, pos - 1);
        List<Task> back = this.tasks.subList(pos, this.tasks.size());
        List<Task> markedTask = List.of(
                this.tasks
                        .get(pos - 1)
                        .markDone()
        );

        TaskList retTaskList = new TaskList(Stream.of(front, markedTask, back)
                .flatMap(x -> x.stream())
                .toList()
        );

        return new CommandResult(Optional.of(retTaskList), String.format("%d marked!\n", pos));
    }

    // also used as file format
    @Override
    public String toString() {
        return this.tasks.stream()
                .map(x -> x.toFileFormat())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}
