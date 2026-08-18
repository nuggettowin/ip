import java.util.Arrays;
import java.util.stream.Stream;

public class TaskList {
    public record AddTaskResult(TaskList taskList, String resultString) {}
    private final String[] tasks;
    private final static String success_message = "Added!";

    public TaskList() {
        this.tasks = new String[0];
    }

    public TaskList(String[] tasks) {
        this.tasks = tasks;
    }

    public AddTaskResult addTask(String task) {
        String[] addedTask = Stream.concat(Arrays.stream(this.tasks), Stream.of(task))
                .toArray(len -> new String[len]);
        TaskList listRes = new TaskList(addedTask);
        return new AddTaskResult(listRes, this.success_message);
    }

    public String listTasks() {
        return Arrays.toString(this.tasks);
    }
}
