package pookie.command;

import pookie.Storage;
import pookie.TaskList;
import pookie.model.Task;
import pookie.ui.Ui;

public class DeleteCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                ui.showMessage("usage: delete <task number>");
                return;
            }

            int index = Integer.parseInt(parts[1].trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task removedTask = tasks.remove(index);
                ui.showMessages(
                        "Noted. I've removed this task:",
                        "  " + removedTask,
                        "Now you have " + tasks.size() + " tasks in the list."
                );
            } else {
                ui.showInvalidTaskNumberError();
            }
        } catch (NumberFormatException e) {
            ui.showInvalidTaskNumberError();
        }
    }
}
