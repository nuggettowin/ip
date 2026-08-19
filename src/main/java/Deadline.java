public class Deadline extends Task {
    private final String deadline;
    private final static String TASK_TYPE = "D";
    private final static String DEADLINE_SEP = "/by";

    public Deadline(boolean isDone, String argsLine) {
        int deadlineIndex = argsLine.indexOf(DEADLINE_SEP);
        String taskLabel = argsLine.substring(0, deadlineIndex).trim();
        this.deadline = argsLine.substring(deadlineIndex + DEADLINE_SEP.length()).trim();

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
        return String.format("%s (by: %s)", super.toString(), this.deadline);
    }
}
