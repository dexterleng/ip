package pookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pookie.ui.Ui;

public class MockUi implements Ui {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void showMessage(String message) {
        messages.add(message);
    }

    @Override
    public void showMessages(String... messages) {
        this.messages.addAll(Arrays.asList(messages));
    }

    @Override
    public String readCommand() {
        return "";
    }

    @Override
    public void showInvalidTaskNumberError() {
        messages.add("Please provide a valid task number.");
    }

    @Override
    public void showInvalidDateError() {
        messages.add("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
    }

    @Override
    public void close() {
    }

    public List<String> getMessages() {
        return messages;
    }
}