import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";
    private static final File FILE = new File(FILE_PATH);
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private Storage storage;
    private TaskList tasks;
    private boolean testMode;

    public Pookie(Storage storage, boolean testMode) throws CorruptFileException, IOException {
        this.storage = storage;
        this.tasks = new TaskList(storage.loadTasks());
        this.testMode = testMode;
    }

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
        private LocalDateTime by;

        public Deadline(Boolean isDone, String description, LocalDateTime by) {
            super(isDone, description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D][" + getStatusIcon() + "] " + getDescription() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
        }

        @Override
        public String toFileString() {
            return "D | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + by.format(OUTPUT_FORMATTER);
        }
    }

    static class Event extends Task {
        private LocalDateTime from;
        private LocalDateTime to;

        public Event(Boolean isDone, String description, LocalDateTime from, LocalDateTime to) {
            super(isDone, description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E][" + getStatusIcon() + "] " + getDescription() + " (from: " + from.format(OUTPUT_FORMATTER) + " to: " + to.format(OUTPUT_FORMATTER) + ")";
        }

        @Override
        public String toFileString() {
            return "E | " + (getIsDone() ? 1 : 0) + " | " + getDescription() + " | " + from.format(OUTPUT_FORMATTER) + " | " + to.format(OUTPUT_FORMATTER);
        }
    }

    static class Storage {
        private File file;

        public Storage(File file) {
            this.file = file;
        }

        public ArrayList<Task> loadTasks() throws CorruptFileException, IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
                            String byStr = parts[3];
                            LocalDateTime by = null;
                            try {
                                by = LocalDateTime.parse(byStr, OUTPUT_FORMATTER);
                            } catch (DateTimeParseException e) {
                                throw new CorruptFileException();
                            }
                            task = new Deadline(isDone, description, by);
                            break;
                        case "E":
                            if (parts.length != 5) throw new CorruptFileException();
                            String fromStr = parts[3];
                            String toStr = parts[4];
                            LocalDateTime from = null;
                            LocalDateTime to = null;
                            try {
                                from = LocalDateTime.parse(fromStr, OUTPUT_FORMATTER);
                                to = LocalDateTime.parse(toStr, OUTPUT_FORMATTER);
                            } catch (DateTimeParseException e) {
                                throw new CorruptFileException();
                            }
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

        public void saveTasks(ArrayList<Task> tasks) throws IOException {
            file.getParentFile().mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (Task task : tasks) {
                pw.println(task.toFileString());
            }
            pw.close();
        }
    }

    static class TaskList {
        private ArrayList<Task> tasks;

        public TaskList() {
            this.tasks = new ArrayList<>();
        }

        public TaskList(ArrayList<Task> tasks) {
            this.tasks = tasks;
        }

        public Task get(int index) {
            return tasks.get(index);
        }

        public void add(Task task) {
            tasks.add(task);
        }

        public Task remove(int index) {
            return tasks.remove(index);
        }

        public int size() {
            return tasks.size();
        }

        public boolean isEmpty() {
            return tasks.isEmpty();
        }

        public ArrayList<Task> getList() {
            return new ArrayList<>(tasks);
        }
    }

    public static void main(String[] args) throws CorruptFileException, IOException {
        boolean testMode = false;

        // Check for test mode from command-line arguments
        if (args.length > 0 && args[0].equals("--test")) {
            testMode = true;
        }

        Storage storage = new Storage(new File(FILE_PATH));
        Pookie pookie = new Pookie(storage, testMode);
        pookie.run();
    }

    public void run() throws IOException {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Pookie");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                if (!testMode) {
                    storage.saveTasks(tasks.getList()); // Only save if not in test mode
                }
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
                String deadlineStr = parts[1].trim();
                LocalDateTime deadline = null;
                try {
                    deadline = LocalDateTime.parse(deadlineStr, INPUT_FORMATTER);
                } catch (DateTimeParseException e) {
                    displayInvalidDateError();
                    continue;
                }
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
                String fromStr = arguments.substring(fromIndex + 7, toIndex + 1).trim();
                String toStr = arguments.substring(toIndex + 5).trim();
                if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" usage: event <description> /from <start time> /to <end time>");
                    System.out.println("____________________________________________________________\n");
                    continue;
                }

                LocalDateTime from = null;
                LocalDateTime to = null;
                try {
                    from = LocalDateTime.parse(fromStr, INPUT_FORMATTER);
                    to = LocalDateTime.parse(toStr, INPUT_FORMATTER);
                } catch (DateTimeParseException e) {
                    displayInvalidDateError();
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

    private void displayInvalidTaskNumberError() {
        System.out.println("____________________________________________________________");
        System.out.println(" Please provide a valid task number.");
        System.out.println("____________________________________________________________\n");
    }

    private void displayInvalidDateError() {
        System.out.println("____________________________________________________________");
        System.out.println(" Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
        System.out.println("____________________________________________________________\n");
    }
}