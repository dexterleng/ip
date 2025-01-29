import java.io.IOException;

public class ByeCommand extends Command {
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
        if (!testMode) {
            storage.saveTasks(tasks.getList()); // Only save if not in test mode
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
