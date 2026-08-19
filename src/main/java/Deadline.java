public class Deadline extends Task {
    private final String deadline;
    private final static String TASK_TYPE = "D";

    public Deadline(boolean isDone, String argsLine) {
        int deadlineIndex = argsLine.indexOf("/by");
        String taskLabel = argsLine.substring(0, deadlineIndex);
        this.deadline = argsLine.substring(deadlineIndex);

        super(isDone, Deadline.TASK_TYPE, taskLabel);
    }

    private Deadline(boolean isDone, String taskLabel, String deadline) {
        super(isDone, Deadline.TASK_TYPE, taskLabel);
        this.deadline = deadline;
    }

    @Override
    public Task markDone() {
        return new Deadline(true, super.taskLabel, this.deadline);
    }

    @Override
    public String toString() {
        return super.taskLabel;
    }
}
