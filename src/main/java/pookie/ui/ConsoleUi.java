package pookie.ui;

import java.util.Scanner;

/**
 * A console-based implementation of the Ui interface for user interaction.
 * This class handles displaying messages, reading user input, and showing error messages
 * through the standard console.
 */
public class ConsoleUi implements Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays a single message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        showMessages(message);
    }

    /**
     * Displays multiple messages to the user, each on a new line,
     * surrounded by separator lines for better readability.
     *
     * @param messages An array of messages to display.
     */
    public void showMessages(String... messages) {
        printLine();
        for (String message : messages) {
            System.out.println(" " + message);
        }
        printLine();
        System.out.println();
    }

    /**
     * Reads a command entered by the user from the console.
     *
     * @return The trimmed user input as a string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays an error message when an invalid task number is provided.
     */
    public void showInvalidTaskNumberError() {
        showMessage("Please provide a valid task number.");
    }

    /**
     * Displays an error message when an invalid date is provided.
     * The message also specifies the expected date format.
     */
    public void showInvalidDateError() {
        showMessage("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
    }

    /**
     * Closes the Scanner used for reading user input.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Prints a horizontal line separator to the console.
     */
    private void printLine() {
        System.out.println("____________________________________________________________");
    }
}