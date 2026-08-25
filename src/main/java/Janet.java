import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Janet {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    private static final String FILE_PATH = "data/tasks.txt";

    public Janet() throws IOException {
        this.tasks = new TaskList();
        this.storage = new Storage(Janet.FILE_PATH);
    }

    private static final String GREETING = "Hi! I'm Janet, your friendly informational assistant. Let me know what you need!";
    private static final String BANNER = """
                  ⠀ ⠘⠒⠖⠲⠒⠖⠲⠒⠖⠲⠒⠖⠲⢤⣀⠀
                ⠀⠀⠀⣀⣴⠦⠠⣤⠤⠤⠤⠤⠤⠤⠤⠤⠤⣤⠈⣆
                ⠀⣰⡾⠋⠀⠀⠀⣻⠀⠀⠀⠀⣖⣳⠀⠀⠀⣽⠀⣸
                ⣰⠏⠖⣀⠀⠀⠀⣯⠀⠀⠀⠀⠀⠀⠀⠀⣰⢃⡴⠃
                ⡇⠀⠀⠈⣳⣄⢀⡽⢤⡤⢤⡤⣤⠤⠔⠚⠉⠁⠀⠀
                ⢭⣀⣀⡀⢠⠎⠛⠀⠀⠀⡎⠱⡌⢢⠀⠀⠀⠀⠀⠀
                ⣽⠀⠀⠀⣇⠀⠀⠀⠀⠀⠇⠀⠇⠸⠀⠀⠀⠀⠀⠀
                ⢻⣦⡠⠐⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠈⠳⣎⠙⠢⣄⠀⠀⢀⣤⠴⡶⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠈⠓⠶⠤⣉⣹⣁⣀⣋⣧⠀⠀⠀⠀⠀⠀⠀
            """;
    private static final String goodbye = "Bye! Take it sleazy!";
    private static final String exitWord = "bye";

    public static void main(String[] args) {
        System.out.printf("%s\n%s\n", Janet.BANNER, Janet.GREETING);
        Scanner sc = new Scanner(System.in);
        try {
             new Janet().run(sc);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void run(Scanner sc) throws FileNotFoundException {
        System.out.println(this.storage.readFromFile());

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

                this.storage.writeToFile(res.updatedTaskList().toString());
            } catch (IOException e) {
                System.out.printf("IO Failure: %s\n", e.toString());
            } catch (JanetException e) {
                System.out.printf("Failure: %s\n", e.toString());
            }

        }
        System.out.printf("%s\n", goodbye);
    }
}
