package models;

public class TypeOfWord {
    private String meaning;
    private String partOfSpeech;
    private String example;


    // Constructor
    public TypeOfWord (String meaning, String partOfSpeech, String example) {
        this.meaning = meaning;
        this.partOfSpeech = partOfSpeech;
        this.example = example;
    }


    // Getters, setters
    public String getMeaning() {
        return (meaning != null) ? meaning : "";
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPartOfSpeech() {
        return (partOfSpeech != null) ? partOfSpeech : "";
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getExample() {
        return (example != null) ? example : "";
    }

    public void setExample(String example) {
        this.example = example;
    }


    @Override
    public String toString() {
        return "\n(" + partOfSpeech + ") " +
                meaning +
                ". EX: " + example + "\n";
    }

}