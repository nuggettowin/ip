public class Event extends Task {
    public Event(boolean isDone, String taskLabel) {
        super(isDone, taskLabel);
    }

    @Override
    public String toString() {
        return "Event";
    }
}
