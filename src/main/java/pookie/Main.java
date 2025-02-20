package pookie;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pookie.ui.MainWindow;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Pookie pookie;

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Pookie");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow window = fxmlLoader.<MainWindow>getController();
            pookie = new Pookie(window, false);
            window.setPookie(pookie);  // inject the Duke instance
            stage.show();

            stage.setOnCloseRequest(event -> {
                try {
                    handleAppQuit(event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CorruptFileException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAppQuit(WindowEvent event) throws Exception {
        pookie.bye();
    }
}
