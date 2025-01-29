public class Todo extends Task {
    public Todo(Boolean isDone, String description) {
        super(isDone, description);
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + getDescription();
    }

    @Override
    public String toFileString() {
        return "T | " + (getIsDone() ? 1 : 0) + " | " + getDescription();
    }
}