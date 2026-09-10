package janet;

import java.time.LocalDate;

/**
 * Parses storage lines representing <code>Event</code> task type.
 */
public class StorageEventParser extends StorageTaskParser {
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Parses <code>Event</code> specific String values.
     */
    public StorageEventParser(TaskField taskField) {
        super(taskField.isDone(), taskField.taskLabel());
        String fromStr = taskField.args()[StorageEventParser.FROM_INDEX];
        String toStr = taskField.args()[StorageEventParser.TO_INDEX];
        this.from = LocalDate.parse(fromStr);
        this.to = LocalDate.parse(toStr);
    }

    @Override
    public TaskList.CommandResult processStorageCommand(TaskList taskList) throws JanetException {
        return taskList.addTask(new Event(super.isDone, super.taskLabel, this.from, this.to));
    }
}
