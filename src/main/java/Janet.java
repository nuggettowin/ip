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
    private static final String goodbye = "Bye!";
    private static final String exitWord = "bye";

    public static void main(String[] args) {
        System.out.printf("%s\n%s\n", Janet.banner, Janet.greeting);

        Scanner sc = new Scanner(System.in);
        String curr;
        TaskList taskList = new TaskList();
        while (!(curr = sc.next()).equals(exitWord)) {
            System.out.println(curr);
            if (curr.equals("list")) {
                System.out.println(taskList.listTasks());
            } else {
                TaskList.AddTaskResult res = taskList.addTask(curr);
                taskList = res.taskList();
                System.out.println((res.resultString()));
            }

        }

        System.out.printf("%s\n", goodbye);
    }
}
