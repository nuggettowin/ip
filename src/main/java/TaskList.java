import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class TaskList {
    public record AddTaskResult(TaskList taskList, String resultString) {
    }

    private final List<Task> tasks;
    private static final String success_message = "Added ";

    public TaskList() {
        this.tasks = List.of();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public AddTaskResult addTask(String task) {
        List<Task> addedTask = Stream.concat(
                        this.tasks.stream(),
                        Stream.of(new Task(false, task))
                )
                .toList();
        TaskList listRes = new TaskList(addedTask);
        return new AddTaskResult(listRes, this.success_message.concat(task));
    }

    public Optional<String> listTasks() {
        return IntStream.range(1, this.tasks.size() + 1)
                .mapToObj(i -> Integer.toString(i) + ". " + this.tasks.get(i - 1))
                .reduce((a, b) -> a + "\n" + b);
    }

    public TaskList markTask(int pos) {
        List<Task> front = this.tasks.subList(0, pos - 1);
        List<Task> back = this.tasks.subList(pos, this.tasks.size());
        List<Task> markedTask = List.of(
                this.tasks
                        .get(pos - 1)
                        .markDone()
        );

        return new TaskList(Stream.of(front, markedTask, back)
                .flatMap(x -> x.stream())
                .toList()
        );
    }
}
