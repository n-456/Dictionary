package models;

import java.util.List;

public class Word {
    private String keyOfWord;
    private List<TypeOfWord> typeOfWordList;


    // Constructor
    public Word(String keyOfWord, List<TypeOfWord> typeOfWordList) {
        this.keyOfWord = keyOfWord.trim().toLowerCase();
        this.typeOfWordList = typeOfWordList;
    }


    // Getters, setters
    public String getKeyOfWord() {
        return (keyOfWord != null) ? keyOfWord : "";
    }

    public void setKeyOfWord(String keyOfWord) {
        this.keyOfWord = keyOfWord.trim().toLowerCase();
    }

    public List<TypeOfWord> getTypeOfWordList() {
        return typeOfWordList;
    }

    public void setTypeOfWordList(List<TypeOfWord> typeOfWordList) {
        this.typeOfWordList = typeOfWordList;
    }


    @Override
    public String toString() {
        return "\n"   + keyOfWord +
                ": \n" + typeOfWordList +
                "\n";
    }
}
