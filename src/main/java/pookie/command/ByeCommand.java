package pookie.command;

import pookie.Storage;
import pookie.TaskList;
import pookie.ui.Ui;

public class ByeCommand extends Command {
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean isTestMode) throws Exception {
        ui.showMessage("Bye. Hope to see you again soon!");
        if (!isTestMode) {
            storage.saveTasks(tasks.getList()); // Only save if not in test mode
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
