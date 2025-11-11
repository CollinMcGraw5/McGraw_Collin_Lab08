import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

public class FileUtils {
    public static File chooseFile(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(parent);
        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile() : null;
    }

    public static void saveTagsToFile(JFrame parent, Map<String, Integer> tagMap) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Tags");
        int result = chooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                tagMap.forEach((word, freq) -> writer.println(word + ": " + freq));
                JOptionPane.showMessageDialog(parent, "Tags saved to " + file.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Error saving file.");
            }
        }
    }
}