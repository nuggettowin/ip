package janet;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Handles the fields common to all task-related comments.
 */
public abstract class StorageTaskParser {
    public record TaskField(boolean isDone, String taskLabel, String[] args) {}

    protected static final String INLINE_SEP = Pattern.quote("|");

    private static final int TASK_TYPE_INDEX = 0;
    private static final int IS_DONE_INDEX = 1;
    private static final int TASK_LABEL_INDEX = 2;
    private static final int COMMAND_ARG_INDEX = StorageTaskParser.TASK_LABEL_INDEX + 1;
    private static final String IS_DONE_STR= "1";

    protected final boolean isDone;
    protected final String taskLabel;
    private static final Map<String, Function<TaskField, StorageTaskParser>> taskMap = Map.of(
            "T", (x) -> new StorageTodoParser(x),
            "D", (x) -> new StorageDeadlineParser(x),
            "E", (x) -> new StorageEventParser(x)
    );

    public StorageTaskParser(boolean isDone, String taskLabel) {
        this.isDone = isDone;
        this.taskLabel = taskLabel;
    }

    public static StorageTaskParser processBaseTask(String storageCommand) {
        String[] tokens = storageCommand.split(StorageTaskParser.INLINE_SEP);
        String taskType = tokens[StorageTaskParser.TASK_TYPE_INDEX];
        boolean isDone = tokens[StorageTaskParser.IS_DONE_INDEX].equals(StorageTaskParser.IS_DONE_STR);
        String taskLabel = tokens[StorageTaskParser.TASK_LABEL_INDEX];

        String[] args = Arrays.copyOfRange(
                tokens,
                StorageTaskParser.COMMAND_ARG_INDEX,
                tokens.length
        );

        TaskField taskFields = new TaskField(isDone, taskLabel, args);

        return StorageTaskParser.taskMap
                .get(taskType)
                .apply(taskFields);
    }

    public abstract TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException;
}
