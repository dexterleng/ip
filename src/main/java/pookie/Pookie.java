package pookie;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import pookie.command.ByeCommand;
import pookie.command.Command;
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
    private boolean isTestMode;

    /**
     * Constructs the Pookie application with the given UI, storage, and test mode flag.
     *
     * @param ui        The user interface used for interaction.
     * @param storage   The storage system used for loading and saving tasks.
     * @param isTestMode  Boolean flag indicating if the application is running in test mode.
     * @throws CorruptFileException If the saved task file is corrupted.
     * @throws IOException          If an I/O error occurs during file loading.
     */
    public Pookie(Ui ui, Storage storage, boolean isTestMode) throws CorruptFileException, IOException {
        this.ui = ui;
        this.storage = storage;
        this.tasks = new TaskList(storage.loadTasks());
        this.isTestMode = isTestMode;
    }

    public Pookie(Ui ui, boolean isTestMode) throws CorruptFileException, IOException {
        this.ui = ui;
        this.storage = new Storage(new File(FILE_PATH));
        this.tasks = new TaskList(storage.loadTasks());
        this.isTestMode = isTestMode;
    }

    /**
     * Generates a response for the user's chat message.
     */
    public boolean respond(String input) throws Exception {
        Command command = Parser.parse(input);
        command.execute(input, ui, tasks, storage, isTestMode);
        return command.isExit();
    }

    public void sendInitialMessage() {
        ui.showMessages(
            "Hello! I'm Pookie",
            "What can I do for you?"
        );
    }

    public void bye() throws Exception {
        ByeCommand command = new ByeCommand();
        command.execute("", ui, tasks, storage, isTestMode);
    }
}