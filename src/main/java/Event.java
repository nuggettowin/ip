public class Event extends Task {
    private final String from;
    private final String to;
    private static final String TASK_TYPE = "E";

    public Event(boolean isDone, String argsLine) {
        int fromIndex = argsLine.indexOf("/from");
        int toIndex = argsLine.indexOf("/to");
        String taskLabel = argsLine.substring(0, fromIndex);
        String from = argsLine.substring(fromIndex, toIndex);
        String to = argsLine.substring(toIndex);

        this.from = from;
        this.to = to;
        super(isDone, Event.TASK_TYPE, taskLabel);
    }

    private Event(boolean isDone, String taskLabel, String from, String to) {
        super(isDone, Event.TASK_TYPE, taskLabel);
        this.from = from;
        this.to = to;
    }

    @Override
    public Task markDone() {
        return new Event(true, super.taskLabel, this.from, this.to);
    }

    @Override
    public String toString() {
        return super.taskLabel;
    }
}
