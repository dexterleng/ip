import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class EventCommand extends Command {
    @Override
    public void execute(String input, TaskList tasks, Storage storage, boolean testMode) throws Exception {
        String arguments = input.substring(5).trim();

        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            System.out.println("____________________________________________________________");
            System.out.println(" usage: event <description> /from <start time> /to <end time>");
            System.out.println("____________________________________________________________\n");
            return;
        }

        String description = arguments.substring(0, fromIndex).trim();
        String fromStr = arguments.substring(fromIndex + 7, toIndex + 1).trim();
        String toStr = arguments.substring(toIndex + 5).trim();
        if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" usage: event <description> /from <start time> /to <end time>");
            System.out.println("____________________________________________________________\n");
            return;
        }

        LocalDateTime from = null;
        LocalDateTime to = null;
        try {
            from = LocalDateTime.parse(fromStr, Pookie.INPUT_FORMATTER);
            to = LocalDateTime.parse(toStr, Pookie.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            Pookie.displayInvalidDateError();
            return;
        }

        tasks.add(new Event(false, description, from, to));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1));
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________\n");
    }
}
