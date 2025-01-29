import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";
    private static final File FILE = new File(FILE_PATH);

    static class CorruptFileException extends Exception {
        public CorruptFileException() {
            super("Save file is corrupt.");
        }
    }

    static abstract class Task {
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

    static class Todo extends Task {
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

    static class Deadline extends Task {
        private String by;

        public Deadline(Boolean isDone, String description, String by) {
            super(isDone, description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by + ")";
        }

        @Override
        public String toFileString() {
            return "D | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + by;
        }
    }

    static class Event extends Task {
        private String from;
        private String to;

        public Event(Boolean isDone, String description, String from, String to) {
            super(isDone, description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E][" + getStatusIcon() + "] " + getDescription() + " (from: " + from + " to: " + to + ")";
        }

        @Override
        public String toFileString() {
            return "E | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + from + " | " + to;
        }
    }

    public static void main(String[] args) throws CorruptFileException, IOException {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Pookie");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                saveTasks(tasks);
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

                tasks.add(new Todo(false, description));
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

                tasks.add(new Deadline(false, description, deadline));
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

                tasks.add(new Event(false, description, from, to));
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

    private static ArrayList<Task> loadTasks() throws CorruptFileException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) throw new CorruptFileException();

                if (!parts[1].equals("1") && !parts[1].equals("0")) {
                    throw new CorruptFileException();
                }
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                switch (parts[0]) {
                    case "T":
                        if (parts.length != 3) throw new CorruptFileException();
                        task = new Todo(isDone, description);
                        break;
                    case "D":
                        if (parts.length != 4) throw new CorruptFileException();
                        String by = parts[3];
                        task = new Deadline(isDone, description, by);
                        break;
                    case "E":
                        if (parts.length != 5) throw new CorruptFileException();
                        String from = parts[3];
                        String to = parts[4];
                        task = new Event(isDone, description, from, to);
                        break;
                    default:
                        throw new CorruptFileException();
                }
                tasks.add(task);
            }
            return tasks;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) throws IOException {
        FILE.getParentFile().mkdirs();
        PrintWriter pw = new PrintWriter(new FileWriter(FILE));
        for (Task task : tasks) {
            pw.println(task.toFileString());
        }
        pw.close();
    }
}