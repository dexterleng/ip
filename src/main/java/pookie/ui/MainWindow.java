package pookie.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pookie.Pookie;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane implements Ui {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Pookie pookie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image pookieImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setPookie(Pookie d) {
        pookie = d;
        pookie.sendInitialMessage();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws Exception {
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage)
        );

        boolean isExit = pookie.respond(input);
        if (isExit) {
            Platform.exit();
            return;
        }
        userInput.clear();
    }

    @Override
    public void showMessage(String message) {
        dialogContainer.getChildren().addAll(
            DialogBox.getPookieDialog(message, pookieImage)
        );
    }

    @Override
    public void showMessages(String... messages) {
        String message = String.join("\n", messages);
        dialogContainer.getChildren().addAll(
            DialogBox.getPookieDialog(message, pookieImage)
        );
    }

    @Override
    public String readCommand() {
        return "";
    }

    @Override
    public void showInvalidTaskNumberError() {
        showMessage("Please provide a valid task number.");
    }

    @Override
    public void showInvalidDateError() {
        showMessage("Please provide a valid date in the format dd/MM/yyyy HHmm e.g. 29/01/2001 1159.");
    }

    @Override
    public void close() {

    }
}
