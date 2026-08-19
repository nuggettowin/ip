import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class TaskList {
    public record AddTaskResult(TaskList taskList, String resultString) {}
    private final List<String> tasks;
    private static final String success_message = "Added ";

    public TaskList() {
        this.tasks = List.of();
    }

    public TaskList(List<String> tasks) {
        this.tasks = tasks;
    }

    public AddTaskResult addTask(String task) {
        List<String> addedTask = Stream.concat(this.tasks.stream(), Stream.of(task))
                .toList();
        TaskList listRes = new TaskList(addedTask);
        return new AddTaskResult(listRes, this.success_message.concat(task));
    }

    public Optional<String> listTasks() {
        return IntStream.range(1, this.tasks.size() + 1)
                .mapToObj(i -> Integer.toString(i) + ". " + this.tasks.get(i - 1))
                .reduce((a, b) -> a + "\n" + b);
    }
}
