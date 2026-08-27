package janet;

/**
 * Represents a task with a completion status, type, and label.
 */
public abstract class Task {

    private final boolean isDone;
    protected final String taskType;
    protected final String taskLabel;
    protected static final String FILE_DELIMITER = "|";

    /**
     * Creates a task with the specified completion status, type, and label.
     * @throws JanetException If the task label is empty.
     */
    public Task(boolean isDone, String taskType, String taskLabel) throws JanetException {
        if (taskLabel.isEmpty()) {
            throw new JanetException("No task description provided!");
        }
        this.isDone = isDone;
        this.taskType = taskType;
        this.taskLabel = taskLabel;
    }

    /**
     * Returns a new <code>Task</code> the same as this <code>Task</code>but marked as completed.
     * @throws JanetException If the new task could not be instantiated.
     */
    public abstract Task markDone() throws JanetException;

    /**
     * Returns a string representation of the <code>Task</code> that is suitable for saving to a file.
     */
    public String toFileFormat() {
        return String.join(
                Task.FILE_DELIMITER,
                this.taskType,
                this.isDone ? "1" : "0",
                this.taskLabel
        );
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", this.taskType, this.isDone ? "X" : " ", this.taskLabel);
    }
}
