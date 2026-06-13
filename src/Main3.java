import models.Dictionary;
import repository.impl.WordGAO;

public class Main3 {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        //Test load from fiile
        WordGAO wordGAO = new WordGAO();
        wordGAO.loadFromFile(dictionary);

        // Result of load from file
        dictionary.inOrder(dictionary.getRoot());
    }
}
