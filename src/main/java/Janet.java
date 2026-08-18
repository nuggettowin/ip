import java.util.Scanner;

public class Janet {
    private static String greeting = "Hi! I'm Janet, your friendly informational assistant. Let me know what you need!";
    private static String banner = """
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
    private static String goodbye = "Bye!";
    private static String exitWord = "bye";

    public static void main(String[] args) {
        System.out.printf("%s\n%s\n", Janet.banner, Janet.greeting);

        Scanner sc = new Scanner(System.in);
        String curr;
        while (!(curr = sc.next()).equals(exitWord)) {
            System.out.println(curr);
        }

        System.out.printf("%s\n", goodbye);
    }
}
