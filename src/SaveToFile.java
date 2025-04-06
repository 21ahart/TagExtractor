import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class SaveToFile {
    private String results;
    private String filePath;

    private static final String DEFAULT_FILE_PATH = "extracted_tags.txt";

    public SaveToFile(String results, String filePath) {
        this.results = results;
        this.filePath = filePath != null ? filePath : DEFAULT_FILE_PATH;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(results);
            System.out.println("Results saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }
}