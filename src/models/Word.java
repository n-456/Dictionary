package models;

import java.util.List;

public class Word {
    private String name;
    private List<TypeOfWord> typeOfWord;

    // Constructor
    public Word(String name) {
        this.name = name;
    }
    public Word(List<TypeOfWord> typeOfWord){
        this.typeOfWord = typeOfWord;
    }
    public Word(String name, List<TypeOfWord> typeOfWord) {
        this.name = name;
        this.typeOfWord = typeOfWord;
    }

    // Getters, setters
    public String getName() {
        return (name != null) ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeOfWord> getTypeOfWord() {
        return typeOfWord;
    }

    public void setTypeOfWord(List<TypeOfWord> typeOfWord) {
        this.typeOfWord = typeOfWord;
    }

    @Override
    public String toString() {
        return "\n"   + name +
               ": \n" + typeOfWord +
               "\n";
    }
}
