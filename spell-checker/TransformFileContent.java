import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TransformFileContent {
    public static String getFileContent(String dictionaryFilePath) {
       StringBuilder builder= new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line.trim().toLowerCase());
                builder.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary  file: " + e.getMessage());
        }

        return builder.toString();
    }

    public static Set<String> getDictionaryContent(String dictionaryFilePath) {
        Set<String> dictionary = new HashSet<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary  file: " + e.getMessage());
        }

        return dictionary;
    }

    public static Set<String> getNounsContents(String nounsFilePath) {
        Set<String> nouns = new HashSet<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nounsFilePath))) {
            String noun;
            while ((noun = reader.readLine()) != null) {
                nouns.add(noun.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary  file: " + e.getMessage());
        }

        return nouns;
    }
}
