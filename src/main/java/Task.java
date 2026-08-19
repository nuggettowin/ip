public abstract class Task {
    private final boolean isDone;
    protected final String taskType;
    protected final String taskLabel;

    public Task(boolean isDone, String taskType, String taskLabel) {
        this.isDone = isDone;
        this.taskType = taskType;
        this.taskLabel = taskLabel;
    }

    public abstract Task markDone();

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", this.taskType, this.isDone ? "X" : " ", this.taskLabel);
    }
}
