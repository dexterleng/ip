package pookie;

import pookie.command.ByeCommand;
import pookie.command.Command;
import pookie.command.DeadlineCommand;
import pookie.command.DeleteCommand;
import pookie.command.EventCommand;
import pookie.command.InvalidCommand;
import pookie.command.ListCommand;
import pookie.command.MarkCommand;
import pookie.command.TodoCommand;
import pookie.command.UnmarkCommand;

public class Parser {
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