public class Deadline extends Task {
    private final String deadline;
    private final static String TASK_TYPE = "D";
    private final static String DEADLINE_SEP = "/by";

    public Deadline(boolean isDone, String argsLine) throws JanetException {
        int deadlineIndex = argsLine.indexOf(DEADLINE_SEP);
        //TODO: not -1
        if (deadlineIndex == -1) {
            throw new JanetException("Deadline not found!");
        }
        String taskLabel = argsLine.substring(0, deadlineIndex).trim();
        String deadline = argsLine.substring(deadlineIndex + DEADLINE_SEP.length()).trim();

        if (deadline.isEmpty()) {
            throw new JanetException(String.format("Invalid deadline arguments! Deadline: %s\n", deadline));
        }

        this.deadline = deadline;

        super(isDone, Deadline.TASK_TYPE, taskLabel);
    }

    private Deadline(boolean isDone, String taskLabel, String deadline) throws JanetException {
        super(isDone, Deadline.TASK_TYPE, taskLabel);
        this.deadline = deadline;
    }

    @Override
    public Task markDone() throws JanetException {
        return new Deadline(true, super.taskLabel, this.deadline);
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), this.deadline);
    }
}
