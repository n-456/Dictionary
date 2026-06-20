import models.Dictionary;
import models.TypeOfWord;
import models.Word;
import repository.impl.WordGAO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        System.out.println("\n=====LOAD FILE=====\n");
        //Test load from fiile
        WordGAO wordGAO = new WordGAO("resource/dictionary.json");
        wordGAO.loadFromFile(dictionary);
        System.out.println("\n=====IN-ORDER=====\n");
        // Result of load from file
        dictionary.inOrder(dictionary.getRoot());

        System.out.println("\n=====REVERSE IN-ORDER=====\n");
        dictionary.reverseInOrder(dictionary.getRoot());

        System.out.println("\n=====INSERT=====\n");
        // Test insert
        TypeOfWord type1 = new TypeOfWord("kiến","Noun","An ant");
        Word wordInsert = new Word("ant",List.of(type1));
        dictionary.insertWord(wordInsert);
        System.out.println("Đã thêm từ 'ant'");

        System.out.println("\n=====SEARCH=====\n");
        // Test search
        String search1 = "ant";
        Word resultSearch1 = dictionary.searchWord(search1);
        if (resultSearch1 == null) {
            System.out.println("WORD 1: Can't found \"" + search1 + "\"");
        } else {
            System.out.println("WORD 1: " + resultSearch1);
        }
        // Test search
        String search2 = "set of china";
        Word resultSearch2 = dictionary.searchWord(search2);
        if (resultSearch2 == null) {
            System.out.println("WORD 2: Can't found \"" + search2 + "\"");
        } else {
            System.out.println("WORD 2: " + resultSearch2);
        }

        System.out.println("\n=====SEARCH SUBSTRING=====\n");
        // Test search substring
        String substring = "se";
        List<Word> words = dictionary.searchSubstringWord(substring);
        for (Word word : words) {
            System.out.println("WORD CONTSAINS \"" + substring + "\": " + word);
        }
    }
}
