package janet;

public abstract class Task {
    private final boolean isDone;
    protected final String taskType;
    protected final String taskLabel;
    protected static String FILE_DELIMITER = "|";

    public Task(boolean isDone, String taskType, String taskLabel) throws JanetException {
        if (taskLabel.isEmpty()) {
            throw new JanetException("No task description provided!");
        }
        this.isDone = isDone;
        this.taskType = taskType;
        this.taskLabel = taskLabel;
    }

    public abstract Task markDone() throws JanetException;

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
