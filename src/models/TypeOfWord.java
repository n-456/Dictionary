package models;

public class TypeOfWord {
    private String meaning;
    private String type;
    private String example;

    public TypeOfWord (String meaning, String type, String example) {
        this.meaning = meaning;
        this.type = type;
        this.example = example;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "TypeOfWord{" +
                "meaning='" + meaning + '\'' +
                ", type='" + type + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}
