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
    private Image janetImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Janet instance and displays a pending storage-recovery prompt in the chat.
     *
     * @param j Janet application that backs this window.
     */
    public void setJanet(Janet j) {
        this.janet = j;
        if (this.janet.isStorageRecoveryRequired()) {
            this.addJanetMessage(this.janet.getStorageRecoveryPrompt());
        }
    }

    /**
     * Displays an unrecoverable startup error and prevents commands from being submitted without Janet.
     *
     * @param errorMessage Error to display in Janet's chat dialog.
     */
    public void showStartupError(String errorMessage) {
        this.addJanetMessage(errorMessage);
        this.userInput.setDisable(true);
        this.sendButton.setDisable(true);
    }

    /**
     * Creates a new dialog box in response to user input and appends it to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (this.janet == null) {
            return;
        }
        if (this.janet.isStorageRecoveryRequired()) {
            this.handleStorageRecovery(input);
        } else if (Parser.isExitCommand(Parser.formatString(input))) {
            Platform.exit();
            return;
        } else {
            try {
                TaskList.CommandResult response = Janet.getResponse(this.janet, input);
                Janet.processCommandResult(this.janet, response);
                this.addToDialogContainer(input, response.message());
            } catch (IOException e) {
                this.addToDialogContainer(input, String.format("IO failure: %s", e.toString()));
            } catch (JanetException e) {
                this.addToDialogContainer(input, e.toString());
            }
        }
        userInput.clear();
    }

    /**
     * Handles a chat response to the malformed-storage recovery prompt.
     *
     * @param input User response entered in the chat field.
     */
    private void handleStorageRecovery(String input) {
        try {
            if (this.janet.resolveStorageRecovery(input)) {
                this.addToDialogContainer(input, "Saved tasks were cleared. Janet is ready for a fresh start.");
            } else {
                this.addToDialogContainer(input, "Janet will now close.");
                Platform.exit();
            }
        } catch (IOException e) {
            this.addToDialogContainer(input, String.format("IO failure: %s", e.toString()));
        }
    }

    private void addToDialogContainer(String input, String message) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJanetDialog(message, janetImage)
        );
    }

    /**
     * Adds a Janet-only dialog, used for messages that do not have a user command counterpart.
     *
     * @param message Message to show in Janet's chat dialog.
     */
    private void addJanetMessage(String message) {
        dialogContainer.getChildren().add(DialogBox.getJanetDialog(message, janetImage));
    }
}
