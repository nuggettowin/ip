public class Todo extends Task {
    public Todo(boolean isDone, String taskLabel) {
        super(isDone, taskLabel);
    }

    @Override
    public String toString() {
        return "todo";
    }
}
