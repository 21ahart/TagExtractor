import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileToString {
    private String filePath;
    private String fileContent;

    public FileToString(String filePath) {
        this.filePath = filePath;
        readFile();
    }
    private void readFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileContent = contentBuilder.toString();
    }

    public String getFileContent() {
        return fileContent;
    }


}
