import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Janet {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    private static final String exitWord = "bye";
    private static final String FILE_PATH = "data/tasks.txt";

    public Janet() throws IOException {
        this.tasks = new TaskList();
        this.storage = new Storage(Janet.FILE_PATH);
        this.ui = new Ui();
    }


    public static void main(String[] args) {
        try {
             new Janet().run();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void run() throws FileNotFoundException {
        this.ui.showGreeting();
        Scanner sc = new Scanner(System.in);
        try {
            this.tasks = this.storage.readFromFile();
        } catch (JanetException e) {
            System.out.printf("Failure: %s\n", e.toString());
        }

        while (true) {
            String currLine = sc.nextLine().trim();
            System.out.printf("Current line: %s\n", currLine);

            if (currLine.equals(exitWord)) {
                break;
            }

            try {
                TaskList.CommandResult res = new Parser(this.tasks).processCommand(currLine);
                this.tasks = res.updatedTaskList().orElse(this.tasks);
                System.out.println(res.message());

                this.storage.writeToFile(
                        res.updatedTaskList()
                        .map(x -> x.toString())
                        .orElse(new TaskList().toString())
                );
            } catch (IOException e) {
                System.out.printf("IO Failure: %s\n", e.toString());
            } catch (JanetException e) {
                System.out.printf("Failure: %s\n", e.toString());
            }

        }
        this.ui.showGoodbye();
    }
}
