package janet;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Handles the fields common to all task-related comments.
 */
public abstract class StorageTaskParser {
    /**
     * Represents the fields common to all <code>Task</code> types and any remaining arguments.
     *
     * @param isDone Whether the <code>Task</code> has been marked as 'done'.
     * @param taskLabel The description of each task.
     * @param args Any remaining arguments for specific <code>Task</code> types, if any.
     */
    public record TaskField(boolean isDone, String taskLabel, String[] args) {}

    protected static final String INLINE_SEP = Pattern.quote("|");

    private static final Map<String, StorageCommandHandler> taskMap = Map.of(
            "T", (x) -> new StorageTodoParser(x),
            "D", (x) -> new StorageDeadlineParser(x),
            "E", (x) -> new StorageEventParser(x)
    );

    private static final int TASK_TYPE_INDEX = 0;
    private static final int IS_DONE_INDEX = 1;
    private static final int TASK_LABEL_INDEX = 2;
    private static final int COMMAND_ARG_INDEX = StorageTaskParser.TASK_LABEL_INDEX + 1;
    private static final String IS_DONE_STR = "1";

    protected final boolean isDone;
    protected final String taskLabel;

    /**
     * Initializes storage configuration with fields common to all <code>Task</code> types.
     */
    public StorageTaskParser(boolean isDone, String taskLabel) {
        this.isDone = isDone;
        this.taskLabel = taskLabel;
    }

    /**
     * Parses a serialized task command and creates the parser for its task type.
     * The command must contain the task type, completion status, and task label,
     * separated by {@code |}. Any remaining fields are passed to the specific task parser.
     *
     * @param storageCommand The serialized task command read from storage
     * @return The parser corresponding to the command's task type
     */
    public static StorageTaskParser processBaseTask(String storageCommand) throws JanetFileException {
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
                .handle(taskFields);
    }

    public abstract TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException;

    public String generateArgLengthExceptionMessage(String taskName, int positionalArgCount) {
        return String.format(
                "%s takes %d positional arguments but %d was given",
                taskName,
                positionalArgCount,
                positionalArgCount
        );
    }
}
