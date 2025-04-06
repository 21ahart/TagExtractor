import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Tagger {
    private String fileAsString;
    private String stopFileAsString;
    private HashMap<String, Integer> wordCountMap = new HashMap<>();

    public Tagger(String fileAsString, String stopFileAsString) {
        this.fileAsString = fileAsString.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
        this.stopFileAsString = stopFileAsString.toLowerCase();

        countUniqueWords();
    }

    private void countUniqueWords() {
        String[] words = fileAsString.split("\\s+");
        String[] stopWords = stopFileAsString.split("\\s+");

        for (String word : words) {
            if (!wordCountMap.containsKey(word) && !isStopWord(word, stopWords)) {
                wordCountMap.put(word, 1);
            } else if (wordCountMap.containsKey(word)) {
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            }
        }
    }

    private boolean isStopWord(String word, String[] stopWords) {
        for (String stopWord : stopWords) {
            if (word.equals(stopWord)) {
                return true;
            }
        }
        return false;
    }

    public String wordCountMapToString() {
        Map<String, Integer> sortedMap = new TreeMap<>(wordCountMap);

        StringBuilder sb = new StringBuilder();
        for (String word : sortedMap.keySet()) {
            sb.append(word).append(": ").append(sortedMap.get(word)).append("\n");
        }
        return sb.toString();
    }
}