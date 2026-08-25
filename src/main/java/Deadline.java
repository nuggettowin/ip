import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDate deadline;
    private final static String TASK_TYPE = "D";
    private final static String DEADLINE_SEP = "/by";
    private final static DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("MMM d yyyy");

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

        try {
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new JanetException("Invalid date format");
        }

        super(isDone, Deadline.TASK_TYPE, taskLabel);
    }

    private Deadline(boolean isDone, String taskLabel, LocalDate deadline) throws JanetException {
        super(isDone, Deadline.TASK_TYPE, taskLabel);
        this.deadline = deadline;
    }

    @Override
    public Task markDone() throws JanetException {
        return new Deadline(true, super.taskLabel, this.deadline);
    }

    @Override
    public String toFileFormat() {
        return String.join(Task.FILE_DELIMITER, super.toFileFormat(), this.deadline.toString());
    }

    @Override
    public String toString() {
        return String.format(
                "%s (by: %s)",
                super.toString(),
                this.deadline.format(Deadline.dateTimeFormat)
        );
    }
}
