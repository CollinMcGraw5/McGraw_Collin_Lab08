import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class TagExtractorGUI extends JFrame {
    private JTextArea outputArea;
    private File textFile = null;
    private File stopWordsFile = null;
    private Map<String, Integer> tagMap;

    public TagExtractorGUI() {
        setTitle("Tag Extractor");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton loadTextButton = new JButton("Load Text File");
        JButton loadStopButton = new JButton("Load Stop Words");
        JButton extractButton = new JButton("Extract Tags");
        JButton saveButton = new JButton("Save Tags");

        buttonPanel.add(loadTextButton);
        buttonPanel.add(loadStopButton);
        buttonPanel.add(extractButton);
        buttonPanel.add(saveButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load text file
        loadTextButton.addActionListener(e -> {
            File selected = FileUtils.chooseFile(this);
            if (selected != null) {
                textFile = selected;
                outputArea.append("Loaded text file: " + textFile.getName() + "\n");
            }
        });

        // Load stop words file
        loadStopButton.addActionListener(e -> {
            File selected = FileUtils.chooseFile(this);
            if (selected != null) {
                stopWordsFile = selected;
                outputArea.append("Loaded stop words file: " + stopWordsFile.getName() + "\n");
            }
        });

        // Extract tags
        extractButton.addActionListener(e -> {
            if (textFile != null && stopWordsFile != null) {
                Set<String> stopWords = TagProcessor.loadStopWords(stopWordsFile);
                tagMap = TagProcessor.extractTags(textFile, stopWords);
                outputArea.append("\nTags and Frequencies:\n");
                outputArea.append("----------------------\n");
                tagMap.forEach((word, freq) -> outputArea.append(word + ": " + freq + "\n"));
            } else {
                JOptionPane.showMessageDialog(this, "Please load both files first.");
            }
        });

        // Save tags
        saveButton.addActionListener(e -> {
            if (tagMap != null && !tagMap.isEmpty()) {
                FileUtils.saveTagsToFile(this, tagMap);
            } else {
                JOptionPane.showMessageDialog(this, "No tags to save.");
            }
        });

        setVisible(true);
    }
}