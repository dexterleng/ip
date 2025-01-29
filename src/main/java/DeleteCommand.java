import java.io.IOException;

public class DeleteCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("____________________________________________________________");
                System.out.println(" usage: delete <task number>");
                System.out.println("____________________________________________________________\n");
                return;
            }

            int index = Integer.parseInt(parts[1].trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task removedTask = tasks.remove(index);
                System.out.println("____________________________________________________________");
                System.out.println(" Noted. I've removed this task:");
                System.out.println("   " + removedTask);
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________\n");
            } else {
                Pookie.displayInvalidTaskNumberError();
            }
        } catch (NumberFormatException e) {
            Pookie.displayInvalidTaskNumberError();
        }
    }
}
