import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Storage {
    private File file;

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

    public String readFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(this.file);
        StringBuilder ret = new StringBuilder();
        while (sc.hasNextLine()) {
            ret.append(sc.nextLine()).append("\n");
        }
        return ret.toString();
    }
}
