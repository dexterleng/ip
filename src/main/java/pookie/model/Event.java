package pookie.model;

import java.time.LocalDateTime;

import pookie.Pookie;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(Boolean isDone, String description, LocalDateTime from, LocalDateTime to) {
        super(isDone, description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDescription() + " (from: " + from.format(Pookie.OUTPUT_FORMATTER) + " to: " + to.format(Pookie.OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + from.format(Pookie.OUTPUT_FORMATTER) + " | " + to.format(Pookie.OUTPUT_FORMATTER);
    }
}