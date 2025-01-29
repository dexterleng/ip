public abstract class Command {
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {

    }

    public boolean isExit() {
        return false;
    }
}
