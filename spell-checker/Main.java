
import java.security.InvalidParameterException;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        validateInput(args);
        String dictionaryFilePath = args[0];
        String spellCheckFilePath = args[1];
        String nounsFilePath = args[2];
        String content= TransformFileContent.getFileContent(spellCheckFilePath);
        Set<String> dictionary =TransformFileContent.getDictionaryContent(dictionaryFilePath);
        Set<String> nouns =TransformFileContent.getNounsContents(nounsFilePath);
        SpellChecker.init(nouns);
        List<WordInfo> wordInfoList= SpellChecker.compile(dictionary, content);

        for (WordInfo wordInfo : wordInfoList) {
            if (!wordInfo.isSpellingCorrect)
                System.out.println(wordInfo);
        }
    }

    public static void validateInput(String[] args) {
        if (args.length < 1) {
            throw new InvalidParameterException("Dictionary file name or path not provided");
        }

        if (args.length < 2) {
            throw new InvalidParameterException("Spell check file name or path not provided");
        }

        if (args.length < 3) {
            throw new InvalidParameterException("Nouns file name or path not provided");
        }
    }
};