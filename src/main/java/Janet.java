import java.util.Scanner;

public class Janet {
    private static final String greeting = "Hi! I'm Janet, your friendly informational assistant. Let me know what you need!";
    private static final String banner = """
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
        System.out.printf("%s\n%s\n", Janet.banner, Janet.greeting);
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

            String cmd = currLine.split("\\s+")[0].trim();
            String argsLine = currLine.substring(cmd.length()).trim(); // remaining string
            System.out.printf("cmd: %s\nargs:%s\n", cmd, argsLine);

            try {
                processCmd(cmd, argsLine, taskList);
            } catch (JanetException e) {
                System.out.printf("Failure: %s\n", e.toString());
            }

        }
        System.out.printf("%s\n", goodbye);
    }

    private static void processCmd(String cmd, String argsLine, TaskList taskList) throws JanetException {
        switch (cmd) {
        case "list":
            System.out.println(taskList.listTasks()
                    .orElse("No tasks listed!"));
            break;
        case "mark":
            taskList = taskList.markTask(Integer.parseInt(argsLine));
            break;
        case "todo":
            taskList = Janet.addAndGetTaskList(new Todo(false, argsLine), taskList);
            break;
        case "deadline":
            taskList = Janet.addAndGetTaskList(new Deadline(false, argsLine), taskList);
            break;
        case "event":
            taskList = Janet.addAndGetTaskList(new Event(false, argsLine), taskList);
            break;
        case "delete":
            taskList = taskList.deleteTask(Integer.parseInt(argsLine));
            break;
        default:
            System.out.println("Sorry, I don't understand this command :(");
            throw new JanetException("Unrecognised command!");
        }
    }

    private static TaskList addAndGetTaskList(Task task, TaskList taskList) {
        TaskList ret = taskList.addTask(task);
        System.out.printf("Added %s\n", task);
        return ret;
    }
}
