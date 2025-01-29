import java.io.IOException;

public class InvalidCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        ui.showMessage("Invalid command.");
    }
}
