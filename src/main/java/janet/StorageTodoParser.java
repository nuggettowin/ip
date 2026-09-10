package janet;

/**
 * Parses storage lines representing <code>Todo</code> task type.
 */
public class StorageTodoParser extends StorageTaskParser {

    /**
     * Parses <code>Todo</code> specific String values.
     */
    public StorageTodoParser(TaskField taskField) {
        super(taskField.isDone(), taskField.taskLabel());
    }

    @Override
    public TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException {
        return taskList.addTask(new Todo(super.isDone, super.taskLabel));
    }
}
