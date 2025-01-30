package pookie;

import pookie.model.Deadline;
import pookie.model.Event;
import pookie.model.Task;
import pookie.model.Todo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Storage {
    private File file;

    public Storage(File file) {
        this.file = file;
    }

    public ArrayList<Task> loadTasks() throws CorruptFileException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) throw new CorruptFileException();

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
            return new ArrayList<>();
        }
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        file.getParentFile().mkdirs();
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        for (Task task : tasks) {
            pw.println(task.toFileString());
        }
        pw.close();
    }
}

