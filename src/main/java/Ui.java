public class Ui {
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

    public void showGreeting() {
        System.out.printf("%s\n%s\n", Ui.BANNER, Ui.GREETING);
    }

    public void showGoodbye() {
        System.out.printf(Ui.goodbye);
    }
}
