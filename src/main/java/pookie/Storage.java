package pookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import pookie.model.Deadline;
import pookie.model.Event;
import pookie.model.Task;
import pookie.model.Todo;

/**
 * Handles the loading and saving of tasks from and to a file.
 * The Storage class reads tasks from a file, reconstructs them, and saves
 * updated tasks back to the file.
 */
public class Storage {
    private File file;

    /**
     * Constructs a Storage object that manages tasks from the specified file.
     *
     * @param file The file used to load and save tasks.
     */
    public Storage(File file) {
        this.file = file;
    }

    /**
     * Loads tasks from the specified file.
     * Tasks are reconstructed based on their type and stored in an ArrayList.
     * If the file is missing, an empty list is returned. Throws an exception if
     * the file is corrupted or incorrectly formatted.
     *
     * @return An ArrayList containing the loaded tasks.
     * @throws CorruptFileException If the file is corrupted or incorrectly formatted.
     * @throws IOException          If an I/O error occurs during file reading.
     */
    public ArrayList<Task> loadTasks() throws CorruptFileException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) throw new CorruptFileException();

                // Validate the task completion flag
                if (!parts[1].equals("1") && !parts[1].equals("0")) {
                    throw new CorruptFileException();
                }
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                switch (parts[0]) {
                case "T":
                    if (parts.length != 3) throw new CorruptFileException();
                    task = new Todo(isDone, description);
                    break;
                case "D":
                    if (parts.length != 4) throw new CorruptFileException();
                    String byStr = parts[3];
                    LocalDateTime by = null;
                    try {
                        by = LocalDateTime.parse(byStr, Pookie.OUTPUT_FORMATTER);
                    } catch (DateTimeParseException e) {
                        throw new CorruptFileException();
                    }
                    task = new Deadline(isDone, description, by);
                    break;
                case "E":
                    if (parts.length != 5) throw new CorruptFileException();
                    String fromStr = parts[3];
                    String toStr = parts[4];
                    LocalDateTime from = null;
                    LocalDateTime to = null;
                    try {
                        from = LocalDateTime.parse(fromStr, Pookie.OUTPUT_FORMATTER);
                        to = LocalDateTime.parse(toStr, Pookie.OUTPUT_FORMATTER);
                    } catch (DateTimeParseException e) {
                        throw new CorruptFileException();
                    }
                    task = new Event(isDone, description, from, to);
                    break;
                default:
                    throw new CorruptFileException();
                }
                tasks.add(task);
            }
            return tasks;
        } catch (FileNotFoundException e) {
            // Return an empty task list if the file is not found
            return new ArrayList<>();
        }
    }

    /**
     * Saves the given list of tasks to the specified file.
     * If the file or its parent directory does not exist, they are created.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an I/O error occurs during file writing.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        file.getParentFile().mkdirs();  // Ensure the parent directory exists
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                pw.println(task.toFileString());
            }
        }
    }
}