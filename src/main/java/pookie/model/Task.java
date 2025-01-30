package pookie.model;

/**
 * Represents a general task with a description and a completion status.
 * This abstract class is intended to be extended by specific types of tasks
 * like Deadline and Event.
 */
public abstract class Task {
    private boolean isDone;
    private String description;

    /**
     * Constructs a Task with its completion status and description.
     *
     * @param isDone      Boolean indicating whether the task is completed.
     * @param description The description of the task.
     */
    public Task(Boolean isDone, String description) {
        this.isDone = isDone;
        this.description = description;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return True if the task is done, otherwise false.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a status icon representing whether the task is done or not.
     * An 'X' indicates the task is completed, while an empty space means it is not.
     *
     * @return A string containing either 'X' or ' '.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // Mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task, displaying its description.
     *
     * @return A string containing the task's description.
     */
    @Override
    public String toString() {
        return "[ ] " + description;
    }

    /**
     * Returns a string representation of the task formatted for file storage.
     * This method must be implemented by subclasses to include their specific details.
     *
     * @return A formatted string for file storage of the task.
     */
    public abstract String toFileString();
}