public class Event extends Task {
    private final String from;
    private final String to;
    private static final String TASK_TYPE = "E";
    private final static String FROM_SEP = "/from";
    private final static String TO_SEP = "/to";

    public Event(boolean isDone, String argsLine) throws JanetException {
        int fromIndex = argsLine.indexOf(Event.FROM_SEP);
        int toIndex = argsLine.indexOf(Event.TO_SEP);
        String taskLabel = argsLine.substring(0, fromIndex).trim();
        String from = argsLine.substring(fromIndex + Event.FROM_SEP.length(), toIndex).trim();
        String to = argsLine.substring(toIndex + Event.TO_SEP.length()).trim();

        this.from = from;
        this.to = to;
        super(isDone, Event.TASK_TYPE, taskLabel);
    }

    private Event(boolean isDone, String taskLabel, String from, String to) throws JanetException {
        super(isDone, Event.TASK_TYPE, taskLabel);
        this.from = from;
        this.to = to;
    }

    @Override
    public Task markDone() throws JanetException {
        return new Event(true, super.taskLabel, this.from, this.to);
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
