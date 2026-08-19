public class Deadline extends Task {
    private final String deadline;

    public Deadline(boolean isDone, String argsLine) {
        int deadlineIndex = argsLine.indexOf("/by");
        String taskLabel = argsLine.substring(0, deadlineIndex);
        String deadline = argsLine.substring(deadlineIndex);

        this.deadline = deadline;
        super(isDone, taskLabel);
    }

    @Override
    public String toString() {
        return super.taskLabel;
    }
}
