public class Task {
    private final boolean isDone;
    protected final String taskLabel;

    public Task(boolean isDone, String taskLabel) {
        this.isDone = isDone;
        this.taskLabel = taskLabel;
    }

    public Task markDone() {
        return new Task(true, this.taskLabel);
    }

    @Override
    public String toString() {
        return this.taskLabel + " " + this.isDone;
    }
}
