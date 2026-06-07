import models.Dictionary;
import models.Node;
import models.Word;
import repository.impl.WordGAO;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Test readAll()
        WordGAO wordGAO = new WordGAO();
        List<Word> words = wordGAO.readAll();
        //Print result
        System.out.println("---List hien tai---");
        for(Word word:words){
            System.out.println(word);
        }

        //Test writeAll()


        Dictionary dictionary = new Dictionary();

        System.out.println("=== 1. THỰC HIỆN THÊM CÁC NÚT (INSERT) ===");

        dictionary.insert(new Word("cat"));
        dictionary.insert(new Word("bed"));
        dictionary.insert(new Word("ant"));
        dictionary.insert(new Word("hen"));
        dictionary.insert(new Word("see"));
        dictionary.insert(new Word("pet"));

        //writeAll()
        wordGAO.writeAll(dictionary.getRoot());

        //Print result
        System.out.println("---List sau khi them---");
        words = wordGAO.readAll();
        for(Word word:words){
            System.out.println(word);
        }

    }
}