package pookie.ui;

import java.util.Scanner;

public class ConsoleUi implements Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        showMessages(message);
    }

    public void showMessages(String... messages) {
        printLine();
        for (String message : messages) {
            System.out.println(" " + message);
        }
        printLine();
        System.out.println();
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showInvalidTaskNumberError() {
        showMessage("Please provide a valid task number.");
    }

    public void showInvalidDateError() {
        showMessage("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
    }

    public void close() {
        scanner.close();
    }

    private void printLine() {
        System.out.println("____________________________________________________________");
    }
}
