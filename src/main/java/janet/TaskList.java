package janet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class TaskList {
    private final List<Task> tasks;
    private static final String success_message = "Added ";

    public record CommandResult(Optional<TaskList> updatedTaskList, String message) {
    }

    public TaskList() {
        this.tasks = List.of();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

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

    public CommandResult listTasks() {
        Optional<String> retString = IntStream.range(1, this.tasks.size() + 1)
                .mapToObj(i -> Integer.toString(i) + ". " + this.tasks.get(i - 1))
                .reduce((a, b) -> a + "\n" + b);

        return new CommandResult(
                Optional.of(this), retString.orElse("No tasks listed!")
        );
    }

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
