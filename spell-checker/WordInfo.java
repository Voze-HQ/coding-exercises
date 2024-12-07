import java.util.List;

class WordInfo {
    String word;
    int lineNumber;
    int columnNumber;
    boolean isSpellingCorrect;
    List<String> suggestions;
    String previousWord;
    String nextWord;

    WordInfo(String word, int lineNumber, int columnNumber,boolean isSpellingCorrect, List<String> suggestions, String previousWord, String nextWord) {
        this.word = word;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.isSpellingCorrect= isSpellingCorrect;
        this.suggestions=suggestions;
        this.previousWord=previousWord;
        this.nextWord= nextWord;
    }

    @Override
    public String toString() {
        String context = (previousWord != "" ? (" previous word is `" + previousWord + "`") : "")
                + (nextWord != "" ? (" next word is `" + nextWord + "`") : "");
                
        return "WordInfo {\n word:" + word + ",\n lineNumber:" + lineNumber + ",\n columnNumber:" + columnNumber
                + ",\n isSpellingCorrect:" + isSpellingCorrect + ",\n suggestions:" + suggestions + 
                ",\n context: " +context +"\n}\n";
    }
}