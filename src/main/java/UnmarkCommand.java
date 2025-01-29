import java.io.IOException;

public class UnmarkCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                System.out.println("____________________________________________________________");
                System.out.println(" usage: unmark <task number>");
                System.out.println("____________________________________________________________\n");
                return;
            }

            int index = Integer.parseInt(parts[1].trim()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.get(index).markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(index));
                System.out.println("____________________________________________________________\n");
            } else {
                Pookie.displayInvalidTaskNumberError();
            }
        } catch (NumberFormatException e) {
            Pookie.displayInvalidTaskNumberError();
        }
    }
}
