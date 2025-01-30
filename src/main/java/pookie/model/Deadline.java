package pookie.model;

import pookie.Pookie;

import java.time.LocalDateTime;

/**
 * Represents a task with a specific deadline.
 * A Deadline task contains a description, a completion status, and a due date/time.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param isDone      Boolean indicating whether the task is completed.
     * @param description Description of the task.
     * @param by          The deadline (date and time) for the task.
     */
    public Deadline(Boolean isDone, String description, LocalDateTime by) {
        super(isDone, description);
        this.by = by;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return The LocalDateTime representing the task's deadline.
     */
    public LocalDateTime getDeadline() {
        return by;
    }

    /**
     * Returns the string representation of the task, including its type, status, description, and deadline.
     *
     * @return A formatted string representation of the Deadline task.
     */
    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by.format(Pookie.OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns a string suitable for saving the task to a file.
     * The format includes the task type, completion status, description, and deadline.
     *
     * @return A formatted string for file storage of the Deadline task.
     */
    @Override
    public String toFileString() {
        return "D | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + by.format(Pookie.OUTPUT_FORMATTER);
    }
}