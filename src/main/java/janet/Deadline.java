package janet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a <code>Task</code> that must be completed by a specified deadline.
 */
public class Deadline extends Task {

    private final LocalDate deadline;
    private final static String TASK_TYPE = "D";
    protected final static String DEADLINE_SEP = "/by";
    private final static DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Creates a <code>Deadline</code> task type with the specified completion status, type, label, and deadline.
     * @throws JanetException If the task label is empty.
     */
    public Deadline(boolean isDone, String taskLabel, LocalDate deadline) throws JanetException {
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
