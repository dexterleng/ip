import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String arguments = input.substring(8).trim();
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2) {
            System.out.println("____________________________________________________________");
            System.out.println(" usage: deadline <description> /by <deadline>");
            System.out.println("____________________________________________________________\n");
            return;
        }
        String description = parts[0].trim();
        String deadlineStr = parts[1].trim();
        LocalDateTime deadline = null;
        try {
            deadline = LocalDateTime.parse(deadlineStr, Pookie.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            Pookie.displayInvalidDateError();
            return;
        }
        tasks.add(new Deadline(false, description, deadline));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________\n");
    }
}
