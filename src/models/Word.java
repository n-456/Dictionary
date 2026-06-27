package models;

import java.util.List;

public class Word {
    private String keyOfWord;
    private List<TypeOfWord> typeOfWord;


    // Constructor
    public Word(String keyOfWord, List<TypeOfWord> typeOfWord) {
        this.keyOfWord = keyOfWord.trim().toLowerCase();
        this.typeOfWord = typeOfWord;
    }


    // Getters, setters
    public String getKeyOfWord() {
        return (keyOfWord != null) ? keyOfWord : "";
    }

    public void setKeyOfWord(String keyOfWord) {
        this.keyOfWord = keyOfWord.trim().toLowerCase();
    }

    public List<TypeOfWord> getTypeOfWord() {
        return typeOfWord;
    }

    public void setTypeOfWord(List<TypeOfWord> typeOfWord) {
        this.typeOfWord = typeOfWord;
    }


    @Override
    public String toString() {
        return "\n"   + keyOfWord +
                ": \n" + typeOfWord +
                "\n";
    }
}
