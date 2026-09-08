package janet;

public class StorageTodoParser extends StorageTaskParser {

    public StorageTodoParser(TaskField taskField) {
        super(taskField.isDone(), taskField.taskLabel());
    }

    @Override
    public TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException {
        return taskList.addTask(new Todo(super.isDone, super.taskLabel));
    }
}
