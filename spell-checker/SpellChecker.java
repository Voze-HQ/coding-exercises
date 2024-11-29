import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellChecker {
    private static Set<String> nounsSet = new HashSet<String>();

    public static void init(Set<String> nouns) {
        nounsSet = nouns;
    }

    private static boolean isCorrectSpelling(Set<String> dictionary, String word) {
        return dictionary.contains(word.toLowerCase()) || nounsSet.contains(word.toLowerCase());
    }

    public static int levenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] costTable = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            costTable[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            costTable[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;

                costTable[i][j] = Math.min(
                        costTable[i - 1][j] + 1,
                        Math.min(
                                costTable[i][j - 1] + 1,
                                costTable[i - 1][j - 1] + cost));
            }
        }
        return costTable[m][n];
    }

    private static List<String> getSuggestions(Set<String> dictionary, String word) {
        List<String> suggestions = new ArrayList<>();
        int threshold = 1;

        for (String dictWord : dictionary) {
            int distance = levenshteinDistance(word, dictWord);
            if (distance <= threshold) {
                suggestions.add(dictWord);
            }
        }
        return suggestions;
    }

    public static List<WordInfo> compile(Set<String> dictionary, String content) {
        List<WordInfo> wordList = new ArrayList<>();
        String[] lines = content.split("\\R");

        for (int lineNum = 0; lineNum < lines.length; lineNum++) {
            String line = lines[lineNum];
            int columnIndex = 0;

            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                String previousWord = i > 0 ? words[i - 1] : "";
                String nextWord = i < words.length - 1 ? words[i + 1] : "";
                String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "");
                if (!word.isEmpty() && !cleanedWord.isEmpty()) {
                    columnIndex = line.indexOf(word, columnIndex);
                    boolean isSpellingCorrect = SpellChecker.isCorrectSpelling(dictionary, cleanedWord);
                    List<String> suggestions = new ArrayList<String>();

                    if (!isSpellingCorrect) {
                        suggestions = SpellChecker.getSuggestions(dictionary, cleanedWord);
                    }

                    WordInfo wordInfo = new WordInfo(cleanedWord, lineNum + 1, columnIndex + 1, isSpellingCorrect,
                            suggestions, previousWord, nextWord);
                    wordList.add(wordInfo);
                    columnIndex += word.length();
                }
            }
        }
        return wordList;
    }
}
