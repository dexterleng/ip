import java.util.Scanner;

public class Pookie {
    static abstract class Task {
        private String description;
        private boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
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
    }

    static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T][" + getStatusIcon() + "] " + getDescription();
        }
    }

    static class Deadline extends Task {
        private String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by + ")";
        }
    }

    static class Event extends Task {
        private String from;
        private String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E][" + getStatusIcon() + "] " + getDescription() + " (from: " + from + " to: " + to + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Pookie");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100]; // Fixed-size array to store up to 100 tasks
        int taskCount = 0; // Counter for the number of tasks stored

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet.");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("mark")) {
                try {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2) {
                        displayInvalidTaskNumberError();
                        continue;
                    }

                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________\n");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Invalid task number.");
                        System.out.println("____________________________________________________________\n");
                    }
                } catch (NumberFormatException e) {
                    displayInvalidTaskNumberError();
                }
            } else if (input.startsWith("unmark")) {
                try {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2) {
                        displayInvalidTaskNumberError();
                        continue;
                    }

                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[index]);
                        System.out.println("____________________________________________________________\n");
                    } else {
                        displayInvalidTaskNumberError();
                    }
                } catch (NumberFormatException e) {
                    displayInvalidTaskNumberError();
                }
            } else if (input.startsWith("todo")) {
                // TODO: handle error
                String description = input.substring(5).trim();
                tasks[taskCount++] = new Todo(description);
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ");
                if (parts.length == 2) {
                    tasks[taskCount++] = new Deadline(parts[0].trim(), parts[1].trim());
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________\n");
                }
            } else if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                if (parts.length == 3) {
                    tasks[taskCount++] = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________\n");
                }
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(" Invalid command.");
                System.out.println("____________________________________________________________\n");
            }
        }

        scanner.close();
    }

    private static void displayInvalidTaskNumberError() {
        System.out.println("____________________________________________________________");
        System.out.println(" Please provide a valid task number.");
        System.out.println("____________________________________________________________\n");
    }
}
