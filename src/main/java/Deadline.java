import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(Boolean isDone, String description, LocalDateTime by) {
        super(isDone, description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by.format(Pookie.OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + by.format(Pookie.OUTPUT_FORMATTER);
    }
}

