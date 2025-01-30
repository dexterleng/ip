import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";
    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private boolean testMode;

    public Pookie(Ui ui, Storage storage, boolean testMode) throws CorruptFileException, IOException {
        this.ui = ui;
        this.storage = storage;
        this.tasks = new TaskList(storage.loadTasks());
        this.testMode = testMode;
    }

    public static void main(String[] args) throws Exception {
        boolean testMode = false;

        // Check for test mode from command-line arguments
        if (args.length > 0 && args[0].equals("--test")) {
            testMode = true;
        }

        Ui ui = new ConsoleUi();
        Storage storage = new Storage(new File(FILE_PATH));
        Pookie pookie = new Pookie(ui, storage, testMode);
        pookie.run();
    }

    public void run() throws Exception {
        ui.showMessages(
            "Hello! I'm Pookie",
            "What can I do for you?"
        );

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            Command command = Parser.parse(input);
            command.execute(input, ui, tasks, storage, testMode);
            isExit = command.isExit();
        }

        ui.close();
    }
}