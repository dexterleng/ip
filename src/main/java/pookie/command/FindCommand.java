package pookie.command;

import java.util.List;
import java.util.stream.Collectors;

import pookie.Storage;
import pookie.TaskList;
import pookie.model.Task;
import pookie.ui.Ui;

public class FindCommand extends Command {
    @Override
    public void execute(String input, Ui ui, TaskList tasks, Storage storage, boolean isTestMode) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            ui.showMessage("usage: find <keyword>");
            return;
        }

        String keyword = parts[1].trim().toLowerCase();

        List<Task> matchingTasks = tasks.getList().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword))
                .toList();

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No matching tasks found.");
        } else {
            String[] messages = new String[matchingTasks.size() + 1];
            messages[0] = "Here are the matching tasks in your list:";
            for (int i = 0; i < matchingTasks.size(); i++) {
                messages[i + 1] = (i + 1) + ". " + matchingTasks.get(i);
            }
            ui.showMessages(messages);
        }
    }
}