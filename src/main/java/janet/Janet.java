package janet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the main application that manages tasks and handles user commands.
 */
public class Janet {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new Janet application and initializes its storage, task list,
     * and user interface.
     *
     * @throws IOException If the task storage cannot be initialized.
     */
    public Janet() throws IOException, JanetException {
        this.storage = new Storage();
        this.ui = new Ui();
        this.tasks = this.storage.readFromFile();
    }

    /**
     * Starts the Janet application.
     *
     * @param args Command-line arguments.
     * @throws IOException If the task storage cannot be initialized.
     * @throws JanetException If the storage is poorly formatted; user rectification preferred
     */
    public static void main(String[] args) throws IOException, JanetException {
        new Janet().run();
    }

    private void run() throws FileNotFoundException {
        this.ui.showGreeting();
        Scanner sc = new Scanner(System.in);
        try {
            this.tasks = this.storage.readFromFile();
        } catch (JanetException e) {
            this.ui.showError(String.format("Failure: %s\n", e.toString()));
        }

        while (true) {
            String formattedLine = Parser.formatString(sc.nextLine());

            if (Parser.isExitCommand(formattedLine)) {
                break;
            }
            try {
                TaskList.CommandResult res = Janet.getResponse(this, formattedLine);
                Janet.processCommandResult(this, res);
            } catch (IOException e) {
                this.ui.showError(String.format("IO Failure: %s\n", e.toString()));
            } catch (JanetException e) {
                this.ui.showError(String.format("Failure: %s\n", e.toString()));
            }

        }
        this.ui.showGoodbye();
    }

    public static TaskList.CommandResult getResponse(Janet janet, String input) throws JanetException {
        return new Parser(janet.tasks).processCommand(input);
    }

    public static void processCommandResult(Janet janet, TaskList.CommandResult commandResult) throws IOException {
        janet.storage.writeToFile(
                commandResult.updatedTaskList()
                        .map(x -> x.toString())
                        .orElse(new TaskList().toString())
        );
        janet.tasks = commandResult.updatedTaskList().orElse(janet.tasks);
        janet.ui.showMessage(commandResult.message());
    }
}
