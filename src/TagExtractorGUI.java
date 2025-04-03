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

        JButton chooseTextButton = new JButton("Choose Text File");
        chooseTextButton.addActionListener(e -> chooseFile("text"));

        chooseTextButton.add(textLabel);

        JButton chooseStopWordsButton = new JButton("Choose Stop Words File");
        chooseStopWordsButton.addActionListener(e -> chooseFile("stop"));

        chooseStopWordsButton.add(stopWordsLabel);

        JButton extractTagsButton = new JButton("Extract Tags");
        extractTagsButton.addActionListener(e -> {
            runAlgorithm();
        });

        buttonPanel.add(chooseTextButton);
        buttonPanel.add(chooseStopWordsButton);
        buttonPanel.add(extractTagsButton);
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
