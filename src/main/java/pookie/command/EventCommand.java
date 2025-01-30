package pookie.command;

import pookie.*;
import pookie.model.Event;
import pookie.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class EventCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String arguments = input.substring(5).trim();

        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            ui.showMessage("usage: event <description> /from <start time> /to <end time>");
            return;
        }

        String description = arguments.substring(0, fromIndex).trim();
        String fromStr = arguments.substring(fromIndex + 7, toIndex + 1).trim();
        String toStr = arguments.substring(toIndex + 5).trim();
        if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            ui.showMessage("usage: event <description> /from <start time> /to <end time>");
            return;
        }

        LocalDateTime from = null;
        LocalDateTime to = null;
        try {
            from = LocalDateTime.parse(fromStr, Pookie.INPUT_FORMATTER);
            to = LocalDateTime.parse(toStr, Pookie.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            ui.showInvalidDateError();
            return;
        }

        tasks.add(new Event(false, description, from, to));
        ui.showMessages(
            "Got it. I've added this task:" ,
            "  " + tasks.get(tasks.size() - 1),
            "Now you have " + tasks.size() + " tasks in the list."
        );
    }
}
