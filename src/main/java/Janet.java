import java.util.Scanner;

public class Janet {
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

    public static void main() {
        System.out.printf("%s\n%s\n", Janet.BANNER, Janet.GREETING);
        Scanner sc = new Scanner(System.in);
        TaskList taskList = new TaskList();
        Janet.processLoop(sc, taskList);
    }

    private static void processLoop(Scanner sc, TaskList taskList) {
        while (true) {
            String currLine = sc.nextLine().trim();
            System.out.printf("Current line: %s\n", currLine);

            if (currLine.equals(exitWord)) {
                break;
            }

            try {
                TaskList.CommandResult res = new Parser(taskList).processCommand(currLine);
                taskList = res.updatedTaskList().orElse(taskList);
                System.out.println(res.message());
            } catch (JanetException e) {
                System.out.printf("Failure: %s\n", e.toString());
            }

        }
        System.out.printf("%s\n", goodbye);
    }
}
