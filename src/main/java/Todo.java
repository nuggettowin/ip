public class Todo extends Task {
    private static final String TASK_TYPE = "T";

    public Todo(boolean isDone, String taskLabel) {
        super(isDone, Todo.TASK_TYPE, taskLabel);
    }

    @Override
    public Task markDone() {
        return new Todo(true, this.taskLabel);
    }
}
