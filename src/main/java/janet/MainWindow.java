package janet;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Janet janet;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Janet instance */
    public void setJanet(Janet j) {
        janet = j;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Janet's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.equals(Janet.EXIT_WORD)) {
            Platform.exit();
        }
        try {
            TaskList.CommandResult response = Janet.getResponse(janet, input);
            Janet.processCommandResult(janet, response);
            this.addToDialogContainer(input, response.message());
        } catch (IOException e) {
            this.addToDialogContainer(input, String.format("IO failure: %s", e.toString()));
        } catch (JanetException e) {
            this.addToDialogContainer(input, e.toString());
        }
        userInput.clear();
    }

    private void addToDialogContainer(String input, String message) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJanetDialog(message, dukeImage)
        );
    }
}
