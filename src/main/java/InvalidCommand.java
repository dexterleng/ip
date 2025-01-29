import java.io.IOException;

public class InvalidCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        System.out.println("____________________________________________________________");
        System.out.println(" Invalid command.");
        System.out.println("____________________________________________________________\n");
    }
}
