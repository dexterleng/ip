public class ByeCommand extends Command {
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        ui.showMessage("Bye. Hope to see you again soon!");
        if (!testMode) {
            storage.saveTasks(tasks.getList()); // Only save if not in test mode
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
