public interface Ui {
    void showMessage(String message);
    void showMessages(String... messages);
    String readCommand();
    void showInvalidTaskNumberError();
    void showInvalidDateError();
    void close();
}
