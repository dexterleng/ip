package pookie.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import pookie.Pookie;
import pookie.Storage;
import pookie.TaskList;
import pookie.model.Deadline;
import pookie.ui.Ui;

public class DeadlineCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String arguments = input.substring(8).trim();
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2) {
            ui.showMessage("usage: deadline <description> /by <deadline>");
            return;
        }
        String description = parts[0].trim();
        String deadlineStr = parts[1].trim();
        LocalDateTime deadline = null;
        try {
            deadline = LocalDateTime.parse(deadlineStr, Pookie.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            ui.showInvalidDateError();
            return;
        }
        tasks.add(new Deadline(false, description, deadline));
        ui.showMessages(
                "Got it. I've added this task:",
                "  " + tasks.get(tasks.size() - 1),
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }
}
