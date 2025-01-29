import java.io.IOException;

public class ListCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________\n");
    }
}
