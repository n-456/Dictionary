import models.Dictionary;
import models.Word;
import repository.impl.WordGAO;

import java.util.List;

public class Main3 {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        //Test load from fiile
        WordGAO wordGAO = new WordGAO();
        wordGAO.loadFromFile(dictionary);

        // Result of load from file
        dictionary.inOrder(dictionary.getRoot());


        System.out.println("\n=====SEARCH SUBSTRING=====\n");
        // Test search substring
        String substring = "se";
        List<Word> words = dictionary.searchSubstringWord(substring);
        for (Word word : words) {
            System.out.println("WORD CONTSAINS \"" + substring + "\": " + word);
        }


        System.out.println("\n=====SEARCH=====\n");
        // Test search
        String search1 = "and";
        Word resultSearch1 = dictionary.searchWord(search1);
        if (resultSearch1 == null) {
            System.out.println("WORD 1: Can't found \"" + search1 + "\"");
        } else {
            System.out.println("WORD 1: " + resultSearch1);
        }
        // Test search
        String search2 = "aim";
        Word resultSearch2 = dictionary.searchWord(search2);
        if (resultSearch2 == null) {
            System.out.println("WORD 2: Can't found \"" + search2 + "\"");
        } else {
            System.out.println("WORD 2: " + resultSearch2);
        }
    }
}
