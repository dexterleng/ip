package pookie.ui;

/**
 * Represents the user interface for interacting with the application.
 * The Ui interface defines methods for displaying messages, reading user commands,
 * and showing error messages related to task or input validation.
 */
public interface Ui {

    /**
     * Displays a single message to the user.
     *
     * @param message The message to display.
     */
    void showMessage(String message);

    /**
     * Displays multiple messages to the user.
     *
     * @param messages An array of messages to display.
     */
    void showMessages(String... messages);

    /**
     * Reads a command entered by the user.
     *
     * @return The user input as a string.
     */
    String readCommand();

    /**
     * Displays an error message indicating an invalid task number was provided.
     */
    void showInvalidTaskNumberError();

    /**
     * Displays an error message indicating an invalid date was provided.
     */
    void showInvalidDateError();

    /**
     * Closes any resources used by the user interface (e.g., input streams).
     */
    void close();
}