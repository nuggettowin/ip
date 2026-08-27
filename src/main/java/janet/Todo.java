package janet;

/**
 * Represents a <code>Task</code> that can be completed with no deadline.
 */
public class Todo extends Task {
    private static final String TASK_TYPE = "T";

    public Todo(boolean isDone, String taskLabel) throws JanetException {
        super(isDone, Todo.TASK_TYPE, taskLabel);
    }

    @Override
    public Task markDone() throws JanetException {
        return new Todo(true, this.taskLabel);
    }
}
