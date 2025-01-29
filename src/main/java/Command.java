public abstract class Command {
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {

    }

    public boolean isExit() {
        return false;
    }
}
