import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";
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

    public static void main(String[] args) throws CorruptFileException, Exception {
        boolean testMode = false;

        // Check for test mode from command-line arguments
        if (args.length > 0 && args[0].equals("--test")) {
            testMode = true;
        }

        Storage storage = new Storage(new File(FILE_PATH));
        Pookie pookie = new Pookie(storage, testMode);
        pookie.run();
    }

    public void run() throws Exception {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Pookie");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);

        boolean isExit = false;
        while (!isExit) {
            String input = scanner.nextLine().trim();
            Command command = Parser.parse(input);
            command.execute(input, tasks, storage, testMode);
            isExit = command.isExit();
        }

        scanner.close();
    }

    public static void displayInvalidTaskNumberError() {
        System.out.println("____________________________________________________________");
        System.out.println(" Please provide a valid task number.");
        System.out.println("____________________________________________________________\n");
    }

    public static void displayInvalidDateError() {
        System.out.println("____________________________________________________________");
        System.out.println(" Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
        System.out.println("____________________________________________________________\n");
    }
}