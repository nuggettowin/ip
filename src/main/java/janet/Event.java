package janet;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/**
 * Represents a task that takes place over a specified period.
 */
public class Event extends Task {

    private final LocalDate from;
    private final LocalDate to;
    private static final String TASK_TYPE = "E";
    protected static final String EVENT_FROM_SEP = "/from";
    protected static final String EVENT_TO_SEP = "/to";
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    // TODO: handle case where start > end
    /**
     * Creates a <code>Todo</code> task type
     * with the specified completion status, type, label, starting date, and end date.
     * @throws JanetException If the task label is empty.
     */
    public Event(boolean isDone, String taskLabel, LocalDate from, LocalDate to) throws JanetException {
        super(isDone, Event.TASK_TYPE, taskLabel);
        this.from = from;
        this.to = to;
    }


    @Override
    public Task markDone() throws JanetException {
        return new Event(true, super.taskLabel, this.from, this.to);
    }

    @Override
    public String toFileFormat() {
        return String.join(
                Task.FILE_DELIMITER,
                super.toFileFormat(),
                this.from.toString(),
                this.to.toString()
        );
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s to: %s)",
                super.toString(),
                this.from.format(Event.DATE_TIME_FORMAT),
                this.to.format(Event.DATE_TIME_FORMAT)
        );
    }
}
