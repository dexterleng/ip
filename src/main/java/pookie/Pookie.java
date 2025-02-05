package pookie;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import pookie.command.Command;
import pookie.ui.ConsoleUi;
import pookie.ui.Ui;

/**
 * The main class for the Pookie application.
 * Pookie is a task manager that allows users to create, manage, and save tasks
 * including todos, events, and deadlines.
 */
public class Pookie {
    private static final String FILE_PATH = "./data/pookie.txt";

    /**
     * Formatter for parsing user input dates in the format "d/M/yyyy HHmm".
     */
    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Formatter for displaying dates in the output format "MMM dd yyyy, h:mma".
     */
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private boolean testMode;

    /**
     * Constructs the Pookie application with the given UI, storage, and test mode flag.
     *
     * @param ui        The user interface used for interaction.
     * @param storage   The storage system used for loading and saving tasks.
     * @param testMode  Boolean flag indicating if the application is running in test mode.
     * @throws CorruptFileException If the saved task file is corrupted.
     * @throws IOException          If an I/O error occurs during file loading.
     */
    public Pookie(Ui ui, Storage storage, boolean testMode) throws CorruptFileException, IOException {
        this.ui = ui;
        this.storage = storage;
        this.tasks = new TaskList(storage.loadTasks());
        this.testMode = testMode;
    }

    public Pookie(boolean testMode) throws CorruptFileException, IOException {
        this.ui = new ConsoleUi();
        this.storage = new Storage(new File(FILE_PATH));
        this.tasks = new TaskList(storage.loadTasks());
        this.testMode = testMode;
    }

    /**
     * The main entry point of the Pookie application.
     * It initializes the necessary components and starts the application.
     *
     * @param args Command-line arguments.
     * @throws Exception If any exception occurs during the application startup or execution.
     */
    public static void main(String[] args) throws Exception {
        boolean testMode = args.length > 0 && args[0].equals("--test");

        // Check for test mode from command-line arguments
        Ui ui = new ConsoleUi();
        Storage storage = new Storage(new File(FILE_PATH));
        Pookie pookie = new Pookie(ui, storage, testMode);
        pookie.run();
    }

    /**
     * Runs the main loop of the Pookie application.
     * The loop processes user commands until an exit command is received.
     *
     * @throws Exception If an error occurs during command execution.
     */
    public void run() throws Exception {
        ui.showMessages(
                "Hello! I'm pookie.Pookie",
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

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Pookie heard: " + input;
    }
}