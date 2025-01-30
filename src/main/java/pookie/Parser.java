package pookie;

import pookie.command.*;

/**
 * Parses user input and returns the corresponding command to execute.
 * The Parser determines which command to return based on the input string.
 */
public class Parser {

    /**
     * Parses the given user input and returns the appropriate command object.
     *
     * @param input The user input as a string.
     * @return The corresponding Command object to execute.
     */
    public static Command parse(String input) {
        if (input.equals("bye")) {
            return new ByeCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("mark")) {
            return new MarkCommand();
        } else if (input.startsWith("unmark")) {
            return new UnmarkCommand();
        } else if (input.startsWith("delete")) {
            return new DeleteCommand();
        } else if (input.startsWith("todo")) {
            return new TodoCommand();
        } else if (input.startsWith("deadline")) {
            return new DeadlineCommand();
        } else if (input.startsWith("event")) {
            return new EventCommand();
        } else {
            return new InvalidCommand();
        }
    }
}