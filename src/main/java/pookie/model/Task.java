package pookie.model;

public abstract class Task {
    private boolean isDone;
    private String description;

    public Task(Boolean isDone, String description) {
        this.isDone = isDone;
        this.description = description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[ ] " + description;
    }

    public abstract String toFileString();
}