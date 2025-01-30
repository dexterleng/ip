package pookie.model;

import java.time.LocalDateTime;

import pookie.Pookie;

/**
 * Represents an event task with a specific start and end time.
 * An Event task contains a description, a completion status, a start time, and an end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event task.
     *
     * @param isDone      Boolean indicating whether the task is completed.
     * @param description Description of the event.
     * @param from        The start date and time of the event.
     * @param to          The end date and time of the event.
     */
    public Event(Boolean isDone, String description, LocalDateTime from, LocalDateTime to) {
        super(isDone, description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date and time of the event.
     *
     * @return The LocalDateTime representing when the event starts.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date and time of the event.
     *
     * @return The LocalDateTime representing when the event ends.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns the string representation of the event, including its type, status, description, start, and end times.
     *
     * @return A formatted string representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDescription() +
                " (from: " + from.format(Pookie.OUTPUT_FORMATTER) +
                " to: " + to.format(Pookie.OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns a string suitable for saving the event to a file.
     * The format includes the task type, completion status, description, start time, and end time.
     *
     * @return A formatted string for file storage of the Event task.
     */
    @Override
    public String toFileString() {
        return "E | " + (getIsDone() ? 1 : 0) + " | " + getDescription() +
                " | " + from.format(Pookie.OUTPUT_FORMATTER) +
                " | " + to.format(Pookie.OUTPUT_FORMATTER);
    }
}