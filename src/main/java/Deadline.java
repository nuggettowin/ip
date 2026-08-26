import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    private final LocalDate deadline;
    private final static String TASK_TYPE = "D";
    protected final static String DEADLINE_SEP = "/by";
    private final static DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(boolean isDone, String taskLabel, LocalDate deadline) throws JanetException {
        super(isDone, Deadline.TASK_TYPE, taskLabel);
        this.deadline = deadline
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
