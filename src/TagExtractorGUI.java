import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.io.File;

public class TagExtractorGUI extends JFrame {
    private String textAsString;
    private String stopWordsAsString;
    private String results;
    private String textSelectedFilePath = "No file selected";
    private String stopWordsSelectedFilePath = "No file selected";
    private JLabel textLabel = new JLabel("Text File: " + textSelectedFilePath);
    private JLabel stopWordsLabel = new JLabel("Stop Words File: " + stopWordsSelectedFilePath);
    private JTextArea resultArea = new JTextArea(20, 50);

    public TagExtractorGUI(){
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window on the screen

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertical layout for the panel

        // Panel for "Choose Text File" button and its label
        JPanel textFilePanel = new JPanel();
        textFilePanel.setLayout(new BoxLayout(textFilePanel, BoxLayout.Y_AXIS));
        JButton chooseTextButton = new JButton("Choose Text File");
        chooseTextButton.addActionListener(e -> chooseFile("text"));
        textFilePanel.add(chooseTextButton);
        textFilePanel.add(textLabel);

        // Panel for "Choose Stop Words File" button and its label
        JPanel stopWordsFilePanel = new JPanel();
        stopWordsFilePanel.setLayout(new BoxLayout(stopWordsFilePanel, BoxLayout.Y_AXIS));
        JButton chooseStopWordsButton = new JButton("Choose Stop Words File");
        chooseStopWordsButton.addActionListener(e -> chooseFile("stop"));
        stopWordsFilePanel.add(chooseStopWordsButton);
        stopWordsFilePanel.add(stopWordsLabel);

        // Add the panels to the main button panel
        buttonPanel.add(textFilePanel);
        buttonPanel.add(stopWordsFilePanel);

        // Add the "Extract Tags" and "Save Extracted Tags" buttons
        JButton extractTagsButton = new JButton("Extract Tags");
        extractTagsButton.addActionListener(e -> runAlgorithm());
        buttonPanel.add(extractTagsButton);

        JButton saveExtractedTagsButton = new JButton("Save Extracted Tags");
        saveExtractedTagsButton.addActionListener(e -> {
            if (results == null || results.isEmpty()) {
                resultArea.setText("No results to save. Please run the algorithm first.");
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Extracted Tags");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                SaveToFile saveToFile = new SaveToFile(results, filePath);
                saveToFile.saveToFile();
            }
        });

        buttonPanel.add(chooseTextButton);
        buttonPanel.add(chooseStopWordsButton);
        buttonPanel.add(extractTagsButton);
        buttonPanel.add(saveExtractedTagsButton);
        add(buttonPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        textPanel.add(scrollPane);
        add(textPanel, BorderLayout.CENTER);
    }

    private void chooseFile(String fileType) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            String filePath = fileToOpen.getAbsolutePath();

            if (fileType.equals("stop")){
                this.stopWordsAsString = new FileToString(filePath).getFileContent();
                this.stopWordsSelectedFilePath = filePath;
                stopWordsLabel.setText("Stop Words File: " + filePath);

            }
            else if (fileType.equals("text")){
                this.textAsString = new FileToString(filePath).getFileContent();
                this.textSelectedFilePath = filePath;
                textLabel.setText("Text File: " + filePath);
            }
        }
    }

    private void runAlgorithm() {
        Tagger algo = new Tagger(textAsString, stopWordsAsString);
        this.results = algo.wordCountMapToString();

        resultArea.setText(this.results);
        resultArea.setCaretPosition(0);
    }
}
