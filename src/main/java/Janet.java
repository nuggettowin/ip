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
        String curr;
        TaskList taskList = new TaskList();
        while (!(curr = sc.next()).equals(exitWord)) {
            System.out.println(curr);

            switch (curr) {
            case "list":
                System.out.println(taskList.listTasks()
                        .orElse("No tasks listed!"));
                break;
            case "mark":
                taskList = taskList.markTask(Integer.parseInt(sc.next()));
                break;
            case "todo":
                taskList = Janet.addAndGetTaskList(new Todo(false, ""), taskList);
                break;
            case "deadline":
                taskList = Janet.addAndGetTaskList(new Deadline(false, ""), taskList);
                break;
            case "event":
                taskList = Janet.addAndGetTaskList(new Event(false, ""), taskList);
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
