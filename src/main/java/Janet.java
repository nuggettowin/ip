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

        while (true) {
            String currLine = sc.nextLine().trim();
            System.out.printf("Current line: %s\n", currLine);

            if (currLine.equals(exitWord)) {
                break;
            }

            String cmd = currLine.split("\\s+")[0];
            String argsLine = currLine.substring(cmd.length()).trim(); // remaining string

            switch (cmd) {
            case "list":
                System.out.println(taskList.listTasks()
                        .orElse("No tasks listed!"));
                break;
            case "mark":
                taskList = taskList.markTask(Integer.parseInt(sc.next()));
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
            default:
                System.out.println("Sorry, I don't understand this command :(");
            }
        }
        System.out.printf("%s\n", goodbye);
    }

    private static TaskList addAndGetTaskList(Task task, TaskList taskList) {
        TaskList ret = taskList.addTask(task);
        System.out.printf("Added %s\n", task);
        return ret;
    }
}
