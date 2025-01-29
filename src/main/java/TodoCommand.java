import java.io.IOException;

public class TodoCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("____________________________________________________________");
            System.out.println(" usage: todo <description>");
            System.out.println("____________________________________________________________\n");
            return;
        }
        String description = parts[1].trim();

        tasks.add(new Todo(false, description));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________\n");
    }
}
