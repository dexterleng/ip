import java.util.ArrayList;
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
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (tasks.isEmpty()) {
                    System.out.println(" No tasks added yet.");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("mark")) {
                try {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println(" usage: mark <task number>");
                        System.out.println("____________________________________________________________\n");
                        continue;
                    }

                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(index));
                        System.out.println("____________________________________________________________\n");
                    } else {
                        displayInvalidTaskNumberError();
                    }
                } catch (NumberFormatException e) {
                    displayInvalidTaskNumberError();
                }
            } else if (input.startsWith("unmark")) {
                try {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println(" usage: unmark <task number>");
                        System.out.println("____________________________________________________________\n");
                        continue;
                    }

                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(index));
                        System.out.println("____________________________________________________________\n");
                    } else {
                        displayInvalidTaskNumberError();
                    }
                } catch (NumberFormatException e) {
                    displayInvalidTaskNumberError();
                }
            } else if (input.startsWith("delete")) {
                try {
                    String[] parts = input.split(" ", 2);
                    if (parts.length < 2) {
                        System.out.println("____________________________________________________________");
                        System.out.println(" usage: delete <task number>");
                        System.out.println("____________________________________________________________\n");
                        continue;
                    }

                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removedTask);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________\n");
                    } else {
                        displayInvalidTaskNumberError();
                    }
                } catch (NumberFormatException e) {
                    displayInvalidTaskNumberError();
                }
            } else if (input.startsWith("todo")) {
                String[] parts = input.split(" ", 2);
                if (parts.length < 2) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" usage: todo <description>");
                    System.out.println("____________________________________________________________\n");
                    continue;
                }
                String description = parts[1].trim();

                tasks.add(new Todo(description));
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks.get(tasks.size() - 1));
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("deadline")) {
                String arguments = input.substring(8).trim();
                String[] parts = arguments.split(" /by ", 2);
                if (parts.length < 2) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" usage: deadline <description> /by <deadline>");
                    System.out.println("____________________________________________________________\n");
                    continue;
                }
                String description = parts[0].trim();
                String deadline = parts[1].trim();

                tasks.add(new Deadline(description, deadline));
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks.get(tasks.size() - 1));
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________\n");
            } else if (input.startsWith("event")) {
                String arguments = input.substring(5).trim();

                int fromIndex = arguments.indexOf(" /from ");
                int toIndex = arguments.indexOf(" /to ");

                if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" usage: event <description> /from <start time> /to <end time>");
                    System.out.println("____________________________________________________________\n");
                    continue;
                }

                String description = arguments.substring(0, fromIndex).trim();
                String from = arguments.substring(fromIndex + 7, toIndex + 1).trim();
                String to = arguments.substring(toIndex + 5).trim();

                if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" usage: event <description> /from <start time> /to <end time>");
                    System.out.println("____________________________________________________________\n");
                    continue;
                }

                tasks.add(new Event(description, from, to));
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks.get(tasks.size() - 1));
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________\n");
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