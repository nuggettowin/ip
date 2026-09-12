package janet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles Reading and writing tasks to file
 */
public class Storage {

    private static final String FILE_PATH = "data/tasks.txt";
    private final File file;

    /**
     * Creates a storage file at the specified path, creating any missing parent
     * directories and the file itself.
     *
     * @throws IOException If the directories or storage file cannot be created.
     */
    public Storage() throws IOException {
        this.file = new File(Storage.FILE_PATH);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }


    /**
     * Writes the specified text to the storage file.
     *
     * @throws IOException If the file cannot be written to.
     */
    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        fw.write(textToAdd);
        fw.close();
    }


    /**
     * Reads all stored tasks from the storage file and returns them as a task list.
     *
     * @return <code>TaskList</code> containing the tasks stored in the file.
     * @throws FileNotFoundException If the storage file cannot be found.
     * @throws JanetException If a stored task cannot be parsed.
     */
    public TaskList readFromFile() throws FileNotFoundException, JanetFileException {
        try {
            Scanner sc = new Scanner(this.file);
            TaskList taskList = new TaskList();
            while (sc.hasNextLine()) {
                String currLine = sc.nextLine();
                taskList = StorageTaskParser.processBaseTask(currLine)
                        .processStorageCommand(taskList)
                        .updatedTaskList()
                        .orElse(taskList);
            }
            return taskList;
        } catch (JanetException e) {
            throw new JanetFileException(String.format(
                    "I have stopped this process as your file is malformed: %s.", e.toString())
            );
        }
    }
}
