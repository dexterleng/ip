package pookie.model;

/**
 * Represents a to-do task with a description and completion status.
 * A Todo task is a simple task without deadlines or time ranges.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task.
     *
     * @param isDone      Boolean indicating whether the task is completed.
     * @param description The description of the to-do task.
     */
    public Todo(Boolean isDone, String description) {
        super(isDone, description);
    }

    /**
     * Returns the string representation of the to-do task, including its type, status, and description.
     *
     * @return A formatted string representation of the Todo task.
     */
    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Returns a string suitable for saving the to-do task to a file.
     * The format includes the task type, completion status, and description.
     *
     * @return A formatted string for file storage of the Todo task.
     */
    @Override
    public String toFileString() {
        return "T | " + (getIsDone() ? 1 : 0) + " | " + getDescription();
    }
}