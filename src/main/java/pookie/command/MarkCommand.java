package pookie.command;

import pookie.Storage;
import pookie.TaskList;
import pookie.ui.Ui;

public class MarkCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                ui.showMessage("usage: mark <task number>");
                return;
            }
            int index = Integer.parseInt(parts[1].trim()) - 1;

            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsDone();
                ui.showMessages(
                        "Nice! I've marked this task as done:",
                        "  " + tasks.get(index)
                );
            } else {
                ui.showInvalidTaskNumberError();
            }
        } catch (NumberFormatException e) {
            ui.showInvalidTaskNumberError();
        }
    }
}
