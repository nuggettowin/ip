public class Deadline extends Task {
    public Deadline(boolean isDone, String taskLabel) {
        super(isDone, taskLabel);
    }

    @Override
    public String toString() {
        return "Deadline";
    }
}
