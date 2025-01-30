package pookie.command;

import pookie.Storage;
import pookie.TaskList;
import pookie.model.Todo;
import pookie.ui.Ui;

public class TodoCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            ui.showMessage("usage: todo <description>");
            return;
        }
        String description = parts[1].trim();

        tasks.add(new Todo(false, description));
        ui.showMessages(
            "Got it. I've added this task:",
            "  " + tasks.get(tasks.size() - 1),
            "Now you have " + tasks.size() + " tasks in the list."
        );
    }
}
