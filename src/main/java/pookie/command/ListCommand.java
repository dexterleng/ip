package pookie.command;

import pookie.Storage;
import pookie.TaskList;
import pookie.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        if (tasks.isEmpty()) {
            ui.showMessage("No tasks added yet.");
        } else {
            String[] messages = new String[tasks.size() + 1];
            messages[0] = "Here are the tasks in your list:";
            for (int i = 0; i < tasks.size(); i++) {
                messages[i + 1] = (i + 1) + ". " + tasks.get(i);
            }
            ui.showMessages(messages);
        }
    }
}
