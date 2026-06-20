package models;

public class TypeOfWord {
    private String meaning;
    private String type;
    private String example;


    // Constructor
    public TypeOfWord (String meaning, String type, String example) {
        this.meaning = meaning;
        this.type = type;
        this.example = example;
    }


    // Getters, setters
    public String getMeaning() {
        return (meaning != null) ? meaning : "";
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return (type != null) ? type : "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return (example != null) ? example : "";
    }

    public void setExample(String example) {
        this.example = example;
    }


    @Override
    public String toString() {
        return "\n(" + type + ") " +
                meaning +
                ". EX: " + example + "\n";
    }

}
