package janet;

import java.time.LocalDate;

public class StorageDeadlineParser extends StorageTaskParser {
    private static final int DEADLINE_INDEX = 0;

    private final LocalDate deadline;

    public StorageDeadlineParser(TaskField taskField) {
        super(taskField.isDone(), taskField.taskLabel());
        String deadlinStr = taskField.args()[StorageDeadlineParser.DEADLINE_INDEX];
        this.deadline = LocalDate.parse(deadlinStr);
    }

    @Override
    public TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException {
        return taskList.addTask(new Deadline(super.isDone, super.taskLabel, this.deadline));
    }
}
