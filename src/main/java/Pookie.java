import java.util.Scanner;

public class Pookie {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Pookie");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________\n");

        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100]; // Fixed-size array to store up to 100 tasks
        int taskCount = 0; // Counter for the number of tasks stored

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet.");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else {
                if (taskCount < tasks.length) {
                    tasks[taskCount] = input;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println(" added: " + input);
                    System.out.println("____________________________________________________________\n");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Task list is full. Cannot add more tasks.");
                    System.out.println("____________________________________________________________\n");
                }
            }
        }

        scanner.close();
    }
}
