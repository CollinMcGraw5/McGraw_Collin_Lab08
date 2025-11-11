import java.io.*;
import java.util.*;

public class TagProcessor {
    public static Set<String> loadStopWords(File file) {
        Set<String> stopWords = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopWords;
    }

    public static Map<String, Integer> extractTags(File file, Set<String> stopWords) {
        Map<String, Integer> tagMap = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty() && !stopWords.contains(word)) {
                        tagMap.put(word, tagMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tagMap;
    }
}