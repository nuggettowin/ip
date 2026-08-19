public class Event extends Task {
    private final String from;
    private final String to;

    public Event(boolean isDone, String argsLine) {
        int fromIndex = argsLine.indexOf("/from");
        int toIndex = argsLine.indexOf("/to");
        String taskLabel = argsLine.substring(0, fromIndex);
        String from = argsLine.substring(fromIndex, toIndex);
        String to = argsLine.substring(toIndex);

        this.from = from;
        this.to = to;
        super(isDone, taskLabel);
    }

    @Override
    public String toString() {
        return super.taskLabel;
    }
}
