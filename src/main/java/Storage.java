import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Storage {
    private File file;
    protected static final String LINE_SEP = Pattern.quote("|");

    // handle the case where data file or folder doesn't exist at the start
    public Storage(String filePath) throws IOException {
        this.file = new File(filePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        fw.write(textToAdd);
        fw.close();
    }

    public TaskList readFromFile() throws FileNotFoundException, JanetException {
        Scanner sc = new Scanner(this.file);
        TaskList taskList = new TaskList();
        while (sc.hasNextLine()) {
            Parser parser = new Parser(taskList);
            String currLine = sc.nextLine();
            taskList = parser
                    .processStorageCommand(currLine)
                    .updatedTaskList()
                    .orElse(taskList);
        }
        return taskList;
    }
}
