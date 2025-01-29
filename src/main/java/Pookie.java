import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";
    private static final File FILE = new File(FILE_PATH);
    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private Storage storage;
    private TaskList tasks;
    private boolean testMode;

    public Pookie(Storage storage, boolean testMode) throws CorruptFileException, IOException {
        this.storage = storage;
        this.tasks = new TaskList(storage.loadTasks());
        this.testMode = testMode;
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