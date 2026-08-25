import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;
    private static final String TASK_TYPE = "E";
    private final static String FROM_SEP = "/from";
    private final static String TO_SEP = "/to";
    private final static DateTimeFormatter dateTimeFormat =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    // TODO: handle case where start > end
    public Event(boolean isDone, String argsLine) throws JanetException {
        int fromIndex = argsLine.indexOf(Event.FROM_SEP);
        int toIndex = argsLine.indexOf(Event.TO_SEP);

        if (fromIndex == -1 || toIndex == -1) {
            throw new JanetException("From or To not found!");
        }
        String taskLabel = argsLine.substring(0, fromIndex).trim();
        String from = argsLine.substring(fromIndex + Event.FROM_SEP.length(), toIndex).trim();
        String to = argsLine.substring(toIndex + Event.TO_SEP.length()).trim();

        if (from.isEmpty() || to.isEmpty()) {
            throw new JanetException(String.format("Invalid event arguments! From: %s, to: %s\n", from, to));
        }

        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            this.from = fromDate;
            this.to = toDate;
        } catch (DateTimeParseException e) {
            throw new JanetException("Invalid date format");
        }

        super(isDone, Event.TASK_TYPE, taskLabel);
    }

    private Event(boolean isDone, String taskLabel, LocalDate from, LocalDate to) throws JanetException {
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
                this.from.format(Event.dateTimeFormat),
                this.to.format(Event.dateTimeFormat)
        );
    }
}
